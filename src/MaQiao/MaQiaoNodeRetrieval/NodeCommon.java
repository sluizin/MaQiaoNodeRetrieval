package MaQiao.MaQiaoNodeRetrieval;

import static MaQiao.MaQiaoNodeRetrieval.Consts.sequence;

/**
 * 链表通用方法<br/>
 * <font color='red'>注意：rndOne节点为随机节点(不代表链表头或链表尾)</font><br/>
 * @author Sunjian
 * @since 1.7
 * @version 1.0
 */
public final class NodeCommon {
	/**
	 * 随机节点 -> 得到此链表中标识数的节点<br/>
	 * @param rndOne iNode
	 * @param value long
	 * @return iNode
	 */
	public static final iNode NodeSearchByValue(final iNode rndOne, final long value) {
		if (rndOne == null) return null;
		iNode p = null;
		for (p = rndOne.getForward(); p != null; p = p.getForward())
			if (p.iNodeValue() == value) return p;
		for (p = rndOne.getNext(); p != null; p = p.getNext())
			if (p.iNodeValue() == value) return p;
		return null;
	}

	/**
	 * 随机节点 -> 把节点P插入到随机节点所在的指定序列编号之中<br/>
	 * @param rndOne iNode
	 * @param p iNode
	 * @param i int
	 * @return boolean
	 */
	public static final boolean NodeInsertByNodeNum(final iNode rndOne, final iNode p, final int i) {
		if (rndOne == null || p == null || i < 0 || i >= NodeLength(rndOne)) return false;
		final iNode iPoint = NodeLocationArrangeNode(rndOne, i);
		if (iPoint == null) return false;
		return NodeInsertByNodeForward(iPoint, p);
	}

	/**
	 * 随机节点 -> 把节点P插入到随机节点前<br/>
	 * @param rndOne iNode
	 * @param p iNode
	 * @return boolean
	 */
	public static final boolean NodeInsertByNodeForward(final iNode rndOne, final iNode p) {
		if (rndOne == null || p == null) return false;
		p.setForward(rndOne.getForward());
		p.setNext(rndOne);
		rndOne.setForward(p);
		return true;
	}

	/**
	 * 随机节点 -> 把节点P插入到随机节点后<br/>
	 * @param rndOne iNode
	 * @param p iNode
	 * @return boolean
	 */
	public static final boolean NodeInsertByNodeNext(final iNode rndOne, final iNode p) {
		if (rndOne == null || p == null) return false;
		p.setForward(rndOne);
		p.setNext(rndOne.getNext());
		rndOne.setNext(p);
		return true;
	}

	/**
	 * 随机节点 -> 在链表中的排序位置<br/>
	 * 第一位:0 <br/>
	 * @param rndOne iNode
	 * @return int
	 */
	public static final int NodeLocationArrangeNum(final iNode rndOne) {
		int Num = 0;
		for (iNode p = rndOne; p != null; p = p.getForward())
			if (p.getForward() == null) return Math.abs(Num);
			else Num--;
		return -1;
	}

	/**
	 * 随机节点 -> 得到此链表的指定位置的节点<br/>
	 * @param rndOne iNode
	 * @param i int
	 * @return iNode
	 */
	public static final iNode NodeLocationArrangeNode(final iNode rndOne, final int i) {
		final iNode start = NodeLocationStart(rndOne);
		int Num = 0;
		for (iNode p = start; p != null; p = p.getNext())
			if (Num == i) return p;
			else Num++;
		return null;

	}

	/**
	 * 随机节点 -> 查找链表最后一位节点<br/>
	 * @param rndOne iNode
	 * @return iNode
	 */
	public static final iNode NodeLocationEnd(final iNode rndOne) {
		for (iNode p = rndOne; p != null; p = p.getNext())
			if (p.getNext() == null) return p;
		return null;
	}

	/**
	 * 随机节点 -> 查找链表第一位节点<br/>
	 * @param rndOne iNode
	 * @return iNode
	 */
	public static final iNode NodeLocationStart(final iNode rndOne) {
		for (iNode p = rndOne; p != null; p = p.getForward())
			if (p.getForward() == null) return p;
		return rndOne;
	}

	/**
	 * 随机节点 -> 得到链表的长度<br/>
	 * @param rndOne iNode
	 * @return long
	 */
	public static final long NodeLength(final iNode rndOne) {
		if (rndOne == null) return 0;
		long Num = 1;
		iNode p = null;
		for (p = rndOne.getForward(); p != null; p = p.getForward())
			Num++;
		for (p = rndOne.getNext(); p != null; p = p.getNext())
			Num++;
		return Num;
	}

	/**
	 * 把节点移出链表<br/>
	 * 注意：只移出一个节点，如果出现多个相同 iNodeValue() 的，则只移出第一个<br/>
	 * @param start iNode
	 * @param end iNode
	 * @param delEntry iNode
	 * @return boolean
	 */
	public static final boolean NodeRemove(final iNode start, final iNode end, final iNode delEntry) {
		if (start == null) return false;
		for (iNode p = start; p != null; p = p.getNext()) {
			if (p == delEntry || p.iNodeValue() == delEntry.iNodeValue()) {
				if (p.getForward() != null) p.getForward().getNext().setNext(p.getNext());
				if (p.getNext() != null) p.getNext().getForward().setForward(p.getForward());
				NodeClear(p);
				return true;
			}
			if (p == end) break;
		}
		return false;
	}

	/**
	 * 针对节点的清空。并不影响链表。<br/>
	 * 但如果清空节点，则使用 {@link MaQiao.MaQiaoNodeRetrieval#NodeRemove NodeRemove()} 方法<br/>
	 * @param p iNode
	 */
	public static final void NodeClear(final iNode p) {
		if (p == null) return;
		p.setForward(null);
		p.setNext(null);
	}

	/**
	 * 判断链表段的顺序<br/>
	 * 可到达end或null<br/>
	 * Consts.sequence.Reverse:从大到小<br/>
	 * Consts.sequence.Ordering:从小到大<br/>
	 * Consts.sequence.Outoforder:乱序<br/>
	 * @param start iNode
	 * @param end iNode
	 * @return Consts.sequence
	 */
	public static final sequence NodeSequence(final iNode start, final iNode end) {
		return sequence.getSequence(NodeSequenceInt(start, end));
	}

	/**
	 * 判断链表段的顺序<br/>
	 * 1:从大到小<br/>
	 * 0:从小到大<br/>
	 * -1:乱序<br/>
	 * @param start iNode
	 * @param end iNode
	 * @return int
	 */
	/* @Deprecated */
	public static final int NodeSequenceInt(final iNode start, final iNode end) {
		boolean boolPold = false, isFirst = true;
		long code1, code2;
		for (iNode p = start, comp = (p != null) ? p.getNext() : null; p != null && comp != null; comp = ((p = p.getNext()) == null) ? null : p.getNext()) {
			if (p == end) break;
			if ((code1 = p.iNodeValue()) == (code2 = comp.iNodeValue())) continue;
			if (!isFirst || (isFirst = (!isFirst))) if (boolPold ^ code1 < code2) return -1;//乱序
			else continue;
			boolPold = code1 < code2;//第一次得到 <比较> 结果
		}
		if (boolPold) return 0;//从小到大
		return 1;//从大到小
	}
}
