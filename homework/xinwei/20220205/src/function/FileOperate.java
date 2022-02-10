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
	 * �Ѽ����е����ݴ洢���ı���
	 * @param filePath String �ļ�·��
	 * @param encoding String �ļ�����
	 */
	public void chineseOutput(String filePath,String encoding){
		BufferedWriter writer = null;
		List<String> list = new ArrayList<String>();
		try {
			list.add("̫�����ž���Ԫ�����׷����´���������Ͽ������顣����̽��ʿ�����е����顣��֪�����������к������͵�ʿ����������¡��");
			list.add("�����꼸����ɫ��ͯ����ҹ�����ɹǣ�����һƷ�¡���ʳǧ���ڣ�Ψ�������档�����˼��������һ���Ϊ���������������������䡷���");
			list.add("ʮ��������ãã����˼�������������������ӡ���î���¶�ʮ��ҹ���Ρ�����");
			list.add("ɽ���꣬��ˮΪ�ߡ�������������ѩ����غϣ��˸������������а������");
			list.add("�˽Կ����ȣ��Ұ����ճ���Ѭ���������������΢�������������䡷�");
			list.add("�˼����·��ƾ���ɽ���һ�ʼʢ�������������һ����׾���");
			// �����ַ������,��װ�ļ�
        writer = new BufferedWriter(new OutputStreamWriter
        		(new FileOutputStream((filePath)),encoding));
	        for(int i = 0;i < list.size();i++) {
	            String line = list.get(i);
	            writer.write(line);
	            writer.newLine();
	            writer.flush();
	        }
		}catch(FileNotFoundException f){
        	System.out.println("��·������ȷ����ȷ�Ϻ�����");
        }catch(IOException ex) {
        	System.out.println(ex);
        	throw new RuntimeException("�ļ�д��ʧ�ܣ�������");   	
        }finally {
        	try {
        		// �ж϶����ǲ���null
        		if(writer != null)
        			writer.close();
        	}catch(IOException ex) {
        		throw new RuntimeException("�ر���Դʧ��");
        	}
		}
    }
	

	/**
	 * �Ѽ����е����ݴ洢���ı��ĵ���
	 * @param filePath String �ļ�·��
	 * @param encoding String �ļ�����
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
			// �����ַ������,��װ�ļ�
        writer = new BufferedWriter(new OutputStreamWriter
        		(new FileOutputStream((filePath)),encoding));
	        for(int i = 0;i < list.size();i++) {
	            String line = list.get(i);
	            writer.write(line);
	            writer.newLine();
	            writer.flush();
	        }
		}catch(FileNotFoundException f){
        	System.out.println("��·������ȷ����ȷ�Ϻ�����");
		}catch(IOException ex) {
			System.out.println(ex);
			throw new RuntimeException("�ļ�д��ʧ�ܣ�������");
		}finally {
			try {
				// �ж϶����ǲ���null
				if(writer != null)
					writer.close();
			}catch(IOException ex) {
				throw new RuntimeException("�ر���Դʧ��");
			}
		}
	}
	
	
	/**
	 * �ж��ļ�����ȡ��������
	 * @param filePath �ļ����ڵ�·��
	 * @param encoding �ļ�����
	 * @return list �����ļ�
	 */
	public List<String> inputFile(String filePath,String encoding) {
        List<String> list = new ArrayList<String>();
        try {
            File file = new File(filePath);
            // �ж��ļ��Ƿ����
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
                System.out.println("�Ҳ���ָ�����ļ�");
            }
        } catch (Exception ex) {
            System.out.println("��ȡ�ļ����ݳ���");
            ex.printStackTrace();
        }
        return list;
    }
	
	
	/**
	 * ��������ļ��������
	 * @param list �ļ�����·��
	 */
	public void fileReader(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println("��" + (i + 1) +"��" + ": " + list.get(i));
        }
	}
	
	
	/**
	 * ������ؼ��ֵ���
	 * @param list �ļ�����·��
	 * @param KeyWord �ؼ���
	 */
	public void fileKeyWord(List<String> list,String KeyWord) {
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).contains(KeyWord)) {
				System.out.println("��" + (i + 1) +"��" + ": " + list.get(i));
			}
		}
	}
	
	
	/**
	 * �ļ�����
	 * @param src �ļ����ڵ�
	 * @param dest �ļ�Ŀ�ĵ�
	 */
	public void fileCopy(File src, File dest) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
        fis = new FileInputStream(src);
        fos = new FileOutputStream(dest);
        	// �����ֽ�����,����
        	byte[] bytes = new byte[1024];
        	// ��ȡ����,д������
            int len = 0;
            while ((len = fis.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            	}
            }catch(IOException ex){
            	System.out.println(ex);
            	throw new RuntimeException("�ļ�����ʧ��")	;
        	}finally {
        		try {
        			if(fos != null)
        				fos.close();
        	}catch(IOException ex){
        		throw new RuntimeException("�ͷ���Դʧ��");
        	}finally {
        		try{
        			if(fis != null)
        				fis.close();
        		}catch(IOException ex) {
        			throw new RuntimeException("�ͷ���Դʧ��");
        		}
        	}
        }
	}
	
	
	/**
	 * �ļ�������
	 * @param oldname ������
	 * @param newname ������
	 * @return boolean ���ĺ������
	 */
	public boolean changeName(String oldname, String newname){
        File file1 = new File(oldname);
        File file2 = new File(newname);
        return file1.renameTo(file2);
    }
	
	
	/**
	 * �ݹ��ļ���������ļ�����ӡ���
	 * Ҳ���Բ�ѯ��ĳĳ��β���ļ�
	 * �����·�����Ǿ���·��������ô�õ���ÿ��·�������Ǿ���·����
	 * �����·���������·��������ô�õ���ÿ��·�������������ͬһĿ¼��·����
	 * @param path �ļ�·��
	 */
	public void relativePaths(File path){
		File[] list = path.listFiles();
        for(File f : list){
        	// �жϱ���f��ʾ��·���ǲ����ļ���,�ǵĻ��ͼ����ݹ�
            if(f.isDirectory()){
            	relativePaths(f);
            }else {
            	// �����ļ��еĻ��ʹ�ӡ���
                System.out.println(f);
                // Ҳ���Բ�ѯ��ĳĳ��׺����β���ļ�
//                if(f.getName().toLowerCase().endsWith(".txt"))
//                	System.out.println(f);
            }
        }
    }
	
	
	/**
	 * ���ļ����µ��ļ���������ı��ĵ���
	 * @param texts �ļ�������
	 * @param filePath �ı��ĵ�·��
	 * @param encoding �ı��ĵ�����
	 */
	public void no9Test(File texts, String filePath, String encoding) {
		BufferedWriter writer = null;
		File[] files = texts.listFiles();
		try {
			// �����ַ������,��װ�ļ�
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream((filePath)), encoding));
			for(File text : files) {
				writer.write(text + "\n");
			}
		}catch(NullPointerException n){
        	System.out.println("��·������ȷ����ȷ�Ϻ�����");
		} catch (IOException ex) {
			System.out.println(ex);
			throw new RuntimeException("�ļ�д��ʧ�ܣ�������");
		} finally {
			try {
				// �ж϶����ǲ���null
				if (writer != null)
					writer.close();
			} catch (IOException ex) {
				throw new RuntimeException("�ر���Դʧ��");
			}
		}
	}
}