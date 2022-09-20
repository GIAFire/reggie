package com.wjb.reggie.common;

import com.wjb.reggie.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ReggieWebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;
    //设置静态资源映射
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //当访问请求是/backend/**时,去classpath:/backend/寻找对应资源
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
    }


    //设置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //定义放行路径
        ArrayList<String> urls = new ArrayList<>();
        urls.add("/backend/**");//管理系统静态资源
        urls.add("/error");//错误请求
        urls.add("/employee/login");//管理系统登录请求
        urls.add("/employee/logout");//管理系统退出请求

        //配置拦截器和路径
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/**")//拦截所有
                .excludePathPatterns(urls); //放行指定路径
    }

    //扩展mvc框架的消息转换器
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //设置对象转换器，底层使用Jackson将Java对象转为json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //将上面的消息转换器对象追加到mvc框架的转换器集合中
        converters.add(0, messageConverter);
    }
}
