package newbeemaster.com.nbdiycode.adapter;

import com.zone.adapter3.bean.Holder;
import com.zone.adapter3.bean.ViewDelegates;
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
    public void fillData(int postion, SiteItem data, Holder holder) {
        holder.setText(R.id.name, data.getName());
    }
}
