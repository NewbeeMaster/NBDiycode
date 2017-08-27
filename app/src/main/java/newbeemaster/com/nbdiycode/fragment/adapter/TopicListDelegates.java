package newbeemaster.com.nbdiycode.fragment.adapter;

import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zone.adapter3.bean.ViewDelegates;
import com.zone.adapter3.helper.Helper;
import newbeemaster.com.nbdiycode.R;
import newbeemaster.com.nbdiycode.util.TimeUtil;
import zone.com.sdk.API.login.bean.User;
import zone.com.sdk.API.topic.bean.Topic;

/**
 * [2017] by Zone
 */

public class TopicListDelegates extends ViewDelegates<Topic> {

    @Override
    public int getLayoutId() {
        return R.layout.item_topic;
    }

    @Override
    public void fillData(int i, Topic bean, Helper<Helper> holder) {
        final User user = bean.getUser();
        holder.setText(R.id.username, user.getLogin());
        holder.setText(R.id.node_name, bean.getNode_name());
        holder.setText(R.id.time, TimeUtil.computePastTime(bean.getUpdated_at()));
        holder.setText(R.id.title, bean.getTitle());

        // 加载头像
        ImageView imageView = holder.getView(R.id.avatar);

        String url = user.getAvatar_url();
        String url2 = url;
        if (url.contains("diycode"))    // 添加判断，防止替换掉其他网站掉图片
            url2 = url.replace("large_avatar", "avatar");
        Glide.with(context)
                .load(url2)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);

        String state = "评论 " + bean.getReplies_count();
        holder.setText(R.id.state, state);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
//                    case R.id.avatar:
//                    case R.id.username:
//                        UserActivity.newInstance(mContext, user);
//                        break;
//                    case R.id.item:
//                        TopicContentActivity.newInstance(mContext, bean);
//                        break;
//                    case R.id.node_name:
//                        TopicActivity.newInstance(mContext, bean.getNode_id(), bean.getNode_name());
//                        break;
                }
            }
        };

        holder.setOnClickListener(listener, R.id.avatar, R.id.username, R.id.item, R.id.node_name);
    }
}
