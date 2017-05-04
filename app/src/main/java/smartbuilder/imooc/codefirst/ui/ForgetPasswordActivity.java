package smartbuilder.imooc.codefirst.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import smartbuilder.imooc.codefirst.R;
import smartbuilder.imooc.codefirst.entity.MyUser;

/**
 * 创建时间： 2017/5/1.
 * 描述：忘记密码界面
 */

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private EditText emil_et;
    private EditText nowPass_et;
    private EditText newPass1_et;
    private EditText newPass2_et;

    private Button forgetPass_btn;
    private Button updatePass_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        initView();
    }

    private void initView() {
        emil_et= (EditText) findViewById(R.id.et_emil_two);
        forgetPass_btn= (Button) findViewById(R.id.btn_forget_pass);
        updatePass_btn= (Button) findViewById(R.id.btn_update_pass);

        nowPass_et= (EditText) findViewById(R.id.et_now_pass);
        newPass1_et= (EditText) findViewById(R.id.et_new_pass1);
        newPass2_et= (EditText) findViewById(R.id.et_new_pass2);

        updatePass_btn.setOnClickListener(this);
        forgetPass_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_forget_pass:
                    //1、获取输入框密码 2、判断是否为空
                final String emil=emil_et.getText().toString().trim();
                if(!TextUtils.isEmpty(emil)){
                        //发送邮件--无异常则发送成功
                    MyUser.resetPasswordByEmail(emil, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(getApplicationContext(),
                                        "邮件已发送到："+emil,Toast.LENGTH_LONG).show();
                                finish();
                            } else{
                                Toast.makeText(getApplicationContext(),
                                        "邮件发送失败："+e.toString(),Toast.LENGTH_LONG).show();
                             }
                        }
                    });

                }  else {
                    Toast.makeText(this,"邮箱不能为空",Toast.LENGTH_LONG).show();

                 }
                break;
            case R.id.btn_update_pass:
                    //1、获取输入框值
                String nowPass=nowPass_et.getText().toString().trim();
                String newPass1=newPass1_et.getText().toString().trim();
                String newpass2=newPass2_et.getText().toString().trim();
                    //2、判断是否为空
                if(!TextUtils.isEmpty(nowPass) & !TextUtils.isEmpty(newPass1) &
                        !TextUtils.isEmpty(newpass2)){
                        //3、判断两次输入是否一致
                    if(newPass1.equals(newpass2)){
                            //4、修改密码
                        MyUser.updateCurrentUserPassword(nowPass, newPass1, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Toast.makeText(ForgetPasswordActivity.this,
                                            "修改成功",Toast.LENGTH_LONG).show();
                                    finish();
                                }else{
                                    Toast.makeText(ForgetPasswordActivity.this,
                                            "修改失败"+e.toString(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    } else {
                        Toast.makeText(this,"两次输入的不一致",Toast.LENGTH_LONG).show();
                     }

                } else {
                    Toast.makeText(this,"不能为空",Toast.LENGTH_LONG).show();
                 }

                break;
        }
    }
}
