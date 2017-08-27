package newbeemaster.com.nbdiycode.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.zone.adapter3.QuickRcvAdapter;
import com.zone.adapter3.base.IAdapter;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import ezy.ui.layout.LoadingLayout;
import newbeemaster.com.nbdiycode.R;
import newbeemaster.com.nbdiycode.fragment.adapter.TopicListDelegates;
import retrofit2.Call;
import zone.com.retrofit.base.ZonePullView;
import zone.com.retrofit.views.LoadingAnimView;
import zone.com.sdk.API.topic.bean.Topic;
import zone.com.sdk.Diycode;
import zone.com.zrefreshlayout.ZRefreshLayout;

/**
 * [2017] by Zone
 */

public class TopicListFragment extends RxFragment {
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.loading)
    LoadingAnimView loading;
    @Bind(R.id.refresh)
    ZRefreshLayout refresh;
    @Bind(R.id.ll_root)
    LinearLayout llRoot;

    private IAdapter<Topic> adapter;
    private List<Topic> datas=new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_list, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//
        loading.setNum(12);
        loading.setHostRadius(60);
        loading.setRadius(13);
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));

        adapter = new QuickRcvAdapter<>(view.getContext(), datas);
        adapter.addViewHolder(new TopicListDelegates())
                .relatedList(rv);

        final ZonePullView zonePullView = new ZonePullView<List<Topic>>(refresh, adapter) {
            @NonNull
            @Override
            protected Call<List<Topic>> request(int offset, int limit) {
                return Diycode.getInstance().getTopicsList(null, null, offset, limit);
            }

            @Override
            protected void handleData(int offset, List<Topic> body) {
                datas.addAll(body);
            }
        };
        //todo 对于fragment 不要直接替换他的child 会导致新的view高度为0 所以加一层在下面那层作怪~
        zonePullView.firstLoading(0, LoadingLayout.wrap(llRoot));

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
