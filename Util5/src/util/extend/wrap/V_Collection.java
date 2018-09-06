package util.extend.wrap;

import java.util.Collection;
import java.util.HashMap;

/** Value Of Collection
 * @author wlh
 * Map ������  Collection
 * @param <K>
 * @param <V>
 */
@SuppressWarnings("serial")
public abstract class V_Collection<K,V> extends HashMap<K, Collection<V>> {
	protected abstract Collection<V> newCollection();
	
	/** ������������put,����
	 * @param k
	 * @param v
	 * @return this
	 */
	public V_Collection<K,V> putColle(K k , V v){
		Collection<V> value = get(k);
		if(value == null){
			value =  newCollection();
		}
		value.add(v);
		return this;
	}
	
}
