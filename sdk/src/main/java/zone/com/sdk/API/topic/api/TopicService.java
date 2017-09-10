package zone.com.sdk.API.topic.api;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import zone.com.sdk.API.topic.bean.Topic;
import zone.com.sdk.API.topic.bean.TopicContent;
import zone.com.sdk.API.topic.bean.TopicReply;

interface TopicService {

    //--- topic ------------------------------------------------------------------------------------

    /**
     * 获取 topic 列表
     *
     * @param type    类型，默认 last_actived，可选["last_actived", "recent", "no_reply", "popular", "excellent"]
     * @param node_id 如果你需要只看某个节点的，请传此参数, 如果不传 则返回全部
     * @param offset  偏移数值，默认值 0
     * @param limit   数量极限，默认值 20，值范围 1..150
     * @return topic 列表
     */
    @GET("topics.json")
    Call<List<Topic>> getTopicsList(@Query("type") String type, @Query("node_id") Integer node_id,
                                    @Query("offset") Integer offset, @Query("limit") Integer limit);


    /**
     * 获取 topic 内容
     *
     * @param id topic 的 id
     * @return 内容详情
     */
    @GET("topics/{id}.json")
    Call<TopicContent> getTopic(@Path("id") int id);




    /**
     * 获取 topic 回复列表
     *
     * @param id     topic 的 id
     * @param offset 偏移数值 默认 0
     * @param limit  数量极限，默认值 20，值范围 1...150
     * @return 回复列表
     */
    @GET("topics/{id}/replies.json")
    Call<List<TopicReply>> getTopicRepliesList(@Path("id") int id, @Query("offset") Integer offset,
                                               @Query("limit") Integer limit);


    /**
     * 创建 topic 回帖(回复，评论)
     *
     * @param id   话题列表
     * @param body 回帖内容, Markdown 格式
     * @return 回复详情
     */
    @POST("topics/{id}/replies.json")
    @FormUrlEncoded
    Observable<TopicReply> createTopicReply(@Path("id") int id, @Field("body") String body);
}
