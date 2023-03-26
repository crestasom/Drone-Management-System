package com.crestasom.pps.util;

import java.util.UUID;

import lombok.Generated;

public class PPSUtils {
	@Generated
	private PPSUtils() {

	}

	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
}
