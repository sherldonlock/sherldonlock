package com.nicechat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

//启动类
@SpringBootApplication
@MapperScan("com.nicechat.dao")
@EnableEurekaClient //在服务启动后自动注册到Eureka中！
@EnableDiscoveryClient //服务发现~
public class Provider_8001 {

    public static void main(String[] args) {
        SpringApplication.run(Provider_8001.class, args);
        System.out.println("Provider-8001 success!");
    }

    /*//设置跨域过滤器
    private CorsConfiguration corsConfig() {
        CorsConfiguration conf = new CorsConfiguration();
        conf.addAllowedHeader("*");
        conf.addAllowedMethod("*");
        conf.addAllowedOrigin("*");
        conf.setAllowCredentials(true);
        conf.setMaxAge(3600L);
        return conf;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig());
        return new CorsFilter(source);

    }*/
}
