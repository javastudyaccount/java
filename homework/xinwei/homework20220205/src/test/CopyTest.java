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
        fileoperate.fileCopy(new File("D:\\Chinese.txt"),
        		new File("F:\\Chinese.txt"));
        fileoperate.fileCopy(new File("D:\\English.txt"),
        		new File("F:\\English.txt"));
    }
}
