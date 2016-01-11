package com.example.kp2101.journey0100;

/**
 * Created by Yu on 2016/1/11.
 */
public class Myplace {
    double lat;
    double lon;
    String name;
    String address;

    public Myplace(double lat, double lon, String name, String address){
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
