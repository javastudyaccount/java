package test;

import java.io.File;

import org.junit.Test;

import function.FileOperate;
/*
 * �ݹ�ָ��Ŀ¼�µ������ļ�
 */
public class Recursion {
	@Test
	public void recursionDir() {
		FileOperate fileoperate = new FileOperate();
		File dir = new File("C:\\Windows");
        fileoperate.getAllDir(dir);
    }
}
