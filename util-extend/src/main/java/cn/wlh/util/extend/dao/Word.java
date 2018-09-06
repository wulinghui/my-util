package cn.wlh.util.extend.dao;

import java.util.HashMap;

@SuppressWarnings("serial")
public class Word extends HashMap<String, Object> {
	public static final String SQL = "::sql::";//sql ���
//	public static final String RS = "::isXuykRs::";//�Ƿ���ҪRs�����...   ��update ���� ��ѯ����
	public static final String SQL_INPUT = "::SQL_INPUT::";// ִ��jdbc �� input - set
	public static final String DAO_OBJ = "::DAO_OBJ::"; //��� dao��Ķ���
	// �û�����put,��ϵͳ����...С�Ķ��߳�.
	public static final String DAO_ClASS = "::DAO_ClASS::"; //��� dao����..����Map<String,Method>
	public static final String DAO_METHOD = "::DAO_METHOD::"; //��� dao��ķ���
	public static final String DAO_METHOD_INPUT = "::DAO_METHOD_INPUT::";// ����������
	public static final String VIEW_MODEL = "::VIEW_MODEL::";
	String viewHtml;
	public String getViewHtml() {
		return viewHtml;
	}
	public void setViewHtml(String viewHtml) {
		this.viewHtml = viewHtml;
	}
	 Inner viewMap = null;//new Inner( VIEW_MODEL );
	public Word putInputs(Object ...strings ){
		this.put( SQL_INPUT , strings );return this;
	}
	public Word putSql(String strings ){
		this.put( SQL , strings );return this;      
	}
//	public Word putRs(Boolean strings ){
//		this.put( RS , strings );return this;
//	}//
	public Word putDaoMethod(String strings ){
		this.put( DAO_METHOD , strings );return this;
	}
	public Word putDaoMethodInput(Object strings ){
		this.put( DAO_METHOD_INPUT , strings );return this;
	}
	public Inner getVIEW_MODEL(){
		return (Inner) this.get(VIEW_MODEL);
	}
	//ȫ����̬����...
//	public Word putDao_Object(Object strings ){    
//		this.put( DAO_OBJ , strings );return this;
//	}
	/////////////////////////////
	// ���������ķ���,���.����һ���������ٴ�ʹ�ö��߳�,��ô����ķ������ܾ�������
	// 
	//////////////////////////////
	/**ǰ׺*/
	public class Prefix{
		String suf;
		public Prefix(String pre) {
			this.suf = pre;
		}
		public Prefix put(String key,Object value){
			Word.this.put(suf+key, value);
//			System.out.println("ok");
			return this;
		}
	}
	/**��׺*/     
	public class Suffix{
		String suf;
		public Suffix(String suf) {
			this.suf = suf;
		}
		public Suffix put(String key,Object value){  
			Word.this.put(key + suf , value);
			return this;
		}
	}
	/** �ڲ���Word,������Word��
	 * ��һ��Word����Suffix����Prefixʱ.
	 * Word,һά���������Ӵ�.2������������..
	 * ������Inner����ά��.
	 * */
	public class Inner extends Word{
		Word word;
		public Inner(String key) {
			Word word = new Word();
//			this.word = word;
			// Word���еǼ�
			Word.this.put(key,word);
		}
	}
}
