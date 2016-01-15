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
		final node end = (node) NodeCommon.locationEnd(start);
		//NodeCommon.toPrintAll(start);
		System.out.println("------------------------------");
		node c = new node(99, "" + 99);
		//System.out.println("NodeLength:" + NodeCommon.nodeLength(start));
		NodeCommon.insertByNodeNum(start, c, 2);	
		
		node d = new node(10, "" + 10);
		//System.out.println("NodeLength:" + NodeCommon.nodeLength(start));
		NodeCommon.insertByNodeNum(start, d, 0);		
		node e = new node(100, "" + 100);
		//System.out.println("NodeLength:" + NodeCommon.nodeLength(start));
		NodeCommon.insertByNodeNum(start, e, 3);
		node f = new node(4, "" + 4);
		//System.out.println("NodeLength:" + NodeCommon.nodeLength(start));
		NodeCommon.insertByNodeNum(start, f, 4);	
		NodeCommon.toPrintAll(start);
		System.out.println("------------------------------");	
		NodeCommon.orderingReset(start);
		NodeCommon.toPrintAll(start);
		
		//System.out.println("----------nodeTurnDirection--------------------");
		//NodeCommon.turnDirection(start);
		//NodeCommon.toPrintAll(start);
		//System.out.println("----:"+NodeCommon.searchNode(start,d));
		//node frndOne = (node) NodeCommon.nodeSearchByValue(start, 3);
		//NodeCommon.toPrint(frndOne);
		//System.out.println("------------------------------");
		//node g = new node(12, "" + 12);
		//NodeCommon.insertByHead(start, g);
		//NodeCommon.toPrintAll(start);
		System.out.println("--------11111111111111111111111----------------------");
		NodeCommon.toPrint(NodeCommon.searchByValueDeprecated(d, 2));
		//NodeCommon
	}

	private void init(node start) {
		node p = start;
		for (int i = 1; i <= 5; i++) {
			node c = new node(i, "" + i);
			NodeCommon.insertByNodeNext(p, c);
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
			sb.append(",identityHashCode=");
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
