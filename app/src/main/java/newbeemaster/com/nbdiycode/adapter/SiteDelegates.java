package newbeemaster.com.nbdiycode.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zone.adapter3.bean.Holder;
import com.zone.adapter3.bean.ViewDelegates;

import newbeemaster.com.nbdiycode.R;
import newbeemaster.com.nbdiycode.util.IntentUtil;
import zone.com.sdk.API.sites.bean.SiteItem;

/**
 * [2017] by Zone
 */

public class SiteDelegates extends ViewDelegates<SiteItem> {

    @Override
    public int getLayoutId() {
        return R.layout.item_site;
    }

    @Override
    public void fillData(int postion, SiteItem bean, Holder holder) {
        if (bean.getName().isEmpty()) return;
        holder.setText(R.id.name, bean.getName());
        ImageView icon = (ImageView) holder.getView(R.id.icon);
        Glide.with(context).load(bean.getAvatar_url()).into(icon);

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.openUrl(context, bean.getUrl());
            }
        }, R.id.item);
    }
}
