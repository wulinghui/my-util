package test;

import org.junit.Test;

import util.base._Class;

public class ClassTest {
	@Test
	public void getObj(){
		System.out.println(Object.class.getSuperclass());//null
	}
	@Test
	public void getInter(){
		System.out.println( Cloneable.class.getInterfaces() );
		System.out.println( Cloneable.class.getSuperclass() );
	}
	@Test
	public void DI() throws ClassNotFoundException{
		System.out.println( new _Class.DependencyLookUp( ClassTest.class ).getSub(Object.class) );
	}
	@Test
	public void packa(){
		for (Package pa : Package.getPackages()) {
			System.out.println(pa );
			System.out.println( pa.getImplementationTitle() );
			System.out.println( pa.getImplementationVendor() );
			System.out.println( pa.getImplementationVersion() );
			System.out.println( pa.getName() );
			System.out.println( pa.getSpecificationTitle() );
			System.out.println( pa.getSpecificationVendor() );
			System.out.println( pa.getSpecificationVersion()  );  
		}
		
//		Package pa = Package.getPackage("test");
		
		
	}
	public  void print(Object ... objs){
		for (Object object : objs) {
			System.out.println( object.toString() );
		}
	}
}
