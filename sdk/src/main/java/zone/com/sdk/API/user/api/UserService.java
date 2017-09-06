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

package zone.com.sdk.API.user.api;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import zone.com.sdk.API.topic.bean.Topic;

interface UserService {

    //--- user list --------------------------------------------------------------------------------

    /**
     * 用户收藏的话题列表
     *
     * @param login_name 登录用户名(非昵称)
     * @param offset     偏移数值，默认值 0
     * @param limit      数量极限，默认值 20，值范围 1..150
     * @return 话题列表
     */
    @GET("users/{login}/favorites.json")
    Call<List<Topic>> getUserCollectionTopicList(@Path("login") String login_name,
                                                 @Query("offset") Integer offset, @Query("limit") Integer limit);


    /**
     * 获取用户创建的话题列表
     *
     * @param login_name 登录用户名(非昵称)
     * @param order      类型 默认 recent，可选["recent", "likes", "replies"]
     * @param offset     偏移数值，默认值 0
     * @param limit      数量极限，默认值 20，值范围 1..150
     * @return 话题列表
     */
    @GET("users/{login}/topics.json")
    Call<List<Topic>> getUserCreateTopicList(@Path("login") String login_name, @Query("order") String order,
                                             @Query("offset") Integer offset, @Query("limit") Integer limit);


}
