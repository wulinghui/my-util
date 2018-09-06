package util.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import util.base._Class.PackageAllClass;
import util.base.interfaces._Filter;


public abstract class _Method {
	/**
	 * @param tar
	 * @param name
	 * @param pars
	 * @param declared true -- �Լ������з���   	false -- ��������
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static Method getMethod(Class<?> tar , String name ,Class<?>[] pars,boolean declared) throws SecurityException, NoSuchMethodException{
		Method e = declared ? tar.getDeclaredMethod(name,pars) : tar.getMethod(name,pars);
		//ȡ��Ȩ��У��
		e.setAccessible(true);
		return e;
	}
	/** @see getMethod �Ļ�÷�����֪��--�û���Ҫָ����ʶ
	 * @return ����Ĺ������������Լ���˽�з���
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	public static Method getAllMethod(Class<?> tar , String name ,Class<?>[] pars) throws SecurityException, NoSuchMethodException{
		Method method = getMethod(tar, name, pars, true);
		return method == null ? getMethod(tar, name, pars, false) : method;
	}
	/**
	 * ��method�ŵ�map����
	 * @param map
	 * @param declared true -- �Լ������з���   	false -- ��������
	 * @param tar
	 * @param fi
	 */
	public static void getMethods(Map<String,Method> map, boolean declared ,Class<?> tar,MethodFilter fi){
		//���method
		Method[] mes = declared ? tar.getDeclaredMethods() : tar.getMethods();
		//ȡ��Ȩ��У��
		Method.setAccessible(mes, true);
		//����method
		for (Method method : mes) {
			//���Alias,Alias����
			if( fi.accept(method)){
				Alias alias = method.getAnnotation(Alias.class);
				if( alias == null){
					map.put(method.getName(), method);
				}else{
					map.put(alias.value(), method );
				}
			}
		}
	}
	/**�����е�method�ŵ�map����*/
	public static void getAllMethods(Map<String,Method> map,Class<?> tar,MethodFilter fi){ getMethods(map, false, tar, fi);getMethods(map, true, tar, fi); }
	/*** ���һ���������еķ���..
	 * @throws ClassNotFoundException */
	public static void getMethodsOfPackage(Map<Class<?>,Map<String,Method>> map,Class<?> packa,boolean declared ,MethodFilter fi) throws ClassNotFoundException{
		PackageAllClass pac = new PackageAllClass(packa);
		for (Class<?> tar : pac.ALL_CLASS.values()) {
			//map00000--�洢һ����ķ���
			Map<String,Method> map00000 = new HashMap<String, Method>();
			getMethods(map00000, declared, tar, fi);
			//����map��..
			map.put(  tar , map00000);
		}
	}
	/***�����е�method�ŵ�map����*/
	public static Map<String,Method> getFullMethods(Class<?> tar){
		Map<String,Method> map = new HashMap<String, Method>();
		getAllMethods(map, tar, (MethodFilter) MethodFilter.NO_FILTER);
		return map;
	}
	
	public static interface MethodFilter extends _Filter<Method>{
		/** ����Object �ķ���  	*/
		MethodFilter OBJECT = new MethodFilter(){
			public boolean accept(Method t) {
				String name = t.getName();
				if( 	"toString".equals(name) ||  "getClass".equals(name) ||  
						"equals".equals(name) ||  "hashCode".equals(name) ||   
						"notify".equals(name) ||  "notifyAll".equals(name) ||  
						"wait".equals(name)   ) return false;
				return true;
			}
		};
		/** ���Biz�ķ���  ��txn��ͷ*/
		MethodFilter BIZ = new MethodFilter(){
			@Override
			public boolean accept(Method t) {
				String name = t.getName();
				return name!=null && name.startsWith("txn");
			}
			
		};
		
	}
	
	/**
	 * @author wlh 
	 * ����
	 */
	@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE,ElementType.CONSTRUCTOR,
		ElementType.FIELD,ElementType.LOCAL_VARIABLE,ElementType.METHOD,ElementType.PACKAGE,
		ElementType.PARAMETER})
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface Alias{String value();}
}
