package smartbuilder.imooc.codefirst.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import smartbuilder.imooc.codefirst.R;
import smartbuilder.imooc.codefirst.utils.L;

/**
 * 创建时间： 2017/4/28.
 * 描述：引导页
 */

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {
    
    private ViewPager mViewPager;
    private List<View> mList;
    private View view1,view2,view3;     //三个引导页

    private ImageView point1_iv,point2_iv,point3_iv;    //小点
    private ImageView skip_iv;  //跳过

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        
        initView();
        
    }
/**初始化View   */
    private void initView() {
        point1_iv= (ImageView) findViewById(R.id.iv_point1);
        point2_iv= (ImageView) findViewById(R.id.iv_point2);
        point3_iv= (ImageView) findViewById(R.id.iv_point3);
            //设置初始的圆点的默认图片
        setPointImg(true,false,false);

        mViewPager= (ViewPager) findViewById(R.id.mViewPager);
        mList=new ArrayList<>();
        view1=View.inflate(this,R.layout.pager_item_one,null);
        view2=View.inflate(this,R.layout.pager_item_two,null);
        view3=View.inflate(this,R.layout.pager_item_three,null);
        mList.add(view1);
        mList.add(view2);
        mList.add(view3);
            //按钮点击事件
        view3.findViewById(R.id.btn_start).setOnClickListener(this);
            //跳过
        skip_iv= (ImageView) findViewById(R.id.iv_skip);
        skip_iv.setOnClickListener(this);


        //设置适配器
        mViewPager.setAdapter(new GuideAdapter());
            //监听ViewPager事件
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
        //pager 切换
            @Override
            public void onPageSelected(int position) {
                L.i("position:"+position);
                switch (position){
                    case 0:
                        setPointImg(true,false,false);
                        skip_iv.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        setPointImg(false,true,false);
                        skip_iv.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        setPointImg(false,false,true);
                        skip_iv.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
 /**点击事件   */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.iv_skip:
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
    }

    private class GuideAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
            //初始化
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mList.get(position));
            return mList.get(position);
        }
            //删除
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList.get(position));
        }
    }
        //设置小圆点的图片--根据选中的状态
    private void setPointImg(boolean isCheck1 ,boolean isCheck2 ,boolean isCheck3){
        if (isCheck1){
            point1_iv.setBackgroundResource(R.drawable.point_on);
        } else {
            point1_iv.setBackgroundResource(R.drawable.point_off);
          }

        if (isCheck2){
            point2_iv.setBackgroundResource(R.drawable.point_on);
        } else {
            point2_iv.setBackgroundResource(R.drawable.point_off);
        }

        if (isCheck3){
            point3_iv.setBackgroundResource(R.drawable.point_on);
        } else {
            point3_iv.setBackgroundResource(R.drawable.point_off);
        }

    }
}
