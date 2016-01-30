package MaQiao.MaQiaoNodeRetrieval;

/**
 * 线程组常量
 * @author Sunjian
 * @since 1.7
 * @version 1.0
 */
public final class ThreadNodeConsts {
	/**
	 * 线程组
	 */
	public static final ThreadGroup nodeThreadGroup = new ThreadGroup("extNode Group");
	/**
	 * 线程组数组
	 */
	public static final nodeThreadRun[] nodeThreadRunArray = new nodeThreadRun[Consts.threadArraySize];
	static {
		try {
			for (int i = 0; i < Consts.threadArraySize; i++) {
				final nodeThreadRun nodeThread = new nodeThreadRun();
				final Thread mythread = new Thread(nodeThreadGroup, nodeThread);
				mythread.setName("MyThread #[" + i + "] ");
				mythread.start();
				nodeThreadRunArray[i] = nodeThread;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}