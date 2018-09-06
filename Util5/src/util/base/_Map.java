package util.base;

import java.util.HashMap;
import java.util.Map;

public  abstract class _Map {
	/**
	 * @param objs
	 * @param skip	步长 , objs最好是他的整数倍.
	 * @return
	 */
	public static <K,V> Map<K,V> array2Map(Object[] objs ,int skip ){
		Map<K,V> map = new HashMap<K,V>();
		return array2Map(map,objs,skip);
	}
	/**@param skip	步长 , objs最好是他的整数倍.*/
	@SuppressWarnings("unchecked")
	public static <K,V> Map<K,V> array2Map(Map<K,V> map,Object[] objs ,int skip ){
		if(map == null) throw new NullPointerException();
		int len = objs.length;
		//通过skip来决定循环...但是只取 1 2个.
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
}
