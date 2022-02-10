package function;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author ni
 *
 */

public class FileOperate {
	/**
	 * 把集合中的数据存储到文本内
	 * @param filePath String 文件路径
	 * @param encoding String 文件编码
	 */
	public void chineseOutput(String filePath,String encoding){
		BufferedWriter writer = null;
		List<String> list = new ArrayList<String>();
		try {
			list.add("太乙三门诀，元君六甲符。下传金版术，上刻玉清书。有美探真士，囊中得秘书。自知三醮后，翊我灭残胡。《赐道士邓紫阳》李隆基");
			list.add("先生年几许，颜色似童儿。夜抱九仙骨，朝披一品衣。不食千钟粟，唯餐两颗梨。天生此间气，助我化无为。《赐梨李泌与诸王联句》李亨");
			list.add("十年生死两茫茫，不思量，自难忘。《江城子·乙卯正月二十日夜记梦》苏轼");
			list.add("山无陵，江水为竭。冬雷震震，夏雨雪。天地合，乃敢与君绝。《上邪》佚名");
			list.add("人皆苦炎热，我爱夏日长。熏风自南来，殿阁生微凉。《夏日联句》李昂");
			list.add("人间四月芳菲尽，山寺桃花始盛开。《大林寺桃花》白居易");
			// 创建字符输出流,封装文件
        writer = new BufferedWriter(new OutputStreamWriter
        		(new FileOutputStream((filePath)),encoding));
	        for(int i = 0;i < list.size();i++) {
	            String line = list.get(i);
	            writer.write(line);
	            writer.newLine();
	            writer.flush();
	        }
		}catch(FileNotFoundException f){
        	System.out.println("此路径不正确，请确认后重试");
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
	

	/**
	 * 把集合中的数据存储到文本文档内
	 * @param filePath String 文件路径
	 * @param encoding String 文件编码
	 */
	public void englishOutput(String filePath,String encoding) {
		BufferedWriter writer = null;
		List<String> list = new ArrayList<String>();
		try {
			list.add("beneath this mask ,there is more than flesh. there is an idea,and ideas are bulletproof.");
			list.add("with a lonely heart,you' ll feel rootless wherever you go.");
			list.add("Nothing is impossible!");
			list.add("This was the last drachm required to turn the scale of her indecision");
			list.add("While there is life there is hope.");
			list.add("Though this did not lessen the self-reproach which she continued to heap upon herself for her negligence.");
			// 创建字符输出流,封装文件
        writer = new BufferedWriter(new OutputStreamWriter
        		(new FileOutputStream((filePath)),encoding));
	        for(int i = 0;i < list.size();i++) {
	            String line = list.get(i);
	            writer.write(line);
	            writer.newLine();
	            writer.flush();
	        }
		}catch(FileNotFoundException f){
        	System.out.println("此路径不正确，请确认后重试");
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
	
	
	/**
	 * 判断文件并读取其中内容
	 * @param filePath 文件所在的路径
	 * @param encoding 文件编码
	 * @return list 返回文件
	 */
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
	
	
	/**
	 * 按行输出文件里的内容
	 * @param list 文件所在路径
	 */
	public void fileReader(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println("第" + (i + 1) +"行" + ": " + list.get(i));
        }
	}
	
	
	/**
	 * 输出带关键字的行
	 * @param list 文件所在路径
	 * @param KeyWord 关键字
	 */
	public void fileKeyWord(List<String> list,String KeyWord) {
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).contains(KeyWord)) {
				System.out.println("第" + (i + 1) +"行" + ": " + list.get(i));
			}
		}
	}
	
	
	/**
	 * 文件复制
	 * @param src 文件所在地
	 * @param dest 文件目的地
	 */
	public void fileCopy(File src, File dest) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
        fis = new FileInputStream(src);
        fos = new FileOutputStream(dest);
        	// 定义字节数组,缓冲
        	byte[] bytes = new byte[1024];
        	// 读取数组,写入数组
            int len = 0;
            while ((len = fis.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            	}
            }catch(IOException ex){
            	System.out.println(ex);
            	throw new RuntimeException("文件复制失败")	;
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
	
	
	/**
	 * 文件重命名
	 * @param oldname 旧名字
	 * @param newname 新名字
	 * @return boolean 更改后的名字
	 */
	public boolean changeName(String oldname, String newname){
        File file1 = new File(oldname);
        File file2 = new File(newname);
        return file1.renameTo(file2);
    }
	
	
	/**
	 * 递归文件夹里面的文件并打印输出
	 * 也可以查询以某某结尾的文件
	 * 如果此路径名是绝对路径名，那么得到的每个路径名都是绝对路径名
	 * 如果此路径名是相对路径名，那么得到的每个路径名都是相对于同一目录的路径名
	 * @param path 文件路径
	 */
	public void relativePaths(File path){
		File[] list = path.listFiles();
        for(File f : list){
        	// 判断变量f表示的路径是不是文件夹,是的话就继续递归
            if(f.isDirectory()){
            	relativePaths(f);
            }else {
            	// 不是文件夹的话就打印输出
                System.out.println(f);
                // 也可以查询以某某后缀名结尾的文件
//                if(f.getName().toLowerCase().endsWith(".txt"))
//                	System.out.println(f);
            }
        }
    }
	
	
	/**
	 * 把文件夹下的文件名输出到文本文档中
	 * @param texts 文件夹名字
	 * @param filePath 文本文档路径
	 * @param encoding 文本文档编码
	 */
	public void no9Test(File texts, String filePath, String encoding) {
		BufferedWriter writer = null;
		File[] files = texts.listFiles();
		try {
			// 创建字符输出流,封装文件
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream((filePath)), encoding));
			for(File text : files) {
				writer.write(text + "\n");
			}
		}catch(NullPointerException n){
        	System.out.println("此路径不正确，请确认后重试");
		} catch (IOException ex) {
			System.out.println(ex);
			throw new RuntimeException("文件写入失败，请重试");
		} finally {
			try {
				// 判断对象是不是null
				if (writer != null)
					writer.close();
			} catch (IOException ex) {
				throw new RuntimeException("关闭资源失败");
			}
		}
	}
}