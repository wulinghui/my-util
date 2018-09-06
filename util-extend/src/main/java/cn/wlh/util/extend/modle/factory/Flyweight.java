package cn.wlh.util.extend.modle.factory;

import java.util.HashMap;
import java.util.Map;

import cn.wlh.util.base._Field;
import cn.wlh.util.base.interfaces._Filter;

/**
 * @author wlh
 * Class<?> tar ����Ķ������ǹ����.
 * 
 * @param <T> -- �������
 */
public class Flyweight<T>{  
	Map<String,T> flyMap = new HashMap<String,T>();
	private Flyweight(){}
	
	public static <T1> Flyweight<T1> newInstance(Class<?> tar) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		//ͨ��������. c������� public static ����,������map��  
		Flyweight f = new Flyweight();     
		_Field.getStaticFieldValues( f.flyMap, false, tar, _Filter.NO_FILTER);    
		return f;
	}
	
	public <A> ZiLi<T,A> newBean(String key, A a){
		ZiLi<T,A>  zi = new ZiLi<T,A>(  flyMap.get(key) ,a);
		return zi;
	}
	//�����ֶ�new        
	/** ��װ�� */
	private class ZiLi<T,A>{    
		T t;//�����
		A a;//�仯��
		
		public ZiLi(T t, A a) {
			super();
			this.t = t;
			this.a = a;
		}
		public A getA() {
			return a;
		}
		public void setA(A a) {
			this.a = a;
		}
		public T getT() {
			return t;
		}
	}
}
