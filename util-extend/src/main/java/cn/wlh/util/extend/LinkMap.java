package cn.wlh.util.extend;

/**
 * @author wlh
 * ����map���ݵ�����:��һ��,��������ֻ��һ��51�����Ǳ����ݵ�100�������˷ѵ�..
 * ����������õ��������Ⱦ���С,�������ݵ�Ҳ��С..
 * ����༶Map..����������˵��һ�����Լ�ڴ�..
 */
public class LinkMap<K,V> {
	K key;
	V value;
	LinkMap<K,V> next;
	//ֻ��get,û��set.
	public K getKey() {
		return key;
	}
	public V getValue() {
		return value;
	}
	public LinkMap<K, V> getNext() {
		return next;
	}
	public LinkMap() {
		super();
	}
	public LinkMap(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}
	public V find(K key) {
		if( key ==null ) return null;
		LinkMap<K,V> l = this;
		do {
			//�ж�
			if( key.equals( l.key ) )
				return l.value;
			//�´�ѭ��
			l = l.next;
		}while( l !=null );
		return null;//û���ҵ�..
	}
	
	public void put ( K key ,V value) {
		if( key ==null ) return;
		LinkMap<K,V> l = this;
		do {
			//�ж�
			if( 	key.equals ( l.key )	 )
				l.value = value;//�޸�.
//				return;//������..
			//�´�ѭ��
			l = l.next;
		}while( l !=null );
		// ��ʱ  l==null
		l = new LinkMap<K,V>(key,value);//����...
	}
	public void remove(K key) {
		if( key ==null ) return;
		LinkMap<K,V> forward = null;//����ǰһ��
		LinkMap<K,V> l = this;
		do {
			//�ж�
			if( 	key.equals ( l.key )	 ) {//�ҵ���
				if( forward != null ) {//��ǰ�߽��Ͼ�����
					forward.next = l.next;
				}
			}else {//ǰ��
				forward = l;
				l = l.next;
			}
			//�´�ѭ��
			l = l.next;
		}while( l !=null );
		// ��ʱ  l==null
		l = new LinkMap<K,V>(key,value);
	}
}
