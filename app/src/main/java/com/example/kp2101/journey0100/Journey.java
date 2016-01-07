package com.example.kp2101.journey0100;

/**
 * Created by Yu on 2016/1/6.
 */
public class Journey {
    String jId;
    String jName;
    public Journey(String jId, String jName){
        this.jId = jId;
        this.jName = jName;
    }

    public String getJId(){
        return jId;
    }

    public String getjName(){
        return jName;
    }

    public void setJName(String jName){
        this.jName = jName;
    }
}
