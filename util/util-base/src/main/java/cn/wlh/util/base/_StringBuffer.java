package cn.wlh.util.base;

import java.util.LinkedHashSet;
import java.util.Set;

public  abstract class _StringBuffer {
	/** �� ���ҵ�str�ĵط�����inner
	 * @return ����֮���λ��*/  
	public static int insert(StringBuffer sb,String str,int offsert,Object inner){
		int i = sb.indexOf(str,offsert)+1;
		String ss = String.valueOf( inner );
		sb.insert( i , ss );
		return i+ss.length();
	}
	/**  �� ���ҵ�str��findex�εĵط�����inner */      
	public static int insert(StringBuffer sb,String str,int oneOffsert ,int findex,Object inner){
		int i = getInsertOffsert(sb, str, oneOffsert, findex);
		String ss = String.valueOf( inner );
		sb.insert( i , ss );
		return i+ss.length();
	}
	/** ��ò����ƫ����
	 * @param sb
	 * @param inner
	 * @param oneOffsert --�״�ƫ����
	 * @param findex	-- ��index �ڼ���ƫ��   0-��һ��
	 * @return
	 */
	public static int getInsertOffsert( StringBuffer sb,String inner,int oneOffsert ,int findex) {
		int i = 0;
		int ret = 0;
		do {
			ret = sb.indexOf(inner,oneOffsert)+1;
		}while( i<findex );
		return ret;
	}
	/**��ô�������ָ���ַ�֮�������
	 * @param sb
	 * @param offsert
	 * @param s
	 * @return
	 */
	public static <T> T handleAllAppoint(StringBuffer sb,int offsert,HandleAppoint<T> h,String ... s){
		T t = h.initT();
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
			}/**���û�������NullExc��*/
			t = h.handle(sb, staEn[0] , staEn[1] );
		}while(true);
		return t;
	}
	public static interface HandleAppoint<T>{ T handle(StringBuffer sb,int start,int end); T initT();/*δ��ֹNullExc*/}
	/** ��������,���洢
	 * @author wlh	Filter
	 */
	public static class FilterAppoint implements HandleAppoint<Set<String>>{
		public final Set<String>  set = new LinkedHashSet<String>(); 
		public Set<String> handle(StringBuffer sb, int start, int end) {
			set.add( sb.delete(start, end+1).toString() );//����ɾ�����洢
			return set;          
		}
		public Set<String> initT() {
			return set;
		}
	}
	/**�������*/
	public static class GetAppoint implements HandleAppoint<Set<String>>{
		public final Set<String>  set = new LinkedHashSet<String>();//����
		@Override
		public Set<String> handle(StringBuffer sb, int start, int end) {
			String sub = sb.substring( start , end );//��ȡ,����ɾ��
			//������select * ���԰���ȥ��.ͬʱȫ��תΪ��д.�������ݿ�ת��.
			//�������������.Υ���൥һְ��.
			set.add( sub/*.replaceAll("\\*", "").replaceAll("\\/", "").toUpperCase()*/ );
			return set;
		}
		public Set<String> initT() {
			return set;
		}
	}
}
