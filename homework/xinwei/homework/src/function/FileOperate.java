package function;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class FileOperate {

	public void chineseOutput(String filePath,String encoding){
		BufferedWriter writer = null;
		try {
			// 创建字符输出流,封装文件
        writer = new BufferedWriter(new OutputStreamWriter
        		(new FileOutputStream((filePath)),encoding));
        	writer.write("啊实打实的\r\n");
        	writer.flush();
        	writer.write("打的奥施康定马上\r\n");
        	writer.flush();	
        	writer.write("撒断开连接琢磨ID美诺维奇\r\n");
        	writer.flush();
        	writer.write("王企鹅群无恶趣味安苏\r\n");
        	writer.flush();
        	writer.write("我打死\r\n");
        	writer.flush();
        	writer.write("我我我撒大数据库\r\n");
        	writer.flush();
        	writer.write("萨迪克吗我大晚上的哦价位我\r\n"); 
        }catch(IOException ex) {
        	System.out.println(ex);
        	throw new RuntimeException("文件写入失败，请重试");
        }finally {
        	try {
        		// 判断对象是不是null
        		if(writer != null)
        			writer.close();
        	}catch(IOException ex) {
        		throw new RuntimeException("关闭资源失败");
        	}
		}
    }

	public void englishOutput(String filePath,String encoding) {
		BufferedWriter writer = null;
		try {
			// 创建字符输出流,封装文件
        writer = new BufferedWriter(new OutputStreamWriter
        		(new FileOutputStream((filePath)),encoding));
	        writer.write("this damasked\r\n");
	        writer.flush();
	        writer.write("wastes waterside\r\n");
	        writer.flush();
	        writer.write("wqexzdcaesffsgdrfyh thistles\r\n");
	        writer.flush();
	        writer.write("wqezxczxfewrtgfdsgfdsg\r\n");
	        writer.flush();
	        writer.write("wqesazxczsxcaerhbdc\r\n");
	        writer.flush();
	        writer.write("thisdzssd  this\r\n");
	        writer.flush();
	        writer.write("wqeszdczxthis\r\n");
		}catch(IOException ex) {
			System.out.println(ex);
			throw new RuntimeException("文件写入失败，请重试");
		}finally {
			try {
				// 判断对象是不是null
				if(writer != null)
					writer.close();
			}catch(IOException ex) {
				throw new RuntimeException("关闭资源失败");
			}
		}
	}
	
	public List<String> inputFile(String filePath,String encoding) {
        List<String> list = new ArrayList<String>();
        try {
            File file = new File(filePath);
            // 判断文件是否存在
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    list.add(lineTxt);
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception ex) {
            System.out.println("读取文件内容出错");
            ex.printStackTrace();
        }
        return list;
    }
	
	public void fileReader(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println("第" + (i + 1) +"行" + ": " + list.get(i));
        }
	}
	
	public void fileKeyWord(List<String> list,String KeyWord) {
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).contains(KeyWord)) {
				System.out.println("第" + (i + 1) +"行" + ": " + list.get(i));
			}
		}
	}
	
	/*
	 *  字节流复制文件
	 *  采用数组缓冲提高效率
	 *  字节数组
	 *  FileInputStream 输入字节数组
	 *  FileOutputStream 输出字节数组
	 */
	public void copy(File src, File desc) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
        fis = new FileInputStream(src);
        fos = new FileOutputStream(desc);
        	// 定义字节数组,缓冲
        	byte[] bytes = new byte[1024];
        	// 读取数组,写入数组
            int len = 0;
            while ((len = fis.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            	}
            }catch(IOException ex){
            	System.out.println(ex);
            	throw new RuntimeException("文件复制失败");
        	}finally {
        		try {
        			if(fos != null)
        				fos.close();
        	}catch(IOException ex){
        		throw new RuntimeException("释放资源失败");
        	}finally {
        		try{
        			if(fis != null)
        				fis.close();
        		}catch(IOException ex) {
        			throw new RuntimeException("释放资源失败");
        		}
        	}
        }
	}
	
	public boolean changeName(String oldname, String newname){
        File file1 = new File(oldname);
        File file2 = new File(newname);
        return file1.renameTo(file2);
    }
	
	public void getAllDir(File dir){
        System.out.println(dir);
        // 调用方法listFiles()对目录,dir进行遍历
        File[] fileArr = dir.listFiles();
        for(File f : fileArr){
        	// 判断变量f表示的路径是不是文件夹
            if(f.isDirectory()){
                getAllDir(f);
            }else {
                System.out.println(f);
            }
        }
    }
}