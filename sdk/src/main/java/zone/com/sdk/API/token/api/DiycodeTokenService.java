package zone.com.sdk.API.token.api;

import zone.com.sdk.API.token.bean.Token;
import zone.com.sdk.base.ConstantURL;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * [2017] by Zone
 */

public interface DiycodeTokenService  {



    //--- Token ------------------------------------------------------------------------------------
    /**
     * 获取 Token (一般在登录时调用)
     *
     * @param client_id     客户端 id
     * @param client_secret 客户端私钥
     * @param grant_type    授权方式 - 密码
     * @param username      用户名
     * @param password      密码
     * @return Token 实体类
     */
    @POST(ConstantURL.OAUTH_URL)
    @FormUrlEncoded
    Call<Token> getToken(
            @Field("client_id") String client_id, @Field("client_secret") String client_secret,
            @Field("grant_type") String grant_type, @Field("username") String username,
            @Field("password") String password);


    /**
     * 刷新 token
     *
     * @param client_id     客户端 id
     * @param client_secret 客户端私钥
     * @param grant_type    授权方式 - Refresh Token
     * @param refresh_token token 信息
     * @return Token 实体类
     */
    @POST(ConstantURL.OAUTH_URL)
    @FormUrlEncoded
    Call<Token> refreshToken(@Field("client_id") String client_id, @Field("client_secret") String client_secret,
                             @Field("grant_type") String grant_type, @Field("refresh_token") String refresh_token);
}
