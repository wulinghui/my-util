package util.extend.modle.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * ����,�����в�������ϵͳ����,Ч�ʺͲ�͸��.
 * ����ʹ��public static final ��ȷ�������Ͷ���.����ģʽ -�ӿ�
 * ʹ�ù�����ô���new 
 * �ö���+��װ    -- ������Ԫģʽ. .--���������.@see Flyweight
 * ����˼��---Լ�����ڹ淶.
 * 
 * 
 * @author wlh
 * ����Ϊ������,����,��Ԫ,����,new.
 * ��Щ�������ǵ����� 
 * @param <S>	S �洢����
 * @param <T>	T new�Ķ���
 * @param <F>	�洢�ı�ʶ
 * ��ʵֻ����Ԫģʽ������     
 */
public abstract class Factory<S,T,F>{
	
	S s = newStore() ;//store
	protected abstract  S newStore();//��ô洢�Ķ���
	//�÷����������������..
	public T getBean(Class<?> cla,F flag) {
		if( accpet() ) {
			T t = getBeanFormStore(flag);
			if( t == null ) {
				t = getInstance(cla);
				store(t,flag);
			}
			return t;
		}
		return null;
	}
	protected  boolean accpet() {return true;}//�Ƿ�Ϸ�
//	protected abstract boolean isNoNewObj();//�ж��Ƿ���new����
//	protected abstract T noNewObj();//����new����,��store������
	protected abstract T getBeanFormStore(F key);
	protected abstract  T getInstance(Class<?> cla);//��new����
	protected abstract void store(T t,F key);//��δ洢..

//	public static final Factory<Object,Object> SINGLE = new Factory<Object,Object>(){
	public static class Single<T> extends Factory<T,T,Boolean>{
		protected T getBeanFormStore(Boolean key) {
			return s;
		}
		protected T getInstance(Class<?> cla) {
			try {  
				return (T) cla.newInstance();
			} catch (Exception e) {  
				return null;
			}
		}
		protected void store(T t,Boolean key) {
			this.s = t;
		}
		@Override
		protected T newStore() {
			return null;
		}
	};
//	public static final Factory<Collection<Object>,Object> ENUM = new Factory<Collection<Object>,Object>(){
	public static class Enum0<T> extends Factory<Map<String,T>,T,String>{
		@Override
		protected Map<String, T> newStore() {   
			return new HashMap();
		}
		@Override
		protected T getBeanFormStore(String key) {  
			return super.s.get(key);
		}
		@Override
		protected T getInstance(Class<?> cla) {
			return null;
		}
		@Override
		protected void store(T t,String key) {
			super.s.put(key, t);
		}
	};
	//��Ԫ
	public static class XdYr<E,T>{
		Enum0<T> enu = new Enum0<T>();
		public XdYr0<E,T> getBean(E e,Class<T> cla,String flag){
			XdYr0<E,T> xd = new XdYr0<E,T>();
			xd.t = enu.getBean(cla, flag);
			xd.e = e;
			return xd;
		}
	}
	public static class XdYr0<E,T>{
		E e;
		T t;//����Ķ���
		public E getE() {
			return e;
		}
		public void setE(E e) {
			this.e = e;
		}
		public T getT() {
			return t;
		}
		public void setT(T t) {
			this.t = t;
		}
	}
	//new ����
	@Deprecated/**--Ч��̫��....*/
	public static <C> C getBean(Class<C> c) {
		C obj = null;
		//���ĳ���µ����з���,
		//ͨ��return ����ȷ������,
		//����,
		//--Ч��̫��....
		return obj;
	}
}
