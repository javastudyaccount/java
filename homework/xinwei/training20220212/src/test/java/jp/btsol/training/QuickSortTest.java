package jp.btsol.training;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class QuickSortTest {
/**
 * 把数组中的内容按照从小到大的顺序进行排序
 */
	@Test
	void test() {
			QuickSort ob = new QuickSort();
			int nums[] = {10, 9, 6, 2, 1, 5, 3, 7, 4 ,8};
			System.out.println("原数组:");
			System.out.println(Arrays.toString(nums));
			ob.sort(nums);
			System.out.println("排序后的数组");
			System.out.println(Arrays.toString(nums));
	}
}
