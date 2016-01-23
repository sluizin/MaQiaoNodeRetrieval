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
	 *
	 */
	public static final class nodeThreadRun implements Runnable {
		@SuppressWarnings("unused")
		private int locked = 0;
		volatile boolean working = true;
		volatile boolean stopped = false;
		volatile boolean forward = false;
		volatile ThreadNodeAttributeAbstract nodeAttr = null;

		public nodeThreadRun() {
		}

		public nodeThreadRun(ThreadNodeAttributeAbstract nodeAttr, boolean forward) {
			this.nodeAttr = nodeAttr;
			this.forward = forward;
		}

		@Override
		public void run() {
			//System.out.println("Static " + Thread.currentThread().getName() + " starting.");
			try {
				while (true) {
					if (this.working && nodeAttr != null) {
						lock();
						if (forward) {
							/*							for (p = nodeAttr.input; (nodeAttr.nextFinish==0) && p != null; p = p.Forward) {
															if (nodeAttr.compare(p)) {
																nodeAttr.forwardNode = p;
																break;
															}
														}*/
							nodeAttr.forward();
							nodeAttr.forwardFinish = 1;

						} else {
							/*							for (p = nodeAttr.input;(nodeAttr.forwardFinish==0) && p != null; p = p.Next) {
															if (nodeAttr.compare(p)) {
																nodeAttr.nextNode = p;
																break;
															}
														}*/

							nodeAttr.next();
							nodeAttr.nextFinish = 1;
						}
						//System.out.println(Thread.currentThread().getName() + " -> over");
						this.working = false;
						this.nodeAttr = null;
						unLock();
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

		synchronized void WorkStop() {
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
				nodeThread1.nodeAttr = null;
				nodeThread1.forward = false;
			}
			Thread myth1 = new Thread(nodeThreadGroup, nodeThread1);
			myth1.setName("MyThread #1 ");
			{
				nodeThread1.nodeAttr = null;
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