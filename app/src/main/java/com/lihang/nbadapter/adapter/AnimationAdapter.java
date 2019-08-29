package com.lihang.nbadapter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.lihang.nbadapter.BaseAdapter;
import com.lihang.nbadapter.R;
import com.lihang.nbadapter.viewholder.AnimationHolder;

/**
 * Created by leo
 * on 2019/8/22.
 */
public class AnimationAdapter extends BaseAdapter<String> {
    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        return new AnimationHolder(getResId(viewGroup, R.layout.item_animation));
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        AnimationHolder animationHolder = (AnimationHolder) viewHolder;
        String itemBean = dataList.get(position);
        animationHolder.txt.setText(itemBean);
    }

}
