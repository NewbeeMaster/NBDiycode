package zone.com.retrofit.views;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import zone.com.retrofit.R;

/**
 * [2017] by Zone
 */

public class LoadingPopWindow extends BasePopWindow {

    public LoadingPopWindow(Activity activity) {
        super(activity);
        setPopContentView(R.layout.pop_loading, -1);
//        setBgVisibility(false);
    }

    @Override
    protected void findView(View mMenuView) {
        LoadingAnimView loading = (LoadingAnimView) mMenuView.findViewById(R.id.loadingAnimView);
        loading.setNum(12);
        loading.setHostRadius(60);
        loading.setRadius(13);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void setLocation(View view) {
        showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);
    }
}
