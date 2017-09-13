package zone.com.retrofitlib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * [2017] by Zone
 */

public class Utils {
    public static boolean haveNetWork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info == null) {
            return false;
        }
        if (info.getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        return false;
    }
}
