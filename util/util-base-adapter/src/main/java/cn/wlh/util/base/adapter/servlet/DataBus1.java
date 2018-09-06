package cn.wlh.util.base.adapter.servlet;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import cn.wlh.util.base._Field;




/**
 * @author �����
 * ���Ǽ̳�json��jar     -- net.sf.json �޷��̳�..����ô����...666
 * ��ʵ��Map
 */
public class DataBus1 extends org.json.JSONObject implements Map<String,Object>{
	/** ��ø����ڲ���Map */
	Map<String,Object> myMap = _Field.getValueByField(this, "map");
	@Override
	public int size() {
		return myMap.size();
	}

	@Override
	public boolean isEmpty() {
		return myMap.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return myMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return myMap.containsValue(value);
	}

	@Override
	public Object get(Object key) {
		return myMap.get(key);
	}

	@Override
	public Object remove(String key) {
		return super.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		myMap.putAll(m);
	}

	@Override
	public void clear() {
		myMap.clear();
	}

	@Override
	public Collection<Object> values() {
		return myMap.values();
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		return myMap.entrySet();
	}

	@Override
	public Object remove(Object key) {
		return myMap.remove(key);
	}

}
