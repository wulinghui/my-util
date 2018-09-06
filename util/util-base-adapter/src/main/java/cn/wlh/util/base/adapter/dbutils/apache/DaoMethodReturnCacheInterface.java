package cn.wlh.util.base.adapter.dbutils.apache;

import java.beans.PropertyDescriptor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.AllDaoSuperOfSingle;
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
public interface DaoMethodReturnCacheInterface<K1, V1, K2, V2, Context extends Map<K1, V1>, DataBus extends Map<K2, V2>, Table extends List<?>, Set extends List<V2>, Recored extends DataBus, Value extends V2> {
	public int update(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException;
	public int update(AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context) throws SQLException ;
	Table getTable(AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException;

	Set getSet(AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context) throws SQLException;

	Recored getRecord(AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException;

	Value getValue(AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException;

	//////////////////////////////////////////////////////////////////////////////////
	// connection
	/////////////////////////////////////////////////////////////////////////////////
	Table getTable(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException;

	Set getSet(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException;

	Recored getRecord(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus,
			Context context) throws SQLException;

	Value getValue(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException;

	Table getTable(Connection conn, String sql, Object... params) throws SQLException;

	Set getSet(Connection conn, String sql, Object... params) throws SQLException;

	Recored getRecored(Connection conn, String sql, Object... params) throws SQLException;

	Value getValue(Connection conn, String sql, Object... params) throws SQLException;

	boolean equals(Object obj);

	int[] batch(Connection conn, String sql, Object[][] params) throws SQLException;

	int[] batch(String sql, Object[][] params) throws SQLException;

	DataSource getDataSource();

	boolean isPmdKnownBroken();

	String toString();

	void fillStatement(PreparedStatement stmt, Object... params) throws SQLException;

	void fillStatementWithBean(PreparedStatement stmt, Object bean, PropertyDescriptor[] properties)
			throws SQLException;

	int update(Connection conn, String sql) throws SQLException;

	int update(Connection conn, String sql, Object param) throws SQLException;

	void fillStatementWithBean(PreparedStatement stmt, Object bean, String... propertyNames) throws SQLException;

	int update(Connection conn, String sql, Object... params) throws SQLException;

	int update(String sql) throws SQLException;

	int update(String sql, Object param) throws SQLException;

	int update(String sql, Object... params) throws SQLException;

	int execute(Connection conn, String sql, Object... params) throws SQLException;

	int execute(String sql, Object... params) throws SQLException;

}