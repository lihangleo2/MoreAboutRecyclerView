package com.lihang.nbadapter.activity;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lihang.nbadapter.R;
import com.lihang.nbadapter.adapter.ByPagerAdapter;
import com.lihang.nbadapter.adapter.SimpleAdapter;
import com.lihang.nbadapter.base.BaseActivity;
import com.lihang.nbadapter.utils.UIUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by leo
 * on 2019/8/27.
 */
public class UseByPagerActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ByPagerAdapter adapter;
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    public String getActivityTitle() {
        return getString(R.string.pager_use);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_useby_pager;
    }

    @Override
    protected void processLogic() {
        for (int i = 0; i < 15; i++) {
            arrayList.add("1");
        }
        adapter = new ByPagerAdapter();
        adapter.setDataList(arrayList);
        int padding = (UIUtil.getWidth(this) / 4 - UIUtil.dip2px(this, 40)) / 2;
        int truePading = padding + UIUtil.dip2px(this, 20);
        recyclerView.setPadding(truePading, 0, truePading, 0);


        recyclerView.setAdapter(adapter);
        //关键是加上这句
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }
}
