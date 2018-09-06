package util.extend.complier1.java;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dao.sql.Word;
import util.base.Config;
import util.base._Complier;
import util.base._File;
import util.base._File.HandlerFile;
import util.base._Reader;
import util.base._Serializt;
import util.base._Writer;
import util.base.interfaces.Seri_Clone;
import util.extend.database1.file._FileBase;

/**
 * �����Ƕ�һ���ļ��Ĳ���
 * 
 * @author wlh �Ա�֮ǰ�İ汾:�ô������ֻ�ܶ�Ӧ,һ��..��һ���ļ�Ҫ��ֳɶ����ͬ���ļ���д�벻ͬ���ļ�ʱ.
 *         ��������İ취��:�Ȱ�ʵ����洢��·����,֮���ٱ���д��.
 */
public class ComplierClient implements HandlerFile<IOException> {
	public static final String STORE_PATH = 
			_File.joinPath(  "wlh" , "complier" , "entity"  );
	public static HandlerFile<IOException> writeFile = new HandlerFile<IOException>() {

		@Override
		public void handler(File file) throws IOException {
			if( file.isDirectory() ) return;
			try { // ���л�
				EntityFile entity = _Serializt.deserializeObj(file);
				File f = entity.getStorePath();//���·��
				_File.createFile(f);//�����ļ�
				_Writer.oneWrite( f , false, entity.toString() );//д��
				if( entity instanceof JavaFile) {//������Ҫ������
					System.out.println( "entity=" + entity);
					if( _Complier.complier(entity.toString(), ((JavaFile) entity).className) )
						System.out.println( "_Complier.complier === ok ");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	final ComplierFileClient<?>[] coms;// һ���ļ������ж���ʵ�������,����ģʽ..

	public ComplierClient(ComplierFileClient<?>... coms) {
		if (coms.length == 0)
			throw new IndexOutOfBoundsException();
		this.coms = coms;
	}
	public static void main(String[] args) throws IOException, ClassNotFoundException, Throwable, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException {
		//ȡ������.��ΪԴ�ļ�д�Ļ���д.ֻҪ�������ļ��������ûӰ��..
				//�������л�������,��Set,�������������..�������������Ȱ��ļ���ɾ��,��д��
				String forFile = _File.joinPath(_File.WOKE_PATH , STORE_PATH );
		File fi = new File(forFile);
		_File.delete(fi);
		
		ComplierClient com = new ComplierClient(new ComplierFileClient(
				Split.oracle
				) );
		_File.forFile( new File(
				"E:\\workSpace\\Util5\\src\\util\\extend\\complier\\java\\a"
				/*_File.package2SourcePath("util")*/ ) , com);
		
		
		_File.forFile( fi , writeFile);//�������л����ļ�...
		System.out.println( "================= ok ============"  );
//		Class c = Class.forName("util.base.jdk7.ni.ABC");
//		Method me = c.getMethod("findUser", Word.class);
//		System.out.println( me.invoke( c.newInstance(), new Word() ) );
	}
	@Override
	public void handler(File file) throws IOException {
		// 1.�Ȱ��ļ�ȫ������jvm��--������Ҫ����sb.--�ᴩ������..
		System.out.println("file="+file);
		if (!file.isFile())
			return;
		StringBuffer sb;
		try {
			sb = new StringBuffer().append(_Reader.notOutOfMemory(file));
			for (ComplierFileClient complierFile0 : coms) {
//				String fileInner = null;
				complierFile0.handle(file, sb);
				//�洢Bean,����Entity..
//				complierFile0.storeBean( complierFile0.entity.id );  
			}
		} finally {// ��д��ָ���ļ�,��ֶ��
			
		}
	}

	// TODO װ��ģʽ�Ӱ�װ��
	public static class ComplierFileClient<Entity extends EntityFile> {
		ComplierFile<Entity> complier;
//		Entity entity;
		/**
		 * @param targeOfregex
		 *            ԭ���·��Ҫ�滻��ֵ,����
		 * @param replacement
		 *            �滻������
		 * @param complier
		 *            �������,--װ��ģʽ.
		 */
		public ComplierFileClient(ComplierFile<Entity> complier) {
			this.complier = complier;
		}

		public void handle(File file, StringBuffer sb) {
//			entity = null; 
					complier.handle(file, sb);
//			entity = entity == null ?  (Entity) new EntityFile(""): entity;
//			filebase =  new _FileBase(entity.getClass().getSimpleName(),
//						ComplierClient.STORE_PATH, entity.getClass() );
//			return this.entity.toString();
//			return entity != null ? entity.toString() : "null";
		}
		
		
	}
	/** ����������,��һ��һ��Ҫ���л� ,�����������Ϳ��Է����л���.*/
	public static interface ComplierFile<Entity extends EntityFile>{Entity handle(File file,StringBuffer sb);	}
	public static abstract class ComplierFileOfPart<Entity extends EntityFile> implements ComplierFile<Entity>{
		ComplierFile<Entity> part;//���
		public ComplierFileOfPart(ComplierFile<Entity> part) {
			super();
			this.part = part;
		}
		public ComplierFile<Entity> getPart() {
			return part;
		}
	}
	/**
	 * @author wlh
	 * һ��ʵ�������һ�ű�,һ��idһ������.
	 */
	@SuppressWarnings("serial")
	public static class EntityFile implements Seri_Clone{
		String storePath;//Ҫд���·��,д��ָ���ļ�·��
		_FileBase<EntityFile> filebase;
		String id;
		protected  EntityFile(String id) {
			this.id = id;
			this.filebase =  new _FileBase(this.getClass().getSimpleName(),
					ComplierClient.STORE_PATH, this.getClass() );
		}
		// TODO ��������...
//		public static <T extends EntityFile>  T getInstance(String id) {
//			
//		}
		public File getStorePath() {
			return new File(storePath);
		}
		public void setStorePath(String storePath) {
			this.storePath = storePath;
		}
		public EntityFile  getBean() throws  Exception  { return filebase.getBean(id);};
		public void storeBean() throws IOException {
			String path = filebase.getAbsolutePath(id);//���·��
			_File.createFile(new File(path));//�����ļ�
			filebase.storeBean(this, id);//�洢this
		}
	}

}
