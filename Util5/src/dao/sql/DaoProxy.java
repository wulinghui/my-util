package dao.sql;

import java.util.Map;

import util.extend.Confi;

/**
 * @author wlh
 * ͳһdao���,
 * ����ʹ������,ʹ���û����򵥵Ĳ���
 */
public abstract class DaoProxy{
	DaoProxy(){}    
	private static final Map<String,SqlBcui> map ;
	static{
		//�ȼ���������.
		String [] drivers = Confi.config.drivers();
		for (int i = 0; i < drivers.length; i++) {
			try {
				Class.forName( drivers[i] );
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		//��map
		map = Confi.config.sqlBcuis();
	}
	//map�ĳ�ʼ��  key=����+����   	value=SqlBcui����
	public final  static <T extends SqlBcui> T get(Class<T> cla,String key){   
		return (T) map.get( key );
	}
}
