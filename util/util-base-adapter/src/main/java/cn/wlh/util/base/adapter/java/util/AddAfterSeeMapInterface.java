package cn.wlh.util.base.adapter.java.util;

import java.util.Map;
/**
 * @author �����
 *��һ��Ӧ�ó�����
  ��add����֮����ȷ��������Ҫadd�ˣ���������ֻ�ǲ�ѯ�����޸��ˡ���ʱ���ö��������������
 * @param <E>
 */
public interface AddAfterSeeMapInterface<K, V> extends Map<K, V> {


	boolean isSave();
	
	/**������ӡ�
	 * save֮��ö෽�����ᱨnull
	 * 
	 */
	void save();
	
	/** ���K�������е�����.
	 * @param k
	 * @return
	 */
	int indexOfKey(K k);
	
	/** �޸�ֵ  , save֮����ʹ��put 
	 * @param k
	 * @param v
	 * @return ԭ����ֵ
	 */
	V update(K k , V v);
}
