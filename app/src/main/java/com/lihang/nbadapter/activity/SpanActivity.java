package com.lihang.nbadapter.activity;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lihang.nbadapter.R;
import com.lihang.nbadapter.adapter.SimpleAdapter;
import com.lihang.nbadapter.adapter.SpanAdapter;
import com.lihang.nbadapter.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by leo
 * on 2019/8/27.
 */
public class SpanActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private SpanAdapter adapter;
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    public String getActivityTitle() {
        return getString(R.string.span_use);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_span;
    }

    @Override
    protected void processLogic() {
        for (int i = 0; i < 15; i++) {
            arrayList.add("1");
        }
        adapter = new SpanAdapter();
        adapter.setDataList(arrayList);
        recyclerView.setAdapter(adapter);
        final int divide = (int) getResources().getDimension(R.dimen.dp_5);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.set(divide, divide, divide, divide);
            }
        });

        GridLayoutManager layoutManagerInfo = (GridLayoutManager) recyclerView.getLayoutManager();
        layoutManagerInfo.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position % 7 == 0) {
                    return 1;
                } else if (position % 7 == 1) {
                    return 2;
                } else if (position % 7 == 2) {
                    return 3;
                } else if (position % 7 == 3) {
                    return 4;
                } else if (position % 7 == 4) {
                    return 2;
                } else if (position % 7 == 5) {
                    return 5;
                } else if (position % 7 == 6) {
                    return 1;
                } else {
                    return 6;
                }


            }
        });
    }
}
