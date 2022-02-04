package test;

import java.util.List;

import org.junit.Test;

import main.FileOperate;

public class TestEnglishFileOpera {
	@Test
	public void testEnglishFileOpera() {
		FileOperate fileOpera = new FileOperate();
		List<String> lineList = fileOpera.readFile("file\\english_utf8.txt", "UTF-8");
		fileOpera.outputToConsole(lineList);
		System.out.println("--------------------------------");
		fileOpera.outputToConsoleContainText(lineList, "us");
		fileOpera.writeAnotherFile("file\\english_utf8.txt", "UTF-8", "file\\another_english.txt", "UTF-16");
	}
}
