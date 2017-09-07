package newbeemaster.com.nbdiycode.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import com.zone.adapter3.QuickRcvAdapter;
import com.zone.adapter3.base.IAdapter;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import ezy.ui.layout.LoadingLayout;
import newbeemaster.com.nbdiycode.R;
import newbeemaster.com.nbdiycode.activity.common.BaseNBActivity;
import newbeemaster.com.nbdiycode.adapter.NotificationDelegates;
import retrofit2.Call;
import zone.com.retrofit.base.ZonePullView;
import zone.com.sdk.API.notifications.bean.Notification;
import zone.com.sdk.Diycode;
import zone.com.zrefreshlayout.ZRefreshLayout;

/**
 * [2017] by Zone
 */

public class NotificationActivity extends BaseNBActivity {

    private static String TYPE_NodeChanged = "NodeChanged";             // 节点变更
    private static String TYPE_TopicReply = "TopicReply";               // Topic 回复
    private static String TYPE_NewsReply = "Hacknews";                  // News  回复
    private static String TYPE_Mention = "Mention";                     // 有人提及
    private static String MENTION_TYPE_TopicReply = "Reply";            // - Topic 回复中提及
    private static String MENTION_TYPE_NewReply = "HacknewsReply";      // - News  回复中提及
    private static String MENTION_TYPE_ProjectReply = "ProjectReply";   // - 项目   回复中提及

    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.refresh)
    ZRefreshLayout refresh;
    @Bind(R.id.ll_root)
    LinearLayout llRoot;

    private IAdapter<Notification> adapter;
    private List<Notification> datas = new ArrayList<>();

    @Override
    public void setContentView() {
        setContentView(R.layout.a_common_my);
    }

    @Override
    public void initData() {
        setTitle("Diycode");

        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new QuickRcvAdapter<>(this, datas);
        adapter.addViewHolder(new NotificationDelegates())
                .relatedList(rv);

        final ZonePullView zonePullView = new ZonePullView<List<Notification>>(refresh, adapter) {
            @NonNull
            @Override
            protected Call<List<Notification>> request(int offset, int limit) {
                return Diycode.getInstance().getNotificationsList(offset, limit);
            }

            @Override
            protected void handleData(int offset, List<Notification> body) {
                datas.addAll(clearExtraData(body));
            }

            /**
             * 清洗数据，主要清洗对象
             * 1. HackNew 的回复 type = Hacknews
             * 2. HackNew 的提及 type = Mention, mention_type = HacknewsReply
             * 3. Project 的提及 type = Mention, mention_type = ProjectReply
             * <p>
             * 保留数据
             * 1. Topic 的回复 type = TopicReply
             * 2. Topic 的提及 type = Mention, mention_type = Reply
             */
            private List<Notification> clearExtraData(List<Notification> datas) {
                List<Notification> clearDatas = new ArrayList<>();
                for (Notification data : datas) {
                    if (data.getType().equals(TYPE_TopicReply) ||
                            (data.getType().equals(TYPE_Mention) && data.getMention_type().equals
                                    (MENTION_TYPE_TopicReply))) {
                        clearDatas.add(data);
                    }
                }
                return clearDatas;
            }
        }.setLimit(10);


        zonePullView.firstLoading(0, LoadingLayout.wrap(llRoot));
    }



}
