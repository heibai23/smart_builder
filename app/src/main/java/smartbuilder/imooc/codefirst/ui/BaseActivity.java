package smartbuilder.imooc.codefirst.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * 创建时间： 2017/4/26.
 * 描述：Activity 基类--不需要 绑定布局
 */

/**主要做的事情：---所有页面统一有的东西
 * 1、统一的属性
 * 2、统一的方法
 * 3、统一的接口
 *
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //显示返回键
    }

    //菜单栏操作--监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:  //返回键的id
                finish();
              break;
        }
        return super.onOptionsItemSelected(item);
    }
}
