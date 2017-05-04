package smartbuilder.imooc.codefirst.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static smartbuilder.imooc.codefirst.utils.StaticClass.IMAGE_TITLE;

/**
 * 创建时间： 2017/4/27.
 * 描述：数据存储封装工具类
 */

public class SharedUtil {

    public static final String NAME="config";   //文件名称
        //存储 --键/值
    public static void putString(Context mContext,String key,String value){
        SharedPreferences sp=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(key,value).commit();
    }
        //取值 --键/默认值
    public static String getString(Context mContext,String key,String defValue){
        SharedPreferences sp=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getString(key,defValue);
    }
    public static void putInt(Context mContext,String key,int value){
        SharedPreferences sp=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt(key,value).commit();
    }
        //---Int
    public static int getInt(Context mContext,String key,int defValue){
        SharedPreferences sp=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getInt(key,defValue);
    }

    public static void putBoolean(Context mContext,String key,boolean value){
        SharedPreferences sp=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean(key,value).commit();
    }
        //---Boolean
    public static boolean getBoolean(Context mContext,String key,boolean defValue){
        SharedPreferences sp=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getBoolean(key,defValue);
    }
        //删除  单个
    public static void deleteShared(Context mContext,String key){
        SharedPreferences sp=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }
        //删除  全部
    public static void deleteAll(Context mContext,String key){
        SharedPreferences sp=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        sp.edit().clear();
    }
        //将设置的头像保存
    public static void putImageToShare(Context context , ImageView imageView){
            //取得图片
        BitmapDrawable drawable= (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap=drawable.getBitmap();
            //1、将bitmap压缩转化为 字节数组输出流
        ByteArrayOutputStream byStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,80 ,byStream);
            //2、字节数组输出流 转化为  String---利用Base
        byte[] byteArray=byStream.toByteArray();
        String imgString =new String(Base64.encodeToString(byteArray,Base64.DEFAULT));
            //3、将String 保存在ShareUtil--工具类
        SharedUtil.putString(context,IMAGE_TITLE,imgString);
    }
        //恢复头像
    public static void setImageFromShare(Context context,ImageView imageView) {
            //1、拿到String
        String imgString = SharedUtil.getString(context, IMAGE_TITLE, "");
        if (imgString != null) {
                //2、将String类型转化为 字节数组流---利用base64
            byte[] byteArray = Base64.decode(imgString, Base64.DEFAULT);
                //3、将字节数组流转化为  字节数组输入流
            ByteArrayInputStream byStream = new ByteArrayInputStream(byteArray);
                //4、将输入流转化为  图片
            Bitmap bitmap = BitmapFactory.decodeStream(byStream);
            imageView.setImageBitmap(bitmap);  //设置头像

        }
    }

}
