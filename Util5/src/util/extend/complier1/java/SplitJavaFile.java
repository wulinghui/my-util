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
	
//	StringWrap.Set imports = new StringWrap.Set(); 是在inner里做的事
	/**
	 * 这些是每次循环处理Str的暂存容器, handlerClass具体是子类处理的inner的
	 */
	protected String  package0 , className ,handlerClass ,  describe,  annotation,  master,  innner ;
	
	
	/* TODO S-oracle: 查询所有的u--findUser=>
	 * select * from userz
	 * 	* from userz	* from userz * from userz * from userz
	 * * from userz;
	 */
	//第二版本...上面的特殊符号不统一,当需要的时候又需要转义字符.麻烦..   统一分隔符 - 
	/* TODO S - oracle  查询所有的u - - findUser -
	 * select * from userz
	 * 	* from userz	* from userz * from userz * from userz
	 * * from userz;
	 *///要处理的标识
	private final String []  flags ;//获得,下面操作的标识		"TODO S-",";",";"
	public SplitJavaFile(String... flags) {
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
	public final JavaFile handle(File file,StringBuffer sb) {
		//获得一个文件的内容...一个文件一个对象e
		//获得包名和类名并生成java对象
//		String fullClass = _File.absolutePath2ClassName( file.getAbsolutePath() );
//		int i = fullClass.lastIndexOf('.'); 
		//TODO 子类可以把里面的包名或类名不断变化..   该类是通过原文件的路径.生成java文件.,可能会替换源文件.
		//也就是说一个Biz就生成一个Dao...
//		JavaFile java = new JavaFile( fullClass.substring(0, i) , fullClass.substring(i+1) );
		
		//获得文件里面所有需要指定操作的内容..
				Set<String> ss = getSBInner(sb, this.flags );
				System.out.println("Set<String> ss="+ss);
				for(String str :  ss) {
					handlerJava(str);
				}
				return null;
	}
	public void handlerJava(String str  ) {
		//替换 * 号
		str = str.replaceAll("\\*", "");
//				TODO S - 	   oracle - Z_FA_WJ  -  查询所有的u -  -findUser - select * from biz;
//		分割成	指定的指定标识字符、数据库类型、	类名		、方法注解 、    注解 、方法名、 	方法体的语句  
		//小心有indexException
		String [] splits = _String.trimArrayOfMee( false,str.split("-") );//强力修饰
		//把数组2属性,统一入口. 
		if( !setSingleFile(splits) ) return;
		
		try {    	//反射获得SliptJavaFile的子类  
			_Sysout.print0("splits="+splits);  
			JavaFile java = JavaFile.getInstance( this.package0 , this.className );
			Field split =  map.get( this.handlerClass ) ;
			if( split != null ) ((SplitJavaFile) split.get(null)).handleInner( /*splits ,*/ java );
			//序列化到文件中...
			java.storeBean();
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}
	//其他的可以复写...
	public  void handleInner(/*String [] splits ,*/ JavaFile java) {
		//把sql语句里面的内容通过数据字典进行转化.	--应该在生成sql的时候转大写.    
		String inner = _String.toFullDataByDictionary( this.innner , this.initDataDictionary() )/*.toUpperCase()*/;
//		java.addImport(Word.class);
		String methodBody = this.handleInner( inner , java );
//		System.out.println("methodBody+"+methodBody);
		//由子类处理并添加方法..
		java.addMethod( java.new Method( this.describe , this.annotation , this.master ,
				//通过配置文件交给子类.获得具体的操作inner的方法,不在这里用,为了子类可以复写本方法..
				//Confi.config.complierSplitJavaFiles().get( splits[0] +":"+splits[1] ).
				methodBody ) );
	}
	/**把数组2属性,统一入口.  @return false 不往下执行*/
	protected boolean setSingleFile(String[] splits) {
		if( splits.length < 7  ) {
			System.out.println(" 分割的不标准....");  
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
	//这是处理  
	public abstract String handleInner(String inner , JavaFile java);
	//子类可以重写,数据字典..
	public  Map<String,String> initDataDictionary(){
		 String [] dictionary = new String[]{
				 "#S","SELECT"//查询
				,"#D","DELETE"//删除
				,"#I","UPDATE"//更新
				,"##","*"//代替*号,
				
		};
		return _Map.array2Map(dictionary, 2);
	}
	/**通过指定的字符串--获得sb里面的内容.*/
	public Set<String> getSBInner(StringBuffer sb,String... h ){
		return _StringBuffer.handleAllAppoint(
				sb,0,new _StringBuffer.GetAppoint()/*new _StringBuffer.FilterAppoint()*/ ,h);
	}
	/** 获得if里面的内容,没有标识了
	 * 13213(if: a!=b:afd1 : b==c:c=1 )dsada
	 * 会获得new String[]{ "a!=b" , "afd1" , "b==c" , "c=1" }
	 * */
	public String[] getIfInner(String str) {
		String [] strs = _String.subString( str , 0 , "(if:" ,  ")" ).split(":");
		return _String.trimArrayOfMee(false, strs);
	}
	
}
