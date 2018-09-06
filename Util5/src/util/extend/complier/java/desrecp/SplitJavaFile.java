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
	/* TODO S-oracle: 查询所有的u-findUser=>
	 * select * from userz
	 * 	* from userz	* from userz * from userz * from userz
	 * * from userz;
	 */
	//第二版本...上面的特殊符号不统一,当需要的时候又需要转义字符.麻烦..   统一分隔符 - 
	/* TODO S - oracle - 查询所有的u - findUser -
	 * select * from userz
	 * 	* from userz	* from userz * from userz * from userz
	 * * from userz;
	 *///要处理的标识
	private final String []  flags;//获得,下面操作的标识		"TODO S-",";",";"
	public SplitJavaFile(String... flags) {
		super();
		if( flags.length <2 ) throw new IndexOutOfBoundsException();
		this.flags = flags;
	}
	/*TODO 该处用.sb操作一层一层放入,太浪费                还是sb一个贯穿所有,不安全
	 * */
	/**
	 * @param file 原文件           --不能处理
	 * @param sb   可以操作	--上层处理下来的,建议最后在操作拆分原文件的sb.
	 * @return 
	 */
	public void handle(File file,StringBuffer sb) {
		//获得一个文件的内容...一个文件一个对象e
//		StringBuffer sb = new StringBuffer();
		//获得包名和类名并生成java对象
		String fullClass = _File.absolutePath2ClassName( file.getAbsolutePath() );
		int i = fullClass.lastIndexOf('.'); 
		JavaFile java = new JavaFile( fullClass.substring(0, i) , fullClass.substring(i+1) );
		//获得文件里面所有需要指定操作的内容..
		for(String str : getSBInner(sb, this.flags ) ) {
			//替换 * 号
			str = str.replaceAll("\\*", ""); 
//			分割成	指定的指定标识字符、数据库类型、方法注解、方法名、 方法体的语句  
			//小心有indexException
			String [] splits = _String.trimArrayOfMee( false,str.split(" -") );//强力修饰
			//把sql语句里面的内容通过数据字典进行转化.	--应该在生成sql的时候转大写.    
			String inner = _String.toFullDataByDictionary( splits[4] , this.getDataDictionary() )/*.toUpperCase()*/;
			//由子类处理并添加方法..
			java.addMethod( java.new Method( splits[2] , "" , splits[3],
					//通过配置文件交给子类.获得具体的操作inner的方法,不在这里用,为了子类可以复写本方法..
					//Confi.config.complierSplitJavaFiles().get( splits[0] +":"+splits[1] ).
					this.handleInner( inner )));
		}
//		return java;
	}
	public abstract Map<String,String> getDataDictionary();
	//这是处理  
	public abstract String handleInner(String inner);
	/**通过指定的字符串--获得sb里面的内容.*/
	public Set<String> getSBInner(StringBuffer sb,String... h ){
		return _StringBuffer.handleAllAppoint(
				sb,0,new _StringBuffer.GetAppoint()/*new _StringBuffer.FilterAppoint()*/ ,h);
	}
}
