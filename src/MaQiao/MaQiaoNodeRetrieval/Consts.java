package MaQiao.MaQiaoNodeRetrieval;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

/**
 * 常量池 <br/>
 * @author Sun.jian(孙.健)
 * @since 1.7
 * @version 1.0
 */
public final class Consts {
	public static final Unsafe UNSAFE;
	/**
	 * 是否打开线程挂起动作
	 */
	public static final boolean isOpenUNSAFEPark = false;
	/**
	 * 线程组数组最大容量
	 */
	public static final int threadArraySize = 3;/* 必须大于2 */
	/**
	 * String对象中value(char[])地址偏移量
	 */
	public static long StringArrayOffset = 0L;
	/**
	 * Thread对象中target(Runnable)地址偏移量
	 */
	public static long ThreadRunnableOffset = 0L;

	static {
		try {
			//if (threadArraySize < 2) threadArraySize = 2;
			final Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			UNSAFE = (Unsafe) field.get(null);
			StringArrayOffset = UNSAFE.objectFieldOffset(java.lang.String.class.getDeclaredField("value"));/* 得到String对象中数组的偏移量 */
			ThreadRunnableOffset = UNSAFE.objectFieldOffset(java.lang.Thread.class.getDeclaredField("target"));/* 得到Thread对象中target(Runnable)地址偏移量 */
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * char[]数组地址偏移量
	 */
	public static final long ArrayAddress = UNSAFE.arrayBaseOffset(char[].class);

	/**
	 * 初始化
	 */
	public Consts() {
	}

	/**
	 * Consts.sequence.Reverse:从大到小<br/>
	 * Consts.sequence.Ordering:从小到大<br/>
	 * Consts.sequence.Outoforder:乱序<br/>
	 * @author Sunjian<br/>
	 * @since 1.7
	 * @version 1.1
	 */
	public static enum sequence {
		/**
		 * 倒序
		 */
		Reverse(1),
		/**
		 * 顺序
		 */
		Ordering(0),
		/**
		 * 乱序
		 */
		Outoforder(-1);
		int value;

		private sequence(final int value) {
			this.value = value;
		}

		public static final sequence getSequence(final int value) {
			for (sequence c : sequence.values())
				if (c.value == value) return c;
			return null;
		}

		@Override
		public String toString() {
			return this.name() + ":" + this.ordinal();
		}
	}

}
