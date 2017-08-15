package zone.com.retrofit.base;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import ezy.ui.layout.LoadingLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zone.com.retrofit.callwrapper.DialogCall;

/**
 * A listview that package a t
 * BasePullView(context,pullView,listView,adapter,data,ParseDataListener).bindEngine();
 *
 * @param <P>
 * @param <L>
 * @param <A>
 * @param <D>
 */
public abstract class BasePullView<P, L, A, E, D> {

    private final Context context;
    public P pullView;
    public A adapter;
    public List<D> data;
    public L listView;

    private int firstNumber = 5;

    protected int offset = firstNumber;
    protected int limit = 5;


    public BasePullView(Context context, P pullView, L listView, A adapter, List<D> data) {
        this.context = context;
        this.pullView = pullView;
        this.listView = listView;
        this.adapter = adapter;
        this.data = data;
    }


    public void firstLoading(int offset, LoadingLayout mLoadingLayout) {
        this.offset = offset;
        Call<E> callBack = request(offset, limit);
        DialogCall<E> dialogCall;
        if (callBack instanceof DialogCall)
            dialogCall = new DialogCall(callBack);
        else
            dialogCall = (DialogCall<E>) callBack;
        bindRefreshEngine(dialogCall.firstLoading(mLoadingLayout));
    }


    public void bindRefreshEngine(Call<E> call) {
        Callback<E> callback = new Callback<E>() {
            @Override
            public void onResponse(Call<E> call, Response<E> response) {
                boolean successful = false;
                if (response.isSuccessful()) {
                    //第一页的时候 清除数据
                    if (offset == getFirstNumber())
                        clearData();

                    int beforeSize = data.size();
                    //data 可以想象已经处理完毕
                    handleData(offset, response.body());
                    int difference = data.size() - beforeSize;

                    //通知数据变更
                    notifyDataSetChanged();

                    if (difference < limit) {
                        offset = -1;
                    } else
                        offset++;
                    successful = true;
                }
                doLast(successful, call, response, null);
            }

            @Override
            public void onFailure(Call<E> call, Throwable t) {
                //发生错误 包括转换错误
                doLast(false, call, null, t);
            }

            private void doLast(boolean successful, Call<E> call, Response<E> response, Throwable t) {
                BasePullView.this.doLast(successful, call, response, t);
            }
        };
        call.enqueue(callback);
    }

    public void clearData() {
        if (data != null && data.size() != 0)
            data.clear();
    }

    public int getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(int firstNumber) {
        this.firstNumber = firstNumber;
        offset = firstNumber;
    }


    /**
     * 请求数据，并返回请求的 uuid
     * 例如：return mDiycode.getTopicsList(null, mNodeId, offset, limit);
     *
     * @param offset 偏移量  todo 0是刷新 或者 第一次加载,其他加载;
     * @param limit  请求数量
     * @return uuid
     */
    @NonNull
    protected abstract Call<E> request(int offset, int limit);

    protected abstract void handleData(int offset, E body);


    public abstract void onRefreshComplete();

    /**
     * 这里可以处理 error fail的事情
     *
     * @param successful
     * @param call
     * @param response
     * @param t
     */
    public abstract void doLast(boolean successful, Call<E> call, Response<E> response, Throwable t);

    public abstract void onLoadMoreComplete();

    public abstract void onLoadMoreFail();

    public abstract void onLoadMoreEnd();

    public abstract void setCanLoadMore(boolean canLoadMore);

    public abstract void notifyDataSetChanged();

}
