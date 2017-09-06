package newbeemaster.com.nbdiycode;

import android.app.Application;
import android.graphics.Color;

import com.socks.library.ZLog;
import com.zone.lib.Configuration;

import zone.com.zrefreshlayout.Config;
import zone.com.zrefreshlayout.footer.NullFooter;
import zone.com.zrefreshlayout.header.MeterialHead;
import zone.com.zrefreshlayout.resistance.DampingHalf;

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
        newbeemaster.com.nbdiycode.util.Config.init(this);
        zone.com.retrofitlib.Config.getInstance().setContext(this);

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
                .setPinContent(true)
//                .setHeader(new  CircleRefresh())
                .setResistance(new newbeemaster.com.nbdiycode.config.zrefresh.DampingTo3Head())
                .writeLog(isDebug)
                .perform();
    }
}
