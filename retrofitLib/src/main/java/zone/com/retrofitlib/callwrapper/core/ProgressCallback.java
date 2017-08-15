package zone.com.retrofitlib.callwrapper.core;

import retrofit2.Callback;

public interface ProgressCallback<T> extends Callback<T> {
    void onLoading(long total, long current, long networkSpeed, boolean isDownloading);
}