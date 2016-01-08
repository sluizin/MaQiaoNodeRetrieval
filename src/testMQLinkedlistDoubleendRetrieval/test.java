package testMQLinkedlistDoubleendRetrieval;

import org.junit.Test;

import MaQiao.MaQiaoNodeRetrieval.iNode;

public class test {

	@SuppressWarnings("unused")
	@Test
	public void test1() {
		int i = 0;
		node start = new node(0);
		node p = start;
		for (i = 1; i <= 20; i++) {
			node c = new node(i);
			p.b = c;
			c.a = p;
			p = c;
		}
		node end = p;
		{
			i = 0;
			for (p = start; p != null; p = p.b) {
				System.out.println((i++) + "\t:" + p.toString());
			}
		}

	}

	public static final class node implements iNode {
		int value = 0;
		node a = null;
		node b = null;

		public node() {

		}

		public node(int value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return "node [value=" + value + "\t]";
		}

		/* 以下为接口方法 */
		@Override
		public node getForward() {
			return a;
		}

		@Override
		public node getNext() {
			return b;
		}

		@Override
		public long iNodeValue() {
			return value;
		}

		@Override
		public void setForward(final iNode f) {
			this.a = (node) f;

		}

		@Override
		public void setNext(iNode f) {
			this.b = (node) f;

		}

		@Override
		public long iNodeSystemHashCode() {
			return System.identityHashCode(this);
		}

	}
}
