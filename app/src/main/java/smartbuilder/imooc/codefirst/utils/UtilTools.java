package smartbuilder.imooc.codefirst.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * 创建时间： 2017/4/26.
 * 描述：工具类
 */

public class UtilTools {

        //设置字体--封装方法
    public static void setFontType(Context mContext, TextView mTextView){
        Typeface fontType=Typeface.createFromAsset(mContext.getAssets(),"fonts/FONT.TTF");
        mTextView.setTypeface(fontType);
    }

}
