package MaQiao.MaQiaoNodeRetrieval;

/**
 * 节点 抽象类
 * @author Sunjian
 * @since 1.7
 * @version 1.0
 */
public abstract class extNode {
	extNode Forward = null;

	public extNode getForward() {
		return Forward;
	}

	public void setForward(final extNode f) {
		this.Forward = f;
	}

	extNode Next = null;

	public extNode getNext() {
		return Next;
	}

	public void setNext(final extNode f) {
		this.Next = f;
	}

	/**
	 * 节点的标识值
	 * @return long
	 */
	public abstract long extNodeValue();

	/**
	 * 对象的 System.identityHashCode
	 * @return long
	 */
	public long iNodeSystemHashCode() {
		return System.identityHashCode(this);
	}

}
