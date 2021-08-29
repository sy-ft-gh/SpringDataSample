package com.example.sample1.repository;

import java.util.List;
import java.util.Optional;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.sample1.domain.Weather;
import com.example.sample1.domain.WeatherPK;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, WeatherPK>, JpaSpecificationExecutor<Weather> {
    // 全件取得のみのSQLは自動生成されるので割愛
    // weatherテーブルの全件をCityとDateの昇順で取得
    List<Weather> getAllByOrderByCityAscDateAsc();
    // weatherテーブルを主キーで検索し取得
    Optional<Weather> findByCityAndDate(String city, Date date);
    // weatherテーブルとcitiesテーブルを内部結合し、citiesテーブルに存在するweatherテーブルのみ取得
    @Query("select w from Weather w inner join Cities c on w.city = c.name")
    List<Weather> getAllByExitsCities();
}
