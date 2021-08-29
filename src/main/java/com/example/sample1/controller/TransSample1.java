package com.example.sample1.controller;

import org.springframework.stereotype.Controller;


import java.util.List;
import java.util.ArrayList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.sample1.domain.WeatherAndCities;
import com.example.sample1.form.InputWeatherAndCities;
import com.example.sample1.repository.WeatherAndCityRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;



@Controller
public class TransSample1 {

    @Autowired
    WeatherAndCityRepository weatherAndCityRepository;
 
    @Autowired
    private Validator validator;
 
    // WeatherInfo 一覧取得
    @RequestMapping("/insWeatherData")
    @Transactional
    public ModelAndView view_ins_weather1(@ModelAttribute("inputForm") @Validated InputWeatherAndCities inputWeatherAndCities,  
                                            BindingResult result,
                                            ModelAndView model) {
        model.setViewName("WeatherAndCitiesStoredResult");
        model.addObject("status", 1);
        if (result.hasErrors()) {
            // バインディング時にエラーが発生していた場合はここへ遷移
            System.out.println("validation error raise");
            // 入力エラー時はモデル情報をそのまま返却→バリデーション結果表示
            return model;
        } else {
            if (inputWeatherAndCities.getLocation_x() != null || inputWeatherAndCities.getLocation_y() != null) {
                ValidationUtils.invokeValidator(validator, inputWeatherAndCities, result, InputWeatherAndCities.Inputstep1.class);
                if (result.hasErrors()) {
                    // バインディング時にエラーが発生していた場合はここへ遷移
                    System.out.println("validation error2 raise");
                    // 入力エラー時はモデル情報をそのまま返却→バリデーション結果表示
                    return model;
                }
            }
        }
        Date inputDate = Date.valueOf(LocalDate.now());
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            inputDate = new java.sql.Date(sdf.parse(inputWeatherAndCities.getDate()).getTime());
        } catch (ParseException e) {
            System.out.println("date parse error");
        }
        
        // weatherテーブルのプライマリキーを指定した取得
        var weather = weatherAndCityRepository.findByCityAndDate(inputWeatherAndCities.getCity(),  inputWeatherAndCities.getDate());
        // 存在しなければレコードを追加
        if (!weather.isPresent()) {
            // weatherテーブルはキー重複が無いため登録可能
            var ins_data = new WeatherAndCities();
            ins_data.setCity(inputWeatherAndCities.getCity());
            ins_data.setTemp_Lo(inputWeatherAndCities.getTempLo());
            ins_data.setTemp_Hi(inputWeatherAndCities.getTempHi());
            ins_data.setPrcp(inputWeatherAndCities.getPrcp());
            ins_data.setDate(inputDate);
            weatherAndCityRepository.insertWeather(ins_data);
            // citiesテーブルはキーチェックをしていないため、新規キーしか入らない（重複した場合はエラー）
            // @Transactionalが無い場合はwheatherテーブルの更新がCOMMITされてしまう
            ins_data.setName(inputWeatherAndCities.getCity());
            ins_data.setLocation(inputWeatherAndCities.getLocation_x(), inputWeatherAndCities.getLocation_y());
            weatherAndCityRepository.insertCities(ins_data.getName(), ins_data.getLocation().x, ins_data.getLocation().y);
            
        }
        // 登録完了時にstatusを0に切り替え
        model.addObject("status", 0);

        return model;
    }
}
