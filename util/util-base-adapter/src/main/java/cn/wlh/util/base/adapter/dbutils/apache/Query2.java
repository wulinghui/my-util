package cn.wlh.util.base.adapter.dbutils.apache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

/**
 * @author �����
 * �����и�����,��һ�� Map �����key ���������˶�ε�ʱ��ÿ�ζ�ȥ��������Ч����Щ����
 * 1.�ڻ��������ʱ����һ��set<key,index>;
 */
public class Query2 extends Query {

	public Query2(DataSource dataSource) {
		super(dataSource);
	}
	
//	public <E,V> Object[] toArray(Map<E,V> map,List<E> list) {
//		int size = list.size();
//		Map<String ,Integer [] > temp = new HashMap<>();
//		Set set;
//		for (int i = 0; i < size ; i++) {
//			// ������ӱ��.�����ǲ���ȡ��..���������� --����������ʹ�á�
	        // �ӱ��,�鷳�����ӽ�����ʹ���ߵ����ѡ�
//		}
//	}
	
}
