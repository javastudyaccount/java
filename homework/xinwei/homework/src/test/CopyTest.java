package test;

import java.io.File;

import org.junit.Test;

import function.FileOperate;
/*
 * �����ļ�
 * �����ļ���Ӣ���ļ�
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
