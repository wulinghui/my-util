package util.extend.database.file.imp;

import java.io.IOException;
import java.util.Set;

/**�����Enum������.����ǰѶ���ŵ�һ���ļ�����
 * Enum�ǰѶ����ͬ�ķŵ���ͬ���ļ�����
 * @author wlh
 * ������Ҫ��Ϊ��Ӧ�Բ�ͬ����ģʽ�Ĳ���.
 * Set - ��ͬ������.�ŵ���ͬ���ļ���
 * ����Ҫ�л�ʱ,��ֱ����id�����.�������ܳ�Ϊ����ģʽ��.
 * @param <T>
 */
public abstract class SetSameBaseSub<T> extends _FileBase<Set<T>> {
	public SetSameBaseSub( Class<T> cl, String tableDescribe,
			String[] fields, String[] fieldsDescrbe) {
		super("Set", _FileBaseClient.toPath(cl.getName()) 
				, tableDescribe, fields, fieldsDescrbe);
	}
	public Set<T> getBean(String id) throws IOException, Exception{
		return super.getBean(id);
	}
	public void sotreBean(String id,Set<T> t) throws IOException{
		super.storeBean(t, id);
	}
}
