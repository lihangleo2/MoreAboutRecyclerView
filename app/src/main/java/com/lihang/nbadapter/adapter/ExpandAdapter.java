package com.lihang.nbadapter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lihang.nbadapter.BaseAdapter;
import com.lihang.nbadapter.R;
import com.lihang.nbadapter.bean.FatherBean;
import com.lihang.nbadapter.viewholder.ExpandFarterHolder;
import com.lihang.nbadapter.viewholder.ExpandSonHolder;

/**
 * Created by leo
 * on 2019/8/22.
 */
public class ExpandAdapter extends BaseAdapter<Object> {
    final int TYPE_FATHER = 0;
    final int TYPE_SON = 1;


    @Override
    public int getItemViewType(int position) {
        Object object = dataList.get(position);
        if (object instanceof FatherBean) {
            return TYPE_FATHER;
        } else {
            return TYPE_SON;
        }
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case TYPE_FATHER:
                return new ExpandFarterHolder(getResId(viewGroup, R.layout.item_expand_title));
            default:
                return new ExpandSonHolder(getResId(viewGroup, R.layout.item_expand_son));
        }
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ExpandFarterHolder) {
            ExpandFarterHolder expandFarterHolder = (ExpandFarterHolder) viewHolder;
            FatherBean itemBean = (FatherBean) dataList.get(position);
            expandFarterHolder.txt_title.setText(itemBean.getTitle());
            if (itemBean.isSelect()) {
                expandFarterHolder.image_gou.setVisibility(View.VISIBLE);
            } else {
                expandFarterHolder.image_gou.setVisibility(View.GONE);
            }
        } else if (viewHolder instanceof ExpandSonHolder) {
            ExpandSonHolder expandSonHolder = (ExpandSonHolder) viewHolder;
            String itemBean = (String) dataList.get(position);
            expandSonHolder.txt_title.setText(itemBean);
        }
    }

}
