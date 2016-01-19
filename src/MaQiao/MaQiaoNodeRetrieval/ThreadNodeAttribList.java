package MaQiao.MaQiaoNodeRetrieval;

/**
 * 双线程节点属性实际类
 * @version 1.0
 * @author Sunjian
 * @since 1.7
 */
public final class ThreadNodeAttribList {

	/**
	 * 节点比较
	 * @author Sunjian
	 * @since 1.7
	 * @version 1.0
	 */
	public static final class nodeAttrCompare extends ThreadNodeAttributeAbstract {
		long sort1 = 0L;
		long sort2 = 0L;
		long value = 0L;

		public nodeAttrCompare(long value) {
			this.value = value;
		}

		public long getSort() {
			return sort1 + sort2;
		}

		@Override
		public boolean compare(extNode p) {
			return false;
		}

		@Override
		public void forward() {
			for (extNode p = input; p != null; p = p.Forward) {
				if (p.extNodeValue() > this.value) sort1++;
			}
		}

		@Override
		public void next() {
			for (extNode p = input; p != null; p = p.Next) {
				if (p.extNodeValue() > this.value) sort2++;
			}
		}

	}

	/**
	 * 节点汇总
	 * @author Sunjian
	 * @since 1.7
	 * @version 1.0
	 */
	public static final class nodeAttrSummary extends ThreadNodeAttributeAbstract {
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
			for (extNode p = input; p != null; p = p.Forward) {
				sort1++;
			}
		}

		@Override
		public void next() {
			for (extNode p = input; p != null; p = p.Next) {
				sort2++;
			}
		}

	}

	/**
	 * 节点查找，按value值查找
	 * @author Sunjian
	 * @since 1.7
	 * @version 1.0
	 */
	public static final class nodeAttrEquals extends ThreadNodeAttributeAbstract {
		long value = 0L;

		nodeAttrEquals(long value) {
			this.value = value;
		}

		@Override
		public boolean compare(final extNode p) {
			if (p.extNodeValue() == value) return true;
			else return false;
		}

		@Override
		public void forward() {
			for (extNode p = input; p != null; p = p.Forward) {
				if (compare(p)) {
					forwardNode = p;
					break;
				}
			}
		}

		@Override
		public void next() {
			for (extNode p = input; p != null; p = p.Next) {
				if (compare(p)) {
					nextNode = p;
					break;
				}
			}
		}
	}
}
