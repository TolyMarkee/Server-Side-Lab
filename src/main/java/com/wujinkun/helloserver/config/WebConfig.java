package com.wujinkun.helloserver.config;

import com.wujinkun.helloserver.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MVC配置类：挂载自定义拦截器，配置拦截/放行规则（实验要求）
 */
@Configuration // 核心注解：标识为Spring配置类，自动加载
public class WebConfig implements WebMvcConfigurer {

    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor()) // 挂载自定义鉴权拦截器
                .addPathPatterns("/api/**") // 拦截/api下的所有请求（核心规则）
                .excludePathPatterns("/api/users/login"); // 仅全局放行登录接口，其余由拦截器内部判断
    }
}