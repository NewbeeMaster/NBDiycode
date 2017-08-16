package newbeemaster.com.nbdiycode.activity.common;

import android.os.Bundle;
import android.view.View;

import and.base.activity.BaseAppCompatActivity;
import butterknife.ButterKnife;
import newbeemaster.com.nbdiycode.R;

/**
 * [2017] by Zone
 */

public abstract class BaseNBActivity extends BaseAppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void findIDs() {
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
