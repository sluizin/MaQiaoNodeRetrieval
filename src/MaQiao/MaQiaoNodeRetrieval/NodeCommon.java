package MaQiao.MaQiaoNodeRetrieval;

import static MaQiao.MaQiaoNodeRetrieval.Consts.sequence;

/**
 * 链表通用方法<br/>
 * <font color='red'>注意：rndNode节点为随机节点(不代表链表头或链表尾)</font><br/>
 * @author Sunjian
 * @since 1.7
 * @version 1.0
 */
public final class NodeCommon {
	/**
	 * 随机节点 -> 判断此链表中是否含此标识值的节点<br/>
	 * 以标识值为标准<br/>
	 * @param rndNode extNode
	 * @param value long
	 * @return boolean
	 */
	public static final boolean nodeSearchByValueExist(final extNode rndNode, final long value) {
		return (nodeSearchByValue(rndNode, value) == null) ? false : true;
	}

	/**
	 * 随机节点 -> 得到此链表中标识数的节点<br/>
	 * 以标识值为标准。如果出现重复，则选中第一个节点返回<br/>
	 * @param rndNode extNode
	 * @param value long
	 * @return extNode
	 */
	public static final extNode nodeSearchByValue(final extNode rndNode, final long value) {
		if (rndNode == null) return null;
		extNode p = null;
		for (p = rndNode.Forward; p != null; p = p.Forward)
			if (p.extNodeValue() == value) return p;
		for (p = rndNode.Next; p != null; p = p.Next)
			if (p.extNodeValue() == value) return p;
		return null;
	}

	/**
	 * 随机节点 -> 把节点P插入到含有随机节点的链表中[有序]<br/>
	 * 注意：先要判断链表的顺序，如果出现乱序，则插入到随机节点后面<br/>
	 * @param rndNode extNode
	 * @param newNode extNode
	 * @return boolean
	 */
	public static final boolean nodeInsertByNodeCompositor(final extNode rndNode, final extNode newNode) {
		if (rndNode == null || newNode == null) return false;
		final extNode start = nodeLocationStart(rndNode);
		switch (nodeSequenceInt(start, null)) {
		case 1:
			for (extNode p = start; p != null; p = p.Next) {
				if (newNode.extNodeValue() >= p.extNodeValue()) return nodeInsertByNodeForward(p, newNode);
				if (p.Next == null) return nodeInsertByNodeNext(p, newNode);/* 到达链尾，没有小于标识的位置，则直接加到链尾 */
			}
			break;
		case 0:
			for (extNode p = start; p != null; p = p.Next) {
				if (newNode.extNodeValue() <= p.extNodeValue()) return nodeInsertByNodeForward(p, newNode);
				if (p.Next == null) return nodeInsertByNodeNext(p, newNode);/* 没有大于标识的位置，则直接加到链尾 */
			}
			break;
		default:
			return nodeInsertByNodeNext(rndNode, newNode);
		}
		return false;
	}

	/**
	 * 随机节点 -> 把节点P插入到随机节点所在的指定序列编号之中<br/>
	 * @param rndNode extNode
	 * @param p extNode
	 * @param i int
	 * @return boolean
	 */
	public static final boolean nodeInsertByNodeNum(final extNode rndNode, final extNode p, final int i) {
		if (rndNode == null || p == null || i < 0 || i >= nodeLength(rndNode)) return false;
		final extNode iPoint = nodeLocationArrangeNode(rndNode, i);
		if (iPoint == null) return false;
		return nodeInsertByNodeForward(iPoint, p);
	}

	/**
	 * 随机节点 -> 把节点P插入到随机节点前<br/>
	 * @param rndNode extNode
	 * @param p extNode
	 * @return boolean
	 */
	public static final boolean nodeInsertByNodeForward(final extNode rndNode, final extNode p) {
		if (rndNode == null || p == null) return false;
		p.Forward = rndNode.Forward;
		p.Next = rndNode;
		if (rndNode.Forward != null) rndNode.Forward.Next = p;
		rndNode.Forward = p;
		return true;
	}

	/**
	 * 随机节点 -> 把节点P插入到随机节点后<br/>
	 * @param rndNode extNode
	 * @param p extNode
	 * @return boolean
	 */
	public static final boolean nodeInsertByNodeNext(final extNode rndNode, final extNode p) {
		if (rndNode == null || p == null) return false;
		p.Forward = rndNode;
		p.Next = rndNode.Next;
		if (rndNode.Next != null) rndNode.Next.Forward = p;
		rndNode.Next = p;
		return true;
	}

	/**
	 * 随机节点 -> 在链表中的排序位置<br/>
	 * 第一位:0 <br/>
	 * @param rndNode extNode
	 * @return int
	 */
	public static final int nodeLocationArrangeNum(final extNode rndNode) {
		int Num = 0;
		for (extNode p = rndNode; p != null; p = p.Forward)
			if (p.Forward == null) return Math.abs(Num);
			else Num--;
		return -1;
	}

	/**
	 * 随机节点 -> 得到此链表的指定位置的节点<br/>
	 * @param rndNode extNode
	 * @param i int
	 * @return extNode
	 */
	public static final extNode nodeLocationArrangeNode(final extNode rndNode, final int i) {
		final extNode start = nodeLocationStart(rndNode);
		int Num = 0;
		for (extNode p = start; p != null; p = p.Next)
			if (Num == i) return p;
			else Num++;
		return null;

	}

	/**
	 * 随机节点 -> 查找链表最后一位节点<br/>
	 * @param rndNode extNode
	 * @return extNode
	 */
	public static final extNode nodeLocationEnd(final extNode rndNode) {
		for (extNode p = rndNode; p != null; p = p.Next)
			if (p.Next == null) return p;
		return null;
	}

	/**
	 * 随机节点 -> 查找链表第一位节点<br/>
	 * @param rndNode extNode
	 * @return extNode
	 */
	public static final extNode nodeLocationStart(final extNode rndNode) {
		for (extNode p = rndNode; p != null; p = p.Forward)
			if (p.Forward == null) return p;
		return rndNode;
	}

	/**
	 * 随机节点 -> 得到链表的长度<br/>
	 * @param rndNode extNode
	 * @return long
	 */
	public static final long nodeLength(final extNode rndNode) {
		if (rndNode == null) return 0;
		long Num = 1;
		extNode p = null;
		for (p = rndNode.Forward; p != null; p = p.Forward)
			Num++;
		for (p = rndNode.Next; p != null; p = p.Next)
			Num++;
		return Num;
	}

	/**
	 * 随机节点 -> 打印出节点toString()[独立节点]<br/>
	 * @param rndNode extNode
	 */
	public static final void toPrint(final extNode rndNode) {
		if (rndNode == null) return;
		System.out.println("\t:" + rndNode.toString());
	}

	/**
	 * 随机节点 -> 打印出所在的链表toString()[向前]<br/>
	 * @param rndNode extNode
	 */
	public static final void toPrintForward(final extNode rndNode) {
		int i = 0;
		for (extNode p = rndNode; p != null; p = p.Forward)
			System.out.println((i--) + "\t:" + p.toString());
	}

	/**
	 * 随机节点 -> 打印出所在的链表toString()[向后]<br/>
	 * @param rndNode extNode
	 */
	public static final void toPrintNext(final extNode rndNode) {
		int i = 0;
		for (extNode p = rndNode; p != null; p = p.Next)
			System.out.println((i++) + "\t:" + p.toString());
	}

	/**
	 * 随机节点 -> 打印出所在链表toString()[所有]<br/>
	 * @param rndNode extNode
	 */
	public static final void toPrintAll(final extNode rndNode) {
		int i = 0;
		extNode start = nodeLocationStart(rndNode);
		for (extNode p = start; p != null; p = p.Next)
			System.out.println((i++) + "\t:" + p.toString());
	}

	/**
	 * 把节点移出链表<br/>
	 * 注意：只移出一个节点，如果出现多个相同 extNodeValue() 的，则只移出第一个<br/>
	 * @param start extNode
	 * @param end extNode
	 * @param delEntry extNode
	 * @return boolean
	 */
	public static final boolean nodeRemove(final extNode start, final extNode end, final extNode delEntry) {
		if (start == null) return false;
		for (extNode p = start; p != null; p = p.Next) {
			if (p == delEntry || p.extNodeValue() == delEntry.extNodeValue()) return nodeRemove(p);
			if (p == end) break;
		}
		return false;
	}

	/**
	 * 把节点移出链表<br/>
	 * 注意：只移出一个节点，如果出现多个相同 extNodeValue() 的，则只移出第一个<br/>
	 * @param start extNode
	 * @param end extNode
	 * @param delEntry extNode
	 * @return boolean
	 */
	public static final boolean nodeRemove(final extNode start, final extNode end, final long extNodeValue) {
		if (start == null) return false;
		for (extNode p = start; p != null; p = p.Next) {
			if (p.extNodeValue() == extNodeValue) return nodeRemove(p);
			if (p == end) break;
		}
		return false;
	}

	/**
	 * 把节点移出链表<br
	 * /
	 * @param p extNode
	 * @return boolean
	 */
	public static final boolean nodeRemove(final extNode p) {
		if (p == null) return false;
		if (p.Forward != null) p.Forward.Next = p.Next;
		if (p.Next != null) p.Next.Forward = p.Forward;
		nodeClear(p);
		return true;
	}

	/**
	 * 针对节点的清空。并不影响链表。<br/>
	 * 但如果清空节点，则使用 {@link MaQiao.MaQiaoNodeRetrieval#NodeRemove nodeRemove()} 方法<br/>
	 * @param p extNode
	 */
	public static final void nodeClear(final extNode p) {
		if (p == null) return;
		p.Forward = null;
		p.Next = null;
	}

	/**
	 * 判断链表段的顺序<br/>
	 * 可到达end或null<br/>
	 * Consts.sequence.Reverse:从大到小<br/>
	 * Consts.sequence.Ordering:从小到大<br/>
	 * Consts.sequence.Outoforder:乱序<br/>
	 * @param start extNode
	 * @param end extNode
	 * @return Consts.sequence
	 */
	public static final sequence nodeSequence(final extNode start, final extNode end) {
		return sequence.getSequence(nodeSequenceInt(start, end));
	}

	/**
	 * 判断链表段的顺序<br/>
	 * 1:从大到小<br/>
	 * 0:从小到大<br/>
	 * -1:乱序<br/>
	 * @param start extNode
	 * @param end extNode
	 * @return int
	 */
	/* @Deprecated */
	public static final int nodeSequenceInt(final extNode start, final extNode end) {
		boolean boolPold = false, isFirst = true;
		long code1, code2;
		for (extNode p = start, comp = (p != null) ? p.Next : null; p != null && comp != null; comp = ((p = p.Next) == null) ? null : p.Next) {
			if (p == end) break;
			if ((code1 = p.extNodeValue()) == (code2 = comp.extNodeValue())) continue;
			if (!isFirst || (isFirst = (!isFirst))) if (boolPold ^ code1 < code2) return -1;//乱序
			else continue;
			boolPold = code1 < code2;//第一次得到 <比较> 结果
		}
		if (boolPold) return 0;//从小到大
		return 1;//从大到小
	}

	/**
	 * 随机节点 -> 判断含有随机节点的链表的顺序<br/>
	 * 如果为空的随机节点，则返回-1<br/>
	 * 1:从大到小<br/>
	 * 0:从小到大<br/>
	 * -1:乱序<br/>
	 * @param rndNode extNode
	 * @return int
	 */
	/* @Deprecated */
	public static final int nodeSequenceInt(final extNode rndNode) {
		if (rndNode == null) return -1;
		return nodeSequenceInt(nodeLocationStart(rndNode), nodeLocationEnd(rndNode));
	}
}
