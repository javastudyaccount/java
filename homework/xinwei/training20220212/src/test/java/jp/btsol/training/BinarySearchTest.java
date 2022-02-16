package jp.btsol.training;

import org.junit.jupiter.api.Test;

class BinarySearchTest {
/**
 * 在有序数组中查找数字在数组中的索引
 * 只查找第一次出现的索引
 * 数组只能是有序数组!!
 */
	@Test
	void test() {
		BinarySearch bs = new BinarySearch();
		int[] nums = {1, 5, 6, 7, 8, 11, 5};
        int search_num = 5;
        int low = 0;
        int high = nums.length - 1;
        int index = bs.binarySearch(nums, search_num, low, high);
        if (index == -1) {
            System.out.println("数组中没有 "+ search_num+ " 这个数字");
        } else {
            System.out.println(search_num + " 在数组中的索引是 " + index);
        }
    }
}
