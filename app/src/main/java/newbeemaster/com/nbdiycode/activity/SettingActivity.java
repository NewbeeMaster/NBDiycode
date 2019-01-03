package newbeemaster.com.nbdiycode.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zone.lib.utils.activity_fragment_ui.ToastUtils;
import com.zone.lib.utils.data.info.AppUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import newbeemaster.com.nbdiycode.R;
import newbeemaster.com.nbdiycode.activity.common.BaseNBActivity;
import newbeemaster.com.nbdiycode.util.IntentUtil;
import zone.com.sdk.API.login.event.UserEvent;
import zone.com.sdk.Diycode;

/**
 * [2017] by Zone
 */

public class SettingActivity extends BaseNBActivity {

    @BindView(R.id.app_version)
    TextView appVersion;
    @BindView(R.id.user)
    LinearLayout userLinearLayout;

    @Override
    public void setContentView() {
        setContentView(R.layout.a_setting);
        registerEventBus();
    }

    @Override
    public void initData() {
        setTitle("设置");
        appVersion.setText(AppUtils.getVersionName(this));

        if(Diycode.getInstance().isLogin())
            userLinearLayout.setVisibility(View.VISIBLE);
        else
            userLinearLayout.setVisibility(View.GONE);
    }

    @OnClick({ R.id.clear_cache, R.id.contribute, R.id.logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.clear_cache:
                //todo 显示 数据大小 http://blog.csdn.net/yaphetzhao/article/details/51729356
                Glide.get(this).clearDiskCache();
                break;


            case R.id.contribute:
                IntentUtil.openAlipay(this);
                break;

            case R.id.logout:
                Diycode.getInstance().logout();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public synchronized void onLoginEvent(UserEvent event) {
        userLinearLayout.setVisibility(View.GONE);
        ToastUtils.showShort(this,"退出成功");
    }
}
