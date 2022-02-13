package xiangyang;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class TestQuickSort {

	@Test
	void test() {
		int nums[] = {7, -5, 3, 2, 1, 0, 45};
		int expctedNums[] = {-5, 0, 1, 2, 3, 7, 45};
		System.out.println("Original Array:");
		System.out.println(Arrays.toString(nums));
		QuickSort qs = new QuickSort();
		qs.sort(nums);
		System.out.println("Sorted Array:");
		System.out.println(Arrays.toString(nums));
		assertEquals(Arrays.toString(expctedNums), Arrays.toString(nums));
	}

}
