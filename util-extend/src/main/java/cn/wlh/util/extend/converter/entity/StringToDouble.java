package cn.wlh.util.extend.converter.entity;

import cn.wlh.util.extend.converter.RegiStorOfConverter;

/**
 * @author �����
 * �հ�������..ֻ��Class.from("...");����..
 */
class StringToDouble  extends RegiStorOfConverter<String,Double>{
	 static {
		 //ע��һ��
		 new StringToDouble();
	 }
	@Override
	public Double toConverter(String src) {
		return Double.parseDouble(src);
	}
 }
