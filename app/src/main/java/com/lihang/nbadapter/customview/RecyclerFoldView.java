package com.lihang.nbadapter.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.lihang.nbadapter.MyApplication;
import com.lihang.nbadapter.utils.UIUtil;


/**
 * 继承RecyclerView重写{@link #getChildDrawingOrder(int, int)}对Item的绘制顺序进行控制
 * 参考https://blog.csdn.net/harvic880925/article/details/86606873
 */

public class RecyclerFoldView extends RecyclerView {
    /**
     * 按下的X轴坐标
     */
    private float mDownX;

    /**
     * 布局器构建者
     */
    private LeftFlowLayoutManger.Builder mManagerBuilder;

    public RecyclerFoldView(Context context) {
        super(context);
        init();
    }

    public RecyclerFoldView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RecyclerFoldView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        createManageBuilder();
        setLayoutManager(mManagerBuilder.build());
        setChildrenDrawingOrderEnabled(true); //开启重新排序
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    /**
     * 创建布局构建器
     */
    private void createManageBuilder() {
        if (mManagerBuilder == null) {
            mManagerBuilder = new LeftFlowLayoutManger.Builder().setScreenWidth(UIUtil.getWidth(MyApplication.getContext()));
        }
    }

    public void stayEnd(boolean isStay) {
        getCoverFlowLayout().stayEnd(isStay);
    }

    /**
     * 设置Item的间隔比例
     *
     * @param intervalRatio Item间隔比例。
     *                      即：item的宽 x intervalRatio
     */
    public void setIntervalRatio(float intervalRatio) {
        createManageBuilder();
        mManagerBuilder.setIntervalRatio(intervalRatio);
        setLayoutManager(mManagerBuilder.build());
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (!(layout instanceof LeftFlowLayoutManger)) {
            throw new IllegalArgumentException("The layout manager must be CoverFlowLayoutManger");
        }
        super.setLayoutManager(layout);
    }


    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        return Math.abs(childCount - 1 - i);
    }

    /**
     * 获取LayoutManger，并强制转换为CoverFlowLayoutManger
     */
    public LeftFlowLayoutManger getCoverFlowLayout() {
        return ((LeftFlowLayoutManger) getLayoutManager());
    }

    /**
     * 获取被选中的Item位置
     */
    public int getSelectedPos() {
        return getCoverFlowLayout().getSelectedPos();
    }

    /**
     * 设置选中监听
     *
     * @param l 监听接口
     */
    public void setOnItemSelectedListener(LeftFlowLayoutManger.OnSelected l) {
        getCoverFlowLayout().setOnSelectedListener(l);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                getParent().requestDisallowInterceptTouchEvent(true); //设置父类不拦截滑动事件
                break;
            case MotionEvent.ACTION_MOVE:
                if ((ev.getX() > mDownX && getCoverFlowLayout().getCenterPosition() == 0) ||
                        (ev.getX() < mDownX && getCoverFlowLayout().getCenterPosition() ==
                                getCoverFlowLayout().getItemCount() - 1)) {
                    //如果是滑动到了最前和最后，开放父类滑动事件拦截
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    //滑动到中间，设置父类不拦截滑动事件
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
