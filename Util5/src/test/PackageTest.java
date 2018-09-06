package test;

import org.junit.Test;

import util.base._File;

public class PackageTest {
	@Test
	public void getName() {
		System.out.println(  Package.getPackage( "test" ) );
		String path =  _File.package2Path( Package.getPackage("test").getClass() );
		System.out.println( path );
	}
}
