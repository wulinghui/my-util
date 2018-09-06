package cn.wlh.util.base.adapter.datasource.dbcp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

/**
 * @author �����
 * ����Ĺ��췽��ֻ�Ǽ򵥵Ŀ�����һ��,û������Bean
 */
public class DBCPUtils {
	/**��ds�ĳɱ����Ϳ���һ��ϵͳ�ж������Դ��,ע��ʹ�ö���*/
	private  DataSource ds = null;
	/** ͳһ����*/
	private static Map<String,DBCPUtils> map = new HashMap<String,DBCPUtils>();
	/** 
	 * @param prop
	 * 
	 */
	DBCPUtils(Properties prop){
		try {
		    ds = BasicDataSourceFactory.createDataSource(prop);//�õ�һ������Դ 
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError("��ʼ���������������ļ�");
		}
	}
	public  DataSource getDataSource(){
		return ds;
	}
	public  Connection getConnection(){
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException("������æ������");
//			Thread.currentThread().sleep( 1000 * 60 );
		}
	}
	
	public static void release(Connection conn,Statement stmt,ResultSet rs){
		//�ر���Դ
				if(rs!=null){
					try {
						rs.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(stmt!=null){
					try {
						stmt.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(conn!=null){
					try {
						conn.close();//�ر�
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
	}
	public static void putInstanceOfDataSource(String id,Properties prop) {
		if( map.get(id) != null ) throw new RuntimeException("����Դ�Ѵ���"); 
		DBCPUtils value = new DBCPUtils(prop);
		map.put(id, value);
	} 
	public static DBCPUtils getInstance(String id) {
		return map.get(id);
	}
}
