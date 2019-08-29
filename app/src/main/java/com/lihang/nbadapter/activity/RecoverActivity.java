package com.lihang.nbadapter.activity;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lihang.nbadapter.AnimationType;
import com.lihang.nbadapter.R;
import com.lihang.nbadapter.adapter.RecoverAdapter;
import com.lihang.nbadapter.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by leo
 * on 2019/8/27.
 */
public class RecoverActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private RecoverAdapter adapter;
    private RecyclerView.ItemDecoration itemDecoration;
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    public String getActivityTitle() {
        return getString(R.string.recover_use);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_recover;
    }

    @Override
    protected void processLogic() {
        for (int i = 0; i < 7; i++) {
            arrayList.add("1");
        }
        adapter = new RecoverAdapter();
        adapter.showItemAnim(AnimationType.TRANSLATE_FROM_RIGHT);
        adapter.setDataList(arrayList);
        recyclerView.setAdapter(adapter);

        itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.right = -45;
            }
        };
        recyclerView.addItemDecoration(itemDecoration);
    }
}
