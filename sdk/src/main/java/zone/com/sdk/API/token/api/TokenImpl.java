package zone.com.sdk.API.token.api;

import zone.com.sdk.API.token.bean.Token;
import zone.com.sdk.base.BaseImpl;
import zone.com.sdk.base.ConstantURL;
import zone.com.retrofit.callwrapper.DialogCall;

/**
 * [2017] by Zone
 */

public class TokenImpl extends BaseImpl<TokenService> implements TokenAPI {

    @Override
    public DialogCall<Token> getToken(String grant_type, String username, String password) {
        return dialogWrapper(mService.getToken(ConstantURL.client_id, ConstantURL.client_secret
                , ConstantURL.GRANT_TYPE_LOGIN, username, password));
    }

    @Override
    public DialogCall<Token> refreshToken(String refresh_token) {
        // 如果本地没有缓存的 token，则直接返回一个 401 异常
        return dialogWrapper(mService.refreshToken(ConstantURL.client_id, ConstantURL.client_secret
                , ConstantURL.GRANT_TYPE_REFRESH, refresh_token));
    }
}
