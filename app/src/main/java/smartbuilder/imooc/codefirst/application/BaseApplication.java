package smartbuilder.imooc.codefirst.application;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;
import smartbuilder.imooc.codefirst.utils.StaticClass;

/**
 * Created by cheng on 2017/4/26.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
            //初始化Bugly报告 --第三个参数：调试的开关
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);
            //初始化Bmob
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);
    }
}
