package com.lihang.nbadapter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.lihang.nbadapter.BaseAdapter;
import com.lihang.nbadapter.R;
import com.lihang.nbadapter.adapter.MainAdapter;
import com.lihang.nbadapter.bean.MainBean;
import com.lihang.nbadapter.utils.ToastUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MainAdapter adapter;
    private ArrayList<MainBean> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addData();
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new MainAdapter();
        setOnItemClick();
        adapter.setDataList(dataList);
        recyclerView.setAdapter(adapter);

    }

    private void setOnItemClick() {
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener<MainBean>() {
            @Override
            public void onItemClick(MainBean item, int position) {
                switch (position) {
                    case 0:
                        transfer(SimpleActivity.class);
                        break;
                    case 1:
                        ToastUtils.showToast("本页面就是,看代码了解");
                        break;

                    case 2:
                        transfer(AddHeadActivity.class);
                        break;

                    case 3:
                        transfer(GridActivity.class);
                        break;

                    case 4:
                        transfer(PullActivity.class);
                        break;

                    case 5:
                        transfer(AnimationActivity.class);
                        break;

                    case 6:
                        transfer(ClickWithShowActivity.class);
                        break;
                    case 7:
                        transfer(NotifyActivity.class);
                        break;
                    case 8:
                        transfer(SwipActivity.class);
                        break;
                    case 9:
                        transfer(SwipSlideItemActivity.class);
                        break;
                    case 10:
                        transfer(SmartRefreshActivity.class);
                        break;
                    case 11:
                        transfer(ExpandActivity.class);
                        break;
                    case 12:
                        transfer(UseByPagerActivity.class);
                        break;
                    case 13:
                        transfer(SpanActivity.class);
                        break;

                    case 14:
                        transfer(RecoverActivity.class);
                        break;
                    case 15:
                        transfer(FoldActivity.class);
                        break;
                    case 16:
                        transfer(StickyHeadActivity.class);
                        break;
                    case 17:
                        transfer(CoordActivity.class);
                        break;
                }
            }
        });
    }

    private void addData() {
        dataList.add(new MainBean(getString(R.string.simple_use), getString(R.string.simple_des)));
        dataList.add(new MainBean(getString(R.string.muti_use), getString(R.string.muti_des)));
        dataList.add(new MainBean(getString(R.string.addhead_use), getString(R.string.addhead_des)));
        dataList.add(new MainBean(getString(R.string.grid_use), getString(R.string.grid_des)));
        dataList.add(new MainBean(getString(R.string.pull_use), getString(R.string.pull_des)));
        dataList.add(new MainBean(getString(R.string.animation_use), getString(R.string.animation_des)));
        dataList.add(new MainBean(getString(R.string.shadowclick_use), getString(R.string.shadowclick_des)));
        dataList.add(new MainBean(getString(R.string.notify_use), getString(R.string.notify_des)));
        dataList.add(new MainBean(getString(R.string.swip_use), getString(R.string.swip_des)));
        dataList.add(new MainBean(getString(R.string.swip_slide_use), getString(R.string.swip_slide_des)));
        dataList.add(new MainBean(getString(R.string.smart_use), getString(R.string.smart_des)));
        dataList.add(new MainBean(getString(R.string.expand_use), getString(R.string.expand_des)));
        dataList.add(new MainBean(getString(R.string.pager_use), getString(R.string.pager_des)));
        dataList.add(new MainBean(getString(R.string.span_use), getString(R.string.span_des)));
        dataList.add(new MainBean(getString(R.string.recover_use), getString(R.string.recover_des)));
        dataList.add(new MainBean(getString(R.string.fold_use), getString(R.string.fold_des)));
        dataList.add(new MainBean(getString(R.string.sticky_use), getString(R.string.sticky_des)));
        dataList.add(new MainBean(getString(R.string.coord_use), getString(R.string.coord_des)));
    }

    public void transfer(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
