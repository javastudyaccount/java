package xiangyang;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

class TestToCamel {
	
	@Test
	void test1() {
		assertEquals("doSearchKey", ToCamel.toCamel("  DO Search key  "));
	}
	
	@Test
	void test2() {
		assertEquals("dosearchkey", ToCamel.toCamel("DOSEARCHKEY"));
	}
	
	@Test
	void test3() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		ToCamel.toCamel("        ");
		assertEquals("请输入有效的字符串：words。\r\n", output.toString());
	}
	
	@Test
	void test4() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		ToCamel.toCamel(null);
		assertEquals("words不能为null！\r\n", output.toString());
	}
	
}
