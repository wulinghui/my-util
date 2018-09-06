package util.extend.complier.java.desrecp;

import java.io.File;
import java.io.IOException;

import util.base._Reader;
import util.base._Writer;
import util.base._File;
import util.base._File.HandlerFile;

/**
 * @author wlh
 * * 编译文件的入口
 * @param <Entity> -- 一种后缀结尾一种实体类
//2.	可以有多种实体类操作   -- 上移,该类对应一个实体类
		
//2.1 里面同类的操作可以随意替换..排序...
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
		//1.先把文件全部读入jvm中--尽量不要操作sb.--贯穿上下文..
		StringBuffer sb = new StringBuffer().append(
				_Reader.notOutOfMemory(file) );//转成sb
		//
		Object t = null;
		
		for (ComplierFile complierFile : comm) {
			do {
				t = complierFile.handle(file, sb,t);
				complierFile = this.next;
			}while( next ==null);//职责链.
			
		}
		//最写入指定文件
		_Writer.oneWrite( toTargeFile(file) , false, sb);
	}
	public abstract  Object handle(File file,StringBuffer sb , Object  t);
	/** 输出的位置,可以重写.  */  
	public  File toTargeFile(File source) {
		return _File.repalceRelativePathOfWork(source, this.targeOfregex, this.replacement);
	}
}
