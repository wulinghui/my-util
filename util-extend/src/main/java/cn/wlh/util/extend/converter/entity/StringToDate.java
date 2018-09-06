package cn.wlh.util.extend.converter.entity;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import cn.wlh.util.base.JavaUtilFactory;
import cn.wlh.util.extend.converter.RegiStorOfConverter;

/**
 * @author �����
 * �հ�������..ֻ��Class.from("...");����..
 */
class StringToDate  extends RegiStorOfConverter<String,Date>{
	private  static Map<String,SimpleDateFormat> cache = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_METHOD);
	
	static {
		 //ע��һ��
		 new StringToDate();
	}
	@Override
	public Date toConverter(String src) {
		return toConverter(src,"");
	}
	@Override
	public Date toConverter(String obj, Object... objects) {
		SimpleDateFormat sdf = getSimpleDateFormat(objects);
		try {
			return sdf.parse(obj);
		} catch (ParseException e) {
			throw new RuntimeException( e.getMessage() );
		}
	}
	public static  SimpleDateFormat getSimpleDateFormat(Object... objects) {
		int i = objects.length;
		String str = (String) objects[0];
		if( str == null ) {
			str = "" ; // new SimpleDateFormat();
		}
		if( i != 2 ) {
			SimpleDateFormat simpleDateFormat = cache.get(str);
			if( simpleDateFormat == null) {
				simpleDateFormat = new SimpleDateFormat(str);
				cache.put(str, simpleDateFormat);
			}
			return simpleDateFormat;
		}else {
			// ������..
			Object twoPar = objects[1];
			if( twoPar instanceof  DateFormatSymbols ) {
				return  new SimpleDateFormat(str,(DateFormatSymbols) twoPar);
			}else if( twoPar instanceof Locale){
				return  new SimpleDateFormat(str, (Locale) twoPar);
			}else {
				return  new SimpleDateFormat();
			}
		}
		
	}
 }
