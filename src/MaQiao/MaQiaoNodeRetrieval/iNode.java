package MaQiao.MaQiaoNodeRetrieval;

/**
 * 节点的接口
 * @author Sunjian
 * @since 1.7
 * @version 1.0
 */
public interface iNode{
	/**
	 * 链表前端节点
	 * @return iNode
	 */
	public iNode getForward();

	/**
	 * 设置链表前端节点
	 * @param f iNode
	 */
	public void setForward(final iNode f);

	/**
	 * 链表后端节点
	 * @return iNode
	 */
	public iNode getNext();

	/**
	 * 设置链表后端节点
	 * @param f iNode
	 */
	public void setNext(final iNode f);

	/**
	 * 接口值 或 标识数
	 * @return long
	 */
	public long iNodeValue();
	/**
	 * 节点 系统级HashCode值
	 * @return long
	 */
	public long iNodeSystemHashCode();
}
