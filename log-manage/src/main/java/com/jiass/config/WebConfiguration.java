package com.jiass.config;

import com.jiass.interceptor.LogRecordInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public  class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    private LogRecordInterceptor logRecordInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logRecordInterceptor).addPathPatterns("/**");
    }
}
