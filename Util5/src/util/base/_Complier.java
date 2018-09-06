package util.base;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.junit.Test;

import util.base._File._Path;


public class _Complier {
	public static  boolean complier(String sourceStr , String clsName , String...strings ) {
		//��ǰ������  
        JavaCompiler cmp = ToolProvider.getSystemJavaCompiler(); 
        //Java��׼�ļ�������  
        StandardJavaFileManager fm = cmp.getStandardFileManager(null,null,null);  
        //Java�ļ�����  
        JavaFileObject jfo = new StringJavaObject(clsName,sourceStr);
      //���������������javac <options>�е�options  
        List<String> optionsList = new ArrayList<String>(); 
      //�����ļ��Ĵ�ŵط���ע�⣺�˴���ΪEclipse���������  
        optionsList.addAll(Arrays.asList(strings));//"-d","./bin"
        //Ҫ����ĵ�Ԫ  
        List<JavaFileObject> jfos = Arrays.asList(jfo);  
        //���ñ��뻷��  
        JavaCompiler.CompilationTask task = cmp.getTask(null, fm, null, optionsList,null,jfos);
      //����ɹ�  
       return task.call();
	}
	public static  boolean complier(String sourceStr , String clsName , String[]strings , JavaFileObject[] javaFileObjects ) {
		//��ǰ������  
        JavaCompiler cmp = ToolProvider.getSystemJavaCompiler(); 
        //Java��׼�ļ�������  
        StandardJavaFileManager fm = cmp.getStandardFileManager(null,null,null);  
        //Java�ļ�����  
//        JavaFileObject jfo = new StringJavaObject(clsName,sourceStr);
      //���������������javac <options>�е�options  
        List<String> optionsList = Arrays.asList(strings) ; // new ArrayList<String>(); 
      //�����ļ��Ĵ�ŵط���ע�⣺�˴���ΪEclipse���������  
//        optionsList.addAll(Arrays.asList(strings));//"-d","./bin"
        //Ҫ����ĵ�Ԫ  
        List<JavaFileObject> jfos = Arrays.asList( javaFileObjects );  
        //���ñ��뻷��  
        JavaCompiler.CompilationTask task = cmp.getTask(null, fm, null, optionsList,null,jfos);
      //����ɹ�  
       return task.call();
	}
	
	public  static boolean complier(String sourceStr , String clsName  ) {      
		return complier(sourceStr, clsName, "-d", /*"./bin"*/  _Path.getClassRoot(String.class).getPath() );
	}
	@Test
	public void a1111() throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		//JavaԴ����  
        String sourceStr = "package util.base1; public class Hello{    public String sayHello (String name) {return \"Hello,\" + name + \"!\";}}";  
        //�������ļ���  
        String clsName = "Hello";  
        //������  
        String methodName = "sayHello";  
        //��ǰ������  
        JavaCompiler cmp = ToolProvider.getSystemJavaCompiler();  
        //Java��׼�ļ�������  
        StandardJavaFileManager fm = cmp.getStandardFileManager(null,null,null);  
        //Java�ļ�����  
        JavaFileObject jfo = new StringJavaObject(clsName,sourceStr);  
        //���������������javac <options>�е�options  
        List<String> optionsList = new ArrayList<String>();  
        //�����ļ��Ĵ�ŵط���ע�⣺�˴���ΪEclipse���������  
        optionsList.addAll(Arrays.asList("-d", _Path.getClassRoot(String.class).getPath() ));  //"./bin"
        //Ҫ����ĵ�Ԫ  
        List<JavaFileObject> jfos = Arrays.asList(jfo);  
        //���ñ��뻷��  
        JavaCompiler.CompilationTask task = cmp.getTask(null, fm, null, optionsList,null,jfos);  
        //����ɹ�  
        if(task.call()){  
            //���ɶ���  
            Object obj = Class.forName("util.base1."+clsName).newInstance();  
            Class<? extends Object> cls = obj.getClass();  
            System.out.println( cls );
            //����sayHello����  
            Method m = cls.getMethod(methodName, String.class);  
            String str = (String) m.invoke(obj, "Dynamic Compilation");  
            System.out.println(str);  
       }
	}
	
	
	
	//�ı��е�Java����  sa
	public static class StringJavaObject extends SimpleJavaFileObject{  
		    //Դ����  
		    private String content = "";  
		    //��ѭJava�淶���������ļ�  
		    public StringJavaObject(String _javaFileName,String _content){
		          super(_createStringJavaObjectUri(_javaFileName),Kind.SOURCE);  
		          content = _content;  
		    }  
		    //����һ��URL��Դ·��  
		    private static URI _createStringJavaObjectUri(String name){  
		       //ע��˴�û�����ð���  
		       return URI.create("String:///" + name + Kind.SOURCE.extension);  
		    }  
		    //�ı��ļ�����  
		    @Override  
		    public CharSequence getCharContent(boolean ignoreEncodingErrors)  
		           throws IOException {  
		       return content;  
		   }
		}
	
	public static class FileJavaObject extends SimpleJavaFileObject{
		File f;
		protected FileJavaObject(File f ) {
			super( f.toURI() , Kind.SOURCE);
			this.f = f ;
		}
		@Override  
	    public CharSequence getCharContent(boolean ignoreEncodingErrors)  
	           throws IOException { 
	       return String.valueOf( _Reader.notOutOfMemory(f) );  
	   }
	}
	
}	
	

