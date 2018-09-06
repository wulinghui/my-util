package cn.wlh.util.base.adapter.bean.ioc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cn.wlh.util.base._Serializt;
import cn.wlh.util.base.adapter.java.util.JavaUtilFactory;
import cn.wlh.util.base.adapter.spring.core.bean.IOCOfSpringBean;

/**
 * @author ����� ����֮ǰ��ʹ�ã�IOC��������ͳһʹ��,������Ч�����⡣ ͬʱϵͳ����Ĳ���ģʽ��ʵ�ʾ��������Ҳ��ͦ�ٵġ�
 * 
 *         ����:new���ǵ�����ָ���Ĺ����� �ӿڵ�һʵ���໹����Spring @Auto ֻ�������³����в�ʹ��:
 *         1.�༶��İ汾������ @see
 *         cn.wlh.util.base.adapter.bean.ioc.IOC1.getLatestBean(Class)
 *         2.������Ҫ�Լ����new����ķ����� (@see
 *         cn.wlh.util.base.adapter.bean.ioc.AbstractFactory1.ClientOfIOC.main(String[]))
 * 
 */
public abstract class IOC1 {
	IOC1() {
	}

	// static Map<String,IFactory> allFactory = new HashMap<String,IFactory>();
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// 2.������Ҫ�Լ����new����ķ�����
	// allFactory ������Ҫ��¶��ʹ����.����ķǷ�ʹ���Ե�С��
	// �����Ҫ�޸Ľ���ʹ�÷���
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	static Map<String, AbstractFactory1> allFactory = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_FIELD);

	/**
	 * ע�����й���..
	 * 
	 * @param factory
	 * @param id
	 */
	public static <T extends AbstractFactory1> void register(T factory) {
		allFactory.put(factory.getClass().getName(), factory);
	}

	/**
	 * ���Bean
	 * 
	 * @param factory
	 * @param id
	 * @return
	 */
	public static <T extends AbstractFactory1<K>, K> K getBean(Class<T> factory, String id) {
		return (K) getAbstractFactory(factory).getBean(id);
	}

	/**ͨ�������������ʵ��.
	 * @param factory
	 * @param fieldName
	 * @return
	 */
	public static <T extends AbstractFactory1<K>, K> K getBeanByFactoryField(Class<T> factory, String fieldName) {
		AbstractFactory1 iFactory = getAbstractFactory(factory);
		return (K) getBeanByFactoryField(iFactory, fieldName);
	}
	/**ͨ�������������ʵ��.
	 * @param factory
	 * @param fieldName
	 * @return
	 */
	public static <K> K getBeanByFactoryField(AbstractFactory1<K> iFactory, String fieldName) {
		String value = iFactory.getValue(fieldName);
		return (K) iFactory.getBean(value);
	}

	/**��ó��󹤳�.
	 * @param factory
	 * @return
	 */
	public static <T extends AbstractFactory1> AbstractFactory1 getAbstractFactory(Class<T> factory) {
		return allFactory.get(factory.getName());
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// 1.�༶��İ汾������
	// latestBeanOfSingleMap ������Ҫ��¶��ʹ����.����ķǷ�ʹ���Ե�С��
	// �����Ҫ�޸Ľ���ʹ�÷���
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	static Map<Class, Object> latestBeanOfSingleMap = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_FIELD);

	/**
	 * ֻ��õ���...
	 * 
	 * @param superClass
	 * @return
	 */
	public static <T> T getLatestBeanOfSingle(Class<T> superClass) {
		// �ȴӻ�����ȡ����..û���ٴ�springBeanȡ
		Object object = latestBeanOfSingleMap.get(superClass);
		if (object == null) {
			object = getLatestBean(superClass);
			latestBeanOfSingleMap.put(superClass, object);
		}
		return (T) object;
	}

	/**
	 * ��������û�оͱ��� ���ʹ���������Ӧ�ñ����ͬһ��ʵ���Ķ�α���..
	 * 
	 * @param superClass
	 * @return ��һ���ǵ���.����ÿ�α�����ȡֵ
	 */
	public static <T> T getLatestBean(Class<T> superClass) {
		// �������ÿ�ζ����������������ԭ��:�п���������Ļ�new���µ�ʵ��(spring��һ���ǵ���)
		Map<String, T> beansOfType = 
				//				null; 
				IOCOfSpringBean.getBeansOfType(superClass);// TODO ��֪�������ܲ���ʵ��������.
		Iterator<Entry<String, T>> iterator = beansOfType.entrySet().iterator();
		// ��������϶���..��Ȼ�ͱ����..
		Entry<String, T> temp = iterator.next();
		String tKey = temp.getKey();
		T tValue = temp.getValue();
		String nextKey = null;
		while (iterator.hasNext()) {
			temp = iterator.next();
			nextKey = temp.getKey();
			// ���ڵ�key��֮ǰ��key��..���滻,�����ϴ�.
			if (nextKey.compareTo(tKey) > 0) {
				tKey = nextKey;
				tValue = temp.getValue();
			}
		}
		// ��������..
		return (T) tValue;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// �������ķǷ�ʹ������..��ȫ�Ե�ʹ��...
	// ���ⲻ�����ĵ���ģʽ,�ѷ�����Ϊʵ������
	// ����ʹ���߾ͷ�����.
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	static IOC1 ioc = new IOC1() {
//	}; // �ҿ���������������ķǷ�ʹ������

	/** ����new����Ĳ���.. */
	public void updateNewIdea(String factoryClassName, String key1, String key2) {
		allFactory.get(factoryClassName).falgInterchange(key1, key2);
	}

	/** ���ڰ汾����.. */
	public Object put(Class key, Object value) {
		return latestBeanOfSingleMap.put(key, value);
	}

	public Set<Entry<Class, Object>> entrySet() {
		return latestBeanOfSingleMap.entrySet();
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// �ø�����.�޸ĵ���Ϣѽ..��������ô�޸�?
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static Map<String, AbstractFactory1> copyAllFactory() {
		
		
		try {
			return (Map<String, AbstractFactory1>) _Serializt.clone(  allFactory);
		} catch (ClassNotFoundException | IOException e) {
			Map newMap = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_FIELD);
			newMap.putAll(allFactory);
			return newMap;
		}
	}

	public static Map<Class, Object> copyLatestBeanOfSingleMap() {
		try {
			return (Map<Class, Object>) _Serializt.clone(  latestBeanOfSingleMap);
		} catch (ClassNotFoundException | IOException e) {
			Map newMap = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_FIELD);
			newMap.putAll(latestBeanOfSingleMap);
			return newMap;
		}
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//1.�����ϵͳ��ʱ��--ϵͳ��Ϣͬ��--���ݿ⣬��
//2.����ϵͳ������ʱ�� -- �޸ĵļ�¼�־û�
	//  ˼·:���л��뷴���л�,���Ǿ�����ôͬ��,��ô�洢�Ͳ�������������ĵ�����.
	//  TODO �ٶ�  ��̬���Կ������л���?
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	static IOC1 ioc = new IOC1() {
		//�������IOC�಻�У��Ͳ�̰��һ������һ�����Ե�ͬ��..����.
		public  void setAllFactory(Map<String, AbstractFactory1> allFactory) {
			IOC1.allFactory = allFactory;
		}
		public  void setLatestBeanOfSingleMap(Map<Class, Object> latestBeanOfSingleMap) {
			IOC1.latestBeanOfSingleMap = latestBeanOfSingleMap;
		}
	}; // �ҿ���������������ķǷ�ʹ������
}
