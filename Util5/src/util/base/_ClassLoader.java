package util.base;

import java.io.File;
import java.io.IOException;

import javax.tools.JavaFileObject.Kind;

public class _ClassLoader  {
	String url;
	String kind;
	/** url ����ֻ�ܷ�2���Ƶ�.class�ļ�����
	 *  @param url  --��Ŀ¼.
	 *  @param suff --�ļ���׺.
	 */
	public _ClassLoader(String url, String suff) {
		super();
		this.url = url;
		this.kind = suff;
	}
	public _ClassLoader(String url, Kind kind) { this(url, kind.extension); }
	public _ClassLoader(String url) {this(url,Kind.CLASS);}  
	
	public class Loader extends ClassLoader {
		File file;
		String fullClassName;
		
		public Loader(String fullClassName) throws IOException {
			super(null); // ָ�����������Ϊ null
			loader(fullClassName);
		}
		public Loader(String[] fullClassNames) throws IOException {
			super();//Ĭ��ָ�� ϵͳ�ļ�����..
			for (String string : fullClassNames) {
				loader(string);
			}
		}
		private void loader(String fullClassName) throws IOException {
			this.fullClassName = fullClassName;
			 String str = _File.joinPath(url , _File.toSeparator(fullClassName) + kind );
			file = new File ( str ) ;
			byte[] raw = _Reader.notOutOfMemory0(file);
			// ���,��class�����ݼ��ص�jvm��  -- ���һ��Ҫ��...����û����jvm��û����
			super.defineClass(fullClassName , raw, 0, raw.length);
		}
//	   @Override // ����дҲ��
//	    protected Class loadClass(String name, boolean resolve)  
//	            throws ClassNotFoundException {
//	        Class cls = null;  
//	        cls = super.findLoadedClass(name);  // ϵͳ�� ��     
//	        if (cls == null)  
//	            cls = super.getSystemClassLoader().loadClass(name); // ϵͳû��  
//	        if (cls == null)  
//	            throw new ClassNotFoundException(name);  
//	        if (resolve)  
//	        	super.resolveClass(cls);  //ϵͳ��
//	        return cls;  
//	    }
	         
	}
}  
