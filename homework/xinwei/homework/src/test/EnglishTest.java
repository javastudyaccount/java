package test;

import java.util.List;

import org.junit.Test;

import function.FileOperate;
/*
 * Ӣ���������
 * �ļ�·��+�ַ�����
 * ��ȡ���д���"this"�ؼ��ֵ��У��������һ��
 */
public class EnglishTest {
	@Test
	public void English() {
		FileOperate fileoperate = new FileOperate();
		fileoperate.englishOutput("C:\\Windows\\English.txt", "UTF-8");
		List<String> list = fileoperate.inputFile("C:\\Windows\\English.txt", "UTF-8");
		fileoperate.fileReader(list);
		System.out.println("-------------------------------------------");
		fileoperate.fileKeyWord(list, "this");
		}
	}
