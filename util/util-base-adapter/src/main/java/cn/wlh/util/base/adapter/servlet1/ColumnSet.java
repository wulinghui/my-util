package cn.wlh.util.base.adapter.servlet1;

/**
 * @author ����� RecordSet2 -- ����ΪColumnSet���� ���Ǿۺ�json��jar ��ʵ��List -- ͨ�� json ����
 *         ���������ʵ��.
 * 
 */
public class ColumnSet extends AbstractJSONArray<Object> {

	@Override
	public Object get(int index) {
		return super.json.get(index);
	}
	
}
