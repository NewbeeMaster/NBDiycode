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
 * Last modified 2017-03-08 06:45:28
 *
 * GitHub:  https://github.com/GcsSloop
 * Website: http://www.gcssloop.com
 * Weibo:   http://weibo.com/GcsSloop
 */

package newbeemaster.com.nbdiycode.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * 当 RecyclerView 外围嵌套 ScrollView 时，将滚动事件交予上层处理
 */
public class RecyclerViewUtil {

    /**
     * http://blog.csdn.net/u010839880/article/details/52672489
     * 但是在使用NestedScrollView嵌套RecyclerView的时候会发现我们在RecyclerView上滑动的时候没有了滚动的效果，查看文档找到的解决办法：
     * @param layoutManager
     * @param recyclerView
     */
    public static void soomthScroll(LinearLayoutManager layoutManager, RecyclerView recyclerView){
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }
}
