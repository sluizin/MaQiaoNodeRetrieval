package testMQLinkedlistDoubleendRetrieval;

import org.junit.Test;

import MaQiao.MaQiaoNodeRetrieval.NodeCommon;
import MaQiao.MaQiaoNodeRetrieval.UtilTool;
import MaQiao.MaQiaoNodeRetrieval.extNode;

public class test2 {

	@SuppressWarnings("unused")
	@Test
	public void test() {
		final node start = new node(0, "0");
		init(start);
		final node end = (node) NodeCommon.nodeLocationEnd(start);
		NodeCommon.toPrintAll(start);
		System.out.println("------------------------------");
		node c = new node(99, "" + 99);
		System.out.println("NodeLength:" + NodeCommon.nodeLength(start));
		System.out.println("------------------------------");
		NodeCommon.nodeInsertByNodeNum(start, c, 2);
		NodeCommon.toPrintAll(start);
		System.out.println("------------------------------");
		node frndOne = (node) NodeCommon.nodeSearchByValue(start, 3);
		NodeCommon.toPrint(frndOne);
		System.out.println("------------------------------");
	}

	private void init(node start) {
		int i = 0;
		node p = start;
		for (i = 1; i <= 5; i++) {
			node c = new node(i, "" + i);
			NodeCommon.nodeInsertByNodeNext(p, c);
			p = c;
		}

	}

	public static final class node extends extNode {
		int value = 0;
		String str = null;

		public node() {

		}

		public node(int value, String str) {
			this.value = value;
			this.str = str;
		}

		@Override
		public String toString() {
			int size = 8;
			StringBuilder sb = new StringBuilder(100);
			sb.append("node (F:");
			if (super.getForward() != null) sb.append(UtilTool.format(System.identityHashCode(super.getForward()), size, ' '));
			else sb.append("        ");
			sb.append(")[");
			sb.append(UtilTool.format(super.iNodeSystemHashCode(), size, ' '));
			sb.append("](N:");
			if (super.getNext() != null) sb.append(UtilTool.format(System.identityHashCode(super.getNext()), size, ' '));
			else sb.append("        ");
			sb.append(")");
			sb.append("\t[");
			sb.append("value=");
			sb.append(value);
			sb.append(", str=");
			sb.append(str);
			sb.append(",value=");
			sb.append(System.identityHashCode(value));
			sb.append("]");
			return sb.toString();
		}

		@Override
		public long extNodeValue() {
			return value;
		}
	}
}
