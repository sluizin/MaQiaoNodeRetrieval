package MaQiao.MaQiaoNodeRetrieval;

public final class nodeThreadRun implements Runnable {
	/**
	 * 设置锁状态{空[0]；修改前[2]；修改后[4]；运行前[8]}
	 */
	private int locked = 0;
	boolean forward = false;
	ThreadNodeAttributeAbstract nodeAttrib = null;

	/**
	 * 得到此线程的名称
	 * @return String
	 */
	public String getName() {
		Thread t = getThreadthis(this);
		if (t != null) return t.getName();
		return "not find!!!";
	}

	public nodeThreadRun() {
		initClass(null, false);
	}

	/**
	 * 初始化
	 * @param nodeAttr
	 */
	public nodeThreadRun(ThreadNodeAttributeAbstract nodeAttr) {
		initClass(nodeAttr, false);
	}

	/**
	 * 初始化
	 * @param nodeAttr
	 * @param forward
	 */
	public nodeThreadRun(ThreadNodeAttributeAbstract nodeAttr, boolean forward) {
		initClass(nodeAttr, forward);
	}

	public void initClass(final ThreadNodeAttributeAbstract nodeAttr, final boolean forward) {
		if (!init(nodeAttr, forward)) throw new Error("nodeThreadRun().init Error");
	}

	/**
	 * 得到线程锁，设置参数
	 * @param nodeAttr ThreadNodeAttributeAbstract
	 * @param forward boolean
	 * @return boolean
	 */
	public boolean init(final ThreadNodeAttributeAbstract nodeAttr, final boolean forward) {
		if (isFree()) {
			if (Consts.isOpenUNSAFEPark) if (isPark) unpark();
			CASlockInt(0, 2);
			this.nodeAttrib = nodeAttr;
			this.forward = forward;
			locked = 4;
			//CASlockInt(2, 4);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void run() {
		//System.out.println("Static " + Thread.currentThread().getName() + " starting.");
		try {
			while (true) {
				//System.out.println("XXX:" + locked);
				if (testLock(4)) {
					//if (locked == 4) {
					if (null != this.nodeAttrib) {
						CASlockInt(4, 8);
						try {
							if (forward) {
								nodeAttrib.forward();
								nodeAttrib.stateFinishForward = true;
							} else {
								nodeAttrib.next();
								nodeAttrib.stateFinishNext = true;
							}
						} finally {
							this.nodeAttrib = null;
							locked = 0;
							//CASlockInt(8, 0);
						}
					} else {
						locked = 0;
						//CASlockInt(4, 0);
					}
				}
			}
		} catch (Exception exc) {
			//System.out.println("Static " + Thread.currentThread().getName() + " interrupted.");
		}
		//System.out.println("Static " + Thread.currentThread().getName() + " exiting.");

	}

	/**
	 * 判断此线程是否已挂起
	 */
	@Deprecated
	boolean isPark = false;

	/**
	 * 挂起本线程
	 */
	public final void park() {
		Consts.UNSAFE.park(true, 0L);
		isPark = true;
	}

	/**
	 * 恢复本线程
	 */
	public final void unpark() {
		Thread t = getThreadthis(this);
		if (t != null) Consts.UNSAFE.unpark(t);
		isPark = false;
	}

	/**
	 * 使用 compareAndSwapInt 设置锁状态{空[0]；修改前[2]；修改后[4]；运行前[8]}
	 * @param source int
	 * @param to int
	 */
	final void CASlockInt(final int source, final int to) {
		while (!Consts.UNSAFE.compareAndSwapInt(this, lockedOffset, source, to)) {
		}
	}

	final boolean testLock(final int value) {
		return Consts.UNSAFE.compareAndSwapInt(this, lockedOffset, value, value);
	}

	@Deprecated
	public final boolean isFreeDepr() {
		return locked == 0;
	}

	public final boolean isFree() {
		//System.out.println(this.getName() + "[isFree](boolean):" + Consts.UNSAFE.compareAndSwapInt(this, lockedOffset, 0, 0));
		if (Consts.isOpenUNSAFEPark) return isPark || Consts.UNSAFE.compareAndSwapInt(this, lockedOffset, 0, 0);
		return Consts.UNSAFE.compareAndSwapInt(this, lockedOffset, 0, 0);
	}

	/*得到锁对象的偏移量*/
	static long lockedOffset = 0L;
	static {
		try {
			lockedOffset = Consts.UNSAFE.objectFieldOffset(nodeThreadRun.class.getDeclaredField("locked"));/*得到锁对象的偏移量*/
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 得到nodeThreadRun所在的线程组中的Thread
	 * @param obj nodeThreadRun
	 * @return Thread
	 */
	public static final Thread getThreadthis(final nodeThreadRun obj) {
		final Thread threadArray[] = new Thread[ThreadNodeConsts.nodeThreadGroup.activeCount()];
		ThreadNodeConsts.nodeThreadGroup.enumerate(threadArray);
		for (Thread t : threadArray)
			if (getNodeThreadRun(t) == obj) return t;
		return null;
	}

	public static final nodeThreadRun getNodeThreadRun(final Thread t) {
		//if(t.isAlive())return null;
		return (nodeThreadRun) Consts.UNSAFE.getObject(t, Consts.ThreadRunnableOffset);
	}
}
