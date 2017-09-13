package zone.com.sdk.API.token.api;

import zone.com.retrofitlib.callwrapper.DialogCall;
import zone.com.sdk.API.token.bean.Token;

/**
 * [2017] by Zone
 */

public interface TokenAPI {

    DialogCall<Token> getToken(String grant_type, String username, String password);

    DialogCall<Token> refreshToken(String refresh_token);
}
