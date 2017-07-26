package com.example.testandroid.bean;

/**
 * Created by zhangqixun on 16/7/5.
 */
public class Weather {
    private String city;
    private String quality;
    private String temperature;

    private String wind_direction;
    private String getWind_direction_degree;

    private String UV;
    private String eatingAdvice;
    private String outAdvice;



    public  Weather(){
        city="";
        quality="";
        temperature="";
        wind_direction="";
        getWind_direction_degree="";
        UV="";
        eatingAdvice="";
        outAdvice="";
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getEatingAdvice() {
        return eatingAdvice;
    }

    public String getOutAdvice() {
        return outAdvice;
    }

    public String getUV() {
        return UV;
    }

    public void setEatingAdvice(String eatingAdvice) {
        this.eatingAdvice = eatingAdvice;
    }

    public void setOutAdvice(String outAdvice) {
        this.outAdvice = outAdvice;
    }

    public void setUV(String UV) {
        this.UV = UV;
    }

    public String getCity() {
        return city;
    }



    public String getTemperature() {
        return temperature;
    }

    public String getGetWind_direction_degree() {
        return getWind_direction_degree;
    }

    public String getWind_direction() {
        return wind_direction;
    }

    public void setGetWind_direction_degree(String getWind_direction_degree) {
        this.getWind_direction_degree = getWind_direction_degree;
    }

    public void setWind_direction(String wind_direction) {
        this.wind_direction = wind_direction;
    }

    public void setCity(String city) {
        this.city = city;
    }



    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }


}
