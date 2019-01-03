package newbeemaster.com.nbdiycode.activity;

import android.content.Intent;

import com.zone.lib.utils.activity_fragment_ui.FragmentSwitcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import newbeemaster.com.nbdiycode.R;
import newbeemaster.com.nbdiycode.activity.common.AnimSwitchEnum;
import newbeemaster.com.nbdiycode.activity.common.BaseNBActivity;
import newbeemaster.com.nbdiycode.constant.SPConstant;
import newbeemaster.com.nbdiycode.event.GuideFinishEvent;
import newbeemaster.com.nbdiycode.fragment.BeginFragment;
import newbeemaster.com.nbdiycode.fragment.GuideFragment;
import newbeemaster.com.nbdiycode.utils.SP1;

/**
 * [2017] by Zone
 */

public class GuideActivity extends BaseNBActivity {

    private FragmentSwitcher fragmentSwitcher;

    @Override
    public void setContentView() {
//        mKindControl.get(ScreenSettingKind.class).setFullScreen();
//        mKindControl.get(ScreenSettingKind.class).setNoTitle_AppCompatActivity();
        setContentView(R.layout.a_guide);
        registerEventBus();
        setAnimSwitchEnum(AnimSwitchEnum.Fade);
    }


    @Override
    public void initData() {
        int openCount = SP1.INSTANCE.get(SPConstant.OPEN_COUNT, 0);

        fragmentSwitcher = new FragmentSwitcher(this, R.id.flRoot);
        if (openCount == 0)//第一次
            fragmentSwitcher.initFragment(new GuideFragment());
        else
            fragmentSwitcher.initFragment(new BeginFragment());
        fragmentSwitcher.switchPage(0);

        openCount++;
        SP1.INSTANCE.put(SPConstant.OPEN_COUNT, openCount);
    }

    @Override
    public void setListener() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public synchronized void onMessageEvent(GuideFinishEvent event) {

        //防止第二次进入
        EventBus.getDefault().unregister(this);

        /* Do something */
        startActivity(new Intent(GuideActivity.this, MainActivity.class));
        finish();
    }

}
