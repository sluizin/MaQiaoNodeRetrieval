package MaQiao.MaQiaoNodeRetrieval.attribList;

import MaQiao.MaQiaoNodeRetrieval.ThreadNodeAttributeAbstract;
import MaQiao.MaQiaoNodeRetrieval.extNode;

/**
 * 节点汇总
 * @author Sunjian
 * @since 1.7
 * @version 1.0
 */
public final class nodeAttrSummary extends ThreadNodeAttributeAbstract {
	long sort1 = 0L;
	long sort2 = 0L;

	public long getSort() {
		return sort1 + sort2;
	}

	@Override
	public boolean compare(extNode p) {
		return false;
	}

	@Override
	public void forward() {
		for (extNode p = nodeRunBegin; p != null; p = p.Forward) {
			sort1++;
		}
	}

	@Override
	public void next() {
		for (extNode p = nodeRunBegin; p != null; p = p.Next) {
			sort2++;
		}
	}

}

