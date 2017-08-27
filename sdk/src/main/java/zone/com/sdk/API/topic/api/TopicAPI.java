package zone.com.sdk.API.topic.api;

import android.support.annotation.Nullable;

import java.util.List;

import retrofit2.Call;
import zone.com.sdk.API.topic.bean.Topic;

public interface TopicAPI {
    //--- topic ------------------------------------------------------------------------------------

    /**
     * 获取 topic 列表
     *
     * @param type    类型，默认 last_actived，可选["last_actived", "recent", "no_reply", "popular", "excellent"]
     * @param node_id 如果你需要只看某个节点的，请传此参数, 如果不传 则返回全部
     * @param offset  偏移数值，默认值 0
     * @param limit   数量极限，默认值 20，值范围 1..150
     * @see GetTopicsListEvent
     */
    Call<List<Topic>> getTopicsList(String type, @Nullable Integer node_id, @Nullable Integer offset, @Nullable Integer limit);
}
