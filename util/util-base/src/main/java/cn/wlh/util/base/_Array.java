package cn.wlh.util.base;

import java.lang.reflect.Array;

/**
 * java.lang.reflect.Array
 * @author wlh
 *
 */
public class _Array {
	public static <T> T[] getArrays(Class<T> componentType , int length) {
		return (T[]) Array.newInstance(componentType, length);
	}
	/**
	 * @param strings
	 * @return true �ǿյ�.
	 */
	public static boolean isBank( String[] strings ) {
		return strings == null ;
	}
		/**�Ƿ���������.
		 * @param c
		 * @param strChar
		 * @return true-��������
		 */
		public static boolean lookUp(char c , char[] strChar) {
			for (char d : strChar) {
				if( d == c) return true; //��
			}
			return false;//����
		}
}
