package cn.wlh.util.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public abstract class JavaUtilFactory {
	JavaUtilFactory(){}
	/** �������ڷ���������ʹ�ò�ѯ���޸� */  // ����uuid��������ֱ��д-1 , 1  , 0 ��Ӳ���롣
	public static final int SELECT_OF_METHOD = UUID.randomUUID().variant();
	/** �����������Լ�������ʹ�ò�ѯ���޸� */
	public static final int SELECT_OF_FIELD = UUID.randomUUID().variant();
	/** �������ڷ���������ʹ����ɾ */
	public static final int INSERT_OF_METHOD = UUID.randomUUID().variant();
	/** �����������Լ�������ʹ����ɾ */
	public static final int INSERT_OF_FIELD = UUID.randomUUID().variant();
	/*
	 * ���ϵ�flagĬ�� 
	 */
	public static List newList(int flag) {
		return new ArrayList();
	}
	public static Set newSet(int flag) {
		return new HashSet();
	}
	public static Map newMap(int flag) {
		return new HashMap<>();
	}
	
}
