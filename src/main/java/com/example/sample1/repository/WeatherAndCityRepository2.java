package com.example.sample1.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.sample1.domain.WeatherAndCity;;

@Mapper
public interface WeatherAndCityRepository2 {

    // Get Weather List
    @Select({
      "select",
      "c.name as city",
      ",c.country",
      ",w.date",
      ",w.prcp",
      "from cities c ",
          "inner join",
          "weather w",
              "on c.name = w.city"
    })
    List<WeatherAndCity> findAll();
    
     
     
}
