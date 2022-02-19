package jp.btsol.training;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
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
			
			Assertions.assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9 , 10}, nums);
	}
	@Test
	void testNull() {
			QuickSort ob = new QuickSort();
			int nums[] = null;
			ob.sort(nums);
			
			Assertions.assertArrayEquals(null, nums);
	}

	@Test
	void testEmpty() {
			QuickSort ob = new QuickSort();
			int nums[] = {};
			ob.sort(nums);
			
			Assertions.assertArrayEquals(new int[]{}, nums);
	}
}
