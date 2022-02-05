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
			// �����ַ������,��װ�ļ�
        writer = new BufferedWriter(new OutputStreamWriter
        		(new FileOutputStream((filePath)),encoding));
        	writer.write("��ʵ��ʵ��\r\n");
        	writer.flush();
        	writer.write("��İ�ʩ��������\r\n");
        	writer.flush();	
        	writer.write("���Ͽ�������ĥID��ŵά��\r\n");
        	writer.flush();
        	writer.write("�����Ⱥ�޶�Ȥζ����\r\n");
        	writer.flush();
        	writer.write("�Ҵ���\r\n");
        	writer.flush();
        	writer.write("�������������ݿ�\r\n");
        	writer.flush();
        	writer.write("���Ͽ����Ҵ����ϵ�Ŷ��λ��\r\n"); 
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

	public void englishOutput(String filePath,String encoding) {
		BufferedWriter writer = null;
		try {
			// �����ַ������,��װ�ļ�
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
	
	public void fileReader(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println("��" + (i + 1) +"��" + ": " + list.get(i));
        }
	}
	
	public void fileKeyWord(List<String> list,String KeyWord) {
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).contains(KeyWord)) {
				System.out.println("��" + (i + 1) +"��" + ": " + list.get(i));
			}
		}
	}
	
	/*
	 *  �ֽ��������ļ�
	 *  �������黺�����Ч��
	 *  �ֽ�����
	 *  FileInputStream �����ֽ�����
	 *  FileOutputStream ����ֽ�����
	 */
	public void copy(File src, File desc) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
        fis = new FileInputStream(src);
        fos = new FileOutputStream(desc);
        	// �����ֽ�����,����
        	byte[] bytes = new byte[1024];
        	// ��ȡ����,д������
            int len = 0;
            while ((len = fis.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            	}
            }catch(IOException ex){
            	System.out.println(ex);
            	throw new RuntimeException("�ļ�����ʧ��");
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
	
	public boolean changeName(String oldname, String newname){
        File file1 = new File(oldname);
        File file2 = new File(newname);
        return file1.renameTo(file2);
    }
	
	public void getAllDir(File dir){
        System.out.println(dir);
        // ���÷���listFiles()��Ŀ¼,dir���б���
        File[] fileArr = dir.listFiles();
        for(File f : fileArr){
        	// �жϱ���f��ʾ��·���ǲ����ļ���
            if(f.isDirectory()){
                getAllDir(f);
            }else {
                System.out.println(f);
            }
        }
    }
}