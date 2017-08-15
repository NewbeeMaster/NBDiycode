package zone.com.sdk.API.file.api;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;

/**
 * [2017] by Zone
 */

public interface FileService {

    @Multipart
    @POST("http://server.jeasonlzy.com/OkHttpUtils/upload")
    Call<String> uploadFile(@Part MultipartBody.Part file);


    @Multipart
    @POST("http://server.jeasonlzy.com/OkHttpUtils/upload")
    Call<String> uploadFiles(@PartMap() Map<String, RequestBody> maps);

    @GET("http://down.360safe.com/360/inst.exe")
    @Streaming //当心大文件：请使用@Streaming！更高效
    Call<ResponseBody> downLoad();
}
