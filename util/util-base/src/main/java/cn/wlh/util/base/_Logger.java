package cn.wlh.util.base;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ����� ��־������ģʽ,����ֱ�����õ�����jar.
 */
public class _Logger {
	protected   _Logger() {} // ����
	
	Map<String, LoggerAdapter> map = new HashMap<String, LoggerAdapter>();
	static String defaultLogger = "0";
	static _Logger loger = new _Logger();
	public static void setDefaultLogger(String defaultLogger) {
		_Logger.defaultLogger = defaultLogger;
	}
	public static void setLoger(_Logger loger) {
		_Logger.loger = loger;
	}
	/** ������־����,ͬʱȷ������Ĭ�ϼ���.������д���������-�ı䲻ͬ�ļ���.*/
	public _Logger putNewInstance(LoggerAdapter obj) {
		if(obj == null) return loger;
		switch( obj.getClass().getName() ) {
			case "org.apche.log4j.adaptor":
				map.put("0", obj);
				break;
		}
		return loger;
	}
	/** 
	 * @param greade ����Ϊ������ܵ�,ż��Ԥ����������ʵ�����Ի�����־.
	 * 1-10 ��һ�����ϵĿ��    11-20 �ڶ������ϵĿ��  21-30....
	 * 00-09 ��һ�����ϵĿ��   10-19  �ڶ������ϵĿ��  20-29 ....
	 * ��ͬ�Ŀ�ܴ�ӡ�ĸ�ʽ��ͬ.�Ӷ�ʵ�ָ����Ի�����־.
	 * @param a
	 * @param replacement
	 * @return
	 */
	public _Logger log(int greade, String a, Object... replacement) {
		int _greade = greade % 10; //�����ļ���
		String _key = greade / 10 + "";  //ȷ�����.
		map.get(_key).log(_greade, a, replacement);
		return loger;
	}
	
	public _Logger info( String a, Object... replacement) {
		map.get(defaultLogger).info(a, replacement);
		return loger;
	}

	public _Logger debug( String a, Object... replacement) {
		map.get(defaultLogger).info(a, replacement);
		return loger;
	}

	public _Logger warn( String a, Object... replacement) {
		map.get(defaultLogger).info(a, replacement);
		return loger;
	}

	public _Logger error( String a, Object... replacement) {
		map.get(defaultLogger).info(a, replacement);
		return loger;
	}
	
	/**
	 * @author �����
	 * ����ת��������־��� - ����ֻ���������
	 * 
	 */
	public static interface LoggerAdapter{
		/**
		 * @param greade  �������0-9.���ܼ�ǰ׺
		 * @param a
		 * @param replacement
		 */
		public void log(int greade, String a, Object... replacement) ;

		public void info( String a, Object... replacement) ;

		public  void debug( String a, Object... replacement) ;

		public  void warn( String a, Object... replacement) ;

		public  void error( String a, Object... replacement) ;
		
	}
}
