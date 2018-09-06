package util.extend.complier.java.desrecp;

import java.io.File;
import java.io.IOException;

import util.base._Reader;
import util.base._Writer;
import util.base._File;
import util.base._File.HandlerFile;

/**
 * @author wlh
 * * �����ļ������
 * @param <Entity> -- һ�ֺ�׺��βһ��ʵ����
//2.	�����ж���ʵ�������   -- ����,�����Ӧһ��ʵ����
		
//2.1 ����ͬ��Ĳ������������滻..����...
 */
public abstract  class ComplierFile implements HandlerFile<IOException>{
	String targeOfregex;
	String replacement;
	ComplierFile comm [] = new ComplierFile [1] ;
	ComplierFile next;
	public ComplierFile(String targeOfregex, String replacement) {
		this.targeOfregex = targeOfregex;
		this.replacement = replacement;
	}
	@Override
	public final  void handler(final File file) throws IOException {
		//1.�Ȱ��ļ�ȫ������jvm��--������Ҫ����sb.--�ᴩ������..
		StringBuffer sb = new StringBuffer().append(
				_Reader.notOutOfMemory(file) );//ת��sb
		//
		Object t = null;
		
		for (ComplierFile complierFile : comm) {
			do {
				t = complierFile.handle(file, sb,t);
				complierFile = this.next;
			}while( next ==null);//ְ����.
			
		}
		//��д��ָ���ļ�
		_Writer.oneWrite( toTargeFile(file) , false, sb);
	}
	public abstract  Object handle(File file,StringBuffer sb , Object  t);
	/** �����λ��,������д.  */  
	public  File toTargeFile(File source) {
		return _File.repalceRelativePathOfWork(source, this.targeOfregex, this.replacement);
	}
}
