package util.extend.freemaker.fm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.TemplateException;


public class FreemarkerTest {

	public static void main(String[] args) throws IOException, TemplateException {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("username", "${username321}");//��ftl��Ҫ��ֵ�ı���
		root.put("user", new User(1,"name1",11,new Group("��1")));
		List<User> users = new ArrayList<User>();
		users.add(new User(1,"name1",11));
		users.add(new User(2,"name2",15));
		
		users.add(new User(3,"name3",20));
		users.add(new User(4,"name4",30)); 
		root.put("users", users);
		
		FreemarkerUtil util = new FreemarkerUtil();
//		util.fprint("001.jsp", root, "0001.jsp");
		
		util.fprint("01.ftl", root, "01.ftl");//������ftl�ļ�...
		/*TODO ���������������ftlģ��,����������ģ���������ģ��..  ����Ҳ���Է���${username321}
		������,���ƶ�������:��һ��ģ���ļ�01.ftl��ͨ��01���ļ���ͨ��FileMap��������Map.
		������ftl...�������û�б�Ҫ.
		 *    
		 */
		util.fprint("02.ftl", root, "02.ftl");
		util.fprint("02.ftl", root, "02.ftl");
		util.fprint("02.ftl", root);
		util.fprint("03.ftl", root, "03.html");
		util.fprint("04.ftl", root, "04.html");
		util.fprint("05.ftl", root, "05.html");
		util.fprint("06.ftl", root, "06.html");
		util.fprint("02.ftl", "05.html", "src","ftl","a01");
	}
	
}