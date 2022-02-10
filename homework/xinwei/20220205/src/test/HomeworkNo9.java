package test;

import java.io.File;

import org.junit.Test;

import function.FileOperate;
/*existing relative path, with sub directories, filename has space
 * 列出列表内的所有文件，并输出到文本文档中
 * 文件名带空格的也可以输出
 */
public class HomeworkNo9 {
	@Test
	public void ListFiles() {
		FileOperate fileoperate = new FileOperate();
		File path = new File("file");
		fileoperate.no9Test(path, "file\\list.txt", "UTF-8");
	}
}
