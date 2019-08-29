package com.lihang.nbadapter.activity;

import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.lihang.nbadapter.BaseAdapter;
import com.lihang.nbadapter.R;
import com.lihang.nbadapter.adapter.NotifyAdapter;
import com.lihang.nbadapter.base.BaseActivity;
import com.lihang.nbadapter.bean.Person;
import com.lihang.nbadapter.utils.LogUtils;
import com.lihang.nbadapter.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;

/**
 * Created by leo
 * on 2019/8/26.
 */
public class NotifyActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private NotifyAdapter adapter;
    private ArrayList<Person> arrayList = new ArrayList<>();

    @Override
    public String getActivityTitle() {
        return getString(R.string.notify_use);
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
                LogUtils.i("我试试看吧", item.getName() + " " + item.getId());
                ToastUtils.showToast(position + "");
            }
        });
        adapter.setDataList(arrayList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notify, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            /**
             * 1、notifyDataSetChanged 这个不说了，就是整体刷新
             * */
            case R.id.notifyItemChanged:
                /**
                 * 2、notifyItemChanged  局部刷新，只刷新某一个item/2参的甚至可以刷新某个item里的某个view，更加局部
                 * */
                /*
                 * 方式 1：notifyItemChanged（int position）
                 * 说明：就是刷新你要刷新的那个position。其他的item不刷新
                 * 注意点1: 这个方法需要你主动在外面把arrayList里的数据源进行修改
                 * 注意点2: 这个方法运行的是public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position)；
                 * 假如你的item里有图片加载之类的，但是你只是修改一段文字，这个还是会导致你要刷新的那个条目整体刷新，所以这里就个2参的方法，看方式2。
                 * */

                //例：假设我们改变第一个item的数据,看下适配器里打印的数据
                Person itemBean = arrayList.get(0);
                itemBean.setName("编号 ==> 更新数据");
                adapter.notifyItemChanged(0);

                /*
                 * 方式 2：notifyItemChanged(int position,Object payload)
                 * 说明：第二个参数是，你要刷新的数据，可以调用多次，传任何类型
                 * 注意点1：使用这个方法在适配器里要重写  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position,List<Object> payloads);
                 * 重写了这个方法(记得去掉super),那么将不会走onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position)方法。
                 * 所以要判断payloads是否为空，为空主动调用2参的onBindViewHolder;
                 * 注意点2：如果你调用了几次notifyItemChanged(int position,Object payload)，那么在接收方法里List<Object> payloads的size就是多长
                 * 而且类型和你传过来是一样的。比如你传一个名字，我得到名字后，就只取操作名字修改，图片加载就不用操作了。这里相当于更局部了
                 * 注意点3：这里就需要在3参的onBindViewHolder里去修改数据源。不修改数据源的话，上下滑动复用会回到之前的数据
                 * */
                //例：我们修改第一个的item的名字和id,如下
//                adapter.notifyItemChanged(0, "修改名字了");
//                adapter.notifyItemChanged(0, -1);

                break;
            case R.id.notifyItemInserted:
                /**
                 * 3、notifyItemInserted  在position位置添加一条数据，伴随动画效果
                 * */
                /*
                 * 注意点1：调用了notifyItemInserted(int position)之前要在你数据源相同位置加上你要加的item
                 * 注意点2：一切正常且调用了notifyItemInserted(int position)，假如你点击你插入的位置，还有你插入位置的下一个item,
                 * 返回的position都一样。这算google的bug了吧。
                 * 注意点3：要解决这个问题一般要配合notifyItemRangeChanged(int positionStart, int itemCount)使用了。
                 * */

                //例：我们再第2项插入一条数据。
                Person person = new Person("id", "技术界的小学生 ==>");
                arrayList.add(2, person);
                adapter.notifyItemInserted(2);
                //解决注意点2的bug，要调用这个句(这里要从3开始，2本身就是他自己，不需要再刷)
                adapter.notifyItemRangeChanged(3, adapter.getItemCount());
                /**
                 * 4、notifyItemRangeChanged(int positionStart, int itemCount) 也是局部刷新，就是从类别页的第 positionStart 项开始，刷新itemCount数量的item
                 * */
                break;

            case R.id.notifyItemRemoved:
                /**
                 * 5、notifyItemRemoved()  同notifyItemInserted()一样，只不过这是移除
                 * */
                arrayList.remove(2);
                adapter.notifyItemRemoved(2);
                adapter.notifyItemRangeChanged(2, adapter.getItemCount());
                break;

            case R.id.notifyItemRangeInserted:
                /**
                 * 6、notifyItemRangeInserted(int positionStart, int itemCount) 这里和notifyItemInserted()差不多，不同的是这里插入的是很多个.
                 * 可以理解为，从positionStart项开始插入itemCount个数的item
                 * */
                ArrayList<Person> addList = new ArrayList<>();
                Person person1 = new Person("id1", "技术界的小学生1 ==>");
                Person person2 = new Person("id2", "技术界的小学生2 ==>");
                addList.add(person1);
                addList.add(person2);
                arrayList.addAll(2, addList);
                adapter.notifyItemRangeInserted(2, 2);
                adapter.notifyItemRangeChanged(4, adapter.getItemCount());
                break;

            case R.id.notifyItemRangeRemoved:
                /**
                 * 7、notifyItemRangeRemoved(int positionStart, int itemCount)，这里同notifyItemRangeInserted、只不过这里是移除多个
                 * */
                arrayList.remove(2);
                arrayList.remove(2);
                adapter.notifyItemRangeRemoved(2, 2);
                adapter.notifyItemRangeChanged(2, adapter.getItemCount());
                break;

            case R.id.notifyItemMoved:
                /**
                 * 8、notifyItemMoved(int fromPosition, int toPosition)  这个就是2个位置互换
                 * */
                //交换arrayList的第二项和第三项
                Collections.swap(arrayList, 2, 4);
                adapter.notifyItemMoved(2, 4);
                //这些方法都有之前说的注意点2的bug，只能条用rangeChange去解决
                adapter.notifyItemRangeChanged(2, 3);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
