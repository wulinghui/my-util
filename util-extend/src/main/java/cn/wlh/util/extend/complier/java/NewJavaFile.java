package cn.wlh.util.extend.complier.java;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import cn.wlh.util.base._File;
import cn.wlh.util.base._Map;
import cn.wlh.util.base._String;
import cn.wlh.util.base._StringBuffer;
import cn.wlh.util.extend.complier.ComplierClient.ComplierFile;
import cn.wlh.util.extend.dao.Word;

/**
 * @author wlh
 * ���»��һ���µ�java�ļ�.�������֮ǰ��java�ļ�.
 */
public  class NewJavaFile implements ComplierFile<JavaFile>{
	//�ڶ��汾...�����������Ų�ͳһ,����Ҫ��ʱ������Ҫת���ַ�.�鷳..   ͳһ�ָ��� - 
	/* TODO S - oracle  ��ѯ���е�u - - findUser -
	 * select * from userz
	 * 	* from userz	* from userz * from userz * from userz
	 * * from userz;
	 *///Ҫ����ı�ʶ
	private final String []  flags ;//���,��������ı�ʶ		"TODO S-",";",";"
	public NewJavaFile(String[] flags) {
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
	File file;
	public final JavaFile handle(File file,StringBuffer sb) {
		//��ֵ
		this.file = file; 
		//���һ���ļ�������...һ���ļ�һ������e
		//����ļ�����������Ҫָ������������..
		Set<String> ss = getSBInner(sb, this.flags );
		for(String str :  ss) {
			System.out.println( "getSBInner=" +  str);
			try {
				handlerJava(str);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	public void handlerJava(String str  ) throws IOException {
		//�滻 * ��
		str = str.replaceAll("\\*", "");
//				TODO S - 	   oracle   ��ѯ���е�u -  -findUser - select * from biz;
//		�ָ��	ָ����ָ����ʶ�ַ������ݿ����͡�����ע�� ��    ע�� ���������� 	����������  
		//С����indexException
		String [] splits = _String.trimArrayOfMee( false,str.split("-") );//ǿ������
		//�Ǽ�.
		array2InnerField(splits);
		handleInner(   );
	}
	//�����Ŀ��Ը�д...
	protected String method_describe = "";//ע��
	protected String method_annotation= "";//
	protected String method_master= "";//����.������
	protected String method_innner= "";//����.
	public  void handleInner(  ) throws IOException {
		
		//��sql������������ͨ�������ֵ����ת��.	--Ӧ��������sql��ʱ��ת��д.    
		String inner = _String.toFullDataByDictionary( this.method_innner , this.initDataDictionary() )/*.toUpperCase()*/;
		System.out.println( "splits[4]="+ this.method_innner );
		System.out.println( "String inner=" + inner );
		//�������ֵ����֮���new
		JavaFile java = toJavaFile();
		java.addImport(Word.class); 
		//�����ദ����ӷ���..
		java.addMethod( java.new Method( this.method_describe , "" ," String " + this.method_master +"(Word word)",
				//ͨ�������ļ���������.��þ���Ĳ���inner�ķ���,����������,Ϊ��������Ը�д������..
				//Confi.config.complierSplitJavaFiles().get( splits[0] +":"+splits[1] ).
				this.handleInner( inner  )) );
		String s = "C:\\Users\\wlh\\eclipse-workspace\\util\\util-extend\\src\\main\\java\\\\util\\extend\\complier\\java\\a\\\\Test0.java";
		System.out.println( s +"==="+ new File(s).exists() );
		System.out.println( "java.addMethod"  + java );
		//ÿ�δ���������л�,���� 
		java.storeBean();
	}
	
	//���Ǵ���  
	public String handleInner(String inner ){
		return "return " + _String.toStringFormatOfTwo(inner) + ";";
	}
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
	
	/** ת������.������ʹ��.ͳһ���	 */
	protected void array2InnerField(String [] spilts) {
		 method_describe = spilts[3];//ע��
		 method_annotation= "";//
		 method_master= spilts[5];//����.������
		 method_innner= spilts[6];//����.
	}
	protected JavaFile toJavaFile() {
		//��ð���������������java����
//		String fullClass = _File.absolutePath2ClassName( file.getAbsolutePath() );
		String fullClass = _File.absolutePath2ClassNameOfSrc( file.getAbsolutePath() );
		int i = fullClass.lastIndexOf('.'); 
		//TODO ������԰�����İ������������ϱ仯..   ������ͨ��ԭ�ļ���·��.����java�ļ�.,���ܻ��滻Դ�ļ�.
		//Ҳ����˵һ��Biz������һ��Dao...
		System.out.println( " fullClass=="+ fullClass );
		System.out.println( "fullClass.substring(0, i)=="+ fullClass.substring(0, i) );
		System.out.println( "fullClass.substring(i+1)=="+ fullClass.substring(i+1) );
		JavaFile java = JavaFile.getInstance( fullClass.substring(0, i) , fullClass.substring(i+1) +"00" );
		System.out.println( "JavaFile=" + java );
		return java;
	}
}
