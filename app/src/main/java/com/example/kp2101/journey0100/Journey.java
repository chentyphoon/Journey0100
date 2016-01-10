package com.example.kp2101.journey0100;

/**
 * Created by Yu on 2016/1/6.
 */
public class Journey {
    String jId;
    String jName;
    String jPic;
    String ujMoney;
    public Journey(String jId, String jName, String jPic, String ujMoney){
        this.jId = jId;
        this.jName = jName;
        this.jPic = jPic;
        this.ujMoney = ujMoney;
    }

    public String getjId(){
        return jId;
    }

    public String getjName(){
        return jName;
    }

    public String getjPic(){
        return jPic;
    }

    public String getUjMoney(){
        return ujMoney;
    }

    public void setjName(String jName){
        this.jName = jName;
    }

    public void setjPic(String jPic){
        this.jPic = jPic;
    }
}
