package util.extend.wrap;

/**
 * @author wlh
 * ���ģʽ�Ļ���...
 * �ŵ�:Ϊ���Ʒ���Ȩ�� ��  ��д��ͬ����ֵ�ķ���..
 * ȱ��:������ø���̫��ķ���,�е��������
 * @param <K> --��װ����.
 */
public abstract class WrapObject<K> {
	protected K k = newObject();
	
	/**Ϊ�� this.k = newObject();   Ĭ�ϵĳ�ʼ��.   */
	protected abstract K newObject();
	
	/**
	 * @author wlh
	 * ��������ȱ��:������ø���̫��ķ���,�е��������
	 * �����Ա�¶���û�
	 */
	public static abstract class WrapSource<K> extends WrapObject<K> {
		/** return K k */
		public final K getSource(){return super.k;};
		
		protected final void setSource(K k) { this.k = k; }
	}
	
	
}
