package MaQiao.MaQiaoNodeRetrieval;

import static MaQiao.MaQiaoNodeRetrieval.Consts.sequence;

import java.util.ArrayList;
import java.util.List;

import MaQiao.MaQiaoNodeRetrieval.ThreadNodeAttribList.nodeAttrSummary;
import MaQiao.MaQiaoNodeRetrieval.ThreadNodeConsts;

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
	public static final boolean searchByValueExist(final extNode rndNode, final long value) {
		return (searchByValue(rndNode, value) == null) ? false : true;
	}

	/**
	 * 随机节点 -> 判断此链表是否存在循环<br/>
	 * @param rndNode extNode
	 * @return boolean
	 */
	public static final boolean isCircle(final extNode rndNode) {
		if (rndNode == null) return false;
		List<Long> nodeList = new ArrayList<Long>();
		extNode p = null;
		long iNodeHC = 0L;
		for (p = rndNode; p != null; p = p.Next) {
			iNodeHC = p.iNodeSystemHashCode();
			if (nodeList.isEmpty() || !nodeList.contains(iNodeHC)) nodeList.add(iNodeHC);
			else return true;
		}
		for (p = rndNode.Forward; p != null; p = p.Forward) {
			iNodeHC = p.iNodeSystemHashCode();
			if (nodeList.isEmpty() || !nodeList.contains(iNodeHC)) nodeList.add(iNodeHC);
			else return true;
		}
		return false;
	}

	/**
	 * 随机节点 -> 判断此节点是否为链表头<br/>
	 * @param rndNode extNode
	 * @return boolean
	 */
	public static final boolean isLinkedHead(final extNode rndNode) {
		if (rndNode == null) return false;
		if (rndNode.Forward == null) return true;
		else return false;
	}

	/**
	 * 随机节点 -> 判断此节点是否为链表尾<br/>
	 * @param rndNode extNode
	 * @return boolean
	 */
	public static final boolean isLinkedEnd(final extNode rndNode) {
		if (rndNode == null) return false;
		if (rndNode.Next == null) return true;
		else return false;
	}

	/**
	 * 随机节点 -> 判断此节点是否存在此链表中<br/>
	 * 0:无效链表或节点<br/>
	 * 1:未检测到此节点<br/>
	 * 2:查到相同标识值的节点<br/>
	 * 8:查到相同系统级HashCode值的节点[对象相同]<br/>
	 * @param rndNode extNode
	 * @param p extNode
	 * @return int
	 */
	public static final int searchNode(final extNode rndNode, final extNode p) {
		if (rndNode == null || p == null) return 0;
		int t = 0;
		extNode p1 = null;
		for (p1 = rndNode; p1 != null; p1 = p1.Forward) {
			if (p.extNodeValue() == p1.extNodeValue()) t |= 2;
			if (p.iNodeSystemHashCode() == p1.iNodeSystemHashCode()) t |= 8;
			if (t > 1) return t;
		}
		for (p1 = rndNode.Next; p1 != null; p1 = p1.Next) {
			if (p.extNodeValue() == p1.extNodeValue()) t |= 2;
			if (p.iNodeSystemHashCode() == p1.iNodeSystemHashCode()) t |= 8;
			if (t > 1) return t;
		}
		return 1;
	}

	/**
	 * 随机节点 -> 链表原地转向<br/>
	 * @param rndNode extNode
	 * @return boolean
	 */
	public static final boolean turnDirection(final extNode rndNode) {
		if (rndNode == null) return false;
		extNode p = null;
		for (p = rndNode.Forward; p != null; p = p.Next)
			change(p);
		for (p = rndNode.Next; p != null; p = p.Forward)
			change(p);
		change(rndNode);
		return true;
	}

	/**
	 * 随机节点 -> 把无序或倒序链表转成正序<br/>
	 * @param rndNode extNode
	 * @return boolean
	 */
	public static final boolean orderingReset(final extNode rndNode) {
		if (rndNode == null) return false;
		final extNode start = locationStart(rndNode);
		extNode e = start.Next, f = null, p = null;
		clear(start);
		for (f = e == null ? null : e.Next, p = start; e != null; e = f, f = e == null ? null : e.Next, p = start) {
			if (e.extNodeValue() <= start.extNodeValue()) {
				/* 此值与start小 向前推 */
				for (; p != null; p = p.Forward) {
					if (p.extNodeValue() <= e.extNodeValue()) {
						insertByNodeNext(p, e);
						break;
					}
					if (p.Forward == null) {
						insertByNodeForward((p == start) ? start : p, e);
						break;
					}
				}
			} else {
				/* 此值与start大 向后推 */
				for (; p != null; p = p.Next) {
					if (p.extNodeValue() > e.extNodeValue()) {
						insertByNodeForward(p, e);
						break;
					}
					if (p.Next == null) {
						insertByNodeNext((p == start) ? start : p, e);
						break;
					}
				}
			}
		}
		return true;
	}

	/**
	 * 随机节点 -> 判断此链表中是否含有此HashCode的节点<br/>
	 * @param rndNode extNode
	 * @param hashcode long
	 * @return boolean
	 */
	public static final boolean isExistByHashCode(final extNode rndNode, final long hashcode) {
		return (searchByHashCode(rndNode, hashcode) == null) ? false : true;
	}

	/**
	 * 随机节点 -> 判断链表中是否含有此标识数的节点<br/>
	 * @param rndNode extNode
	 * @param value long
	 * @return boolean
	 */
	public static final boolean isExistByValue(final extNode rndNode, final long value) {
		return (searchByValue(rndNode, value) == null) ? false : true;
	}

	/**
	 * 随机节点 -> 得到此链表中HashCode的节点<br/>
	 * @param rndNode extNode
	 * @param hashcode long
	 * @return extNode
	 */
	public static final extNode searchByHashCode(final extNode rndNode, final long hashcode) {
		if (rndNode == null) return null;
		extNode p = null;
		for (p = rndNode.Forward; p != null; p = p.Forward)
			if (p.iNodeSystemHashCode() == hashcode) return p;
		for (p = rndNode.Next; p != null; p = p.Next)
			if (p.iNodeSystemHashCode() == hashcode) return p;
		return null;
	}

	/**
	 * 随机节点 -> 得到此链表中标识数的节点<br/>
	 * 以标识值为标准。如果出现重复，则选中第一个节点返回<br/>
	 * @param rndNode extNode
	 * @param value long
	 * @return extNode
	 */
	public static final extNode searchByValue(final extNode rndNode, final long value) {
		if (rndNode == null) return null;
		extNode p = null;
		for (p = rndNode.Forward; p != null; p = p.Forward)
			if (p.extNodeValue() == value) return p;
		for (p = rndNode.Next; p != null; p = p.Next)
			if (p.extNodeValue() == value) return p;
		return null;
	}

	/**
	 * 随机节点 -> 把节点P插入到含有随机节点的链表中[头部]<br/>
	 * 注意：不进行排序，直接插入链表头部<br/>
	 * @param rndNode extNode
	 * @param newNode extNode
	 * @return boolean
	 */
	public static final boolean insertByHead(final extNode rndNode, final extNode newNode) {
		if (rndNode == null || newNode == null) return false;
		for (extNode p = rndNode; p != null; p = p.Forward)
			if (p.Forward == null) return insertByNodeForward(p, newNode);
		return false;
	}

	/**
	 * 随机节点 -> 把节点P插入到含有随机节点的链表中[尾部]<br/>
	 * 注意：不进行排序，直接插入链表尾部<br/>
	 * @param rndNode extNode
	 * @param newNode extNode
	 * @return boolean
	 */
	public static final boolean insertByEnd(final extNode rndNode, final extNode newNode) {
		if (rndNode == null || newNode == null) return false;
		for (extNode p = rndNode; p != null; p = p.Next)
			if (p.Next == null) return insertByNodeForward(p, newNode);
		return false;
	}

	/**
	 * 随机节点 -> 把节点P插入到含有随机节点的链表中[有序]<br/>
	 * 注意：先要判断链表的顺序，如果出现乱序，则插入到随机节点后面<br/>
	 * @param rndNode extNode
	 * @param newNode extNode
	 * @return boolean
	 */
	public static final boolean insertByNodeCompositor(final extNode rndNode, final extNode newNode) {
		if (rndNode == null || newNode == null) return false;
		final extNode start = locationStart(rndNode);
		switch (sequenceInt(start, null)) {
		case 1:
			for (extNode p = start; p != null; p = p.Next) {
				if (newNode.extNodeValue() >= p.extNodeValue()) return insertByNodeForward(p, newNode);
				if (p.Next == null) return insertByNodeNext(p, newNode);/* 到达链尾，没有小于标识的位置，则直接加到链尾 */
			}
			break;
		case 0:
			for (extNode p = start; p != null; p = p.Next) {
				if (newNode.extNodeValue() <= p.extNodeValue()) return insertByNodeForward(p, newNode);
				if (p.Next == null) return insertByNodeNext(p, newNode);/* 没有大于标识的位置，则直接加到链尾 */
			}
			break;
		default:
			return insertByNodeNext(rndNode, newNode);
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
	public static final boolean insertByNodeNum(final extNode rndNode, final extNode p, final int i) {
		if (rndNode == null || p == null || i < 0 || i >= length(rndNode)) return false;
		final extNode iPoint = locationArrangeNode(rndNode, i);
		if (iPoint == null) return false;
		return insertByNodeForward(iPoint, p);
	}

	/**
	 * 随机节点 -> 把节点P插入到随机节点前[清除P节点的原有链信息]<br/>
	 * @param rndNode extNode
	 * @param p extNode
	 * @return boolean
	 */
	public static final boolean insertByNodeForward(final extNode rndNode, final extNode p) {
		if (rndNode == null || p == null) return false;
		p.Forward = rndNode.Forward;
		p.Next = rndNode;
		if (rndNode.Forward != null) rndNode.Forward.Next = p;
		rndNode.Forward = p;
		return true;
	}

	/**
	 * 随机节点 -> 把节点P插入到随机节点后[清除P节点的原有链信息]<br/>
	 * @param rndNode extNode
	 * @param p extNode
	 * @return boolean
	 */
	public static final boolean insertByNodeNext(final extNode rndNode, final extNode p) {
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
	public static final int locationArrangeNum(final extNode rndNode) {
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
	public static final extNode locationArrangeNode(final extNode rndNode, final int i) {
		final extNode start = locationStart(rndNode);
		int Num = 0;
		for (extNode p = start; p != null; p = p.Next)
			if (Num == i) return p;
			else Num++;
		return null;

	}

	/**
	 * 随机节点 -> 查找链表第一位节点[链表头]<br/>
	 * @param rndNode extNode
	 * @return extNode
	 */
	public static final extNode locationStart(final extNode rndNode) {
		for (extNode p = rndNode; p != null; p = p.Forward)
			if (p.Forward == null) return p;
		return rndNode;
	}

	/**
	 * 随机节点 -> 查找链表最后一位节点[链表尾]<br/>
	 * @param rndNode extNode
	 * @return extNode
	 */
	public static final extNode locationEnd(final extNode rndNode) {
		for (extNode p = rndNode; p != null; p = p.Next)
			if (p.Next == null) return p;
		return null;
	}

	/**
	 * 节点的前后互换<br/>
	 * @param e extNode
	 * @return boolean
	 */
	public static final boolean change(final extNode e) {
		if (e == null) return false;
		final extNode p = e.Forward;
		e.Forward = e.Next;
		e.Next = p;
		return true;
	}

	/**
	 * 两个节点在链表中互换<br/>
	 * @param e extNode
	 * @param f extNode
	 * @return boolean
	 */
	public static final boolean change(final extNode e, final extNode f) {
		if (e == null || f == null) return false;
		extNode p = e.Forward;
		e.Forward = f.Forward;
		f.Forward = p;
		p = e.Next;
		e.Next = f.Next;
		f.Next = p;
		return true;
	}

	/**
	 * 随机节点 -> 得到链表的长度<br/>
	 * @param rndNode extNode
	 * @return long
	 */
	public static final long length(final extNode rndNode) {
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
	public static final String toString(final extNode rndNode) {
		if (rndNode == null) return "";
		return ("\t:" + rndNode.toString());
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
		extNode start = locationStart(rndNode);
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
	public static final boolean remove(final extNode start, final extNode end, final extNode delEntry) {
		if (start == null) return false;
		for (extNode p = start; p != null; p = p.Next) {
			if (p == delEntry || p.extNodeValue() == delEntry.extNodeValue()) return remove(p);
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
	public static final boolean remove(final extNode start, final extNode end, final long extNodeValue) {
		if (start == null) return false;
		for (extNode p = start; p != null; p = p.Next) {
			if (p.extNodeValue() == extNodeValue) return remove(p);
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
	public static final boolean remove(final extNode p) {
		if (p == null) return false;
		if (p.Forward != null) p.Forward.Next = p.Next;
		if (p.Next != null) p.Next.Forward = p.Forward;
		clear(p);
		return true;
	}

	/**
	 * 针对节点的清空。并不影响链表。<br/>
	 * 但如果清空节点，则使用 {@link MaQiao.MaQiaoNodeRetrieval#NodeRemove nodeRemove()} 方法<br/>
	 * @param p extNode
	 */
	public static final void clear(final extNode p) {
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
	public static final sequence sequence(final extNode start, final extNode end) {
		return sequence.getSequence(sequenceInt(start, end));
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
	public static final int sequenceInt(final extNode start, final extNode end) {
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
	public static final int sequenceInt(final extNode rndNode) {
		if (rndNode == null) return -1;
		return sequenceInt(locationStart(rndNode), locationEnd(rndNode));
	}

	/**
	 * 随机节点 -> 得到此链表中标识数的节点<br/>
	 * 以标识值为标准。如果出现重复，则选中第一个节点返回<br/>
	 * @param rndNode extNode
	 * @param value long
	 * @return extNode
	 */
	@Deprecated
	public static final extNode searchByValueDeprecated(final extNode rndNode, final long value) {
		if (rndNode == null) return null;
		ThreadNodeAttribList.nodeAttrEquals nodeAttr = new ThreadNodeAttribList.nodeAttrEquals(value);
		threadNodeRun(rndNode, nodeAttr);
		while (true) {
			if (nodeAttr.getDoubleFindSuccess()) { return nodeAttr.getResult(); }
		}
	}

	/**
	 * 随机节点 -> 得到链表的长度<br/>
	 * @param rndNode extNode
	 * @return long
	 */
	public static final long length2(final extNode rndNode) {
		if (rndNode == null) return 0;
		nodeAttrSummary nodeAttr = new nodeAttrSummary();
		threadNodeRun(rndNode, nodeAttr);
		while (true) {
			if (nodeAttr.getDoubleFindSuccess()) { return nodeAttr.getSort(); }
		}
	}

	/**
	 * 设置双线程参数。准备开启
	 * @param rndNode extNode
	 * @param nodeAttr ThreadNodeAttributeAbstract
	 */
	private static final void threadNodeRun(final extNode rndNode, final ThreadNodeAttributeAbstract nodeAttr) {		
		nodeAttr.nodeRunBegin = rndNode;
		ThreadNodeConsts.nodeThread1.init(nodeAttr, true);
		ThreadNodeConsts.nodeThread2.init(nodeAttr, true);
		//ThreadNodeConsts.nodeThread1.nodeAttr = ThreadNodeConsts.nodeThread2.nodeAttr = nodeAttr;
		//ThreadNodeConsts.nodeThread1.working = ThreadNodeConsts.nodeThread2.working = true;
	}
}
