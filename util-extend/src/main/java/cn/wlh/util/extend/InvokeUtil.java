package cn.wlh.util.extend;

import java.lang.reflect.InvocationTargetException;

import cn.wlh.util.base._Class;

public abstract class InvokeUtil{
	Class<?> tar;
	public InvokeUtil(Class<?> cla) {
		this.tar = cla;
	}
	public InvokeUtil(Object obj) {
		this.tar = obj.getClass();
	}
	public InvokeUtil(String className) throws ClassNotFoundException {
		this.tar = Class.forName(className);
	}
	@SuppressWarnings("unchecked")
	public <T> T superMethod(Object target,String name,Object...objects ) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//ͨ��getClass���parameterTypes
		Class<?> [] parameterTypes = _Class.obj2Class(objects);
		//��ù�������,������
//		return (T) (declared ? tar.getDeclaredMethod(name, parameterTypes).invoke(target, objects) 
//				: tar.getMethod(name, parameterTypes).invoke(target, objects) );
		return (T) tar.getMethod(name, parameterTypes).invoke(target, objects);
	}
	@SuppressWarnings("unchecked")
	public <T> T protectedMethod(Object target,String name,Object...objects ) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class<?> [] parameterTypes = _Class.obj2Class(objects);
		//�ܱ����ķ���
		return (T) tar.getDeclaredMethod(name, parameterTypes).invoke(target, objects);
	}
	@SuppressWarnings("unchecked")
	public <T> T staticMethod(String name,boolean declared,Object...objects ) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class<?> [] parameterTypes = _Class.obj2Class(objects);
		//�ܱ����ķ���,�Ҿ�̬.
		return (T) tar.getDeclaredMethod(name, parameterTypes).invoke(null, objects);
	}
	@SuppressWarnings("unchecked")
	protected  <T> T invoke(Object target,Class<?> tar,String name,boolean declared,Object...objects ) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//ͨ��getClass���parameterTypes
		Class<?> [] parameterTypes = _Class.obj2Class(objects);
		//��ù�������,������
		return (T) (declared ? tar.getDeclaredMethod(name, parameterTypes).invoke(target, objects) 
				: tar.getMethod(name, parameterTypes).invoke(target, objects) );
	}
	
}
