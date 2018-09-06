package cn.wlh.util.base;

import java.io.File;

/**
 * @author �����
 * Ĭ����maven
 */
abstract class _PathFacade {
	
	/** �ļ�ת����
	 * @param file �ļ���  c:/work/.../com/cn/Test.class
	 * @return com.cn.Test
	 */
	public abstract  String absolutePath2ClassName(File file);
	/**����ת·��
	 * @param className ����  com.cn.Test
	 * @return c:/work/.../com/cn/Test
	 */
	public abstract  String className2AbsolutePath(String className);
	/**javaԴ�����·��
	 * @return
	 */
	public abstract String getSrc_relative() ;
	/**javaԴ�����·��
	 * @return
	 */
	public abstract String getSrc_absolute() ;
}
