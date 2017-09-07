package newbeemaster.com.nbdiycode.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zone.adapter3.bean.ViewDelegates;
import com.zone.adapter3.helper.Helper;
import newbeemaster.com.nbdiycode.R;
import newbeemaster.com.nbdiycode.util.IntentUtil;
import newbeemaster.com.nbdiycode.util.TimeUtil;
import newbeemaster.com.nbdiycode.util.UrlUtil;
import zone.com.sdk.API.login.bean.User;
import zone.com.sdk.API.news.bean.New;

/**
 * [2017] by Zone
 */

public class NewListDelegates extends ViewDelegates<New> {

    @Override
    public int getLayoutId() {
        return R.layout.item_news;
    }

    @Override
    public void fillData(int i, New bean, Helper<Helper> holder) {
        final User user = bean.getUser();
        holder.setText(R.id.username, user.getLogin());
        holder.setText(R.id.node_name, bean.getNode_name());
        holder.setText(R.id.time, TimeUtil.computePastTime(bean.getUpdated_at()));
        holder.setText(R.id.title, bean.getTitle());
        holder.setText(R.id.host_name, UrlUtil.getHost(bean.getAddress()));

        // 加载头像
        ImageView imageView = holder.getView(R.id.avatar);
        String url = user.getAvatar_url();
        String url2 = url;
        if (url.contains("diycode"))    // 添加判断，防止替换掉其他网站掉图片
            url2 = url.replace("large_avatar", "avatar");
        Glide.with(context).load(url2).diskCacheStrategy(DiskCacheStrategy.RESULT).into(imageView);

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo UserActivity
//                UserActivity.newInstance(context, user);
            }
        }, R.id.avatar, R.id.username);

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.openUrl(context, bean.getAddress());
            }
        },R.id.item);
    }
}
