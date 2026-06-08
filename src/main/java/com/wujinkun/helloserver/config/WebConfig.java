package com.wujinkun.helloserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // 不再注册任何拦截器，全部交给Spring Security管理
}