package util.extend.database.file;

import java.io.IOException;
import java.util.Map;

import util.extend.database.file.FileBaseUtil;
/**
 * 配置文件中的数据字典,供注解或其他地方减少冗余代码
 * @author wlh
 *
 */
public abstract class DataDictionary {
	/**使用所有
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
		Map<String,String> v = get(id);//反
		if( v.get(key) == null){//没有才放进去
			v.put(key, value);//放
		}
		over(id , v);//存
	}
	/**字典得满足正则表达式
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
	/**获得View的数据字典
	 * */
	public static Map<String,String> getView() throws IOException, ClassNotFoundException{
		return get("View");
	}
	/**覆盖所有*/
	public static void overView(Map<String,String> v) throws IOException{
		over("View", v);
	}
	public static void putView(String key,String value) throws IOException, ClassNotFoundException{
		put("View", key, value);
	}
	/**获得Sql的数据字典
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