package smartbuilder.imooc.codefirst.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import smartbuilder.imooc.codefirst.R;
import smartbuilder.imooc.codefirst.utils.SharedUtil;
import smartbuilder.imooc.codefirst.utils.StaticClass;
import smartbuilder.imooc.codefirst.utils.UtilTools;

import static smartbuilder.imooc.codefirst.utils.StaticClass.HANDLER_SPLASH;

/**
 * 创建时间： 2017/4/27.
 * 描述：闪屏界面
 */

public class SplashActivity extends AppCompatActivity{
    /**
     * 1、延时
     * 2、判断程序是否第一次运行
     * 3、自定义字体
     * 4、Activity全屏主题
     */
    private TextView splash_tv;

        //延时
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case HANDLER_SPLASH:
                    if(isFirst()){
                        startActivity(new Intent(getApplicationContext(),GuideActivity.class));

                    } else{
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                     }
                    finish();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }
/**初始化      */
    private void initView() {

            //发送指定Message，延时2s
        handler.sendEmptyMessageDelayed(HANDLER_SPLASH,2000);
        splash_tv= (TextView) findViewById(R.id.tv_splash);
        UtilTools.setFontType(this,splash_tv);





    }
        //判断是否为第一次打开
    private boolean isFirst() {
            //获取value(是否为第一次)
        boolean isFisrt= SharedUtil.getBoolean(this,StaticClass.SPLASH_IS_FIRST,true);
        if(isFisrt){
            SharedUtil.putBoolean(this,StaticClass.SPLASH_IS_FIRST,false);
            return true;
        } else{

            return false;
         }
    }
        //禁止返回键
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
