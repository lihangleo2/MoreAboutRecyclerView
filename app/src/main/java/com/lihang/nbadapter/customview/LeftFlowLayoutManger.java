package com.lihang.nbadapter.customview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

/**
 * <p>通过重写LayoutManger布局方法{@link #onLayoutChildren(RecyclerView.Recycler, RecyclerView.State)}
 * 对Item进行布局，并对超出屏幕的Item进行回收
 * <p>通过重写LayoutManger中的{@link #scrollHorizontallyBy(int, RecyclerView.Recycler, RecyclerView.State)}
 * 进行水平滚动处理
 * <p>
 * 参考https://blog.csdn.net/harvic880925/article/details/86606873
 */

public class LeftFlowLayoutManger extends RecyclerView.LayoutManager {

    /**
     * 最大存储item信息存储数量，
     * 超过设置数量，则动态计算来获取
     */
    private final int MAX_RECT_COUNT = 100;

    /**
     * 滑动总偏移量
     */
    private int mOffsetAll = 0;

    /**
     * Item宽
     */
    private int mDecoratedChildWidth = 0;

    /**
     * Item高
     */
    private int mDecoratedChildHeight = 0;

    /**
     * Item间隔与item宽的比例
     */
    private float mIntervalRatio = 1f;

    /**
     * 保存所有的Item的上下左右的偏移量信息
     */
    private SparseArray<Rect> mAllItemFrames = new SparseArray<>();

    /**
     * 记录Item是否出现过屏幕且还没有回收。true表示出现过屏幕上，并且还没被回收
     */
    private SparseBooleanArray mHasAttachedItems = new SparseBooleanArray();

    /**
     * RecyclerView的Item回收器
     */
    private RecyclerView.Recycler mRecycle;

    /**
     * RecyclerView的状态器
     */
    private RecyclerView.State mState;

    /**
     * 滚动动画
     */
    private ValueAnimator mAnimation;

    /**
     * 正显示在中间的Item
     */
    private int mSelectPosition = 0;

    /**
     * 前一个正显示在中间的Item
     */
    private int mLastSelectPosition = 0;

    /**
     * 滑动的方向：左
     */
    private static int SCROLL_LEFT = 1;

    /**
     * 滑动的方向：右
     */
    private static int SCROLL_RIGHT = 2;

    /**
     * 选中监听
     */
    private OnSelected mSelectedListener;

    /**
     * 屏幕的宽度
     */
    private int screenWidth;

    /**
     * 屏幕中最多显示完整item的个数  即最后不能滑动的item个数
     */
    private int endNum;
    private boolean isStay;

    public LeftFlowLayoutManger(int screenWidth) {
        this.screenWidth = screenWidth;
        if (mDecoratedChildWidth != 0) {
            if (isStay) {
                endNum = screenWidth / mDecoratedChildWidth;
            } else {
                endNum = 1;
            }
        }
    }

    public void stayEnd(boolean isStay) {
        this.isStay = isStay;
        if (!isStay) {
            endNum = 1;
        }
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //如果没有item，直接返回
        //跳过preLayout，preLayout主要用于支持动画
        if (getItemCount() == 0) {
            mOffsetAll = 0;
            detachAndScrapAttachedViews(recycler);
            return;
        }
        if (getItemCount() <= 0 && state.isPreLayout()) {
            mOffsetAll = 0;
            return;
        }
        detachAndScrapAttachedViews(recycler); //在布局之前，将所有的子View先Detach掉，放入到Scrap缓存中
        mAllItemFrames.clear();
        mHasAttachedItems.clear();

        //得到子view的宽和高，这边的item的宽高都是一样的，所以只需要进行一次测量
        View scrap = recycler.getViewForPosition(0);
        addView(scrap);
        measureChildWithMargins(scrap, 0, 0);
        //计算测量布局的宽高
        mDecoratedChildWidth = getDecoratedMeasuredWidth(scrap);
        mDecoratedChildHeight = getDecoratedMeasuredHeight(scrap);
        if (mDecoratedChildWidth != 0) {
            if (isStay) {
                endNum = screenWidth / mDecoratedChildWidth;
            } else {
                endNum = 1;
            }
        }
        float offset = 0;

        /**只存{@link MAX_RECT_COUNT}个item具体位置*/
        for (int i = 0; i < getItemCount() + 1 && i < MAX_RECT_COUNT; i++) {
            Rect frame = mAllItemFrames.get(i);
            if (frame == null) {
                frame = new Rect();
            }
            frame.set(Math.round(offset), 0, Math.round(offset + mDecoratedChildWidth), mDecoratedChildHeight);
            mAllItemFrames.put(i, frame);
            mHasAttachedItems.put(i, false);
            offset = offset + getIntervalDistance(); //原始位置累加，否则越后面误差越大
        }

        if ((mRecycle == null || mState == null) && mSelectPosition != 0) {  //在为初始化前调用smoothScrollToPosition 或者 scrollToPosition,只会记录位置   所以初始化时需要滚动到对应位置
            mOffsetAll = calculateOffsetForPosition(mSelectPosition);
            onSelectedCallBack();
        }

        layoutItems(recycler, state, SCROLL_RIGHT);

        mRecycle = recycler;
        mState = state;
    }

    @Override
    /**
     * dx : 滑动的距离   >0向左    <0向右
     */
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler,
                                    RecyclerView.State state) {
        if (mAnimation != null && mAnimation.isRunning()) mAnimation.cancel();
        int travel = dx;
        if (dx + mOffsetAll < 0) {//如果滑动到第一个条目则不再继续滑动   滑动到最后一个条目则也不再继续
            travel = -mOffsetAll;
        } else if (dx + mOffsetAll > getMaxOffset()) {
            travel = (int) (getMaxOffset() - mOffsetAll);
        }

        mOffsetAll += travel; //累计偏移量
        layoutItems(recycler, state, dx > 0 ? SCROLL_RIGHT : SCROLL_LEFT);
        return travel;
    }

    /**
     * 布局Item
     * <p>注意：1，先清除已经超出屏幕的item
     * <p>     2，再绘制可以显示在屏幕里面的item
     */
    private void layoutItems(RecyclerView.Recycler recycler,
                             RecyclerView.State state, int scrollDirection) {
        if (state.isPreLayout()) return;
        Rect displayFrame = new Rect(mOffsetAll, 0, mOffsetAll + getHorizontalSpace(), getVerticalSpace());
        int position = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            position = getPosition(child);
            Rect rect = getFrame(position);
            if (!Rect.intersects(displayFrame, rect)) {//Item没有在显示区域，就说明需要回收
                removeAndRecycleView(child, recycler); //回收滑出屏幕的View
                mHasAttachedItems.delete(position);
            } else { //Item还在显示区域内，更新滑动后Item的位置
                layoutItem(child, rect, position); //更新Item位置
                mHasAttachedItems.put(position, true);
            }
        }
        if (position == 0) {
            position = mSelectPosition;
        }
        int min = position - 50 >= 0 ? position - 50 : 0;
        int max = position + 50 < getItemCount() ? position + 50 : getItemCount();
        for (int i = min; i < max; i++) {
            Rect rect = getFrame(i);
            if (Rect.intersects(displayFrame, rect) && !mHasAttachedItems.get(i)) { //重新加载可见范围内的Item
                View scrap = recycler.getViewForPosition(i);
                measureChildWithMargins(scrap, 0, 0);
                if (scrollDirection == SCROLL_RIGHT) { //向左滚动，新增的Item需要添加在最前面
                    addView(scrap, 0);
                } else { //向右滚动，新增的item要添加在最后面
                    addView(scrap);
                }
                layoutItem(scrap, rect, i); //将这个Item布局出来
                mHasAttachedItems.put(i, true);
            }
        }
    }

    /**
     * 布局Item位置
     *
     * @param child 要布局的Item
     * @param frame 位置信息
     */
    private void layoutItem(View child, Rect frame, int pos) {
        if (frame.left - mOffsetAll < 0) {
            float scale = computeScaleX(frame.left - mOffsetAll);
            child.setScaleX(scale); //缩放
            child.setScaleY(scale); //缩放
            if (getCenterPosition() != getItemCount() - 1) {
                child.setAlpha(computeAlphaX(frame.left - mOffsetAll));
            }
            int left = (int) ((1 - scale) * mDecoratedChildWidth / 2);
            layoutDecorated(child,
                    -left,
                    frame.top,
                    frame.right - frame.left - left,
                    frame.bottom);
        } else {
            layoutDecorated(child,
                    frame.left - mOffsetAll,
                    frame.top,
                    frame.right - mOffsetAll,
                    frame.bottom);
            child.setScaleX(1); //缩放
            child.setScaleY(1); //缩放
            child.setAlpha(1);
        }
    }

    /**
     * 动态获取Item的位置信息
     *
     * @param index item位置
     * @return item的Rect信息
     */
    private Rect getFrame(int index) {
        Rect frame = mAllItemFrames.get(index);
        if (frame == null) {
            frame = new Rect();
            float offset = getIntervalDistance() * index; //原始位置累加（即累计间隔距离）
            frame.set(Math.round(offset), 0, Math.round(offset + mDecoratedChildWidth), mDecoratedChildHeight);
        }
        return frame;
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        switch (state) {
            case RecyclerView.SCROLL_STATE_IDLE:
                //滚动停止时
                fixOffsetWhenFinishScroll();
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:
                //拖拽滚动时
                break;
            case RecyclerView.SCROLL_STATE_SETTLING:
                //动画滚动时
                break;
        }
    }

    @Override
    public void scrollToPosition(int position) {
        if (position < 0 || position > getItemCount() - 1) return;
        mOffsetAll = calculateOffsetForPosition(position);
        if (mRecycle == null || mState == null) {//如果RecyclerView还没初始化完，先记录下要滚动的位置
            mSelectPosition = position;
        } else {
            layoutItems(mRecycle, mState, position > mSelectPosition ? SCROLL_RIGHT : SCROLL_LEFT);
            onSelectedCallBack();
        }
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        int finalOffset = calculateOffsetForPosition(position);
        if (mRecycle == null || mState == null) {//如果RecyclerView还没初始化完，先记录下要滚动的位置
            mSelectPosition = position;
        } else {
            startScroll(mOffsetAll, finalOffset);
        }
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public void onAdapterChanged(RecyclerView.Adapter oldAdapter, RecyclerView.Adapter newAdapter) {
        removeAllViews();
        mRecycle = null;
        mState = null;
        mOffsetAll = 0;
        mSelectPosition = 0;
        mLastSelectPosition = 0;
        mHasAttachedItems.clear();
        mAllItemFrames.clear();
    }

    /**
     * 获取整个布局的水平空间大小
     */
    private int getHorizontalSpace() {
        return getWidth() - getPaddingRight() - getPaddingLeft();
    }

    /**
     * 获取整个布局的垂直空间大小
     */
    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

    /**
     * 获取最大偏移量
     */
    private float getMaxOffset() {
        return (getItemCount() - endNum) * getIntervalDistance();
    }

    /**
     * 计算Item缩放系数
     *
     * @param x Item的偏移量
     * @return 缩放系数
     */
    private float computeScaleX(int x) {
        float scale = 1 - (0.2f * Math.abs(x)) / mDecoratedChildWidth;
        if (scale > 1) scale = 1;
        return scale;

    }

    private float computeAlphaX(int x) {
        float alpha = 1 - (0.8f * Math.abs(x)) / mDecoratedChildWidth;
        if (alpha > 1) alpha = 1;
        return alpha;
    }

    /**
     * 计算Item所在的位置偏移
     *
     * @param position 要计算Item位置
     */
    private int calculateOffsetForPosition(int position) {
        return Math.round(getIntervalDistance() * position);
    }

    /**
     * 修正停止滚动后，Item滚动到中间位置
     */
    private void fixOffsetWhenFinishScroll() {
        int scrollN = (int) (mOffsetAll * 1.0f / getIntervalDistance());
        float moreDx = (mOffsetAll % getIntervalDistance());
        if (moreDx > (getIntervalDistance() * 0.5)) {
            scrollN++;
        }
        int finalOffset = (int) (scrollN * getIntervalDistance());
        startScroll(mOffsetAll, finalOffset);
        mSelectPosition = Math.round(finalOffset * 1.0f / getIntervalDistance());
    }

    /**
     * 滚动到指定X轴位置
     *
     * @param from X轴方向起始点的偏移量
     * @param to   X轴方向终点的偏移量
     */
    private void startScroll(int from, int to) {
        if (to > 0) {
            to = to - 30;
        }
        if (mAnimation != null && mAnimation.isRunning()) {
            mAnimation.cancel();
        }
        final int direction = from < to ? SCROLL_RIGHT : SCROLL_LEFT;
        mAnimation = ValueAnimator.ofFloat(from, to);
        mAnimation.setDuration(500);
        mAnimation.setInterpolator(new DecelerateInterpolator());
        mAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffsetAll = Math.round((float) animation.getAnimatedValue());
                layoutItems(mRecycle, mState, direction);
            }
        });
        mAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                onSelectedCallBack();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnimation.start();
    }

    /**
     * 获取Item间隔
     */
    private float getIntervalDistance() {
        return mDecoratedChildWidth * mIntervalRatio;
    }

    /**
     * 计算当前选中位置，并回调
     */
    private void onSelectedCallBack() {
        mSelectPosition = Math.round(mOffsetAll / getIntervalDistance());
        if (mSelectedListener != null && mSelectPosition != mLastSelectPosition) {
            mSelectedListener.onItemSelected(mSelectPosition);
        }
        mLastSelectPosition = mSelectPosition;
    }

    /**
     * 获取第一个可见的Item位置
     * <p>Note:该Item为绘制在可见区域的第一个Item，有可能被第二个Item遮挡
     */
    public int getFirstVisiblePosition() {
        Rect displayFrame = new Rect(mOffsetAll, 0, mOffsetAll + getHorizontalSpace(), getVerticalSpace());
        int cur = getCenterPosition();
        for (int i = cur - 1; i >= 0; i--) {
            Rect rect = getFrame(i);
            if (!Rect.intersects(displayFrame, rect)) {
                return i + 1;
            }
        }
        return 0;
    }

    /**
     * 获取中间位置
     * <p>Note:该方法主要用于{@link RecyclerLeftFlow#getChildDrawingOrder(int, int)}判断中间位置
     * <p>如果需要获取被选中的Item位置，调用{@link #getSelectedPos()}
     */
    public int getCenterPosition() {
        int pos = (int) (mOffsetAll / getIntervalDistance());
        int more = (int) (mOffsetAll % getIntervalDistance());
        if (more > getIntervalDistance() * 0.5f) pos++;
        return pos;
    }


    /**
     * 设置选中监听
     *
     * @param l 监听接口
     */
    public void setOnSelectedListener(OnSelected l) {
        mSelectedListener = l;
    }

    /**
     * 获取被选中Item位置
     */
    public int getSelectedPos() {
        return mSelectPosition;
    }

    /**
     * 选中监听接口
     */
    public interface OnSelected {
        /**
         * 监听选中回调
         *
         * @param position 显示在中间的Item的位置
         */
        void onItemSelected(int position);
    }

    public static class Builder {
        float cstIntervalRatio = -1f;
        int screenWidth;


        public Builder setIntervalRatio(float ratio) {
            cstIntervalRatio = ratio;
            return this;
        }

        public Builder setScreenWidth(int screenWidth) {
            this.screenWidth = screenWidth;
            return this;
        }

        public LeftFlowLayoutManger build() {
            return new LeftFlowLayoutManger(screenWidth);
        }
    }
}