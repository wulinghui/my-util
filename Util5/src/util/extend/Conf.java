package util.extend;

import java.util.HashMap;
import java.util.Map;

import util.base._Class;
import util.base._Class.PackageAllClass;

/**
 * @author wlh
 * 该类是所有的Config--交给子类在com.nokey_value包下的子类重写checkIn()
 * 子程序用的,无法确定.key和value
 */
public abstract class Conf {
	static Map<String,Object> map = new HashMap<String, Object>();
	static{
		try{//com.config包下的类..执行登记
			PackageAllClass pa = new PackageAllClass(_Class.forName("com.novalue.Config" ));//
			for (Class<?> cla : pa.ALL_CLASS.values()) {
				map.put(cla.getSimpleName(),//在这里确定子类..否则报错. 
						((Conf)cla.newInstance()).checkIn());
				
			}
		}catch (Exception e) {
		}
	}  
	/** 子类 登记,建议放回数组 @return [] */
	protected abstract Object checkIn();
	
	/**
	 * @param key --类名
	 * @param index --下标
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
