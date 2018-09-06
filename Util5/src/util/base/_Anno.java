package util.base;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class _Anno {
	/**����anno���е�ֵ
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException */
	public static  Map<String,Object> forAnno(Annotation  anno) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		//����,ѭ���������������,���ǲ��ǰ��ն����˳��,Ŀǰ��֪������˳����,����Ĭ���������.
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		for (Method method : anno.getClass().getDeclaredMethods()) {
			String meName = method.getName();
			if( !"equals".equals(meName) &&  !"toString".equals(meName) && 
					!"hashCode".equals(meName) && !"annotationType".equals(meName) ){
				Object a = null;
				a = method.invoke(anno);
				map.put(meName, a);
			}
		}
		return map;
	}
	public static Map<String,Annotation> annos2Map(Annotation [] annos){
		Map<String,Annotation> map = new LinkedHashMap<String, Annotation>();
		for (Annotation anno : annos ) {
			map.put(anno.getClass().getSimpleName(), anno);
		}
		return map;
	}
}
