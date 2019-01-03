package newbeemaster.com.nbdiycode.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.expectanim.ExpectAnim;
import com.makeramen.roundedimageview.RoundedImageView;
import com.zone.adapter3.QuickRcvAdapter;
import com.zone.adapter3.base.IAdapter;
import com.zone.adapter3.loadmore.OnScrollRcvListener;
import com.zone.lib.utils.data.convert.DensityUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import ezy.ui.layout.LoadingLayout;
import io.reactivex.Observable;
import newbeemaster.com.nbdiycode.R;
import newbeemaster.com.nbdiycode.activity.common.BaseNBActivity;
import newbeemaster.com.nbdiycode.adapter.TopicListDelegates;
import newbeemaster.com.nbdiycode.event.DataUpdateEvent;
import newbeemaster.com.nbdiycode.util.ImageUtils;
import newbeemaster.com.nbdiycode.util.RecyclerViewUtil;
import newbeemaster.com.nbdiycode.utils.RxComposes;
import zone.com.sdk.API.login.bean.User;
import zone.com.sdk.API.topic.bean.Topic;
import zone.com.sdk.Diycode;

import static com.github.florent37.expectanim.core.Expectations.alpha;
import static com.github.florent37.expectanim.core.Expectations.height;
import static com.github.florent37.expectanim.core.Expectations.leftOfParent;
import static com.github.florent37.expectanim.core.Expectations.sameCenterVerticalAs;
import static com.github.florent37.expectanim.core.Expectations.scale;
import static com.github.florent37.expectanim.core.Expectations.toRightOf;
import static com.github.florent37.expectanim.core.Expectations.topOfParent;

/**
 * [2017] by Zone
 */

public class UserActivity extends BaseNBActivity {

    public static String TYPE_DEFAULT = "default";
    public static String TYPE_USER_CREATE = "user_create";
    public static String TYPE_USER_REPLY = "user_reply";
    public static String USER = "user";
    @BindView(R.id.scroll_view)
    NestedScrollView scrollView;
    @BindView(R.id.background)
    ImageView background;
    private ExpectAnim expectAnimMove;

    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.fl_root)
    FrameLayout fl_root;

    private IAdapter<Topic> adapter;
    private List<Topic> datas = new ArrayList<>();


    public static void newInstance(@NonNull Context context, @NonNull User user) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(USER, user);
        context.startActivity(intent);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.a_user);
        registerEventBus();
    }

    @Override
    public void initData() {

        LinearLayoutManager layoutManager;
        rv.setLayoutManager(layoutManager = new LinearLayoutManager(this));
        adapter = new QuickRcvAdapter<>(this, datas);
        adapter.addViewHolder(new TopicListDelegates())
                .relatedList(rv)
                .addOnScrollListener(new OnScrollRcvListener());
        RecyclerViewUtil.soomthScroll(layoutManager, rv);

        initScrollAnimation();
        initUserInfo();
    }


    private void initUserInfo() {
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra(USER);
        if (null != user) {
            setTitle(user.getLogin());
            username.setText(user.getName());
            ImageUtils.loadImage(this, user.getAvatar_url(), avatar);


            Diycode.getInstance().getUser(user.getLogin())
                    .loading(LoadingLayout.wrap(fl_root))
                    .enqueueObservable()
                    .compose(bindToLifecycle())
                    .compose(RxComposes.applyObservableAsync())
                    .flatMap(userDetail -> Observable.zip(Observable.just(userDetail)
                            , Diycode.getInstance().getUserCreateTopicList(userDetail.getLogin(), null, 0, userDetail.getTopics_count())
                                    .enqueueObservable()
                                    .compose(bindToLifecycle())
                                    .compose(RxComposes.applyObservableAsync())
                            , (userDetail1, topics) -> topics))
                    .subscribe(topics -> {
                        datas.addAll(topics);
                        adapter.notifyDataSetChanged();
                        adapter.end();
                    });
        }
    }

    private void initScrollAnimation() {

        this.expectAnimMove = new ExpectAnim()
                .expect(avatar)
                .toBe(
                        topOfParent().withMarginDp(13),
                        leftOfParent().withMarginDp(13),
                        scale(0.5f, 0.5f)
                )
                .expect(username)
                .toBe(
                        toRightOf(avatar).withMarginDp(16),
                        sameCenterVerticalAs(avatar),
                        alpha(0.5f)
                )
                .expect(background)
                .toBe(
                        height(DensityUtils.dp2px(this, 60))
                                .withGravity(Gravity.LEFT, Gravity.TOP)
                )
                .toAnimation();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //todo ?
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if(v instanceof NestedScrollView){
                        final float percent = (scrollY * 1f) / ((NestedScrollView)v).getMaxScrollAmount();
                        expectAnimMove.setPercent(percent);
                    }
                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public synchronized void onDataUpdateEventEvent(DataUpdateEvent event) {
        for (Topic data : datas) {
            if(data.getId()==event.topicId)
                data.setReplies_count(data.getReplies_count()+1);
        }
        adapter.notifyDataSetChanged();
    }

}
