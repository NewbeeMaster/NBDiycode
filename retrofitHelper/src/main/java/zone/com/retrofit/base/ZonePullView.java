package zone.com.retrofit.base;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.zone.adapter3.base.IAdapter;

import retrofit2.Call;
import retrofit2.Response;
import zone.com.retrofit.utils.HandlerUiUtil;
import zone.com.zrefreshlayout.ZRefreshLayout;

/**
 * [2017] by Zone
 */

public abstract class ZonePullView<E> extends BasePullView<ZRefreshLayout, RecyclerView, IAdapter, E, Object> {

    public ZonePullView(ZRefreshLayout pullView, IAdapter adapter) {
        super(pullView.getContext(),pullView, adapter.getRecyclerView(), adapter, adapter.getData());
        pullViewSetListener();
    }

    private void pullViewSetListener() {
        adapter.addOnScrollListener(new OnScrollRcvListenerExZRefresh(pullView));
        pullView.setPullListener(new ZRefreshLayout.PullListener() {
            @Override
            public void refresh(ZRefreshLayout zRefreshLayout) {
                setCanLoadMore(true);
                offset = getFirstNumber();
                bindRefreshEngine(request(offset, limit));
            }

            @Override
            public void refreshAnimationComplete(ZRefreshLayout zRefreshLayout) {
            }
        });
        pullView.setLoadMoreListener(true, new ZRefreshLayout.LoadMoreListener() {
            @Override
            public void loadMore(ZRefreshLayout zRefreshLayout) {
                HandlerUiUtil.postDelay(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("heihei", "加载页面：" + offset);
                        bindRefreshEngine(request(offset, limit));
                    }
                }, 500);
            }

            @Override
            public void loadMoreAnimationComplete(ZRefreshLayout zRefreshLayout) {

            }
        });
    }

    @Override
    public void onRefreshComplete() {
        if (pullView.isRefresh())
            pullView.refreshComplete();
    }

    @Override
    public void onLoadMoreComplete() {
        if (pullView.isLoadMore()) {
            if (offset == -1) {
                onLoadMoreEnd();
                setCanLoadMore(false);
            } else
                adapter.loadMoreComplete();
        }

    }

    @Override
    public void onLoadMoreFail() {
        if (pullView.isLoadMore())
            adapter.loadMoreFail();
    }

    @Override
    public void onLoadMoreEnd() {
        if (pullView.isLoadMore())
            adapter.end();
    }

    @Override
    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }


    @Override
    public void doLast(boolean successful, Call<E> call, Response<E> response, Throwable t) {
        onRefreshComplete();
        onLoadMoreComplete();
    }

    @Override
    public void setCanLoadMore(boolean canLoadMore) {
        pullView.setCanLoadMore(canLoadMore);
    }
}


