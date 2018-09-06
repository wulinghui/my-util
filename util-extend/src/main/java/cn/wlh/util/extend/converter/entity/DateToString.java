package cn.wlh.util.extend.converter.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.wlh.util.extend.converter.RegiStorOfConverter;

/**
 * @author �����
 * �հ�������..ֻ��Class.from("...");����..
 */
class DateToString  extends RegiStorOfConverter<Date,String>{
	 static {
		 //ע��һ��
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
