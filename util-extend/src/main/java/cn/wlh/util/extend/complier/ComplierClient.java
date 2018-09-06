package cn.wlh.util.extend.complier;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import cn.wlh.util.base.Print;
import cn.wlh.util.base._Complier;
import cn.wlh.util.base._File;
import cn.wlh.util.base._File.HandlerFile;
import cn.wlh.util.base._Reader;
import cn.wlh.util.base._Serializt;
import cn.wlh.util.base._Writer;
import cn.wlh.util.base.interfaces.Seri_Clone;
import cn.wlh.util.extend.Conf;
import cn.wlh.util.extend.complier.java.JavaFile;
import cn.wlh.util.extend.complier.java.NewJavaFile;
import cn.wlh.util.extend.database.file._FileBase;

/**
 * �����Ƕ�һ���ļ��Ĳ���
 * 
 * @author wlh �Ա�֮ǰ�İ汾:�ô������ֻ�ܶ�Ӧ,һ��..��һ���ļ�Ҫ��ֳɶ����ͬ���ļ���д�벻ͬ���ļ�ʱ.
 *         ��������İ취��:�Ȱ�ʵ����洢��·����,֮���ٱ���д��.
 */
public abstract class  ComplierClient implements HandlerFile<IOException> {
	static Print log = Print.newInstance( ComplierClient.class );
	
	public static void main(String[] args) throws ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException, IOException, Throwable {
		main( new File("C:\\Users\\wlh\\eclipse-workspace\\util\\util-extend\\src\\main\\java\\util\\extend\\complier\\java\\a") ,
			new ComplierFileWrap[]{
			new ComplierFileWrap<JavaFile>( 
					new NewJavaFile(new String[]{"TODO S-",";",";"}) 
					)
		});
	}
	
	public static void main( File complierDirectory , ComplierFileWrap<?>[] coms) throws IOException, ClassNotFoundException, Throwable, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException {
		if( !complierDirectory.isDirectory() || coms.length == 0 ) return;
		//ȡ������.��ΪԴ�ļ�д�Ļ���д.ֻҪ�������ļ��������ûӰ��..
		//�������л�������,��Set,�������������..�������������Ȱ��ļ���ɾ��,��д��
		String forFile = _File.joinPath(_File.WOKE_PATH , STORE_PATH );
		File fi = new File(forFile);
		_File.delete(fi);
		
		_File.forFile( complierDirectory  , new ComplierClient( coms ){} );
		
		_File.forFile( fi , writeFile);//�������л����ļ�...
		log.log("================= ok ============");
	}
	
	
	
	
	
 static final String STORE_PATH = Conf.config.complierClient_STORE_PATH();
 /** д��ָ�����ļ���*/
	static HandlerFile<IOException> writeFile = new HandlerFile<IOException>() {
		@Override
		public void handler(File file) throws IOException {
			if (file.isDirectory())
				return;
			try { // ���л�
				EntityFile entity = _Serializt.deserializeObj(file);
				System.out.println("entity=" + entity);
				File f = entity.getStorePath();// ���·��
				_File.createFile(f);// �����ļ�
				_Writer.oneWrite(f, false, entity.toString());// д��
				// ������Ҫ������
				if (entity instanceof JavaFile) { 
					if (_Complier.complier(entity.toString(), ((JavaFile) entity).className))
						System.out.println("_Complier.complier === ok ");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	final ComplierFileWrap<?>[] coms;// һ���ļ������ж���ʵ�������,����ģʽ..

	 ComplierClient(ComplierFileWrap<?>[] coms) {
		this.coms = coms;
	}

	@Override
	public void handler(File file) throws IOException {
		// 1.�Ȱ��ļ�ȫ������jvm��--������Ҫ����sb.--�ᴩ������..
				System.out.println("file="+file);
				if (!file.isFile()) {
					System.out.println( "!file.isFile()" + !file.isFile() );
					return;
				}
				StringBuffer sb;
					sb = new StringBuffer().append(_Reader.notOutOfMemory(file));
					for (ComplierFileWrap complierFile0 : coms) {
						complierFile0.handle(file, sb);
					}// ��д��ָ���ļ�,��ֶ��. ���ദ��.
				
	}

	// TODO װ��ģʽ�Ӱ�װ��
	public static class ComplierFileWrap<Entity extends EntityFile> {
		ComplierFile<Entity> complier;
		/**
		 * @param complier
		 *            �������,--װ��ģʽ.
		 */
		public ComplierFileWrap(ComplierFile<Entity> complier) {
			this.complier = complier;
		}

		public void handle(File file, StringBuffer sb) {
			complier.handle(file, sb);
		}
	}

	/** ����������,��һ��һ��Ҫ���л� ,�����������Ϳ��Է����л���. */
	public static interface ComplierFile<Entity extends EntityFile> {
		Entity handle(File file, StringBuffer sb);
	}

	public static abstract class ComplierFileOfPart<Entity extends EntityFile> implements ComplierFile<Entity> {
		ComplierFile<Entity> part;// ���

		public ComplierFileOfPart(ComplierFile<Entity> part) {
			super();
			this.part = part;
		}

		public ComplierFile<Entity> getPart() {
			return part;
		}
	}

	/**
	 * @author wlh һ��ʵ�������һ�ű�,һ��idһ������.
	 */
	@SuppressWarnings("serial")
	public static class EntityFile implements Seri_Clone {
		String storePath;// Ҫд���·��,д��ָ���ļ�·��
		_FileBase<EntityFile> filebase; // ���л���·��.   ���ݿ�.
		String id;

		protected EntityFile(String id, String storePath) {
			this.id = id;
			this.storePath = storePath;
			this.filebase = new _FileBase(this.getClass().getSimpleName(), ComplierClient.STORE_PATH , this.getClass()); 
		}

		public File getStorePath() {
			return new File(storePath);
		}

		public EntityFile getBean() throws Exception {
			return filebase.getBean(id);
		};

		public void storeBean() throws IOException {
			String path = filebase.getAbsolutePath(id);// ���·��
			_File.createFile(new File(path));// �����ļ�
			filebase.storeBean(this, id);// �洢this
		}
	}
}
