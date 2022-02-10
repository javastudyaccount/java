package test;

import java.io.File;

import org.junit.Test;

import function.FileOperate;
/*
 * 递归指定目录下的所有文件
 * 也可以查询以某某结尾的文件
 */
public class Recursion {
	@Test
	public void recursionDir() {
		FileOperate fileoperate = new FileOperate();
		File path = new File("D:");
        fileoperate.relativePaths(path);
        
    }
}
