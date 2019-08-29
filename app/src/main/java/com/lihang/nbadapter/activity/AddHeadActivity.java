package com.lihang.nbadapter.activity;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.lihang.nbadapter.BaseAdapter;
import com.lihang.nbadapter.R;
import com.lihang.nbadapter.adapter.AddHeadAdapter;
import com.lihang.nbadapter.base.BaseActivity;
import com.lihang.nbadapter.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by leo
 * on 2019/8/22.
 */
public class AddHeadActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private AddHeadAdapter adapter;
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    public String getActivityTitle() {
        return getString(R.string.addhead_use);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_addhead;
    }

    @Override
    protected void processLogic() {
        arrayList.add("娜扎");
        arrayList.add("小龙女");
        arrayList.add("我哦哦哦哦哦");

        adapter = new AddHeadAdapter();
        setItemOnclick();
        adapter.setDataList(arrayList);
        recyclerView.setAdapter(adapter);
        //默认先添加一个头部一个底部
        addHead();
        addFoot();
    }

    private void setItemOnclick() {
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(String item, int position) {
                ToastUtils.showToast(item);
            }
        });
    }


    @OnClick(R.id.btn_add)
    public void addHead() {
        View headLayout = LayoutInflater.from(AddHeadActivity.this).inflate(R.layout.layout_head, null);
        TextView head_txt = headLayout.findViewById(R.id.head_txt);
        int red = new Random().nextInt(255);
        int green = new Random().nextInt(255);
        int blue = new Random().nextInt(255);

        head_txt.setBackgroundColor(Color.argb(255, red, green, blue));
        adapter.addHeadView(headLayout);
    }

    @OnClick(R.id.btn_remove)
    public void removeHead() {
        adapter.removeHeadView(0);
    }


    @OnClick(R.id.btn_addfoot)
    public void addFoot() {
        View headLayout = LayoutInflater.from(AddHeadActivity.this).inflate(R.layout.layout_foot, null);
        TextView head_txt = headLayout.findViewById(R.id.head_txt);
        int red = new Random().nextInt(255);
        int green = new Random().nextInt(255);
        int blue = new Random().nextInt(255);

        head_txt.setBackgroundColor(Color.argb(255, red, green, blue));
        adapter.addFootView(headLayout);
    }

    @OnClick(R.id.btn_removefoot)
    public void removeFoot() {
        adapter.removeFootView(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_head, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.recyclerView_hori:
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                break;
            case R.id.recyclerView_vet:
                LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
                linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
