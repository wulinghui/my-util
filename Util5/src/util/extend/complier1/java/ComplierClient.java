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
 * 该类是对一个文件的操作
 * 
 * @author wlh 对比之前的版本:该处的输出只能对应,一个..当一个文件要拆分成多个不同的文件且写入不同的文件时.
 *         我们这里的办法是:先把实体类存储到路径中,之后再遍历写入.
 */
public class ComplierClient implements HandlerFile<IOException> {
	public static final String STORE_PATH = 
			_File.joinPath(  "wlh" , "complier" , "entity"  );
	public static HandlerFile<IOException> writeFile = new HandlerFile<IOException>() {

		@Override
		public void handler(File file) throws IOException {
			if( file.isDirectory() ) return;
			try { // 序列化
				EntityFile entity = _Serializt.deserializeObj(file);
				File f = entity.getStorePath();//获得路径
				_File.createFile(f);//创建文件
				_Writer.oneWrite( f , false, entity.toString() );//写入
				if( entity instanceof JavaFile) {//这里需要编译了
					System.out.println( "entity=" + entity);
					if( _Complier.complier(entity.toString(), ((JavaFile) entity).className) )
						System.out.println( "_Complier.complier === ok ");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	final ComplierFileClient<?>[] coms;// 一个文件可以有多种实体类操作,命令模式..

	public ComplierClient(ComplierFileClient<?>... coms) {
		if (coms.length == 0)
			throw new IndexOutOfBoundsException();
		this.coms = coms;
	}
	public static void main(String[] args) throws IOException, ClassNotFoundException, Throwable, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException {
		//取消缓存.因为源文件写的会重写.只要不减少文件的输出就没影响..
				//但是序列化的内容,是Set,这个缓存是致命..所以我们这里先把文件都删除,再写入
				String forFile = _File.joinPath(_File.WOKE_PATH , STORE_PATH );
		File fi = new File(forFile);
		_File.delete(fi);
		
		ComplierClient com = new ComplierClient(new ComplierFileClient(
				Split.oracle
				) );
		_File.forFile( new File(
				"E:\\workSpace\\Util5\\src\\util\\extend\\complier\\java\\a"
				/*_File.package2SourcePath("util")*/ ) , com);
		
		
		_File.forFile( fi , writeFile);//操作序列化的文件...
		System.out.println( "================= ok ============"  );
//		Class c = Class.forName("util.base.jdk7.ni.ABC");
//		Method me = c.getMethod("findUser", Word.class);
//		System.out.println( me.invoke( c.newInstance(), new Word() ) );
	}
	@Override
	public void handler(File file) throws IOException {
		// 1.先把文件全部读入jvm中--尽量不要操作sb.--贯穿上下文..
		System.out.println("file="+file);
		if (!file.isFile())
			return;
		StringBuffer sb;
		try {
			sb = new StringBuffer().append(_Reader.notOutOfMemory(file));
			for (ComplierFileClient complierFile0 : coms) {
//				String fileInner = null;
				complierFile0.handle(file, sb);
				//存储Bean,交给Entity..
//				complierFile0.storeBean( complierFile0.entity.id );  
			}
		} finally {// 最写入指定文件,拆分多次
			
		}
	}

	// TODO 装饰模式加包装类
	public static class ComplierFileClient<Entity extends EntityFile> {
		ComplierFile<Entity> complier;
//		Entity entity;
		/**
		 * @param targeOfregex
		 *            原相对路径要替换的值,正则
		 * @param replacement
		 *            替换的内容
		 * @param complier
		 *            处理的类,--装饰模式.
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
	/** 基本的里面,第一次一定要序列化 ,这样零件里面就可以反序列化了.*/
	public static interface ComplierFile<Entity extends EntityFile>{Entity handle(File file,StringBuffer sb);	}
	public static abstract class ComplierFileOfPart<Entity extends EntityFile> implements ComplierFile<Entity>{
		ComplierFile<Entity> part;//零件
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
	 * 一个实体类就是一张表,一个id一条数据.
	 */
	@SuppressWarnings("serial")
	public static class EntityFile implements Seri_Clone{
		String storePath;//要写入的路径,写入指定文件路径
		_FileBase<EntityFile> filebase;
		String id;
		protected  EntityFile(String id) {
			this.id = id;
			this.filebase =  new _FileBase(this.getClass().getSimpleName(),
					ComplierClient.STORE_PATH, this.getClass() );
		}
		// TODO 交给子类...
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
			String path = filebase.getAbsolutePath(id);//获得路径
			_File.createFile(new File(path));//创建文件
			filebase.storeBean(this, id);//存储this
		}
	}

}
