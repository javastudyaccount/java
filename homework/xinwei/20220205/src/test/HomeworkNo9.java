package test;

import java.io.File;

import org.junit.Test;

import function.FileOperate;
/*existing relative path, with sub directories, filename has space
 * �г��б��ڵ������ļ�����������ı��ĵ���
 * �ļ������ո��Ҳ�������
 */
public class HomeworkNo9 {
	@Test
	public void ListFiles() {
		FileOperate fileoperate = new FileOperate();
		File path = new File("file");
		fileoperate.no9Test(path, "file\\list.txt", "UTF-8");
	}
}
