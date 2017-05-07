package smartbuilder.imooc.codefirst.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import smartbuilder.imooc.codefirst.R;
import smartbuilder.imooc.codefirst.adapter.ChatAdapter;
import smartbuilder.imooc.codefirst.entity.ChatData;
import smartbuilder.imooc.codefirst.utils.L;
import smartbuilder.imooc.codefirst.utils.StaticClass;

/**
 * 创建时间： 2017/4/26.
 * 描述：智能聊天--- 界面
 */

public class ButlerFragment extends Fragment implements View.OnClickListener {

    private EditText chat_et;   //聊天框
    private Button send_btn;    //发送

    private List<ChatData> dataList;    //数据源
    private ChatData chatData;  //实体
    private ListView mListView;
    private ChatAdapter chatAdapter;    //适配器


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.bulterfragment,null);
        findView(view);
        return view;
    }
/**     初始化View   */
    private void findView(View view) {
        chat_et= (EditText) view.findViewById(R.id.et_chat);
        send_btn= (Button) view.findViewById(R.id.btn_send);

        send_btn.setOnClickListener(this);

        mListView= (ListView) view.findViewById(R.id.mListView);
        dataList=new ArrayList<>();     //初始化数据源

        chatAdapter=new ChatAdapter(getActivity(),dataList);
        mListView.setAdapter(chatAdapter);
        addLeftText("你好，傻丽友，啦啦啦");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send:
                    //发送消息内容
                sendMessage();

                break;
        }
    }


    /**     发送消息内容   */
    private void sendMessage() {
            //1、获取输入框内容，判断是否为空,字数限制
        String msg=chat_et.getText().toString();
        if(!TextUtils.isEmpty(msg)){
            if(msg.length()>30){
                Toast.makeText(getActivity(),"超过30字数限制",Toast.LENGTH_SHORT).show();
            } else {
                    //2、清空输入框内容,添加内容到右边
                chat_et.setText("");
                addRightText(msg);
                String text="";
                try {
                        //将中文转码--转为utf-8
                    text= URLEncoder.encode(msg,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //3、获取json数据
                String url="http://apis.haoservice.com/efficient/robot?info="+text+
                        "&key="+ StaticClass.ROBOAT_APP_KEY;
               /* String url="http://op.juhe.cn/robot/index?info="+msg+"&key="+
                        StaticClass.CHAT_APP_KEY;   */
                L.i("json"+url);
                RxVolley.get(url, new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        L.i("json"+t);
                            //4、解析数据,显示在左边
                        parseJson(t);
                    }
                });
            }

        } else {
            Toast.makeText(getActivity(),"输入不能为空",Toast.LENGTH_SHORT).show();
        }
    }
/**         解析json数据 ,显示出来   */
    private void parseJson(String t) {
        try {
                //生成json 的object对象
            JSONObject jsonObject=new JSONObject(t);
                //根据字段名获取结果列表数据
            JSONObject jsonResult=jsonObject.getJSONObject("result");
                //根据key获取 value
            String text=jsonResult.getString("text");
            addLeftText(text);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**     添加左边文本内容   */
    private void addLeftText(String text) {
        ChatData chatData=new ChatData();
        chatData.setType(chatAdapter.LEFT_TEXT);
        chatData.setText(text);
        dataList.add(chatData);
            //通知adapter刷新
        chatAdapter.notifyDataSetChanged();
            //滚动到底部--对话
        mListView.setSelection(mListView.getBottom());
    }
    /**     添加右边文本内容   */
    private void addRightText(String text) {
        ChatData chatData=new ChatData();
        chatData.setType(chatAdapter.RIGHT_TEXT);
        chatData.setText(text);
        dataList.add(chatData);
            //通知adapter刷新
        chatAdapter.notifyDataSetChanged();
            //滚动到底部--对话
        mListView.setSelection(mListView.getBottom());
    }
}
