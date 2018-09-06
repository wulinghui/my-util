package cn.wlh.util.extend.converter.entity;

import cn.wlh.util.extend.converter.RegiStorOfConverter;

/**
 * @author 吴灵辉
 * 闭包不可用..只能Class.from("...");加载..
 */
class StringToByte  extends RegiStorOfConverter<String,Byte>{
	 static {
		 //注册一次
		 new StringToByte();
	 }
	@Override
	public Byte toConverter(String src) {
		return Byte.parseByte(src);
	}
 }
