package com.crestasom.dms.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ConfigUtility {

	@Autowired
	private Environment env;

	public String getProperty(String pPropertyKey) {
		return env.getProperty(pPropertyKey);
	}

	public Integer getPropertyAsInt(String pPropertyKey) {
		return Integer.parseInt(env.getProperty(pPropertyKey));
	}
}