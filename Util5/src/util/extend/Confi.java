package util.extend;

import java.util.Map;

import dao.sql.Dao;
import dao.sql.SqlBcui;
import util.base._Class;
import util.base._Exception;
    
/**
 * @author wlh
 * ���������е�Config--����������com.novalue���µ�������д
 * �ӳ����õ�,ȷ��value	����ȷ����key
 */
public abstract class Confi {        
	public static final Confi config =  _Exception.toRuntime(new _Exception.ToNullPointerException<Confi>() {
		@Override 
		public Confi handle() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
			//���������     -- com.novalue.Confi
			return (Confi) _Class.newObj(  _Class.forName("com.novalue.Config" ) );
		}
	});
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
