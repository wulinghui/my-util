package util.extend.database.file.imp;

import java.io.IOException;

public abstract class EnumBaseSub<T> extends _FileBase<T> {
	
	public EnumBaseSub(Class<T> cl, String tableDescribe,
			String[] fields, String[] fieldsDescrbe) {
		super( "Enum" , _FileBaseClient.toPath(cl.getName()), tableDescribe, fields, fieldsDescrbe);
	}
	/** �����ھͲ���Ҫȥ�ļ�����ȥ��
	 */
	public T getBean(String name) throws IOException, Exception{
//		if( isContain(name) ) return null;//�����ھͲ���Ҫȥ�ļ�����ȥ��
		return super.getBean( name );//��Ȼ����.
	}
	/**�����������治���ڵ�key,�Ͳ���ֱ�����л����ļ���
	 * �����Ŀ����ʱ,��Ҫ��̬����ӵ��µĶ���.����Ҫ:
	 * ��Ҫ�Ȳ����Client.
	 * @param name
	 * @param t
	 * @throws IOException
	 */
	public void storeBean(String name,T t) throws IOException{
//		if(!super.field.keySet().contains(name)) return;
		if( isContain(name) ) return;
		super.storeBean(t,name );
	}
	private boolean isContain(String name){
		return !super.field.keySet().contains(name);
	}
}
