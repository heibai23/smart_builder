package smartbuilder.imooc.codefirst.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import smartbuilder.imooc.codefirst.R;
import smartbuilder.imooc.codefirst.utils.L;
import smartbuilder.imooc.codefirst.utils.StaticClass;
import smartbuilder.imooc.codefirst.view.CustomDialog;

/**
 * 创建时间： 2017/5/6.
 * 描述：号码对属地查询
 */

public class LocationActivity extends BaseActivity implements View.OnClickListener {

    private EditText phone_et;
    private Button search_btn;
    private TextView result_tv;     //归属地
    private ImageView company_iv;   //所属城市

    private CustomDialog loadingDialog;     //等待对话框

    private boolean isFirst=true;  //第一次查询

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        initView();
    }
/**     初始化View   */
    private void initView() {
        phone_et= (EditText) findViewById(R.id.et_phone);
        search_btn= (Button) findViewById(R.id.btn_search);
        result_tv= (TextView) findViewById(R.id.tv_result);
        company_iv= (ImageView) findViewById(R.id.iv_company);

        search_btn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_search:
                    //1、获取输入框内容，判断是否为空
                String phone=phone_et.getText().toString().trim();
                if(!TextUtils.isEmpty(phone)){
                        //2、RxVolley网络请求json数据
                    String url="http://apis.juhe.cn/mobile/get?phone="+phone
                            +"&key="+ StaticClass.LOCATION_PHONE_KEY;
                    RxVolley.get(url, new HttpCallback() {
                            //成功返回json数据
                        @Override
                        public void onSuccess(String t) {
                            Toast.makeText(getApplicationContext(),
                                    "成功",Toast.LENGTH_SHORT).show();
                            L.i("json"+t);
                                //3、解析json数据
                            parseJson(t);
                            isFirst=false;  //不是第一次运行
                        }
                    });

                } else{
                    Toast.makeText(this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                 }
                break;


        }
    }
//解析json 数据"province":"浙江",
 /*   "city":"杭州",
            "areacode":"0571",
            "zip":"310000",
            "company":"中国移动",
            "card":"移动动感地带卡"    */
    private void parseJson(String t) {

        try {
                //1、通过json数据生成json对象
            JSONObject jsonObject=new JSONObject(t);
                //2、通过字段名获取到object数据对象
            JSONObject jsonResult=jsonObject.getJSONObject("result");
                //3、获取数据
            String province=jsonResult.getString("province");
            String city=jsonResult.getString("city");
            String company=jsonResult.getString("company");
            String areacode=jsonResult.getString("areacode");
            //String card=jsonResult.getString("card");   //手机卡信息
            if(!isFirst){
                result_tv.setText("");

            }
                //4、设置数据
            result_tv.append("归属地："+province+city+"\n");
            result_tv.append("公司："+company+"\n");
            result_tv.append("区号："+areacode+"\n");
            //result_tv.append("卡信息："+card+"\n");
                //显示
            result_tv.setVisibility(View.VISIBLE);

                //根据获取的公司设置不用的图片
            switch (company){
                case "移动" :
                    company_iv.setBackgroundResource(R.drawable.china_mobile);
                    company_iv.setVisibility(View.VISIBLE); //显示
                    break;
                case "联通" :
                    company_iv.setBackgroundResource(R.drawable.china_unicom);
                    company_iv.setVisibility(View.VISIBLE); //显示
                    break;
                case "电信" :
                    company_iv.setBackgroundResource(R.drawable.china_telecom);
                    company_iv.setVisibility(View.VISIBLE); //显示
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
