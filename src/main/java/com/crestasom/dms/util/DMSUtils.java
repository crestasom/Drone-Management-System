package com.crestasom.dms.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

import org.springframework.util.ObjectUtils;
import lombok.Generated;

public class DMSUtils {
	@Generated
	private DMSUtils() {
	}

	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	public static void storeImgToFile(String filePath, String imgData) throws IOException {
		if (ObjectUtils.isEmpty(imgData) || ObjectUtils.isEmpty(filePath)) {
			return;
		}
		File file = new File(filePath);
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(decodeImg(imgData));
		fos.close();
	}

	private static byte[] decodeImg(String img) {
		return Base64.getDecoder().decode(img);
	}
}
