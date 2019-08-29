package com.lihang.nbadapter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lihang.nbadapter.BaseAdapter;
import com.lihang.nbadapter.R;
import com.lihang.nbadapter.base.SwipBaseHolder;
import com.lihang.nbadapter.swiphelper.SlidTranslate;
import com.lihang.nbadapter.utils.ToastUtils;
import com.lihang.nbadapter.viewholder.SlideItemHoder;

/**
 * Created by leo
 * on 2019/8/27.
 */
public class SwipSlidItemAdapter extends BaseAdapter<String> implements SlidTranslate {
    final int TYPE_BOTTOM = 0;
    final int TYPE_WITH = 1;

    @Override
    public int getItemViewType(int position) {
        if (dataList.get(position).equals("侧滑菜单固定在item底部不动")) {
            return TYPE_BOTTOM;
        } else {
            return TYPE_WITH;
        }
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case TYPE_BOTTOM:
                return new SlideItemHoder(getResId(viewGroup, R.layout.item_slide_bottom));
            default:
                return new SlideItemHoder(getResId(viewGroup, R.layout.item_slide_with));
        }
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        SlideItemHoder slideItemHoder = (SlideItemHoder) viewHolder;
        String itemBean = dataList.get(position);
        slideItemHoder.txt_text.setText(itemBean + "  position=" + position);

        slideItemHoder.slide_itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("item点击  " + position);
            }
        });

        slideItemHoder.zhiding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("置顶  " + position);
            }
        });

        slideItemHoder.shanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("删除  " + position);
            }
        });
    }


    @Override
    public void translationX(SwipBaseHolder swipBaseHolder, float dX) {
        SlideItemHoder slideItemHoder = (SlideItemHoder) swipBaseHolder;
        slideItemHoder.slide_itemView.setTranslationX(dX);
    }
}
