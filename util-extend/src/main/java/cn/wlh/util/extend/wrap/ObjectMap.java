package cn.wlh.util.extend.wrap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author �����
 * �����Map�������еĶ���
 * @param <K> ������
 * @param <V> ����ֵ
 * �����Map��ͨ�����鶨����ɵģ����Կռ��Ч����һ���̶��϶��ǿ��Խ��ܵ�--��ԭʼ������ȿ϶�����
 * ���ǲ��ܰ����������еĶ��󣬷���ǿ�������Ե��������ֲ��ˡ������Ӧ�ó������ڣ�
 * 1�� �������������ȫ���������Ľṹ��֮��Ӧ����������ֻ�����Ǹ�����Ĺ��̣�--Ҳ����˵������Խṹ��ȫ�и��ο��ı�׼���磺POJO���ṹ��ȫ��Ӧ��
 * 2: ���Ҷ�ĳһ��ҵ����ϤΪ��֮��Ŀ���չ�ԣ��ö���������һ�����ɣ�����ȫ���Կ������������ҿ�����ȡ����ɱ�ҵ��Ϊ�����õ�ҵ��
 */
public class ObjectMap<K, V> implements Map<K, V> {
	Object[] key;
	Object[] value;
	int size;
	public ObjectMap(int length) {
		init(length);
	}
	/**��ʼ������
	 * @param length
	 */
	protected void init(int length) {
		key = new Object[length];
		value = new Object[length];
	}
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0 ? true : false;
	}
	public boolean containsKey(Object key) {
		return getKeyIndex(key) == -1 ? false : true;
	}
	/**���key����Ӧ���±�
	 * @param key
	 * @return -1 û�в鵽
	 */
	protected int getKeyIndex(Object key) {
		Objects.requireNonNull(key);
		int len = this.key.length;
		for (int i = 0; i < len; i++) {
			if( key.equals( this.key[i]) ) return i;
		}
		return -1;
	}
	@Override
	public boolean containsValue(Object value) {
		int j = -1;
		int len = this.value.length;
		for (int i = 0; i < len; i++) {
			if( value.equals( this.value[i]) ) j=i;
		}
		return j == -1 ? false : true;
	}

	@Override
	public V get(Object key) {
		int keyIndex = getKeyIndex(key);
		return keyIndex == -1 ? null : (V) this.value[keyIndex];
	}

	@Override
	public V put(K key, V value) {
		int keyIndex = getKeyIndex(key);
		if( keyIndex == -1 ) {
			this.key[size] = key;
			this.value[size] = value;
			this.size++;
			return null;
		}else {
			V v = (V) this.value[keyIndex];
			this.key[keyIndex] = key;
			this.value[keyIndex] = value;
			return v;
		}
	}

	@Override
	public V remove(Object key) {
		int keyIndex = getKeyIndex(key);
		if( keyIndex == -1 ) {
			return null;
		}else {
			this.key[keyIndex] = null;
			this.size--;
			return (V) this.value[keyIndex];
		}
	}
	@Override
	public void clear() {
		init(key.length);
	}
	
	/**
	 * �ṩͨ��this.size����ʱ��ʵ�������±�.
	 * ������������Ҫԭ����put����û�ж������ֵ���Կ��ƣ��м�����ݿ���Ϊnull,���´�����Ƭ
	 * @param size �ǳ��Ȳ����±�
	 * @return  -1Ϊ�����size..
	 * �������·���������ʵ�֡�
	 */
	protected int getRealIndex(int size) {
		for (int i = 0; i < this.key.length; i++) {
			if( this.key[i] != null ) size--;
			if( size == 0 ) return i;
		}
		return -1;
	}
	/**
	 * 
	 * ��Ҫԭ����put����û�ж������ֵ���Կ��ƣ��м�����ݿ���Ϊnull
	 */
	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		if ( map.size() > this.key.length ) Objects.requireNonNull(null); 
//		Object key2 ;
//		Object value2 ; 
//		for (Entry<? extends K, ? extends V> object : map.entrySet()) {
//			 key2 = object.getKey();
//			 value2 = object.getValue();
//		}
		
	}

	

	@Override
	public Set<K> keySet() {
		Set set = new HashSet();
		Object e;
		for (int i = 0; i < this.size; i++) {
			int realIndex = getRealIndex(i);
			set.add( this.key [ realIndex ]);
		}
		return set;
	}

	@Override
	public Collection<V> values() {
		ArrayList arrayList = new ArrayList();
		Object e;
		for (int i = 0; i < this.size; i++) {
			int realIndex = getRealIndex(i);
			arrayList.add( this.value [ realIndex ]);
		}
		return arrayList;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		int keyIndex;
		MyEntry<Object,Object> entry;
		Set set = new HashSet();
		for (int i = 0; i < this.size; i++) {
			keyIndex = this.getKeyIndex(i);
			entry = new MyEntry<Object,Object>(key[keyIndex], value[keyIndex]);
			set.add(entry);
		}
		return set;
	}
	public class MyEntry<K,V> implements Entry<K,V>{
		K key;
		V value;
		public MyEntry(K key, V value) {
			super();
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			V value1 = this.value;
			this.value = value;
			return value1;
		}
	}
}
