package zone.com.sdk.API.token.api;

import zone.com.sdk.API.token.bean.Token;
import zone.com.retrofit.callwrapper.DialogCall;

/**
 * [2017] by Zone
 */

public interface TokenAPI {

    DialogCall<Token> getToken(String grant_type, String username, String password);

    DialogCall<Token> refreshToken(String refresh_token);
}
