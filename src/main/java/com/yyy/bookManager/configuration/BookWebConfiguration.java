package com.yyy.bookManager.configuration;


import com.yyy.bookManager.interceptor.HostInfoInterceptor;
import com.yyy.bookManager.interceptor.LoginInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//真正让拦截器生效的地方,注意，里面注册的顺序非常重要,addPathPatterns 设置了对于什么url的请求拦截器会生效。
@Component
public class BookWebConfiguration {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private HostInfoInterceptor hostInfoInterceptor;

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer(){
            //添加拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry){
                registry.addInterceptor(hostInfoInterceptor).addPathPatterns("/**");
                registry.addInterceptor(loginInterceptor).addPathPatterns("/books/**");
            }
        };
    }
}
