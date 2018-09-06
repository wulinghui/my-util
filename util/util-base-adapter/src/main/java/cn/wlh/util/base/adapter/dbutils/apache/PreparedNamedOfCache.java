package cn.wlh.util.base.adapter.dbutils.apache;

import java.util.ArrayList;
import java.util.List;

/**
 * @author �����
 * ����ԭ��@see super<br/>
���Զ���Ĺ���,������sql��<br/>
 * insert into users(username,password,email,birthday)  values(:name,:pw,:email,:birth)<br/>
 * 
 * [name, pw ,email, birth, <br/>
 * insert into users(username,password,email,birthday)  values(?,?,?,?) ]<br/>
 * 
 */
public class PreparedNamedOfCache extends PreparedOfCache {
	protected PreparedNamedOfCache(char c) {
		setStartChar(new char[] {
				c //':'	
		});
	}
	
	@Override
	public CachePreparedMap getParameter(String sql) {
		char[] strChar = sql.toCharArray();
		int length = strChar.length;
		int endLength = 5; 
		List<Character> chars = new ArrayList<Character>();
		Object[] array ; 
		String str;
//		List<String> strs = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		int indexOf = 1; // ��Ӧ������λ��
		CachePreparedMap preparedMap = new CachePreparedMap();
		//ѭ������char
		for (int i = endLength ; i < length; i++) { //udpate delete ����5��֮��
			char c = strChar[i];
			if( flags[0] ) {//��: 
				if( lookUp(c , fileterChar) ) { // �� :sd  where  �� sd  where�ո���,
					sb.append("? ");//�����滻��? ��
					flags[0] = false;
					array = chars.toArray();
					str = String.valueOf(array);
					// ���벢����..
					preparedMap.putOneOfAutoAdd( str, indexOf++ );
					chars = new ArrayList<Character>();
				}else{ 
					chars.add(c);
				}
			}else if( c == startChar[0] ) {
				 flags[0]  = true; // ��: ������ӵ�sb����ȥ��
			}else {//��ӵ�sb����ȥ��
				sb.append(c);
			}
		}
		preparedMap.setFinalSql( sb.toString() );
		preparedMap.saveAllKeyAndValue();
		return preparedMap;
	}
}
