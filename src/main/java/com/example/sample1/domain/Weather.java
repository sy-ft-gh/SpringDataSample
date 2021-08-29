package com.example.sample1.domain;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Id;

// エンティティクラスである 指定のアノテーション
@Entity
// テーブル名。データベースのテーブル名となる
@Table(name="weather")
// プライマリキーが複合（city+date）のため、これを管理するためのWeatherPKクラスを
// ID＝プライマリキー管理用のクラスとして設定します
@IdClass(WeatherPK.class)
public class Weather {
    // @IdでIDとして設定
    @Id
    private String city;
    @Id
    private Date date;
    private int temp_Lo;
    private int temp_Hi;
    private float prcp;
    // 以下 getter, setter
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
    // メンバ変数のコピーメソッドを作成
    public void CopyFrom(Weather src) {
        this.city = src.getCity();
        this.date = src.getDate();
        this.temp_Lo = src.getTemp_Lo();
        this.temp_Hi = src.getTemp_Hi();
        this.prcp = src.getPrcp();
    }
}
