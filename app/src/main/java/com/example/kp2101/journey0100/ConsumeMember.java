package com.example.kp2101.journey0100;

/**
 * Created by Yu on 2016/1/6.
 */
public class ConsumeMember {
    String uId;
    String uName;
    boolean selected=false;
    String need="0";
    String paid="0";



    public ConsumeMember(String uId, String uName){
        this.uId = uId;
        this.uName = uName;
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

    public String getNeed() {
        return need;
    }

    public void setNeed(String need){ this.need= need; };

    public String getPaid() {
        return paid;
    }
    public void setPaid(String paid){ this.paid= paid; };

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


}
