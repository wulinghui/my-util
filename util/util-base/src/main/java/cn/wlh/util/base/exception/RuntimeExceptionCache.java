package cn.wlh.util.base.exception;

public class RuntimeExceptionCache extends StringExceptionCacha<RuntimeException>{
	public static final RuntimeExceptionCache exception = new RuntimeExceptionCache();
	@Override
	protected RuntimeException newInstance(String message) {
		return new RuntimeException(message);
	}
	public static void main(String[] args) {
		A a = new A();
		B b = new B();
		try {
			a.aaaa();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			b.bbbbb();
//			 Thread.currentThread().currentThread().getStackTrace()[0].
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * �ӽ����֪..�����ֵջ����������..��Ҳ��˵������ java.lang.Throwable.fillInStackTrace()����ֵջ..
		 * Ҳ����˵�����ڲ�ͬ��������ʹ�ã�����������ά���Ѷȡ�����
		 * �����ֵջΪ�����Ļ����������������粻�����ء�               Thread.currentThread().getStackTrace(); //  (new Exception()).getStackTrace();
		 * �����������û����ⲻ��һ���õķ�ʽ��һ���û�������ʹ�ý����²���ά����		
		 * 
		 * ���ǿ�����һ���ط�ͳһ��ʼ������������һ���ط�ʹ�á�   ����ֵջ��Ϣ�Ƕ���ĵط���
		 * 
		 * ֻ�ܰ����������û��ˡ�
		 */
//		xxx.doThrow("..");
	}
	static {
		try {
			RuntimeExceptionCache.exception.put("aaaa",  new RuntimeException("aaaa"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	static RuntimeException runtimeException = new RuntimeException("zzzz");
	
}

/**
 * @author �����
 * ����ֵջ�Ļ��� 
 */
class A{
	void aaaa() {
		RuntimeExceptionCache.exception.doThrow("aaaa");
	}
}
class B{
	void bbbbb() {
		RuntimeExceptionCache.exception.doThrow("aaaa");
	}
}
