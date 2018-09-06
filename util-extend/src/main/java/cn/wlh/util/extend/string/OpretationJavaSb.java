package cn.wlh.util.extend.string;

import cn.wlh.util.base._StringBuffer;

/**
 * @author wlh
 * ����Java�ļ���SB..
 */
public abstract class OpretationJavaSb {
	StringBuffer sb = new StringBuffer();
	
	public StringBuffer getSb() {
		return sb;
	}
	public OpretationJavaSb setSb(StringBuffer sb) {
		this.sb = sb; return this;
	}  
	
	
	
	protected OpretationJavaSb impPackage(String className){
		String strPack = "import "+className+';';
		//��ֹ�ظ����������ı���
		if( sb.indexOf(strPack) != -1) return this;
		_StringBuffer.insert(sb, ";", 0 , strPack);
		return this;
	}/**׷��ע��*/
	protected OpretationJavaSb appendNote(String describe){
		sb.append( "/**" + describe + "*/");
		return this;
	}/**׷��ע��*/
	protected OpretationJavaSb appendAnno(String anno){
		sb.append(anno);
		return this;
	}/**׷�ӷ�����*/
	protected OpretationJavaSb appendMethod(String method){
		sb.append( "public " + method + "{" /*"){"*/);
		return this;
	}
	protected OpretationJavaSb appendInner(String inner){
		sb.append(  inner ).append(';');
		return this;
	}
	
	//����������������
	/**����Class*/
	protected OpretationJavaSb impPackage(StringBuffer sb,String className){
		String strPack = "import "+className+';';
		//��ֹ�ظ����������ı���
		if( sb.indexOf(strPack) != -1) return this;
		_StringBuffer.insert(sb, ";", 0 , strPack);
		return this;
	}/**׷��ע��*/
	protected OpretationJavaSb appendNote(StringBuffer sb,String describe){
		sb.append( "/**" + describe + "*/");
		return this;
	}/**׷��ע��*/
	protected OpretationJavaSb appendAnno(StringBuffer sb,String anno){
		sb.append(anno);
		return this;
	}/**׷�ӷ�����*/
	protected OpretationJavaSb appendMethod(StringBuffer sb,String method){
		sb.append( "public " + method + "{" /*"){"*/);
		return this;
	}
	protected OpretationJavaSb appendInner(StringBuffer sb,String inner){
		sb.append(  inner ).append(';');
		return this;
	}
}
