package test;

import org.junit.jupiter.api.Test;

import main.HandleLineByStream;

public class TestHandleLineByStream {
	@Test
	public void testHandleLineByStream() {
		HandleLineByStream handleLineByStream = new HandleLineByStream();
		handleLineByStream.handleLineByStream("file/chinese_gb2312.txt/", "GB2312", "һ");
		System.out.println("================================");
		handleLineByStream.handleLineByStream("file/english_utf8.txt", "UTF-8", "us");
	}
}
