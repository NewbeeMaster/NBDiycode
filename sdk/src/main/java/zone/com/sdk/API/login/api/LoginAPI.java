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

package zone.com.sdk.API.login.api;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import zone.com.retrofitlib.callwrapper.DialogCall;
import zone.com.sdk.API.login.bean.UserDetail;
import zone.com.sdk.API.token.bean.Token;


public interface LoginAPI {

    //--- login ------------------------------------------------------------------------------------

    /**
     * 登录时调用
     * 返回一个 token，用于获取各类私有信息使用，该 token 用 LoginEvent 接收。
     *
     * @param user_name 用户名
     * @param password  密码
     */
    DialogCall<Token> login(@NonNull String user_name, @NonNull String password);


    /**
     * 获取当前登录者的详细资料
     *
     * @return 用户详情
     */
    Observable<UserDetail> getMe();

    /**
     * 是否登录
     *
     * @return 是否登录
     */
    boolean isLogin();
    /**
     * 是否登录
     *
     * @return 是否登录
     */
    void logout();

}
