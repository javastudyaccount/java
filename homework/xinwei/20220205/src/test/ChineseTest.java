package test;

import java.util.List;

import org.junit.Test;

import function.FileOperate;
/*
 * 中文输出输入
 * 文件路径+字符编码
 * 读取行中带有"我"关键字的行，并输出那一行
 */
public class ChineseTest {
	@Test
	public void Chinese() {
		FileOperate fileoperate = new FileOperate();
		fileoperate.chineseOutput("D:\\Chinese.txt", "GB2312");
		List<String> list = fileoperate.inputFile("D:\\Chinese.txt", "GB2312");
		fileoperate.fileReader(list);
		System.out.println("-------------------------------------------");
		fileoperate.fileKeyWord(list, "我");
	}
}
