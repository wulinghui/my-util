package cn.wlh.util.extend.complierter2.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wlh
 * ����.js�ļ�
 */
public class JavaScriptEntity {
	List<String> list = new ArrayList<>();
	public JavaScriptEntity function(String inner) {
		String aa = "var  "+1+"= function(){"
				+ inner 
				+ "}";
		list.add(aa);
		return this;
	}
	
}
