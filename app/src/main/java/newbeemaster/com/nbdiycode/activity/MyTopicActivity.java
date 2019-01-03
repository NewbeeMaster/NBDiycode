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
 * Last modified 2017-04-09 20:47:16
 *
 * GitHub:  https://github.com/GcsSloop
 * Website: http://www.gcssloop.com
 * Weibo:   http://weibo.com/GcsSloop
 */

package newbeemaster.com.nbdiycode.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.zone.adapter3.QuickRcvAdapter;
import com.zone.adapter3.base.IAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ezy.ui.layout.LoadingLayout;
import newbeemaster.com.nbdiycode.R;
import newbeemaster.com.nbdiycode.activity.common.BaseNBActivity;
import newbeemaster.com.nbdiycode.constant.SPConstant;
import newbeemaster.com.nbdiycode.adapter.TopicListDelegates;
import newbeemaster.com.nbdiycode.event.DataUpdateEvent;
import newbeemaster.com.nbdiycode.utils.SP1;
import retrofit2.Call;
import zone.com.sdk.API.login.bean.UserDetail;
import zone.com.sdk.API.topic.bean.Topic;
import zone.com.sdk.Diycode;
import zone.com.zhelper.ZonePullView;
import zone.com.zrefreshlayout.ZRefreshLayout;

public class MyTopicActivity extends BaseNBActivity {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    ZRefreshLayout refresh;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;

    private IAdapter<Topic> adapter;
    private List<Topic> datas = new ArrayList<>();
    private InfoType type;


    public enum InfoType  implements Serializable{
        MY_TOPIC("我的帖子"), MY_COLLECT("我的收藏");
        private String content;

        InfoType(String content) {
            this.content = content;
        }

        public Call<List<Topic>> request(int offset, int limit) {
            UserDetail userDetail = SP1.INSTANCE.get(SPConstant.USER_DETAIL, UserDetail.class);
            Call<List<Topic>> result = null;
            switch (content) {
                case "我的帖子":
                    result = Diycode.getInstance()
                            .getUserCreateTopicList(userDetail.getLogin(), null, offset, limit);
                    break;
                case "我的收藏":
                    result = Diycode.getInstance()
                            .getUserCollectionTopicList(userDetail.getLogin(), offset, limit);
                    break;
            }
            return result;
        }

    }


    @Override
    public void setContentView() {
        setContentView(R.layout.a_common_my);
        registerEventBus();
         type = (InfoType) getIntent().getSerializableExtra("type");
    }

    @Override
    public void initData() {
        setTitle(type.content);

        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new QuickRcvAdapter<>(this, datas);
        adapter.addViewHolder(new TopicListDelegates())
                .relatedList(rv);

        final ZonePullView zonePullView = new ZonePullView<List<Topic>>(refresh, adapter) {
            @NonNull
            @Override
            protected Call<List<Topic>> request(int offset, int limit) {
                return type.request(offset, limit);
            }

            @Override
            protected void handleData(int offset, List<Topic> body) {
                datas.addAll(body);
            }
        };
        zonePullView.firstLoading(0, LoadingLayout.wrap(llRoot));

    }


    @Override
    public void setListener() {

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public synchronized void onDataUpdateEventEvent(DataUpdateEvent event) {
        for (Topic data : datas) {
            if(data.getId()==event.topicId)
                data.setReplies_count(data.getReplies_count()+1);
        }
        adapter.notifyDataSetChanged();
    }

}
