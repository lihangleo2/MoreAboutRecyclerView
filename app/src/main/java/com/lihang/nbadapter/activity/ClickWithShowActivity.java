package com.lihang.nbadapter.activity;

import android.support.v7.widget.RecyclerView;

import com.lihang.nbadapter.BaseAdapter;
import com.lihang.nbadapter.R;
import com.lihang.nbadapter.adapter.ShadowClickAdapter;
import com.lihang.nbadapter.base.BaseActivity;
import com.lihang.nbadapter.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by leo
 * on 2019/8/26.
 */
public class ClickWithShowActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ShadowClickAdapter adapter;
    ArrayList<String> arrayList = new ArrayList<>();
    @Override
    public String getActivityTitle() {
        return getString(R.string.shadowclick_use);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_simple;
    }

    @Override
    protected void processLogic() {
        for (int i = 0; i < 15; i++) {
            arrayList.add("1");
        }
        adapter = new ShadowClickAdapter();
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(String item, int position) {
                ToastUtils.showToast(position+"");
            }
        });
        adapter.setDataList(arrayList);
        recyclerView.setAdapter(adapter);
    }
}
