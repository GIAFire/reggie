package com.wjb.reggie.config;

import com.wjb.reggie.common.JacksonObjectMapper;
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

//web相关配置
@Configuration
public class ReggieWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;

    //设置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //定义放行路径
        ArrayList<String> urls = new ArrayList<>();
        urls.add("/front/**");//app系统静态资源
        urls.add("/error");//错误请求
        urls.add("/user/sendMsg");//app验证码发送
        urls.add("/user/login");//app登录
        urls.add("/user/logout");//app退出

        //配置拦截器和路径
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/**")//拦截所有
                .excludePathPatterns(urls); //放行指定路径
    }

    //设置静态资源映射
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //当访问请求是/front/**时,去classpath:/front/寻找对应资源
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
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