package cn.wlh.util.extend.converter;

import cn.wlh.util.extend.wrap.K1_k2_V_MapOfCache;

/**
 * @author �����
 * �ṩ��ͬ��ת����ͬ��ķ�����
 * ��������ֻ�ṩת��,���������У���ж�...
 */
public abstract class AbstarctConverter {
	private AbstarctConverter(){};
	static K1_k2_V_MapOfCache<Class<?> , Class<?> , RegiStorOfConverter> registorMap = new K1_k2_V_MapOfCache();
	
	public static <T> T executConverter(Object obj , Class<T> tarClass) {
		RegiStorOfConverter regiStorOfConverter = getConverterBean(obj, tarClass);
		if( regiStorOfConverter ==null) return null;
		return (T) regiStorOfConverter.toConverter(obj);
	}
	public static <T> T executConverter(Object obj , Class<T> tarClass , Object ...paramss ) {
		RegiStorOfConverter regiStorOfConverter = getConverterBean(obj, tarClass);
		if( regiStorOfConverter ==null) return null;
		return (T) regiStorOfConverter.toConverter(obj , paramss);
	}
	/*//ʾ��
	public static  class RegiStorOfConverterEx extends RegiStorOfConverter<String,Integer>{
		 static {
			 //ע��һ��
			 new RegiStorOfConverterEx();
		 }
		@Override
		public Integer toConverter(String src) {
			return Integer.parseInt(src);
		}
	 }
	public static void main(String[] args) throws ClassNotFoundException {
		System.out.println(111);
		Class.forName("cn.wlh.util.extend.converter.StringToInt");
		
		Integer executConverter = AbstarctConverter.executConverter("12341", Integer.class);
		System.out.println( executConverter +1 );
		//�������಻����
		Class.forName("cn.wlh.util.extend.converter.AbstarctConverter.RegiStorOfConverterEx");
	}*/

	private static <T> RegiStorOfConverter getConverterBean(Object obj, Class<T> tarClass) {
		if( obj == null ) return null;
		Class<? extends Object> srcClass1 = obj.getClass();
		RegiStorOfConverter regiStorOfConverter = registorMap.get(srcClass1,tarClass );
		return regiStorOfConverter;
	}
}
