package util.extend.complier.java;

import java.io.File;
import java.io.IOException;

import util.base._File;
import util.base._File.HandlerFile;
import util.base._Reader;
import util.base._Writer;

/**
 * @author wlh
 * �����Ƕ�һ���ļ��Ĳ���
 */
public class ComplierClient implements HandlerFile<IOException>{
	
	public static void main(String[] args) throws Throwable {
		ComplierClient cc = new ComplierClient( 
				 new ComplierFileClient<JavaFile>("src","src1",Split.wlh_dao)
				,new ComplierFileClientOfJava(Split.wlh_dao)
				);
		_File.forFile( new File(
				"E:\\workSpace\\Util5\\src\\util\\extend\\complier\\java"
				/*_File.package2SourcePath("util")*/ ) , cc );
	}
	
	final ComplierFileClient<?> [] coms ;//һ���ļ������ж���ʵ�������,����ģʽ..
	
	public ComplierClient(ComplierFileClient<?>... coms) {
		if ( coms.length == 0 ) throw new IndexOutOfBoundsException();
		this.coms = coms;
	}
	@Override
	public void handler(File file)  {
		//1.�Ȱ��ļ�ȫ������jvm��--������Ҫ����sb.--�ᴩ������..
		if( !file.isFile() ) return;
				StringBuffer sb;
				try {
					sb = new StringBuffer().append(
							_Reader.notOutOfMemory(file) );
					for (ComplierFileClient complierFile0 : coms) {
						String fileInner = complierFile0.handle(file, sb);
						//��д��ָ���ļ�,��ֶ��
						File fi = complierFile0.toTargeFile(file);
						_File.createFile(fi);
						//�ô������ֻ�ܶ�Ӧ,һ��..��һ���ļ�Ҫ��ֳɶ����ͬ���ļ���д�벻ͬ���ļ�ʱ.
						//�����һ����д��Ͳ��ʺ���...
						if ( !file.equals( fi ) ) //Ϊ�˷�ֹ�滻ԭ�ļ�...
						_Writer.oneWrite( fi , false, fileInner );
					}	
				} catch (IOException e) {
					e.printStackTrace();
				}//ת��sb
	}
	//java����..
	public void destroy() {
		
	}
	/**
	 * װ��ģʽ ��  ְ����ģʽ������
	 * @author wlh
	 *
	 * @param <Entity> -- ����һ�ֺ�׺��βһ��ʵ����//TODO װ��ģʽ
	public static abstract class ComplierFile<Entity>{
		protected String targeOfregex;//Ȩ�������û�
		protected String replacement;//
		protected ComplierFile<Entity> next;
		����ʹ��װ��ģʽ,��Ϊ�ڲ�������.��������͸��.������,��һ����һ�������Ĳ���.����set..�������Ѻ�.
		 �Լ���new�ķ�ʽ�涨��,��β(new �޲�).��ְ������nullΪ��β..��ʹ��ʱ�����ж϶�һ�����û��ķ���.
		������������,�Ͳ���������ǿ������..�������д��ôÿ�������Ҫ�������������.�����鷳. �ó����ְ������Щ
		��������Է��뷽������Ļ���װ��ģʽҪ��..
		
		public abstract Entity handle(File file,StringBuffer sb);
		//  �����λ��,������д.    
		public  File toTargeFile(File source) {
			return _File.repalceRelativePathOfWork(source, this.targeOfregex, this.replacement);
		}
	}*/
	/*
	//TODO ����������������ӵ�ģʽ,��ģʽ��ְ����ģʽ:
	public static abstract  class ComplierFileClient<Entity>{
		protected String targeOfregex;//Ȩ�������û�
		protected String replacement;//
		ComplierFile<Entity> next;
		public ComplierFileClient(String targeOfregex, String replacement, ComplierFile<Entity> next) {
			if( next == null ) throw new NullPointerException();
			this.targeOfregex = targeOfregex;
			this.replacement = replacement;
			this.next = next;
		}
		public  String handle(File file,StringBuffer sb) {
			Entity entity = null ;
			ComplierFile<Entity> com = this.next;
			do{
				entity = com.handle(file, sb);
				com = com.next;
				com.entity = entity;
			}while( com != null );
			return entity !=null ? entity.toString() : null; 
		}
		//  �����λ��,������д.    
		public  File toTargeFile(File source) {
			return _File.repalceRelativePathOfWork(source, this.targeOfregex, this.replacement);
		}
	}
	public static abstract class ComplierFile<Entity>{
		Entity entity;
		ComplierFile<Entity> next;
		public abstract Entity handle(File file,StringBuffer sb);	
	}*/
	//TODO װ��ģʽ�Ӱ�װ��
	public static  class ComplierFileClient<Entity>{
		protected String targeOfregex;//Ȩ�������û�
		protected String replacement;//
		ComplierFile<Entity> complier;
		Entity entity;
		/**
		 * @param targeOfregex	ԭ���·��Ҫ�滻��ֵ,����
		 * @param replacement	�滻������
		 * @param complier		�������,--װ��ģʽ.
		 */
		public ComplierFileClient(String targeOfregex, String replacement, ComplierFile<Entity> complier) {
			this.targeOfregex = targeOfregex;
			this.replacement = replacement;
			this.complier = complier;
		}
		
		public  String handle(File file,StringBuffer sb) {
			entity = complier.handle(file, sb);
			return entity !=null ? entity.toString() : "null"; 
		}
		//  �����λ��,������д.
		/** �����滻·�� */
		public  File toTargeFile(File source) {
			return _File.repalceRelativePathOfWork(source, this.targeOfregex, this.replacement);
		}
	}/** �����λ��:ͨ��java��ʵ��������·��*/
	public static class ComplierFileClientOfJava extends ComplierFileClient<JavaFile>{

		public ComplierFileClientOfJava(ComplierFile<JavaFile> complier) {
			super("", "", complier);
		}
		/** ͨ��java��ʵ��������·��*/
		public File toTargeFile(File source) {
			String pack = _File.package2SourcePath("src", this.entity.package0 );
			String fullPath = _File.joinPath(pack , this.entity.className + ".java");
			return new File(fullPath);
		}
	}
	public static interface ComplierFile<Entity>{Entity handle(File file,StringBuffer sb);	}
	public static abstract class ComplierFileOfBase<Entity> implements ComplierFile<Entity>{}
	public static abstract class ComplierFileOfPart<Entity> implements ComplierFile<Entity>{
		ComplierFile<Entity> part;//���
		public ComplierFileOfPart(ComplierFile<Entity> part) {
			super();
			this.part = part;
		}
		public ComplierFile<Entity> getPart() {
			return part;
		}
	}
	@Deprecated//�Ͱ�toStringд�þ�����
	public static interface EntityInter {}
}
