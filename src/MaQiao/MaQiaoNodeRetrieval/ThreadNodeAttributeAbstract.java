package MaQiao.MaQiaoNodeRetrieval;

/**
 * 节点抽象类
 * @version 1.0
 * @author Sunjian
 * @since 1.7
 */
public abstract class ThreadNodeAttributeAbstract {
	/**
	 * 线程运行启始节点<br/>
	 */
	extNode input = null;
	/**
	 * 线程运行状态[向前]<br/>
	 * 0:未完成<br/>
	 * 1:已完成<br/>
	 */
	int forwardFinish = 0;
	/**
	 * 线程运行状态[向后]<br/>
	 * 0:未完成<br/>
	 * 1:已完成<br/>
	 */
	int nextFinish = 0;
	/**
	 * 输出的节点[向前]
	 */
	extNode forwardNode = null;
	/**
	 * 输出的节点[向后]
	 */
	extNode nextNode = null;

	/**
	 * 得到两个线程是否完成
	 * @return boolean
	 */
	public final boolean getDoubleFindSuccess() {
		return ((forwardFinish + nextFinish) == 2);
	}

	/**
	 * 判断其它线程已经查出结果
	 * @return boolean
	 */
	public final boolean isFinded() {
		return (forwardNode != null || nextNode != null || getDoubleFindSuccess());
	}

	/**
	 * 得到结果节点，只得到一个即可
	 * @return extNode
	 */
	public final extNode getResult() {
		extNode p = null;
		if (getDoubleFindSuccess()) {
			p = (forwardNode != null) ? forwardNode : nextNode;
		}
		return p;
	}

	/**
	 * 搜索出来的节点 进行比较<br/>
	 * 预留
	 * @param p extNode
	 * @return boolean
	 */
	@Deprecated
	public abstract boolean compare(extNode p);

	/**
	 * 从 input节点 开始向前检索<br/>
	 * <code>for (extNode p = input; p != null; p = p.Forward) {</code><br/>
	 * 循环条件可以选择，可不进行全程检索<br/>
	 */
	public abstract void forward();

	/**
	 * 从 input节点 开始向后检索<br/>
	 * <code>for (extNode p = input; p != null; p = p.Forward) {</code><br/>
	 * 循环条件可以选择，可不进行全程检索<br/>
	 */
	public abstract void next();

}
