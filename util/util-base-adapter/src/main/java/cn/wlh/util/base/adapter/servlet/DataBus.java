package cn.wlh.util.base.adapter.servlet;

import java.util.Map;

import cn.wlh.util.base.adapter.java.util.AdapterMap;
import cn.wlh.util.base.adapter.java.util.JavaUtilFactory;

public class DataBus extends AdapterMap<String,Object> {

	/**
	 * @param source
	 * @param i  @seee cn.wlh.util.base.adapter.java.util.JavaUtilFactory.newflag(int)
	 *          ����ľͲ�new Map��
	 */
	private DataBus(Map<String, Object> source , int i) {
		super(source);
		this.newFlag = i ;
	}
	/**��¼new����ı�ʶ*/
	final int newFlag;  
	/**
	 * @param  i   @seee cn.wlh.util.base.adapter.java.util.JavaUtilFactory.newflag(int)
	 */
	public DataBus(int i) {
		this( JavaUtilFactory.newMap( i ) , i);
	}
	/**
	 *  �������ڷ���������ʹ�ò�ѯ���޸� 
	 */
	public DataBus() {
		this( JavaUtilFactory.SELECT_OF_METHOD );
	}
	@Override
	public void clear() {
		super.setSource( new DataBus(this.newFlag) );
	}
	@Override
	public void putAll(Map m) {
		if( size() == 0 ) {
			super.setSource(m);
		}
		super.putAll(m);
	}
	public void putAll0(DataBus m) {
		this.putAll(m);
	}
}
