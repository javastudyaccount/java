package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FileOperate {
	public List<String> listAllFiles(String path) {
		List<String> relativePaths = new ArrayList<>();
		File filePath = new File(path);
		try {
			Collection<File> files = FileUtils.listFiles(filePath, null, true);
			for (File file : files) {
				if (!file.isDirectory()) {
//					System.out.println(file);
					relativePaths.add(file.toString());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("请输入有效的路径");
		}
		return relativePaths;
	}
}
