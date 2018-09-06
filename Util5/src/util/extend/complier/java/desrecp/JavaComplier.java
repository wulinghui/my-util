package util.extend.complier.java.desrecp;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.base._Reader;
import util.base._Writer;
import util.file.FileUtil;
import util.file.FileUtil.ForFile;

/**������򵥵ķ�����ʵ��,һ��ܹ�
 * @author wlh
 *
 */
public class JavaComplier extends OprJavaSb implements ForFile{
	public final String handlePackage;//����İ���
	public JavaComplier(String handlePackage) {
		this.handlePackage = handlePackage.toLowerCase();//תСд
	}
	public static void main(String[] args) {
		FileUtil.forFile(new File(""), new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".java");//�����java�ļ���ִ��,����
			}
		}, new JavaComplier("coM"));
	}
	public void handler(File file) {
		try {
			//1.�Ȱ��ļ�ȫ������jvm��--������Ҫ����sb.
			StringBuffer sb = new StringBuffer().append(
					_Reader.notOutOfMemory(file) );//ת��sb
			String absolutePath = file.getAbsolutePath();//����·����
			//2.���Class����--Ϊ�˺���ķ���
			Class<?> cl = getClassByFileName(absolutePath);
			//��ʼ���,�Ȳ��dao��
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////
//							D	
//							a
//							o
//							��
// TODO dao��
/////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static final String  DAO= "dao";
	public void splitToDao(StringBuffer sb,Class<?> cl,String absolutePath) throws IOException{//�ָ��dao��
		//�����ѭ��sql���: ���� 		ToDO S-ORACLE:   ��ѯ���е�U-FINDUSER  =>   SELECT  FROM USERZ;LIST LI = NEW ARRAYLIST()
		StringBuffer sbInit = initFileSB(cl, DAO );
		/**TODO	����ĳ�ʼ����ÿ����ܲ�ͬ.Ҳ��֪�����ĸ��ط��ع�..����������
		 * import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.dao.method.SqlRowset;
import cn.gwssi.common.dao.resource.PublicResource;
import cn.gwssi.common.tag.dao.Column;
import cn.gwssi.common.tag.dao.Filter;
import cn.gwssi.common.tag.dao.Insert;
import cn.gwssi.common.tag.dao.PrimaryKey;
import cn.gwssi.common.tag.dao.Select;
import cn.gwssi.common.tag.dao.SqlMethod;
import cn.gwssi.common.tag.dao.Table;
		 */
		impPackage(sbInit, "cn.gwssi.common.component.exception.TxnException");
		
		//������е�sql���,����ѭ����������Dao��.
		for(String str : getSBInner(sb, "TODO S-",";",";") ) {//�����sql�������;��   ->��������֪��,biz�Ľ��շ�ʽ��ʲô.������ӵĻ�����дһ��ռλ����.����sql
			//str-���ִ�Сд
			String [] typeSql = str.replaceAll("\\*", "").split(":");//�ָ�����ݿ����ͺ�sql���
			String dataType = typeSql[0].split("-")[1].trim();//������ݿ�����--ORACLE
			String sqlInner = typeSql[1].trim();//sql�������
			//��sql������������ͨ�������ֵ����ת��.	--��ȫ��ת�ɴ�д,������������ת�ɴ�д.Ӧ��������sql��ʱ��ת
			sqlInner = toFullData(sqlInner, DaoComplier.dataDictionary)/*.toUpperCase()*/;
			//�����SELECT  FROM USERZ;LIST LI = NEW ARRAYLIST()�������ദ��
			DaoComplier.mapDao.get(dataType).builder(sqlInner, sbInit);
		};
		//д���ļ���
		writeFile(sbInit, DAO ,absolutePath);
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////
//							B							
//							i						
//							z
//							��
	// TODO biz��
/////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void splitToBiz(StringBuffer sb,Class<?> cl){//�ָ��dao��
		//�޳�����ע��
		//ע��Dao
		//�滻֮ǰ��ռλDao�������.
		//��������biz
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////
//							V	
//							i
//							e
//							w
	// TODO view��
/////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void splitToView(StringBuffer sb,Class<?> cl){//�ָ��dao��
		//ע��Biz
		//���ɷ���,
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////
//	h	
//	t
//	m
//	l
// TODO html��
/////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void splitToHtml(StringBuffer sb,Class<?> cl){
		
	}

	
	
	
	
	
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////
//				TODO ��������������ĵ���	
/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**д�뵽�ļ���*/
	public void writeFile(StringBuffer sb,String newFile,String path) throws IOException{
		sb.append('}');//�����Ľ�����
		path = path.replaceFirst( "biz", newFile);//�����·��,����֮ǰ��·�����ܰ���biz
		File fi = FileUtil.createFile( new File(path) );//�����ļ�
		//����ԭ�ļ�,һ����д��
		_Writer.oneWrite( fi , false, sb);
	}
	/**��Ӱ���,�Ѱ����ĵ�һ��biz����dao*/
	public StringBuffer initFileSB(Class<?> c,String newPack){
		StringBuffer sb =new StringBuffer("package ");
		//��Ӱ���.����ֻ�滻��һ��.
		sb.append( c.getPackage().getName().replaceFirst("biz" ,newPack )).append(';');
		//�������,
		sb.append("public class ");
		sb.append(c.getSimpleName());
		sb.append( '_' + newPack + '{');
		return sb;
	}
	/**�������*/
	public Class<?> getClassByFileName(String absolutePath) throws ClassNotFoundException{
		int in = absolutePath.indexOf(this.handlePackage);
		int dian = absolutePath.indexOf(".");//�ļ���׺��
		String re = "";
		if(dian != -1){
			re = absolutePath.substring(in, dian);
			//��·����,��������
			re = FileUtil.toPoint(re);
			return Class.forName(re);
		}
		return null;//·��-������ȷ,ͬʱ��д����java�淶�Ļ�.�϶�����Ϊnull��.
	}/**ͨ��ָ�����ַ���--���sb���������.*/
	public Set<String> getSBInner(StringBuffer sb,String... h ){
		return handleAllAppoint(sb,0,new getAppoint(),h);
	}
	/**ͨ��ָ���������ֵ�ת������ȫ������,���������滻*/
	public String toFullData(String sqlInner,Map<String,String> map){
		for (String key : map.keySet()) {
			//�������������滻
			sqlInner = sqlInner.replaceAll(key, map.get(key));
		}
		return sqlInner;
	}
	/**��ô�������ָ���ַ�֮�������
	 * @param sb
	 * @param offsert
	 * @param s
	 * @return
	 */
	public static <T> T handleAllAppoint(StringBuffer sb,int offsert,HandleAppoint<T> h,String ... s){
		T t = null;
		int [] staEn = new int[2];
		int len = s.length;
		int ls = 0;
		loop:do{
			for (int i = 0; i < len; i++) {
				ls = sb.indexOf( s[i] , offsert);
				//���û�еĻ����˳���
				if( ls < offsert || ls == -1 ) break loop;
//				offsert = ls+1;//�������Ϊ�´ε�ƫ����
				offsert = ls+s[i].length();
				//��һ��
				if( i == 0 )  staEn[0] = ls;
				//���һ��
				if( i == len-1 ) staEn[1] = ls;
			}
			t = h.handle(sb, staEn[0] , staEn[1] );
		}while(true);
		return t;
	}
	public static interface HandleAppoint<T>{ T handle(StringBuffer sb,int start,int end);}
	
	/** ��������,���洢
	 * @author wlh	Filter
	 */
	public static class FilterAppoint implements HandleAppoint<Set<StringBuffer>>{
		public final Set<StringBuffer>  set = new LinkedHashSet<StringBuffer>(); 
		public Set<StringBuffer> handle(StringBuffer sb, int start, int end) {
			set.add( sb.delete(start, end+1) );//����ɾ�����洢
			return set;
		}
	}
	/**�������*/
	public static class getAppoint implements HandleAppoint<Set<String>>{
		public final Set<String>  set = new LinkedHashSet<String>();//����
		@Override
		public Set<String> handle(StringBuffer sb, int start, int end) {
			String sub = sb.substring( start , end );//��ȡ,����ɾ��
			//������select * ���԰���ȥ��.ͬʱȫ��תΪ��д.�������ݿ�ת��.
			//�������������.Υ���൥һְ��.
			set.add( sub/*.replaceAll("\\*", "").replaceAll("\\/", "").toUpperCase()*/ );
			return set;
		}
	}/**��ȡ���ַ�����������.*/
	public static class SubAppoint{
		public int start;
		public int end;
		public String inner;
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((inner == null) ? 0 : inner.hashCode());
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
			SubAppoint other = (SubAppoint) obj;
			if (inner == null) {
				if (other.inner != null)
					return false;
			} else if (!inner.equals(other.inner))
				return false;
			return true;
		}
	}/**�������Sub������SubAppoint�ദ��*/
	public static class getAllSubAppoint  implements HandleAppoint<List<SubAppoint>>{
		public final List<SubAppoint> list = new ArrayList<SubAppoint>();
		public List<SubAppoint> handle(StringBuffer sb, int start, int end) { 
			//����subA��. 
			SubAppoint subA = new SubAppoint(); 
			subA.start = start;  
			subA.end = end;   
			subA.inner = sb.substring( start , end );     
			//
			list.add(subA);
			return list;
		}-
	}
}
