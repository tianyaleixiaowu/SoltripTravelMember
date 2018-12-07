package com.s.t.m.common.core.configurer;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;
/**
 * 文件上传限制配置
 * @author Bai
 *
 */
@Configuration
public class MultipartConfigurer {
	//文件上传储存的地址
	public static final String SAVEFILEPATH = "E://img";

    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory=new MultipartConfigFactory();
        factory.setMaxFileSize("10MB");
        factory.setMaxRequestSize("10MB");
        return factory.createMultipartConfig();
    }
}
