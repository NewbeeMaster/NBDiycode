package zone.com.retrofitlib.views;

import android.app.Activity;
import android.view.View;
import android.view.Window;

import zone.com.retrofitlib.R;

/**
 * [2017] by Zone
 */

public class LoadingDialog extends BaseDialog {


    public LoadingDialog(Activity activity) {
        super(activity);
        setBgVisibility(true);
        setDialogContentView(R.layout.pop_loading, -1);
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
    protected void setLocation(Window window) {
//        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
//        window.setWindowAnimations(R.style.bottom_menu_animation); // 添加动画
    }

    @Override
    public void dismiss() {
        System.out.println("Dialog->dismiss");
        super.dismiss();
    }
}
