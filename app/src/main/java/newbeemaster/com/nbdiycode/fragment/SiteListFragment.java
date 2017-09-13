package newbeemaster.com.nbdiycode.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import newbeemaster.com.nbdiycode.adapter.SiteDelegates;
import newbeemaster.com.nbdiycode.adapter.SiteFullDelegates;
import retrofit2.Call;
import zone.com.sdk.API.sites.bean.SiteItem;
import zone.com.sdk.API.sites.bean.Sites;
import zone.com.sdk.Diycode;
import zone.com.zhelper.ZonePullView;
import zone.com.zrefreshlayout.ZRefreshLayout;

/**
 * [2017] by Zone
 */

public class SiteListFragment extends Fragment {
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.refresh)
    ZRefreshLayout refresh;
    @Bind(R.id.ll_root)
    LinearLayout llRoot;

    private IAdapter<SiteItem> adapter;
    private List<SiteItem> datas = new ArrayList<>();


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
        rv.setLayoutManager(new GridLayoutManager(view.getContext(), 2));

        adapter = new QuickRcvAdapter<SiteItem>(view.getContext(), datas){
            @Override
            protected int getItemViewType2(int dataPosition) {
                return datas.get(dataPosition).isFullspan()?1:0;
            }
        };
        adapter.addViewHolder(0,new SiteDelegates())
                .addViewHolder(1,new SiteFullDelegates())
                .relatedList(rv);

        final ZonePullView zonePullView = new ZonePullView<List<Sites>>(refresh, adapter) {
            @NonNull
            @Override
            protected Call<List<Sites>> request(int offset, int limit) {
                return Diycode.getInstance().getSites();
            }

            @Override
            protected void handleData(int offset, List<Sites> body) {
                //因为刷新会恢复所以 这里置成false;
                setCanLoadMore(false);
                for (Sites sites : body) {
                    datas.add(new SiteItem(sites.getName()).setFullspan(true));
                    for (Sites.Site site : sites.getSites()) {
                        datas.add(new SiteItem(site));
                    }
                }
            }

        };
        // 对于fragment 不要直接替换他的child 会导致新的view高度为0 所以加一层在下面那层作怪~
        zonePullView.firstLoading(0, LoadingLayout.wrap(llRoot));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
