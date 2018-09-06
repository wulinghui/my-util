package util.extend;

import java.util.Map;

import dao.sql.Dao;
import dao.sql.SqlBcui;
import util.base._Class;
import util.base._Exception;
    
/**
 * @author wlh
 * 该类是所有的Config--交给子类在com.novalue包下的子类重写
 * 子程序用的,确定value	但是确定了key
 */
public abstract class Confi {        
	public static final Confi config =  _Exception.toRuntime(new _Exception.ToNullPointerException<Confi>() {
		@Override 
		public Confi handle() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
			//必需这个类     -- com.novalue.Confi
			return (Confi) _Class.newObj(  _Class.forName("com.novalue.Config" ) );
		}
	});
	/** 获得dao的所有公开方法,通过MethodOfPackage这个类
	 * 在DaoProxy类中使用  		请指定dao的包名       --该包下的类
	 * */
	public abstract Class<?> getDaoAllMethodByMethodOfPackage();
	/** 获得biz的所有公开方法  @see getDaoAllMethodByMethodOfPackage*/
	public abstract Class<?> getBizAllMethodByMethodOfPackage();
	/** DaoProxy 的dao驱动用法*/
	public abstract String[] drivers();
	/** DaoProxy 操作dao类  */
	public abstract Map<String,SqlBcui> sqlBcuis();
	/** complier的Dao  操作dao类*/
	public abstract Map<String,Dao> complierDaos();
}
