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
	protected extNode nodeRunBegin = null;
	/**
	 * 线程运行状态[向前]，是否已检索完<br/>
	 */
	boolean stateFinishForward = false;
	/**
	 * 线程运行状态[向后]，是否已检索完<br/>
	 */
	boolean stateFinishNext = false;

	/**
	 * 得到两个线程是否完成
	 * @return boolean
	 */
	public final boolean getDoubleFindSuccess() {
		return stateFinishForward & stateFinishNext;
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
	 * <code>for (extNode p = nodeRunBegin; p != null; p = p.Forward) {<br/>
	 * }</code><br/>
	 * 循环条件可以选择，可不进行全程检索<br/>
	 */
	public abstract void forward();

	/**
	 * 从 input节点 开始向后检索<br/>
	 * <code>for (extNode p = nodeRunBegin; p != null; p = p.Forward) {<br/>
	 * }</code><br/>
	 * 循环条件可以选择，可不进行全程检索<br/>
	 */
	public abstract void next();

}
