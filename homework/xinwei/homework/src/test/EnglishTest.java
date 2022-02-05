package test;

import java.util.List;

import org.junit.Test;

import function.FileOperate;
/*
 * 英文输出输入
 * 文件路径+字符编码
 * 读取行中带有"this"关键字的行，并输出那一行
 */
public class EnglishTest {
	@Test
	public void English() {
		FileOperate fileoperate = new FileOperate();
		fileoperate.englishOutput("C:\\Windows\\English.txt", "UTF-8");
		List<String> list = fileoperate.inputFile("C:\\Windows\\English.txt", "UTF-8");
		fileoperate.fileReader(list);
		System.out.println("-------------------------------------------");
		fileoperate.fileKeyWord(list, "this");
		}
	}
