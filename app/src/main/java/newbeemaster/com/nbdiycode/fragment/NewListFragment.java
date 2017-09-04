package newbeemaster.com.nbdiycode.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.zone.adapter3.QuickRcvAdapter;
import com.zone.adapter3.base.IAdapter;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import ezy.ui.layout.LoadingLayout;
import newbeemaster.com.nbdiycode.R;
import newbeemaster.com.nbdiycode.fragment.adapter.NewListDelegates;
import retrofit2.Call;
import zone.com.retrofit.base.ZonePullView;
import zone.com.sdk.API.news.bean.New;
import zone.com.sdk.Diycode;
import zone.com.zrefreshlayout.ZRefreshLayout;

/**
 * [2017] by Zone
 */

public class NewListFragment extends Fragment {
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.refresh)
    ZRefreshLayout refresh;
    @Bind(R.id.ll_root)
    LinearLayout llRoot;

    private IAdapter<New> adapter;
    private List<New> datas=new ArrayList<>();


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
        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));

        adapter = new QuickRcvAdapter<>(view.getContext(), datas);
        adapter.addViewHolder(new NewListDelegates())
                .relatedList(rv);

        final ZonePullView zonePullView = new ZonePullView<List<New>>(refresh, adapter) {
            @NonNull
            @Override
            protected Call<List<New>> request(int offset, int limit) {
                return Diycode.getInstance().getNewsList(null, offset, limit) ;
            }

            @Override
            protected void handleData(int offset, List<New> body) {
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
