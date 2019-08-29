package com.lihang.nbadapter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.lihang.nbadapter.BaseAdapter;
import com.lihang.nbadapter.R;
import com.lihang.nbadapter.viewholder.AddHeadHolder;

/**
 * Created by leo
 * on 2019/8/27.
 */
public class SpanAdapter extends BaseAdapter<String> {
    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        return new AddHeadHolder(getResId(viewGroup, R.layout.item_span));
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        AddHeadHolder addHeadHolder = (AddHeadHolder) viewHolder;
        addHeadHolder.txt.setText("我是item" + position);
    }
}
