package cn.wlh.util.extend;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


/**
 * @author Administrator
 * ������Ҫ�Ǵ����ӡ��־,
 */
public abstract class Console {
	Console(String flag,String f){
		this.flag = flag;
		this.f = f;
	}
	static Set<String> store = new HashSet<String>();
	final String flag;//�����־�ı�ʶ
	final String f;//�����ʽ
	static  String regex = "\\{}";
	static{
		init("err");
	}
	public static void over(String ... i){
		store.clear();
		for (String e : i) {
			store.add(e);
		}
	}
	public static void init(String... store){
//		for (Object object : store) {
//			String flag = null;
//			
//			Console.store.add(  );
//		}
		Console.store.addAll( Arrays.asList(store) );// store--String��ʱ��
	}
	public static Console getInstance(String flag,String f){
		return new Console(flag,f){};
	}
	public static Console[] getInstances(int... flag){
		int len = flag.length;
		Console cons []= new Console[len];
		String [] s = new String[len];
		for (int i = 0; i < len; i++) {
			String z =String.valueOf(flag[i]);
			cons[i] = new Console(z,flag[i]+"::{f}\n") {};
			store.add(z);//�Ǽ�
		}
		return cons;
	}
	public static Map<String,Console> getInstances(String... flag){
		int len = flag.length;
		Console cons []= new Console[len];
		Map<String,Console> map = new LinkedHashMap<String, Console>();
		for (int i = 0; i < len; i++) {
			map.put(flag[i], new Console(flag[i],flag[i]+"::{f}\n") {});
			store.add(flag[i]);//�Ǽ�
		}
		return map;
	}
	
	public static Console getInstance(String flag){
		return new Console(flag,"{f}\n"){};
	}
	public static Console getInstance(Class<?> flag){return getInstance(flag.getSimpleName());}
	public  Console log(String a,Object... replacement ){
		if(store.contains(this.flag)){
			//��������ӡΪ��ַ����
			for (int i = 0; i < replacement.length; i++) {
				if( replacement[i].getClass().isArray() ){
					int length = Array.getLength( replacement[i] );//����
				    StringBuffer sb = new StringBuffer();
				    sb.append('[');
				    for (int j = 0; j < length; j++) {
				    	sb.append('{');
				        sb.append( Array.get(replacement[i], j)/*����*/);
				        sb.append("},");
				    }
				    sb.deleteCharAt(sb.length()-1);//
				    sb.append(']');
				    replacement[i] = sb;
				}
			}
			String b = null;
			//���log
			int len = replacement.length;
			for (int i = 0; i < len; i++) {
				//��ʹ��foreach
				b = a;
//				System.out.println("a==="+a);
				a = a.replaceFirst( regex , replacement[i].toString());
//				System.out.println("b==="+a);
				//���,ֱ��׷������:
				if(b!=null && b.equals(a)) {
						a += replacement[i].toString();
				}
			}
			System.out.print( handle(a) );
		}
		return this;
	}
	public Console log(Object a){ return log("","",a);	}
	public  String handle(String a){
		return this.f.replace("{f}", a);
	}
}
