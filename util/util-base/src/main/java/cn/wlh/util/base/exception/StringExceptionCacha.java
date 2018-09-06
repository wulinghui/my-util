package cn.wlh.util.base.exception;
@Deprecated //@see cn.wlh.util.base.exception.RuntimeExceptionCache ʾ��..
public abstract class StringExceptionCacha<T extends Throwable> extends AbstractExceptionOfFlyWeight<String, T> {
	
	/**���ʵ��
	 * @param message
	 * @param isCache  true--new   false--������
	 * @return
	 */
	protected T getInstance(String message,boolean isNoCache) {
		if( isNoCache ) return newInstance(message);
		T t = get(message);
		if( t == null ) {
			t = newInstance(message);
			put(message, t);
		}
		return t;
	}
	
	/**ִ�����쳣..
	 * @param message
	 * @param isNoCache true--new   false--������
	 * @throws T
	 */
	protected void doThrow(String message,boolean isNoCache) throws T {
		T instance = getInstance(message,isNoCache);
		super.excuteThrows(instance);
	}
	/**ִ�����쳣�һ���
	 * @param message
	 * @throws T
	 */
	public void doThrow(String message) throws T {
		doThrow(message, false);
	}
	/**ִ�����쳣������
	 * @param message
	 * @throws T
	 */
	public void doThrowNoCache(String message) throws T {
		doThrow(message, true);
	}
	/** new ʵ��
	 * @param message
	 * @return
	 */
	protected abstract T newInstance(String message);
}
