package cn.wlh.util.base.adapter.java.util;

import java.util.Map;
import java.util.Properties;

import cn.wlh.util.base._Map;

/**
 * @author �����
 * ��ǿjava.util.Properties�Ĺ���.
 */
public class AdapterProperties extends Properties {

	/***/
	private static final long serialVersionUID = -1770106762218119615L;
	
	/**����������ݰ�˳�����Properties����. <br/>
	 * [ key1 , value1 <br/>
	 * 	 key2 , value2 <br/>
	 *   key3 , value3 ]<br/>
	 * �������������ڹ��캯����ʹ��  <br/>
	 * @param array2Map
	 * @return
	 */
	public  AdapterProperties regsiterByArray(Object [] array2Map) {
		_Map.array2Map(this, array2Map, 2);
		return this;
	}
}
