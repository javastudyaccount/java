package test;

import org.junit.Test;

import main.FileOperate;

public class TestRenameFile {
	@Test
	public void testRenameFile() {
		FileOperate fileOpera = new FileOperate();
		fileOpera.renameFile("file//rename//oldFile.txt", 
				"file//rename//newFile.txt");
	}
}
