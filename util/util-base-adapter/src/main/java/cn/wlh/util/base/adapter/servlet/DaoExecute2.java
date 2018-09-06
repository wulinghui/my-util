package cn.wlh.util.base.adapter.servlet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.sql.DataSource;

import org.apache.commons.dbutils.ResultSetHandler;

import cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache;

/**
 * @author �����
 * �̶�����..
 * ʵ��Context2ִ��dao������ý�����İ�
 * 
 */
public class DaoExecute2  extends DaoMethodReturnCache<Context2	, DataBus2>{
	public DaoExecute2(DataSource dataSource) {
		super(dataSource);
	}
	/*
	 * 
	 * ���������ķ��������õ��������.���Կ�����4�������Ϳ����������ķ�����.
	 * 
	 * 
	 * 
	 */
	@Override
	public <T> T query(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, DataBus2 dataBus,
			ResultSetHandler<T> rsh, Context2 context) throws SQLException {
		T update = super.query(conn, daoSingle, methodName, dataBus, rsh, context);
		MyContext con = (MyContext) context;
		con.registerDao( keyRule(daoSingle, methodName) , update);
		return update; 
	}

	@Override
	public <T> T query(AllDaoSuperOfSingle daoSingle, String methodName, DataBus2 dataBus, ResultSetHandler<T> rsh,
			Context2 context) throws SQLException {
		T update = super.query(daoSingle, methodName, dataBus, rsh, context);
		MyContext con = (MyContext) context;
		con.registerDao( keyRule(daoSingle, methodName) , update);
		return update;
	}

	@Override
	public int update(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, DataBus2 dataBus,
			Context2 context) throws SQLException {
		int update = super.update(conn, daoSingle, methodName, dataBus, context);
		MyContext con = (MyContext) context;
		con.registerDao( keyRule(daoSingle, methodName) , update);
		return update;
	}

	@Override
	public int update(AllDaoSuperOfSingle daoSingle, String methodName, DataBus2 dataBus, Context2 context)
			throws SQLException {
		int update = super.update(daoSingle, methodName, dataBus, context);
		MyContext con = (MyContext) context;
		con.registerDao( keyRule(daoSingle, methodName) , update);
		return update;
	}
	/** ��÷����Ĺ���..
	 * @param daoSingle --�����ͱ���һ��
	 * @param methodName
	 * @return tableName-methodName
	 */
	protected String keyRule(AllDaoSuperOfSingle daoSingle, String methodName) {
		return daoSingle.getClass().getSimpleName().toUpperCase() + "-" + methodName;
	}
	
	/**
	 * @author �����
	 * ��ϵͳnew Context2()ʱ.
	 * ʵ��new MyContext();
	 * �����û���Context2..
	 */
	public static class MyContext extends Context2{

		public MyContext(ServletRequest source) {
			super(source);
		}

		@Override
		public void registerDao(String key, Object value) {
			super.registerDao(key, value);
		}

		@Override
		public void clearBiz() {
			super.clearBiz();
		}

		@Override
		public void clearDao() {
			super.clearDao();
		}
		public Map<String,Object> getBizMap() {
			return super.businessMap;
		}
		public Map<String,Object> getDaoMap() {
			return super.daoMap;
		}
	}
}
