package smartbuilder.imooc.codefirst.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import smartbuilder.imooc.codefirst.R;
import smartbuilder.imooc.codefirst.entity.MyUser;

/**
 * 创建时间： 2017/4/30.
 * 描述：注册界面
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText user_et;
    private EditText pass_one_et;
    private EditText pass_two_et;
    private EditText age_et;
    private EditText desc_et;
    private EditText emil_et;

    private RadioGroup mRadioGroup;
    private Button register_two_btn;

    private boolean isGender=true;   //默认男性

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        user_et= (EditText) findViewById(R.id.et_user);
        pass_one_et= (EditText) findViewById(R.id.et_pass_one);
        pass_two_et= (EditText) findViewById(R.id.et_pass_two);
        age_et= (EditText) findViewById(R.id.et_age);
        desc_et= (EditText) findViewById(R.id.et_desc);
        emil_et= (EditText) findViewById(R.id.et_emil);
        mRadioGroup= (RadioGroup) findViewById(R.id.mRadioGroup);
        register_two_btn= (Button) findViewById(R.id.btn_register_two);
            //
        register_two_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register_two:
                    //获取输入框中的信息
                String name=user_et.getText().toString().trim();    //去空格
                String pass_one=pass_one_et.getText().toString().trim();
                String pass_two=pass_two_et.getText().toString().trim();
                String age=age_et.getText().toString().trim();
                String desc=desc_et.getText().toString().trim();
                String emil=emil_et.getText().toString().trim();
                    //判断是否为空
                if(!TextUtils.isEmpty(name) & !TextUtils.isEmpty(pass_one) &
                        !TextUtils.isEmpty(pass_two) & !TextUtils.isEmpty(emil) &
                            !TextUtils.isEmpty(age)){

                        //判断 两次密码输入是否一致
                    if(pass_one.equals(pass_two)) {

                        //先把性别判断一下
                        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                if (checkedId == R.id.rb_boy) {
                                    isGender = true;
                                } else if (checkedId == R.id.rb_girl) {
                                    isGender = false;
                                }
                            }
                        });
                        //判断简介是否为空
                        if (TextUtils.isEmpty(desc)) {
                            desc = "这个人很懒，什么都没有留下";
                        }

                        // 注册
                        MyUser user = new MyUser();
                        user.setUsername(name);
                        user.setDesc(desc);
                        user.setPassword(pass_one);
                        user.setSex(isGender);
                        user.setAge(Integer.parseInt(age));
                        user.setEmail(emil);

                        user.signUp(new SaveListener<MyUser>() {
                            @Override
                            public void done(MyUser myUser, BmobException e) {
                                if(e==null){
                                    Toast.makeText(getApplicationContext(),"注册成功",
                                            Toast.LENGTH_LONG).show();
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(),"注册失败"+e.toString(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    } else {
                        Toast.makeText(this,"两次输入的密码不一致",Toast.LENGTH_LONG).show();
                      }

                } else {
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_LONG).show();
                  }
                break;
        }
    }
}
