package cn.wlh.util.base.exception;

/**
 * @author �����
 * �����������������Ĵ�����Ϊ-�����ǲ�ͬ�̵߳Ļ�..����������Ѿ������ˡ�
 * ֻ�����û���Ҫ�����淶д���档
 * @param <T>
 */
public abstract class AbstractExceptionOfThreadLocal <T extends Throwable> extends AbstractExceptionOfArrayCache<T>{
	 static final ThreadLocal<Throwable> CASE_THREADLOCAL = new ThreadLocal<Throwable>();
	 static final ThreadLocal<Object[]> INFO_THREADLOCAL = new ThreadLocal<Object[]>();
	
	/**
	 * @return
	 * @see java.lang.ThreadLocal#get()
	 */
	public final static Throwable getCase() {
		return CASE_THREADLOCAL.get();
	}
	/**
	 * @return  ��ô�����쳣�������Ϣ
	 * @see java.lang.ThreadLocal#get()
	 */
	public final static Object[] getInfo() {
		return INFO_THREADLOCAL.get();
	}

	public AbstractExceptionOfThreadLocal(int arrayLenth) {
		super(arrayLenth);
	}
	
	/**һ���������һ�д����Ӧһ��ʵ����
	 * @param index
	 * @param message
	 * @return
	 */
	protected T getInstance(int index , String message ){
		T t = get(index);//�ӻ�����
		if( t == null ) {
			t = newInstance(message);
			put(index, t);
		}
		return t;
	}
	/**new 
	 * @param message
	 * @return
	 */
	protected abstract  T newInstance(String message);
	
	
	/**�����Զ����쳣����ִ�����쳣..
	 * @param message
	 * @throws T
	 */ // ���뻺��..ֻ�ǻ���ֵջ��Ϣ�ʹ�ӡ����̨����Ϣ(message).
	public void doThrowOfNew(int index , String message,Object ... infos ) throws  T {
		T instance = getInstance(index,message);
		doThrow(instance, infos);
	}
	/**�����Զ����쳣����ִ�����쳣..
	 * @param index 
	 * @param message 
	 * @param cause -- ϵͳ�ڲ�ͬ��tryλ���׳���
	 * @param infos
	 * @throws T
	 */
	public void doThrowOfNew(int index , String message, T cause,Object ... infos ) throws  T {
//		new Throwable(message , cause);
		T instance = getInstance(index,message);
		//�����̷߳�cause����Ϊ����ϵͳ�����ڲ�ͬ��tryλ���׳���,����ֱ�ӷŵ�instance����.
		CASE_THREADLOCAL.set(cause);
		INFO_THREADLOCAL.set(infos);
		super.excuteThrows(instance);
	}
	/**ִ�����쳣..
	 * ͨ������ϵͳ�������쳣������û�����档ֻ�ܽ��á�
	 * @param message
	 * @throws T
	 */ // bu����..
	public void doThrow(T e,Object ... infos ) throws  T {
		//����
		CASE_THREADLOCAL.set(e);
		INFO_THREADLOCAL.set(infos);
		super.excuteThrows(e);
	}
	
}
