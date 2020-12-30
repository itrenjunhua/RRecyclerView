# Android RecyclerView 控件使用
Android `RecyclerView` 控件使用方法及代码示例，以及 `RecyclerView` 分割线绘制类和 `Adapter` 的封装。

## 主要包含的代码
> 1.使用不同的RecyclerView.LayoutManger实现不同风格的布局(ListView类型、GridView类型、瀑布流类型)  
> 2.给RecyclerView添加分割线  
> 3.给 item 添加点击事件和长按事件  
> 4.使用 GridLayoutManager 指定item占用列数、使用多种 item 类型  
> 5.与 SwipeRefreshLayout 控件结合实现刷新和自动加载更多  
> 6.使用 ItemTouchHelper 实现拖拽和侧滑删除效果  
> 7.将Adapter进行封装

![RecyclerView效果图目录](https://github.com/itrenjunhua/RRecyclerView/raw/master/images/image_menu.jpg)

## RecyclerView 绘制类
> 1.解决了 `GridLayoutManager` 类型时添加分割线后 item 大小不一致的问题  
> 2.支持自定义设置水平分割线高度、颜色(或Drawable)，垂直分割线高度、颜色(或Drawable)；以及水平分割线和垂直分割线交叉点的颜色(或Drawable)  
> 3.支持自定义是否需要绘制第一行之前、第一列之前、最后一行之后、最后一列之后的分割线，并指定其宽度和颜色(或Drawable)  

[Android RecyclerView —— 自定义分割线](https://blog.csdn.net/ITRenj/article/details/92649029)

## RecyclerView Adapter封装
#### 封装说明：
定义一个 `RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder>` 插件类，
自定义一个 `RecyclerViewHolder<C extends BaseRecyclerCell> extends RecyclerView.ViewHolder` 类，
然后为每一种类型的 item 创建一个 cell(继承至 `BaseRecyclerCell`) ，最后重写 `RecyclerAdapter` 类中的抽象方法 `getRecyclerCell(int itemTypeValue)` 返回 `BaseRecyclerCell<T>` 子类实例（也就是当前列表类型的cell，多种条目类型实现时请看下文）。

当一个列表中有多种条目类型时，需要使用 `MultiItemAdapter` 适配器类，添加到该类中的数据，必须实现 `MultiItemEntity` 接口，返回当前数据类型对应的 `itemType` 值（条目类型值，用于区分不同的条目类型），在重写方法 `getRecyclerCell(int itemTypeValue)` 时，可以根据参数 `itemTypeValue` 返回对应的 `BaseRecyclerCell<T>` 子类实例

#### 好处：
> 1. 同一种条目布局的 `RecyclerView` 都可以使用同一个 `RecyclerAdapter` 类，并且Adapter实现非常简单，只需要实现一个方法返回对应的 `BaseRecyclerCell` 对象；  
> 2. 如果一个列表有多重类型的 item 样式，只需要创建一个 cell 类，在 `MultiItemAdapter` 类中 `getRecyclerCell(int itemTypeValue)` 方法返回即可，不需要修改其他中的代码；  
> 3. 当应用中有相同的 item 类型时，可以实现代码的复用(因为每一个 item(或者一种类型的数据) 就是一个独立的 cell，与具体的页面无关)

[Android RecyclerView —— 适配器封装探索](https://blog.csdn.net/ITRenj/article/details/92405204)

## 自定义下拉刷新和加载更多效果
该部分实现仅仅说明实现原理，功能比较单一。在编写App时建议使用完整下拉刷新和上拉加载框架。如  [SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout) 等。

扩展`RecyclerView`控件，实现自定义下拉刷新和加载更多效果，控件名：RefreshRecyclerView  
> 在布局文件中定义

	<com.android.recyclerviewtest.view.RefreshRecyclerView
        android:id="@+id/recyclerview"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
> 在代码中找到控件，然后设置监听

	RefreshRecyclerView recyclerview = (RefreshRecyclerView) findViewById(R.id.recyclerview);
	// 刷新监听
        recyclerview.setOnRefreshListener(new RefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
        // 加载更多监听
        recyclerview.setOnLoadMoreListener(new RefreshRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

            }
        });
> 设置能否刷新或者加载更多

	recyclerview.setCanLoadMore(false); // 设置能否加载更多
    recyclerview.setCanRefresh(false);  // 设置能否刷新
> 设置是否还有更多

	recyclerview.setHasMore(true);

博客说明地址：

[Android RecyclerView —— 基本使用](https://blog.csdn.net/ITRenj/article/details/91556719)

[Android RecyclerView —— 适配器封装探索](https://blog.csdn.net/ITRenj/article/details/92405204)

[Android RecyclerView —— 自定义分割线](https://blog.csdn.net/ITRenj/article/details/92649029)
## 效果图
![RecyclerView效果图目录](https://github.com/itrenjunhua/RRecyclerView/raw/master/images/image_list1.jpg) 
![RecyclerView效果图目录](https://github.com/itrenjunhua/RRecyclerView/raw/master/images/image_list2.jpg) 
![RecyclerView效果图目录](https://github.com/itrenjunhua/RRecyclerView/raw/master/images/image_grid1.jpg) 
![RecyclerView效果图目录](https://github.com/itrenjunhua/RRecyclerView/raw/master/images/image_grid2.jpg) 
![RecyclerView效果图目录](https://github.com/itrenjunhua/RRecyclerView/raw/master/images/image_grid3.jpg) 
![RecyclerView效果图目录](https://github.com/itrenjunhua/RRecyclerView/raw/master/images/image_sta1.jpg) 
![RecyclerView效果图目录](https://github.com/itrenjunhua/RRecyclerView/raw/master/images/image_sta2.jpg) 