package cn.wlh.util.extend.modle.factory;


/**
 * @author wlh
 * �Զ����Map,linkedMap���������ݵ����..
 * ֻ�༸��Object�ķ�����..
 * @param <K>
 * @param <V>
 */
public class Cache<K,V> {
	Link<K,V> first;//��һ��
	Link<K,V> last;//���һ��
	public Cache(){
		first = new Link<K,V>();
		last = first;
	}
	
	/**��putֻ�Ƿ������,��û�м�У��.�����putȥ��.
	 * @param key
	 * @param value
	 */
	public void put ( K key ,V value) {
		Link<K,V> link  = new Link<K,V>();
		link.key = key;
		link.value = value;
		//last�ϵǼ�
		last.next = link;//��һ�ξ��Ѿ��� first��������
		//�滻last
		last = link;
	}
	public V get(K key) {
		return first.find(key);
	}
	private class Link<K,V>{
		K key;
		V value;
		Link<K,V> next;
		public Link() {}
		public Link(K key, V value) {
			super();
			this.key = key;
			this.value = value;
		}
		public Link(Link<K,V> link) {
			this.next = link;
		}
		public V find(K key) {
			Link<K,V> l = this;
			do {
				if( key.equals(l.key) )
					return value;
				l = l.next;
			}while( l !=null );
			return null;//û���ҵ�..
		}
	}
}
