package cn.wlh.util.base;


public  abstract class _Exception{
	/**�����ջ���˳��߳�
	 * @return ��Щ������Ҫ����ֵ,��������������Ż�---ռλ������������*/
	public static <T> T printStackAndExit(Throwable e){
		e.printStackTrace();
		Thread.currentThread().interrupt();
		return null;
	}
	/** ����ʹ��throw�ؼ���.ͬʱ�����԰�eҲ����Ioc����
	 * @param <T>
	 * @param e
	 * @throws T
	 */
	public static <T extends Throwable> void throwable(T e) throws T{
		 throw e;
	}
	public static <T extends RuntimeException> void runtime(T e) {
		 throw e;
	}
	public static <T extends Throwable> void throwable( Class<T> e ) throws T{ 
		try {
			throwable( e.newInstance() );
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	public static <T extends RuntimeException> void runtime(Class<T> e) {
		try {
			runtime( e.newInstance() );
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	/** ת��Ϊ����ʱ�쳣.
	 *  ��������public static final R t = new .... throws �����
	 *  ��static final��ʱ�����쳣.��static{}�ֲ��ܴ����ʱ��
	 * @param h
	 */
	public static <R,Run extends RuntimeException> R toRuntime(ToException<R,Run> h){
		try{
			return h.handle();
		}catch(Throwable e){
			e.printStackTrace();   
			throw h.toException();
		}
	}
	public static interface ToException<R,Run extends RuntimeException>{
		R handle() throws Throwable;
		Run toException();
	}
	public static abstract class ToNullPointerException<R> implements ToException<R,NullPointerException>{
		public NullPointerException toException() {return new NullPointerException();}
	}
	/**
	 * ���������쳣,ȷ��������������
	 */
	public static <R> R handleAll(HandleException<R> h) {
		try{
			return h.normal();
		}catch(Throwable e){
			return h.exception(e);
		}
	}
	public static interface HandleException<R>{
		/** �������� */
		R normal() throws Throwable;
		/** �쳣���� */
		R exception(Throwable e);
	}
	public static abstract class Print<R> implements HandleException<R>{
		public R exception(Throwable e) {
			e.printStackTrace();//�򵥵Ĵ�ӡ��.
			return null;
		}
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
}
