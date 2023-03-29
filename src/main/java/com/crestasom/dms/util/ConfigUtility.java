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

	public String getProperty(String pPropertyKey, String defaultValue) {
		String val = env.getProperty(pPropertyKey);
		if (val == null) {
			val = defaultValue;
		}
		return val;
	}

	public Integer getPropertyAsInt(String pPropertyKey) {
		return Integer.parseInt(env.getProperty(pPropertyKey));
	}

	public Integer getPropertyAsInt(String pPropertyKey, Integer defaultValue) {
		String val = env.getProperty(pPropertyKey);
		Integer retVal;
		if (val == null) {
			retVal = defaultValue;
		} else {
			retVal = Integer.parseInt(val);
		}
		return retVal;
	}
}