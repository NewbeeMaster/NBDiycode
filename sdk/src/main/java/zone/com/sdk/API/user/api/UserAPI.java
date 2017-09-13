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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Path;
import zone.com.retrofitlib.callwrapper.DialogCall;
import zone.com.sdk.API.login.bean.UserDetail;
import zone.com.sdk.API.topic.bean.Topic;


public interface UserAPI {


    //--- user list --------------------------------------------------------------------------------

    /**
     * 用户收藏的话题列表
     *
     * @param login_name 登录用户名(非昵称)
     * @param offset     偏移数值，默认值 0
     * @param limit      数量极限，默认值 20，值范围 1..150
     */
    DialogCall<List<Topic>> getUserCollectionTopicList(@NonNull String login_name,
                                                       @Nullable Integer offset, @Nullable Integer limit);


    /**
     * 获取用户创建的话题列表
     *
     * @param login_name 登录用户名(非昵称)
     * @param order      类型 默认 recent，可选["recent", "likes", "replies"]
     * @param offset     偏移数值，默认值 0
     * @param limit      数量极限，默认值 20，值范围 1..150
     */
    DialogCall<List<Topic>> getUserCreateTopicList(@NonNull String login_name, @Nullable String order,
                                                   @Nullable Integer offset, @Nullable Integer limit);


    /**
     * 获取用户详细资料
     *
     * @param login_name 登录用户名(非昵称)
     * @return 用户详情
     */
    DialogCall<UserDetail> getUser(String login_name);
}
