package com.example.sample1.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import java.sql.Date;

@Embeddable
public class WeatherPK implements Serializable {
    private static final long serialVersionUID = 1L;
    private String city;
    private Date date;
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    // 必須となるhashCode、equalsメソッド
    // hashCode関数をEclipseにより生成
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        return result;
    }
    // equalsメソッドをEclipseにより生成
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WeatherPK other = (WeatherPK) obj;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        return true;
    }    
}
