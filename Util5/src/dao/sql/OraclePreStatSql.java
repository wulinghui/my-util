package dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author wlh
 * oracle���ݿ��Ԥ����򵥲���.
 */
public  class OraclePreStatSql extends RelationSql<PreparedStatement,Object> {

	public OraclePreStatSql(String url, String user, String password,int poolLength)
			throws SQLException {
		super(new DataPool(url, user, password,poolLength));
	}

	@Override
	protected PreparedStatement setInput(String sql, Connection conn,
			Object word) throws SQLException{
		// ���ps
		PreparedStatement ps =  conn.prepareStatement(sql);
		//�жϸ��ݲ�ͬ������,ִ�в�ͬ�Ĳ���.
		if( word != null) {
			if(word instanceof Collection){
				//��List�Ĳ���,ͨ����:word.values
				Collection words = (Collection) word;
				int i = 0;
				for (Object object : words) {
					i++;
					ps.setObject( i, object);
				}
			}else if( word instanceof Object[]){
				//����Ĳ���
				Object [] words = (Object[]) word;
				int i = 0;
				int len = words.length;
				for (; i < len; i++) {
					ps.setObject(i + 1, words[i]);
				}
			}else if( word instanceof Word ){
				//Word����Ĳ���
				Word words = (Word) word;
				Object [] inpts = (Object[]) words.get( Word.SQL_INPUT );//�������ҪWordע����
				if( inpts == null) return ps;//��ֹΪ��.
				int i = 0;
				int len = inpts.length;
				for (; i < len; i++) {
					ps.setObject(i + 1,//ͨ��inpts����������ֵ 
							words.get(inpts[i]) );
				}
			}
		}
		ps.setMaxRows(50);//����� 
		ps.setMaxFieldSize(50);//�����
		return ps;
	}
	@Override
	protected List getOutput(String sql, PreparedStatement ps, ResultSet rs)
			throws SQLException {
		int count;
		List list = new ArrayList();
		rs = ps.executeQuery();
		ResultSetMetaData rsmd = ps.getMetaData();
		count = rsmd.getColumnCount();
//		System.out.println(count);
		while (rs.next()) {
			Map<String, Object> map = new Word();
			for (int j = 0; j < count; j++) {
				String colN = rsmd.getColumnName(j + 1);
				map.put(colN, rs.getObject(colN));
			}
			list.add(map);
		}
//		System.out.println(list);
		return list;
	}

	@Override
	protected Integer getOutput(String sql, PreparedStatement stat)
			throws SQLException {
		return stat.executeUpdate();
	}
}
