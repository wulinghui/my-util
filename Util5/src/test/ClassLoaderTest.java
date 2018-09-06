package test;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.tools.JavaFileObject.Kind;

import org.junit.Test;

import util.base.HowswapCL;
import util.base._Class;
import util.base._ClassLoader;
import util.base._ClassLoader.Loader;
import util.base._File._Path;

public class ClassLoaderTest {
	@Test
	public void classLoad() {
//		_ClassLoader load = new _ClassLoader( _Path.getClassRoot(String.class).getPath() , Kind.CLASS  );
		
		_ClassLoader load = new _ClassLoader( "C:\\Users\\wlh\\eclipse-workspace\\cccc\\bin" , Kind.CLASS  );

		String str = "util.base.jdk7.ni.Class1";
		_ClassLoader.Loader l;
		while(true) {    
		try {
			l = load.new Loader( str );
			Class cla = l.loadClass( str );
			_Class.invoker( cla , "aaa");
			Thread.sleep(500);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
//		
//		Thread t;
//		t = new Thread(new Runnable() {
//			public void run() {
//				try {
//					while( true ) {
//						String str = "util.base.jdk7.ni.Class1";
//						_ClassLoader.Loader l = load.new Loader( str );
//						Class cla = l.loadClass( str );
//						_Class.invoker( cla , "aaa");
//						Thread.sleep(500);
//					}
//					
//
//				} catch (Exception ex) {
//					ex.printStackTrace();
//				}
//			}
//			
//		});
//		t.start();
	}
}
