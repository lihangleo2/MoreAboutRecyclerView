package com.lihang.nbadapter.activity;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.lihang.nbadapter.R;
import com.lihang.nbadapter.adapter.SimpleAdapter;
import com.lihang.nbadapter.base.BaseActivity;
import com.lihang.nbadapter.utils.LogUtils;
import com.lihang.nbadapter.utils.UIUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by leo
 * on 2019/8/29.
 */
public class CoordActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private SimpleAdapter adapter;
    ArrayList<String> arrayList = new ArrayList<>();

    //其中这个appBarLayout也有个滑动监听
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.txt_move)
    TextView txt_move;

    @Override
    public String getActivityTitle() {
        return getString(R.string.coord_use);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_coord;
    }

    @Override
    protected void processLogic() {
        setListener();
        for (int i = 0; i < 15; i++) {
            arrayList.add("1");
        }
        adapter = new SimpleAdapter();
        adapter.setDataList(arrayList);
        recyclerView.setAdapter(adapter);
    }

    private void setListener() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //已经滑动区域
                int offset = Math.abs(verticalOffset);
                //可滑动的总区域.
                int total = appBarLayout.getTotalScrollRange();

                int screenWith = UIUtil.getWidth(CoordActivity.this);

                txt_move.setTranslationX(offset * screenWith / total);
                txt_move.setTranslationY(-offset / 2);
            }
        });

    }
}
