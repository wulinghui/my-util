package util.base;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import util.base._Method.Alias;
import util.base.interfaces._Filter;

public class _Field {
	public static Field getField(Class<?> tar,boolean declared ,String name ) throws SecurityException, NoSuchFieldException{
		Field f = declared ? tar.getDeclaredField(name) : tar.getField(name);
		f.setAccessible(true);
		return f;
	}
	public static Field getAllField(Class<?> tar,boolean declared ,String name ) throws SecurityException, NoSuchFieldException{
		Field fie = getField(tar, true, name);
		return fie == null ? getField(tar,  false ,name) : fie;
	}
	/** declared true-- ˽��*/
	public static Field[] getFields(Class<?> tar,boolean declared  ) throws SecurityException, NoSuchFieldException{
		return declared ? tar.getDeclaredFields() : tar.getFields();
	}
	public static void getFields(Map<String,Field> map,boolean declared ,Class<?> tar,_Filter<Field> fi ) throws SecurityException, NoSuchFieldException{
		//�������
		Field[] f = getFields(tar, declared);
		//ȡ��У��
		Field.setAccessible(f, true);
		//����f
		for (Field field : f) {
			if(fi.accept(field)){
			//���Alias,Alias����
				Alias alias = field.getAnnotation(Alias.class);
				if( alias == null){
					map.put(field.getName(), field);
				}else{
					map.put(alias.value(), field );
				}
			}
		}
	}
	/**��þ�̬����ֵ������map��*/
	public static void getStaticFieldValues(Map<String,Object> map,boolean declared ,Class<?> tar,_Filter<Field> fi ) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		//�������
		Field[] f = getFields(tar, declared);
		//ȡ��У��
		Field.setAccessible(f, true);
		//����f
		for (Field field : f) {
			if(fi.accept(field)){
				//���Alias,Alias����
					Alias alias = field.getAnnotation(Alias.class);
					if( alias == null){
						map.put(field.getName(), field.get(null) );
					}else{
						map.put(alias.value(), field.get(null) );
					}
				}
		}
	}
	public static void getAllFields(Map<String,Field> map,Class<?> tar,_Filter<Field> fi) throws SecurityException, NoSuchFieldException{ getFields(map, false, tar, fi);getFields(map, true, tar, fi); }
	public static Map<String,Field> getAllFields(Class<?> tar) throws SecurityException, NoSuchFieldException{
		Map<String,Field> map = new LinkedHashMap<String, Field>();
		getAllFields(map,tar,_Filter.NO_FILTER);
		return map;
	}
}
