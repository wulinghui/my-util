package util.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import util.base.interfaces._Filter;

/**
 * @author wlh
 * �ݹ�:
 * 1.ʲôʱ�����
 * 2.����
 * 3.����
 * 4.����--�ص�
 * 5.���ݵĴ洢.--�ص��Ľ��
 * @param <T>
 * @param <H>
 */
public abstract class _For {
	/**��������
	 * @param <T> �������
	 * @param <R> �Żص���
	 * @param list �洢��
	 * @param ts   ��Ҫ���������
	 * @param f    ���˵�    f = null 
	 * @param sort �����	sort=null --������
	 * @param h    ������
	 * @return
	 */
	public static <T,R> List<R> forArray(List<R> list,T[] ts,_Filter<T> f,Comparator<T> sort,CallBack<T,R> h){
		//_For����
		if(sort != null) Arrays.sort(ts, sort);
		//ϵͳ�ṩ
		if(list == null ) list = new ArrayList<R>();
		//����
		for (T t : ts) {
			//����
			if( f==null || f.accept(t) ){
				//�����洢
				R r = h.handle(t);//����null
				if( r !=null ) list.add( r );  
			}
		}
		return list;
	}
	public static <T,R> List<R> forArray(List<R> list,Collection<T> ts,_Filter<T> f,Comparator<T> sort,CallBack<T,R> h){
		return forArray( list, (T[])ts.toArray()  , f ,sort , h );
	}
	public static <T,R> List<R> forArray(T[] ts , CallBack<T,R> h){
		return forArray(null,ts,null,null,h);
	}
	public static <T,R> List<R> forArray(Collection<T>  ts , CallBack<T,R> h){
		return forArray(null, (T[])ts.toArray() ,null,null,h);
	}
	/** �ص� */
	public static interface CallBack<T,R>{
		R handle(T t);
	}
	public static interface NoRutrun<T> extends CallBack<T,Void>{}
//	public static class A implements NoRutrun<Object>{
//
//		@Override
//		public Void handle(Object t) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//		
//	}
}
