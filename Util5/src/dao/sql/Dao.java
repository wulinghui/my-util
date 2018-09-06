package dao.sql;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import util.base._Exception;
import util.base._Exception.ToNullPointerException;
import util.base._Method.MethodFilter;
import util.extend.Confi;
import util.extend.MethodOfPackage;
/**
 * @author wlh 
 * �﷨��.   ���û���������
 */
public class Dao {

	OraclePreStatSql dao;

	public Dao(String sourceData) {
		dao = DaoProxy.get(OraclePreStatSql.class, sourceData);
	}

	public Integer commit(String sql, Object word) throws SQLException {
		return (Integer) dao.operationDao(sql, false, word);
	}

	public List<Map<String, Object>> query(String sql, Object word) throws SQLException {
		return (List<Map<String, Object>>) dao.operationDao(sql, true, word);
	}

	
/*
 * 
 *  ����������ķ���...
 * 	
 */
	public static final MethodOfPackage.Simple methodsOfDao = _Exception
			.toRuntime(new ToNullPointerException<MethodOfPackage.Simple>() {
				@Override
				public MethodOfPackage.Simple handle() throws Throwable {   
					return new MethodOfPackage.Simple(Confi.config.getDaoAllMethodByMethodOfPackage(), false ,MethodFilter.OBJECT );
				}
			});      
	@Deprecated
	protected String getSql(String table, String method, Word word)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object obj = word.get(Word.DAO_METHOD_INPUT);//̫����
		
//		Object args [] = obj == null ? new Object[0] : (Object[]) obj; 
		return (String) methodsOfDao.invoke( word.get(Word.DAO_OBJ),//
				table, method,  obj );//        
	}

	public List<Map<String, Object>> query(String table, String method, Word word)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		return query(getSql(table, method, word), word.get(Word.SQL_INPUT)  );
	}

	public Dao execute(String table, String method, Word word, String out)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		// �����������word����...
//		word.put(out, dao.operationDao(getSql(table, method, word), (Boolean) word.get(Word.RS), word.get(Word.DAO_INPUT) ) );
		String str = getSql(table, method, word).toUpperCase();
		word.put(out, dao.operationDao( str , str.startsWith("SELECT") , word.get(Word.SQL_INPUT) ) );
		return this;
	}
	/**���� getSql()*///������
	public Object execute(String table, String method, Object daoObj ,
			Object[] daoArgs , boolean isXuykRs, Object sqlInputs) throws SQLException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Object daoInner = methodsOfDao.invoke(daoObj, table, method, daoArgs);
		if(daoInner ==null) return null;
		return dao.operationDao(daoInner.toString(), isXuykRs, sqlInputs);
	}
	/** ���˹涨 �������������table*/
	public Object execute(String table, String method, 
			Word word , Object sqlInputs) throws SQLException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//dao�����Ǿ�̬����	,�Ҳ����� Word ����   �޲�(null)
//		Object daoInner = methodsOfDao.invoke(null, table, method, daoArgs);
		//���ӻ���,����ϵͳ��invoke.��ԭ����..
		Entry<Class<?>, Map<String, Method>> map =  methodsOfDao.getEntry(table);
		//����Word��.
		word.put( Word.DAO_ClASS, map );
		//ʹ��,��Word---û�н�ά��.�Ž�ȥ
		Object daoInner =  getDaoInner(map, method, word);//map.get(method).invoke(null,  word );
		if(daoInner ==null) return null;
		return execute( daoInner , sqlInputs);
	}
	public Object execute( String method, 
			Word word , Object sqlInputs) throws SQLException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		//ʹ�û���,����ϵͳ��invoke.��ԭ����..
		Entry<Class<?>, Map<String, Method>> map =  (Entry<Class<?>, Map<String, Method>>) word.get(Word.DAO_ClASS);
		//ʹ��,��Word---û�н�ά��.�Ž�ȥ
		Object daoInner = getDaoInner(map, method, word);//map.get(method).invoke(null,  word );
		if(daoInner ==null) return null;
		return execute( daoInner , sqlInputs);
	}
	//Ϊ���������ʹ��Spring Bean Aop
	protected Object getDaoInner( Entry<Class<?>, Map<String, Method>> map , String method ,Word word) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		//���� ��̬����..
		return map.getValue().get(method).invoke(null,  word );
	}
	public Object execute(Word word) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		return  execute( (String) word.get(Word.DAO_METHOD) ,  word , word.get( Word.SQL_INPUT ) );
	}
	public Dao execute(Word word,String out) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		word.put(out, execute(word) ); return this;
	}   
		
		
	//daoInner -- sql���
	public Object execute(Object daoInner, Object sqlInputs) throws SQLException {
		String sql = daoInner.toString().toUpperCase();
		// ͨ��sql.startsWith("SELECT")���ж�.
		return dao.operationDao( sql , sql.startsWith("SELECT") , sqlInputs);
	}
	
	public Integer commit(String table, String method, Word word)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		return commit(getSql(table, method, word), word.get(Word.SQL_INPUT) );
	}
}