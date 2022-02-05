package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * @author tingh
 *
 */
public class FileOperate {
	
	/**
	 * @param fileName 读取文件路径
	 * @param encoding 读取文件的编码
	 * @return 包含行的list
	 */
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
	
	/**
	 * @param lineList 包含行的list
	 * 
	 */
	public void outputToConsole(List<String> lineList) {
		
		for (String line : lineList) {
			System.out.println(line);
		}
	}
	

	/**
	 * @param lineList 包含行的list
	 * @param text 过滤的字符串
	 */
	public void outputToConsoleContainText(List<String> lineList, String text) {
		
		for (String line : lineList) {
			if (line.contains(text)) {
				System.out.println(line);
			}
		}
	}

	/**
	 * @param readFileName 读取的文件路径
	 * @param readEncoding 读取的文件编码
	 * @param writeFileName 写入的文件路径
	 * @param writeEncoding 写入的文件编码
	 */
	public void writeAnotherFile(String readFileName, String readEncoding, 
			String writeFileName, String writeEncoding) {
		
		List<String> lineList = readFile(readFileName, readEncoding);
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

	/**
	 * @param oldFileName 原文件的路径
	 * @param newFileName 修改名称之后的路径
	 */
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
	
//	/**
//	 * List all files under path, including files in subdirectories
//	 * @param path 需要遍历文件的文件夹的路径
//	 */
//	public void listAllFiles(String path) {
//		
//		File filePath = new File(path);
//		Collection<File> files = FileUtils.listFiles(filePath, null, true);
//		for (File file : files) {
//			if (!file.isDirectory()) {
//				System.out.println(file);
//			}
//		}
//	}
	
	/**
	 * List all files under path, including files in subdirectories
	 * @param path 需要遍历文件的文件夹的路径
	 * @return List of file relative path
	 */
	public List<String> listAllFiles(String path) {
		List<String> relativePaths = new ArrayList<>();
		File filePath = new File(path);
		Collection<File> files = FileUtils.listFiles(filePath, null, true);
		for (File file : files) {
			if (!file.isDirectory()) {
//				System.out.println(file);
				relativePaths.add(file.toString());
			}
		}
		return relativePaths;
	}
	
	
}
