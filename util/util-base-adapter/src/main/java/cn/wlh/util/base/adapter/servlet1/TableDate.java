package cn.wlh.util.base.adapter.servlet1;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * @author ����� RecordSet2 -- ����ΪColumnSet���� ���Ǿۺ�json��jar ��ʵ��List -- ͨ�� json ����
 *         ���������ʵ��.
 * 
 */
public class TableDate extends AbstractJSONArray<Record> {

	@Override
	public Record get(int index) {
		Object object = json.get(index);
		//������ʵ����.
//		System.out.println( object.getClass().getName() );
		Record db = new Record();
		//������ʵ����.
		if( object instanceof JSONObject) {
//			db = new Record();
			JSONObject new_name = (JSONObject) object;
			db.putAll(new_name);
		}
		return db;
	}

//	public void addAll(List<Map<String, Object>> query2) {
//		super.json.addAll(query2);
//	}
	
	
}
