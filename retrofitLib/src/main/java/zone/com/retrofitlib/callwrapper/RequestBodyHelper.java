package zone.com.retrofitlib.callwrapper;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import zone.com.retrofitlib.callwrapper.core.MediaTypeUtils;
import zone.com.retrofitlib.callwrapper.core.ProgressCallback;
import zone.com.retrofitlib.callwrapper.upload.ProgressRequestBody;


/**
 * Created by Zone on 2016/2/10.
 */
public class RequestBodyHelper {
    protected Map<String, String> paramsMap;
    protected Map<String, File> fileMap;
    protected Map<String, String> fileNameMap;
//    protected String jsonStr;
//
//    public String getJsonStr() {
//        return jsonStr;
//    }
//
//    public RequestParams setJsonStr(String jsonStr) {
//        this.jsonStr = jsonStr;
//        return this;
//    }

    private void file2NameMapChecked() {
        if (fileMap == null)
            fileMap = new ConcurrentHashMap<>();
        if (fileNameMap == null)
            fileNameMap = new ConcurrentHashMap<>();
    }

    public RequestBodyHelper put(String key, File file) {
        return put(key, null, file);
    }

    public RequestBodyHelper put(String key, String fileName, File file) {
        file2NameMapChecked();
        fileMap.put(key, file);
        fileNameMap.put(key, fileName == null ? file.getName() : fileName);
        return this;
    }

    public RequestBodyHelper setFileMap(Map<String, File> fileMap) {
        file2NameMapChecked();
        this.fileMap.putAll(fileMap);
        for (Map.Entry<String, File> stringFileEntry : fileMap.entrySet())
            fileNameMap.put(stringFileEntry.getKey(), stringFileEntry.getValue().getName());
        return this;
    }

    public Map<String, File> getFileMap() {
        return fileMap;
    }

    public Map<String, String> getFileNameMap() {
        return fileNameMap;
    }

    private void paramsMapChecked() {
        if (paramsMap == null)
            paramsMap = new ConcurrentHashMap<>();
    }

    public RequestBodyHelper put(String key, String value) {
        paramsMapChecked();
        paramsMap.put(key, value);
        return this;
    }

    public RequestBodyHelper setParamsMap(Map<String, String> paramsMap) {
        paramsMapChecked();
        this.paramsMap.putAll(paramsMap);
        return this;
    }

    public Map<String, String> getParamsMap() {
        return paramsMap;
    }

    public RequestBody createRequestBody(ProgressCallback listener) {
        RequestBody formBody = null;
        if (getFileMap() == null && getFileNameMap() == null) {
            //have not file post
            FormBody.Builder form = new FormBody.Builder();
            for (Map.Entry<String, String> item : getParamsMap().entrySet())
                form.add(item.getKey(), item.getValue());
            formBody = form.build();
        } else {
            //have file post
            MultipartBody.Builder form = new MultipartBody.Builder();
            form.setType(MultipartBody.FORM);
            for (Map.Entry<String, String> item : getParamsMap().entrySet())
                form.addFormDataPart(item.getKey(), item.getValue());
            for (Map.Entry<String, File> item : getFileMap().entrySet()) {
                form.addFormDataPart(item.getKey(), getFileNameMap().get(item.getKey()),
                        RequestBody.create(MediaType.parse(MediaTypeUtils.getFileSuffix(item.getValue())), item.getValue()));
            }

            //requestParams.getmProgressListener()  This method has already been processed to determine whether or not to open the
            formBody = new ProgressRequestBody(form.build(), listener);
        }
        return formBody;
    }
}
