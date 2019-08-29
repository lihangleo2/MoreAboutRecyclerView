package com.lihang.nbadapter.activity;

import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.lihang.nbadapter.AnimationType;
import com.lihang.nbadapter.R;
import com.lihang.nbadapter.adapter.AnimationAdapter;
import com.lihang.nbadapter.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by leo
 * on 2019/8/26.
 */
public class LinearAnimActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btn_change)
    Button btn_change;
    private AnimationAdapter adapter;
    private boolean alawayShowAnim = true;
    private AnimationType animationType = AnimationType.TRANSLATE_FROM_RIGHT;
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    public String getActivityTitle() {
        return "线性动画展示";
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_linear_anim;
    }

    @Override
    protected void processLogic() {
        for (int i = 0; i < 25; i++) {
            arrayList.add("带有动画item" + i);
        }
        adapter = new AnimationAdapter();
        adapter.showItemAnim(animationType, alawayShowAnim);
        recyclerView.setAdapter(adapter);
        adapter.setDataList(arrayList);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_anim, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.anim_form_right:
                animationType = AnimationType.TRANSLATE_FROM_RIGHT;
                adapter.showItemAnim(animationType, alawayShowAnim);
                adapter.notifyAnimItemPosition();
                adapter.notifyDataSetChanged();
                break;
            case R.id.anim_form_left:
                animationType = AnimationType.TRANSLATE_FROM_LEFT;
                adapter.showItemAnim(animationType, alawayShowAnim);
                adapter.notifyAnimItemPosition();
                adapter.notifyDataSetChanged();
                break;

            case R.id.anim_scale:
                animationType = AnimationType.SCALE;
                adapter.showItemAnim(animationType, alawayShowAnim);
                adapter.notifyAnimItemPosition();
                adapter.notifyDataSetChanged();
                break;

            case R.id.anim_form_bottom:
                animationType = AnimationType.TRANSLATE_FROM_BOTTOM;
                adapter.showItemAnim(animationType, alawayShowAnim);
                adapter.notifyAnimItemPosition();
                adapter.notifyDataSetChanged();
                break;

            case R.id.anim_alpha:
                animationType = AnimationType.ALPHA;
                adapter.showItemAnim(animationType, alawayShowAnim);
                adapter.notifyAnimItemPosition();
                adapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.btn_change)
    public void change() {
        if (getStringByUI(btn_change).equals("动画只限新增")) {
            //就是item已经启动过动画，上下滑动，重复启动动画
            btn_change.setText("动画一直显示");
            alawayShowAnim = true;
            adapter.showItemAnim(animationType, alawayShowAnim);
            adapter.notifyDataSetChanged();
        } else {
            //就是item启动过动画后，第二次不再启动，除非是新增项或刷新
            btn_change.setText("动画只限新增");
            alawayShowAnim = false;
            adapter.showItemAnim(animationType, alawayShowAnim);
            //假如你项目里刷新，需要调用这句，重置当前已显示position，不然比如到50，你不重置0，在50前，都不会显示
            adapter.notifyAnimItemPosition();
            adapter.notifyDataSetChanged();
        }

    }
}
