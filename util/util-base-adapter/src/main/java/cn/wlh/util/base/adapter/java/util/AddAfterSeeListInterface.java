package cn.wlh.util.base.adapter.java.util;

import java.util.List;
/**
 * @author �����
 *��һ��Ӧ�ó�����
  ��add����֮����ȷ��������Ҫadd�ˣ���������ֻ�ǲ�ѯ�����޸��ˡ���ʱ���ö��������������
 * @param <E>
 */
public interface AddAfterSeeListInterface<E> extends List<E> {

	boolean isSave();
	
	/**������ӡ�
	 * save֮��ö෽�����ᱨnull
	 * 
	 */
	void save();
	
}