package newbeemaster.com.nbdiycode.adapter;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zone.adapter3.bean.Holder;
import com.zone.adapter3.bean.ViewDelegates;
import newbeemaster.com.nbdiycode.R;
import newbeemaster.com.nbdiycode.activity.UserActivity;
import newbeemaster.com.nbdiycode.gilde.GlideImageGetter;
import newbeemaster.com.nbdiycode.util.HtmlUtil;
import newbeemaster.com.nbdiycode.util.ImageUtils;
import newbeemaster.com.nbdiycode.util.TimeUtil;
import zone.com.sdk.API.login.bean.User;
import zone.com.sdk.API.topic.bean.TopicReply;

/**
 * [2017] by Zone
 */

public class TopicReplyDelegates extends ViewDelegates<TopicReply> {
    @Override
    public int getLayoutId() {
        return R.layout.item_topic_reply;
    }


    @Override
    public void fillData(int postion, TopicReply bean, Holder helper) {
        final User user = bean.getUser();
        helper.setText(R.id.username, user.getLogin());
        helper.setText(R.id.time, TimeUtil.computePastTime(bean.getUpdated_at()));

        ImageView avatar = (ImageView) helper.getView(R.id.avatar);
        ImageUtils.loadImage(context, user.getAvatar_url(), avatar);
        TextView content = (TextView) helper.getView(R.id.content);
        // TODO 评论区代码问题  Html.fromHtml 第二个参数？
        content.setText(Html.fromHtml(HtmlUtil.removeP(bean.getBody_html()),
                new GlideImageGetter(context, content), null));

        helper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserActivity.class);
                intent.putExtra(UserActivity.USER, user);
                context.startActivity(intent);
            }
        }, R.id.avatar, R.id.username);
    }
}
