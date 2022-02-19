package jp.btsol.training;
/**
 * 在有序数组中查找数字在数组中的索引
 * 只查找第一次出现的索引
 * 数组只能是有序数组!!
 * @author ni
 *
 */
public class BinarySearch {
	/**
	 * 在有序数组中查找数字在数组中的索引
	 * 只查找第一次出现的索引
	 * @param nums int 数组
	 * @param key int 想要查询的数字
	 * @param low int 数组中最小的数字
	 * @param high int 数组中最大数字
	 * @return -1表示数组中没有这个数字
	 */
	public int binarySearch(int[] nums, int key,int low,int high) {
		if(low > high) {
			return -1;
		}
        int mid = (low + high) /2;// 中间数
        int guess = nums[mid];// 猜测的数字
            if (guess == key) {
                return mid; 
            } else if (guess > key) {
                return binarySearch(nums, key, low, high - 1);
            } else {
                return binarySearch(nums, key, low + 1, high);
        }
    }
}
