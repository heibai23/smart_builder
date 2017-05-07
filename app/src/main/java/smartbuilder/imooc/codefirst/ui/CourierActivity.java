package smartbuilder.imooc.codefirst.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import smartbuilder.imooc.codefirst.R;
import smartbuilder.imooc.codefirst.adapter.CourierAdapter;
import smartbuilder.imooc.codefirst.entity.CourierData;
import smartbuilder.imooc.codefirst.utils.L;
import smartbuilder.imooc.codefirst.utils.StaticClass;

/**
 * 创建时间： 2017/5/4.
 * 描述：登录界面
 */

public class CourierActivity extends BaseActivity implements View.OnClickListener {

    private EditText company_et;        //公司名称
    private EditText number_et;     //快递单号

    private Button select_btn;  //查询

    private ListView mListView;   //物流信息
    private CourierData courierData;    //一个item----ListView
        //数据源
    private List<CourierData> listCourData=new ArrayList<>();
    private CourierAdapter courierAdapter;      //适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);

        initView();


    }
/**         初始化View     */
    private void initView() {
        company_et= (EditText) findViewById(R.id.et_company);
        number_et= (EditText) findViewById(R.id.et_number);
        select_btn= (Button) findViewById(R.id.btn_select);

        select_btn.setOnClickListener(this);

        mListView= (ListView) findViewById(R.id.mListView_Info);
        courierAdapter=new CourierAdapter(this,listCourData);
        mListView.setAdapter(courierAdapter);



    }
/**         点击事件      */
    @Override
    public void onClick(View v) {
        /**         ListView显示
         *      1、获取输入框中的值，判断是否为空
         *      2、上传数据，获取物流信息
         *      3、解析json数据，调用适配器加载
         *      4、实体类
         *      4、ListView设置数据、显示效果
         */
        switch (v.getId()){
            case R.id.btn_select:
                String company=company_et.getText().toString().trim();
                String number=number_et.getText().toString().trim();
                    //请求的URL
                String url="http://v.juhe.cn/exp/index?key="+StaticClass.COURIER_APP_KEY+
                        "&com="+company+"&no="+number;
                    //1、输入框
                if(!TextUtils.isEmpty(company)& !TextUtils.isEmpty(number)){
                        //2、获取数据
                    RxVolley.get(url, new HttpCallback() {

                        @Override
                        public void onSuccess(String t) {
                            Toast.makeText(getApplicationContext(),"查询成功：",
                                    Toast.LENGTH_SHORT).show();
                            L.i("Json"+t);
                            parseJson(t);   //解析数据
                        }
                    });

                } else {
                    Toast.makeText(this,"输入不能为空",Toast.LENGTH_SHORT).show();
                  }
                break;
        }

    }
 /**     解析Json 数据    */
    private void parseJson(String t) {
        try {
                //通过json数据生成json对象
            JSONObject jsonObject=new JSONObject(t);
                //通过字段名获取到object数据对象
            JSONObject jsonResult=jsonObject.getJSONObject("result");
                //通过类似key获取到value---list数据
            JSONArray jsonArray=jsonResult.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                    //取得Object数据---逐个
                JSONObject object=jsonArray.getJSONObject(i);
                courierData=new CourierData();
                    //通过key找到对应 value，赋值给一个item---逐个
                courierData.setDatetime(object.getString("datetime"));
                courierData.setRemark(object.getString("remark"));
                courierData.setZone(object.getString("zone"));
                    //添加数据源
                listCourData.add(courierData);
            }
                //倒序排列
            Collections.reverse(listCourData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
