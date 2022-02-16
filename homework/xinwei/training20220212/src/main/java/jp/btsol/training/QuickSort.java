package jp.btsol.training;

/**
 * 遍历数组并判断其内容
 * 按照从小到大的顺序进行输出排序
 * @author ni
 *
 */
public class QuickSort {
	private int temp_array[];
	private int len;
	/**
	 * 判断数组是否为空，为空则结束这个方法
	 * @param nums int[] 数组
	 */
	public void sort(int[] nums) {
		// 如果数组为空，或者数组长度为0，不合法的情况下return结束这个方法
		if(nums == null || nums.length == 0) {
			return;
		}
		this.temp_array = nums;
		len = nums.length;
		quickSort(0, len - 1);
	}
	/**
	 * 把数组按从小到大的顺序进行排序
	 * @param low_index int 指向索引最小的值
	 * @param high_index int 指向索引右边最大的值
	 */
	private void quickSort(int low_index, int high_index) {
		int i = low_index;
		int j = high_index;
		int pivot = temp_array[low_index+(high_index-low_index)/2];
		while(i <= j) {
			while(temp_array[i] < pivot) {
				i++;
			}
			while(temp_array[j] > pivot) {
				j--;
			}
			if(i <= j) {
				exchangeNumbers(i,j);
				i++;
				j--;
			}
		}
		// 两个索引相遇后
		if(low_index < j)
			quickSort(low_index, j);
		if(i < high_index)
			quickSort(i, high_index);
		}
		/**
		 * i停下，j也停下的时候，交换i和j位置的数字
		 * @param i int 停下后的位置
		 * @param j int 停下后的位置
		 */
		private void exchangeNumbers(int i, int j) {
			int temp = temp_array[i];
			temp_array[i] = temp_array[j];
			temp_array[j] = temp;
		}
}
