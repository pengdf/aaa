package com._52shabake.mg.util;

import java.util.Random;

public class VerifyCodeUtil {
	public static String getCode() {

		String str = "0123456789";
		StringBuilder sb = new StringBuilder(4);
		for (int i = 0; i < 4; i++) {
			char ch = str.charAt(new Random().nextInt(str.length()));
			sb.append(ch);
		}
		return sb.toString();

	}
}
