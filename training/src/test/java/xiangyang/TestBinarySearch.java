package xiangyang;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestBinarySearch {

	@Test
	void test1() {
		int[] nums = {1, 5, 6, 7, 8, 11};
		int searchNum = 7;
		int index = BinarySearch.binarySearch(nums, searchNum);
		assertEquals(3, index);
	}

}
