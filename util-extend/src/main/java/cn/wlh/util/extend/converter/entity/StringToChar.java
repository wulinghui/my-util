package cn.wlh.util.extend.converter.entity;

import cn.wlh.util.extend.converter.RegiStorOfConverter;

/**
 * @author �����
 * �հ�������..ֻ��Class.from("...");����..
 */
class StringToChar  extends RegiStorOfConverter<String,Character>{
	 static {
		 //ע��һ��
		 new StringToChar();
	 }
	@Override
	public Character toConverter(String src) {
		return src.charAt(0);
	}
//	public static void main(String[] args) throws ClassNotFoundException {
//	Class.forName("cn.wlh.util.extend.converter.StringToChar");
//		
//	Character executConverter = AbstarctConverter.executConverter("\n", Character.class);
//		System.out.println( executConverter  + "vexdfs");
//		System.out.println( AbstarctConverter.executConverter("��", Character.class)  + "vexdfs");
//	}
 }
