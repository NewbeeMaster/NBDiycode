package zone.com.sdk.base.extra;

import android.support.annotation.NonNull;

import java.io.IOException;

import zone.com.sdk.base.ConstantURL;
import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * [2017] by Zone
 */

public class TokenInterceptor implements Interceptor {
    private final CacheUtil mCacheUtil;

    public TokenInterceptor(@NonNull CacheUtil mCacheUtil) {
        this.mCacheUtil = mCacheUtil;
    }

    // 为所有请求自动添加 token
    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        // 如果当前没有缓存 token 或者请求已经附带 token 了，就不再添加
        if (null == mCacheUtil.getToken() || alreadyHasAuthorizationHeader(originalRequest)) {
            return chain.proceed(originalRequest);
        }
        String token = ConstantURL.TOKEN_PREFIX + mCacheUtil.getToken().getAccess_token();
        // 为请求附加 token
        Request authorised = originalRequest.newBuilder()
                .header(ConstantURL.KEY_TOKEN, token)
                .build();
        return chain.proceed(authorised);
    }


    private boolean alreadyHasAuthorizationHeader(Request originalRequest) {
        // 用于 debug 时临时移除 token
        String token = originalRequest.header(ConstantURL.KEY_TOKEN);
        // 如果本身是请求 token 的 URL，直接返回 true
        // 如果不是，则判断 header 中是否已经添加过 Authorization 这个字段，以及是否为空
        return !(null == token
                || token.isEmpty()
                || originalRequest.url().toString().contains(ConstantURL.OAUTH_URL));
    }
}

