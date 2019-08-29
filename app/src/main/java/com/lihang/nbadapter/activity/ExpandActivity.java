package com.lihang.nbadapter.activity;

import android.support.v7.widget.RecyclerView;

import com.lihang.nbadapter.BaseAdapter;
import com.lihang.nbadapter.R;
import com.lihang.nbadapter.adapter.ExpandAdapter;
import com.lihang.nbadapter.base.BaseActivity;
import com.lihang.nbadapter.bean.FatherBean;
import com.lihang.nbadapter.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by leo
 * on 2019/8/27.
 */
public class ExpandActivity extends BaseActivity implements BaseAdapter.OnItemClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ExpandAdapter adapter;
    ArrayList<Object> arrayList = new ArrayList<>();

    @Override
    public String getActivityTitle() {
        return getString(R.string.expand_use);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_simple;
    }

    @Override
    protected void processLogic() {
        //这里没有营养，就是模拟数据00
        addData();
        adapter = new ExpandAdapter();
        adapter.setOnItemClickListener(this);
        adapter.setDataList(arrayList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(Object item, int position) {
        if (item instanceof FatherBean) {
            FatherBean fatherBean = (FatherBean) item;
            ToastUtils.showToast(fatherBean.getTitle());
            if (fatherBean.isSelect()) {
                fatherBean.setSelect(false);
                //这里因为我是常用类型，不能用这个，只能通过循环了
                //在项目中，如果是类的形式，没毛病
//                arrayList.removeAll(fatherBean.getSons());
                for (int i = 0; i < fatherBean.getSons().size(); i++) {
                    arrayList.remove(position + 1);
                }
                adapter.notifyItemChanged(position);
                adapter.notifyItemRangeRemoved(position + 1, fatherBean.getSons().size());
                adapter.notifyItemRangeChanged(position + 1, arrayList.size());
            } else {
                fatherBean.setSelect(true);
                //说明是选中，把子集加进去
                arrayList.addAll(position + 1, fatherBean.getSons());
                adapter.notifyItemChanged(position);
                adapter.notifyItemRangeInserted(position + 1, fatherBean.getSons().size());
                adapter.notifyItemRangeChanged(position + 1, arrayList.size());
            }
        } else if (item instanceof String) {
            String sonBean = (String) item;
            ToastUtils.showToast(sonBean);
        }
    }


    private void addData() {
        FatherBean fatherBean = new FatherBean();
        fatherBean.setTitle("明星分类");
        ArrayList<String> sonList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            sonList.add("彭于晏 " + i + " 号");
        }
        fatherBean.setSons(sonList);
        arrayList.add(fatherBean);


        FatherBean fatherBean2 = new FatherBean();
        fatherBean2.setTitle("游戏分类");
        ArrayList<String> sonList2 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            sonList2.add("王者荣耀 " + i + " 号");
        }
        fatherBean2.setSons(sonList2);
        arrayList.add(fatherBean2);


        FatherBean fatherBean3 = new FatherBean();
        fatherBean3.setTitle("IT分类");
        ArrayList<String> sonList3 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            sonList3.add("Android " + i + " 号");
        }
        fatherBean3.setSons(sonList3);
        arrayList.add(fatherBean3);


        for (int i = 0; i < 12; i++) {
            FatherBean fatherBean4 = new FatherBean();
            fatherBean4.setTitle(i + "  号默认分类");
            ArrayList<String> sonList4 = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                sonList4.add(j + "  号子集");
            }
            fatherBean4.setSons(sonList4);
            arrayList.add(fatherBean4);
        }
    }


}
