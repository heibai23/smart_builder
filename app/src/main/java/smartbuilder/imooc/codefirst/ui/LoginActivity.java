package smartbuilder.imooc.codefirst.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import smartbuilder.imooc.codefirst.MainActivity;
import smartbuilder.imooc.codefirst.R;
import smartbuilder.imooc.codefirst.entity.MyUser;
import smartbuilder.imooc.codefirst.utils.SharedUtil;
import smartbuilder.imooc.codefirst.utils.StaticClass;
import smartbuilder.imooc.codefirst.view.CustomDialog;

/**
 * 创建时间： 2017/4/30.
 * 描述：登录界面
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button register_btn;    //注册
    private Button login_btn;

    private EditText username_et;
    private EditText password_et;

    private CheckBox keep_pass_cb;

    private TextView forget_pass_tv;

    private CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {

        login_btn= (Button) findViewById(R.id.btn_login);
        register_btn= (Button) findViewById(R.id.btn_register);

        username_et= (EditText) findViewById(R.id.et_username);
        password_et= (EditText) findViewById(R.id.et_password);

        keep_pass_cb= (CheckBox) findViewById(R.id.cb_keep_pass);

        forget_pass_tv= (TextView) findViewById(R.id.tv_forget_pass);
            //width 单位
        dialog=new CustomDialog(this, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,R.layout.dialog_loding,
                    R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
            //屏幕外点击无效
        dialog.setCancelable(false);

        //监听事件
        login_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);
        forget_pass_tv.setOnClickListener(this);

            //设置选中状态--默认不选中
        boolean isChecked=SharedUtil.getBoolean(this,StaticClass.KEEP_PASS,false);
        keep_pass_cb.setChecked(isChecked); //记住上一次的选中状态
        if(isChecked){
                //设置密码
            username_et.setText(SharedUtil.getString(this,StaticClass.USER_NAME,""));
            password_et.setText(SharedUtil.getString(this,StaticClass.USER_PASSWORD,""));

        } else {

         }
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){
            case R.id.btn_register:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
         //登录
            case R.id.btn_login:
                    //1、获取输入框的值
                String name=username_et.getText().toString();
                String password=password_et.getText().toString();
                    //2、判断是否为空
                if(!TextUtils.isEmpty(name) & !TextUtils.isEmpty(password)){
                        //显示loading
                    dialog.show();

                    final MyUser user=new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                        //3、登录方法--没有产生异常代表 成功
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                                //4、判断结果
                            //隐藏dialog
                            Thread thread=new Thread();
                            try {
                                thread.sleep(100);
                            } catch (InterruptedException e1) {
                                e.printStackTrace();
                            }
                            dialog.dismiss();
                            if(e==null){

                                    //邮箱验证
                                if(user.getEmailVerified()){
                                        //跳转
                                    startActivity(new Intent(getApplicationContext(),
                                            MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(),"请前往邮箱验证",
                                            Toast.LENGTH_LONG).show();
                                 }

                            } else {
                                int code=e.getErrorCode();
                                if (code==101) {
                                    Toast.makeText(LoginActivity.this, "用户名或密码错误",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(LoginActivity.this, "登录失败："+e.toString(),
                                            Toast.LENGTH_LONG).show();
                                  }
                             }
                        }
                    });

                } else{
                    Toast.makeText(this,"不能为空",Toast.LENGTH_LONG).show();
                  }

                break;
            case R.id.tv_forget_pass:
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
        }
    }
        //假设我现在登录用户名和密码，但是没点击登录
    @Override
    protected void onDestroy() {
        super.onDestroy();
            //保存状态
        SharedUtil.putBoolean(this,StaticClass.KEEP_PASS,keep_pass_cb.isChecked());

            //是否记住密码
        if(keep_pass_cb.isChecked()){

                //记住输入框--用户名密码
            SharedUtil.putString(this, StaticClass.USER_NAME,
                    username_et.getText().toString().trim());
            SharedUtil.putString(this,StaticClass.USER_PASSWORD,
                    password_et.getText().toString().trim());
        } else {
                //清除
            SharedUtil.deleteShared(this,StaticClass.USER_NAME);
            SharedUtil.deleteShared(this,StaticClass.USER_PASSWORD);
         }
    }
}
