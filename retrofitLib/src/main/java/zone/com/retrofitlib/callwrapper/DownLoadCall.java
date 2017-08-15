package zone.com.retrofitlib.callwrapper;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.IOException;

import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zone.com.retrofitlib.RunConfig;
import zone.com.retrofitlib.callwrapper.down.DownLoadUtils;
import zone.com.retrofitlib.callwrapper.core.MainHandlerUtils;
import zone.com.retrofitlib.callwrapper.core.ProgressCallback;
import zone.com.retrofit.utils.ExecutorUtils;

public class DownLoadCall implements Call<ResponseBody> {

    private final Call<ResponseBody> call;
    private final File file;

    /**
     * @param call
     * @param file 可以是文件夹 也可以是文件
     */
    public DownLoadCall(Call<ResponseBody> call, File file) {
        this.call = call;
        this.file = file;
    }

    // =======================================
    // ============ Call原生方法 ==============
    // =======================================
    @Override
    public Response execute() throws IOException {
        return call.execute();
    }

    @Override
    public boolean isExecuted() {
        return call.isExecuted();
    }

    @Override
    public void cancel() {
        call.cancel();
    }

    @Override
    public boolean isCanceled() {
        return call.isCanceled();
    }

    @Override
    public Call clone() {
        return call.clone();
    }

    @Override
    public Request request() {
        return call.request();
    }


    @Override
    public void enqueue(@NonNull final Callback callback) {
        call.enqueue(new CallBackInner(callback));
    }

    private class CallBackInner implements Callback<ResponseBody> {

        private Callback<ResponseBody> callBack;

        public CallBackInner(Callback<ResponseBody> callBack) {
            this.callBack = callBack;

        }

        public void onResponse(final Call<ResponseBody> call, final Response<ResponseBody> response) {

            callBack.onResponse(call, response);

            if (response == null || !response.isSuccessful())
                callBack.onFailure(call, new Throwable(response.message()));

            if (RunConfig.isAPP) {
                ExecutorUtils.execute(new Runnable() {
                    @Override
                    public void run() {
                        saveFile(call, response);
                    }
                });
            } else
                saveFile(call, response);
        }

        private void saveFile(Call<ResponseBody> call, Response<ResponseBody> response) {
            try {
                if (callBack instanceof ProgressCallback)
                    DownLoadUtils.saveFile((ProgressCallback) callBack, response.body()
                            , call.request().url().toString(), file);
                else
                    DownLoadUtils.saveFile(null, response.body()
                            , call.request().url().toString(), file);
            } catch (IOException e) {
//                      e.printStackTrace();
                MainHandlerUtils.onFailure(callBack, call, new Throwable(e.getMessage()));
            }
        }


        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            callBack.onFailure(call, t);
        }

    }

}