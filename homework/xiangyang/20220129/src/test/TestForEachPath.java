package test;

import org.junit.Test;

import main.FileOperate;

public class TestForEachPath {
	@Test
	public void testForEachPath() {
		FileOperate fileopera = new FileOperate();
		fileopera.ForEachPath("file//");
	}
}
