package util.extend.database.file.imp;

import java.io.IOException;


/**
 * @author wlh
 * 只需要读取一个.单例对象,
 * @param <T>
 */
public abstract class SingleBaseSub<T> extends _FileBase<T> {
	public static String id = "Single";
	public SingleBaseSub(Class<T> cl, String tableDescribe) {
		super(id , _FileBaseClient.toPath( cl.getName() ),
				tableDescribe, new String[]{ id } , new String[]{tableDescribe});
	}
	public T getBean() throws IOException, Exception{
		return super.getBean( id);
	}
	public void storeBean(T t) throws IOException{
		super.storeBean(t, id );
	}
//	public static void main(String[] args) {
//		System.out.println(String.class.getPackage().getName());
//	}
}
