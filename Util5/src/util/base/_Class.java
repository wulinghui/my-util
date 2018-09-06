package util.base;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import util.base._File.FileFilter;
import util.base._File.HandlerFile;




public abstract class _Class {
	private static Map<String,Class<?> > classMap = new HashMap<String,Class<?> >();
	public static Class<?> [] obj2Class(Object...objects){
		int len = objects.length;
		Class<?> [] parameterTypes = new Class<?> [ len ];
		for (int i = 0; i < len ; i++) {
			parameterTypes[i] = objects[i].getClass();
		}
		return parameterTypes;
	}
	public static <T> T invoker( Class<?> cla ,String name  ) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException {
//		Class<?> cla = Class.forName( className );
		return (T) cla.getMethod(name).invoke( cla.newInstance() );
	}
	public static Class<?> forName(String className){
		Class<?> cla = classMap.get(className);
		if( cla == null ) {
			try {
				cla = Class.forName(className);
			} catch (ClassNotFoundException e) {
				throw new NullPointerException();
			}
			classMap.put(className, cla);
		}
		return cla;
	}
	/** �������|�ֲ�-�ڲ��������*/
	public static String getInterName(Class<?> cla,String index) { return cla.getClass().getName() + "$" + index;}
	/** ͨ������new����*/
	public  static <T> T newObj(Class<T> cla) throws InstantiationException, IllegalAccessException{ return cla.newInstance(); }
	/**����ȫ�޶���ת���ɲ�ͬϵͳ��·���ָ��� */
	public static String cla2Path(String classFullName){return _File.toSeparator(classFullName);}
	/**�Ѳ�ͬϵͳ��·���ָ���ת������ȫ�޶���*/
	public static String path2Cla(String pathSeparater){return _File.toPoint(pathSeparater);}
	/** ���и��� */
	public static Map<String,Class<?>> getAllSuper(Class<?> cla){
		if(cla == null) throw new NullPointerException();
		Map<String,Class<?>> map = new LinkedHashMap<String,Class<?>>();
		do{
			cla = cla.getSuperclass();
		 	if( cla == null ) return map;
		 	map.put(cla.getName(), cla);
		}while(true);
	}
	public static Class<?> lookClassBySimple(Collection<Class<?>> set,String simple){
		for (Class<?> class1 : set) {
			if( class1.getSimpleName().equals(simple) ) return class1;
		}
		return null;
	}
	/** ���еĽӿ�*/
	public static Map<String,Class<?>> getAllInter(Class<?> cla){
		if( cla == null) throw new NullPointerException();
		//��õ�ǰ����ֱ��ʵ�ֵĽӿ�
		Map<String,Class<?>> map = interfaces2Map(cla);
		//������и���,�������еĸ���
		for (Class<?> iterable_element : getAllSuper(cla).values() ) {
			//����map��
			map.putAll( interfaces2Map(iterable_element) ) ;
		}
		//	������нӿڵĸ���.������map��
		Map<String,Class<?>> map000 =   new LinkedHashMap<String,Class<?>>();
		map000.putAll(map);
		for (Class<?> iterable_element : map000.values()) {
			map.putAll( getAllSuper(iterable_element) ) ;
		}
		return map;
	}
	/** ������еĸ�������еĽӿ�*/
	public static Map<String,Class<?>> getAllSupAndInter(Class<?> cla){
		Map<String,Class<?>> map = getAllSuper(cla);
		map.putAll( getAllInter(cla)  );
		return map;
	}
	/**��ǰ����ֱ��ʵ�ֵĽӿ�*/
	protected static Map<String,Class<?>> interfaces2Map(Class<?> cla){
		if( cla == null) throw new NullPointerException();
		Map<String,Class<?>> map = new LinkedHashMap<String,Class<?>>();
		for (Class<?> iterable_element : cla.getInterfaces()) {
			map.put( iterable_element.getName(), iterable_element);
		}
		return map;
	}
	/**DI�������ҵĻ���,������и���ͽӿ�.*/
	public static class DependencyLookUp{
		public Map< Class<?> , Set< Class<?> > > map = new HashMap< Class<?> , Set< Class<?> > >();
		public DependencyLookUp(Class<?> packa) throws ClassNotFoundException{
			PackageSupAndInter ps = new PackageSupAndInter(packa);
			/* ���� -- 
			 * 		��key ����value 
			 * 		��value ����key
			 */
			//������
			for (Class<?> key : ps.map.keySet() ) {
				//�������еĸ�������еĽӿ�
				for (Class<?> supInter : ps.map.get(key).values() ) {
					Set< Class<?> > set = map.get(supInter);//����Ĵ洢
//					if(set ==null) set = new HashSet<Class<?>>() ;
//					set.add( key );//��ӵ�set
//					map.put(supInter, set);
					if( set == null){
						 set = new HashSet<Class<?>>() ;
						 map.put(supInter, set);//��һ��put�Ļ���..
					}
					set.add( key );//��ӵ�set
				}
			}
		}
		/**�������*/
		public Set< Class<?> > getSub(Class<?> key){return map.get(key);}
	}
	/** Key - �Ѱ����������е�java�ļ�ת������
	 *  Value - Key���еĸ�������еĽӿ�
	 * */
	public static class PackageSupAndInter{
		public final Map<Class<?> , Map<String,Class<?>> > map = new HashMap<Class<?>, Map<String,Class<?>>>();
		public PackageSupAndInter(Class<?> packa) throws ClassNotFoundException{
			Map<String,Class<?>> clas = new PackageAllClass(packa).ALL_CLASS;
			// ���������ĸ���ͽӿ�
			for (Class<?> iterable_element : clas.values()) {
				map.put( iterable_element,
				getAllSupAndInter(iterable_element));
			}
		}
	}
	/** �Ѱ����������е�java�ļ�ת������	*/
	public static class PackageAllClass{
		public Map<String,Class<?>> ALL_CLASS = new HashMap<String,Class<?>>();
		public PackageAllClass(Class<?> cla) throws ClassNotFoundException  {
			ToClass a = new ToClass(cla);
			// TODO sysout
			_File.forFile(new File( _File.package2Path( cla ) ), FileFilter.JAVA , a );
			// ת��
			ALL_CLASS.putAll( a.ALL_CLASS );
		}
	}
	/** ���ļ���·����2����*/
	protected static class ToClass implements HandlerFile<ClassNotFoundException>{
		public  Map<String,Class<?>> ALL_CLASS = new HashMap<String,Class<?>>();
		Class<?> cla;
		public ToClass(Class<?> cla) {
			this.cla = cla;
		}
		public void handler(File file) throws ClassNotFoundException{
			String className = _File.absolutePath2ClassName( file.getAbsolutePath() , this.cla);
			//�洢
//			System.out.println("ToClass+"+className);  
			ALL_CLASS.put(className, Class.forName(className) );
		}
	}
}