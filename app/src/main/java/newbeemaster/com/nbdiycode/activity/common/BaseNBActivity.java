package newbeemaster.com.nbdiycode.activity.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;
import com.zone.lib.base.activity.BaseAppCompatActivity;
import com.zone.lib.base.activity.kinds.SwipeBackKind;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import newbeemaster.com.nbdiycode.R;
import newbeemaster.com.nbdiycode.util.ViewHolder;

/**
 * [2017] by Zone
 */

public abstract class BaseNBActivity extends BaseAppCompatActivity implements LifecycleProvider<ActivityEvent> {

    protected ViewHolder mViewHolder;
    private boolean isRegisterEventBus;
    private Unbinder binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.lifecycleSubject.onNext(ActivityEvent.CREATE);
    }

    public void registerEventBus() {
        isRegisterEventBus = true;
        EventBus.getDefault().register(this);
    }

    @Override
    public void kindsOnCreate() {
        super.kindsOnCreate();
        mKindControl.get(SwipeBackKind.class).setSwipeBackEnable(false);
    }

    @Override
    public void updateKinds() {
        super.updateKinds();
    }

    @Override
    public void findIDs() {
        binder=ButterKnife.bind(this);
        initActionBar(R.id.toolbar);
    }

    @Override
    public void setListener() {

    }
    // 初始化 ActiobBar
    private void initActionBar(int toolbar) {
        if (findViewById(toolbar) != null) {
            setSupportActionBar((Toolbar) findViewById(toolbar));
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    // 默认点击左上角是结束当前 Activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if (isRegisterEventBus)
            try {
                EventBus.getDefault().unregister(this);
            } catch (Exception e) {
                e.printStackTrace();
            }

        try {
            binder.unbind();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.lifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
    }


    // ======================================= 
    // ============= RxActivity ============== 
    // =======================================

    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return this.lifecycleSubject.hide();
    }

    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(this.lifecycleSubject, event);
    }

    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(this.lifecycleSubject);
    }

    @CallSuper
    protected void onStart() {
        super.onStart();
        this.lifecycleSubject.onNext(ActivityEvent.START);
    }

    @CallSuper
    protected void onResume() {
        super.onResume();
        this.lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @CallSuper
    protected void onPause() {
        this.lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @CallSuper
    protected void onStop() {
        this.lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    public void openActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    public void openActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        this.startActivity(intent);
    }

    public <T> void openActivity(Class<?> cls, String name, T value) {
        Intent intent = new Intent(this, cls);
        if (value == null) {
            throw new RuntimeException("the put value type can't support.");
        } else {
            if (value.getClass() == Boolean.class) {
                intent.putExtra(name, (Boolean) value);
            } else if (value.getClass() == Float.class) {
                intent.putExtra(name, (Float) value);
            } else if (value.getClass() == Integer.class) {
                intent.putExtra(name, (Integer) value);
            } else if (value.getClass() == Long.class) {
                intent.putExtra(name, (Long) value);
            } else if (value.getClass() == String.class) {
                intent.putExtra(name, (String) value);
            } else {
                throw new RuntimeException("the put value type can't support.");
            }
        }
        this.startActivity(intent);
    }

    public <T> void openActivity(Class<?> cls, String name, Serializable value) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(name, value);
        this.startActivity(intent);
    }


    /*
     * 在setContent之前调用
     * Android 全屏界面切换到非全屏界面的问题
     *http://blog.csdn.net/u013011318/article/details/48296869
     */
    protected void smoothSwitchScreen() {
        // 5.0以上修复了此bug
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        ViewGroup rootView = ((ViewGroup) this.findViewById(android.R.id.content));
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        rootView.setPadding(0, statusBarHeight, 0, 0);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }
    }


    // ======================================= 
    // =============切换动画============== 
    // =======================================


    private AnimSwitchEnum mAnimSwitchEnum = AnimSwitchEnum.None;

    public void setAnimSwitchEnum(AnimSwitchEnum animSwitchEnum) {
        this.mAnimSwitchEnum = animSwitchEnum;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        mAnimSwitchEnum.overridePendingTransition(this);
    }

    @Override
    public void finish() {
        super.finish();
        mAnimSwitchEnum.overridePendingTransition(this);
    }
}
