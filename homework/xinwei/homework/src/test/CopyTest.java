package test;

import java.io.File;

import org.junit.Test;

import function.FileOperate;
/*
 * 复制文件
 * 中文文件和英文文件
 */
public class CopyTest {
	@Test
	public void fileCopy() {
		FileOperate fileoperate = new FileOperate();
        fileoperate.copy(new File("C:\\Windows\\Chinese.txt"),
        		new File("C:\\Windows\\system32\\Chinese.txt"));
        fileoperate.copy(new File("C:\\Windows\\English.txt"),
        		new File("C:\\Windows\\system32\\English.txt"));
    }
}
