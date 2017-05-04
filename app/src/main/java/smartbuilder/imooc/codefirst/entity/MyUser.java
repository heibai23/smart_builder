package smartbuilder.imooc.codefirst.entity;

import cn.bmob.v3.BmobUser;

/**
 * 创建时间： 2017/5/1.
 * 描述：用户属性
 */

public class MyUser extends BmobUser {

    private int age;
    private String desc;    //简介
    private boolean sex ;   //性别--true为男

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
}
