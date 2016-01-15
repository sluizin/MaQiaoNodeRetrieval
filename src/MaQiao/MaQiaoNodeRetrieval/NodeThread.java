package MaQiao.MaQiaoNodeRetrieval;

/**
 * 遍历线程对象
 * @author Sunjian
 * @since 1.7
 * @version 1.0
 */
public class NodeThread extends Thread {

	volatile boolean work = false;
	volatile boolean stopped = false;
	volatile boolean forward = false;
	volatile ThreadNodeAttribute nodeAttr = null;

	public NodeThread(ThreadGroup tg, String name) {
		super(tg, name);
		this.stopped = false;
	}

	public NodeThread(ThreadGroup tg, String name, ThreadNodeAttribute nodeAttr, boolean forward) {
		super(tg, name);
		this.stopped = false;
		this.nodeAttr = nodeAttr;
		this.forward = forward;
		this.work = true;
	}

	public void run() {
		System.out.println(Thread.currentThread().getName() + " starting.");
		extNode p = null;
		try {
			while (!stopped) {
				//Thread.sleep(1000);
				if (work) {
					if (nodeAttr != null) {
						for (p = nodeAttr.input; p != null; p = (forward) ? p.Forward : p.Next) {
							System.out.println(Thread.currentThread().getName() + ":" + NodeCommon.toString(p));
							if (nodeAttr.compare(p)) {
								if (forward) {
									nodeAttr.outputForward = p;
									nodeAttr.lockedForward = 1;
								} else {
									nodeAttr.outputNext = p;
									nodeAttr.lockedNext = 1;
								}
								break;
							}
							//Thread.sleep(250);
						}
						if (forward) {
							nodeAttr.lockedForward = 1;
						} else {
							nodeAttr.lockedNext = 1;
						}
						System.out.println(Thread.currentThread().getName() + " -> over");
						work = false;
						nodeAttr = null;
						break;
					}

				}
			}
		} catch (Exception exc) {
			System.out.println(Thread.currentThread().getName() + " interrupted.");
		}
		System.out.println(Thread.currentThread().getName() + " exiting.");
	}

	synchronized void Stop() {
		stopped = true;
	}
}
