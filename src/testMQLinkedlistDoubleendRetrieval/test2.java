package testMQLinkedlistDoubleendRetrieval;

import org.junit.Test;
import MaQiao.MaQiaoNodeRetrieval.NodeCommon;
import MaQiao.MaQiaoNodeRetrieval.UtilTool;
import MaQiao.MaQiaoNodeRetrieval.extNode;

public class test2 {

	private final long maxrows = 3000000L;
	public int maxrowsInt = 0;

	@SuppressWarnings({ "unused", "deprecation" })
	@Test
	public void test() {
		if (maxrows > Integer.MAX_VALUE && maxrows < Integer.MIN_VALUE) {
			maxrowsInt = 0;
		} else maxrowsInt = (int) maxrows;
		final node start = new node(0, "0");
		init(start);
		final node end = (node) NodeCommon.locationEnd(start);
		//NodeCommon.toPrintAll(start);
		System.out.println("------------------------------");
		//node c = new node(99, "" + 99);
		//System.out.println("NodeLength:" + NodeCommon.nodeLength(start));
		//NodeCommon.insertByNodeNum(start, c, 2);	
		int center = maxrowsInt / 2;
		node d = new node(center, "" + center);
		//System.out.println("NodeLength:" + NodeCommon.nodeLength(start));
		NodeCommon.insertByNodeNum(start, d, center);
		//node e = new node(100, "" + 100);
		//System.out.println("NodeLength:" + NodeCommon.nodeLength(start));
		//NodeCommon.insertByNodeNum(start, e, 3);
		//node f = new node(4, "" + 4);
		//System.out.println("NodeLength:" + NodeCommon.nodeLength(start));
		//NodeCommon.insertByNodeNum(start, f, 4);	
		//NodeCommon.toPrintAll(start);
		//System.out.println("------------------------------");	
		//NodeCommon.orderingReset(start);
		//NodeCommon.toPrintAll(start);

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
		final int maxArray = 10;
		/*
		 * 比中间值向后的数量[遍历时向后的数量]
		 * 一般的单线程遍历时先向前再向后，所以单线程要多遍历很多
		 */
		final int spaceMax = 20;
		Long[] array = new Long[maxArray + spaceMax];
		{
			for (int i = 0; i < array.length; i++) {
				array[i] = new Long(UtilTool.getRndInt(1, (int) maxrowsInt));
			}
			for (int i = 0; i < spaceMax; i++) {
				array[maxArray + i] = new Long(UtilTool.getRndInt(center, (int) maxrowsInt));//new Long(maxrowsInt - 5);
			}
		}
		//ExecutorService pool = Executors. newSingleThreadExecutor();
		//long array[] = { maxrows - 1, 2900099, 2710099, 2710099, 10, 1, 1500, 2900099, 2900099, maxrows - 10, 2900099, 2900099, 2900099, 2900099, 2900099, 2900099, 2900099 };
		//long array[]={1,2,3,4,5,6,7,8};
		System.out.println("--- 检索目标[" + center + "] ---");
		{
			System.out.println(UtilTool.getString(array, false, center));
		}
		System.out.println(UtilTool.minNum + "<-->" + (array.length - UtilTool.minNum));
		System.out.println("--- 单线程 ---");
		{
			long time1 = System.nanoTime();
			for (int i = 0; i < array.length; i++) {
				NodeCommon.length(d);
				NodeCommon.toString(NodeCommon.searchByValue(d, array[i]));
				//System.out.println("length2:" + NodeCommon.length(d));
				//System.out.println("****************-- search out" + i + " \t-->->->->:" + NodeCommon.toString(NodeCommon.searchByValue(d, array[i])));
			}
			long time2 = System.nanoTime();
			System.out.println("SingleThreadtime:\t" + (time2 - time1));
		}
		System.out.println("--- 多线程 ---");
		{
			long time1 = System.nanoTime();
			for (int i = 0; i < array.length; i++) {
				NodeCommon.length2(d);
				//System.out.println("------------------------------");
				NodeCommon.searchByValueDeprecated(d, array[i]);
				//System.out.println("length2:" + NodeCommon.length2(d));
				//System.out.println("****************-- search out" + i + " \t-->->->->:" + NodeCommon.toString(NodeCommon.searchByValueDeprecated(d, array[i])));

				//System.out.println("---------------------------------------------------------------------------------");
			}
			long time2 = System.nanoTime();
			System.out.println("MultiThreadtime:\t" + (time2 - time1));// /1000000L

		}
	}

	private void init(node start) {
		node p = start;
		for (int i = 1; i <= maxrows; i++) {
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
			sb.append(')');
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
