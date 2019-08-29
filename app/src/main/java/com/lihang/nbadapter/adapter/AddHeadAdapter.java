package com.lihang.nbadapter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.lihang.nbadapter.BaseAdapter;
import com.lihang.nbadapter.R;
import com.lihang.nbadapter.viewholder.AddHeadHolder;

/**
 * Created by leo
 * on 2019/8/22.
 */
public class AddHeadAdapter extends BaseAdapter<String> {
    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        return new AddHeadHolder(getResId(viewGroup, R.layout.item_simple));
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final String itemBean = dataList.get(position);
        AddHeadHolder addHeadHolder = (AddHeadHolder) viewHolder;
        addHeadHolder.txt.setText(itemBean + " ==== " + position);
    }

}
