package test;

import java.util.ArrayList;

public class InteriorClass {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
//		System.out.println( Class.forName("test.InteriorClass$1").newInstance() );
//		System.out.println( Class.forName("util.clas.Single$1").newInstance() );
//		System.out.println( get(a.getClass()) );
//		ArrayList a = new ArrayList() {};//测试,不会报错...也就是说.匿名内部类,也是客观存在的.只不过名字不知道而已
		class vvv{}
		System.out.println(vvv.class.getName());
	}
}
