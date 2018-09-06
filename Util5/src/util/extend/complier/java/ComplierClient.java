package util.extend.complier.java;

import java.io.File;
import java.io.IOException;

import util.base._File;
import util.base._File.HandlerFile;
import util.base._Reader;
import util.base._Writer;

/**
 * @author wlh
 * 该类是对一个文件的操作
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
	
	final ComplierFileClient<?> [] coms ;//一个文件可以有多种实体类操作,命令模式..
	
	public ComplierClient(ComplierFileClient<?>... coms) {
		if ( coms.length == 0 ) throw new IndexOutOfBoundsException();
		this.coms = coms;
	}
	@Override
	public void handler(File file)  {
		//1.先把文件全部读入jvm中--尽量不要操作sb.--贯穿上下文..
		if( !file.isFile() ) return;
				StringBuffer sb;
				try {
					sb = new StringBuffer().append(
							_Reader.notOutOfMemory(file) );
					for (ComplierFileClient complierFile0 : coms) {
						String fileInner = complierFile0.handle(file, sb);
						//最写入指定文件,拆分多次
						File fi = complierFile0.toTargeFile(file);
						_File.createFile(fi);
						//该处的输出只能对应,一个..当一个文件要拆分成多个不同的文件且写入不同的文件时.
						//这里的一次性写入就不适合了...
						if ( !file.equals( fi ) ) //为了防止替换原文件...
						_Writer.oneWrite( fi , false, fileInner );
					}	
				} catch (IOException e) {
					e.printStackTrace();
				}//转成sb
	}
	//java编译..
	public void destroy() {
		
	}
	/**
	 * 装饰模式 和  职责链模式的区别
	 * @author wlh
	 *
	 * @param <Entity> -- 至少一种后缀结尾一种实体类//TODO 装饰模式
	public static abstract class ComplierFile<Entity>{
		protected String targeOfregex;//权利交给用户
		protected String replacement;//
		protected ComplierFile<Entity> next;
		建议使用装饰模式,因为内部无属性.用他可以透明.无粒度,且一定有一个基本的操作.无需set..对数组友好.
		 自己用new的方式规定了,结尾(new 无参).而职责链以null为结尾..在使用时还得判断多一个对用户的方法.
		这里有了属性,就不单单是增强功能了..如果这样写那么每个零件都要给他里面的属性.反而麻烦. 用抽象的职责链好些
		如果把属性放入方法里面的化用装饰模式要好..
		
		public abstract Entity handle(File file,StringBuffer sb);
		//  输出的位置,可以重写.    
		public  File toTargeFile(File source) {
			return _File.repalceRelativePathOfWork(source, this.targeOfregex, this.replacement);
		}
	}*/
	/*
	//TODO 我们这里采用灵活但复杂的模式,该模式是职责链模式:
	public static abstract  class ComplierFileClient<Entity>{
		protected String targeOfregex;//权利交给用户
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
		//  输出的位置,可以重写.    
		public  File toTargeFile(File source) {
			return _File.repalceRelativePathOfWork(source, this.targeOfregex, this.replacement);
		}
	}
	public static abstract class ComplierFile<Entity>{
		Entity entity;
		ComplierFile<Entity> next;
		public abstract Entity handle(File file,StringBuffer sb);	
	}*/
	//TODO 装饰模式加包装类
	public static  class ComplierFileClient<Entity>{
		protected String targeOfregex;//权利交给用户
		protected String replacement;//
		ComplierFile<Entity> complier;
		Entity entity;
		/**
		 * @param targeOfregex	原相对路径要替换的值,正则
		 * @param replacement	替换的内容
		 * @param complier		处理的类,--装饰模式.
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
		//  输出的位置,可以重写.
		/** 这是替换路径 */
		public  File toTargeFile(File source) {
			return _File.repalceRelativePathOfWork(source, this.targeOfregex, this.replacement);
		}
	}/** 输出的位置:通过java的实体类生成路径*/
	public static class ComplierFileClientOfJava extends ComplierFileClient<JavaFile>{

		public ComplierFileClientOfJava(ComplierFile<JavaFile> complier) {
			super("", "", complier);
		}
		/** 通过java的实体类生成路径*/
		public File toTargeFile(File source) {
			String pack = _File.package2SourcePath("src", this.entity.package0 );
			String fullPath = _File.joinPath(pack , this.entity.className + ".java");
			return new File(fullPath);
		}
	}
	public static interface ComplierFile<Entity>{Entity handle(File file,StringBuffer sb);	}
	public static abstract class ComplierFileOfBase<Entity> implements ComplierFile<Entity>{}
	public static abstract class ComplierFileOfPart<Entity> implements ComplierFile<Entity>{
		ComplierFile<Entity> part;//零件
		public ComplierFileOfPart(ComplierFile<Entity> part) {
			super();
			this.part = part;
		}
		public ComplierFile<Entity> getPart() {
			return part;
		}
	}
	@Deprecated//就把toString写好就行了
	public static interface EntityInter {}
}
