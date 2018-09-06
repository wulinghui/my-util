package util.extend.database.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import util.base._File;

/**
 * �ļ������ݿ�,ͨ���ļ��־û�����
 * ��׺��Ϊ���ݿ��� 
 * @author wulinghui
 */
public abstract class FileSql {
	
	public static void main(String[] args) {
		String a = System.getProperty("user.dir");
//		System.out.println(a);
	}
	final String suf;//����
	final String directory;//�ֱ�
	static final Map<String,FileSql> m = new HashMap<String,FileSql>();
//	static final Console console = Console.getInstance( FileSql.class );
	
	FileSql(String suf,Class<?> directory){
		this.suf = suf;
		this.directory = toPath(directory.getName());
//		console.log("this.directory=" + this.directory );
		m.put(suf+"+"+directory.getName(), this);
	}/**�Ա���,��·��Ϊһ������
	 * @param tableName --����
	 * @param search --����*/
	public static FileSql getInstance(String tableName,Class<?> search){
		FileSql fs = m.get( tableName+"+"+search.getName() );
		if( fs != null ) return fs;
		return new FileSql(tableName, search){};
	}
	/**��. -> �ĳ� ����ϵͳ���ļ��ָ*/
	public static String toPath(String a){
		String sb = _File.WOKE_PATH + File.separator +a;
		return sb.replaceAll("\\.", "\\"+File.separator);//��������,\\����Ҫ��
	}
	public  String getPathByFullClassName(String id){
		return this.directory + File.separator + id + "." + this.suf;
	}
	public String putPro(String id,Map map) throws IOException{
		Properties pro = new Properties();
		pro.putAll(map);
		String pa = getPathByFullClassName(id);
		FileOutputStream oFile = new FileOutputStream(pa);//true��ʾ׷�Ӵ�
		pro.store(oFile,null);
		return pa;
	}
	public Properties getPro(String id) throws IOException{
		FileInputStream in1 = null;
		Properties pro = new Properties();
		try {
			in1 = new FileInputStream( getPathByFullClassName(id) );
			pro.load(in1);
		} finally{
			if(in1 != null) in1.close();
		}
		return pro;
	}
	
	
	public  void serialize(Object user,String id) throws IOException {
		OutputStream out = null;
		ObjectOutputStream oos = null;
		 try{
		 out = new FileOutputStream( getPathByFullClassName(id) );
		 oos = new ObjectOutputStream(out);
	     oos.writeObject(user);
	     oos.flush();
		 }finally{
			 if(out!=null)out.close();
	    	 if(oos!=null)oos.close();
	     }
	}
	/**�����л��ɶ���
	 * @param in
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public  <T> T deserialize(String id) throws IOException, ClassNotFoundException{
		 InputStream in = null;
		 ObjectInputStream ois = null;
		 T t = null;
		 try{
			 in = new FileInputStream( getPathByFullClassName(id) );
			 ois = new ObjectInputStream(in);
			 t = (T) ois.readObject();
		 }finally{
			 if(in != null) in.close();
			 if(ois !=null) ois.close();
		 }
	     return  t;
	}
}
