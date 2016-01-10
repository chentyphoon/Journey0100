package com.example.kp2101.journey0100;

/**
 * Created by Yu on 2016/1/6.
 */
public class Member {
    String uId;
    String uName;
    String ujMoney;
    boolean selected=false;


    public Member(String uId, String uName,String ujMoney){
        this.uId = uId;
        this.uName = uName;
        this.ujMoney = ujMoney;

    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getujMoney() {
        return ujMoney;
    }

    public void setujMoney(String ujMoney) {
        this.ujMoney = ujMoney;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


}
