package cn.wlh.util.base.exception;

import java.util.List;
import java.util.Map;

import cn.wlh.util.base.JavaUtilFactory;
import cn.wlh.util.base.exception.ExceptionUtil1.ExceptionCache;

/**
 * @author �����
 * ������ʷ��¼
 */
public class ExceptionUtil1 extends ExceptionUtil {
	
	final ThreadLocal<List<Object>> HIS_EXCEPTION_INFO = new ThreadLocal<>();
	@Override
	protected void setThrowLocal(Throwable e, Object... infos) {
		super.setThrowLocal(e, infos);
		List<Object> list = HIS_EXCEPTION_INFO.get();
		//��һ��
		if( list ==null) {
			list = JavaUtilFactory.newList(JavaUtilFactory.INSERT_OF_METHOD);
			HIS_EXCEPTION_INFO.set(list);
		}
		//������ʷ
		for (Object object : list) {
			list.add(object);
		}
	}
	public ExceptionCache newExceptionCache() {
		return new ExceptionCache();
	}
	//��ÿ������ʹ�á�
	/** ExceptionUtil1 eUtil����������Ŀ��ͬʹ�á�
	 *  ExceptionCache eUtil.new ExceptionCache();ÿ����ʹ�á������ֻ�ڱ�����ʹ�ã���Ϊ������messageΪ�����ġ�
	 *  �Լ���ϵͳ(��װjar--��Ҫ��Աά���ĵط�)��ʹ�����жϳ���ĵط��������١��������ظ��͸������ˡ�
	 */
	public class ExceptionCache {
		Map<String,RuntimeException> runtimeException = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_METHOD);
		Map<String,ExceptionUtil> throwException = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_METHOD);
		/**message��Ҫ�ظ�
		 * @param message
		 * @param infos
		 */
		public void interrupt(String message,Object ... infos) {
			RuntimeException runtimeException2 = runtimeException.get(message);
			if(runtimeException2 ==null) {
				runtimeException2 = new RuntimeException(message);
				runtimeException.put(message, runtimeException2);
			}
			setThrowLocal(runtimeException2,infos);
			throw runtimeException2;
		}
		/**message��Ҫ�ظ�
		 * @param message
		 * @param infos
		 */
		public void doThrow(String message,Object ... infos) throws ExceptionUtil {
			ExceptionUtil runtimeException2 = throwException.get(message);
			if(runtimeException2 ==null) {
				runtimeException2 = new ExceptionUtil(message);
				throwException.put(message, runtimeException2);
			}
			setThrowLocal(runtimeException2,infos);
			throw runtimeException2;
		}
	}
	/*�����ٶ�
	 * one:18048
	 * two:22242
	 */
	
	/*public static  void main(String[] args) {
		ExceptionUtil1 util = new ExceptionUtil1();
		int length = 10000000;
		long currentTimeMillis = System.currentTimeMillis();
		for (int i = 0; i < length; i++) {
			try {
				throw new Throwable("zzz"); // ģ���׳�
			} catch (Throwable e) {
				try {
					util.HandleAfterThrow(e); // ģ�⴦��
				} catch (Throwable e1) {
					//ģ���¼
					util.getExceptionInfo();
					util.getThrowable();
				}
			}
		}
		long currentTimeMillis1 = System.currentTimeMillis();
		System.out.println("one:"+( currentTimeMillis1 - currentTimeMillis ));
		long currentTimeMillis2 = System.currentTimeMillis();
		for (int i = 0; i < length; i++) {
			try {
				throw new Throwable("zzz"); // ģ���׳�
			} catch (Throwable e) {
				try {
					throw new Throwable(e.getMessage()) {
					}; // �Զ���ת���쳣.
				} catch (Throwable e1) {
					//ģ���¼
					e.getMessage();
					e.getCause();
					e.getLocalizedMessage();
				}
			}
		}
		long currentTimeMillis3 = System.currentTimeMillis();
		System.out.println("two:"+( currentTimeMillis3 - currentTimeMillis2 ));
	} */
	 
}
class AAAA{  
	public static ExceptionUtil1 exceptionUtil1 = new ExceptionUtil1();
	public static ExceptionCache exceptionCache = exceptionUtil1.new ExceptionCache();
	public void test() throws ExceptionUtil {
		exceptionUtil1.doThrow();
		exceptionCache.doThrow("");
	}
}