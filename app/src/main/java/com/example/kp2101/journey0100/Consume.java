package com.example.kp2101.journey0100;

/**
 * Created by Yu on 2016/1/6.
 */
public class Consume {
    String cId;
    String cName;
    String cDollar;
    String cLocation;
    String cLon;
    String cLat;
    String cTime;
    String cPic;
    String cDescrip;

    public Consume(String cId, String cName, String cDollar, String cLocation, String cLon, String cLat,
                   String cTime, String cPic, String cDescrip){
        this.cId = cId;
        this.cName = cName;
        this.cDollar = cDollar;
        this.cLocation = cLocation;
        this.cLon = cLon;
        this.cLat = cLat;
        this.cTime = cTime;
        this.cPic = cPic;
        this.cDescrip = cDescrip;
    }

    public String getcId(){ return cId;  }
    public String getcName(){
        return cName;
    }
    public String getcDollar(){
        return cDollar;
    }

    public String getcLocation() {
        return cLocation;
    }

    public String getcLon() {
        return cLon;
    }

    public String getcLat() {
        return cLat;
    }

    public String getcTime() {
        return cTime;
    }

    public String getcPic() {
        return cPic;
    }

    public String getcDescrip() {
        return cDescrip;
    }

    public void setcName(String cName){
        this.cName = cName;
    }
}
