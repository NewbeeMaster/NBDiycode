package newbeemaster.com.nbdiycode.activity;

import android.widget.EditText;

import com.socks.library.ZLog;

import org.greenrobot.eventbus.EventBus;

import and.utils.activity_fragment_ui.ToastUtils;
import and.utils.data.check.StringCheck;
import and.utils.data.file2io2data.SharedUtils;
import butterknife.Bind;
import butterknife.OnClick;
import newbeemaster.com.nbdiycode.R;
import newbeemaster.com.nbdiycode.activity.common.BaseNBActivity;
import newbeemaster.com.nbdiycode.constant.SPConstant;
import newbeemaster.com.nbdiycode.event.UserEvent;
import newbeemaster.com.nbdiycode.util.IntentUtil;
import newbeemaster.com.nbdiycode.utils.RxComposes;
import zone.com.sdk.Diycode;
import zone.com.sdk.base.extra.CacheUtil;

/**
 * [2017] by Zone
 */

public class LoginActvity extends BaseNBActivity {

    @Bind(R.id.etUsername)
    EditText etUsername;
    @Bind(R.id.etPassword)
    EditText etPassword;

    @Override
    public void setContentView() {
        setContentView(R.layout.a_login);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }

    @OnClick(R.id.back_img)
    public void onBackClicked() {
        finish();
    }

    @OnClick(R.id.btLogin)
    public void onLoginClicked() {
        //检测
        String username = etUsername.getText().toString();
        if (StringCheck.isEmptyTrim(username)) {
            ToastUtils.showShort(this, "账户名不能为空！");
            return;
        }
        String password = etPassword.getText().toString();
        if (StringCheck.isEmptyTrim(password)) {
            ToastUtils.showShort(this, "密码不能为空！");
            return;
        }

        Diycode.getInstance().login(username, password)
                .enqueueObservable()
                .compose(bindToLifecycle())
                .compose(RxComposes.applyObservableAsync())
                .subscribe(token -> {
                            new CacheUtil(this).saveToken(token);
                            Diycode.getInstance().getMe()
                                    .compose(bindToLifecycle())
                                    .compose(RxComposes.applyObservableAsync())
                                    .subscribe(userDetail -> {
                                                EventBus.getDefault().post(new UserEvent(userDetail));
                                                SharedUtils.put(SPConstant.USER_DETAIL, userDetail);
                                            },
                                            throwable -> {
                                                ToastUtils.showShort(this, "用户信息获取异常！");
                                            });

                            finish();
                        }
                        , throwable -> {
                            ZLog.e("登录失败：" + throwable.getMessage());
                            ToastUtils.showShort(this, "登录失败");
                        });


    }

    @OnClick(R.id.sign_up)
    public void onSignUpClicked() {
        IntentUtil.openUrl(this, "https://www.diycode.cc/account/sign_up");
    }
}
