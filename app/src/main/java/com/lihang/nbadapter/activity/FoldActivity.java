package com.lihang.nbadapter.activity;

import android.view.Menu;
import android.view.MenuItem;

import com.lihang.nbadapter.R;
import com.lihang.nbadapter.adapter.FoldAdapter;
import com.lihang.nbadapter.base.BaseActivity;
import com.lihang.nbadapter.customview.RecyclerFoldView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by leo
 * on 2019/8/27.
 */
public class FoldActivity extends BaseActivity {
    @BindView(R.id.relativeFlow)
    RecyclerFoldView relativeFlow;
    private FoldAdapter adapter;
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    public String getActivityTitle() {
        return getString(R.string.fold_use);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_fold;
    }

    @Override
    protected void processLogic() {
        for (int i = 0; i < 10; i++) {
            arrayList.add("1");
        }
        adapter = new FoldAdapter();
        adapter.setDataList(arrayList);
        relativeFlow.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fold, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.scroll_everWhere:
                relativeFlow.stayEnd(false);
                adapter.notifyDataSetChanged();
                relativeFlow.smoothScrollToPosition(0);
                break;
            case R.id.scroll_stayend:
                relativeFlow.stayEnd(true);
                adapter.notifyDataSetChanged();
                relativeFlow.smoothScrollToPosition(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
