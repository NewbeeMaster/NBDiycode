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

package zone.com.sdk.API.notifications.api;

import android.support.annotation.NonNull;

import java.util.List;

import retrofit2.Call;
import zone.com.retrofitlib.callwrapper.DialogCall;
import zone.com.sdk.API.notifications.bean.Notification;


public interface NotificationsAPI {

    /**
     * 获取通知列表
     *
     * @param offset 偏移数值，默认值 0
     * @param limit  数量极限，默认值 20，值范围 1..150
     */
    DialogCall<List<Notification>> getNotificationsList(@NonNull Integer offset, @NonNull Integer limit);

    /**
     * 获得未读通知的数量
     *
     */
    String getNotificationUnReadCount();

    /**
     * 将某些通知标记为已读
     *
     * @param ids id集合
     */
    String markNotificationAsRead(int[] ids);

    /**
     * 删除用户的某条通知
     *
     * @param id id
     */
    String deleteNotionfition(@NonNull Integer id);

    /**
     * 删除当前用户的所有通知
     *
     */
    String deleteAllNotification();
}
