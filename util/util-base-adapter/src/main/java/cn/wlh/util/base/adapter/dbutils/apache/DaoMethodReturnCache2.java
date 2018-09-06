package cn.wlh.util.base.adapter.dbutils.apache;

import java.beans.PropertyDescriptor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.AllDaoSuperOfSingle;
import cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.CachePreparedMap;

/**
 * 	@author �����  <br/>
 * 	cn.wlh.util.base.adapter.dbutils.apache.DaoExecute.main(String[]) <br/>
	//1.����ֵ̫��..��û����չ������ʹ�� <br/>
	//2.���ඨ����..����û������...�����ֵ����ö��,���Ƿ���ֵ����. <br/>
	//3.productdao2.table -- �ѵ�����jar��¶���û��ˣ������ڽ�����Ǩ�ơ�<br/>
	�ܽ�:��һ������ֻҪ�����ڲ�����.���þۺ�ģʽ..
	TODO insert Ҳ��ҪHandle?���������ʱ�򿴿�API..
 */
/**
 * @author �����
 *
 * @param <K1>
 *            Context ��key
 * @param <V1>
 *            Context ��value
 * @param <K2>
 *            DataBus ��key
 * @param <V2>
 *            DataBus ��value
 * @param <Context>
 *            Map ������
 * @param <DataBus>
 *            Map ����Bean
 * @param <Table>
 *            ��ѯ�������
 * @param <Set>
 *            ��ѯ���ж��е�����
 * @param <Recored>
 *            ��ѯ���е�����
 * @param <Value>
 *            ��ѯ��ֵ������
 */
public abstract class DaoMethodReturnCache2<K1, V1, K2, V2, Context extends Map<K1, V1>, DataBus extends Map<K2, V2>, Table extends List<?>, Set extends List<V2>, Recored extends DataBus, Value extends V2> implements DaoMethodReturnCacheInterface<K1, V1, K2, V2, Context, DataBus, Table, Set, Recored, Value> {
	protected QueryRunner query;
	//  ����...
	// final static MapListHandler table = new MapListHandler();
	// final static ColumnListHandler<Object> set = new
	// ColumnListHandler<Object>(1);
	// final static MapHandler record = new MapHandler();
	// final static ScalarHandler value = new ScalarHandler(1);

	public DaoMethodReturnCache2(DataSource dataSource) {
		Objects.requireNonNull(dataSource);
		query = new QueryRunner(dataSource);
	}
	
	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#getTable(cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.AllDaoSuperOfSingle, java.lang.String, DataBus, Context)
	 */
	@Override
	public Table getTable(AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException {
		return getTable(query.getDataSource().getConnection(), daoSingle, methodName, dataBus, context);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#getSet(cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.AllDaoSuperOfSingle, java.lang.String, DataBus, Context)
	 */
	@Override
	public Set getSet(AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException {
		return getSet(query.getDataSource().getConnection(), daoSingle, methodName, dataBus, context);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#getRecord(cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.AllDaoSuperOfSingle, java.lang.String, DataBus, Context)
	 */
	@Override
	public Recored getRecord(AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException {
		return getRecord(query.getDataSource().getConnection(), daoSingle, methodName, dataBus, context);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#getValue(cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.AllDaoSuperOfSingle, java.lang.String, DataBus, Context)
	 */
	@Override
	public Value getValue(AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException {
		return getValue(query.getDataSource().getConnection(), daoSingle, methodName, dataBus, context);
	}
	public int update(AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context) throws SQLException {
		return update(query.getDataSource().getConnection(), daoSingle, methodName, dataBus, context);
	}
	//////////////////////////////////////////////////////////////////////////////////
	// connection
	/////////////////////////////////////////////////////////////////////////////////
	public int update(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException {
		CachePreparedMap invoke = DaoMethodReturnCache.invoke(daoSingle, methodName, new Object[] { context, dataBus });
		Object[] params = DaoMethodReturnCache.getAllValue(invoke, dataBus);
		String sql = invoke.getFinalSql();
		return query.update(sql, params);
	}

	
	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#getTable(java.sql.Connection, cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.AllDaoSuperOfSingle, java.lang.String, DataBus, Context)
	 */
	@Override
	public Table getTable(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus,
			Context context) throws SQLException {
		CachePreparedMap invoke = DaoMethodReturnCache.invoke(daoSingle, methodName, new Object[] { context, dataBus });
		Object[] params = DaoMethodReturnCache.getAllValue(invoke, dataBus);
		String sql = invoke.getFinalSql();
		
		return getTable(conn, sql, params);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#getSet(java.sql.Connection, cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.AllDaoSuperOfSingle, java.lang.String, DataBus, Context)
	 */
	@Override
	public Set getSet(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus,
			Context context) throws SQLException{
		CachePreparedMap invoke = DaoMethodReturnCache.invoke(daoSingle, methodName, new Object[] { context, dataBus });
		Object[] params = DaoMethodReturnCache.getAllValue(invoke, dataBus);
		String sql = invoke.getFinalSql();
		return getSet(conn, sql, params);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#getRecord(java.sql.Connection, cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.AllDaoSuperOfSingle, java.lang.String, DataBus, Context)
	 */
	@Override
	public  Recored getRecord(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName,
			DataBus dataBus, Context context) throws SQLException{
		CachePreparedMap invoke = DaoMethodReturnCache.invoke(daoSingle, methodName, new Object[] { context, dataBus });
		Object[] params = DaoMethodReturnCache.getAllValue(invoke, dataBus);
		String sql = invoke.getFinalSql();
		
		return getRecored(conn, sql, params);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#getValue(java.sql.Connection, cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.AllDaoSuperOfSingle, java.lang.String, DataBus, Context)
	 */
	@Override
	public  Value getValue(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus,
			Context context) throws SQLException{
		CachePreparedMap invoke = DaoMethodReturnCache.invoke(daoSingle, methodName, new Object[] { context, dataBus });
		Object[] params = DaoMethodReturnCache.getAllValue(invoke, dataBus);
		String sql = invoke.getFinalSql();
		return getValue(conn, sql, params);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#getTable(java.sql.Connection, java.lang.String, java.lang.Object)
	 */
	@Override
	public abstract Table getTable(Connection conn, String sql, Object... params) throws SQLException;

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#getSet(java.sql.Connection, java.lang.String, java.lang.Object)
	 */
	@Override
	public abstract Set getSet(Connection conn, String sql, Object... params) throws SQLException;

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#getRecored(java.sql.Connection, java.lang.String, java.lang.Object)
	 */
	@Override
	public abstract Recored getRecored(Connection conn, String sql, Object... params) throws SQLException;

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#getValue(java.sql.Connection, java.lang.String, java.lang.Object)
	 */
	@Override
	public abstract Value getValue(Connection conn, String sql, Object... params) throws SQLException;


	// protected void //�÷���...
	// public static interface Handler<T>{
	//
	// }
	/*
	 * ������org.apache.commons.dbutils.QueryRunner��API (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return query.equals(obj);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#batch(java.sql.Connection, java.lang.String, java.lang.Object[][])
	 */
	@Override
	public int[] batch(Connection conn, String sql, Object[][] params) throws SQLException {
		return query.batch(conn, sql, params);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#batch(java.lang.String, java.lang.Object[][])
	 */
	@Override
	public int[] batch(String sql, Object[][] params) throws SQLException {
		return query.batch(sql, params);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#getDataSource()
	 */
	@Override
	public DataSource getDataSource() {
		return query.getDataSource();
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#isPmdKnownBroken()
	 */
	@Override
	public boolean isPmdKnownBroken() {
		return query.isPmdKnownBroken();
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#toString()
	 */
	@Override
	public String toString() {
		return query.toString();
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#fillStatement(java.sql.PreparedStatement, java.lang.Object)
	 */
	@Override
	public void fillStatement(PreparedStatement stmt, Object... params) throws SQLException {
		query.fillStatement(stmt, params);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#fillStatementWithBean(java.sql.PreparedStatement, java.lang.Object, java.beans.PropertyDescriptor[])
	 */
	@Override
	public void fillStatementWithBean(PreparedStatement stmt, Object bean, PropertyDescriptor[] properties)
			throws SQLException {
		query.fillStatementWithBean(stmt, bean, properties);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#update(java.sql.Connection, java.lang.String)
	 */
	@Override
	public int update(Connection conn, String sql) throws SQLException {
		return query.update(conn, sql);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#update(java.sql.Connection, java.lang.String, java.lang.Object)
	 */
	@Override
	public int update(Connection conn, String sql, Object param) throws SQLException {
		return query.update(conn, sql, param);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#fillStatementWithBean(java.sql.PreparedStatement, java.lang.Object, java.lang.String)
	 */
	@Override
	public void fillStatementWithBean(PreparedStatement stmt, Object bean, String... propertyNames)
			throws SQLException {
		query.fillStatementWithBean(stmt, bean, propertyNames);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#update(java.sql.Connection, java.lang.String, java.lang.Object)
	 */
	@Override
	public int update(Connection conn, String sql, Object... params) throws SQLException {
		return query.update(conn, sql, params);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#update(java.lang.String)
	 */
	@Override
	public int update(String sql) throws SQLException {
		return query.update(sql);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#update(java.lang.String, java.lang.Object)
	 */
	@Override
	public int update(String sql, Object param) throws SQLException {
		return query.update(sql, param);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#update(java.lang.String, java.lang.Object)
	 */
	@Override
	public int update(String sql, Object... params) throws SQLException {
		return query.update(sql, params);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#execute(java.sql.Connection, java.lang.String, java.lang.Object)
	 */
	@Override
	public int execute(Connection conn, String sql, Object... params) throws SQLException {
		return query.execute(conn, sql, params);
	}

	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCacheInterface#execute(java.lang.String, java.lang.Object)
	 */
	@Override
	public int execute(String sql, Object... params) throws SQLException {
		return query.execute(sql, params);
	}
}
