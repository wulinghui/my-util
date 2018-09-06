package cn.wlh.util.base;

import java.io.File;
import java.io.Serializable;

import javax.tools.JavaFileObject.Kind;

/**
 * @author wlh
 * ����䵱IOC��Config--�ڹ������е���.
 * ��ά���鲻��,����,ֻ��׷��.
 * �����ýӿڵľ�̬���������ڲ���.��ö��
 * ����Ϊ����..
 //��ϵͳ��.�û��޷���չ--ȷ����key �� value

  ����:͸��,�Ҳ���ҪΪ�����ַ���.
 */
@SuppressWarnings("serial")
public interface Config extends Cloneable, Serializable {
	public static final String[][] CONF = new String[][]{
		{/**[0]*/ //-- File
			 Kind.SOURCE.extension//
			,"src"  //
			,"main" //maven��Source·��
			,"java" //maven��Source·��
			,"wlh"  
		},{/**[1]*/ //-- Signle
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
