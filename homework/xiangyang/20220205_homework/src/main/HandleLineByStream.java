package main;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HandleLineByStream {
	public void handleLineByStream(String fileName, String encoding, String text) {
		Charset charset = Charset.forName(encoding);
		try (Stream<String> lines = Files.lines(Paths.get(fileName), charset)) {
			List<String> filterLines = lines.filter(s -> s.contains(text)).collect(Collectors.toList());
			filterLines.forEach(System.out::println);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
