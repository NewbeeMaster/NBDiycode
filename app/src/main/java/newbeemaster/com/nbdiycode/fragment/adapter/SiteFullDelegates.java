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
import zone.com.sdk.API.sites.bean.SiteItem;
import zone.com.sdk.API.topic.bean.Topic;

/**
 * [2017] by Zone
 */

public class SiteFullDelegates extends ViewDelegates<SiteItem> {

    @Override
    public int getLayoutId() {
        return R.layout.item_sites;
    }

    @Override
    public boolean isFullspan() {
        return true;
    }

    @Override
    public void fillData(int i, SiteItem bean, Helper<Helper> holder) {
        holder.setText(R.id.name, bean.getName());
    }
}
