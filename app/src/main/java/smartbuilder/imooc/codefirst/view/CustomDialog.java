package smartbuilder.imooc.codefirst.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import smartbuilder.imooc.codefirst.R;

/**
 * 创建时间： 2017/5/2.
 * 描述：自定义Dialog
 */

public class CustomDialog extends Dialog {
        //定义模板
    public CustomDialog(Context context,int layout,int style) {
       this(context, WindowManager.LayoutParams.WRAP_CONTENT,
               WindowManager.LayoutParams.WRAP_CONTENT,layout,style, Gravity.CENTER);
    }
        //定义属性

    public CustomDialog(Context context,int width,int height,int layout,int style,
                        int gravity,int anim){
        super(context, style);

        setContentView(layout);
            //取得window
        Window window=getWindow();
            //取得布局参数
        WindowManager.LayoutParams layoutParams=window.getAttributes();
        layoutParams.width=width;
        layoutParams.height=height;
        layoutParams.gravity=gravity;
            //设置布局参数
        window.setAttributes(layoutParams);
        window.setWindowAnimations(anim);       //布局动画
    }
        //实例
    public CustomDialog(Context context,int width,int height,int layout,int style,
                        int gravity){
        this(context,width,height,layout,style,gravity,R.style.pop_anim_style);


    }
}
