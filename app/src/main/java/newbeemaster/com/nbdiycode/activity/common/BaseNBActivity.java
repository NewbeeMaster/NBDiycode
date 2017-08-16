package newbeemaster.com.nbdiycode.activity.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;

import and.base.activity.BaseAppCompatActivity;
import butterknife.ButterKnife;
import newbeemaster.com.nbdiycode.util.ViewHolder;

/**
 * [2017] by Zone
 */

public abstract class BaseNBActivity extends BaseAppCompatActivity {

    private boolean enable;
    protected ViewHolder mViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (enable)
            EventBus.getDefault().register(this);
    }

    public void switchEventBus(boolean enable) {
        this.enable = enable;
    }

    @Override
    public void findIDs() {
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (enable)
            try {
                EventBus.getDefault().unregister(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        ButterKnife.unbind(this);
    }

    public static void openActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }
}
