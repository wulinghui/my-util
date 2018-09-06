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
 * 文件型数据库,通过文件持久化数据
 * 后缀名为数据库名 
 * @author wulinghui
 */
public abstract class FileBaseUtil {

	
	public static void main(String[] args) {
		String a = System.getProperty("user.dir");
//		System.out.println(a);
	}
	final String suf;//表名--后缀
	final String directory;//分表--路径
	static final Map<String,FileBaseUtil> m = new HashMap<String,FileBaseUtil>();
//	static final Console console = Console.getInstance( FileBaseUtil.class );
	
	FileBaseUtil(String suf,Class<?> directory){
		this.suf = suf;
		this.directory = toPath(directory.getName());
//		console.log("this.directory=" + this.directory );
		m.put(suf+"+"+directory.getName(), this);
	}/**以表名,和路径为一个对象
	 * @param tableName --表名
	 * @param search --检索*/
	public static FileBaseUtil getInstance(String tableName,Class<?> search){
		FileBaseUtil fs = m.get( tableName+"+"+search.getName() );
		if( fs != null ) return fs;
		return new FileBaseUtil(tableName, search){};
	}
	/**把. -> 改成 各个系统的文件分割。*/
	public static String toPath(String a){
		String sb = _File.WOKE_PATH + File.separator +a;
		return sb.replaceAll("\\.", "\\"+File.separator);//都是正则,\\是需要的
	}
	public  String getPathByFullClassName(String id){//id文件名
		return this.directory + File.separator + id + "." + this.suf;
	}
	public String putPro(String id,Map map) throws IOException{
		Properties pro = new Properties();
		pro.putAll(map);
		String pa = getPathByFullClassName(id);
		FileOutputStream oFile = new FileOutputStream(pa);//true表示追加打开
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
		 String path = getPathByFullClassName(id);
		 out = new FileOutputStream( path );
		 oos = new ObjectOutputStream(out);
	     oos.writeObject(user);
	     oos.flush();
		 }finally{
			 if(out!=null)out.close();
	    	 if(oos!=null)oos.close();
	     }
	}
	/**反序列化成对象
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
