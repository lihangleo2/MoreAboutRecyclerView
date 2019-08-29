package com.lihang.nbadapter.activity;

import android.view.View;
import android.widget.AdapterView;

import com.lihang.nbadapter.R;
import com.lihang.nbadapter.adapter.StickyAdapter;
import com.lihang.nbadapter.base.BaseActivity;
import com.lihang.nbadapter.bean.StickyBean;
import com.lihang.nbadapter.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by leo
 * on 2019/8/28.
 * 优点：此粘性头部虽不是recyclerView实现，但是对于自定义的headView很好实现，以及不用考虑是否有图片问题
 * 缺点1：缺点也同时存在，就是如果有gridView网格。那么要在item里面嵌套gridView或者网格recyclerView。
 * 缺点2：如果是多type的的粘性头部，此方案暂未解决
 * recyclerView解决方案，较详细教程：https://github.com/qdxxxx/StickyHeaderDecoration
 */
public class StickyHeadActivity extends BaseActivity {
    @BindView(R.id.stickListView)
    StickyListHeadersListView stickListView;
    private StickyAdapter stickyAdapter;
    private ArrayList<StickyBean> dataList = new ArrayList<>();

    @Override
    public String getActivityTitle() {
        return getString(R.string.sticky_use);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_sticky;
    }

    @Override
    protected void processLogic() {

        stickyAdapter = new StickyAdapter(this);
        stickListView.setAdapter(stickyAdapter);
        initData();

        //设置头部的点击事件
        stickListView.setOnHeaderClickListener(new StickyListHeadersListView.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {
                ToastUtils.showToast("headerId:" + headerId);
            }
        });

        //设置内容的点击事件
        stickListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ToastUtils.showToast("position = " + i);
            }
        });

        //设置头部改变的监听
        stickListView.setOnStickyHeaderChangedListener(new StickyListHeadersListView.OnStickyHeaderChangedListener() {
            @Override
            public void onStickyHeaderChanged(StickyListHeadersListView l, View header, int itemPosition, long headerId) {
                ToastUtils.showToast("头部切换  itemPosition = " + itemPosition);
            }
        });
        stickyAdapter.setDataList(dataList);
        stickListView.setDividerHeight(0);


    }

    private void initData() {
        for (int i = 0; i < 3; i++) {
            StickyBean stickyBean = new StickyBean(0, "明星分类 ", "明星 " + i + " 号");
            dataList.add(stickyBean);
        }

        for (int i = 0; i < 5; i++) {
            StickyBean stickyBean = new StickyBean(1, "游戏分类 ", "游戏 " + i + " 号");
            dataList.add(stickyBean);
        }

        for (int i = 0; i < 6; i++) {
            StickyBean stickyBean = new StickyBean(2, "主播分类 ", "美女主播 " + i + " 号");
            dataList.add(stickyBean);
        }

        for (int i = 0; i < 2; i++) {
            StickyBean stickyBean = new StickyBean(3, "美女分类 ", "比基尼 " + i + " 号");
            dataList.add(stickyBean);
        }

        for (int i = 0; i < 7; i++) {
            StickyBean stickyBean = new StickyBean(4, "二货分类 ", "二哈 " + i + " 号");
            dataList.add(stickyBean);
        }

    }
}
