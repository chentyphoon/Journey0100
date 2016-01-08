package com.example.kp2101.journey0100;

/**
 * Created by Yu on 2016/1/6.
 */
public class Member {
    String uId;
    String uName;
    public Member(String uId, String uName){
        this.uId = uId;
        this.uName = uName;
    }

    public String getuId(){
        return uId;
    }

    public String getuName(){
        return uName;
    }

    public void setuName(String uName){
        this.uName = uName;
    }
}
