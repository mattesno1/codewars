import java.util.ArrayList;
import java.util.List;

/** https://www.codewars.com/kata/range-extraction/solutions/java */
class Solution {

		public static String rangeExtraction(int[] arr) {

			StringBuilder sb = new StringBuilder();
			Buffer buffer = new Buffer();
			for (int n : arr) {
				if (!buffer.add(n)) {
					sb.append(buffer).append(',');
					buffer = new Buffer();
					buffer.add(n);
				}
			}
			return sb.append(buffer).toString();
    }
}

class Buffer {

	private final List<Integer> integers = new ArrayList<>();

	boolean add(int n) {
		return accepts(n) && integers.add(n);
	}

	private boolean accepts(int n) {
		return integers.isEmpty() || last() == n-1;
	}

	private Integer first() {
		return integers.get(0);
	}

	private Integer last() {
		return integers.get(integers.size() - 1);
	}

	@Override
	public String toString() {

		if (integers.size() == 1) {
			return last().toString();
		}

		if (integers.size() == 2) {
			return String.format("%d,%d", first(), last());
		}

		if (integers.size() > 2) {
			return String.format("%d-%d", first(), last());
		}

		return "";
	}
}
