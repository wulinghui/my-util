package util.extend.complier.java.desrecp;

import util.base._StringBuffer;

public abstract class OprJavaSb {
	StringBuffer sb = new StringBuffer();
	
	public OprJavaSb(StringBuffer sb) {
		super();
		this.sb = sb;
	}
	
	protected OprJavaSb impPackage(String className){
		String strPack = "import "+className+';';
		//防止重复导包引发的报错
		if( sb.indexOf(strPack) != -1) return this;
		_StringBuffer.insert(sb, ";", 0 , strPack);
		return this;
	}
	/**追加注解*/
	protected OprJavaSb appendNote(String describe){
		sb.append( "/**" + describe + "*/");
		return this;
	}
	/**追加注解*/
	protected OprJavaSb appendAnno(String anno){
		sb.append(anno);
		return this;
	}
	/**追加方法名*/
	protected OprJavaSb appendMethod(String method){
		sb.append( "public " + method + "{" /*"){"*/);
		return this;
	}
	protected OprJavaSb appendInner(String inner){
		sb.append(  inner ).append(';');
		return this;
	}
	
}
