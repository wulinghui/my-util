package util.base.jdk7;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import util.base._Class;

public class _InvokeRef {
	static java.lang.invoke.MethodHandles.Lookup look = MethodHandles.lookup();
	
	
	public static java.lang.invoke.MethodHandles.Lookup getLook() {
		return look;
	}
	/** �ж���ʱ������bind , ���� find...
	 * bind ��ͨ�������÷���..
	 * find ��Clalss��÷���..
	 * */
	public static <T> T invokeOfBind(Object receiver, String name, MethodType type , Object... args) throws Throwable {
		MethodHandle method  = look.bind(receiver, name, type);
		return (T) method.invoke(args);
	}
	public static <T> T invokeOfBind( Object receiver, String name, Class<T> rtype, Object... args ) throws Throwable {
		MethodType type = null ; 
		int i = args.length;
		if(  i == 0 ) {
			type = MethodType.methodType(rtype);
		}else {
			Class<?> ptypes [] = _Class.obj2Class(args);
			type = MethodType.methodType(rtype,ptypes);
		}
		return invokeOfBind(receiver, name, type, args);
	}
	public static void main(String[] args) throws Throwable {
		//��һ������׶μ��
		MethodHandle method  =	//���������л�.. 
				MethodHandles.lookup()//���������л�..
//				.bind("", "toString"
//						,MethodType.methodType(String.class)//�������л�..
//						)//ͨ�������÷���..
				.findVirtual( String.class, "toString"
									,MethodType.methodType(String.class)//�������л�..
									)// ͨ�����÷���..
				;
		System.out.println( method.toString() );
//		String sss = (String) method.invoke(); //bind - ���ö�����
		String sss = (String) method.invoke("");// ��Method.invoke һ��..
//		method.
//		method.invoke("");//����,�������Ȩ��...��һ������..
		//�ڶ������н׶μ��
		//	java.lang.invoke.InvokeDynamic  Ȩ�޲�������..��ֻ��������һ����
		Object a  = new _InvokeRef();
		MethodHandle method1  = look.bind( a , "test"
				,MethodType.methodType(void.class , a.getClass() )
				);
		method1.invoke(null);
	}
	
	public void test(Object obj) { System.out.println("xxxxxx"); }
//	public class MethodHandle1 extends MethodHandle{
//
//		MethodHandle1(MethodType type) {
//			super(type, null);
//		}
//		
//	}
//
//	public class Method1 extends java.lang.reflect.Method{
//		
//	}
}
