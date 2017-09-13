/*
 * Copyright 2017 GcsSloop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 2017-03-08 01:01:18
 *
 * GitHub:  https://github.com/GcsSloop
 * Website: http://www.gcssloop.com
 * Weibo:   http://weibo.com/GcsSloop
 */

package zone.com.retrofitlib;

import android.content.Context;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import zone.com.retrofitlib.callwrapper.DialogCall;
import zone.com.retrofitlib.callwrapper.DownLoadCall;

/**
 * 实现类，具体实现在此处
 *
 * @param <Service>
 */
public abstract class BaseNetwork<Service> {

    protected Context context;
    private static Retrofit mRetrofit;
    protected Service mService;

    public BaseNetwork() {
        if (RunConfig.isAPP) {
            context = Config.getInstance().getContext();
        }
        initConfig();
        initRetrofitInner();
        this.mService = mRetrofit.create(getServiceClass());

    }

    private Class<Service> getServiceClass() {
        return (Class<Service>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected abstract void initConfig();

    private synchronized void initRetrofitInner() {
        if (null != mRetrofit)
            return;
        mRetrofit=initRetrofit();
    }

    protected abstract Retrofit initRetrofit() ;

    protected <T> DialogCall<T> dialogWrapper(Call<T> call) {
        return new DialogCall(call);
    }

    protected DownLoadCall downLoadWrapper(Call call, File file) {
        return new DownLoadCall((Call<ResponseBody>) call, file);
    }

    public static Retrofit getRetrofit() {
        return mRetrofit;
    }
}



