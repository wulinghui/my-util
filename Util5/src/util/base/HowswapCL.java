package util.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashSet;
  
/* 
 *  ʵ���Ȳ����Զ���ClassLoader�����ص���.class 
 */  
public class HowswapCL extends ClassLoader {  

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Thread t;
		t = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					while (true) {
						// ÿ�ζ�������һ���µ��������
						// class��Ҫ�����Լ�package���ֵ��ļ�����
						String url = System.getProperty("user.dir") + "/lib";// "/lib/yerasel/GetPI.jar";
						HowswapCL cl = new HowswapCL(url,
								new String[] { "yerasel.GetPI" });
						//  ���¼���Class
						Class cls = cl.loadClass("yerasel.GetPI");
						Object foo = cls.newInstance();
						// �����ú����Ĳ���
						Method m = foo.getClass().getMethod("Output",
								new Class[] {});
						m.invoke(foo, new Object[] {});
						Thread.sleep(500);
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		t.start();
	}
	
	
    private String basedir; // ��Ҫ���������ֱ�Ӽ��ص����ļ��Ļ�Ŀ¼  
    private HashSet dynaclazns; // ��Ҫ�ɸ��������ֱ�Ӽ��ص�����  
   
    public HowswapCL(String basedir, String[] clazns) {  
        super(null); // ָ�����������Ϊ null  
        this.basedir = basedir;  
        dynaclazns = new HashSet();  
        loadClassByMe(clazns);  
    }  
  
    private void loadClassByMe(String[] clazns) {  
        for (int i = 0; i < clazns.length; i++) {  
            loadDirectly(clazns[i]);  
            dynaclazns.add(clazns[i]);  
        }  
    }  
  
    private Class loadDirectly(String name) {  
        Class cls = null;  
        StringBuffer sb = new StringBuffer(basedir);  
        String classname = name.replace('.', File.separatorChar) + ".class";  
        sb.append(File.separator + classname);  
        File classF = new File(sb.toString());  
        try {  
            cls = instantiateClass(name, new FileInputStream(classF),  
                    classF.length());
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return cls;  
    }  
  
    private Class instantiateClass(String name, InputStream fin, long len) {  
        byte[] raw = new byte[(int) len];  
        try {  
            fin.read(raw);  
            fin.close();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
  
        return defineClass(name, raw, 0, raw.length);  
    }  
    @Override
    protected Class loadClass(String name, boolean resolve)  
            throws ClassNotFoundException {  
        Class cls = null;  
        cls = findLoadedClass(name);  
        if (!this.dynaclazns.contains(name) && cls == null)  
            cls = getSystemClassLoader().loadClass(name);  
        if (cls == null)  
            throw new ClassNotFoundException(name);  
        if (resolve)  
            resolveClass(cls);  
        return cls;  
    }  
}