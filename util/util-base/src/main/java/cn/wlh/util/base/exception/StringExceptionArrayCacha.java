package cn.wlh.util.base.exception;
/**
 * @author �����
 * TODO �������һЩ����Ҫ����Ĵ���ʽ��ֱ��new
 * ����Ҳ�Ϳ��Ʋ���...
 * 1.ͨ������������...���ǵ��߲���������ʱ��.�ڴ��CPU����ͬ��Ҳ������
 * 2.����ֻ����-ֵջ��Ϣ���������Ϣ�ŵ��̱߳����С��ڴ����쳣��ʱ����ȡ�������ֵ�������Ǵ��쳣�����á�
 * --Ҳ����˵���쳣������Ҳ��ֻ���ڿ��Ƴ������̣�����ʾ��Ϣ�ŵ��̱߳����У���Ҫ��ʱ��ֱ�Ӵ��̱߳����л�á�
 * @param <T>
 */
@Deprecated //@see cn.wlh.util.base.exception.RuntimeExceptionCache ʾ��..
public abstract class StringExceptionArrayCacha<T extends Throwable> extends AbstractExceptionOfArrayCache<T> {
	
	public StringExceptionArrayCacha(int arrayLenth) {
		super(arrayLenth);
	}

	/**���ʵ��
	 * @param message
	 * @param index 
	 * @param isCache  true--new   false--������
	 * @return
	 */
	protected T getInstance(int index ,String message,boolean isNoCache ) {
		if( isNoCache ) return newInstance(message);
		T t = get(index);
		if( t == null ) {
			t = newInstance(message);
			put(index, t);
		}
		return t;
	}
	
	/**ִ�����쳣..
	 * @param message
	 * @param isNoCache true--new   false--������
	 * @throws T
	 */
	protected void doThrow(int index ,String message,boolean isNoCache) throws T {
		T instance = getInstance(index ,message,isNoCache);
		super.excuteThrows(instance);
	}
	/**ִ�����쳣�һ���
	 * @param message
	 * @throws T
	 */
	public void doThrow(int index ,String message) throws T {
		doThrow(index ,message, false);
	}
	/**ִ�����쳣������
	 * @param message
	 * @throws T
	 */
	public void doThrowNoCache(int index ,String message) throws T {
		doThrow(index ,message, true);
	}
	/** new ʵ��
	 * @param message
	 * @return
	 */
	protected abstract T newInstance(String message);
}
