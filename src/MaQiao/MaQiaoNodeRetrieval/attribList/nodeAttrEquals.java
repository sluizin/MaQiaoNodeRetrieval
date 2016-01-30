package MaQiao.MaQiaoNodeRetrieval.attribList;

import MaQiao.MaQiaoNodeRetrieval.ThreadNodeAttributeAbstract;
import MaQiao.MaQiaoNodeRetrieval.extNode;


/**
 * 节点查找，按value值查找
 * @author Sunjian
 * @since 1.7
 * @version 1.0
 */
public final class nodeAttrEquals extends ThreadNodeAttributeAbstract {
	long value = 0L;
	extNode forwardNode = null;
	extNode nextNode = null;

	public nodeAttrEquals(long value) {
		this.value = value;
	}

	/**
	 * 得到结果节点，只得到一个即可
	 * @return extNode
	 */
	public final extNode getResult() {
		extNode p = null;
		if (super.getDoubleFindSuccess()) {
			p = (forwardNode != null) ? forwardNode : nextNode;
		}
		return p;
	}

	@Override
	public boolean compare(final extNode p) {
		if (p.extNodeValue() == value) return true;
		else return false;
	}

	@Override
	public void forward() {
		for (extNode p = nodeRunBegin; p != null; p = p.Forward) {
			if (compare(p)) {
				forwardNode = p;
				break;
			}
		}
	}

	@Override
	public void next() {
		for (extNode p = nodeRunBegin; p != null; p = p.Next) {
			if (compare(p)) {
				nextNode = p;
				break;
			}
		}
	}
}