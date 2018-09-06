package cn.wlh.util.base.exception;

/**
 * @author �����  --һ���������һ�д����Ӧһ��ʵ����
 * ��Ԫģʽ,ʵ��һЩ������Ĺ���..
 * ������ķ�ʽʵ��..�û������ע��,�ͱ����޸Ĺ��췽���������ֵ����������,Ҳ����˵�û��������֪��,��������ӵ������һ����
 * ��������Ҳ��һ�����⣬һ��ʼ�û��Ͱ�����д�ĺܴ���ɺ�����Ա������ʱ�Ĵ���
 */@Deprecated  //@see cn.wlh.util.base.exception.RuntimeExceptionCache ʾ��..
public class AbstractExceptionOfArrayCache<T extends Throwable> {
	/***/
	private static final long serialVersionUID = 3870803932126272408L;
	final Object cache [];
	public AbstractExceptionOfArrayCache(int arrayLenth) {
		cache = new Object[arrayLenth];
	}

	protected void put(int index  , T e) {
		cache[index] = e;
	}
	
	protected T get(int index) {
		return  (T) cache[index];
	}
	
	protected final  void excuteThrows(T e) throws T {
		throw e;
	}
}










 class AbstractExceptionOfArrayCacheSub extends AbstractExceptionOfArrayCache<RuntimeException>{
	public AbstractExceptionOfArrayCacheSub(int arrayLenth) {
		super(arrayLenth);
	}
	
	protected RuntimeException getInstance(int index , String message , boolean isNoCache){
		if( isNoCache ) return newInstance(message);
		RuntimeException t = get(index);
		if( t == null ) {
			t = newInstance(message);
			put(index, t);
		}
		return t;
	}
	protected  RuntimeException newInstance(String message) {
		return new RuntimeException(message);
	};

	/**ִ�����쳣..
	 * @param message
	 * @param isNoCache true--new   false--������
	 * @throws T
	 */
	protected void doThrow(int index , String message,boolean isNoCache) throws RuntimeException {
		RuntimeException instance = getInstance(index,message,isNoCache);
		super.excuteThrows(instance);
	}
	/**ִ�����쳣�һ���
	 * @param message
	 * @throws T
	 */
	public void doThrow(int index ,String message) throws RuntimeException {
		doThrow(index ,message, false);
	}
	/**ִ�����쳣������
	 * @param message
	 * @throws T
	 */
	public void doThrowNoCache(int index ,String message) throws RuntimeException {
		doThrow(index ,message, true);
	}
}
class AA{
	static AbstractExceptionOfArrayCacheSub EX_RUNTIME  = new AbstractExceptionOfArrayCacheSub(2) ;
	/*
	 * ������������ÿ�ζ���Ҫ�޸ĵ�
	 */
	void aaa(){
		EX_RUNTIME.doThrow(0, "zzz");
	}
	
	void bbb() {
		EX_RUNTIME.doThrow(0, "zzz");
	}
}