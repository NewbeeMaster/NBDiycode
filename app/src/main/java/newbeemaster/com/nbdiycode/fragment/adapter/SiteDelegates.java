package newbeemaster.com.nbdiycode.fragment.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zone.adapter3.bean.ViewDelegates;
import com.zone.adapter3.helper.Helper;

import newbeemaster.com.nbdiycode.R;
import newbeemaster.com.nbdiycode.util.IntentUtil;
import zone.com.sdk.API.sites.bean.SiteItem;
import zone.com.sdk.API.topic.bean.Topic;

/**
 * [2017] by Zone
 */

public class SiteDelegates extends ViewDelegates<SiteItem> {

    @Override
    public int getLayoutId() {
        return R.layout.item_site;
    }


    @Override
    public void fillData(int i, SiteItem bean, Helper<Helper> holder) {
        if (bean.getName().isEmpty()) return;
        holder.setText(R.id.name, bean.getName());
        ImageView icon = holder.getView(R.id.icon);
        Glide.with(context).load(bean.getAvatar_url()).into(icon);

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.openUrl(context, bean.getUrl());
            }
        }, R.id.item);
    }
}
