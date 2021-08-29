package com.example.sample1.domain;

import org.springframework.data.jpa.domain.Specification;

public class WeatherSpecs {
    
    public static Specification<Weather> prcpGreaterThanEqual(Float prcp) {
        return prcp == null ? null : (root, query, builder) ->
                builder.greaterThan(root.get("prcp"), prcp);
    }
}
