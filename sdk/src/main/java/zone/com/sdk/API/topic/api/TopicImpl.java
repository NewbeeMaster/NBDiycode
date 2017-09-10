package zone.com.sdk.API.topic.api;

import android.support.annotation.Nullable;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import zone.com.retrofit.callwrapper.DialogCall;
import zone.com.sdk.API.topic.bean.Topic;
import zone.com.sdk.API.topic.bean.TopicContent;
import zone.com.sdk.API.topic.bean.TopicReply;
import zone.com.sdk.base.BaseImpl;

public class TopicImpl extends BaseImpl<TopicService> implements TopicAPI {


    @Override
    public Call<List<Topic>> getTopicsList(String type, @Nullable Integer node_id, @Nullable Integer offset, @Nullable Integer limit) {
        return dialogWrapper(mService.getTopicsList(type, node_id, offset, limit));
    }

    @Override
    public DialogCall<TopicContent> getTopic(int id) {
        return dialogWrapper(mService.getTopic(id));
    }

    @Override
    public DialogCall<List<TopicReply>> getTopicRepliesList(int id, Integer offset, Integer limit) {
        return  dialogWrapper(mService.getTopicRepliesList(id, offset, limit));
    }

    @Override
    public Observable<TopicReply> createTopicReply(int id, String body) {
        return mService.createTopicReply(id,body);
    }

}