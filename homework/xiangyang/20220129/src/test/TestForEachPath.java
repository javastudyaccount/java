package test;

import java.util.List;

import org.junit.Test;

import main.FileOperate;

public class TestForEachPath {
	@Test
	public void testForEachPath() {
		FileOperate fileOpera = new FileOperate();
//		fileopera.ForEachPath("file//");
		List<String> files = fileOpera.listAllFiles("file//");
		for(String file : files) {
			System.out.println(file);
		}
	}
}
