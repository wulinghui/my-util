package util.extend.database.file.imp;

import java.io.IOException;
import java.util.Set;

/**该类和Enum的区别.这个是把多个放到一个文件里面
 * Enum是把多个相同的放到不同的文件里面
 * @author wlh
 * 该类主要是为了应对不同策略模式的产物.
 * Set - 相同的描述.放到不同的文件中
 * 当需要切换时,就直接用id获得了.这样就能成为策略模式了.
 * @param <T>
 */
public abstract class SetSameBaseSub<T> extends _FileBase<Set<T>> {
	public SetSameBaseSub( Class<T> cl, String tableDescribe,
			String[] fields, String[] fieldsDescrbe) {
		super("Set", _FileBaseClient.toPath(cl.getName()) 
				, tableDescribe, fields, fieldsDescrbe);
	}
	public Set<T> getBean(String id) throws IOException, Exception{
		return super.getBean(id);
	}
	public void sotreBean(String id,Set<T> t) throws IOException{
		super.storeBean(t, id);
	}
}
