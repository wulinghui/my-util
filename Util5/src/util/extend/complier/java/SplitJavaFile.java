package util.extend.complier.java;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import dao.sql.Word;
import util.base._Field;
import util.base._File;
import util.base._Map;
import util.base._String;
import util.base._StringBuffer;
import util.extend.complier.java.ComplierClient.ComplierFile;

public abstract class SplitJavaFile implements ComplierFile<JavaFile>{
	public static Map<String,Field> map;
	static {
		try {   
			map = _Field.getAllFields( Split.class );  
		} catch (SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}  
	}
//	TODO S-oracle-Z_FA_WJ-��ѯ���е�u- -findUser-select * from biz;;;;
	/* TODO S-oracle: ��ѯ���е�u--findUser=>
	 * select * from userz
	 * 	* from userz	* from userz * from userz * from userz
	 * * from userz;
	 */
	//�ڶ��汾...�����������Ų�ͳһ,����Ҫ��ʱ������Ҫת���ַ�.�鷳..   ͳһ�ָ��� - 
	/* TODO S - oracle  ��ѯ���е�u - - findUser -
	 * select * from userz
	 * 	* from userz	* from userz * from userz * from userz
	 * * from userz;
	 *///Ҫ����ı�ʶ
	private final String []  flags ;//���,��������ı�ʶ		"TODO S-",";",";"
	public SplitJavaFile(String... flags) {
		if( flags.length <2 ) throw new IndexOutOfBoundsException();
		this.flags = flags;
	}
	/*TODO �ô���.sb����һ��һ�����,̫�˷�                ����sbһ���ᴩ����,����ȫ
	 * */
	/**
	 * @param file ԭ�ļ�           --���ܴ���
	 * @param sb   ���Բ���	--�ϲ㴦��������,��������ڲ������ԭ�ļ���sb.
	 * @return 
	 */
	public final JavaFile handle(File file,StringBuffer sb) {
		//���һ���ļ�������...һ���ļ�һ������e
		//��ð���������������java����
		String fullClass = _File.absolutePath2ClassName( file.getAbsolutePath() );
		int i = fullClass.lastIndexOf('.'); 
		//TODO ������԰�����İ������������ϱ仯..   ������ͨ��ԭ�ļ���·��.����java�ļ�.,���ܻ��滻Դ�ļ�.
		//Ҳ����˵һ��Biz������һ��Dao...
		JavaFile java = new JavaFile( fullClass.substring(0, i) , fullClass.substring(i+1) );
		
		//����ļ�����������Ҫָ������������..
		Set<String> ss = getSBInner(sb, this.flags );
		for(String str :  ss) {
			handlerJava(str,java);
		}
		return java;
	}
	public void handlerJava(String str , JavaFile java ) {
		//�滻 * ��
		str = str.replaceAll("\\*", "");
//				TODO S - 	   oracle   ��ѯ���е�u -  -findUser - select * from biz;
//		�ָ��	ָ����ָ����ʶ�ַ������ݿ����͡�����ע�� ��    ע�� ���������� 	����������  
		//С����indexException
		String [] splits = _String.trimArrayOfMee( false,str.split("-") );//ǿ������
		try {    	//������SliptJavaFile������  
			Field split =  map.get( splits[1] ) ;
			if( split != null ) ((SplitJavaFile) split.get(null)).handleInner( splits , java );
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	//�����Ŀ��Ը�д...
	public  void handleInner(String [] splits , JavaFile java) {
		//��sql������������ͨ�������ֵ����ת��.	--Ӧ��������sql��ʱ��ת��д.    
		String inner = _String.toFullDataByDictionary( splits[4] , this.initDataDictionary() )/*.toUpperCase()*/;
		java.addImport(Word.class);
		//�����ദ����ӷ���..
		java.addMethod( java.new Method( splits[2] , "" ," String " + splits[3] +"(Word word)",
				//ͨ�������ļ���������.��þ���Ĳ���inner�ķ���,����������,Ϊ��������Ը�д������..
				//Confi.config.complierSplitJavaFiles().get( splits[0] +":"+splits[1] ).
				this.handleInner( inner  )) );
	}
	//���Ǵ���  
	public abstract String handleInner(String inner );
	public  Map<String,String> initDataDictionary(){
		 String [] dictionary = new String[]{
				 "#S","SELECT"//��ѯ
				,"#D","DELETE"//ɾ��
				,"#I","UPDATE"//����
				,"##","*"//����*��,
				
		};
		return _Map.array2Map(dictionary, 2);
	}
	/**ͨ��ָ�����ַ���--���sb���������.*/
	public Set<String> getSBInner(StringBuffer sb,String... h ){
		return _StringBuffer.handleAllAppoint(
				sb,0,new _StringBuffer.GetAppoint()/*new _StringBuffer.FilterAppoint()*/ ,h);
	}
}
