package cn.wlh.util.base.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * @author �����
 *@see /util-base/src/main/java/cn/wlh/util/base/exception/info.txt
 *
 *���ַ����ĺô���  1.���Ĵ����ٶȡ�
 *             2.���趨���쳣
 *���ַ�����ȱ��: 	1.�Լ������ֵջ��Ϣû�С�--�û�׼ȷ��λ�����λ�á�
 *				2.����ֱ��ͨ���쳣����������Ϣ��
 *
 *
 *TODO 	�Լ������ֵջ��Ϣû�С�--�û�׼ȷ��λ�����λ�ã����Ը���ֻ���������ϲ��ʹ�á�
 *		�����Զ����쳣����Ҫʹ�ø��࣬���򽫸��²���Ա�����������š�
 *		�����޷��鿴Դ��͸���ج�Σ�û��ֵջ��Ϣ�������û������ʾ-GG����
 */
public class ExceptionUtil extends Exception{
	// �������̣�ֵջ��Ϣ��׼��
	 final RuntimeException INTERRUPT = new RuntimeException("end liucheng,zhizhan xinxi bu zhun");
//	 final ExceptionUtil INTERRUPT0 = new ExceptionUtil("end liucheng,zhizhan xinxi bu zhun");
	 
	 final ThreadLocal<Throwable> THROWABLE = new ThreadLocal<>();
	 final ThreadLocal<Object []> EXCEPTION_INFO = new ThreadLocal<Object []>();
	 final String string;
	 public ExceptionUtil(String string) {
		this.string = string;
	 }
	 public ExceptionUtil() {
		 this(":next- \t -next:");
	 }
		/**1.ʹ���쳣��ֹ����ִ�С�--�����û������ס�ֵջ��Ϣ
		 * �����ṩ��ֹ����ʾ��Ϣ����ֵջ��Ϣ��
		 * @param infos
		 */
		public  void interrupt(Object... infos ) {
			setThrowLocal(INTERRUPT,infos);
			throw INTERRUPT;
		}
		/**1.ʹ���쳣��ֹ����ִ�С�--
		 * �����ṩ��ֹ����ʾ��Ϣ����ֵջ��Ϣ��
		 * @param infos
		 * @throws Exception 
		 */
		public  void doThrow(Object... infos ) throws ExceptionUtil {
			setThrowLocal(this,infos);
			throw this;
		}
	
	
	// 2.��ϵͳ�쳣���д������Ӹ��쳣����ִ�к������̡�
	/**3.��ϵͳ�쳣���д���֮��Ѹ��쳣�׳���
	 * @throws T 
	 * 
	 */
	public  <T extends Throwable> void HandleAfterThrow(T e) throws T {
		setThrowLocal(e,new Object[]{});
		throw e;
	}
	/**4.��ϵͳ�쳣���д���֮��Ѹ��쳣ת�����Զ����쳣���׳���
	 * @throws T 
	 * 
	 */
	public  <T extends Throwable> void HandleAfterThrow(T e,Object... infos ) throws T {
		setThrowLocal(e, infos);
		throw e;
	}
	
	//5.��ϵͳ�쳣ֱ���׳������ϲ㴦��
	
	//setThrowLocal
	protected   void setThrowLocal(Throwable e,Object... infos) {
		THROWABLE.set(e);
		EXCEPTION_INFO.set(infos);
	}


	
	/**
	 * @return the interrupt
	 */
	public  Throwable getThrowable() {
		return THROWABLE.get();
	}

	@Override
	public String getMessage() {
		return getThrowable().getMessage();
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getLocalizedMessage()
	 */
	@Override
	public String getLocalizedMessage() {
		// TODO Auto-generated method stub
		return getThrowable().getLocalizedMessage();
	}


	/* (non-Javadoc)
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getThrowable().toString();
	}


	/* (non-Javadoc)
	 * @see java.lang.Throwable#printStackTrace()
	 */
	@Override
	public void printStackTrace() {
		// TODO Auto-generated method stub
		getThrowable().printStackTrace();
	}


	/* (non-Javadoc)
	 * @see java.lang.Throwable#printStackTrace(java.io.PrintStream)
	 */
	@Override
	public void printStackTrace(PrintStream s) {
		// TODO Auto-generated method stub
		getThrowable().printStackTrace(s);
	}


	/* (non-Javadoc)
	 * @see java.lang.Throwable#printStackTrace(java.io.PrintWriter)
	 */
	@Override
	public void printStackTrace(PrintWriter s) {
		// TODO Auto-generated method stub
		getThrowable().printStackTrace(s);
	}


	/* (non-Javadoc)
	 * @see java.lang.Throwable#getStackTrace()
	 */
	@Override
	public StackTraceElement[] getStackTrace() {
		// TODO Auto-generated method stub
		return getThrowable().getStackTrace();
	}


	/* (non-Javadoc)
	 * @see java.lang.Throwable#setStackTrace(java.lang.StackTraceElement[])
	 */
	@Override
	public void setStackTrace(StackTraceElement[] stackTrace) {
		getThrowable().setStackTrace(stackTrace);
	}


	/**
	 * @return the throwable
	 */
	public  Object [] getExceptionInfo() {
		return EXCEPTION_INFO.get();
	}
	
	/*�����ٶ�
	 *one:16441
	 *two:24785
	 */
	/*public static void main(String[] args) {
		ExceptionUtil util = new ExceptionUtil();
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
	}*/
}
