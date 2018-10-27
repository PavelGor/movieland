package com.gordeev.movieland.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"com.gordeev.movieland"},
                excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern="com\\.gordeev\\.movieland\\.controller\\..*"))
@PropertySource("classpath:application.properties")
public class RootConfig {
}
