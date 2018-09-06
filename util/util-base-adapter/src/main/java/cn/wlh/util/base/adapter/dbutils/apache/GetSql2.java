package cn.wlh.util.base.adapter.dbutils.apache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wlh.util.base.adapter.datasource.dbcp.DBCPUtils;

/**
 * @author �����
 * һ��ԭ����jdbc , �ʼ�����Լ�д���Ӻ���ǰ�����Ĺ���.��=?ǰ����ַ�������ó��������Ƕ����������ܾ���Щ�����ˡ�
 * insert into users(username,password,email,birthday) values(?,?,?,?)
 * �ٶ�:NamedPreparedStatement
 */
public class GetSql2 {
	DBCPUtils instance = DBCPUtils.getInstance("null");
	Connection connection = instance.getConnection();
	//��һ�ַ���.�����ڲ�����,�ɶ��Ըߵ�ʱ��ʹ�á�
	private void test() throws SQLException {
		String sql = "insert into users(username,password,email,birthday) values(?,?,?,?)";
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		int i = 1;
		prepareStatement.setObject(i++, "����");
		prepareStatement.setObject(i++, "123456");
		prepareStatement.setObject(i++, "123456@QQ.COM");
		prepareStatement.setObject(i++, "1995.10.11");
	}
	//�ڶ��ַ���. �����ڲ�����,��߿ɶ��Ըߡ�
	//NamedPreparedStatement ��ǰ�������.����':'��ʼ  ����' '������
	private void test1() throws SQLException {
		//������ٶ�.
		String sql =  "insert into users(username,password,email,birthday) "
				+ "values(:name,:pw,:email,:birth)";
		Connection con = connection;
		String name = null;
		String address = null;
		NamedPreparedStatement p = new NamedPreparedStatement(con, sql);
		p.setString("name", name);
		p.setString("pw", address);
		p.execute();
	}
	
	//��3�ַ���. �����ڲ�����, �� shenqingh = ? ���ܼ�ֵ�Ե�
	private void test2() throws SQLException {
		Map map = new HashMap();
		String query = "select * from people where (first_name = ? or last_name = ?) and address = ?";
		PreparedStatement prepareStatement = connection.prepareStatement(query);
		List<String> parameter = new Prepared().getParameter(query);
//		int i = 1 , l = 0;
//		prepareStatement.setObject(i++, map.get(parameter.get(l++) ) );
//		prepareStatement.setObject(i++, map.get(parameter.get(l++) ) );
//		prepareStatement.setObject(i++, map.get(parameter.get(l++) ) );
		setParameter(prepareStatement, parameter, map);
//		prepareStatement.ex
		ResultSet executeQuery = prepareStatement.executeQuery();
	}
	//��3�ַ���. �����ڲ�����, �� shenqingh = ? ���ܼ�ֵ�Ե�
	private void test2_() throws SQLException {
		Map map = new HashMap();
		String query = "select * from people where (first_name = ? or last_name = ?) and address = ?";
		PreparedStatement prepared = prepared(map, query, connection);
		ResultSet executeQuery = prepared.executeQuery();
		int executeUpdate = prepared.executeUpdate();
	}
	private void setParameter(PreparedStatement prepareStatement ,List<String> parameter ,Map map ) throws SQLException {
		int size = parameter.size();
		for (int i = 0; i < size; i++) {
			prepareStatement.setObject(i+1, map.get(parameter.get(i) ) );
		}
	}
	public PreparedStatement prepared( Map map,String query  ,Connection connection) throws SQLException {
		PreparedStatement prepareStatement = connection.prepareStatement(query);
		List<String> parameter = new Prepared().getParameter(query);
		setParameter(prepareStatement, parameter, map);
		return prepareStatement;
	}
	/**
	 * @author �����
	 *һ��ԭ����jdbc , �ʼ�����Լ�д���Ӻ���ǰ�����Ĺ���.��=?ǰ����ַ�������ó�����ֻ���� shenqing = ?�������.
	 */
	public static class Prepared{
		protected Prepared(){}
		static Prepared prepared []=new Prepared[] {
				new Prepared() , 
				new AnalysisSQL(':')
		};
		char [] fileterChar = {
			' ','\n','\t','\'','"'	
		};
		char [] startChar = {
			'?' , '='	
		};
		protected void setFileterChar(char[] fileterChar) {
			this.fileterChar = fileterChar;
		}
		protected void setStartChar(char[] startChar) {
			this.startChar = startChar;
		}
		protected void setFlags(boolean[] flags) {
			this.flags = flags;
		}
		boolean flags []= new boolean[startChar.length];  // = ?�ı��
		/**��� shenqing = ? ��shenqing����
		 * @param sql
		 * @return shenqing 
		 */
		public static Prepared getInstance(String id) {
			if( "".equals(id)) {
				return prepared[0];
			}else if( ":".equals(id) ){
				return prepared[1];
			}
			return null;
		}
		/**���һ����sql , ������key.
		 * @param query
		 * @param beanFlag
		 * @return
		 */
		public List<String> getParameter(String sql) {
			//�Ȱ�sql�Ŀո� \t \nȫ����һ��' '  ��ʱ����Ҫ.
			char[] strChar = sql.toCharArray();
			int length = strChar.length;
			int endLength = startChar.length;
			List<Character> chars = new ArrayList<Character>();
			Object[] array ; 
			String str;
			List<String> strs = new ArrayList<>();
			//ѭ������char
			for (int i = length; length >  endLength; i--) { // 2 
				char c = strChar[i];
				
				if( flags[0] ) {//��? �����ж�=
					if(flags[1] ==true && !lookUp(c , fileterChar)  ) { //   ���ڽ���  �����ڹ�����������.
						chars.add(c);
					}else if( startChar[1] == c ) { //   Ϊ=��ʱ��     
						flags[1]  = true; 
					// �ڽ���֮ǰ�Ĺ�����������. "s = ?" �� " = ?"��Щ	
					}else if(lookUp(c , fileterChar) && flags[1] == false) {
						
					}else if( chars.size() > 0 ){ //���ڽ���  ���ڹ�����������.. "shenq ingh = ?" �� "shenq ingh"
						flags[0] = false;
						flags[1] = false;
						array = chars.toArray();
						str = String.valueOf(array);
						strs.add(str);
						chars.clear();
					}else  if( chars.size() == 0 ){ // //���ڽ���  ���ڹ�����������.. "shenq ingh = ?" �� "ingh ="
						
					}
				}else if( c == startChar[0] ) {
					 flags[0]  = true; // ��? �´�ѭ���ж�=
				}
			}
			strs.add(sql);
			return strs;
		}
		// �Ƿ���������.
		public boolean lookUp(char c , char[] strChar) {
			for (char d : strChar) {
				if( d == c) return true; //��
			}
			return false;//����
		}
	}
	
//	public class NamedPreparedStatement{
//		char [] start;
//		char [] end;
//		boolean flag;
//		List<String> para = new ArrayList<String>(); 
//��ʹ��HashMap��SQL����ֶ�Ӧ�ı���ת�����ʺ�	
//Ȼ���ٴ���һ����Ӧ��PreparedStatement��䣬���ұ����˶�Ӧ������������ӳ���ϵ�����������Ǽ��ݵģ���������Էֱ�������á�		
//	}
}
