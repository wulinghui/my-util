package util.extend.complier1.java;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import util.base._Field;
import util.base._Map;
import util.base._String;
import util.base._StringBuffer;
import util.base._Sysout;
import util.base.jdk7.ni.Class1;
import util.extend.Confi;
import util.extend.complier1.java.ComplierClient.ComplierFile;

public abstract class SplitJavaFile implements ComplierFile<JavaFile>{
	public static Map<String,Field> map;
	static {
		try {   
			map = _Field.getAllFields( Split.class );  
		} catch (SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}  
	}
	
//	StringWrap.Set imports = new StringWrap.Set(); ����inner��������
	/**
	 * ��Щ��ÿ��ѭ������Str���ݴ�����, handlerClass���������ദ���inner��
	 */
	protected String  package0 , className ,handlerClass ,  describe,  annotation,  master,  innner ;
	
	
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
//		String fullClass = _File.absolutePath2ClassName( file.getAbsolutePath() );
//		int i = fullClass.lastIndexOf('.'); 
		//TODO ������԰�����İ������������ϱ仯..   ������ͨ��ԭ�ļ���·��.����java�ļ�.,���ܻ��滻Դ�ļ�.
		//Ҳ����˵һ��Biz������һ��Dao...
//		JavaFile java = new JavaFile( fullClass.substring(0, i) , fullClass.substring(i+1) );
		
		//����ļ�����������Ҫָ������������..
				Set<String> ss = getSBInner(sb, this.flags );
				System.out.println("Set<String> ss="+ss);
				for(String str :  ss) {
					handlerJava(str);
				}
				return null;
	}
	public void handlerJava(String str  ) {
		//�滻 * ��
		str = str.replaceAll("\\*", "");
//				TODO S - 	   oracle - Z_FA_WJ  -  ��ѯ���е�u -  -findUser - select * from biz;
//		�ָ��	ָ����ָ����ʶ�ַ������ݿ����͡�	����		������ע�� ��    ע�� ���������� 	����������  
		//С����indexException
		String [] splits = _String.trimArrayOfMee( false,str.split("-") );//ǿ������
		//������2����,ͳһ���. 
		if( !setSingleFile(splits) ) return;
		
		try {    	//������SliptJavaFile������  
			_Sysout.print0("splits="+splits);  
			JavaFile java = JavaFile.getInstance( this.package0 , this.className );
			Field split =  map.get( this.handlerClass ) ;
			if( split != null ) ((SplitJavaFile) split.get(null)).handleInner( /*splits ,*/ java );
			//���л����ļ���...
			java.storeBean();
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}
	//�����Ŀ��Ը�д...
	public  void handleInner(/*String [] splits ,*/ JavaFile java) {
		//��sql������������ͨ�������ֵ����ת��.	--Ӧ��������sql��ʱ��ת��д.    
		String inner = _String.toFullDataByDictionary( this.innner , this.initDataDictionary() )/*.toUpperCase()*/;
//		java.addImport(Word.class);
		String methodBody = this.handleInner( inner , java );
//		System.out.println("methodBody+"+methodBody);
		//�����ദ����ӷ���..
		java.addMethod( java.new Method( this.describe , this.annotation , this.master ,
				//ͨ�������ļ���������.��þ���Ĳ���inner�ķ���,����������,Ϊ��������Ը�д������..
				//Confi.config.complierSplitJavaFiles().get( splits[0] +":"+splits[1] ).
				methodBody ) );
	}
	/**������2����,ͳһ���.  @return false ������ִ��*/
	protected boolean setSingleFile(String[] splits) {
		if( splits.length < 7  ) {
			System.out.println(" �ָ�Ĳ���׼....");  
			return false;
		}
		
		this.className = splits[2];
		this.handlerClass = splits[1];
		this.innner = splits[6];
		this.describe = splits[3];
		this.master = " String " + splits[5] +"(Word word)" ;
		this.annotation = splits[4];
		
		Package pa = null;
		try {
			pa = Confi.config.getDaoAllMethodByMethodOfPackage().getPackage();
		}catch(Throwable e) {
			pa = Class1.class.getPackage();  
		}
		System.out.println("Package pa ="+pa);
		this.package0 = pa.getName();
		return true;
	}
	//���Ǵ���  
	public abstract String handleInner(String inner , JavaFile java);
	//���������д,�����ֵ�..
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
	/** ���if���������,û�б�ʶ��
	 * 13213(if: a!=b:afd1 : b==c:c=1 )dsada
	 * ����new String[]{ "a!=b" , "afd1" , "b==c" , "c=1" }
	 * */
	public String[] getIfInner(String str) {
		String [] strs = _String.subString( str , 0 , "(if:" ,  ")" ).split(":");
		return _String.trimArrayOfMee(false, strs);
	}
	
}
