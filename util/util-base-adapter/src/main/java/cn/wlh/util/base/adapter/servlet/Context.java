package cn.wlh.util.base.adapter.servlet;

import java.util.Map;
import java.util.function.BiFunction;

import cn.wlh.util.base.adapter.java.util.JavaUtilFactory;

public class Context extends DataBus{
	public Context() {
		super(JavaUtilFactory.INSERT_OF_FIELD);
	}
	/**
	 * ��׼ʹ��.ֱ���쳣
	 */
	public static final String  EXCEPTION_MASEEGE = "��׼ʹ��.ֱ���쳣";
	public static void notAllowUser(){
		throw new RuntimeException( EXCEPTION_MASEEGE);
	}
	@Override
	public void putAll(Map m) {
		this.notAllowUser();
	}
	@Override
	public void putAll0(DataBus m) {
		this.notAllowUser();
	}
//	@Override
//	public void replaceAll(BiFunction<? super String, ? super Object, ? extends Object> function) {
//		this.notAllowUser();
//	}
	@Override
	public void clear() {
		this.notAllowUser();
	}
	@Override
	protected void setSource(Map<String, Object> source) {
		this.notAllowUser();
	}
	
}
