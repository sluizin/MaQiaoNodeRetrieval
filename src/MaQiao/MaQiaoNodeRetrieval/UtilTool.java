package MaQiao.MaQiaoNodeRetrieval;

public final class UtilTool {
	public static String formatNum(final long num, final int size) {
		String n = Long.toString(num);
		return n.substring(n.length() - size, n.length());
	}

	/**
	 * 格式化long
	 * @param num long
	 * @param size int
	 * @param c char
	 * @return String
	 */
	public static String format(final long num, final int size, final char c) {
		return format(Long.toString(num), size, c);
	}

	/**
	 * 格式化String
	 * @param str String
	 * @param size int
	 * @param c char
	 * @return String
	 */
	public static String format(final String str, final int size, final char c) {
		final int len = str.length();
		if (len <= 0 || size <= 0) return new String("");
		final String newString = new String(new char[size]);
		final Object objSource = Consts.UNSAFE.getObject(str, Consts.StringArrayOffset);
		final Object objDest = Consts.UNSAFE.getObject(newString, Consts.StringArrayOffset);
		final int lenC = len - size;
		if (lenC > 0) System.arraycopy(objSource, 0, objDest, lenC, size);
		else {
			System.arraycopy(objSource, 0, objDest, -lenC, len);
			for (int i = 0; i < -lenC; i++)
				Consts.UNSAFE.putChar(objDest, Consts.ArrayAddress + (i << 1), c);
		}
		return newString;
	}
}
