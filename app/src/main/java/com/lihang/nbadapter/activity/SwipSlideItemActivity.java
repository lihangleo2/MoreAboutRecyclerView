package com.lihang.nbadapter.activity;

import android.support.v7.widget.RecyclerView;

import com.lihang.nbadapter.R;
import com.lihang.nbadapter.adapter.SwipSlidItemAdapter;
import com.lihang.nbadapter.base.BaseActivity;
import com.lihang.nbadapter.swiphelper.PlusItemSlideCallback;
import com.lihang.nbadapter.swiphelper.SwipItemHelper;
import com.lihang.nbadapter.swiphelper.WItemTouchHelperPlus;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by leo
 * on 2019/8/26.
 */
public class SwipSlideItemActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private SwipSlidItemAdapter adapter;
    private ArrayList<String> arrayList = new ArrayList<>();
    private SwipItemHelper swipItemHelper;

    @Override
    public String getActivityTitle() {
        return getString(R.string.swip_use);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_notify;
    }

    @Override
    protected void processLogic() {
        for (int i = 0; i < 15; i++) {
            if (i % 2 == 0) {
                arrayList.add("侧滑菜单固定在item底部不动");
            } else {
                arrayList.add("跟随item,从右滑出");
            }
        }
        adapter = new SwipSlidItemAdapter();
        adapter.setDataList(arrayList);
        recyclerView.setAdapter(adapter);
        swipItemHelper = new SwipItemHelper(adapter);
        swipItemHelper.attachRecyclerView(recyclerView);
    }


}
