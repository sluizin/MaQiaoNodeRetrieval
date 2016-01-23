package MaQiao.MaQiaoNodeRetrieval;

import java.util.Random;

/**
 * 工具方法集
 * @author Sunajin
 * @since 1.7
 * @version 1.0
 */
public final class UtilTool {
	static Random rd1 = new Random();
	/**
	 * 得到min-max之间的随机数
	 * @param min int
	 * @param max int
	 * @return int
	 */
	public final static int getRndInt(final int min, final int max) {
		return min + rd1.nextInt(max - min + 1);
	}
	/**
	 * 把数组输出成String
	 * @param array T[]
	 * @param multi boolean
	 * @return String
	 */
	public static final <T> String getString(final T[] array, final boolean multi) {
		StringBuilder sb = new StringBuilder(100);
		if (multi) sb.append("-------------\n");
		else sb.append('[');
		for (int i = 0, len = array.length; i < len; i++) {
			T t = array[i];
			if (t == null) continue;
			if (multi) sb.append(i + ':');
			if (t instanceof String) {
				sb.append('\"');
				sb.append(array[i].toString());
				sb.append('\"');
			} else sb.append(array[i].toString());
			if (i < len - 1)  if (!multi)sb.append(',');
			if (multi) sb.append('\n');
		}
		if (multi) sb.append("-------------\n");
		else sb.append(']');
		return sb.toString();
	}
	/**
	 * 格式化long 使用String.subString()方法
	 * @param num long
	 * @param size int
	 * @return String
	 */
	public static final String formatNum(final long num, final int size) {
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
	public static final String format(final long num, final int size, final char c) {
		return format(Long.toString(num), size, c);
	}

	/**
	 * 格式化String
	 * @param str String
	 * @param size int
	 * @param c char
	 * @return String
	 */
	public static final String format(final String str, final int size, final char c) {
		final int len = str.length();
		if (len <= 0 || size <= 0) return new String("");
		final String newString = new String(new char[size]);
		final Object objSource = Consts.UNSAFE.getObject(str, Consts.StringArrayOffset);
		final Object objDest = Consts.UNSAFE.getObject(newString, Consts.StringArrayOffset);
		final int lenC = len - size;
		if (lenC > 0) System.arraycopy(objSource, 0, objDest, lenC, size);
		else {
			System.arraycopy(objSource, 0, objDest, -lenC, len);
			for (int i = 0, lenCover = -lenC; i < lenCover; i++)
				Consts.UNSAFE.putChar(objDest, Consts.ArrayAddress + (i << 1), c);
		}
		return newString;
	}
}
