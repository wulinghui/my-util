package cn.wlh.util.extend.converter.entity;

import cn.wlh.util.extend.converter.RegiStorOfConverter;

/**
 * @author �����
 * �հ�������..ֻ��Class.from("...");����..
 */
class StringToShort  extends RegiStorOfConverter<String,Short>{
	 static {
		 //ע��һ��
		 new StringToShort();
	 }
	@Override
	public Short toConverter(String src) {
		return Short.parseShort(src);
	}
 }
