package cn.wlh.util.extend.converter.entity;

import cn.wlh.util.extend.converter.RegiStorOfConverter;

/**
 * @author �����
 * �հ�������..ֻ��Class.from("...");����..
 */
class StringToBoolean  extends RegiStorOfConverter<String,Boolean>{
	 static {
		 //ע��һ��
		 new StringToBoolean();
	 }
	@Override
	public Boolean toConverter(String src) {
		return Boolean.parseBoolean(src);
	}
 }
