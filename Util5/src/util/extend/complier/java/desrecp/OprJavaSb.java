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
		//��ֹ�ظ����������ı���
		if( sb.indexOf(strPack) != -1) return this;
		_StringBuffer.insert(sb, ";", 0 , strPack);
		return this;
	}
	/**׷��ע��*/
	protected OprJavaSb appendNote(String describe){
		sb.append( "/**" + describe + "*/");
		return this;
	}
	/**׷��ע��*/
	protected OprJavaSb appendAnno(String anno){
		sb.append(anno);
		return this;
	}
	/**׷�ӷ�����*/
	protected OprJavaSb appendMethod(String method){
		sb.append( "public " + method + "{" /*"){"*/);
		return this;
	}
	protected OprJavaSb appendInner(String inner){
		sb.append(  inner ).append(';');
		return this;
	}
	
}
