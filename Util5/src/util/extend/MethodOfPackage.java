package util.extend;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import util.base._Method;
import util.base._Method.MethodFilter;

/**
 * @author wlh
 * ���һ���������еķ���..�����Ӱ�
 * ��  �򵥵�����  �� ������  �洢
 */
public abstract class MethodOfPackage {
	MethodOfPackage(){}
	String packag;
	protected Map<Class<?>,Map<String,Method>> methods = new HashMap<Class<?>,Map<String,Method>>();
	/**
	 * @param packag --����
	 * @param declared   true -- �Լ������з���   	false -- ��������
	 */
	public MethodOfPackage(Class<?> packag,boolean declared , MethodFilter filter) throws ClassNotFoundException {
		_Method.getMethodsOfPackage(methods,packag,declared,filter);
		//TODO sysout
//		System.out.println( "MethodOfPackage="+methods);  
	}
	/** --- Ϊ�����㲻ͬ����Ҫ,д��abstract
	 * @param simple --�򵥵�����
	 * @param name   --������
	 * @param args null-ִ���޲εķ���.
	 * @return 
	 */
	public abstract Method getMethod(String simple,String name);

	public Object invoke(Object obj,String simple,String name,Object... args ) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if( args == null )
			return getMethod(simple, name).invoke(obj);
		return getMethod(simple, name).invoke(obj, args);
	}
	
	
	/** ͨ���򵥵���������� */
	public static class Simple extends MethodOfPackage{
		/** ͨ���򵥵���������� */
		public Simple(Class<?> packag, boolean declared, MethodFilter filter) throws ClassNotFoundException {
			super(packag, declared, filter);
		}
		@Override
		public Method getMethod(String simple, String name) {
			for (Entry<Class<?>, Map<String, Method>> iterable_element : methods.entrySet() ) {
				if ( iterable_element.getKey().getSimpleName().equals(simple) ) 
					return iterable_element.getValue().get(name);
			} 
			return null;
		}
		public Map<String,Method> getMethodMap(String simple){
			for (Entry<Class<?>, Map<String, Method>> iterable_element : methods.entrySet() ) {
				if ( iterable_element.getKey().getSimpleName().equals(simple) ) 
					return iterable_element.getValue();
			}
			return null;
		}
		public Entry<Class<?>, Map<String, Method>> getEntry(String simple){
			for (Entry<Class<?>, Map<String, Method>> iterable_element : methods.entrySet() ) {
				if ( iterable_element.getKey().getSimpleName().equals(simple) ) 
					return iterable_element;
			}
			return null;
		}
	}
	/** ͨ����ȫ�޶�������� */
	public static class FullName extends MethodOfPackage{
		/** ͨ����ȫ�޶�������� */
		public FullName(Class<?> packag, boolean declared, MethodFilter filter) throws ClassNotFoundException {
			super(packag, declared, filter);
		}
		public Method getMethod(String fullClassName, String name) {
//			System.out.println("FullName=="+fullClassName);
			try {   
//				System.out.println( "FullName=="+Class.forName(fullClassName));
				return methods.get( Class.forName(fullClassName)  ).get(name);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();  
				return null;
			}
		} 
		public Map<String,Method> getMethodMap(Class<?> clas){
			return super.methods.get(clas);
		}
	}
}
