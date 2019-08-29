package com.lihang.nbadapter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.lihang.nbadapter.R;
import com.lihang.nbadapter.bean.StickyBean;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by leo
 * on 2019/8/28.
 */
public class StickyAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private Context context;
    private ArrayList<StickyBean> dataList;

    public StickyAdapter(Context context) {
        this.context = context;
    }

    public void setDataList(ArrayList<StickyBean> sourceList) {
        this.dataList = sourceList;
        notifyDataSetChanged();
    }


    //设置个数的总个数
    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }


    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }


    //获得相应数据集合中特定位置的数据项
    @Override
    public long getItemId(int position) {
        return position;
    }


    //获得头部相应数据集合中特定位置的数据项
    @Override
    public long getHeaderId(int position) {
        return dataList.get(position).getId();
    }

    //绑定内容的数据
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        BodyHolder bodyHolder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_sticky_body, viewGroup, false);
            bodyHolder = new BodyHolder(view);
            view.setTag(bodyHolder);
        } else {
            bodyHolder = (BodyHolder) view.getTag();
        }
        //设置数据
        bodyHolder.bodyTv.setText(dataList.get(position).getSonTitle());
        return view;
    }

    //绑定头部的数据
    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {

        HeadHolder headHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_sticky_head, parent, false);
            headHolder = new HeadHolder(convertView);
            convertView.setTag(headHolder);
        } else {
            headHolder = (HeadHolder) convertView.getTag();
        }
        //设置数据
        headHolder.headTv.setText(dataList.get(position).getHeadTitle());

        return convertView;
    }


    //头部的内部类
    class HeadHolder {
        private TextView headTv;

        public HeadHolder(View itemHeadView) {
            headTv = (TextView) itemHeadView.findViewById(R.id.txHead);
        }
    }

    //内容的内部类
    class BodyHolder {
        private TextView bodyTv;

        public BodyHolder(View itemBodyView) {

            bodyTv = (TextView) itemBodyView.findViewById(R.id.txContent);
        }
    }


}
