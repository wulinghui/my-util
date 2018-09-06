package util.base;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public  abstract class _String{
	/** ��strת�� "str" */
	public static String toStringFormatOfTwo(String str) {return "\"" + str.replaceAll("\\s", " ") + "\"";}
	/**
	 * @param str
	 * @return true- = null || =""
	 */
	public static boolean isEmpty(String str){    
		return null==str || str.isEmpty(); 
	}
	public static String array2String(String... s ) {
		String a = "";
		for (String string : s) {
			a += string;
		}
		return a;
	}
	/**ͨ��ָ���������ֵ�ת������ȫ������,���������滻*/
	public static String toFullDataByDictionary(String sqlInner,Map<String,String> map){
		for (String key : map.keySet()) {
			//�������������滻
			sqlInner = sqlInner.replaceAll(key, map.get(key));
		}
		return sqlInner;
	}
	/** �����ַ������������
	 * @param simple false-ǿ������*/
	public static String[] trimArrayOfMee(boolean simple,String[] trim) {
		int len = trim.length;
		if( simple ) {//��
			for (int i = 0; i < len; i++) {
				trim[i] = trim[i].trim();//ֱ��trim����.
			}
		}else {//���˷���ȥ�ո�..
			for (int i = 0; i < len; i++) {
				trim[i] = trim(trim[i]);//ֱ��trim����.
			}
		}
		
		return trim;//Ϊ�������ط���ʹ��,Ҳ���Բ��÷Ż�.
	}
	/** �����������2�ַ�������
	 * @param isSimple true-�򵥵����� 
	 * */
	public static String[] classs2Strings(boolean isSimple,Class<?>... clas) {
		int len = clas.length;
		String [] res  = new String[len];
		if( isSimple ) {//�򵥵����� 
			for (int i = 0; i < res.length; i++) {
				res[i] = clas[i].getSimpleName();
			}	
		}else {//��ȫ�޶���
			for (int i = 0; i < res.length; i++) {
				res[i] = clas[i].getName();
			}
		}
		return res;
	}
	/** �������ֻ�滻��һ��
	 * @param target Ŀ��str
	 * @param startSymbol ��ͷ�ķ���
	 * @param endSymbol ��β�ķ���
	 * @param replacement 
	 * @return [0]:start-end�м�ķ���;[1]:�滻֮��洢�ĵط� 
	 */
	public static String[] getAndReplaceFirst_startAndEnd(String target,String startSymbol,String endSymbol,String replacement){
		int a = target.indexOf(startSymbol);//a+1
		int b = target.indexOf(endSymbol);
		String [] strs = new String[2];
		if(a+b>0) 	
			strs[0] = target.substring(a+1, b);
		strs[1] = target.replaceFirst("["+startSymbol+"].+?["+endSymbol+"]",replacement);
		return  strs;
	}
	/**�滻��������Ҫ���
	 * @param target
	 * @param startSymbol
	 * @param endSymbol
	 * @param replacement
	 * @param list -- ÿ���滻֮��Ĵ�ŵط�
	 * @return --�����滻��str
	 */
	public static String getAndReplaceAll_startAndEnd
		(String target,String startSymbol,String endSymbol,
				String replacement,List<String> list) {
		if(list == null) list = new ArrayList<String>();
//����������Ҫ�������list��?	throw new TsException("null","list = null","����:��ͬ�������ܲ���,���ǲ��ܴ������á�����newһ���µ�Ҳû��");
		boolean flag = true;
		String store = null;
		while(flag){
			int a = target.indexOf(startSymbol);//a+1
			int b = target.indexOf(endSymbol);
			if(a+b>0){
				store = target.substring(a+1, b);
				list.add(store);
				target = target.replaceFirst("["+startSymbol+"].+?["+endSymbol+"]",replacement);
			}else{
				flag = false;
			}
		}	
		return  target;
	}
	/**��Ҫʹ��,ûд�� TODO  �ָ����һ��,�ַ�.  ��:.	��util.extend.complier.java.Dao �е� (��ð���������������java����)���õ�..
	 * */
	public String[] getPackageAndClass(File file) {
		String fullClass = _File.absolutePath2ClassName( file.getAbsolutePath() );
		return fullClass.split("");
	}
	/**ȥ����β�κοհ��ַ��������ո��Ʊ������ҳ���ȡ��� [\f\n\r\t\v] ��Ч��
	 * @param source
	 * @return
	 */
	public static String trim(String source) {
		return source.replaceAll("^\\s+?|\\s+?$", "");
	}
	/**  �������
	 * @param inner
	 * @return "+inner+"
	 */
	public static String insertVar(String inner) {
		return "\"+" + inner + "+\"";
	}
	/** �����������
	 * @param source ԭ�ַ�.	{}Ϊ���������
	 * @param inner     
	 * @return
	 */
	public static String insertInner(String source , String inner) {return source.replaceAll("{}", inner);}
////////////////////////////sub  �����Լ�
	/**sub  OfMe-�����Լ�*/
	public static String subStringOfMe(String str,int begin,String end){return str.substring( begin , str.indexOf(end) + 1 ); }
	/**sub OfMe-�����Լ�
	 * @param fromIndex ƫ����
	 * */
	public static String subStringOfMe(String str,int fromIndex,String begin,String end) {
		int beginIndex = str.indexOf(begin,fromIndex);
		int endIndex = str.indexOf(end, beginIndex + 1 );//��ֹ��ͬ���ַ���..
		endIndex = endIndex == -1 ?  str.length() : endIndex;//��ֹû�ҵ�-1ʱ����..
		return str.substring(beginIndex, endIndex + 1);
	}
    ////////////////////////////        sub  �������Լ�
	public static String subString(String str,int begin,String end){return str.substring( begin + 1 , str.indexOf(end)  ); }
	
	public static String subString(String str,int fromIndex,String begin,String end) {
		int beginIndex = indexPrefix( str , begin,fromIndex) + begin.length() ;
		int endIndex =   indexSuffix( str , end , beginIndex  );//���ü�1��,�Ѿ�ƫ����
		return str.substring(beginIndex, endIndex  );
	}
	/** ���ǰ׺ , ���û���ҵ���,Ϊ0*/
	public static int indexPrefix(String str , String inner , int fromIndex) {
		int i = str.indexOf(inner, fromIndex);
		return i == -1 ? 0 : i;
	}
	/** ��ú�׺ , ���û���ҵ���,Ϊ�ַ�����*/
	public static int indexSuffix(String str , String inner , int fromIndex) {
		int i = str.indexOf(inner, fromIndex);
		return i == -1 ? str.length() : i ;
	}
}