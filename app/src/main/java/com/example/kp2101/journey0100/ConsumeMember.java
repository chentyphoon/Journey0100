package com.example.kp2101.journey0100;

/**
 * Created by Yu on 2016/1/6.
 */
public class ConsumeMember {
    String uId;
    String uName;
    boolean selected=false;
    Double need=0.0;
    Double paid=0.0;



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

    public Double getNeed() {
        return need;
    }

    public void setNeed(Double need){ this.need= need; };

    public Double getPaid() {
        return paid;
    }
    public void setPaid(Double paid){ this.paid= paid; };

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


}
