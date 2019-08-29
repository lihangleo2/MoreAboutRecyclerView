package com.lihang.nbadapter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.lihang.nbadapter.BaseAdapter;
import com.lihang.nbadapter.R;
import com.lihang.nbadapter.viewholder.SimpleHolder;

/**
 * Created by leo
 * on 2019/8/22.
 */
public class ShadowClickAdapter extends BaseAdapter<String> {
    //主要是配置ripple水波纹效果使用。
    //这里不好封装在BaseAdapter,放base里只能放ItemView上。比如有些item你是圆角的，这里改不了。除非把ripple改成代码动态加入，但是麻烦
    //主要看布局里的background
    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        return new SimpleHolder(getResId(viewGroup, R.layout.item_shadow_click));
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    }

}
