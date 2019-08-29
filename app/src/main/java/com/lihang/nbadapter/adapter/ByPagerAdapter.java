package com.lihang.nbadapter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.lihang.nbadapter.BaseAdapter;
import com.lihang.nbadapter.R;
import com.lihang.nbadapter.utils.UIUtil;
import com.lihang.nbadapter.viewholder.PagerUseHolder;

/**
 * Created by leo
 * on 2019/8/27.
 */
public class ByPagerAdapter extends BaseAdapter<String> {
    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        return new PagerUseHolder(getResId(viewGroup, R.layout.item_useby_pager));
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        PagerUseHolder pagerUseHolder = (PagerUseHolder) viewHolder;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) pagerUseHolder.image.getLayoutParams();
        layoutParams.width = UIUtil.getWidth(viewHolder.itemView.getContext()) * 3 / 4;
    }
}
