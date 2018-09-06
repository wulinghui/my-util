package cn.wlh.util.base;

import org.apache.commons.lang3.StringUtils;

/**
 * �ַ���������
 * @since 1.0.0
 */
public final class StringUtil {

    /**�ַ����ָ���
     */
    public static final String SEPARATOR = String.valueOf((char) 29);

    /** �ж��ַ����Ƿ�Ϊ��
     */
    public static boolean isEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    /** �ж��ַ����Ƿ�ǿ�
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    
    /** �ָ�̶���ʽ���ַ���
     */
    public static String[] splitString(String str, String separator) {
        return StringUtils.splitByWholeSeparator(str, separator);
    }
}