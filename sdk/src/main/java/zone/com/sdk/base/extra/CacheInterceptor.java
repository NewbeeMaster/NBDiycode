package zone.com.sdk.base.extra;

import android.content.Context;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import zone.com.retrofitlib.Config;
import zone.com.retrofitlib.utils.Utils;

/**
 * 为okhttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
 */
class CacheInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {

        // 有网络时 设置缓存超时时间1个小时
        int maxAge = 60 * 60;
        // 无网络时，设置超时为1天
        int maxStale = 60 * 60 * 24;
        Request request = chain.request();
        if (!Utils.haveNetWork(Config.getInstance().getContext())) {
            //无网络时只从缓存中读取 todo post不支持
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }

        Response response = chain.proceed(request);
        if (Utils.haveNetWork(Config.getInstance().getContext())) {
            String cacheControl = request.cacheControl().toString();
            if (cacheControl != null) {//服务端设置了缓存策略，则使用服务端缓存策略
                return response.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {//服务端未设置缓存策略，则客户端自行设置
                return response.newBuilder()
                        .header("Cache-Control", "public, max-age=36000")
                        .removeHeader("Pragma")
                        .build();
            }

        } else {
            //无网络
            return response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=3600000")
                    .removeHeader("Pragma")
                    .build();
        }
    }
}