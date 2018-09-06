package cn.wlh.util.base.adapter.bean.ioc;

import java.util.HashMap;
import java.util.Map;

/*
������Ŀ��ʱ��ͻȻ���Լ�����IOC�Ŀ�ܡ�
1.Ϊʲôspring��@Auto..ע��,û�а汾����(Ϊ�����㿪��ԭ��ϵͳ������һ���������һ���������ע�룬ϵͳ���Ĳ���ʹ��)�������ע�����ʽ�޷�����һ���ӿ�ʵ����İ汾����@Requred(name=����)��ͳ���Ӳ�����ˡ�
�������xml���潫��÷ǳ��Ӵ�,����Ҫ����(�ȱ���)��
2.Spring�޷��ڷ�����ע�룬ֻ��ͨ���ײ��BeanFactory�����bean����������bean��
3.ֱ����Spring����޷�ʵ��ϵͳ�������jar�Ľ��

�Ȳ����ǡ����ȷ�װBeanFactory��ʹ�á��ӿ� <T> T getBean(Class<T> c , String beanName);
 �����һ�����ܹ���������spring��ע�룬����ʹ��spring��bean.���ṩ��new��ʹ�ã�����Ĭ����һ����߰汾��bean��ͬʱ�ṩ��̬�ĸı�new��ʹ��bean�� set���ܡ�3����Ȳ����ǣ��ɱ���ͬʱ�����Ѿ���Խ����ˡ�


����һ��:
����������Ч������ֻ����java10(switch֧�ֱ��ʽ)�вſ��Ը���.
1.������Ҫά�����ݿ�--ʵ�ּ��书��.(ϵͳ����Ĳ���ģʽ.��������Ǳ����). --��������
2.һ��Map���ϵ��ڴ�Ͳ���Ч��, ����һ��IFactory����newһ���������. (2�ز���Ч������.)   --��Ҫ��һ��Map��ά��.
3.��ά����ʱ��ֱ��,��Ҫ���ҵ���˭ע���key,Ȼ���ٶ������ݿ⿴�����getBean����.         --��ά����˵�Ǹ�ج��.������̨�ķ�װ(�������ñ�һ��.) 
 */
public  class IOC {
	static Map<Class,IFactory> allFactory = new HashMap<Class,IFactory>(); 
	/** ע��
	 * @param cla
	 * @param id
	 */
	public static void register(Class cla, IFactory id) {
		allFactory.put(cla, id);
	}
	/** ���Bean
	 * @param cla
	 * @param id
	 * @return
	 */
	public static <T> T getBean(Class<T> cla, String id) {
		return (T) allFactory.get(cla).getBean(id);
	}
}
