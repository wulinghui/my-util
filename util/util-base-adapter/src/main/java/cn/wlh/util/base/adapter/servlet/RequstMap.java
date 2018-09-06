package cn.wlh.util.base.adapter.servlet;

import java.util.Map;

import javax.servlet.ServletRequest;

import cn.wlh.util.base.adapter.java.util.AdapterMap;
import net.sf.json.JSONObject;

/**
 * @author �����
 * 
 */
public class RequstMap extends AdapterMap<String, String[]> implements JsonInterface{
	/***/
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param source 
	 */
	public RequstMap(Map<String, String[]> source) {
		super(source);
	}
	public RequstMap(ServletRequest source) {
		this(source.getParameterMap());
	}
	
	
	public String getOne(String key) {
		JSONObject obj;
		return this.getOne(key, 0);
	}
	public String getOne(String key ,int arrayIndex ) {
		return this.get(key)[arrayIndex];
	}
	/** ֻ�����е�key,û����������Ĳ�����
	 * @param key
	 * @param value
	 * @param arrayIndex
	 * @return
	 */
	public RequstMap putOne(String key , String value ,int arrayIndex ) {
		this.get(key)[arrayIndex] = value;
		return this;
	}
	/**һ������put��ȥ��
	 * @param key
	 * @param value
	 * @param arrayIndex
	 * @return
	 */
	public RequstMap putOneOfAutoAdd(String key , String value ,int arrayIndex ) {
		String[] strings = this.get(key);
		if( strings == null ) {
			strings = new String[ arrayIndex +1 ];
			strings[ arrayIndex ] = value;
		}else{
			//�ж������Ƿ��㹻
			int length = strings.length;
			int i = arrayIndex +1;
			//�㹻
			if( length >= i ) {
				return putOne(key, value, arrayIndex);
			}else {
				//����
				String [] newStrings = new String[ i ];
				for (int j = 0; j < length; j++) {
					newStrings[j] = newStrings[j];
				}
				newStrings[ arrayIndex ] = value;
			}
		}
		return this;
	}
	public RequstMap putOne(String key , String value  ) {
		return putOneOfAutoAdd(key, value, 0);
	}
	@Override
	public String toJson() {
		DataBus2 bd = new DataBus2();
		bd.putAll(this);
		return bd.toString();
	}
}
