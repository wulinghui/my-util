package cn.wlh.util.extend.converter.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.wlh.util.extend.converter.RegiStorOfConverter;

/**
 * @author 吴灵辉
 * 闭包不可用..只能Class.from("...");加载..
 */
class DateToString  extends RegiStorOfConverter<Date,String>{
	 static {
		 //注册一次
		 new DateToString();
	 }
	@Override
	public String toConverter(Date src) {
		return toConverter(src,null);
	}

	@Override
	public String toConverter(Date obj, Object... objects) {
		SimpleDateFormat simpleDateFormat = StringToDate.getSimpleDateFormat(objects);
		return simpleDateFormat.format(obj);
	}
	
 }
