package com.elecom.crawler;

import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication springApp = new SpringApplication(Application.class);
        Set<Object> set = springApp.getSources();
        set.add("classpath:job-quartz.xml");
        springApp.setSources(set);
        springApp.run(args);

    }
}
