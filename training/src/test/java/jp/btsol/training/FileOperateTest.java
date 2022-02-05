package jp.btsol.training;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class FileOperateTest {

	@Test
	void testListAllFiles() {
		FileOperate fileOpera = new FileOperate();
//		fileopera.ForEachPath("file//");
		List<String> files = fileOpera.listAllFiles("C:\\app\\workspace_mahjong\\training\\file");
		for(String file : files) {
			System.out.println(file);
		}
	}
	@Test
	void testListAllFiles2() {
		FileOperate fileOpera = new FileOperate();
//		fileopera.ForEachPath("file//");
		List<String> files = fileOpera.listAllFiles("file");
		fileOpera.write2File(files, "file\\list.txt", "UTF-8");
	}

}
