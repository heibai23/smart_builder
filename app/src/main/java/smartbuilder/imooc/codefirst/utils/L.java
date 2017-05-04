package smartbuilder.imooc.codefirst.utils;

import android.util.Log;

/**
 * 创建时间： 2017/4/27.
 * 描述：Log 封装类
 */

public class L {
        //开关--是否为测试模式
    public static final boolean DEBUG=true;
        //TAG
    public static final String TAG="SmartBuilder";

        //五个等级的封装方法
    public static void d(String text){
        if(DEBUG){
            Log.d(TAG,text);
        }
    }
    public static void i(String text){
        if(DEBUG){
            Log.i(TAG,text);
        }
    }
    public static void w(String text){
        if(DEBUG){
            Log.w(TAG,text);
        }
    }
    public static void e(String text){
        if(DEBUG){
            Log.e(TAG,text);
        }
    }
    public static void v(String text){
        if(DEBUG){
            Log.v(TAG,text);
        }
    }
}
