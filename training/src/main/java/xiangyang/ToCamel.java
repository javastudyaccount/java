package xiangyang;

public class ToCamel {
	/**
	 * 将输入的字符串以“空格”分割为单词，并将第一个单词首字母大写；
	 * 其余字母小写，剩余单词首字母大写，其余字母小写
	 * Examples: toCamel("Do sEarch") returns "doSearch"
	 * 
	 * @param words 单词字符串 words
	 * @return 第一个单词首字母小写，其余单词首字母大写的字符串
	 */
	public static String toCamel(String words) {
		if (words == null) {
			System.out.println("words不能为null！");
			return words;
		}
		String _words = words.trim();
		if (_words.length() == 0) {
			System.out.println("请输入有效的字符串：words。");
			return _words;
		}
		String[] arrayWords = _words.split(" ");

		arrayWords[0] = arrayWords[0].substring(0, 1).toLowerCase() + arrayWords[0].substring(1).toLowerCase();

		for (int i = 1; i < arrayWords.length; i++) {
			arrayWords[i] = arrayWords[i].substring(0, 1).toUpperCase() + arrayWords[i].substring(1).toLowerCase();
		}

		_words = String.join("", arrayWords);

		return _words;
	}
}
