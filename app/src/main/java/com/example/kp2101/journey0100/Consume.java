package com.example.kp2101.journey0100;

/**
 * Created by Yu on 2016/1/6.
 */
public class Consume {
    String cId;
    String cName;
    String cDollar;
    public Consume(String cId, String cName, String cDollar){
        this.cId = cId;
        this.cName = cName;
        this.cDollar = cDollar;
    }

    public String getcId(){ return cId;  }
    public String getcName(){
        return cName;
    }
    public String getcDollar(){
        return cDollar;
    }

    public void setcName(String cName){
        this.cName = cName;
    }
}
