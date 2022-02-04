package test;

import java.util.List;

import org.junit.Test;

import main.FileOperate;

public class TestChineseFileOpera {
	@Test
	public void testChineseFileOpera() {
		FileOperate fileOpera = new FileOperate();
		List<String> lineList = fileOpera.readFile("file\\chinese_gb2312.txt", "GB2312");
		fileOpera.outputToConsole(lineList);
		System.out.println("--------------------------------");
		fileOpera.outputToConsoleContainText(lineList, "һ");
		fileOpera.writeAnotherFile("file\\chinese_gb2312.txt", "GB2312", "file\\another_chinese.txt", "UTF-8");
	}
}
