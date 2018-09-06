package util.extend.complier1.java;

import java.util.HashSet;
import java.util.Set;

import javax.tools.JavaFileObject.Kind;

import util.base._File;
import util.base._String;
import util.base.interfaces.Seri_Clone;
import util.extend.complier1.java.ComplierClient.EntityFile;
import util.extend.wrap.StringWrap;

/** 新建一个文件一个JavaFile对象  */
@SuppressWarnings("serial")
public  class JavaFile extends EntityFile{
	protected  String package0;
	final StringWrap.Set imports = new StringWrap.Set();
	protected String className;
	final Set<Field> fields = new HashSet<Field>();
	final Set<Method> methods = new HashSet<Method>();  
	 

	private JavaFile(String package0, String className) {
		super(package0 + "." + className);
		this.package0 = package0;   
		this.className = className; 
		//设置源文件存放的位置
//		this.setStorePath(_File.joinPath(_File.WOKE_PATH,"src",package0,className+".java"));
		this.setStorePath(_File.joinPath( _File.getSourcePath().getAbsolutePath() , _File.toSeparator(package0),className+Kind.SOURCE.extension )  );
	}
	public static JavaFile getInstance(String package0, String className) {
		JavaFile java = new JavaFile(package0, className);
		try {
			java = (JavaFile) java.getBean();
		}finally {
			return java;
		}
	}
	public String toStringFormat(String str) {
		//这里为了防止 \n 符号导致 失效
//		System.out.println( "str==================" + str);
//		str = str.replaceAll("\\s", " ");
//		System.out.println( "Stringt =====================" + str );
		return _String.toStringFormatOfTwo(str);	
	}
	public String inserReturn(String retu) { return "return " + retu + ';'   ;}
	public void addImport(String... s) { this.imports.add( _String.array2String(s) ); }
	public void addImport(Class<?>... s) { addImport(_String.classs2Strings(false, s)); }
	
	public void addField(Field... e) {	arrayAdd(this.fields, e);	}
	
	public void addMethod(Method... e) { 	arrayAdd(this.methods, e);	}
	@Override
	public String toString() {
		return this.destroy().toString();
	}
	/**最后组装sb*/
	public StringBuilder destroy() {
		StringBuilder sb = new StringBuilder();
		sb.append("package ");
		sb.append(this.package0);
		sb.append(';');
		for (String string : this.imports) {
			sb.append("import ");
			sb.append(string);
			sb.append(';');
		}
		sb.append("public class ");
		sb.append(this.className);
		sb.append('{');
		appendContent(sb, this.fields);
		appendContent(sb, this.methods);
		sb.append('}');
		return sb;
	}
	private void appendContent(StringBuilder sb,Set<? extends Content > con) {
		for (Content content : con) {
			if( content !=null)	sb.append( content.toString() );
		}
	}
	
	public JavaFile arrayAdd(Set set,Content... cons) {
		for (Content content : cons) {
			set.add(content);
		}
		return this;
	}
	
	public class Field extends Content{
		/** master 去重*/
		public Field(String describe, String annotation, String master, String innner) {
			super(describe, annotation, master, innner);
		}
		public Field(String master, String innner) {
			super( null , null , master, innner);
		}
		public String toString() {
			StringBuilder sb = super.toSB();
			sb.append(" = ");
			sb.append(this.innner );
			sb.append(';');
			return sb.toString();
		}
	}
	public class Method extends Content{
		/** master 去重*/
		public Method(String describe, String annotation, String master, String innner) {
			super(describe, annotation, master, innner);
		}
		public Method(String master, String innner) {
			super(null, null, master, innner);
		}
		public String toString() {
			StringBuilder sb = super.toSB();
			sb.append('{');
			sb.append( this.innner );
			sb.append('}');
			return sb.toString();
		}
	}
	protected class Content implements Seri_Clone{//内容
		String describe = "";//注解
		String annotation= "";//
		String master= "";//主人.主内容
		String innner= "";//内容.
		
		public Content(String describe, String annotation, String master, String innner) {
			super();
			if( describe != null )	this.describe = describe;
			if( annotation != null )	this.annotation = annotation;
			if( master != null )	this.master = master;
			if( innner != null )	this.innner = innner;
		}
		
		public StringBuilder toSB() {
			StringBuilder sb = new StringBuilder();
			sb.append("/**");
			sb.append(this.describe);
			sb.append("*/\n");
			sb.append(this.annotation);
			sb.append( '\n' );
			sb.append("public ");
			sb.append(this.master);
			return sb;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((master == null) ? 0 : master.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Content other = (Content) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (master == null) {
				if (other.master != null)
					return false;
			} else if (!master.equals(other.master))
				return false;
			return true;
		}
		private JavaFile getOuterType() {
			return JavaFile.this;
		}
		public String getDescribe() {
			return describe;
		}
		public void setDescribe(String describe) {
			this.describe = describe;
		}
		public String getAnnotation() {
			return annotation;
		}
		public void setAnnotation(String annotation) {
			this.annotation = annotation;
		}
		public String getMaster() {
			return master;
		}
		public void setMaster(String master) {
			this.master = master;
		}
		public String getInnner() {
			return innner;
		}
		public void setInnner(String innner) {
			this.innner = innner;
		}
	}
	
}
