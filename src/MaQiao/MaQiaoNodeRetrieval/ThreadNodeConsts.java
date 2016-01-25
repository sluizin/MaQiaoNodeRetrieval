package MaQiao.MaQiaoNodeRetrieval;

/**
 * 线程组常量
 * @author Sunjian
 * @since 1.7
 * @version 1.0
 */
public final class ThreadNodeConsts {

	/**
	 * 静态化线程组
	 * @author Sunjian
	 */
	public static final class nodeThreadRun implements Runnable {
		@SuppressWarnings("unused")
		private int locked = 0;
		volatile boolean working = true;
		volatile boolean stopped = false;
		volatile boolean forward = false;
		volatile ThreadNodeAttributeAbstract nodeAttrib = null;

		private nodeThreadRun() {
		}

		/**
		 * 初始化
		 * @param nodeAttr
		 * @param forward
		 */
		public nodeThreadRun(ThreadNodeAttributeAbstract nodeAttr, boolean forward) {
			this.forward = forward;
			init(nodeAttr, false);
		}

		/**
		 * 得到线程锁，设置参数
		 * @param nodeAttr ThreadNodeAttributeAbstract
		 * @param working boolean
		 */
		public void init(final ThreadNodeAttributeAbstract nodeAttr, final boolean working) {
			lock();
			try {
				this.nodeAttrib = nodeAttr;
				this.working = working;
			} finally {
				unLock();
			}
		}

		@Override
		public void run() {
			//System.out.println("Static " + Thread.currentThread().getName() + " starting.");
			try {
				while (!stopped) {
					if (this.working && nodeAttrib != null) {
						lock();
						try {
							if (forward) {
								nodeAttrib.forward();
								nodeAttrib.stateFinishForward = true;
							} else {
								nodeAttrib.next();
								nodeAttrib.stateFinishNext = true;
							}
						} finally {
							this.working = false;
							this.nodeAttrib = null;
							unLock();
						}
					}
				}
			} catch (Exception exc) {
				//System.out.println("Static " + Thread.currentThread().getName() + " interrupted.");
			}
			//System.out.println("Static " + Thread.currentThread().getName() + " exiting.");

		}

		synchronized void Stop() {
			stopped = true;
		}

		synchronized void setWorkStop() {
			working = false;
		}

		/**
		 * 锁定对象
		 */
		private final void lock() {
			//System.out.println("locking........");
			while (!Consts.UNSAFE.compareAndSwapInt(this, lockedOffset, 0, 1)) {
			}
		}

		/**
		 * 释放对象锁
		 */
		private final void unLock() {
			//System.out.println("unLock........");
			while (!Consts.UNSAFE.compareAndSwapInt(this, lockedOffset, 1, 0)) {
			}
		}

	}

	static long lockedOffset = 0L;
	/**
	 * 线程组
	 */
	public static final ThreadGroup nodeThreadGroup = new ThreadGroup("extNode Group");
	/**
	 * 线程1，向前的线程
	 */
	public static final nodeThreadRun nodeThread1 = new nodeThreadRun();
	/**
	 * 线程2，向后的线程
	 */
	public static final nodeThreadRun nodeThread2 = new nodeThreadRun();
	static {
		try {
			lockedOffset = Consts.UNSAFE.objectFieldOffset(nodeThreadRun.class.getDeclaredField("locked"));/*得到锁对象的偏移量*/
			{
				nodeThread1.nodeAttrib = null;
				nodeThread1.forward = false;
			}
			Thread myth1 = new Thread(nodeThreadGroup, nodeThread1);
			myth1.setName("MyThread #1 ");
			{
				nodeThread1.nodeAttrib = null;
				nodeThread1.forward = true;
			}
			Thread myth2 = new Thread(nodeThreadGroup, nodeThread2);
			myth2.setName(" MyThread #2 ");
			myth1.start();
			myth2.start();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}