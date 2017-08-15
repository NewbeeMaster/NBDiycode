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

package zone.com.sdk.API.gank2.api;
import zone.com.sdk.API.gank.bean.MeiZiData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import zone.com.retrofit.callwrapper.DialogCall;

 interface Gank2Service {

    //--- Token ------------------------------------------------------------------------------------

    /**
     * 获取 Token (一般在登录时调用)
     *
     * @return Token 实体类
     */
    @GET("{limit}/{pageNumber}")
    Call<MeiZiData> getPics(@Path("limit") String limit, @Path("pageNumber") String pageNumber);

}
