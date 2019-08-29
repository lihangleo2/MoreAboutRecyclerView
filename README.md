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
### 1.2、实现多布局
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
#### 效果如下：
首页是多布局，Normal是简单使用
![image](https://github.com/lihangleo2/RandomRecycleView/blob/master/gifshow/1.normal.gif)  

## 2、添加头部
添加头部只需要(线性，网格，瀑布都是)：
```java
 View headLayout = LayoutInflater.from(AddHeadActivity.this).inflate(R.layout.layout_head, null);
 adapter.addHeadView(headLayout);
```
添加头部和底部其实就是利用多布局，只不过已经做好了封装。  
网格添加头部的特殊处理在：BaseAdapter 里的onAttachedToRecyclerView方法里。  
瀑布流添加头部的特殊处理在：BaseAdapter 里的onViewAttachedToWindow里。

### 2.1、网格recyclerView的均等分割线
如果需要博主处理好的均等分割线加上：
```java
 //设置网格布局间隔，不用担心会不会不等分，一切已经计算好(由于是在复杂之计算到了5列)
 adapter.setGridDivide(recyclerView, (int) getResources().getDimension(R.dimen.dp_10));
```
加上这句，你的分割线一定是10dp，且不会有item变形。这块讲解先看张图：
![image](https://github.com/lihangleo2/RandomRecycleView/blob/master/gifshow/3.grid_itemd_math.png)  

假设我们设置的分割线是20的话：  
x1 = x2 = x3 = x4 = x5 = x6 = 20;  
而且：  
x1 + w + x2 = x3 + w + x4 = x5 + w + x6;将屏幕等分  
有人说分割线可以随意设置，确实没错。如果有图片，你没有遵循上面这个规则，就会明显看到图片被pading压小。  
现在要达到的效果是这样的：x1 = x2+x3 = x4+x5 = x6;  
假设还是上面分割线20的话，由上图可知：分割线总长是120，有4条分割线，那么每条就是30；  
在遵循上面的等式，那么就是x2把10 借给 x1; x5 把10 借给 x6; x3和x4保持不动，是不是达到效果了？然后在分割线里去设置。  
因为随着列数越多，这个函数越不规律。博主只是用代码判断处理到了5列。有高数好的，帮我解决下

#### 效果如下：
|线性|网格|瀑布流|
|:---:|:---:|:---:|
|![](https://github.com/lihangleo2/RandomRecycleView/blob/master/gifshow/2.addhead.gif)|![](https://github.com/lihangleo2/RandomRecycleView/blob/master/gifshow/3.addheadbygrid.gif)|![](https://github.com/lihangleo2/RandomRecycleView/blob/master/gifshow/4.addheadbypull.gif)  

## 3、添加动画
这里处理处理动画有多种方式，个人觉得在OnBind里处理动画，会更佳简洁，明了。
代码如下：
```java
//这是博主封装的几种常见的动画
showItemAnim(AnimationType animationType）
//你还可以自定义动画，只要传入resId即可
showItemAnim(int animResId)
```
需要注意的是，因为在onBindView里处理动画，所以必须在绘制前调用adapter.showItemAnim(AnimationType.TRANSLATE_FROM_RIGHT);

### 效果如下：
|线性|网格|瀑布流|
|:---:|:---:|:---:|
|![](https://github.com/lihangleo2/RandomRecycleView/blob/master/gifshow/5.animation_linear.gif)|![](https://github.com/lihangleo2/RandomRecycleView/blob/master/gifshow/6.animation_grid.gif)|![](https://github.com/lihangleo2/RandomRecycleView/blob/master/gifshow/7.animation_pull.gif)
|启动多张动画|
|![](https://github.com/lihangleo2/RandomRecycleView/blob/master/gifshow/8.animation_even.gif)|

## 4、点击item带圆圈扩散效果
这个其实就是利用ripple来做，直接设置在布局背景里 android:background="@drawable/linerlayout_water_selector"  
这里不封装进去，是因为有可能你布局点击区域是整条，也有可能跟itemView有间距，所以还是自己设置最合理。

### 效果如下：
![image](https://github.com/lihangleo2/RandomRecycleView/blob/master/gifshow/9.ripper.gif)  

   
