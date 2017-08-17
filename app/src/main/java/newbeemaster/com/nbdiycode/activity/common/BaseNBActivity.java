package newbeemaster.com.nbdiycode.activity.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;
import org.greenrobot.eventbus.EventBus;
import and.base.activity.BaseAppCompatActivity;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import newbeemaster.com.nbdiycode.util.ViewHolder;

/**
 * [2017] by Zone
 */

public abstract class BaseNBActivity extends BaseAppCompatActivity implements LifecycleProvider<ActivityEvent> {

    private boolean enable;
    protected ViewHolder mViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.lifecycleSubject.onNext(ActivityEvent.CREATE);
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
        if (enable)
            try {
                EventBus.getDefault().unregister(this);
            } catch (Exception e) {
                e.printStackTrace();
            }

        try {
            ButterKnife.unbind(this);
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

    public static void openActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
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

}
