package com.lihang.nbadapter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.lihang.nbadapter.BaseAdapter;
import com.lihang.nbadapter.R;
import com.lihang.nbadapter.viewholder.GridHolder;

/**
 * Created by leo
 * on 2019/8/23.
 */
public class GridAdapter extends BaseAdapter<String> {
    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        return new GridHolder(getResId(viewGroup, R.layout.item_grid));
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        GridHolder gridHolder = (GridHolder) viewHolder;
        switch (position % 6) {
            case 0:
                gridHolder.image.setImageResource(R.mipmap.wangze1);
                break;
            case 1:
                gridHolder.image.setImageResource(R.mipmap.wangze2);
                break;
            case 2:
                gridHolder.image.setImageResource(R.mipmap.wangze3);
                break;
            case 3:
                gridHolder.image.setImageResource(R.mipmap.wangze4);
                break;
            case 4:
                gridHolder.image.setImageResource(R.mipmap.wangze5);
                break;
            case 5:
                gridHolder.image.setImageResource(R.mipmap.wangze6);
                break;
        }
    }
}
