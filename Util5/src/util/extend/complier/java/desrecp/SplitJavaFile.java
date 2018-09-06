package util.extend.complier.java.desrecp;

import java.io.File;
import java.util.Map;
import java.util.Set;

import util.base._File;
import util.base._String;
import util.base._StringBuffer;
import util.extend.complier.java.ComplierClient.ComplierFileClient;
import util.extend.complier.java.ComplierClient.ComplierFileOfPart;

public abstract class SplitJavaFile  {
	/* TODO S-oracle: ��ѯ���е�u-findUser=>
	 * select * from userz
	 * 	* from userz	* from userz * from userz * from userz
	 * * from userz;
	 */
	//�ڶ��汾...�����������Ų�ͳһ,����Ҫ��ʱ������Ҫת���ַ�.�鷳..   ͳһ�ָ��� - 
	/* TODO S - oracle - ��ѯ���е�u - findUser -
	 * select * from userz
	 * 	* from userz	* from userz * from userz * from userz
	 * * from userz;
	 *///Ҫ����ı�ʶ
	private final String []  flags;//���,��������ı�ʶ		"TODO S-",";",";"
	public SplitJavaFile(String... flags) {
		super();
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
	public void handle(File file,StringBuffer sb) {
		//���һ���ļ�������...һ���ļ�һ������e
//		StringBuffer sb = new StringBuffer();
		//��ð���������������java����
		String fullClass = _File.absolutePath2ClassName( file.getAbsolutePath() );
		int i = fullClass.lastIndexOf('.'); 
		JavaFile java = new JavaFile( fullClass.substring(0, i) , fullClass.substring(i+1) );
		//����ļ�����������Ҫָ������������..
		for(String str : getSBInner(sb, this.flags ) ) {
			//�滻 * ��
			str = str.replaceAll("\\*", ""); 
//			�ָ��	ָ����ָ����ʶ�ַ������ݿ����͡�����ע�⡢�������� ����������  
			//С����indexException
			String [] splits = _String.trimArrayOfMee( false,str.split(" -") );//ǿ������
			//��sql������������ͨ�������ֵ����ת��.	--Ӧ��������sql��ʱ��ת��д.    
			String inner = _String.toFullDataByDictionary( splits[4] , this.getDataDictionary() )/*.toUpperCase()*/;
			//�����ദ����ӷ���..
			java.addMethod( java.new Method( splits[2] , "" , splits[3],
					//ͨ�������ļ���������.��þ���Ĳ���inner�ķ���,����������,Ϊ��������Ը�д������..
					//Confi.config.complierSplitJavaFiles().get( splits[0] +":"+splits[1] ).
					this.handleInner( inner )));
		}
//		return java;
	}
	public abstract Map<String,String> getDataDictionary();
	//���Ǵ���  
	public abstract String handleInner(String inner);
	/**ͨ��ָ�����ַ���--���sb���������.*/
	public Set<String> getSBInner(StringBuffer sb,String... h ){
		return _StringBuffer.handleAllAppoint(
				sb,0,new _StringBuffer.GetAppoint()/*new _StringBuffer.FilterAppoint()*/ ,h);
	}
}
