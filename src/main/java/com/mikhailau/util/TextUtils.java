package com.mikhailau.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class TextUtils {
	private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

	private TextUtils() {
	}

	public static String formatBigDecimal(BigDecimal invTotal) {
		return invTotal.compareTo(BigDecimal.ZERO)==0?"0":
		decimalFormat.format(invTotal.doubleValue());
	}
}
