package smartbuilder.imooc.codefirst.entity;

/**
 * 创建时间： 2017/5/5.
 * 描述：快递物流信息--ListView 的一项item
 */

public class CourierData {
    private String datetime;
    private String remark;      //快件信息
    private String zone;    //到达城市

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
