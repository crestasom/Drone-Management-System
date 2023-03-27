package com.crestasom.dms.util;

import java.util.UUID;

import lombok.Generated;

public class DMSUtils {
	@Generated
	private DMSUtils() {
	}

	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
}
