package newbeemaster.com.nbdiycode.adapter;

import com.zone.adapter3.bean.ViewDelegates;
import com.zone.adapter3.helper.Helper;

import newbeemaster.com.nbdiycode.R;
import zone.com.sdk.API.sites.bean.SiteItem;

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
