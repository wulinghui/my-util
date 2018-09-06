package util.extend.map;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import util.base._File;
import util.base._File.Filter;
import util.base._File.HandlerFile;
import util.base._Reader;
import util.extend.wrap.StringWrap;

/**����������������FreeMarker�����Map����.
 * @author wlh
 * ���һ���ļ���Map
 * Ĭ�����ļ���ΪKey  �ļ����������ΪValue
 * ������д���˵ķ�ʽ
 * Ҳ������д���Key�ķ�ʽ
 * ��������д���Value������...
 */
public class FileMap {
	final Filter filter;
	final MapHanler handler;
	
	/**�Լ�����
	 * @param filter  	���˵ķ�ʽ
	 * @param handler	�Լ�����key�ķ���
	 * ���ǿ��������õݹ�Ļ��ĳһ���������е��ļ�.�������ļ��Ϳ��������ȼ���˵����
	 */
	public FileMap(Filter filter, MapHanler handler) {
		this.filter = filter;
		this.handler = handler;
	}
	/** ���ݹ� �Ĺ���
	 * */
	public FileMap() {
		this( Filter.NODIGUI , new MapHanler( new StringWrap.Map() ) );
	}
	
	/**
	 * @param strings ���·��.
	 */
	public Map<String,String> initMap(String...strings) throws IOException{return initMap0( _File.getAbsoluteFromWork(strings));}
	/**
	 * @param file ����·��
	 */
	public Map<String,String> initMap0(String file ) throws IOException{ return initMap(new File(file));}
	/**
	 * @param fi �ļ���.�������Map
	 */
	public Map<String,String> initMap(File fi ) throws IOException  {
//		System.out.println( "initMap=" + fi );
		_File.forFile( fi, this.filter,  this.handler);
		return handler.map;
	}
	public static void main(String[] args) throws IOException {
		//		src/util/base/jdk7
		File file = new File( "E:\\workSpace\\Util5\\src\\util\\extend");
//		System.out.println(file.exists());			
//		System.out.println( file.isFile() ); 		
//		System.out.println( file.isDirectory() );	 
//		file.listFiles(new java.io.FileFilter() {
//			
//			@Override
//			public boolean accept(File pathname) {
//				System.out.println("pathname="+pathname);
//				return false;
//			}
//		});
//		_Sysout.print( file.listFiles( new FileFilter.NoDiGui() ) );
		
		System.out.println(new FileMap().initMap("src","util","extend").size() );;
	}
	/**
	 * @author wlh
	 * getKey() -- ȷ��key
	 */
	public static class MapHanler implements HandlerFile<IOException>{
		final Map<String,String> map;
		public MapHanler(Map<String,String> map) {
			this.map = map;
		}
		@Override
		public final void handler(File file) throws IOException  {
			map.put( getKey(file) , 
					String.valueOf( _Reader.notOutOfMemory(file) ) );
		}
		protected String getKey(File file) { return file.getName(); }
	}
}