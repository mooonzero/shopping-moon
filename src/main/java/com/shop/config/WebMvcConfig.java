package com.shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    //@Configuration : 설정 파일을 만들거나 Bean을 등록하기 위한 어노테이션

    //WebMvcConfigurer
    //@EnableWebMvc를 통해 자동 설정되는 빈들의 설정자
    //@EnableWebMvc가 자동적으로 세팅해주는 설정에 개발자가 원하는 설정 추가할 수 있게 해줌

    //application.properties에 설정한 'uploadPath'값을 불러옴
   @Value("${uploadPath}")
    String uploadPath;


   @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
       registry.addResourceHandler("/images/**")
               .addResourceLocations(uploadPath);
       //ResourceHandlerRegistry : 리소스 등록 및 핸들러를 관리하는 객체
       //addResourceHandler() : 매핑 URI 설정
       //addResourceLocations() : 로컬 컴퓨터에 저장된 파일을 읽어올 root 설정. 정적 리소스 위치 설정
   }


}
