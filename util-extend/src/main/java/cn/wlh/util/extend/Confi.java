package cn.wlh.util.extend;

import java.util.Map;

import cn.wlh.util.base._Class;
import cn.wlh.util.base._File;
import cn.wlh.util.extend.dao.SqlBcui;
import cn.wlh.util.extend.dao.sql.Dao;
    
/**
 * @author wlh
 * ���������е�Config--����������com.novalue���µ�������д
 * �ӳ����õ�,ȷ��value	����ȷ����key
 */
public abstract class Confi {        
	public static  Confi config ;
	public Confi() { config = this;	}
	static {
		try {
			config =  (Confi) _Class.newObj(  _Class.forName("com.novalue.Config" ) );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** ���dao�����й�������,ͨ��MethodOfPackage�����
	 * ��DaoProxy����ʹ��  		��ָ��dao�İ���       --�ð��µ���
	 * */
	public abstract Class<?> getDaoAllMethodByMethodOfPackage();
	/** ���biz�����й�������  @see getDaoAllMethodByMethodOfPackage*/
	public abstract Class<?> getBizAllMethodByMethodOfPackage();
	/** DaoProxy ��dao�����÷�*/
	public abstract String[] drivers();
	/** DaoProxy ����dao��  */
	public abstract Map<String,SqlBcui> sqlBcuis();
	/** complier��Dao  ����dao��*/
	public abstract Map<String,Dao> complierDaos();
	
}
