package util.base;

import java.io.File;
import java.io.Serializable;

/**
 * @author wlh
 * ����䵱IOC��Config--�ڹ������е���.
 * ��ά���鲻��,����,ֻ��׷��.
 * �����ýӿڵľ�̬���������ڲ���.��ö��
 * ����Ϊ����..
 //��ϵͳ��.�û��޷���չ--ȷ����key �� value
 */
@SuppressWarnings("serial")
public interface Config extends Cloneable, Serializable {
	public static final Object[][] CONF = new Object[][]{
		{//[0] -- File
			 ".java"//
			,"src"//
			,"wlh"
		},{//[1] -- Signle
			"SIGNLE"
		}
	};
	int INT [][] = new int[][]{
		{//[0] -- File	
			File.separator.length()// "\"	
			,( (String)CONF[0][1]).length()//".java"
		}
	}; 
}
