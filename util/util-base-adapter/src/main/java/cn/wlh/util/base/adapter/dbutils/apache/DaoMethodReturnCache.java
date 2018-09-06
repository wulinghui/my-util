package cn.wlh.util.base.adapter.dbutils.apache;

import java.beans.PropertyDescriptor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import javax.sql.DataSource;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.wlh.util.base._Method;
import cn.wlh.util.base._Method.MethodFilter;
import cn.wlh.util.base.adapter.java.lang.AbstractStringBuilder;
import cn.wlh.util.base.adapter.java.util.AddAfterSeeListInterface;
import cn.wlh.util.base.adapter.java.util.JavaUtilFactory;

/**
 * @author �����<br/>
 *         daoִ�з�����ķ���ֵ��<br/>
 *         ����ķ���ֵ��ָ sql���Ͷ�Ӧ������<br/>
 *         [name, pw ,email, birth,<br/>
 *         insert into users(username,password,email,birthday) values(?,?,?,?)
 *         ]<br/>
 */
public class DaoMethodReturnCache<Context extends Map , DataBus extends Map> extends org.apache.commons.dbutils.QueryRunner {
	/*
	 * cn.wlh.util.base.adapter.dbutils.apache.GetSql2.Prepared <br/>
	 * ��cn.wlh.util.base.adapter.dbutils.apache.AnalysisSQL <br/> ����ʵ��,�������.̫ռ�ڴ�
	 */
	// Map<Class,List<String>> cacheMap =
	// JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_FIELD);

	// Map<Class, CachePreparedMap> cacheMap =
	// JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_FIELD); //@see
	// AllDaoSuperOfSingle.class

	// static Map<Class<? extends AllDaoSuperOfSingle>, AllDaoSuperOfSingle>
	// cacheMap = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_FIELD); //@see
	// AllDaoSuperOfSingle.class

	// public static CachePreparedMap invoke(Class cla , String methodName ,Object
	// [] objs ) {
	// //�ȴ�cache������...�����и�����,���Map�����O(N-2)��Ч�ʽ����.����ֱ�ӷ���.
	// }

	// public static CachePreparedMap invoke(AllDaoSuperOfSingle daoSingle, String
	// methodName, Object[] objs) {
	//
	// }
	public DaoMethodReturnCache(DataSource dataSource) {
		super(dataSource);
		if (dataSource == null)
			throw new NullPointerException();
	}
	/**jdk����*/
	public void fillStatementWithBean(PreparedStatement stmt, Object bean, PropertyDescriptor[] properties)
			throws SQLException {
		// TODO Auto-generated method stub
		super.fillStatementWithBean(stmt, bean, properties);
	}

	// /*
	// * ������DBCPUtils.close ���� �����close���ǹ黹�����ӳ�.
	// */
	// protected void close() throws SQLException {
	// // ���췽����֤�� super.ds!=null
	// close(super.ds.getConnection());
	// } �û��黹��,����ʲô..
	public final static MapListHandler table = new MapListHandler();
	public final static ColumnListHandler<Object> set = new ColumnListHandler<Object>(1);
	public final static MapHandler record = new MapHandler();
//	public final  ResultSetHandler<DataBus> record1 = new MapHandler();
	public final static ScalarHandler value = new ScalarHandler(1);

	public List<Map<String, Object>> getTable(AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus,
			Context context) throws SQLException {
		return this.query(daoSingle, methodName, dataBus, table, context);
	}

	public List<Object> getSet(AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException {
		return this.query(daoSingle, methodName, dataBus, set, context);
	}

	public Map<String, Object> getRecord(AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException {
		return this.query(daoSingle, methodName, dataBus, record, context);
	}

	public <T> T getValue(AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException {
		return (T) this.query(daoSingle, methodName, dataBus, value, context);
	}
	//////////////////////////////////////////////////////////////////////////////////
	//   connection
	/////////////////////////////////////////////////////////////////////////////////
	public List<Map<String, Object>> getTable(Connection conn,AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus,
			Context context) throws SQLException {
		return this.query(conn,daoSingle, methodName, dataBus, table, context);
	}

	public List<Object> getSet(Connection conn,AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException {
		return this.query(conn,daoSingle, methodName, dataBus, set, context);
	}

	public Map<String, Object> getRecord(Connection conn,AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException {
		return this.query(conn,daoSingle, methodName, dataBus, record, context);
	}

	public <T> T getValue(Connection conn,AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException {
		return (T) this.query(conn,daoSingle, methodName, dataBus, value, context);
	}

	////////////////////////////////////////////////////////////////////////////////////
	//
	///////////////////////////////////////////////////////////////////////////////////
	protected <T> T query(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus,
			ResultSetHandler<T> rsh, Context context) throws SQLException {
		// ����Ĳ�����û��ȷ��
		CachePreparedMap invoke = invoke(daoSingle, methodName, new Object[] { context, dataBus });
		Object[] params = getAllValue(invoke, dataBus);
		String sql = invoke.getFinalSql();
		return super.query(conn, sql, rsh, params);
	}

	protected <T> T query(AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, ResultSetHandler<T> rsh,
			Context context) throws SQLException {
		// ����Ĳ�����û��ȷ��
		CachePreparedMap invoke = invoke(daoSingle, methodName, new Object[] { context, dataBus });
		Object[] params = getAllValue(invoke, dataBus);
		String sql = invoke.getFinalSql();

		return super.query(sql, rsh, params);
	}

	public int update(Connection conn, AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context)
			throws SQLException {
		// ����Ĳ�����û��ȷ��
		CachePreparedMap invoke = invoke(daoSingle, methodName, new Object[] { context, dataBus });
		Object[] params = getAllValue(invoke, dataBus);
		String sql = invoke.getFinalSql();
		return super.update(conn, sql, params);
	}

	public int update(AllDaoSuperOfSingle daoSingle, String methodName, DataBus dataBus, Context context) throws SQLException {
		// ����Ĳ�����û��ȷ��
		CachePreparedMap invoke = invoke(daoSingle, methodName, new Object[] { context, dataBus });
		Object[] params = getAllValue(invoke, dataBus);
		String sql = invoke.getFinalSql();

		return super.update(sql, params);
	}

	// class Execute{
	// String sql;
	// Object[] params;
	// public Execute(String sql, Object[] params) {
	// this.sql = sql;
	// this.params = params;
	// }
	// }
	// public /*Object[]*/Execute getObjectArray(AllDaoSuperOfSingle daoSingle,
	// String methodName,Map<String,Object> dataBus) {
	// //����Ĳ�����û��ȷ��
	// CachePreparedMap invoke = invoke(daoSingle, methodName , null );
	// Object [] objs = getAllValue(invoke, dataBus);
	// return new Execute(invoke.getFinalSql() , objs );
	// }
	/**
	 * ͨ��CachePreparedMap���dataBus�����ֵ.�������г�����..
	 * 
	 * @param sql
	 * @param dataBus
	 * @return
	 */
	public static Object[] getAllValue(CachePreparedMap sql, Map dataBus) {
		Object[] objs = new Object[sql.getSize()];
		for (Entry<String, AddAfterSeeListInterface<Integer>> element : sql.entrySet()) {
			String key = element.getKey();// ���key
			Object object = dataBus.get(key);// ���ִ������.
			// ��λ
			AddAfterSeeListInterface<Integer> value = element.getValue();
			int size = value.size();
			for (int i = 0; i < size; i++) {
				Integer integer = value.get(i);
				objs[integer - 1] = object;
			}
		}
		return objs;
	}

	/**
	 * @author ����� ����saveAllKeyAndValue������Ȩ��..
	 */
	public static final class CachePreparedMap
			extends cn.wlh.util.base.adapter.dbutils.apache.PreparedOfCache.CachePreparedMap {
		@Override 
		public void saveAllKeyAndValue() {
			throw new RuntimeException("�벻Ҫʹ�ø÷���..�������ϵͳʹ��..");
		}

		/**
		 * ����Ҳ����ʹ�� ����saveAllKeyAndValue������Ȩ��..
		 */
		void saveAllKeyAndValue000() {
			super.saveAllKeyAndValue();
		}
	}

	/**
	 * ����Ҳ�͹涨daoҪ����CachePreparedMap�Ҳ�ҪsaveAllKeyAndValue
	 * 
	 * @param daoSingle
	 * @param methodName
	 * @param objs
	 * @return
	 */
	public static CachePreparedMap invoke(AllDaoSuperOfSingle daoSingle, String methodName, Object[] objs) {
		// CachePreparedMap cachePreparedMap = daoSingle.get(methodName);
		// if( cachePreparedMap == null ) {
		// //�ж��ǲ�����Ҫ����..
		// _Method.getMethod(tar, name, pars, false);
		//
		// }else if( cachePreparedMap.isEmpty() ) { //�Կռ任ʱ��.��Ҫ�ڵ�����Set<Boolean>
		// ���������ж���.̫���ͺ��ڴ�
		// //��ע������.������..���뷴��.
		// }else {
		//
		// }
		Object object = daoSingle.get(methodName);
		// �ж��ǲ�����Ҫ����..
		if (object instanceof Method) {
			// ��ע������.������..���뷴��.
			Method method = (Method) object;
			try {
				CachePreparedMap map = (CachePreparedMap) method.invoke(daoSingle, objs);
				// û�в�����ע��..
				if (method.getAnnotation(NoCache.class) != null) {
					// save��Լ�ڴ�..
					// map.saveAllKeyAndValue();
					map.saveAllKeyAndValue000();
					daoSingle.put(method.getName(), map);
				}
				return map;
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				throw new RuntimeException("�������ʹ�÷����Ĳ�������ȷ");
			}
		} else if (object instanceof CachePreparedMap) {
			return (CachePreparedMap) object;
		} else {
			throw new NullPointerException("����(����������)��û���������..");
		}
	}

	/**
	 * ������������:�����и�����,���Map�����O(N-2)��Ч�ʽ����.����ֱ�ӷ���.<br/>
	 * �����ǵ����ġ� ������Ҫע���������ķ���Ϊ˽�е����..
	 */
	public static class AllDaoSuperOfSingle {
		// Map<String, CachePreparedMap> cacheMap =
		// JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_FIELD);
		Map<String, Object> cacheMap = JavaUtilFactory.newMap(JavaUtilFactory.SELECT_OF_FIELD);

		protected AllDaoSuperOfSingle() {
			// �Ǽǵ�cn.wlh.util.base.adapter.dbutils.apache.DaoMethodReturnCache.cacheMap ����
			// DaoMethodReturnCache.cacheMap.put(this.getClass(), this); //�����������α���..
			// ����Ǽ����������Լ��ķ���.. ͬʱ���Ҳ�Ͳ�����cachePreparedMap��.  true--��������з���.
			_Method.getMethods(this.cacheMap, true, getClass(),  MethodFilter.NO_FILTER);
		}

		public Object get(Object key) {
			return cacheMap.get(key);
		}

		public Object put(String key, CachePreparedMap value) {
			return cacheMap.put(key, value);
		}
	}

	/**
	 * @author ����� ʵ��һЩ��sql99�Ĺ淶���
	 */
	public static class Sql99<DataBus extends Map> extends AllDaoSuperOfSingle {
		public static final String INSERT = "INSERT INTO ";
		public static final String SELECT = "SELECT ";
		public static final String FROM = "FROM ";
		public static final String WHERE = "WHERE ";
		public static final String SET = "SET ";

		/**
		 * �������� insert into tableName(key1,key2) values (?,?) ͨ��dataBus�����keyʵ�ֶ�̬�������
		 * 
		 * @param tableName
		 * @param dataBus
		 * @return
		 */
		protected CachePreparedMap insertOfNormal(String tableName, DataBus dataBus) {
			Objects.requireNonNull(tableName);
			if (dataBus == null || dataBus.isEmpty())
				throw new NullPointerException();
			// ��ʼ��
			StringBuilder sb00 = new StringBuilder(INSERT);
			Append sb = new Append(sb00);
			sb.append(tableName);

			sb.addLeftParenthesis();

			// ���key1,key2 values (? , ? , ?)
			CachePreparedMap cacheMap = new CachePreparedMap();
			Iterator<Entry<String, Object>> iterable = dataBus.entrySet().iterator();
			Entry<String, Object> next = iterable.next();
			String key = next.getKey();
			Object value2 = next.getValue();
			// ���key
			sb.append(key);
			int current = 1; //
			int keyIndex = sb.getSb().length(); // ��һ��key��λ��

			// map�Ǽ�..
			cacheMap.putOneOfAutoAdd(key, current++);
			// ���value
			sb.append(" VALUES ");
			sb.addLeftParenthesis();
			sb.addQuestionMark();
			//
			// int ValueIndex = sb.getSb().length();

			while (iterable.hasNext()) {
				key = next.getKey();
				value2 = next.getValue();
				// key
				sb.insertComma(keyIndex);
				sb.insert(keyIndex, key);
				keyIndex = sb.getSb().length();
				// value
				sb.addComma();
				sb.addQuestionMark();
				// map�Ǽ�..
				cacheMap.putOneOfAutoAdd(key, current++);
			}
			// ����)
			sb.insertRightParenthesis(keyIndex);
			sb.addRightParenthesis();
			// save
			cacheMap.saveAllKeyAndValue();
			return cacheMap;
		}

		/**
		 * �����ѯ������..������ �û�ֻ��Ҫдselect������. insert into tableName(key1,key2) <br/>
		 * select a.shenqingh key1, b.key2 from a,b where a.id = b.id <br/>
		 * 
		 * @param tableName
		 * @param cacheMap
		 *            ������������select���..
		 * @return
		 */
		protected CachePreparedMap insertOfTable(String tableName, CachePreparedMap cacheMap) {
			// ���select ���..
			String finalSql = cacheMap.getFinalSql();
			String insertSql = null;
			// ����Ĳ������ﲻ�����á�ֻ��Ҫ��װ��sql������ݾ��С�
			// ��ʼ������...
			Append sb = new Append(new StringBuilder(INSERT));
			sb.append(tableName);
			sb.addLeftParenthesis();
			// ���ﲻ���ñ����ķ�ʽ��subString���.
			// a.shenqingh key1, b.key2
			String substring = finalSql.substring(SELECT.length(), finalSql.indexOf(FROM));
			// �ָ����е� [ a.shenqingh key1 , b.key2 ,....]
			String[] split = substring.split(",");
			// ��һ��
			String colName = getColName(split[0]);
			sb.append(colName);
			// ��2,3...��
			for (int i = 1; i < split.length; i++) {
				sb.addComma();
				colName = getColName(split[1]);
				sb.append(colName);
			}
			// ƴ��������sql���
			sb.addRightParenthesis();
			sb.append(finalSql);
			cacheMap.setFinalSql(insertSql);
			return cacheMap;
		}

		/**
		 * ��̬����select�ֶ�.
		 * 
		 * @param cols
		 * @return SELECT KEY1,KEY2 ... FROM ...�Լ�ʵ��..
		 */
		protected Append dynamicSelect(String... cols) {
			int len;
			Append sb = new Append(new StringBuilder(SELECT));
			// û�о�Ĭ�� select * from
			if (cols == null || (len = cols.length) == 0) {
				// throw new NullPointerException();
				// return new Append( new StringBuilder(SELECT + "* " + FROM) );
				sb.append("* ");
				sb.append(FROM);
				return sb;
			}
			// Append sb = new Append( new StringBuilder(SELECT) );
			sb.append(cols[0]);
			for (int i = 1; i < len; i++) {
				sb.addComma();
				sb.append(cols[i]);
			}
			sb.addSpace();
			sb.append(FROM);
			return sb;
		}

		/**
		 * ��̬���ع����ֶ�.<br/>
		 * �Լ�ʵ�ֵ���� + <br/>
		 * where key1 = ? AND key2 = ?ͬʱ��cacheMap�н����˶�Ӧ������..<br/>
		 * ��Ҫע���һ��.�����ظ���key = ? <br/>
		 * ��ɾ������DMLʱ��ʹ�����������Ҫ�ر�С�ģ����dataBusû��ֵ,�������й�������������ζ�Ž��������ű�
		 * @param cols
		 */
		protected void dynamicWhere(CachePreparedMap cacheMap, DataBus dataBus) {
			dynamicWhere(new Append(new StringBuilder(cacheMap.getFinalSql())), cacheMap, dataBus);
		}

		/** ��ɾ������DMLʱ��ʹ�����������Ҫ�ر�С�ģ����dataBusû��ֵ,�������й�������������ζ�Ž��������ű� */
		protected void dynamicWhere(Append sb, CachePreparedMap cacheMap, DataBus dataBus) {
			// û�����.
			if (dataBus == null || dataBus.isEmpty()) // throw new NullPointerException();
				return;
			// ��ʼ��
			// Append sb = new Append( new StringBuilder(cacheMap.getFinalSql()) );
			Iterator<String> iterator = dataBus.keySet().iterator();
			sb.append(WHERE);
			int i = cacheMap.getSize();
			// ��һ��
			// sb.append(iterator.next());
			// sb.addPareped();
			String key = iterator.next();
			sb.addPareped(key);
			// cacheMap.putOneOfAutoAdd(key, i++);
			// û��ʹ��putʱi=0.��ӦsetObject-���±�Ϊ1
			cacheMap.putOneOfAutoAdd(key, ++i);
			// ��2,3...
			while (iterator.hasNext()) {
				sb.append(" AND ");
				key = iterator.next();
				sb.addPareped(key);
				cacheMap.putOneOfAutoAdd(key, ++i);
			}
			// ����cacheMap����ȥ..
			cacheMap.setFinalSql(sb.toString());
			// return cacheMap;
		}

		/**
		 * @see dynamicSet(CachePreparedMap cacheMap,Append sb , Map<String,Object>
		 *      dataBus )
		 */
		protected CachePreparedMap dynamicSet(Append sb, DataBus dataBus) {
			return dynamicSet(new CachePreparedMap(), sb, dataBus);
		}

		/**
		 * ��̬����set�ֶ�.<br/>
		 * �Լ�ʵ�ֵ���� + <br/>
		 * SET key1 = ?,key2 = ?,ͬʱ��cacheMap�н����˶�Ӧ������..<br/>
		 * ��Ҫע���һ��.�����ظ���key = ? <br/>
		 * 
		 * @param cols
		 * @return cacheMap --���ﻹû�а�sql���Ž�ȥ.. ͨ������ʹ������֮���ʹ��dynamicWhere�ӹ���������ʹ��..
		 */
		protected CachePreparedMap dynamicSet(CachePreparedMap cacheMap, Append sb, DataBus dataBus) {
			// û�����.
			if (dataBus == null || dataBus.isEmpty()) // throw new NullPointerException();
				return cacheMap;
			// ��ʼ��
			Iterator<String> iterator = dataBus.keySet().iterator();
			sb.append(SET);
			int i = cacheMap.getSize() + 1;
			// ��һ��
			// sb.append(iterator.next());
			// sb.addPareped();
			String key = iterator.next();
			sb.addPareped(key);
			cacheMap.putOneOfAutoAdd(key, i++);
			// ��2,3...
			while (iterator.hasNext()) {
				sb.addComma();
				key = iterator.next();
				sb.addPareped(key);
				cacheMap.putOneOfAutoAdd(key, i++);
			}
			return cacheMap;
		}

		/**
		 * ��һ�������������л����ʵ������.<br/>
		 * ��:a.shenqingh key1==key1 <br/>
		 * shenqingh as key1==key1<br/>
		 * shenqingh 'key1'==key1<br/>
		 * a.shenqingh ==shenqingh<br/>
		 * 
		 * @param fullName
		 * @return
		 */
		protected String getColName(String fullName) {
			String[] split = fullName.split(" ");
			switch (split.length) {
			case 1:
				String zz[] = split[0].split(".");
				if (zz.length == 2) {
					return zz[1];
				} else {
					return zz[0];
				}
			case 2:
				return split[1].replaceAll("'", "");
			case 3:
				return split[2].replaceAll("'", "");
			default:
				return "";
			}
		}
	}

	/**
	 * @author �����<br/>
	 *         ��StringBuilder����ǿ..<br/>
	 *         ���̰߳�ȫ����.����ֻ�ڷ�����ʹ��..<br/>
	 */
	public static class Append extends AbstractStringBuilder {
		public Append(StringBuilder sb) {
			super(sb);
		}

		/**
		 * ��� ?
		 */
		public final void addQuestionMark() {
			append('?');
		}

		/**
		 * ��� =?
		 */
		public final void addPareped() {
			append("=?");
		}

		/**
		 * ��� key=?
		 * 
		 * @param key
		 */
		public final void addPareped(String key) {
			append(key);
			addPareped();
		}

		/**
		 * ��� �ո�' '
		 */
		public final void addSpace() {
			append(' ');
		}

		/**
		 * ��� ,
		 */
		public final void addComma() {
			append(',');
		}

		public final void insertComma(int index) {
			insert(index, ',');
		}

		/**
		 * ���������
		 */
		public final void addLeftParenthesis() {
			append('(');
		}

		/**
		 * ���������
		 * 
		 * @param sb
		 * @
		 */
		public final void addRightParenthesis() {
			append(')');
		}

		public final void insertRightParenthesis(int index) {
			insert(index, ')');
		}
	}
}
