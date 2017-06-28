package com.elecom.crawler.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("config/logic.properties")
@ConfigurationProperties(prefix = "servlet")
public class PropertiesConfig {

	private Map<String, String> logic = new HashMap<>();

	public Map<String, String> getLogic() {
		return logic;
	}

	public void setLogic(Map<String, String> logic) {
		this.logic = logic;
	}

}
