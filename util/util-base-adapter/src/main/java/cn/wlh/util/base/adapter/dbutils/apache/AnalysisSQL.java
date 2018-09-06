package cn.wlh.util.base.adapter.dbutils.apache;

import java.util.ArrayList;
import java.util.List;

import cn.wlh.util.base.JavaUtilFactory;
import cn.wlh.util.base.adapter.dbutils.apache.GetSql2.Prepared;

/**
 * @author �����
 * ���Զ���Ĺ���,������sql��
 * insert into users(username,password,email,birthday)  values(:name,:pw,:email,:birth)
 * 
 * [name, pw ,email, birth,
 * insert into users(username,password,email,birthday)  values(?,?,?,?) ]
 * 
 */
public class AnalysisSQL extends Prepared{
	protected AnalysisSQL(char c) {
		setStartChar(new char[] {
				c //':'	
		});
	}

		@Override
		public List<String> getParameter(String sql) {
			boolean flags []= new boolean[startChar.length];
			char[] strChar = sql.toCharArray();
			int length = strChar.length;
			int endLength = 0; 
			List<Character> chars = new ArrayList<Character>();
			List<String> strs = JavaUtilFactory.newList(JavaUtilFactory.SELECT_OF_METHOD);
			StringBuilder sb = new StringBuilder();
			char c;
			//ѭ������char
			for (int i = endLength ; i < length; i++) { //udpate delete ����5��֮��
				c = strChar[i];
				if( flags[0] ) {//��: 
					if( lookUp(c , fileterChar) ) { // �� :sd  where  �� sd  where�ո���,
						flags[0] = false;
						addOutPars(chars, strs);
//						chars.clear();
						chars = new ArrayList<Character>();
					}else{ 
						chars.add(c);
					}
				}else if( c == startChar[0] ) {
					 flags[0]  = true; // 
					 sb.append("?"); //�����滻��? ��
				}else {//��ӵ�sb����ȥ��
					sb.append(c);
				}
			}
			//��ֹ���һ��û����ӽ�ȥ��
			if( !chars.isEmpty() )  addOutPars(chars, strs);
				
			// ���һ����   insert into users(username,password,email,birthday)  values(?,?,?,?)
			strs.add(sb.toString());
			return strs;
		}
		/**
		 *  �������Ĳ���������
		 */
		protected final void addOutPars(List<Character> chars, List<String> strs) {
			int len = chars.size();
			char[] array = new char[len];
			String str;
			for (int i = 0; i < len; i++) {
				array[i] = chars.get(i);
			}
			str = String.valueOf(array);
			strs.add(str);
		}
}
