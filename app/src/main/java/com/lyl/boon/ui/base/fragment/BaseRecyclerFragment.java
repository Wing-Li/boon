package com.lyl.boon.ui.base.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.View;
import android.view.ViewGroup;

import com.lyl.boon.R;
import com.lyl.boon.ui.base.BaseFragment;
import com.lyl.boon.ui.base.apdter.MyBaseAdapter;
import com.lyl.boon.view.recycler.LinearLayoutManagerWrapper;
import com.lyl.boon.view.recycler.OnRecycleViewScrollListener;
import com.wang.avi.AVLoadingIndicatorView;

import org.byteam.superadapter.OnItemClickListener;

import java.util.List;

import butterknife.Bind;
import rx.Observer;

/**
 * Wing_Li
 * 2016/4/8.
 */
public abstract class BaseRecyclerFragment<T> extends BaseFragment {

    public static final int TYPE_LIST = 0;
    public static final int TYPE_GRID = 1;
    public static final int TYPE_STAG_V = 2;
    public static final int TYPE_STAG_H = 3;

    /**
     * 网格列表的列数
     */
    public static final int GRID_COUNT = 2;

    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.mSwipeRefreshLayout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    @Nullable
    @Bind(R.id.loadingView)
    AVLoadingIndicatorView mLoadingView;

    protected List<T> mData;
    protected MyBaseAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected int mListType;

    protected int page = 1;

    protected boolean isLoading = false;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_list;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initListType();
        initView();
        loadMore();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopLoad();
        stopLoadingView();
    }

    /**
     * 设置列表的显示方式
     * 设置 滑动、刷新、点击 事件
     */
    private void initView() {
        //必须在子页面new 出列表的Adapter
        if (mAdapter == null) {
            showToast(R.string.load_error);
            return;
        }

        switch (mListType) {
            case TYPE_LIST://列表
                mLayoutManager = new LinearLayoutManagerWrapper(getHolder(), LinearLayoutManager.VERTICAL, false);
                break;
            case TYPE_GRID://网格
                mLayoutManager = new GridLayoutManager(getHolder(), GRID_COUNT);
                break;
            case TYPE_STAG_V://竖向瀑布流
                mLayoutManager = new StaggeredGridLayoutManager(GRID_COUNT, StaggeredGridLayoutManager.VERTICAL);
                ((StaggeredGridLayoutManager) mLayoutManager).setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
                break;
            case TYPE_STAG_H://横向瀑布流
                mLayoutManager = new StaggeredGridLayoutManager(GRID_COUNT, StaggeredGridLayoutManager.HORIZONTAL);
                ((StaggeredGridLayoutManager) mLayoutManager).setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
                break;

            default:
                break;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new OnRecycleViewScrollListener() {
            @Override
            public void onLoadMore() {
                if (!isLoading) {
                    loadMore();
                }
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoading) {
                    page = 1;
                    int count = mAdapter.getCount();
                    mAdapter.clear();
                    mAdapter.notifyItemRangeRemoved(0, count);

                    loadMore();
                }
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int viewType, int position) {
                ItemClickListener(itemView, viewType, position);
            }
        });
    }

    /**
     * 加载更多
     */
    protected void loadMore() {
        isLoading = true;
        setRefreshing(true);
        unsubscribe();
        setSubscribe(observer);
    }

    /**
     * 网络请求成功
     */
    private Observer<List<T>> observer = new Observer<List<T>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            getHolder().showToast(R.string.msg_net_erro);
            stopLoadingView();
            stopLoad();
        }

        @Override
        public void onNext(List<T> dataEntities) {
            stopLoadingView();

            if (dataEntities != null) {
                mAdapter.addAll(dataEntities);
            }

            stopLoad();
            page++;
        }
    };

    /**
     * 停止加载
     */
    private void stopLoad() {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        isLoading = false;
    }

    /**
     * 停止加载动画
     */
    private void stopLoadingView() {
        if (mLoadingView != null && mLoadingView.isShown()) {
            mLoadingView.setVisibility(View.GONE);
            ((ViewGroup) mLoadingView.getParent()).removeView(mLoadingView);
        }
    }

    /**
     * 显示刷新的进度圈
     */
    protected void setRefreshing(boolean b) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(b);
        }
    }

    /**
     * 设置订阅的 发布事件，observer在父类已经写好,如果需要做特殊处理，则重写一个。
     */
    protected abstract void setSubscribe(Observer observer);

    /**
     * 设置列表的类型
     * 给 mListType 赋值以下类型
     * <p/>
     * BaseRecyclerFragment.TYPE_LIST 列表；</>
     * BaseRecyclerFragment.TYPE_GRID 网格；</>
     * BaseRecyclerFragment.TYPE_STAG_V 竖向瀑布流；
     */
    protected abstract void initListType();

    /**
     * 初始化 mData 和 mAdaper
     * <p/>
     * mData 是 List<T> <p/>
     * mAdaper 必须继承 MyBaseAdapter
     */
    protected abstract void initData();

    /**
     * 点击事件
     *
     * @param itemView 当前的 Item
     * @param viewType 当有多种 Item 时，当前的类型
     * @param position 第几个
     */
    protected abstract void ItemClickListener(View itemView, int viewType, int position);
}
