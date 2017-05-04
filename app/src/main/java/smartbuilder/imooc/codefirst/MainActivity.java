package smartbuilder.imooc.codefirst;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import smartbuilder.imooc.codefirst.fragment.ButlerFragment;
import smartbuilder.imooc.codefirst.fragment.GirlFragment;
import smartbuilder.imooc.codefirst.fragment.UserFragment;
import smartbuilder.imooc.codefirst.fragment.WechatFragment;
import smartbuilder.imooc.codefirst.ui.SettingActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String> mTitle;    //标题文字
    private List<Fragment>mFragment;  //v4包下的，上下兼容

    private FloatingActionButton fab_setting;   //悬浮窗--按钮


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //去掉  标题栏的顶部横线（阴影）
        getSupportActionBar().setElevation(0);
        initData();
        initView();
            //Bugly统计测试
       // CrashReport.testJavaCrash();

    }

    //初始化View
    private void initData() {
        //标题栏内容
        mTitle=new ArrayList<>();
        mTitle.add("服务管家");
        mTitle.add("微信精选");
        mTitle.add("美女社区");
        mTitle.add("个人中心");

        //fragment  内容
        mFragment=new ArrayList<>();
        mFragment.add(new ButlerFragment());
        mFragment.add(new WechatFragment());
        mFragment.add(new GirlFragment());
        mFragment.add(new UserFragment());
    }
 /**初始化数据     */
    private void initView() {
     //控件实例化
        mTabLayout= (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager= (ViewPager) findViewById(R.id.mViewPager);
        fab_setting= (FloatingActionButton) findViewById(R.id.fab_setting);
            //默认隐藏--首页
        fab_setting.setVisibility(View.GONE);
        fab_setting.setOnClickListener(this);

            //预加载
        mViewPager.setOffscreenPageLimit(mFragment.size());
            //滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
                //选中的页卡
            @Override
            public void onPageSelected(int position) {
                Log.i("TAG", "onPageSelected: "+position);
                    //首页是没有设置悬浮按钮的
                if(position==0){
                    fab_setting.setVisibility(View.GONE);
                } else{
                    fab_setting.setVisibility(View.VISIBLE);
                  }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
            //设置适配器
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                //选中的Item
            @Override
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }
                //返回Item的个数
            @Override
            public int getCount() {
                return mFragment.size();
            }
                //设置标题
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });
            //绑定
        mTabLayout.setupWithViewPager(mViewPager);
    }
  //监听事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }

    }
}
