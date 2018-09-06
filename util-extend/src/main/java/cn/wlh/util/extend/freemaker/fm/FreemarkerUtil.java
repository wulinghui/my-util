package cn.wlh.util.extend.freemaker.fm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Map;

import cn.wlh.util.base._File;
import cn.wlh.util.extend.map.FileMap;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public class FreemarkerUtil {
	final String basePackagePath;
	final String outFilePath;
    public FreemarkerUtil(String basePackagePath, String outFile) {
		this.basePackagePath = basePackagePath;
		this.outFilePath = outFile;
	}
	public FreemarkerUtil( String basePackagePath ,String... strings) {
		this( basePackagePath , _File.getAbsoluteFromWork( strings ) );
	}
	public FreemarkerUtil(  ) {
		this( "/ftl" , _File.getAbsoluteFromWork("src","page" ) );
	}
	public Template getTemplate(String name) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
            // ͨ��Freemaker��Configuration��ȡ��Ӧ��ftl
            Configuration cfg = new Configuration();
            // �趨ȥ�����ȡ��Ӧ��ftlģ���ļ�
            cfg.setClassForTemplateLoading(this.getClass(), this.basePackagePath );
            // ��ģ���ļ�Ŀ¼���ҵ�����Ϊname���ļ�
            Template temp = cfg.getTemplate(name);
            return temp;
    }
	
    /**
     * ����̨���
     * @param name
     * @param root
     * @throws IOException 
     * @throws TemplateException 
     * @throws ParseException 
     */
    public void fprint(String name, Map<String, ?> root) throws TemplateException, IOException, ParseException {
            // ͨ��Template���Խ�ģ���ļ��������Ӧ����
            Template temp = this.getTemplate(name);
            temp.process(root, new PrintWriter(System.out));
    }
    
    /**
     * ���HTML�ļ�
     * @param name
     * @param root
     * @param outFile
     * @throws IOException 
     * @throws TemplateException 
     * @throws ParseException 
     */
    public void fprint(String name, Map<String,?> root, String outFile) throws IOException, TemplateException, ParseException {
        FileWriter out = null;
        try {
            // ͨ��һ���ļ���������Ϳ���д����Ӧ���ļ��У��˴��õ��Ǿ���·��
            out = new FileWriter(new File( _File.joinPath( this.outFilePath , outFile )   ));
            Template temp = this.getTemplate(name);
            temp.process(root, out);
        } finally {
                if (out != null) out.close();
        }
    }
    /** ͨ���ļ����Map.��ִ�������fpring
     * @param name
     * @param outFile
     * @param strings 
     * @throws IOException
     * @throws TemplateException 
     * @throws ParseException 
     */
    public void fprint(String name,  String outFile,String...strings) throws IOException, TemplateException, ParseException {
    	FileMap map = new FileMap();
    	Map<String, String> root = map.initMap(strings);
    	fprint(name, root,outFile);
    }
}