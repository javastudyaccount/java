package test;

import java.io.File;

import org.junit.Test;

import function.FileOperate;
/*
 * 递归指定目录下的所有文件
 */
public class Recursion {
	@Test
	public void recursionDir() {
		FileOperate fileoperate = new FileOperate();
		File dir = new File("C:\\Windows");
        fileoperate.getAllDir(dir);
    }
}
