package test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.junit.Test;

import util.base._File;

public class FileTest {
	static File f = new File("C://a.txt");
	File path = new File("E:\\workSpace\\Util5\\src\\test\\StringTest.java");
	@Test
	public void eq() {
		File sss = new File("E:\\workSpace\\Util5\\src\\test\\StringTest.java");
		System.out.println(sss.equals(path));
	}
	@Test
	public void repalceRelativePathOfWork() {
		File f = _File.repalceRelativePathOfWork(path, "test", "test");
		System.out.println( f.exists() );
	}
	@Test
	public void getWorkRelativePath() {
		System.out.println(_File.getWorkRelativePath(path) );
	}
	@Test
	public void isFile(){
		//不存在也会flase;不是throw error;
		System.out.println(f.isFile());
	}@Test
	public void delete(){ System.out.println( f.delete());}
	@Test
	public void workPath(){ System.out.println(  _File.WOKE_PATH);}
	@Test    
	public void getClassPath(){
		System.out.println( _File.joinLength("E:","workSpace") );
	}
	@Test
	public void absolutePath2ClassName(){      
		System.out.println( _File.absolutePath2ClassNameFromSrc( "E:\\workSpace\\Util5\\src\\test\\StringTest.java" ));
	}
	@Test
	public void getUrl() throws IOException{
		System.out.println(
				Test.class.getResource("/").getPath());
		System.out.println(
				String.class.getResource("/").getPath());
		System.out.println(
				String.class.getResource("/").getPath());
		//获得类加载根路径  
		String lei = this.getClass().getResource("/").getPath();
		System.out.println(lei);//	/E:/workSpace/Util5/bin/
		//获得当前类所在的工程路径;
		String xdmulujn = this.getClass().getResource("").getPath();
		System.out.println(xdmulujn);//	/E:/workSpace/Util5/bin/test/
		//项目路径
		String xdmu = new File("").getCanonicalPath();
		System.out.println(xdmu);//	E:\workSpace\Util5
		//项目路径
		URL xml = this.getClass().getClassLoader().getResource("");
		System.out.println(xml);//	file:/E:/workSpace/Util5/bin/
		//项目路径
		String user = System.getProperty("user.dir");//
		System.out.println(user);	//	E:\workSpace\Util5
		//获得所有的类路径,包括jar包路径
		String path = System.getProperty("java.class.path");
		System.out.println(path);
		//E:\workSpace\Util5\bin;F:\xvxm\32\eclipse\plugins\org.junit_4.12.0.v201504281640\junit.jar;F:\xvxm\32\eclipse\plugins\org.hamcrest.core_1.3.0.v201303031735.jar;F:\xvxm\32\eclipse\configuration\org.eclipse.osgi\413\0\.cp;F:\xvxm\32\eclipse\configuration\org.eclipse.osgi\412\0\.cp
	}
	@Test
	public void getJar() {
		File fi = new File("/file:/F:/xvxm/32/eclipse/plugins/org.junit_4.12.0.v201504281640/junit.jar!/LICENSE-junit.txt");
		System.out.println( fi.getAbsolutePath() );
		System.out.println(fi.exists());
		System.out.println(
				this.getClass().getResourceAsStream("/LICENSE-junit.txt")
				);
		System.out.println( this.getClass().getResource("/LICENSE-junit.txt").getPath() );
	}
}
