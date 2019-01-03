package newbeemaster.com.nbdiycode.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zone.adapter3.QuickRcvAdapter;
import com.zone.adapter3.base.IAdapter;
import com.zone.adapter3.loadmore.OnScrollRcvListener;
import com.zone.lib.utils.activity_fragment_ui.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import newbeemaster.com.nbdiycode.R;
import newbeemaster.com.nbdiycode.activity.common.BaseNBActivity;
import newbeemaster.com.nbdiycode.adapter.TopicReplyDelegates;
import newbeemaster.com.nbdiycode.event.DataUpdateEvent;
import newbeemaster.com.nbdiycode.util.ImageUtils;
import newbeemaster.com.nbdiycode.util.RecyclerViewUtil;
import newbeemaster.com.nbdiycode.util.TimeUtil;
import newbeemaster.com.nbdiycode.utils.RxComposes;
import newbeemaster.com.nbdiycode.webview.GcsMarkdownViewClient;
import newbeemaster.com.nbdiycode.webview.WebImageListener;
import newbeemaster.com.nbdiycode.widget.MarkdownView;
import zone.com.sdk.API.login.bean.User;
import zone.com.sdk.API.topic.bean.Topic;
import zone.com.sdk.API.topic.bean.TopicContent;
import zone.com.sdk.API.topic.bean.TopicReply;
import zone.com.sdk.Diycode;

/**
 * [2017] by Zone
 */

public class TopicContentActivity extends BaseNBActivity {

    public static String TOPIC = "topic";
    public static String TOPIC_ID = "topic_id";
    public static String ERROR = "error";
    public static String TYPE = "type";
    @BindView(R.id.need_login)
    RelativeLayout needLogin;
    @BindView(R.id.my_reply)
    EditText myReply;
    @BindView(R.id.send_reply)
    Button sendReply;
    @BindView(R.id.can_reply)
    RelativeLayout canReply;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.ll_main)
    LinearLayout ll_main;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.reply_count)
    TextView replyCount;

    private int topic_id = -1;
    private Topic topic = null;

    @BindView(R.id.reply_list)
    RecyclerView reply_list;
    @BindView(R.id.webview_container)
    FrameLayout webview_container;
    private MarkdownView mMarkdownView;

    private IAdapter<TopicReply> adapter;
    private List<TopicReply> datas = new ArrayList<>();

    public static void newInstance(@NonNull Context context, @NonNull Topic topic) {
        Intent intent = new Intent(context, TopicContentActivity.class);
        intent.putExtra(TOPIC, topic);
        context.startActivity(intent);
    }

    public static void newInstance(@NonNull Context context, @NonNull int topic_id) {
        Intent intent = new Intent(context, TopicContentActivity.class);
        intent.putExtra(TOPIC_ID, topic_id);
        context.startActivity(intent);
    }


    @Override
    public void setContentView() {
        setContentView(R.layout.a_topic_content);
    }

    @Override
    public void initData() {
        setTitle("话题");

        Intent intent = getIntent();
        topic_id = intent.getIntExtra(TOPIC_ID, -1);
        topic = (Topic) intent.getSerializableExtra(TOPIC);
        if (topic != null && topic_id <= 0) {
            topic_id = topic.getId();
        }

        initRecyclerView();
        initMarkdown();

        if (topic != null) {

            Diycode.getInstance().getTopic(topic.getId())
                    .enqueueObservable()
                    .compose(bindToLifecycle())
                    .compose(RxComposes.applyObservableAsync())
                    .subscribe(topicContent -> {
                        showAll(topicContent);
                    })
            ;

            if (topic.getReplies_count() != 0)
                Diycode.getInstance().getTopicRepliesList(topic.getId(), null, topic.getReplies_count())
                        .enqueueObservable()
                        .compose(bindToLifecycle())
                        .compose(RxComposes.applyObservableAsync())
                        .subscribe(topicReplies -> {
                            datas.clear();
                            datas.addAll(topicReplies);
                            adapter.notifyDataSetChanged();
                            adapter.end();
                        });
        }
    }

    // 显示全部数据
    private void showAll(TopicContent topic) {
        showPreview(topic);
        mMarkdownView.setMarkDownText(topic.getBody());
    }

    // 显示基础数据
    private void showPreview(TopicContent topic) {
        User user = topic.getUser();
        username.setText(user.getLogin() + "(" + user.getName() + ")");
        time.setText(TimeUtil.computePastTime(topic.getUpdated_at()));
        title.setText(topic.getTitle());
        replyCount.setText("共收到 " + topic.getReplies_count() + "条回复");
        ImageUtils.loadImage(this, user.getAvatar_url(), (ImageView) findViewById(R.id.avatar));
    }


    @Override
    public void setListener() {
        super.setListener();
        findViewById(R.id.avatar).setOnClickListener(this);
        findViewById(R.id.username).setOnClickListener(this);
    }

    private void initMarkdown() {
        mMarkdownView = new MarkdownView(this.getApplicationContext());
        mMarkdownView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        webview_container.addView(mMarkdownView);

        //todo  WebImageListener webView图片被点击 和下载 跳转
        WebImageListener listener = new WebImageListener(this, ImageActivity.class);
        mMarkdownView.addJavascriptInterface(listener, "listener");

        //todo  作业写不可描述的监听？主要是缓存图片
        GcsMarkdownViewClient mWebViewClient = new GcsMarkdownViewClient(this);
        mMarkdownView.setWebViewClient(mWebViewClient);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager;
        reply_list.setLayoutManager(layoutManager = new LinearLayoutManager(this));
        adapter = new QuickRcvAdapter<>(this, datas);
        adapter.addViewHolder(new TopicReplyDelegates())
                .relatedList(reply_list)
                .addOnScrollListener(new OnScrollRcvListener());
        RecyclerViewUtil.soomthScroll(layoutManager, reply_list);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initReply();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initReply();
    }


    // 在 start 和 restart 调用
    private void initReply() {
        if (!Diycode.getInstance().isLogin()) {
            needLogin.setVisibility(View.VISIBLE);
            canReply.setVisibility(View.GONE);
            login.setOnClickListener(this);
        } else {
            needLogin.setVisibility(View.GONE);
            canReply.setVisibility(View.VISIBLE);
            sendReply.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.avatar:
            case R.id.username:
                if (null != topic) {
                    openActivity(UserActivity.class, UserActivity.USER, topic.getUser());
                }
                break;
            case R.id.login:
                openActivity(LoginActvity.class);
                break;
            case R.id.send_reply:
                reply();
                break;
        }
    }

    private synchronized void reply() {
        String reply = myReply.getText().toString();
        if (reply.isEmpty()) {
            ToastUtils.showShort(this, "评论内容不能为空。");
            return;
        }
        Diycode.getInstance()
                .createTopicReply(topic.getId(), reply)
                .compose(bindToLifecycle())
                .compose(RxComposes.applyObservableAsync())
                .subscribe(topicReply -> {
                            ToastUtils.showShort(TopicContentActivity.this, "回复成功！");
                            datas.add(topicReply);
                            adapter.notifyDataSetChanged();
                            EventBus.getDefault().post(new DataUpdateEvent(topic.getId()));
                        }
                        , throwable -> ToastUtils.showShort(TopicContentActivity.this, "回复失败！"));
        myReply.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearWebViewResource();
    }

    // 防止 WebView 引起的内存泄漏
    public void clearWebViewResource() {
        if (mMarkdownView != null) {
            mMarkdownView.clearHistory();
            ((ViewGroup) mMarkdownView.getParent()).removeView(mMarkdownView);
            mMarkdownView.setTag(null);
            mMarkdownView.loadUrl("about:blank");
            mMarkdownView.stopLoading();
            mMarkdownView.setWebViewClient(null);
            mMarkdownView.setWebChromeClient(null);
            mMarkdownView.removeAllViews();
            mMarkdownView.destroy();
            mMarkdownView = null;
        }
    }

}
