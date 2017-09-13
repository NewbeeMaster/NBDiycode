package zone.com.retrofitlib.callwrapper.core;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import zone.com.retrofitlib.RunConfig;
import zone.com.retrofitlib.utils.HandlerUiUtil;

/**
 * Created by Zone on 2016/3/17.
 */
public class MainHandlerUtils {

    public static void onLoading(final ProgressCallback listener, final long total, final long current, final long networkSpeed, final boolean isDownloading) {
        if (RunConfig.isAPP) {
            HandlerUiUtil.post(new Runnable() {
                @Override
                public void run() {
                    listener.onLoading(total, current, networkSpeed, isDownloading);
                }
            });
        } else
            listener.onLoading(total, current, networkSpeed, isDownloading);

    }

    public static void onFailure(final Callback<ResponseBody> callBack, final Call<ResponseBody> call, final Throwable t) {
        if (RunConfig.isAPP) {
            HandlerUiUtil.post(new Runnable() {
                @Override
                public void run() {
                    callBack.onFailure(call, t);
                }
            });
        } else
            callBack.onFailure(call, t);
    }
}
