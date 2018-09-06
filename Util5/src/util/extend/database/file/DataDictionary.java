package util.extend.database.file;

import java.io.IOException;
import java.util.Map;

import util.extend.database.file.FileBaseUtil;
/**
 * �����ļ��е������ֵ�,��ע��������ط������������
 * @author wlh
 *
 */
public abstract class DataDictionary {
	/**ʹ������
	 */
	public static Map<String,String> get(String id) throws IOException, ClassNotFoundException{
		FileBaseUtil fbu = FileBaseUtil.getInstance("DataDictionary", DataDictionary.class);
		return fbu.deserialize(id);
	}
	public static void over(String id,Map<String,String> v) throws IOException{
		FileBaseUtil fbu = FileBaseUtil.getInstance("DataDictionary", DataDictionary.class);
		fbu.serialize(v	, id );
	}
	public static void put(String id,String key,String value) throws IOException, ClassNotFoundException{
		Map<String,String> v = get(id);//��
		if( v.get(key) == null){//û�вŷŽ�ȥ
			v.put(key, value);//��
		}
		over(id , v);//��
	}
	/**�ֵ������������ʽ
	 * @param source
	 * @param dataDictionary
	 * @return
	 */
	public static String toFullData(String source,Map<String,String> dataDictionary){
		for (String key : dataDictionary.keySet()) {
			source = source.replaceAll(key, dataDictionary.get(key));
		}
		return source;
	}
	/**���View�������ֵ�
	 * */
	public static Map<String,String> getView() throws IOException, ClassNotFoundException{
		return get("View");
	}
	/**��������*/
	public static void overView(Map<String,String> v) throws IOException{
		over("View", v);
	}
	public static void putView(String key,String value) throws IOException, ClassNotFoundException{
		put("View", key, value);
	}
	/**���Sql�������ֵ�
	 * */
	public static Map<String,String> getSql() throws IOException, ClassNotFoundException{
		return get("Sql");
	}
	public static void overSql(Map<String,String> v) throws IOException{
		over("Sql", v);
	}
	public static void putSql(String key,String value) throws IOException, ClassNotFoundException{
		put("Sql", key,value);
	}
}