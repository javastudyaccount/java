package xiangyang;
/**
 * QuickSort
 *
 */
public class QuickSort {
	
	private int temp_array[];
	private int len;
	
	/**
	 * 调用quickSort方法对整数数组进行快速排序（有小到大）
	 * @param nums 整数数组
	 */
	public void sort(int[] nums) {
		if (nums == null || nums.length == 0) {
			return;
		}
		this.temp_array = nums;
		len = nums.length;
		quickSort(0, len - 1);
	}
	
	/**
	 * 对整数数组数组进行由小到大的排序
	 * @param low_index 数组的最小索引
	 * @param high_index 数组的最大索引
	 */
	private void quickSort(int low_index, int high_index) {
		int i = low_index;
		int j = high_index;
		int pivot = temp_array[low_index+(high_index-low_index)/2];
		while (i <= j) {
			while (temp_array[i] < pivot) {
				i++;
			}
			while (temp_array[j] > pivot) {
				j--;
			}
			if (i <= j) {
				exchangeNumbers(i, j);
				i++;
				j--;
			}
		}
		
		if (low_index < j)
			quickSort(low_index, j);
		if (i < high_index)
			quickSort(i, high_index);
	}
	
	/**
	 * 将大于pivot的temp_array[i]和小于pivot的temp_array[j]互换位置
	 * @param i 大于pivot的temp_array的索引
	 * @param j 小于pivot的temp_array的索引
	 */
	private void exchangeNumbers(int i, int j) {
		int temp = temp_array[i];
		temp_array[i] = temp_array[j];
		temp_array[j] = temp;
		
	}
}
