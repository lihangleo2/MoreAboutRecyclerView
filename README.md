## 这是一个总结recyclerView各种用法的demo。及封装各种你用到的功能的小小万能适配器
[博客详细教程地址-稍后加上](https://juejin.im/post/5d5ce44d5188252231108e68)  

#### 先来说说此demo包含的内容：

* 小小万能适配器，解放你的双手，以前写几十行代码，现在只要10来行解决。
* 多布局
* 随意添加头部底部（线性，网格，瀑布）
* 网格布局支持均等分割线（由于较为复杂，小于等于5列都已处理，有数学好的同学帮忙整理个函数）
* 支持带加载动画
* item点击带水波纹特效
* 关于recyclerView各种适配器刷新详解：notifyItemChanged，notifyItemInserted，notifyItemRangeChanged等等6种刷新
* item拖拽调整顺序，及item侧滑
* item侧滑菜单，如：删除或指定（侧滑菜单可以随item左滑出现，也可隐藏在item底部）
* 配合SmartRefreshLayout使用，各种果冻效果，上拉，下拉。以及横向recyclerView果冻，左滑更多等
* recyclerView item可展开或收起，展开时展示更多子集
* recyclerView实现viewPager效果。
* 网格recyclerView span合并实现不一样的效果
* item重叠实现不一样的效果
* 跟着启舰思想，实现一个效果爆炸的recyclerView
* recyclerView的粘性头部
* 结合CoordinatorLayout使用，某布局置顶，及各种效果  



## 友情链接
ui中再遇到阴影时,跟Ui小姐姐说,阴影部分别担心，我自己来
> 阴影布局，不管你是什么控件，放进阴影布局即刻享受你想要的阴影  
地址：[https://github.com/lihangleo2/ShadowLayout](https://github.com/lihangleo2/ShadowLayout)

有多种效果
> 一款多效果智能登录按钮,也可用于点赞动画等
地址：[https://github.com/lihangleo2/SmartLoadingView](https://github.com/lihangleo2/SmartLoadingView)

## 1、简单使用

 - 项目build.gradle添加如下
   ```java
   allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
   ```
 - app build.gradle添加如下
    ```java
   dependencies {
	        implementation 'com.github.lihangleo2:MoreAboutRecyclerView:1.0.0'
	}
   ```
   
### 1.1、实现一个简单的列表
适配器代码（个人建议viewHolder可以写在外面，这样有时候可以直接拿来复用）：
```java
//集成万能适配器，并注明此适配器用到的数据类型
public class SimpleAdapter extends BaseAdapter<String> {
    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        //SimpleHolder建议写在外面，这样就搞定了
        return new SimpleHolder(getResId(viewGroup, R.layout.item_simple));
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    //这里还是和正常的onBindViewHolder一样。因为需要封装开启动画或添加头部，所以用onBindMyViewHolder继承出来处理逻辑
    }

}
```
Activity里的代码(一个简单的列表展示就完成了)：
```java
        adapter = new SimpleAdapter();
        adapter.setDataList(arrayList);
        recyclerView.setAdapter(adapter);
```
### 2.2、实现多布局
多布局的话，只要重写getMyViewType（int position）即可，代码如下：
```java
public class MainAdapter extends BaseAdapter<MainBean> {
    final int TYPE_ONE = 0;
    final int TYPE_TWO = 1;
    
    @Override
    public int getMyViewType(int position) {
        if (position % 2 == 0) {
            return TYPE_ONE;
        } else {
            return TYPE_TWO;
        }
    }


    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case TYPE_ONE:
                return new MainHolder(getResId(viewGroup, R.layout.item_main_one));
            default:
                return new MainHolder(getResId(viewGroup, R.layout.item_main_two));
        }
    }

    @Override
    public void onBindMyViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        
    }

}
```
### 2.3、添加头部
添加头部只需要(线性，网格，瀑布都是)：
```java
 View headLayout = LayoutInflater.from(AddHeadActivity.this).inflate(R.layout.layout_head, null);
 adapter.addHeadView(headLayout);
```






   
