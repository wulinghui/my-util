package test;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.junit.Test;

import util.base._String;

public class StringTest {
	@Test
	public void sub(){
		System.out.println("aaaaa.ddd".length());
		System.out.println(
				//��õ�ֵ��--�±�Ϊ0    �ױ���,β��������
				"aaaaa.ddd".substring( "aaaaa".length() ,"aaaaa.ddd".length())
		);;
	}
	@Test
	public void index() {
		String s ="ot\\target\\classes\\springboot\\com\\wlh\\biz\\mdj.java";
		System.out.println( (int) '.' );
		System.out.println( s.indexOf('.') );
		System.out.println( s.indexOf(".") );
		System.out.println( s.indexOf(46) );
	}@Test
	public void replaceAll() {
		String str = "select  from userz\r\n" + 
				"		  	 from userz	 from userz  from userz  from userz\r\n" + 
				"		   from userz;";
		System.out.println(str);
		System.out.println( str.replaceAll("\\s", " ") );
		System.out.println( (int) '\n');
	}
	@Test
	public void subStr() {
		String str = "13213(if:1fdsafd1)dsada";
		String s = _String.subString(str, 0, "(if:", ")");
		System.out.println(s);
	}
}
