package com.example.sample1.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;



public class InputWeatherAndCities {
    // バリデーション指定を付加
    @NotEmpty
    @Size(min = 1, max = 25)
    private String city;
    @NotNull
    private Integer tempLo;
    @NotNull
    private Integer tempHi;
    @NotNull
    private Float prcp;
    @NotNull
    @Pattern(regexp = "\\d{4}\\/\\d{2}\\/\\d{2}")
    private String date;
    @NotNull(groups = {Inputstep1.class})
    private Float location_x;
    @NotNull(groups = {Inputstep1.class})
    private Float location_y;
    
    public static interface Inputstep1 {
    }

    // 以降 getter setter
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getTempLo() {
        return tempLo;
    }

    public void setTempLo(Integer tempLo) {
        this.tempLo = tempLo;
    }

    public Integer getTempHi() {
        return tempHi;
    }

    public void setTempHi(Integer tempHi) {
        this.tempHi = tempHi;
    }

    public Float getPrcp() {
        return prcp;
    }

    public void setPrcp(Float prcp) {
        this.prcp = prcp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Float getLocation_x() {
        return location_x;
    }

    public void setLocation_x(Float location_x) {
        this.location_x = location_x;
    }

    public Float getLocation_y() {
        return location_y;
    }

    public void setLocation_y(Float location_y) {
        this.location_y = location_y;
    }


}
