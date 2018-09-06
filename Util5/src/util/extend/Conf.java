package util.extend;

import java.util.HashMap;
import java.util.Map;

import util.base._Class;
import util.base._Class.PackageAllClass;

/**
 * @author wlh
 * ���������е�Config--����������com.nokey_value���µ�������дcheckIn()
 * �ӳ����õ�,�޷�ȷ��.key��value
 */
public abstract class Conf {
	static Map<String,Object> map = new HashMap<String, Object>();
	static{
		try{//com.config���µ���..ִ�еǼ�
			PackageAllClass pa = new PackageAllClass(_Class.forName("com.novalue.Config" ));//
			for (Class<?> cla : pa.ALL_CLASS.values()) {
				map.put(cla.getSimpleName(),//������ȷ������..���򱨴�. 
						((Conf)cla.newInstance()).checkIn());
				
			}
		}catch (Exception e) {
		}
	}  
	/** ���� �Ǽ�,����Ż����� @return [] */
	protected abstract Object checkIn();
	
	/**
	 * @param key --����
	 * @param index --�±�
	 * @return
	 */
	public static int getInt(String key,int index){
		return ((int[])map.get(key))[index];
	}
	public static String getString(String key,int index){
		return ((String[])map.get(key))[index];
	}
	public static <T> T getObject(String key,int index){
		return ((T[])map.get(key))[index];
	}
}
