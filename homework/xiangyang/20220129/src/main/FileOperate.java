package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FileOperate {
	public List<String> readFile(String fileName, String encoding) {
		List<String> lineList = new ArrayList<String>();
		File file = new File(fileName);
		try {
			lineList = FileUtils.readLines(file, encoding);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lineList;
	}

	public void outputToConsole(List<String> lineList) {
		for (String line : lineList) {
			System.out.println(line);
		}
	}

	public void outputToConsoleContainText(List<String> lineList, String text) {
		for (String line : lineList) {
			if (line.contains(text)) {
				System.out.println(line);
			}
		}
	}

	public void writeAnotherFile(String readFileName, String readEncoding, String writeFileName, String writeEncoding) {
		List<String> lineList = this.readFile(readFileName, readEncoding);
		File writeFile = new File(writeFileName);
		if (!writeFile.exists()) {
			try {
				FileUtils.touch(writeFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				FileUtils.delete(writeFile);
				FileUtils.touch(writeFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			FileUtils.writeLines(writeFile, writeEncoding, lineList, "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void renameFile(String oldFileName, String newFileName) {
		File oldFile = new File(oldFileName);
		if (!oldFile.exists()) {
			try {
				FileUtils.touch(oldFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		File newFile = new File(newFileName);
		if (newFile.exists()) {
			try {
				FileUtils.delete(newFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		oldFile.renameTo(newFile);
	}
	
	public void ForEachPath(String path) {
		File filePath = new File(path);
		Collection<File> files = FileUtils.listFiles(filePath, null, true);
		for (File file : files) {
			if (!file.isDirectory()) {
				System.out.println(file);
			}
		}
	}
}
