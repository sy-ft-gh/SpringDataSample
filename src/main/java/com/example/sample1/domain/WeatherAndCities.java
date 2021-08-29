package com.example.sample1.domain;
import java.sql.Date;

import org.postgresql.geometric.PGpoint;

public class WeatherAndCities {
	public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public int getTemp_Lo() {
        return temp_Lo;
    }
    public void setTemp_Lo(int temp_Lo) {
        this.temp_Lo = temp_Lo;
    }
    public int getTemp_Hi() {
        return temp_Hi;
    }
    public void setTemp_Hi(int temp_Hi) {
        this.temp_Hi = temp_Hi;
    }
    public float getPrcp() {
        return prcp;
    }
    public void setPrcp(float prcp) {
        this.prcp = prcp;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public PGpoint getLocation() {
        return location;
    }
    public void setLocation(PGpoint location) {
        this.location = location;
    }
    public void setLocation(double x, double y) {
        this.location = new PGpoint(x, y);
    }
    private String city;
	private int temp_Lo;
	private int temp_Hi;
	private float prcp;
	private Date date;
	private String name;
	private PGpoint location;
}
