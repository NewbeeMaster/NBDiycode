package zone.com.retrofitlib.utils;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import zone.com.retrofitlib.Config;
import zone.com.retrofitlib.cookie.store.CookieStore;

/**
 * [2017] by Zone
 */

public class RetrofitHelper {


    public static CookieStore getCookieStore(OkHttpClient client) {
        return (CookieStore) client.cookieJar();
    }

    public static Cache getCache() {
        //缓存目录
        String path = Config.getInstance().getContext().getCacheDir().getAbsolutePath()
                + File.separator + "data" + File.separator + "net_cache";
        return new Cache(new File(path), 10 * 1024 * 1024);

    }

    public static Interceptor getHttpLoggingInterceptor(boolean isDebug) {
        if (isDebug) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            return loggingInterceptor;
        } else {
            return nullInterceptor;
        }
    }

    public static Interceptor getStethoInterceptor(boolean isDebug) {
        if (isDebug) {
            return new StethoInterceptor();
        } else {
            return nullInterceptor;
        }
    }

    private static Interceptor nullInterceptor = new Interceptor() {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            return chain.proceed(chain.request());
        }
    };

}