package cn.wlh.util.base;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public  abstract class _Map {
	/**
	 * @param objs
	 * @param skip	���� , objs���������������.
	 * @return
	 */
	public static <K,V> Map<K,V> array2Map(Object[] objs ,int skip ){
		Map<K,V> map = new HashMap<K,V>();
		return array2Map(map,objs,skip);
	}
	/**@param skip	���� , objs���������������.*/
	@SuppressWarnings("unchecked")
	public static <K,V> Map<K,V> array2Map(Map<K,V> map,Object[] objs ,int skip ){
		if(map == null) throw new NullPointerException();
		int len = objs.length;
		//ͨ��skip������ѭ��...����ֻȡ 1 2��.
		for (int i = 0; i < len; i+= skip) {
			map.put( (K)objs[i], (V) objs[i+1] );
		}
		return map;
	}
	public static <K,V> Map<K,V> array2Map(Map<K,V> map, K[] keys ,V[]  values ){
		int l = values.length;
		for (int i = 0; i < l; i++) {
			map.put(keys[i], values[i]);
		}
		return map;
	}
	/** С�������Map
	 * @param map
	 * @return
	 */
	public static Object[] toArray(Map map) {
		
		Object [] params = map.values().toArray();
		return params;
	}
	/**��strings���������Ϊ˳��,���л������.
	 * @param map
	 * @param strings
	 * @return
	 */
	public static Object[] toArray(Map map,Object...strings ) {
		int length = strings.length;
		Object[] objs = new Object[length];
		for (int i = 0; i < length; i++) {
			Object key = strings[i];
			objs[i] = map.get(key);
		}
		return objs;
	}
	public static <E,K> Object[] toArray(Map<E,K> map, Collection<E> set) {
		int size = set.size() ,i = 0;
		Object [] objs = new Object[size];
		Iterator<E> iterator = set.iterator();
		for (; i < size ; i++) {
			objs[i] = map.get(iterator.next());
		}
	return objs;
}
}
