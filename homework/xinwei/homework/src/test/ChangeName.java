package test;

import org.junit.Test;

import function.FileOperate;
/*
 * 实现文件重命名
 */
public class ChangeName {
	@Test
	public void FileNameChange(){
		FileOperate fileoperate = new FileOperate();
		fileoperate.changeName("C:\\Windows\\Chinese.txt","C:\\Windows\\Chinese_1.txt");
		fileoperate.changeName("C:\\Windows\\English.txt","C:\\Windows\\English_1.txt");
	}
}
