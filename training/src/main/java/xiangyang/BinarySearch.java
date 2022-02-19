package xiangyang;

public class BinarySearch {
	/**
	 * 查找flag在nums里的位置
	 * @param nums 被查找的整数数组
	 * @param flag 被查找的整数
	 * @return 如果flag不存在与nums，返回-1；如果存在则返回flag所在的索引值
	 */
	public static int binarySearch(int[] nums, int flag) {
		int hi_nums = nums.length - 1;
		int lo_nums = 0;
		while (hi_nums >= lo_nums) {
			int guess = (lo_nums + hi_nums) >>> 1; // 相当于除以2
			if (nums[guess] > flag) {
				hi_nums = guess - 1;
			}
			else if (nums[guess] < flag) {
				lo_nums = guess + 1;
			}
			else {
				return guess;
			}
		}
		return -1;
	}
}
