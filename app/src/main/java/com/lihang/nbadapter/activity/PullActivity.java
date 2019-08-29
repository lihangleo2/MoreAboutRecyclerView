package com.lihang.nbadapter.activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lihang.nbadapter.R;
import com.lihang.nbadapter.adapter.GridAdapter;
import com.lihang.nbadapter.adapter.PullAdapter;
import com.lihang.nbadapter.adapter.SimpleAdapter;
import com.lihang.nbadapter.base.BaseActivity;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by leo
 * on 2019/8/22.
 */
public class PullActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private PullAdapter adapter;
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    public String getActivityTitle() {
        return getString(R.string.pull_use);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_pull;
    }

    @Override
    protected void processLogic() {
        for (int i = 0; i < 15; i++) {
            arrayList.add("1");
        }
        adapter = new PullAdapter();
        adapter.setGridDivide(recyclerView, (int) getResources().getDimension(R.dimen.dp_10));
        adapter.setDataList(arrayList);
        recyclerView.setAdapter(adapter);
        addHead();
        addFoot();




    }

    @OnClick(R.id.btn_add)
    public void addHead() {
        View headLayout = LayoutInflater.from(PullActivity.this).inflate(R.layout.layout_head, null);
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
        View headLayout = LayoutInflater.from(PullActivity.this).inflate(R.layout.layout_foot, null);
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
}
