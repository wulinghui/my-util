package dao.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.sql.DataPool.WrapConnection;

/**
 *	@author wlh
 *	����һ����ϵ�����ݿ�Ĵ�ͳ����.
 *	��ͬ�������ͬ���ݿ�Ĳ���.  -- ������ͬҲ��
 *  һ���������һ�����ݿ�. --��ͬ������ǲ��
 */
public abstract class RelationSql<Stat extends Statement,Inpts> implements SqlBcui {
	DataPool pool;
	public RelationSql(DataPool pool) {
		this.pool = pool;
	}
	/** �������Ҫ�Ŀ��,�û�ֻ��ʵ��,��Ҫ�ľ�����
	 * �������DataӦ����һά����.�Ž�ȥ��Ӧ��Ҳ��--����ֻ��ִ��,���˵����ⲻ��������.��������һ��.
	 * @param sql
	 * @param inputJsonMap
	 * @return
	 * @throws TsException
	 * @throws SQLException 
	 */
	public Object operationDao(String sql,boolean isXuykRs,Inpts word ) throws   SQLException {
		Stat stat = null;	
		ResultSet rs = null;
		WrapConnection wc = pool.getWrapConnection();
		Object obj;
		try {
			//���Statement
			stat = setInput( sql, wc, word);
//			stat.setMaxRows(50);//����� //����
//			stat.setMaxFieldSize(50);//�����
			//2ִ��   3��ֵ
			if( isXuykRs ) obj = getOutput(sql, stat, rs);
			else obj = getOutput(sql, stat);
		}finally {
			release( rs,stat,null );
		}
		return obj;
	}
	
	/** rs stat  ÿ�β��������ͷ�
	 * @param rs
	 * @param stat	
	 * @param wc  һ������������ͷ�
	 * @throws SQLException
	 */
	public void release(ResultSet rs,Stat stat,WrapConnection wc) throws  SQLException {
		if (rs != null)rs.close();
		if (stat != null)stat.close();
		if(wc!=null) wc.close();  
	}
	protected abstract Stat setInput(String sql, Connection wc, Inpts word) throws SQLException;
	protected abstract Object getOutput(String sql, Stat stat, ResultSet rs) throws  SQLException;
	protected abstract Object getOutput(String sql, Stat stat) throws  SQLException;
}
