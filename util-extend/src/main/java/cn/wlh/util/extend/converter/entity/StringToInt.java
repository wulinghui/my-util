package cn.wlh.util.extend.converter.entity;

import cn.wlh.util.extend.converter.RegiStorOfConverter;

/**
 * @author �����
 * �հ�������..ֻ��Class.from("...");����..
 */
class StringToInt  extends RegiStorOfConverter<String,Integer>{
	 static {
		 //ע��һ��
		 new StringToInt();
	 }
	@Override
	public Integer toConverter(String src) {
		return Integer.parseInt(src);
	}
 }
