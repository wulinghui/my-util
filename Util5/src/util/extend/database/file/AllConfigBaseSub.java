package util.extend.database.file;

import java.io.IOException;
import java.util.Map;

import util.extend.database.file.imp._FileBase;
import util.extend.database.file.imp._FileBase._FileBaseClient;

/**全局配置文件,一次性加载jvm里面.
 * @author wlh
 */
public class AllConfigBaseSub extends _FileBase<Map<String,Map<String,Object>>> {
	public AllConfigBaseSub(String suf, String directory, String tableDescribe,
			String[] fields, String[] fieldsDescrbe) {
		super(suf, _FileBaseClient.toPath( AllConfigBaseSub.class.getPackage().getName() ) 
				, tableDescribe, fields, fieldsDescrbe);
	}
	public Map<String,Map<String,Object>> getBean(String id) throws IOException, Exception{
		return super.getBean(id);
	}
	public void storeBean(String id,Map<String,Map<String,Object>> t) throws IOException{
		super.storeBean(t, id);
	}
	public Map<String,Object> getMap(String id,String key) throws IOException, Exception{
		return getBean(id).get(key);
	}
	public <T> T getOBj(String id,String key,String k) throws IOException, Exception{
		return (T) getMap(id,key).get(key);
	}
}