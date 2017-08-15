package zone.com.sdk.base.extra;

import zone.com.sdk.API.token.bean.Token;
import zone.com.sdk.base.BaseImpl;
import zone.com.sdk.base.ConstantURL;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * [2017] by Zone
 */

public class ZAuthenticator implements okhttp3.Authenticator {


    private final CacheUtil mCacheUtil;

    public ZAuthenticator( CacheUtil mCacheUtil) {
        this.mCacheUtil=mCacheUtil;
    }

    @Override
    public Request authenticate(Route route, Response response) {
//            Log.i("自动刷新 token 开始");
        TokenService tokenService = BaseImpl.getRetrofit().create(TokenService.class);
        String accessToken = "";
        try {
            if (null != mCacheUtil.getToken()) {
                Call<Token> call = tokenService.refreshToken(ConstantURL.client_id,
                        ConstantURL.client_secret, ConstantURL.GRANT_TYPE_REFRESH,
                        mCacheUtil.getToken().getRefresh_token());
                retrofit2.Response<Token> tokenResponse = call.execute();
                Token token = tokenResponse.body();
                if (null != token) {
                    mCacheUtil.saveToken(token);
                    accessToken = token.getAccess_token();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//            Logger.i("自动刷新 token 结束：" + accessToken);
        return response.request().newBuilder()
                .addHeader(ConstantURL.KEY_TOKEN, ConstantURL.TOKEN_PREFIX + accessToken)
                .build();
    }

    interface TokenService {
        /**
         * 刷新 token
         */
        @POST(ConstantURL.OAUTH_URL)
        @FormUrlEncoded
        Call<Token> refreshToken(@Field("client_id") String client_id, @Field("client_secret") String client_secret,
                                 @Field("grant_type") String grant_type, @Field("refresh_token") String refresh_token);
    }
}
