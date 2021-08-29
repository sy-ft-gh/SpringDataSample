package com.example.sample1.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

import org.postgresql.geometric.PGpoint;

//エンティティクラスである 指定のアノテーション
@Entity
//テーブル名。データベースのテーブル名となる
@Table(name="cities")
public class Cities {
    @Id
    private String name;
    
    private PGpoint location;     
    

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
}
