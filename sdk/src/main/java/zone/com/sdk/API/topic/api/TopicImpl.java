package zone.com.sdk.API.topic.api;

import android.support.annotation.Nullable;
import java.util.List;
import retrofit2.Call;
import zone.com.sdk.API.topic.bean.Topic;
import zone.com.sdk.base.BaseImpl;

public class TopicImpl extends BaseImpl<TopicService> implements TopicAPI {


    @Override
    public Call<List<Topic>> getTopicsList(String type, @Nullable Integer node_id, @Nullable Integer offset, @Nullable Integer limit) {
        return dialogWrapper(mService.getTopicsList(type, node_id, offset, limit));
    }
}