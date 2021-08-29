package com.example.sample1.controller;

import java.util.Arrays;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.data.jpa.domain.Specification;

import com.example.sample1.domain.Weather;
import com.example.sample1.domain.WeatherPK;
import com.example.sample1.domain.WeatherSpecs;
import com.example.sample1.repository.WeatherAndCityRepository;
import com.example.sample1.repository.WeatherRepository;


@Controller
public class SelectSample1 {
    
    @Autowired
    WeatherAndCityRepository weatherAndCityRepository;

    @Autowired
    WeatherRepository weatherRepository;
    
    
    // WeatherInfo 一覧取得
    @RequestMapping("/getWeatherList")
    public ModelAndView view_weather1(ModelAndView model) {
        // weatherテーブルの全件取得
        var weathers1 =  weatherAndCityRepository.findAllWeathers();
        model.addObject("weathers1", weathers1);      
        // weatherテーブルのプライマリキーを指定した取得
        var weather = weatherAndCityRepository.findByCityAndDate("Tokyo", "2010-12-10");
        model.addObject("weather", weather); 
        // weatherテーブルの降水量を指定した取得
        var weathers2 = weatherAndCityRepository.findByPRCP(null);
        model.addObject("weathers2", weathers2);
        var weathers3 = weatherAndCityRepository.findByPRCP(Float.valueOf(0.6f));
        model.addObject("weathers3", weathers3);      
        
        if (weather.isPresent()) {
            // City,日付でデータが取れた場合、本日の日付に変えて登録しなおす。
            var ins_data = weather.get();
            LocalDate ldate = LocalDate.now();
            ins_data.setDate(java.sql.Date.valueOf(ldate));
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // 登録前に同一キー情報が登録済みかチェック。登録されている場合はキー重複のエラーが発生するため登録をスキップ
            var confirm = weatherAndCityRepository.findByCityAndDate("Tokyo", ldate.format(dtf));
            if (!confirm.isPresent()) {
                weatherAndCityRepository.insertWeather(ins_data);
            }
        }
        model.setViewName("getDataSample1");
        return model;
    }
    // WeatherInfo 一覧取得
    @RequestMapping("/getWeatherList2")
    public ModelAndView view_weather2(ModelAndView model) {
        // weatherテーブルの全件取得(findAll→全件生成されるメソッド)
        var weathers1 = weatherRepository.findAll();
        model.addObject("weathers1", weathers1);      
        // weatherテーブルのcityとdateを使用して取得
        var wpk = new WeatherPK();
        wpk.setCity("Tokyo"); wpk.setDate(Date.valueOf(LocalDate.of(2010, 12, 10)));
        var weather = weatherRepository.findById(wpk);
        model.addObject("weather", weather); 

        // weatherテーブルの降水量を指定した取得
        // 降水量＝null
        Specification<Weather> spec1 = Specification.where(WeatherSpecs.prcpGreaterThanEqual(null));
        var weathers2 = weatherRepository.findAll(spec1);
        model.addObject("weathers2", weathers2);
        // 降水量=0.6
        Specification<Weather> spec2 = Specification.where(WeatherSpecs.prcpGreaterThanEqual(Float.valueOf(0.6f)));
        var weathers3 = weatherRepository.findAll(spec2);
        model.addObject("weathers3", weathers3);      
        // テーブル結合
        var weathers4 = weatherRepository.getAllByExitsCities();
        weathers4.stream().forEach(i -> System.out.println(i.getCity()+ "," + i.getDate()));
        if (weather.isPresent()) {
            // City,日付でデータが取れた場合、本日の日付に変えて登録しなおす。
            var ins_data = new Weather();
            ins_data.CopyFrom(weather.get());
            LocalDate ldate = LocalDate.now();
            ins_data.setDate(java.sql.Date.valueOf(ldate));
            // 登録前に同一キー情報が登録済みかチェック。登録されている場合はキー重複のエラーが発生するため登録をスキップ
            wpk.setCity(ins_data.getCity()); wpk.setDate(ins_data.getDate());
            var confirm = weatherRepository.findById(wpk);
            if (!confirm.isPresent()) {
                // saveメソッドによりINSERT、UPDATEの登録対象として”リポジトリの先のentitiyManagerに登録される”(実際のSQL実行はCOMMIT前←本メソッド実行後)
                weatherRepository.save(ins_data);
            }
        }
        model.setViewName("getDataSample1");
        return model;
    }
    // WeatherInfo 動的一覧取得
    @RequestMapping("/getWeatherList3")
    public ModelAndView view_weather3(ModelAndView model) {
        // 複数の降水量に該当するレコードの検索
        Float[] prcps = new Float[] {0.6f, 0.25f};
        var weathers1 = weatherAndCityRepository.findByPrcps(Arrays.asList(prcps));        
        model.addObject("weathers1", weathers1);      

        model.setViewName("getDataSample1");
        return model;
    }

}
