package smartbuilder.imooc.codefirst.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;
import smartbuilder.imooc.codefirst.R;
import smartbuilder.imooc.codefirst.entity.MyUser;
import smartbuilder.imooc.codefirst.ui.CourierActivity;
import smartbuilder.imooc.codefirst.ui.LocationActivity;
import smartbuilder.imooc.codefirst.ui.LoginActivity;
import smartbuilder.imooc.codefirst.utils.L;
import smartbuilder.imooc.codefirst.utils.SharedUtil;
import smartbuilder.imooc.codefirst.view.CustomDialog;

import static smartbuilder.imooc.codefirst.utils.StaticClass.CAMERA_REQUEST_CODE;
import static smartbuilder.imooc.codefirst.utils.StaticClass.PICTURE_REQUEST_CODE;
import static smartbuilder.imooc.codefirst.utils.StaticClass.ZOOM_REQUEST_CODE;

/**
 * 创建时间： 2017/4/26.
 * 描述：个人中心
 */

public class UserFragment extends Fragment implements View.OnClickListener {

    private EditText name_et;
    private EditText age_et;
    private EditText sex_et;
    private EditText desc_et;   //简介

    private TextView edit_tv;       //编辑资料
    private TextView courier_tv;      //快递查询
    private TextView location_tv;    //归属地查询

    private Button update_btn;  //修改资料
    private Button exit_btn;        //退出登录
    private Button camera_btn;  //拍照
    private Button picture_btn;     //相册
    private Button cancel_btn;  //取消


    private CustomDialog cirleImage_dialog;     //对话框---圆形头像
    private CircleImageView cirleImage_civ; //圆形头像
    private File photoFile=null;    //取得拍照文件

    private CustomDialog loadingDialog;     //正在加载的对话框


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.userfragment,null);
        findView(view);
        return view;
    }
 /**       初始化View --传入view才能控件实例化      */
    private void findView(View view) {
        name_et= (EditText) view.findViewById(R.id.et_name_frag);
        age_et= (EditText) view.findViewById(R.id.et_age_frag);
        sex_et= (EditText) view.findViewById(R.id.et_sex_frag);
        desc_et= (EditText) view.findViewById(R.id.et_desc_frag);

        update_btn= (Button) view.findViewById(R.id.btn_update_frag);
        exit_btn= (Button) view.findViewById(R.id.btn_exit_frag);

        cirleImage_civ= (CircleImageView) view.findViewById(R.id.civ_circle_image);

        cirleImage_civ.setOnClickListener(this);
            //dialog
        cirleImage_dialog=new CustomDialog(getActivity(), WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                    R.layout.dialog_cirle_image,R.style.Theme_dialog,
                        Gravity.BOTTOM,R.style.pop_anim_style);
            //屏幕外点击无效
        cirleImage_dialog.setCancelable(false);
            //dialog里面的按钮

        camera_btn= (Button) cirleImage_dialog.findViewById(R.id.btn_camera);
        picture_btn= (Button) cirleImage_dialog.findViewById(R.id.btn_picture);
        cancel_btn= (Button) cirleImage_dialog.findViewById(R.id.btn_cancel);

        edit_tv= (TextView) view.findViewById(R.id.tv_edit_frag);
        courier_tv = (TextView) view.findViewById(R.id.tv_Courior);
        location_tv= (TextView) view.findViewById(R.id.tv_location); //归属地查询

        //初始化EditText中的个人信息--具体信息
        MyUser userInfo= BmobUser.getCurrentUser(MyUser.class);
        name_et.setText(userInfo.getUsername());
        age_et.setText(userInfo.getAge()+"");
        sex_et.setText(userInfo.isSex()? "男" : "女");
        desc_et.setText(userInfo.getDesc());

            //点击事件
        edit_tv.setOnClickListener(this);
        courier_tv.setOnClickListener(this);
        location_tv.setOnClickListener(this);

        update_btn.setOnClickListener(this);
        exit_btn.setOnClickListener(this);

        cirleImage_civ.setOnClickListener(this);
        camera_btn.setOnClickListener(this);
        picture_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);
            //默认不能编辑、修改按钮不可见
        setEnable(false);
        update_btn.setVisibility(View.GONE);
    //初始设置、恢复头像
        SharedUtil.setImageFromShare(getActivity(),cirleImage_civ);

            //正在加载的对话框
        loadingDialog=new CustomDialog(getActivity(), WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,R.layout.dialog_loding,
                R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
        //屏幕外点击无效
        loadingDialog.setCancelable(false);

    }
  /**设置编辑框是否可以编辑            */
    private void setEnable(boolean isEnabled) {

        name_et.setEnabled(isEnabled);
        age_et.setEnabled(isEnabled);
        sex_et.setEnabled(isEnabled);
        desc_et.setEnabled(isEnabled);
    }

  /**  监听点击事件             */
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.tv_edit_frag:
                    //编辑资料、修改按钮可见
                setEnable(true);
                update_btn.setVisibility(View.VISIBLE);

                break;
            case R.id.btn_update_frag:
                    //1、取得值
                String name=name_et.getText().toString().trim();
                String age=age_et.getText().toString().trim();
                String sex=sex_et.getText().toString().trim();
                String desc=desc_et.getText().toString().trim();
                    //2、判断是否为空
                if(!TextUtils.isEmpty(name) &!TextUtils.isEmpty(age) &!TextUtils.isEmpty(sex)){
                        //3、更新--新建用户对象
                    MyUser newUser=new MyUser();
                    newUser.setUsername(name);
                    newUser.setAge(Integer.parseInt(age));
                    if(sex.equals("男")){
                        newUser.setSex(true);
                    } else  if(sex.equals("女")){
                        newUser.setSex(false);
                     } else {
                        Toast.makeText(getActivity(),"请正确输入男或女",Toast.LENGTH_LONG).show();
                        break;
                    }
                        //简介
                    if(!TextUtils.isEmpty(desc)){
                        newUser.setDesc(desc);
                    } else {
                        newUser.setDesc("这个人很懒，什么都没有留下");
                    }
                    BmobUser bmobUser=BmobUser.getCurrentUser();
                    newUser.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(getActivity(),"修改完成",Toast.LENGTH_LONG).show();
                                setEnable(false);
                                update_btn.setVisibility(View.GONE);
                            }else{
                                Toast.makeText(getActivity(),"修改失败"+e.toString(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(getActivity(),"不能为空",Toast.LENGTH_LONG).show();
                }

                break;
                //退出登录
            case R.id.btn_exit_frag:
                MyUser.logOut();   //清除缓存用户对象
                BmobUser currentUser = BmobUser.getCurrentUser(); // 现在的currentUser是null了
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
                //头像
            case R.id.civ_circle_image:
                cirleImage_dialog.show();

                break;
                //dialog里按钮--拍照、相册、取消
            case R.id.btn_camera:
                loadingDialog.show();   //显示加载
                toCamera();
                break;
            case R.id.btn_picture:
                loadingDialog.show();   //显示加载
                toPicture();
                break;
            case R.id.btn_cancel:

                cirleImage_dialog.dismiss();
                break;
                //快递查询
            case R.id.tv_Courior:

                startActivity(new Intent(getActivity(), CourierActivity.class));
                break;
            case R.id.tv_location:
                startActivity(new Intent(getActivity(), LocationActivity.class));
                break;
        }

    }

    /**         跳转到拍照   */
    private void toCamera() {
        loadingDialog.dismiss();    //关闭加载框
            //跳转相机的action
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //判断内存卡是否可用，可用的话就进行储存
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(
                new File(Environment.getExternalStorageDirectory(),"fileImgName.jpg")));
        startActivityForResult(intent,CAMERA_REQUEST_CODE);

        cirleImage_dialog.dismiss();

    }
    /**         跳转到相册   */
    private void toPicture() {
        loadingDialog.dismiss();    //关闭加载框

        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,PICTURE_REQUEST_CODE);
        cirleImage_dialog.dismiss();

    }
    /**     对请求返回的结果进行操作的方法  */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
            //返回码是否有效
        if(requestCode!=getActivity().RESULT_CANCELED){
            switch (requestCode){
                case CAMERA_REQUEST_CODE:
                        //相机数据---Uri类型

                    photoFile=new File(Environment.getExternalStorageDirectory(),
                            "fileImgName.jpg");
                    startPhotoZoom(Uri.fromFile(photoFile));


                    break;

                case PICTURE_REQUEST_CODE:
                        //相册数据
                    Uri uri=data.getData();
                    startPhotoZoom(uri);
                    break;
                //裁剪后数据
                case ZOOM_REQUEST_CODE:
                        //有可能舍弃
                    if(data!=null){
                            //拿到图片设置
                        setImageToView(data);
                            //将原先的图片舍弃回收
                        if(photoFile!=null){
                            photoFile.delete();
                        }
                    }
                    break;
            }
        }
    }
    /**将图片设置到头像     */
    private void setImageToView(Intent data) {
        Bundle bundle=data.getExtras();
        if(bundle!=null){
            Bitmap bitmap=bundle.getParcelable("data");
            cirleImage_civ.setImageBitmap(bitmap);
        }
    }

    /**  裁剪图片---发送  */
    private void startPhotoZoom(Uri uri) {
        if(uri==null){
            L.i("uri==null");
            return;
        }
            //裁剪action
        Intent intent=new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
            //设置裁剪
        intent.putExtra("crop",true);
            //裁剪宽高比例
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
            //裁剪图片质量--大小
        intent.putExtra("outputX",230);
        intent.putExtra("outputY",230);
        intent.putExtra("return-data",true);    //数据

        startActivityForResult(intent,ZOOM_REQUEST_CODE);

    }
/**保存头像信息   */
    @Override
    public void onDestroy() {
        super.onDestroy();
            //保存头像
        SharedUtil.putImageToShare(getActivity(),cirleImage_civ);
    }
}
