package com.lihang.nbadapter;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import java.util.ArrayList;

/**
 * NB 万能recyclerView适配器
 * Created by leo
 * on 2019/8/22.
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter {
    //正常type 0;
    final int TYPE_NORMAL = 0;
    //头部type默认小于-100 > -10000  我相信头部不能能回家到9900个，如果超过这个数把底部继续拉下限
    final int TYPE_HEAD = -100;
    //底部type默认小于-10000
    final int TYPE_FOOT = -10000;

    //头部储存器
    ArrayList<View> headViews = new ArrayList<>();
    //底部储存器
    ArrayList<View> footViews = new ArrayList<>();
    //数据源
    public ArrayList<T> dataList;


    /*
     * 下列参数和设置动画相关
     * */
    //记录时间差，看看是否大于50，防止动画一起执行；
    private int currentMillons = 0;
    //记录已经启动过动画的position的位置
    private int currentPosition = -1;
    //如果一直存在小于50的时候，用于叠加delay时间
    private int delyTimePosi = 1;
    private AnimationType animationType;
    private int animResId;
    private boolean alwaysShow;


    public void setDataList(ArrayList<T> dataList) {
        this.dataList = dataList;
    }

    public ArrayList<T> getDataList() {
        return dataList;
    }

    public int getMyViewType(int position) {
        return 0;
    }


    @Override
    public int getItemViewType(int position) {
        if (headViews.size() > 0) {
            if (footViews.size() > 0) {
                //有头部，也有底部
                if (position <= headViews.size() - 1) {
                    return TYPE_HEAD - position;
                } else if (headViews.size() - 1 < position && position < headViews.size() + dataList.size()) {
                    return getMyViewType(getRealPosition(position));
                } else {
                    return TYPE_FOOT - position + dataList.size() + headViews.size();
                }

            } else {
                //有头部，没有底部
                if (position <= headViews.size() - 1) {
                    return TYPE_HEAD - position;
                } else {
                    return getMyViewType(getRealPosition(position));
                }
            }

        } else {
            if (footViews.size() > 0) {
                //没有头部，有底部的时候
                if (position < dataList.size()) {
                    return getMyViewType(position);
                } else {
                    return TYPE_FOOT - position + dataList.size();
                }
            } else {
                //没有头部，也没有底部的时候
                return getMyViewType(position);
            }
        }

    }

    @Override
    public int getItemCount() {
        if (headViews.size() == 0 && footViews.size() == 0) {
            return dataList == null ? 0 : dataList.size();
        } else if (headViews.size() == 0 && footViews.size() > 0) {
            return dataList == null ? footViews.size() : dataList.size() + footViews.size();
        } else if (headViews.size() > 0 && footViews.size() == 0) {
            return dataList == null ? headViews.size() : dataList.size() + headViews.size();
        } else {
            return dataList == null ? headViews.size() + footViews.size() : dataList.size() + headViews.size() + footViews.size();
        }
    }

    public View getResId(ViewGroup viewGroup, int resId) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(resId, viewGroup, false);
    }

    public abstract RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType);

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType <= TYPE_FOOT) {
            return new HeadHolder(footViews.get(Math.abs(viewType + Math.abs(TYPE_FOOT))));
        } else if (TYPE_FOOT < viewType && viewType <= TYPE_HEAD) {
            return new HeadHolder(headViews.get(Math.abs(viewType + Math.abs(TYPE_HEAD))));
        } else {
            return getViewHolder(viewGroup, viewType);
        }

    }


    public abstract void onBindMyViewHolder(RecyclerView.ViewHolder viewHolder, int position);


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {
        //这里已经包括了TYPE_FOOT
        if (getItemViewType(position) <= TYPE_HEAD) return;

        if (onItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(dataList.get(getRealPosition(position)), getRealPosition(position));
                }
            });
        }

        if (onItemLongClickListener != null) {
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemLongClickListener.onItemLongClick(dataList.get(getRealPosition(position)), getRealPosition(position));
                    return true;
                }
            });
        }

        onBindMyViewHolder(viewHolder, getRealPosition(position));
        addItemAnimation(viewHolder, getRealPosition(position));
    }

    private void addItemAnimation(RecyclerView.ViewHolder viewHolder, int position) {
        if (animResId != 0 || animationType != null) {
            if (!alwaysShow) {
                if (position > currentPosition) {
                    currentPosition = position;
                    int nowMillis = (int) System.currentTimeMillis();
                    Animation animator = null;
                    if (animResId != 0) {
                        animator = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), animResId);
                    } else {
                        if (animationType != null) {
                            animator = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), animationType.getResId());
                        }
                    }
                    //这里处理的是当前页显示的item执行动画的间隔太快，看不出效果
                    if (nowMillis - currentMillons >= 10) {
                        delyTimePosi = 1;
                        currentMillons = nowMillis;
                        viewHolder.itemView.startAnimation(animator);
                    } else {
                        delyTimePosi++;
                        currentMillons = nowMillis;
                        animator.setStartOffset(50 * delyTimePosi);
                        viewHolder.itemView.startAnimation(animator);
                    }
                }
            } else {
                int nowMillis = (int) System.currentTimeMillis();
                Animation animator = null;
                if (animResId != 0) {
                    animator = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), animResId);
                } else {
                    if (animationType != null) {
                        animator = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), animationType.getResId());
                    }
                }
                if (nowMillis - currentMillons >= 10) {
                    delyTimePosi = 1;
                    currentMillons = nowMillis;
                    viewHolder.itemView.startAnimation(animator);
                } else {
                    delyTimePosi++;
                    currentMillons = nowMillis;
                    animator.setStartOffset(50 * delyTimePosi);
                    viewHolder.itemView.startAnimation(animator);
                }
            }

        }
    }


    public void notifyAnimItemPosition() {
        currentPosition = -1;
    }

    private int getRealPosition(int position) {
        return position - headViews.size();
    }

    public OnItemClickListener<T> onItemClickListener;

    public OnItemClickListener<T> getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(T item, int position);
    }


    public OnItemLongClickListener<T> onItemLongClickListener;

    public OnItemLongClickListener<T> getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemLongClickListener<T> {
        void onItemLongClick(T item, int position);
    }


    public void addHeadView(View view) {
        headViews.add(view);
        notifyDataSetChanged();
    }

    public void removeHeadView(View view) {
        headViews.remove(view);
        notifyDataSetChanged();
    }

    public void removeHeadView(int index) {
        if (index < headViews.size()) {
            headViews.remove(index);
            notifyDataSetChanged();
        }
    }

    public void addFootView(View view) {
        footViews.add(view);
        notifyDataSetChanged();
    }

    public void removeFootView(View view) {
        footViews.remove(view);
        notifyDataSetChanged();
    }

    public void removeFootView(int index) {
        if (index < footViews.size()) {
            footViews.remove(index);
            notifyDataSetChanged();
        }
    }

    public boolean isHeadView(int position) {
        if (headViews.size() > 0) {
            if (position < headViews.size()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean isFootView(int position) {
        if (footViews.size() > 0) {
            int dataSize;
            if (dataList == null) {
                dataSize = 0;
            } else {
                dataSize = dataList.size();
            }
            int totalSize = headViews.size() + dataSize + footViews.size();
            if (totalSize - (position + 1) < footViews.size()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    //瀑布流添加头部满屏操作
    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && holder instanceof HeadHolder) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }


    //这里是对GridLayoutManager添加头部满屏操作
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {

                    if (isHeadView(position) || isFootView(position)) {
                        return gridLayoutManager.getSpanCount();
                    } else {
                        return 1;
                    }
                }
            });
        } else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            //这里是防止瀑布流自带动画，引起子view乱跳。第一行出现空白格
            recyclerView.setAnimation(null);
            final StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
            staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        }
    }


    //这里是设置网格布局间隔
    public void setGridDivide(final RecyclerView recyclerView, final int divideDimen) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {

            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    int padTop = recyclerView.getPaddingTop();
                    int padBottom = recyclerView.getPaddingBottom();
                    int padLeft = recyclerView.getPaddingLeft();
                    int padRight = recyclerView.getPaddingRight();
                    //为上下均等
                    if (footViews.size() <= 0) {
                        recyclerView.setClipToPadding(false);
                        if (padBottom == 0) {
                            recyclerView.setPadding(padLeft, padTop, padRight, divideDimen);
                        }
                    } else {
                        if (padBottom == divideDimen) {
                            recyclerView.setPadding(padLeft, padTop, padRight, 0);
                        }
                    }

                    //获取列数
                    int spanCount = ((GridLayoutManager) recyclerView.getLayoutManager()).getSpanCount();
                    int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
                    if (isFootView(position)) {
                        int dataSize;
                        if (dataList == null) {
                            dataSize = 0;
                        } else {
                            dataSize = dataList.size();
                        }
                        int totalSize = headViews.size() + dataSize + footViews.size();

                        if (totalSize - position == footViews.size()) {
                            outRect.set(0, divideDimen, 0, 0);
                        }

                    } else {
                        if (!isHeadView(position)) {
                            //由于这里函数太复杂，越多列越是不同。
                            //希望高数学的好的朋友出出注意
                            //这里只给大家判断到 5列的情况了
                            if (spanCount == 2) {
                                if (getRealPosition(position) % 2 == 0) {
                                    outRect.set(divideDimen, divideDimen, divideDimen / 2, 0);
                                } else {
                                    outRect.set(divideDimen / 2, divideDimen, divideDimen, 0);
                                }
                            } else if (spanCount == 3) {
                                if (getRealPosition(position) % 3 == 0) {
                                    outRect.set(divideDimen, divideDimen, divideDimen / 3, 0);
                                } else if (getRealPosition(position) % 3 == 1) {
                                    outRect.set(divideDimen * 2 / 3, divideDimen, divideDimen * 2 / 3, 0);
                                } else {
                                    outRect.set(divideDimen / 3, divideDimen, divideDimen, 0);
                                }
                            } else if (spanCount == 4) {
                                if (getRealPosition(position) % 4 == 0) {
                                    outRect.set(divideDimen, divideDimen, divideDimen / 4, 0);
                                } else if (getRealPosition(position) % 4 == 1) {
                                    outRect.set(divideDimen * 3 / 4, divideDimen, divideDimen / 2, 0);
                                } else if (getRealPosition(position) % 4 == 2) {
                                    outRect.set(divideDimen / 2, divideDimen, divideDimen * 3 / 4, 0);
                                } else {
                                    outRect.set(divideDimen / 4, divideDimen, divideDimen, 0);
                                }
                            } else if (spanCount == 5) {
                                if (getRealPosition(position) % 5 == 0) {
                                    outRect.set(divideDimen, divideDimen, divideDimen / 5, 0);
                                } else if (getRealPosition(position) % 5 == 1) {
                                    outRect.set(divideDimen * 4 / 5, divideDimen, divideDimen * 2 / 5, 0);
                                } else if (getRealPosition(position) % 5 == 2) {
                                    outRect.set(divideDimen * 3 / 5, divideDimen, divideDimen * 3 / 5, 0);
                                } else if (getRealPosition(position) % 5 == 3) {
                                    outRect.set(divideDimen * 2 / 5, divideDimen, divideDimen * 4 / 5, 0);
                                } else {
                                    outRect.set(divideDimen / 5, divideDimen, divideDimen, 0);
                                }
                            } else {
                                outRect.set(divideDimen, divideDimen, divideDimen, 0);
                            }

                        }


                    }

                }
            });

        } else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {

            recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                    int padTop = recyclerView.getPaddingTop();
                    int padBottom = recyclerView.getPaddingBottom();
                    int padLeft = recyclerView.getPaddingLeft();
                    int padRight = recyclerView.getPaddingRight();
                    //为上下均等
                    if (footViews.size() <= 0) {
                        recyclerView.setClipToPadding(false);
                        if (padBottom == 0) {
                            recyclerView.setPadding(padLeft, padTop, padRight, divideDimen);
                        }
                    } else {
                        if (padBottom == divideDimen) {
                            recyclerView.setPadding(padLeft, padTop, padRight, 0);
                        }
                    }
                    int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
                    if (isFootView(position)) {
                        int dataSize;
                        if (dataList == null) {
                            dataSize = 0;
                        } else {
                            dataSize = dataList.size();
                        }
                        int totalSize = headViews.size() + dataSize + footViews.size();

                        if (totalSize - position == footViews.size()) {
                            outRect.set(0, divideDimen, 0, 0);
                        }

                    } else {
                        if (!isHeadView(position)) {
                            outRect.set(divideDimen / 2, divideDimen, divideDimen / 2, 0);
                        }
                    }


                }
            });

        }

    }


    public void showItemAnim(AnimationType animationType) {
        this.animationType = animationType;
    }

    public void showItemAnim(AnimationType animationType, boolean alwaysShow) {
        this.animationType = animationType;
        this.alwaysShow = alwaysShow;
    }

    public void showItemAnim(int animResId) {
        this.animResId = animResId;
    }

    public void showItemAnim(int animResId, boolean alwaysShow) {
        this.animResId = animResId;
        this.alwaysShow = alwaysShow;
    }

}
