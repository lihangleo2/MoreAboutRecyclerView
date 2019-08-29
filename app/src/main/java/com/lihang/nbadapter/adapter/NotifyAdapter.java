package com.lihang.nbadapter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.lihang.nbadapter.BaseAdapter;
import com.lihang.nbadapter.R;
import com.lihang.nbadapter.bean.Person;
import com.lihang.nbadapter.utils.LogUtils;
import com.lihang.nbadapter.viewholder.AnimationHolder;

/**
 * Created by leo
 * on 2019/8/22.
 */
public class NotifyAdapter extends BaseAdapter<Person> {
    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        return new AnimationHolder(getResId(viewGroup, R.layout.item_notify));
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        AnimationHolder animationHolder = (AnimationHolder) viewHolder;
        Person itemBean = dataList.get(position);
        animationHolder.txt.setText(itemBean.getName() + itemBean.getId());
        LogUtils.i("谁走打印谁", itemBean.getName() + itemBean.getId());
    }

//  需要测试notifyItemChanged(int position,Object payload)的话，放开这段注释即可
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
//        if (payloads.isEmpty()) {
//            //如果是空让其走
//            onBindMyViewHolder(holder,position);
//        } else {
//            AnimationHolder animationHolder = (AnimationHolder) holder;
//            String name = (String) payloads.get(0);
//            int id = (int) payloads.get(1);
//            animationHolder.txt.setText(name + id);
//            Person itemBean = dataList.get(position);
//            itemBean.setName(name);
//            itemBean.setId(id + "");
//        }
//    }
}
