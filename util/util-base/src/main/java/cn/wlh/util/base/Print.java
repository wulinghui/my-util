package cn.wlh.util.base;

import java.lang.reflect.Array;



public abstract class Print {
//	static Print m = new Print() {
//		@Override
//		protected void print(String a) {       
//			System.out.print( a );  
//	}};
	static  String regex = "[{}]";
	static int flag = 0 ; 
	
	Class<?> c; // ������һ���ڲ�����־,sysout�ò���.
	
	
	public static  Print  m = new Print() {
		protected void print(int greade, String a) {
			System.out.println( a );
		}
	};
	public static  Print newInstance(Class<?> c) {
		return m; // 
	}
	
	public  Print log(String a , Object... replacement ){
		return log(-1,a, replacement);
	}
	public  Print log(int greade , String a , Object... replacement ){
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
				 // �� sb ���� ����.
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
				a = a.replaceFirst( regex , replacement[i].toString() );
//				System.out.println("b==="+a);
				//���,ֱ��׷������:
				if(b!=null && b.equals(a)) {
						a += replacement[i].toString();
				}
			}
			//TODO �Զ��嶨��������ʽ.
			print(greade , a);
		return (Print) this;
	}
	
	protected abstract void print( int greade , String a);
}
