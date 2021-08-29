package com.example.sample1.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import com.example.sample1.domain.WeatherAndCities;

@Mapper
public interface WeatherAndCityRepository {

    // Weather 一覧の取得
    @Select("SELECT city, temp_lo, temp_hi, prcp, date FROM weather")
    Collection<WeatherAndCities> findAllWeathers();
    
    // Cities 一覧の取得
    @Select("SELECT name, location FROM cities")
    Collection<WeatherAndCities> findAllCities();
    
    
    // City と Date による Weather＋Citiesの結合情報取得
    @Select({"SELECT w.city"
            ,",w.temp_lo"
            ,",w.temp_hi"
            ,",w.prcp"
            ,",w.date"
            ,",c.location"
            ,"FROM weather w INNER JOIN cities c "
            ,"ON (w.city = c.name) "
            ,"WHERE w.city = #{city}"
            ,"AND w.date = CAST(#{date} AS DATE)"})
    Optional<WeatherAndCities> findByCityAndDate(String city, String date);
    
    // 降水量指定による動的取得1
    @Select({"<script>"
            ,"SELECT w.city"
            ,",w.temp_lo"
            ,",w.temp_hi"
            ,",w.prcp"
            ,",w.date"
            ,",c.location"
            ,"FROM weather w INNER JOIN cities c "
            ,"ON (w.city = c.name) "
            ,"<where>"
            ,"<if test='prcp != null'>"
                ,"<![CDATA["
                    ,"AND w.prcp>=#{prcp}"
                ,"]]>"
            ,"</if>"
            ,"</where>"
            ,"</script>"})
    List<WeatherAndCities> findByPRCP(@Param("prcp") Float prcp);
    
    @Insert({"INSERT INTO weather("
            ,"city"
            ,",temp_lo"
            ,",temp_hi"
            ,",prcp"
            ,",date"
            ,") VALUES ("
            ,"#{city}"
            ,",#{temp_Lo}"
            ,",#{temp_Hi}"
            ,",#{prcp}"
            ,",#{date}"
            ,")"
    })
    int insertWeather(WeatherAndCities weather);

    @Insert({"INSERT INTO cities("
        ,"name"
        ,",location"
        ,") VALUES ("
        ,"#{name}"
        ,",Point(#{x},#{y})"
        ,")"
    })
    int insertCities(String name, double x, double y);
    
    @SelectProvider(type = SqlProvider.class, method = "findByPrcps")
    List<WeatherAndCities> findByPrcps(@Param("prcps") List<Float> prcps);
    
    static final class SqlProvider {
        public String findByPrcps(@Param("prcps") List<Float> prcps) {
            return new SQL() {
              {
                  SELECT("w.city, w.temp_lo, w.temp_hi, w.prcp, w.date, c.location");
                  FROM("weather w");
                  INNER_JOIN("cities c on (w.city = c.name)");
                  var filter = new StringBuilder();
                  if (prcps != null && prcps.size() > 0)
                      prcps.stream().forEach(p -> filter.append(filter.length() > 0 ? "," : "").append(String.valueOf(p.floatValue())));
                  if (filter.length() > 0)
                      WHERE("prcp in (" + filter.toString() + ")");
                  
              }
          }.toString();
        }
    }    
}
