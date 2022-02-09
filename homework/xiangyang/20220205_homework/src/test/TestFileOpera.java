package test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.Test;

import main.FileOperate;

public class TestFileOpera {
	@Test
	void testFileOperaAbnormal() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		FileOperate fp = new FileOperate();
		fp.listAllFiles("temp");
//		assertThat(output.toString(), is("请输入有效的路径"));
		assertEquals(output.toString(), "请输入有效的路径");
	}
	
	@Test
	void testFileOperaNormal() {
		FileOperate fp = new FileOperate();
		List<String> fileNameList = fp.listAllFiles("file");
		boolean flag = fileNameList.contains("file\\another_chinese.txt");
		assertTrue(flag);
	}
}
