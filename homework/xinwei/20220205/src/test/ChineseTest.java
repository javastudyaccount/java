package test;

import java.util.List;

import org.junit.Test;

import function.FileOperate;
/*
 * �����������
 * �ļ�·��+�ַ�����
 * ��ȡ���д���"��"�ؼ��ֵ��У��������һ��
 */
public class ChineseTest {
	@Test
	public void Chinese() {
		FileOperate fileoperate = new FileOperate();
		fileoperate.chineseOutput("D:\\Chinese.txt", "GB2312");
		List<String> list = fileoperate.inputFile("D:\\Chinese.txt", "GB2312");
		fileoperate.fileReader(list);
		System.out.println("-------------------------------------------");
		fileoperate.fileKeyWord(list, "��");
	}
}
