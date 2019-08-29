package com.lihang.nbadapter.activity;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;

import com.lihang.nbadapter.BaseAdapter;
import com.lihang.nbadapter.R;
import com.lihang.nbadapter.adapter.NotifyAdapter;
import com.lihang.nbadapter.base.BaseActivity;
import com.lihang.nbadapter.bean.Person;
import com.lihang.nbadapter.swiphelper.DragSwipHelper;
import com.lihang.nbadapter.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by leo
 * on 2019/8/26.
 */
public class SwipActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private NotifyAdapter adapter;
    private ArrayList<Person> arrayList = new ArrayList<>();
    private DragSwipHelper dragSwipHelper;
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
            arrayList.add(new Person((i + 1) + "", "编号 ==> "));
        }
        adapter = new NotifyAdapter();
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener<Person>() {
            @Override
            public void onItemClick(Person item, int position) {
//                ToastUtils.showToast(item.getName() + " " + item.getId());
                ToastUtils.showToast("当前position ==> " + position);
            }
        });
        adapter.setDataList(arrayList);
        recyclerView.setAdapter(adapter);
        dragSwipHelper = new DragSwipHelper();
        dragSwipHelper.attachSwipAndDragRecyclerView(recyclerView, adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_drag_swip, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.drag_swip:
                dragSwipHelper.attachSwipAndDragRecyclerView(recyclerView, adapter);
                break;
            case R.id.only_drag:
                dragSwipHelper.attachDragRecyclerView(recyclerView, adapter);
                break;
            case R.id.only_swip:
                dragSwipHelper.attachSwipRecyclerView(recyclerView, adapter);
                break;
            case R.id.only_left_swip:
                dragSwipHelper.attachRecyclerView(recyclerView, adapter, 0, ItemTouchHelper.LEFT);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
