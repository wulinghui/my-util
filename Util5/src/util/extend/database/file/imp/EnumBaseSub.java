package util.extend.database.file.imp;

import java.io.IOException;

public abstract class EnumBaseSub<T> extends _FileBase<T> {
	
	public EnumBaseSub(Class<T> cl, String tableDescribe,
			String[] fields, String[] fieldsDescrbe) {
		super( "Enum" , _FileBaseClient.toPath(cl.getName()), tableDescribe, fields, fieldsDescrbe);
	}
	/** 不存在就不必要去文件里面去了
	 */
	public T getBean(String name) throws IOException, Exception{
//		if( isContain(name) ) return null;//不存在就不必要去文件里面去了
		return super.getBean( name );//自然报错.
	}
	/**在描述表里面不存在的key,就不能直接序列化到文件中
	 * 如果项目运行时,需要动态的添加的新的多例.这需要:
	 * 需要热部署的Client.
	 * @param name
	 * @param t
	 * @throws IOException
	 */
	public void storeBean(String name,T t) throws IOException{
//		if(!super.field.keySet().contains(name)) return;
		if( isContain(name) ) return;
		super.storeBean(t,name );
	}
	private boolean isContain(String name){
		return !super.field.keySet().contains(name);
	}
}
