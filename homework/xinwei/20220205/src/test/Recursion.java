package test;

import java.io.File;

import org.junit.Test;

import function.FileOperate;
/*
 * �ݹ�ָ��Ŀ¼�µ������ļ�
 * Ҳ���Բ�ѯ��ĳĳ��β���ļ�
 */
public class Recursion {
	@Test
	public void recursionDir() {
		FileOperate fileoperate = new FileOperate();
		File path = new File("D:");
        fileoperate.relativePaths(path);
        
    }
}
