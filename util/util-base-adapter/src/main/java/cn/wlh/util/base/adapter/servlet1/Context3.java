package cn.wlh.util.base.adapter.servlet1;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import cn.wlh.util.base._Exception;
import cn.wlh.util.base.adapter.java.util.JavaUtilFactory;
import cn.wlh.util.base.adapter.servlet.RequstMap;

/**
 * @author ����� json
 * 
 */
public class Context3 extends Record {
	/*
	 * json�����ֵ��������ҳ�潻���õ�. ���Ծ�����Ҫֱ�ӷ��뵽json����.�ȷ��뵽һ����Map�����¼��
	 * ʹ�õ�ʱ��Ҳ������ȡ,������Ҫ����ʾ��ʱ����뵽json���档 TODO
	 * �����и����⣬dao����ҲҪ�ֲ���?����һ����������biz�������кܶ�,dao������Ҳ�кܶ�..
	 */
	public static final int MAP_LEVEL = JavaUtilFactory.INSERT_OF_METHOD;
	
	/** ҳ�洫�����Ĳ��� */
	/** ҳ�洫����������,��׼��� */
	protected  RequstMap requst;
	/*
	 * protected ��ϵͳ����Ԥ��ʹ��..
	 */
	/** ҵ��Map */ // ������
	protected Map<String, Object> businessMap;// = JavaUtilFactory.newMap( MAP_LEVEL );
	/** ����Map=========== ������-������ (Ψһ��ʶ��) */
	protected Map<String, Object> daoMap = JavaUtilFactory.newMap(MAP_LEVEL);

	public Context3(/*ServletRequest source*/) {
		super(JavaUtilFactory.INSERT_OF_FIELD);
//		requst = new RequstMap(source); //����junit����..��ͨ��������.
	}
	
	public RequstMap getRequst() {
		return requst;
	}

	public void setRequst(ServletRequest requst) {
		if( this.requst == null) 
		this.requst = new RequstMap(requst);
	}

	/**
	 * ��׼�û�ʹ��.ֱ���쳣
	 */
	public static final String EXCEPTION_MASEEGE = "��׼ʹ��.ֱ���쳣";

	/**
	 * �������û����²�����
	 */
	public static void notAllowUser() {
		throw new RuntimeException(EXCEPTION_MASEEGE);
	}

	@Override
	public void putAll(Map m) {
		this.notAllowUser();
	}

	/**
	 * ���View--Json������ ..
	 */
	public void clearView() {
		init(super.DEFAULT_LEVEL);
		// this.notAllowUser();
	}

	/*
	 *
	 * 
	 * 
	 * 
	 */
	public String getOne(String key) {
		return requst.getOne(key);
	}

	public String getOne(String key, int arrayIndex) {
		return requst.getOne(key, arrayIndex);
	}

	public String[] get(Object key) {
		return requst.get(key);
	}

	public String[] put(String key, String[] value) {
		return requst.put(key, value);
	}

	public RequstMap putOne(String key, String value, int arrayIndex) {
		return requst.putOne(key, value, arrayIndex);
	}

	public RequstMap putOneOfAutoAdd(String key, String value, int arrayIndex) {
		return requst.putOneOfAutoAdd(key, value, arrayIndex);
	}

	public RequstMap putOne(String key, String value) {
		return requst.putOne(key, value);
	}

	/**
	 * ҳ��Ļ���ʾ����,�ŵ�json-View����.
	 */
	public static final String BACK_VIEW_KEY = "select-key"; 
	public void backView() {
//		json.putAll(requst); //������json������ĳ��,�����Ƴ�ĳ��ֵ�Ļ����������½�..ͬʱ����Ƴ�requstҲ������.
		json.put(BACK_VIEW_KEY, requst);
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 */
	public void registerBiz(String key, Object value) {
		// ������.
		if (businessMap == null)
			businessMap = JavaUtilFactory.newMap(MAP_LEVEL);
		businessMap.put(key, value);
	}

	public Object getBizRecord(String key) {
		return businessMap.get(key);
	}

	public Object getDaoRecord(String key) {
		return daoMap.get(key);
	}

	/**
	 * ���ṩǿ�ƴ�view , biz, dao������ҵĽ��������. <br/>
	 * ���ⱻ����.<br/>
	 * Ҳ����˵ʹ���߱������֪���Լ��Ǵ��ĸ��ط���õ�����.<br/>
	 * ����ֻ��View�ṩ��remove�Ĳ���.�����ļ�¼�������ᱻ�Ƴ���<br/>
	 * �����Ǵ�dao�п���һ�ݸ�view,Ҳ�ͱ�֤����;���ᱻ�۸�.������ŵ�һ�����Դ�����ȡ.<br/>
	 * 
	 * @param key
	 * @return
	 */
	public Object getRecord(String key) {
		throw new RuntimeException(EXCEPTION_MASEEGE);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	// ��ϵͳʹ�õ�.ϵͳ���������� ֱ�����Ա�protected--ֱ�Ӳ���
	///////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * ϵͳ���ڼ�¼Dao�Ľ������
	 * 
	 * @param key
	 *            ������-������ (Ψһ��ʶ��)
	 * @param value
	 *            �����
	 */
	protected void registerDao(String key, Object value) {
		daoMap.put(key, value);
	}
	protected void clearBiz() {
		businessMap = JavaUtilFactory.newMap(MAP_LEVEL);
	}
	protected void clearDao() {
		daoMap = JavaUtilFactory.newMap(MAP_LEVEL);
	}
}
