package cn.wlh.util.base;

import java.io.File;


public class _Path {
	static _PathFacade pfc = getInstance();
	
	public static _PathFacade getInstance() {
		return new _PathFacadeMaven();
	}
	/**��Ŀ·�� */
	public static final String WOKE_PATH = System.getProperty("user.dir");
	/**��Ŀ���ļ���*/
	public static final File WOKE_FILE = new File( WOKE_PATH );
	/**�ָ���ĳ���*/
	public static final int SEPARATOR_LENGTH = File.separator.length();
	/**ƴ��·��
	 * @param strings
	 * @return
	 */
	public final static   String joinPath(String...strings ){
		int len = strings.length;
		String string = strings[0];
		for (int i = 1; i < len ; i++) {
			string += File.separatorChar + strings[i];
		}
		return string;
	}
	/** ����ȫ�޶���ת���ɲ�ͬϵͳ��·���ָ��� */
	public final static String toSeparator(String classFullName){return classFullName.replaceAll("\\.", "\\"+ File.separator);
	}/**�Ѳ�ͬϵͳ��·���ָ���ת������ȫ�޶���*/
	public final static String toPoint(String pathSeparater){return pathSeparater.replaceAll("\\"+File.separator, "\\.");}
	
	/** �ļ�ת����
	 * @param file �ļ���  c:/work/.../com/cn/Test.class
	 * @return com.cn.Test
	 */
	public static String absolutePath2ClassName(File file) {
		return pfc.absolutePath2ClassName(file);
	}
	/**
	 * @param file ֧�����,����,ͳһ��λ.
	 * @return
	 */
	public static String absolutePath2ClassName(String file) {
		return pfc.absolutePath2ClassName(new File(file));
	}
	/**����ת·��
	 * @param className ����  com.cn.Test
	 * @return c:/work/.../com/cn/Test
	 */
	public static  String className2AbsolutePath(String className) {
		return pfc.className2AbsolutePath(className);
	}
	/**javaԴ�����·��
	 * @return
	 */
	public static String getSrc_relative() { 
		return pfc.getSrc_relative();
	}
	/**javaԴ�����·��
	 * @return
	 */
	public static String getSrc_absolute() {
		return pfc.getSrc_absolute();
	}
}
