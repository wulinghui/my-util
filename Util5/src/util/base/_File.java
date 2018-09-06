package util.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public abstract class _File {
	public static final String WOKE_PATH = _Path.getWorkPath0();//System.getProperty("user.dir");
	public static final String separator = "\\"+File.separator;
	/**�����ļ�*/
	public static boolean createFile(File file) throws IOException{
		File fileParent = file.getParentFile(); 
		//������ļ��Ļ��Ͳ������ļ�����,��ʱ�����´�����ʧ����
		if(!fileParent.exists())	fileParent.mkdirs();
		//���ļ����ļ��еĻ����ȼ�ɾ��.
		if(file.exists() && file.isDirectory() ) file.delete();//��֪��file.exists()�Ƿ��Ƕ����У�� 
		//����ļ������ھʹ���
		if(!file.exists())	return	file.createNewFile();
		//TODO ǿ�������ļ�,ͬʱ����ļ����������ļ�,�����ܼ򵥵�ɾ�� 
		return false;
	}
	public static class _Path{
		/**�������ظ�·��		/E:/workSpace/Util5/bin/  */
		public static URL getClassRoot(Class<?> c) {
			return c.getResource("/");//	/E:/workSpace/Util5/bin/
		}/**�������ظ�����·��*/
		public static URL getAbsoluteClassRoot(Class<?> c) {
			return c.getClassLoader().getResource("");//	file:/E:/workSpace/Util5/bin/
		}/**��õ�ǰ�����ڵĹ���·�� */
		public static URL getClassPath(Class<?> c) {
			return c.getResource("");//	/E:/workSpace/Util5/bin/test/
		}/**��Ŀ·��
		 * @throws IOException */
		public static String getWorkPath() throws IOException {
			return new File("").getCanonicalPath();//	E:\workSpace\Util5
		}/**��Ŀ·��*/
		public static String getWorkPath0() {
//			E:\workSpace\Util5
			return System.getProperty("user.dir");
		}
		/**������е���·��,����jar��·��*/
		public static String getPath() {
			//E:\workSpace\Util5\bin;F:\xvxm\32\eclipse\plugins\org.junit_4.12.0.v201504281640\junit.jar;F:\xvxm\32\eclipse\plugins\org.hamcrest.core_1.3.0.v201303031735.jar;F:\xvxm\32\eclipse\configuration\org.eclipse.osgi\413\0\.cp;F:\xvxm\32\eclipse\configuration\org.eclipse.osgi\412\0\.cp
			return System.getProperty("java.class.path");
		}
	}
	/** ����ȫ�޶���ת���ɲ�ͬϵͳ��·���ָ��� */
	public static String toSeparator(String classFullName){return classFullName.replaceAll("\\.", separator);
	}/**�Ѳ�ͬϵͳ��·���ָ���ת������ȫ�޶���*/
	public static String toPoint(String pathSeparater){return pathSeparater.replaceAll(separator, "\\.");}
	/**�ӹ����ռ����ļ���@param xddvlujn -- ���·�� */
	public static String getFileNameFromWork(String xddvlujn){return WOKE_PATH + separator + xddvlujn;}
	/**�ӹ����ռ��þ���·��   @param xddvlujn -- �Զ�ƴ��  */
	public static String getAbsoluteFromWork(String...strings ) { return  WOKE_PATH + separator + joinPath(strings);}
	/** ��ù����ռ�����·��  ��ͷû/*/
	public static String getWorkRelativePath(File file){ return file.getAbsolutePath().substring( (WOKE_PATH+separator) .length() -1 ); }
	/**�ӹ����ռ��л�ô��Դ�����·��
	 * @throws IOException */
	public static File getSourcePath(){
		// ֱ����   ���ж��Ƿ���maven�ı�׼    �ٿ� src 
		File file = new File ( joinPath(WOKE_PATH , (String) Config.CONF[0][1] , (String)Config.CONF[0][2] , (String)Config.CONF[0][3] ) );
		file = file.exists() ? file : new File ( joinPath(WOKE_PATH , (String) Config.CONF[0][1] ) ) ;
		return file;
	}
	/** ƴ��·��
	 * E:\workSpace\Util5\\src\test\StringTest.java  --��ȷ��..
	 * */
	public static String joinPath(String...strings ){
		int len = strings.length;
		if(len < 2) throw new IndexOutOfBoundsException();
		String string = strings[0];
		for (int i = 1; i < len ; i++) {
			string += separator + strings[i];
		}
		return string;
	}
	/**ƴ�ӵĳ���
	 * @param "abc","def"
	 * @return "abc\def".length();
	 * */
	public static int joinLength(String ...strings){
		int len = strings.length;
		if(len < 2) throw new IndexOutOfBoundsException();
		int poinLength = File.separator.length();
		int index = strings[0].length();
		for (int i = 1; i < len ; i++) {
			index += poinLength + strings[i].length();
		}
		return index;
	}
	/**ͨ��src����ľ���·��2��ȫ�޶��� �����Ե�*/@Deprecated
	public static String absolutePath2ClassNameFromSrc(String absolutePath){
		return toPoint( absolutePath.substring( 
				_File.joinLength( _File.WOKE_PATH,(String) Config.CONF[0][1])  +  Config.INT[0][0]  
				                  , absolutePath.indexOf(  (String) Config.CONF[0][0])  ) );
	}
	/** ����·��2��ȫ�޶��� �����Ե�*/@Deprecated
	public static String absolutePath2ClassName(String absolutePath,String src,String end){
		return toPoint( absolutePath.substring(
				_File.joinLength( _File.WOKE_PATH,src ) + Config.INT[0][0]  
				       , absolutePath.indexOf( end) ) );
	}
	/**ͨ��class��Root--����·��2��ȫ�޶��� */
	public static String absolutePath2ClassName(String absolutePath,Class<?> c){
		return toPoint(      
				absolutePath.substring( 			// TODO ����length() --����Ҫ-1
				_Path.getClassRoot(c).getPath().length()-1  , //start-classroot 
				absolutePath.indexOf('.') )  );//end -- .class �� .java����.
	} /**ͨ��class��Root--����·��2��ȫ�޶��� */         
	public static String absolutePath2ClassName(String absolutePath){
		return absolutePath2ClassName(absolutePath , String.class);
	}
	/** ����2����Դ·�� --src�µ�·��  ,������ʹ��  */
	public static String package2SourcePath(String packa){ return package2SourcePath( (String) Config.CONF[0][1] , packa); }
	/** ����2����Դ·�� --src  */
	public static String package2SourcePath(String src,String packa){
		return joinPath( _File.WOKE_PATH, src , toSeparator(packa) ); 
	}
	/** ����2����class·�� --bin�µ�·��  ,������ʹ�� */
	public static String package2ClassPath(Class<?> cla , String packa){
		return joinPath( _Path.getClassRoot(cla).getPath() ,toSeparator( packa ) ); 
	}
	/** cla-�ð������һ����   ����2����·��*/
	public static String package2Path(Class<?> cla) {
		return _Path.getClassPath(cla).getPath();
	}
	/** �滻�����ռ���Ŀ�е����·�� */
	public static File repalceRelativePathOfWork(File file,String regex,String replacement)  {
		return 	new File( joinPath( _Path.getWorkPath0() , 
				getWorkRelativePath(file).replaceAll(regex, replacement) ) );
	}
	/**���������е��ļ�,�����Լ�.
	 * @param fi
	 * @param h
	 * @throws Ex 
	 * @throws Throwable 
	 */@Deprecated
	public static <Ex extends Throwable> void forFile(File fi,FileFilter filter,HandlerFile<Ex> h) throws Ex {
		 if( !fi.isDirectory() ) return;
//		System.out.println("fi="+fi);
//		File files [] = fi.listFiles(filter);
//		_Sysout.print("forFile=",files);
		for (File file : fi.listFiles(filter) ) {
//			System.out.println("file="+file);
			//������ļ���,�͵ݹ鴦��
			if(file.isDirectory())	forFile(file,filter,h);
			//�����ļ�.
			h.handler(file);//�쳣�����Լ�����.    
		}
	}
	
	/**�ο�����: FilenameFilter ��  FileFilter������
public File [] listFiles(java.io.FilenameFilter) 
java.io.FilenameFilter���ļ����������ӿڡ�����������ʵ�ִ˽ӿڡ��ýӿڶ�����һ��
����������                                 file.getPath		file.getName();
public boolean accept(File file, String filename)��������һ������File fileΪ���ڱ����˵��ļ����ڶ�������Ϊ���ڱ����˵��ļ�����FilenameFilter.accept����false���ļ��ᱻ���˵���
�÷�������ƥ��FilenameFilter��ָ���������ļ�
public File [] listFiles(java.io.FileFilter)
public boolean accept(File file)��������һ������File fileΪ���ڱ����˵��ļ���FileFilter.accept����false���ļ��ᱻ���˵���
�÷�������ƥ��FileFilter��ָ���������ļ���
���ڱ����˵��ļ���ָ�Լ�����..
@see Filter
	 */@Deprecated//������FileFilter
	public static interface FileFilter extends FilenameFilter{
		FileFilter JAVA = new FileFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".java") || name.endsWith(".class");
			}
		};
		FileFilter NO_FILTER = new FileFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return true;
			}
		};
		/**���ݹ�,���ǵ�����*/@Deprecated
		public static class NoDiGui implements FileFilter{
			boolean flag = true;
			
			public boolean accept(File dir, String name) {
				System.out.println("Dir="+dir); //��file.listFiles() ��file;
				System.out.println(name);//���ڱ����˵�file.getName();
				return dir.isFile();
				
//				if( flag ) { // Ϊ�˷�ֹ��һ�����ļ���.
//					this.flag = false;
//					return true;
//				}else {  
////					System.out.println("dir="+dir);
////					System.out.println( !dir.isDirectory() );
//					return  !dir.isDirectory();
//				}
			}
		}
	}
	 public static <Ex extends Throwable> void forFile(File fi,Filter filter,HandlerFile<Ex> h) throws Ex {
//			System.out.println("fi="+fi);
//			File files [] = fi.listFiles(filter);
//			_Sysout.print("forFile=",files);
			for (File file : fi.listFiles(filter) ) {
//				System.out.println("file="+file);
				//������ļ���,�͵ݹ鴦��
				if(file.isDirectory())	forFile(file,filter,h);
				//�����ļ�.
				h.handler(file);//�쳣�����Լ�����.    
			}
		}
	
	/**���������е��ļ�,�����Լ�.
	 * @param fi
	 * @param h
	 * @throws Ex 
	 * @throws Throwable 
	 */
	 public static <Ex extends Throwable> void forFile(File fi,HandlerFile<Ex> h) throws Ex { forFile(fi, FileFilter.NO_FILTER, h);}
	 public static interface Filter extends java.io.FileFilter{
		 Filter NODIGUI = new Filter() {
			//������������ pathname.
			public boolean accept(File pathname) {
//				pathname.getParentFile();//accept.file
//				pathname.getName();// accept.name
				return pathname.isFile();
			}
		 };
	}
	/**
	 * @author wlh
	 * ��ö��������ӿڻص�..
	 * �ŵ�:����,ͳһ���(�û������Բ�����,�����ڲ���.--��ȫ),�����л�
	 * ȱ��:��չ�Բ���,�Ҵ�����ڼ���.
//	public static enum Handler{
//		DELETE {
//			public void handler(File file) {
//				file.delete();
//			}
//		};  
//		public abstract void handler(File file);
//	}
	 * ��������ýӿڵ�public static final .
	 * �ŵ�:����,��չ�Ժ�.���enumЧ�ʸߺ��ڴ��.
	 * ȱ��:�޷�ͳһ���,����ȫ,�������л�
	 */

	public static interface HandlerFile<Ex extends Throwable>{
		/**ɾ���ļ�*/
		HandlerFile<FileNotFoundException> DELETE = new HandlerFile<FileNotFoundException>(){
			@Override
			public void handler(File file) {file.delete();}
		};
		/** ��β����ļ����ļ���  */
		void handler(File file) throws Ex;
	}
	/** ɾ���ļ����ļ���(����) */
	public static boolean delete(File fi){
		if(  fi.isDirectory() ){  
			try {
				forFile(fi,HandlerFile.DELETE);     
			} catch (IOException e) {
				return false;
			}
		}//�ٰ��Լ�ɾ��..
		return fi.delete();            
	}
	 /**
     * ɾ���ļ����������ļ����ļ���
     * @param fileName
     *            Ҫɾ�����ļ���
     * @return ɾ���ɹ�����true�����򷵻�false
     */
    public static boolean delete(String fileName) {return delete(new File(fileName));}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////   
    /*
     * java�����ļ���4�ַ�ʽ
     * 
     * @author Administrator ����Java�ṩ��һ�����Դ����ļ���IO�����ࡣ ����û��һ�������ļ��ķ�����
     *         �����ļ���һ����Ҫ�Ĳ���,����ĳ�����봦��ܶ��ļ���ص�ʱ��
     *         Ȼ���м��ַ������Խ���Java�ļ����Ʋ���,�����оٳ�4�����ܻ�ӭ�ķ�ʽ��*/
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * ʹ��FileStreams����
	 * 
	 * �������ķ�ʽ��һ���ļ������ݸ��Ƶ���һ���ļ��С�
	 * ʹ��FileInputStream��ȡ�ļ�A���ֽڣ�ʹ��FileOutputStreamд�뵽�ļ�B�� ���ǵ�һ�������Ĵ���:
	 * 
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	public static void copyFileUsingFileStreams(File source, File dest)
			throws IOException {
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new FileInputStream(source);
			output = new FileOutputStream(dest);
//			Writer wr = null;
//			Reader r = null;
//			PrintWriter pw  = null;
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);
			}
		} finally {
			if(input!=null)input.close();
			if(output!=null)output.close();
		}
	}
	
	/**ʹ��FileChannel����
Java NIO����transferFrom����,�����ĵ�Ӧ�ñ��ļ������Ƶ��ٶȸ��졣 ���ǵڶ��ַ����Ĵ���
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	public static void copyFileUsingFileChannels(File source, File dest) throws IOException {    
        FileChannel inputChannel = null;    
        FileChannel outputChannel = null;    
    try {
        inputChannel = new FileInputStream(source).getChannel();
        outputChannel = new FileOutputStream(dest).getChannel();
        outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
    } finally {
        inputChannel.close();
        outputChannel.close();
    }
}
	 /**ʹ��Commons IO����
	 Apache Commons IO�ṩ�����ļ���������FileUtils��,�����ڸ���һ���ļ�����һ���ط������ǳ�����ʹ��Apache Commons FileUtils��ʱ,���Ѿ�ʹ��������Ŀ��������,�����ʹ��Java NIO FileChannel�ڲ��� ���ǵ����ַ����Ĵ���:
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	public static void copyFileUsingApacheCommonsIO(File source, File dest)
	         throws IOException {
//	     FileUtils.copyFile(source, dest);
	 }

	/**	ʹ��Java7��Files�ิ��
	�������һЩ������Java 7������ܻ�֪��,����ʹ�ø��Ʒ�����Files���ļ�,��һ���ļ����Ƶ���һ���ļ��� ���ǵ��ĸ������Ĵ���:
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	public static void copyFileUsingJava7Files(File source, File dest)
	        throws IOException {    
//	      Files.copy(source.toPath(), dest.toPath());
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * Ϊ�ļ�����.-��ʱ��,��С,����,����,������Щ���.
	 * file.compareTo(pathname);--ֻ�ܶ�·������.. 		OrderByFile
	 */
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	  /**1���� �ļ����� ����
	   * 
	 * @param filePath  
	 */
	public static void orderByName(String filePath) {
	        File file = new File(filePath);
	        File[] files = file.listFiles();
	        List fileList = Arrays.asList(files);
	        Collections.sort(fileList, new Comparator<File>() {
	            @Override
	            public int compare(File o1, File o2) {
	                if (o1.isDirectory() && o2.isFile())
	                    return -1;
	                if (o1.isFile() && o2.isDirectory())
	                    return 1;
	                return o1.getName().compareTo(o2.getName());
	            }
	        });
	        for (File file1 : files) {
//	            System.out.println(file1.getName());

	        }
	 }
	
	/**2���� �ļ����� ����
	 * �� �ļ��޸����ڣ����� 
	 * @param filePath
	 */
	public static void orderByDate(String filePath) {
        File file = new File(filePath);
        File[] files = file.listFiles();
        Arrays.sort(files, new Comparator<File>() {
            public int compare(File f1, File f2) {
                long diff = f1.lastModified() - f2.lastModified();
                if (diff > 0)
                    return 1;
                else if (diff == 0)
                    return 0;
                else
                    return -1;//��� if ���޸�Ϊ ����-1 ͬʱ�˴��޸�Ϊ���� 1  ����ͻ��ǵݼ�
            }

            public boolean equals(Object obj) {
                return true;
            }

        });
//        for (int i = 0; i < files.length; i++) {
//            System.out.println(files[i].getName());
//            System.out.println(new Date(files[i].lastModified()));
//        }
    }
	   /** 3 �� �ļ��������� ����    
     * @param _file _file
     * @return datetime datetime
     * ���Է�������������ã��� windows������
     */
	public static String getFileCreateDate(File _file) {

        File file = _file;

        try {

            Process ls_proc = Runtime.getRuntime().exec("cmd.exe /c dir " + file.getAbsolutePath() + " /tc");

            BufferedReader br = new BufferedReader(new InputStreamReader(ls_proc.getInputStream()));

            for (int i = 0; i < 5; i++) {

                br.readLine();

            }


            String stuff = br.readLine();

            if (stuff == null) {
                return "";
            }

            StringTokenizer st = new StringTokenizer(stuff);

            String dateC = st.nextToken();

            String time = st.nextToken();

            String datetime = dateC.concat(time);

            br.close();

            return datetime;

        } catch (Exception e) {

            return null;

        }

    }
	/**4���� �ļ���С ����
	 * @param filePath
	 */
	public static void orderByLength(String filePath) {
        File file = new File(filePath);
        File[] files = file.listFiles();
        List<File> fileList = Arrays.asList(files);
        Collections.sort(fileList, new Comparator<File>() {
            public int compare(File f1, File f2) {
                long diff = f1.length() - f2.length();
                if (diff > 0)
                    return 1;
                else if (diff == 0)
                    return 0;
                else
                    return -1;//��� if ���޸�Ϊ ����-1 ͬʱ�˴��޸�Ϊ���� 1  ����ͻ��ǵݼ�
            }

            public boolean equals(Object obj) {
                return true;
            }
        });
//        for (File file1 : files) {
//            if (file1.isDirectory()) continue;
//            System.out.println(file1.getName() + ":" + file1.length());
//        }
    }
}
