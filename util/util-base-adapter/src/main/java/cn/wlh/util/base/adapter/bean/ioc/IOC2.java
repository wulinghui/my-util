package cn.wlh.util.base.adapter.bean.ioc;

import java.util.Map;

import cn.wlh.util.base.adapter.java.util.JavaUtilFactory;

/**
 * @author ����� ���ݽ�����Ŀ�����ο���..�õ����¸���.. ǰ��:һ��ʼ3�֮꣬��6�꣬����ʮ�ꡣÿ��cvs�ύ��������ͬ��ԭ�����Ѿ��뿪��
 *         ���ڶ��͵�һ���ߵ��뷨��Щ���룬��ÿ�λ��ö԰汾�����뷨������ܽ��������󲿷ֵ��߼������Է�װ��--�����ġ�
 *         ���ǵ�һ����û�������������ڶ����߼����Լ����߼�����һ����·��������������֪����ȫ��������һ��cms�ṩ���û����á�
 *         ����ͻ�����������������������뷨: ��ͳ��IOCֻ�ܿ��ƶ���,���ܿ��ƻ������͡�
 * ������ܵ�ʵ��:��ʵ������ˡ�������ݿ���ȡ���뼯���������ļ���ȡ���á�ֻ������Щ�������û����ܲ����ģ�ͬʱ��һ��ʹ�õ�ʱ����Ҫ����ά�����ñ�
 * �����������ʹ��һ��Ĭ�ϵ�ֵ������������̫��ʱ��������ϵͳ�������ٶȡ�Ҳ����˵����������ڼ����ʹ�ã�����Ĭ��ֵ���Ѿ����ڵ�ʵ�������Ӽ�--��û�д������в��ҵĵ�����
 * ���������������..
 */
public abstract class IOC2 extends IOC1 {
	/**���ڻ���Map*/
	Map<String,Object> cacheMap = JavaUtilFactory.newMap(JavaUtilFactory.INSERT_OF_FIELD);
	/**��ָ���Ĵ洢������ݣ����û�оͷ���Ĭ��ֵ--Ҳ����ϵͳ����ʱ������..
	 * @param id ��Ӧ���ݿ��������
	 * @param decript �����������ʾ�û��ġ�
	 * @param defaultValue
	 *            ���û�оͷ���Ĭ��ֵ
	 * @return �����ڲ���д�����ʵ��,���Ǹ����ݿ�(���������洢��ʽ)���ھ����ʵ������ڱ�ϵͳ��������ֻ��Ҫ��������Ϣ����һ��������ʵ����(���Կ���ͨ�����л�,������о�ֻ��ͨ������ʵ����).���û�оͷ���Ĭ��ֵ
	 */
	public <T> T getSimapleBean(String id, String decript, T defaultValue) {
		String key = getKeyNameRuleUserMap(id, decript);
		//�ȴӻ�����ȡ..
		Object t = cacheMap.get(key);
		if( t == null ) {
			t = getSimapleBean(id, decript);
			//���ݿ�û��--��ʼ��
			if( t == null ) {
				t = defaultValue;
			}
			//
			cacheMap.put(key, t);
			//
			storeSimapleBean(id, decript, defaultValue);
		}else {
			return (T) t;
		}
//		T t = (T) getSimapleBean(id, decript);
		if (t == null)
			return defaultValue;
		
		return (T) t;
	}
	/**�ṩ����key����������
	 * @param id
	 * @param decript
	 * @return
	 */
	protected  String getKeyNameRuleUserMap(String id, String decript) {
		return id+decript;
	}
//	protected abstract Object getSimapleBean(String id, String decript);
	//ʵ��ʾ��
	/**��ָ���Ĵ洢�������
	 * @param id
	 * @param decript
	 * @return
	 */
	protected  Object getSimapleBean(String id, String decript) {
		// id ..
		Integer valueOf = Integer.valueOf(id);
		switch( valueOf ) {
			//�������
			case 0:
				return 3;
		}
		//decript ������Ψһ��.(һ��)
		switch( decript ) {
		//�������
		case "�������":
			return 10;
		}
		return null;
	}
	/**
	 * �洢Bean
	 */
	protected abstract void storeSimapleBean(String id, String decript,Object defaultValue);
}
