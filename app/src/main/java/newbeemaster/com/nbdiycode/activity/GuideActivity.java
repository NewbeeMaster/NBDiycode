package newbeemaster.com.nbdiycode.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import and.base.activity.kinds.ScreenSettingKind;
import and.utils.activity_fragment_ui.FragmentSwitcher;
import and.utils.data.file2io2data.SharedUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import newbeemaster.com.nbdiycode.R;
import newbeemaster.com.nbdiycode.activity.common.BaseNBActivity;
import newbeemaster.com.nbdiycode.constant.SPConstant;
import newbeemaster.com.nbdiycode.fragment.BeginFragment;
import newbeemaster.com.nbdiycode.fragment.GuideFragment;

/**
 * [2017] by Zone
 */

public class GuideActivity extends BaseNBActivity {

    private FragmentSwitcher fragmentSwitcher;

    @Override
    public void setContentView() {
        mKindControl.get(ScreenSettingKind.class).setFullScreen();
        mKindControl.get(ScreenSettingKind.class).setNoTitle_AppCompatActivity();

        setContentView(R.layout.a_guide);
    }

    @Override
    public void initData() {
        int openCount = SharedUtils.get(SPConstant.OPEN_COUNT, 0);
        fragmentSwitcher = new FragmentSwitcher(this, R.id.flRoot);
        if (openCount == 0)//第一次
            fragmentSwitcher.initFragment(new GuideFragment());
        else
            fragmentSwitcher.initFragment(new BeginFragment());
        fragmentSwitcher.switchPage(0);
        openCount++;

    }

    @Override
    public void setListener() {

    }

}