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
		fileoperate.changeName("D:\\Chinese.txt","D:\\Chinese_1.txt");
		fileoperate.changeName("D:\\English.txt","D:\\English_1.txt");
	}
}
