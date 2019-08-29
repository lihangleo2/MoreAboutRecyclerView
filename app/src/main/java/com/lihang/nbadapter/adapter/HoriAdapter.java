package com.lihang.nbadapter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.lihang.nbadapter.BaseAdapter;
import com.lihang.nbadapter.R;
import com.lihang.nbadapter.viewholder.SimpleHolder;

/**
 * Created by leo
 * on 2019/8/27.
 */
public class HoriAdapter extends BaseAdapter<String> {
    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        return new SimpleHolder(getResId(viewGroup, R.layout.item_hori));
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

    }
}
