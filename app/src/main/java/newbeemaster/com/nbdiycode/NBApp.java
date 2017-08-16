package newbeemaster.com.nbdiycode;

import android.app.Application;
import android.graphics.Color;

import com.socks.library.ZLog;

import and.Configuration;
import and.utils.data.file2io2data.SharedUtils;
import zone.com.zrefreshlayout.Config;
import zone.com.zrefreshlayout.footer.NullFooter;
import zone.com.zrefreshlayout.header.MeterialHead;
import zone.com.zrefreshlayout.resistance.*;

/**
 * [2017] by Zone
 */

public class NBApp extends Application {

    public static final boolean isDebug = BuildConfig.DEBUG;

    @Override
    public void onCreate() {
        super.onCreate();
        initConfig();
    }

    private void initConfig() {
        //todo SHARED_NAME更改成非 final类
//        SharedUtils.SHARED_NAME="NBDiycode";
        Configuration.Build.init(this).perform();

        initRefreshConfig();

        ZLog.config()
//                .addFilter("ga")
                .debug(isDebug);

        //adapter


    }

    private void initRefreshConfig() {
        int[] colors_red_green_yellow = new int[]{
                Color.parseColor("#ffF44336"),
                Color.parseColor("#ff4CAF50"),
                Color.parseColor("#ffFFEB3B")
        };
        Config.build()
                .setHeader(new MeterialHead(colors_red_green_yellow))
                .setFooter(new NullFooter())
                .setResistance(new DampingHalf())
//                .setHeader(new  CircleRefresh())
                .setResistance(new newbeemaster.com.nbdiycode.config.zrefresh.DampingTo3Head())
                .writeLog(isDebug)
                .perform();
    }
}
