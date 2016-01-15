package MaQiao.MaQiaoNodeRetrieval;

public abstract class ThreadNodeAttribute {
	extNode input = null;
	int lockedForward = 0;
	int lockedNext = 0;
	extNode outputForward = null;
	extNode outputNext = null;

	public final boolean getResultSuccess() {
		return ((lockedForward + lockedNext) == 2);
	}

	public final extNode getResult() {
		extNode p = null;
		if (getResultSuccess()) {
			p = (outputForward != null) ? outputForward : outputNext;
		}
		return p;
	}

	/**
	 * 搜索出来的节点 进行比较
	 * @param p extNode
	 * @return boolean
	 */
	public abstract boolean compare(extNode p);

}
