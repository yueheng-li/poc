package com.elecom.crawler.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import com.elecom.crawler.common.interceptor.AuthSessionInterceptor;
import com.elecom.crawler.common.interceptor.ErrorInterceptor;
import com.elecom.crawler.viewResolver.ExcelViewResolver;

@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/WEB-INF/static/",
            "classpath:/resources/", "classpath:/static/", "classpath:/public/" };


    /**
     * filter 設定
     * 
     */
//    @Bean
//    public FilterRegistrationBean filterRegistration() {
//
//        FilterRegistrationBean registration = new FilterRegistrationBean();
////        registration.setFilter(new SecurityFilter());
////        registration.addUrlPatterns("/*");
////        registration.addInitParameter("paramName", "paramValue");
////        registration.setName("LoginFilter");
////        registration.setOrder(1);
//        return registration;
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns ルート追加
        registry.addInterceptor(new ErrorInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new AuthSessionInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
        super.addResourceHandlers(registry);
    }

//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        configurer
//                .defaultContentType(MediaType.APPLICATION_JSON)
//                .favorPathExtension(true);
//    }
//
//    /*
//     * Configure ContentNegotiatingViewResolver
//     */
//    @Bean
//    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
//        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
//        resolver.setContentNegotiationManager(manager);
//
//        // Define all possible view resolvers
//        List<ViewResolver> resolvers = new ArrayList<>();
//
//        resolvers.add(excelViewResolver());
//
//        resolver.setViewResolvers(resolvers);
//        return resolver;
//    }
//
//    /*
//     * Configure View resolver to provide XLS output using Apache POI library to
//     * generate XLS output for an object content
//     */
//    @Bean
//    public ViewResolver excelViewResolver() {
//        return new ExcelViewResolver();
//    }


}
