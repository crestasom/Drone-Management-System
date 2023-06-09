package com.crestasom.dms.util;

import java.io.File;
import java.io.FileInputStream;
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
		try(FileOutputStream fos = new FileOutputStream(file)){
			fos.write(decodeImg(imgData));
		}
	}

	private static byte[] decodeImg(String img) {
		return Base64.getDecoder().decode(img);
	}

	public static String readImageFromFile(String imgPath) throws IOException {
		if (ObjectUtils.isEmpty(imgPath)) {
			return null;
		}
		File file = new File(imgPath);
		try (FileInputStream fis = new FileInputStream(file);) {
			byte[] fileData = fis.readAllBytes();
			return Base64.getEncoder().encodeToString(fileData);
		}

	}
}
