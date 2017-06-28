package com.elecom.crawler.common.properties;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "servlet.properties")  
public class ServletPropertiesSettings {

    private Map<String, String> info;

    public Map<String, String> getInfo() {
        return info;
    }
    public void setInfo(Map<String, String> info) {
        this.info = info;
    }

}
