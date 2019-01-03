package newbeemaster.com.nbdiycode.adapter;

import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zone.adapter3.bean.Holder;
import com.zone.adapter3.bean.ViewDelegates;

import newbeemaster.com.nbdiycode.R;
import newbeemaster.com.nbdiycode.util.HtmlUtil;
import newbeemaster.com.nbdiycode.util.ImageUtils;
import zone.com.sdk.API.login.bean.User;
import zone.com.sdk.API.notifications.bean.Notification;

/**
 * [2017] by Zone
 */

public class NotificationDelegates extends ViewDelegates<Notification> {

    private static String TYPE_NodeChanged = "NodeChanged";             // 节点变更
    private static String TYPE_TopicReply = "TopicReply";               // Topic 回复
    private static String TYPE_NewsReply = "Hacknews";                  // News  回复
    private static String TYPE_Mention = "Mention";                     // 有人提及
    private static String MENTION_TYPE_TopicReply = "Reply";            // - Topic 回复中提及
    private static String MENTION_TYPE_NewReply = "HacknewsReply";      // - News  回复中提及
    private static String MENTION_TYPE_ProjectReply = "ProjectReply";   // - 项目   回复中提及


    @Override
    public int getLayoutId() {
        return R.layout.item_notification;
    }

    @Override
    public void fillData(int postion, Notification bean, Holder helper) {

        final User actor = bean.getActor();
        String suffix = "";
        String desc = "";
        int topic_id = -1;
        if (bean.getType().equals(TYPE_TopicReply)) {
            suffix = "回复了话题：";
            desc = bean.getReply().getTopic_title();
            topic_id = bean.getReply().getTopic_id();
        } else if (bean.getType().equals(TYPE_Mention) && bean.getMention_type().equals
                (MENTION_TYPE_TopicReply)) {
            suffix = "提到了你:";
            desc = bean.getMention().getBody_html();
            topic_id = bean.getMention().getTopic_id();
        }
        String type = actor.getLogin() + " " + suffix;

        ImageView imageView = (ImageView) helper.getView(R.id.avatar);
        ImageUtils.loadImage(context, actor.getAvatar_url(), imageView);
        helper.setText(R.id.notification_type, type);

        Spanned result_desc = Html.fromHtml(HtmlUtil.removeP(desc));
        TextView text_desc = (TextView) helper.getView(R.id.desc);
        text_desc.setText(result_desc);

        helper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UserActivity.newInstance(context, actor);
            }
        }, R.id.avatar, R.id.notification_type);

        final int final_topic_id = topic_id;
        helper.getView(R.id.item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TopicContentActivity.newInstance(mContext, final_topic_id);
            }
        });
    }
}
