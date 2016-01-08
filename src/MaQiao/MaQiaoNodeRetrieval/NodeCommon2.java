package MaQiao.MaQiaoNodeRetrieval;

import static MaQiao.MaQiaoNodeRetrieval.Consts.sequence;
/**
 * 链表通用方法<br/>
 * <font color='red'>注意：rndOne节点为随机节点(不代表链表头或链表尾)</font><br/>
 * @author Sunjian
 * @since 1.7
 * @version 1.0
 */
public final class NodeCommon2 {
	/**
	 * 随机节点 -> 得到此链表中标识数的节点<br/>
	 * @param rndOne aNode
	 * @param value long
	 * @return aNode
	 */
	public static final extNode NodeSearchByValue(final extNode rndOne, final long value) {
		if (rndOne == null) return null;
		extNode p = null;
		for (p = rndOne.getForward(); p != null; p = p.getForward())
			if (p.extNodeValue() == value) return p;
		for (p = rndOne.getNext(); p != null; p = p.getNext())
			if (p.extNodeValue() == value) return p;
		return null;
	}

	/**
	 * 随机节点 -> 把节点P插入到随机节点所在的指定序列编号之中<br/>
	 * @param rndOne aNode
	 * @param p aNode
	 * @param i int
	 * @return boolean
	 */
	public static final boolean NodeInsertByNodeNum(final extNode rndOne, final extNode p, final int i) {
		if (rndOne == null || p == null || i < 0 || i >= NodeLength(rndOne)) return false;
		final extNode iPoint = NodeLocationArrangeNode(rndOne, i);
		if (iPoint == null) return false;
		return NodeInsertByNodeForward(iPoint, p);
	}

	/**
	 * 随机节点 -> 把节点P插入到随机节点前<br/>
	 * @param rndOne aNode
	 * @param p aNode
	 * @return boolean
	 */
	public static final boolean NodeInsertByNodeForward(final extNode rndOne, final extNode p) {
		if (rndOne == null || p == null) return false;
		p.setForward(rndOne.getForward());
		p.setNext(rndOne);
		if(rndOne.getForward()!=null)rndOne.getForward().setNext(p);
		rndOne.setForward(p);
		return true;
	}

	/**
	 * 随机节点 -> 把节点P插入到随机节点后<br/>
	 * @param rndOne aNode
	 * @param p aNode
	 * @return boolean
	 */
	public static final boolean NodeInsertByNodeNext(final extNode rndOne, final extNode p) {
		if (rndOne == null || p == null) return false;
		p.setForward(rndOne);
		p.setNext(rndOne.getNext());
		if(rndOne.getNext()!=null)rndOne.getNext().setForward(p);
		rndOne.setNext(p);
		return true;
	}

	/**
	 * 随机节点 -> 在链表中的排序位置<br/>
	 * 第一位:0 <br/>
	 * @param rndOne aNode
	 * @return int
	 */
	public static final int NodeLocationArrangeNum(final extNode rndOne) {
		int Num = 0;
		for (extNode p = rndOne; p != null; p = p.getForward())
			if (p.getForward() == null) return Math.abs(Num);
			else Num--;
		return -1;
	}

	/**
	 * 随机节点 -> 得到此链表的指定位置的节点<br/>
	 * @param rndOne aNode
	 * @param i int
	 * @return aNode
	 */
	public static final extNode NodeLocationArrangeNode(final extNode rndOne, final int i) {
		final extNode start = NodeLocationStart(rndOne);
		int Num = 0;
		for (extNode p = start; p != null; p = p.getNext())
			if (Num == i) return p;
			else Num++;
		return null;

	}

	/**
	 * 随机节点 -> 查找链表最后一位节点<br/>
	 * @param rndOne aNode
	 * @return aNode
	 */
	public static final extNode NodeLocationEnd(final extNode rndOne) {
		for (extNode p = rndOne; p != null; p = p.getNext())
			if (p.getNext() == null) return p;
		return null;
	}

	/**
	 * 随机节点 -> 查找链表第一位节点<br/>
	 * @param rndOne aNode
	 * @return aNode
	 */
	public static final extNode NodeLocationStart(final extNode rndOne) {
		for (extNode p = rndOne; p != null; p = p.getForward())
			if (p.getForward() == null) return p;
		return rndOne;
	}

	/**
	 * 随机节点 -> 得到链表的长度<br/>
	 * @param rndOne aNode
	 * @return long
	 */
	public static final long NodeLength(final extNode rndOne) {
		if (rndOne == null) return 0;
		long Num = 1;
		extNode p = null;
		for (p = rndOne.getForward(); p != null; p = p.getForward())
			Num++;
		for (p = rndOne.getNext(); p != null; p = p.getNext())
			Num++;
		return Num;
	}

	/**
	 * 随机节点 -> 打印出节点toString()[独立节点]<br/>
	 * @param rndOne aNode
	 */
	public static final void toPrint(final extNode rndOne) {
		if(rndOne==null)return;
		System.out.println("\t:" + rndOne.toString());
	}
	/**
	 * 随机节点 -> 打印出所在的链表toString()[向前]<br/>
	 * @param rndOne aNode
	 */
	public static final void toPrintForward(final extNode rndOne) {
		int i = 0;
		for (extNode p = rndOne; p != null; p = p.getForward())
			System.out.println((i--) + "\t:" + p.toString());
	}

	/**
	 * 随机节点 -> 打印出所在的链表toString()[向后]<br/>
	 * @param rndOne aNode
	 */
	public static final void toPrintNext(final extNode rndOne) {
		int i = 0;
		for (extNode p = rndOne; p != null; p = p.getNext())
			System.out.println((i++) + "\t:" + p.toString());
	}

	/**
	 * 随机节点 -> 打印出所在链表toString()[所有]<br/>
	 * @param rndOne aNode
	 */
	public static final void toPrintAll(final extNode rndOne) {
		int i = 0;
		extNode start = NodeLocationStart(rndOne);
		for (extNode p = start; p != null; p = p.getNext())
			System.out.println((i++) + "\t:" + p.toString());
	}

	/**
	 * 把节点移出链表<br/>
	 * 注意：只移出一个节点，如果出现多个相同 aNodeValue() 的，则只移出第一个<br/>
	 * @param start aNode
	 * @param end aNode
	 * @param delEntry aNode
	 * @return boolean
	 */
	public static final boolean NodeRemove(final extNode start, final extNode end, final extNode delEntry) {
		if (start == null) return false;
		for (extNode p = start; p != null; p = p.getNext()) {
			if (p == delEntry || p.extNodeValue() == delEntry.extNodeValue()) {
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
	 * @param p aNode
	 */
	public static final void NodeClear(final extNode p) {
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
	 * @param start aNode
	 * @param end aNode
	 * @return Consts.sequence
	 */
	public static final sequence NodeSequence(final extNode start, final extNode end) {
		return sequence.getSequence(NodeSequenceInt(start, end));
	}

	/**
	 * 判断链表段的顺序<br/>
	 * 1:从大到小<br/>
	 * 0:从小到大<br/>
	 * -1:乱序<br/>
	 * @param start aNode
	 * @param end aNode
	 * @return int
	 */
	/* @Deprecated */
	public static final int NodeSequenceInt(final extNode start, final extNode end) {
		boolean boolPold = false, isFirst = true;
		long code1, code2;
		for (extNode p = start, comp = (p != null) ? p.getNext() : null; p != null && comp != null; comp = ((p = p.getNext()) == null) ? null : p.getNext()) {
			if (p == end) break;
			if ((code1 = p.extNodeValue()) == (code2 = comp.extNodeValue())) continue;
			if (!isFirst || (isFirst = (!isFirst))) if (boolPold ^ code1 < code2) return -1;//乱序
			else continue;
			boolPold = code1 < code2;//第一次得到 <比较> 结果
		}
		if (boolPold) return 0;//从小到大
		return 1;//从大到小
	}
}
