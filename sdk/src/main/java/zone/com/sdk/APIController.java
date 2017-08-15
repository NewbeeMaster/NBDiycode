package zone.com.sdk;

import zone.com.sdk.API.gank.api.GankAPI;
import zone.com.sdk.API.gank.api.GankImpl;
import zone.com.sdk.API.gank.bean.MeiZiData;
import zone.com.retrofit.callwrapper.DialogCall;

/**
 * [2017] by Zone
 */

public class APIController implements GankAPI {

    private static GankImpl mGankImpl;
    private static APIController instance;

    static {
        instance = new APIController();
        mGankImpl = new GankImpl();
    }

    public static APIController getSingleInstance() {
        return instance;
    }


    @Override
    public DialogCall<MeiZiData> getPics(String limit, String pageNumber) {
        return mGankImpl.getPics(limit, pageNumber);
    }
}
