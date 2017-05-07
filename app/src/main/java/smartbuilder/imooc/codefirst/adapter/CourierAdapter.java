package smartbuilder.imooc.codefirst.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import smartbuilder.imooc.codefirst.R;
import smartbuilder.imooc.codefirst.entity.CourierData;

/**
 * 创建时间： 2017/5/5.
 * 描述：快递查询  适配器
 */

public class CourierAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater inflater;    //布局加载器--可将layout 变为view
    private List<CourierData> courierList;

    private CourierData courier;    //ListView的item---实体

    public CourierAdapter(Context mContext, List<CourierData> courierList) {
        this.mContext = mContext;
        this.courierList=courierList;
            //获取系统服务
        inflater= (LayoutInflater) mContext.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return courierList.size();
    }

    @Override
    public Object getItem(int position) {
        return courierList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
        //初始化数据源
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
            //是否第一次加载
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(R.layout.layout_courier_item,null);
            viewHolder.datetime= (TextView) convertView.findViewById(R.id.tv_datetime);
            viewHolder.remark= (TextView) convertView.findViewById(R.id.tv_remark);
            viewHolder.zone= (TextView) convertView.findViewById(R.id.tv_zone);
                //设置缓存
            convertView.setTag(viewHolder);
        } else {
            viewHolder= (ViewHolder) convertView.getTag();
          }
            //设置数据
        courier=courierList.get(position);  //取得对应的一项 ListView
        viewHolder.datetime.setText(courier.getDatetime());
        viewHolder.remark.setText(courier.getRemark());
        viewHolder.zone.setText(courier.getZone());

        return convertView;
    }
        //一个ListView的item包含的东西
    class ViewHolder{
            private TextView datetime;
            private TextView remark;
            private TextView zone;
    }
}
