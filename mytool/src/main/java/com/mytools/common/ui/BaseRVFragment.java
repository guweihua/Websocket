package com.quansu.common.ui;


import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.quansu.common.inter.BaseAdapterInter;
import com.quansu.common.mvp.BaseView;
import com.quansu.common.mvp.RLRVPresenter;
import com.quansu.common.mvp.RLRVView;
import com.quansu.ui.adapter.OnItemClickListener;
import com.quansu.utils.EmptyViewUtils;
import com.quansu.widget.footer.LoadMoreFooterView;
import com.quansu.widget.irecyclerview.IRecyclerView;
import com.quansu.widget.irecyclerview.OnLoadMoreListener;
import com.quansu.widget.irecyclerview.OnRefreshListener;
import com.quansu.widget.temptyview.TEmptyView;
import com.umeng.analytics.MobclickAgent;
import com.ysnows.quansu.BuildConfig;
import com.ysnows.quansu.R;

import java.util.ArrayList;


/**
 * 带有RecyclerView的Fragment
 */
public abstract class BaseRVFragment<P extends RLRVPresenter> extends BaseFragment<P> implements OnRefreshListener, OnLoadMoreListener, RLRVView, OnItemClickListener {
    @Nullable
    public IRecyclerView iRecyclerView;
    public LoadMoreFooterView loadMoreFooterView;
    public BaseAdapterInter adapter;

    public SwipeRefreshLayout refresh_layout;

    @Override
    protected void initThings(View view, Bundle savedInstanceState) {

        iRecyclerView = getIrecyclerView(view);

        refresh_layout = getRefreshLayout(view);
        if (refresh_layout != null) {
            refresh_layout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
            refresh_layout.setOnRefreshListener(() -> presenter.requestDataRefresh());
        }

        addHeader();
        addFooter();
        loadMoreFooterView = (LoadMoreFooterView) iRecyclerView.getLoadMoreFooterView();

        adapter = getAdapter();
        iRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        SlideInBottomAnimationAdapter slideInBottomAnimationAdapter = new SlideInBottomAnimationAdapter(adapter.getAdapter());
        iRecyclerView.setIAdapter(adapter.getAdapter());

        iRecyclerView.setOnRefreshListener(this);
        iRecyclerView.setOnLoadMoreListener(this);
        adapter.setmOnItemClickListener(this);


        MobclickAgent.setDebugMode(BuildConfig.DEBUG);
    }


    /**
     *
     */
    public void addFooter() {

    }

    protected abstract IRecyclerView getIrecyclerView(View view);

    protected abstract SwipeRefreshLayout getRefreshLayout(View view);

    public void addItemAnimator() {
//        iRecyclerView.setItemAnimator(new SlideInUpAnimator());
    }


    /**
     * 添加头部
     */
    protected void addHeader() {


    }


    boolean isRefresh = false;
    boolean loadMore = false;


    @Override
    public void onRefresh() {
        loadMore = true;
        if (!isRefresh) {
            presenter.requestDataRefresh();
            if (loadMoreFooterView != null) {
                loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
            }
            loadMore = false;
        }
    }

    @Override
    public void onLoadMore() {
        isRefresh = true;
        if (loadMore == false) {
            if (loadMoreFooterView != null) {
                if (loadMoreFooterView.canLoadMore() && adapter.getItemCount() > 0) {
                    loadMoreFooterView.setStatus(LoadMoreFooterView.Status.LOADING);
                    presenter.loadMore();
                }

                isRefresh = false;
            }
        }
    }

    @Override
    public void bindData(Object o, boolean isHasMore) {
        ArrayList datas = (ArrayList) o;
        if (presenter.page == 1) {
            adapter.setData(datas);
        } else {
            adapter.addData(datas);
        }
        hasMore(isHasMore);
    }

    @Override
    public void refreshing(boolean refreshing) {

        if (iRecyclerView != null) {
            iRecyclerView.post(() -> iRecyclerView.setRefreshing(refreshing));
        }

        if (refresh_layout != null) {
            refresh_layout.post(() -> refresh_layout.setRefreshing(refreshing));
        }
    }


    @Override
    public void loading(boolean loading) {
        if (loadMoreFooterView != null) {
            loadMoreFooterView.setStatus(loading ? LoadMoreFooterView.Status.LOADING : LoadMoreFooterView.Status.GONE);
        }
    }

    @Override
    public void hasMore(boolean isHasMore) {
//        if (loadMoreFooterView != null) {
//            loadMoreFooterView.setStatus(isHasMore ? LoadMoreFooterView.Status.GONE : LoadMoreFooterView.Status.THE_END);
//        }



        if (loadMoreFooterView != null) {
            if(adapter.getData()!=null&&adapter.getData().size()>0) {
                loadMoreFooterView.setStatus(isHasMore ? LoadMoreFooterView.Status.GONE : LoadMoreFooterView.Status.THE_END);
            }else{
                loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
                onError(BaseView.ERROR_FIST_DATA);
            }
        }



    }

    @Override
    public void onError(String buttonText) {
        adapter.clear();
        bindEmptyView(buttonText);
    }


    public void bindEmptyView(String buttonText) {
        final TEmptyView emptyView = EmptyViewUtils.genSimpleEmptyView(iRecyclerView);

        emptyView.setShowButton(!TextUtils.isEmpty(buttonText));
        emptyView.setAction(v -> {
            iRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            refreshing(true);
            presenter.requestDataRefresh();
        });

        if (adapter != null) {
            RecyclerView.AdapterDataObserver observer = new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    if (adapter.getData().size() > 0) {
                        iRecyclerView.setVisibility(View.VISIBLE);
                        emptyView.setVisibility(View.GONE);
                    } else {
                        iRecyclerView.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                    }
                }
            };
            adapter.getAdapter().registerAdapterDataObserver(observer);
            observer.onChanged();
        } else {
            throw new RuntimeException("This RecyclerView has no adapter, you must call setAdapter first!");
        }
    }

    @Override
    public P getRP() {
        return presenter;
    }
}
