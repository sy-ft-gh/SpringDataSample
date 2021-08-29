package com.example.sample1.domain;

import java.io.Serializable;
import java.sql.Date;

public class WeatherAndCity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public float getPrcp() {
        return prcp;
    }
    public void setPrcp(float prcp) {
        this.prcp = prcp;
    }
    private String city;
    private String country;
    private Date date;
    private float prcp;
}
