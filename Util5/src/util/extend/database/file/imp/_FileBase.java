package util.extend.database.file.imp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import util.base._File;
import util.base._Serializt;

/**
 * ���һ�ű����ֶ����ֶ�����
 * @author wlh
 * ��Щ������Ҫһ���Զ���jvm-��������
 * ��Щ����ֻ��Ҫ��ȡһ��.
 * ��Щ������Ҫ��ȡ�����ͬ��.
 * ��Щ������Ҫ��ȡ�������ͬ��.
 * Ҳ����˵2�����:��һ��,�������
 */
public abstract class _FileBase<T>{//T�������������,����ʹ�õ�ʱ���������
	/**�����ֶ�ע��,ͳһʹ�õ�·��,��������Ͳ���getInstance�ӿ���*/
	public static abstract class _FileBaseClient{
		public static _FileBase f = new _FileBase("", "", "", null, null){};
		
		
		/**����ļ���·��;	��. -> �ĳ� ����ϵͳ���ļ��ָ*/
		public static String toPath(String a){
			String sb = _File.WOKE_PATH + File.separator +"store" + File.separator +a;
			return sb.replaceAll("\\.", "\\"+File.separator);//��������,\\����Ҫ��
		}
	}  
	final String suf;//����--��׺
	final String directory;//�ֱ�--�ļ���·��
	public final Map<String,String> field = new LinkedHashMap<String,String>();//�����ֶ�.
	public final int lenth;//�ж����ֶ�
	public _FileBase(String suf, String directory,//�����ֻ�������,���涨������
			String tableDescribe,String [] fields,String[] fieldsDescrbe) {
		super();
		this.suf = suf;
		this.directory = directory;
		this.lenth = fields.length;
		//ͳһ��field������,�����뵽һ���ļ�����.
		for (int i = 0; i < this.lenth ; i++) {
			field.put(fields[i], fieldsDescrbe[i]) ;
		}
		//TODO �ڰѱ������ֶε������ŵ�һ���ļ���
		File fieldDescribe = new File( directory.replaceFirst("store", "table") + File.separator + suf +".field");
		try {
			_Serializt.serializeObj( field , fieldDescribe );
		//TODO �ٰ��������������ݷŵ�һ���ļ���
			File tabledDes = new File( _File.WOKE_PATH + File.separator +"table" + File.separator + "directory" +".table");
			Map<String,String> map = _Serializt.deserializeObj(tabledDes);
			if(map == null) map = new HashMap<String,String>();
			map.put(suf+"::"+directory, tableDescribe);//����
			_Serializt.serializeObj(map, tabledDes);//�洢
		//TODO �����Ҫ�鿴������Ϣ�Ļ�д��ѭ��������--��������Ͳ��������һ���ļ���.
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  String getAbsolutePath(String id){//id�ļ���
		return this.directory + File.separator + id + "." + this.suf;
	}
	protected T getBean(String id) throws IOException, Exception{
		return _Serializt.deserializeObj( new File( getAbsolutePath(id) ) );
	}
	protected  void storeBean(T t,String id) throws IOException {
		_Serializt.serializeObj(t, new File( getAbsolutePath(id) ) );
	}

	
	////exp oracle�������ݿ������
	public boolean backupData()  
    {  
        boolean isSuccess = false;   
        String dataSource = "TEST_LOCAL";  
        String backupLocation = "D:\\test\\";  
        Date date = new Date();  
        DateFormat df = DateFormat.getDateTimeInstance();// ��ȷ��ʱ����  
        String dmpName = "test_" + df.format(date) + ".dmp";  
        dmpName = dmpName.replace(" ", "_");  
        dmpName = dmpName.replace(":", "");// ���� test_2016-2-16_163405.dmp  
        String commandStr = "cmd.exe /c exp user/password@" + dataSource+ " file=" + backupLocation  
                            + dmpName + " tables=(tablename1,tablename2,tablename3)";  
        Runtime run = Runtime.getRuntime();  
        Process process = null;  
        try  
        {  
            process = run.exec(commandStr);  
        }  
        catch (IOException e)  
        {  
            e.printStackTrace();  
        }  
        String line = null;   
        // ��ȡErrorStream�ܹؼ�������˹��������  
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));  
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));          
        try  
        {  
            while ((line = br.readLine()) != null)  
            {      
                System.out.println(line);  
            }  
            while ((line = in.readLine()) != null)  
            {  
                    System.out.println(line);  
            }  
                in.close();  
                br.close();  
                process.getOutputStream().close();  
                process.waitFor();                  
                isSuccess = true;  
        }  
        catch(Exception ex)  
        {  
            ex.printStackTrace();  
        }  
        finally  
        {  
            if(in!=null)  
            {  
                try  
                {  
                    in.close();                  
                }  
                catch (IOException e)  
                {  
                    e.printStackTrace();  
                }  
            }  
              
            if(br!=null){  
                try  
                {  
                    br.close();  
                }  
                catch (IOException e)  
                {  
                   e.printStackTrace();  
                }  
            }  
              
        }  
        return isSuccess;  
    }  
//exp
	public String done(String username, String password, String sid, 
			String tables, String file, String log, String query) { 
			String[] cmds = new String[3]; 
			cmds[0] = "cmd"; 
			cmds[1] = "/C"; 
			cmds[2] = "exp " + username + "/" + password + "@" + sid + " tables=" 
			+ tables + " file=" + file + " log=" + log + " query='" + query 
			+ "'"; // �˴����Խ������ù��� 
			Process process = null; 
			try { 
			process = Runtime.getRuntime().exec(cmds); 
			} catch (IOException e) { 
			// TODO Auto-generated catch block 
			e.printStackTrace(); 
			} 
			boolean close = false; 
			String s = ""; 
			try { 
			InputStreamReader isr = new InputStreamReader(process 
			.getErrorStream()); 
			BufferedReader br = new BufferedReader(isr); 
			String line = ""; 
			while ((line = br.readLine()) != null) { 
			s = s + br.readLine() + "<br>"; 
			// invalid username/password logon denied EXP-00056 
			// invalid username/password logon denied ORA-01017 
			// ������ EXP-00011 
			// �޷���������� ORA-12154 
			// ������ֹʧ�� EXP-00000 
			if (line.indexOf("EXP-00056") != -1 
			|| line.indexOf("ORA-01017") != -1 
			|| line.indexOf("EXP-00011") != -1 
			|| line.indexOf("ORA-12154") != -1 
			|| line.indexOf("EXP-00000") != -1) { 
			close = true; 
			break; 
			} 
			} 
			} catch (IOException ioe) { 
			close = true; 
			} 
			if (close) 
			process.destroy(); 
			try { 
			int exitVal = process.waitFor(); 
			System.out.println("exitVal:" + exitVal); 
			} catch (InterruptedException e) { 
			// TODO Auto-generated catch block 
			e.printStackTrace(); 
			} 
			return s; 

			} 
	
	

	 /**
	  * �����ݿ⵼����Ŀ����
	  * @param map
	  * @return
	  */
	 public static String expFile(HashMap<String, Object> map) {
	 String info="";
	 String filename=  "" ;      //StringUtil.formatDbColumn(map.get("FILENAME"))  ;
	 String proj_id=   "" ;      //StringUtil.formatDbColumn(map.get("PROJ_ID"));
	 String[] cmds = new String[3];
	 //�������  ���Ҫ����������� tables��������� ���� tables=(KM_DOC,KM_FOLDER,����,����)
	 String commandBuf = "exp pip_jk/pip@orcl file=E://"+filename+".dmp  tables=(KM_DOC,KM_FOLDER)  query=\"\"\" where PROJ_ID='"+proj_id+"'\"\"\"";
	 cmds[0] = "cmd";
	 cmds[1] = "/C";
	 cmds[2] = commandBuf.toString();
	 Process process = null;
	 try {
	 process = Runtime.getRuntime().exec(cmds);
	 } catch (IOException e) {
	  
	 e.printStackTrace();
	 info="�������";
	 }
	 boolean shouldClose = false;
	 try {
	 InputStreamReader isr = new InputStreamReader(process
	 .getErrorStream());
	 BufferedReader br = new BufferedReader(isr);
	 String line = null;
	 while ((line = br.readLine()) != null) {
	 System.out.println(line);
	 if (line.indexOf("????") != -1) {
	 shouldClose = true;
	 break;
	 }
	 }
	 } catch (IOException ioe) {
	 shouldClose = true;
	 info="�������";
	 }
	 if (shouldClose)
	 process.destroy();
	 int exitVal;
	 try {
	 exitVal = process.waitFor();
	 System.out.print(exitVal);
	 } catch (InterruptedException e) {
	 e.printStackTrace();
	 info="�������";
	 }
	 return info;
	 }
	 
	 /**
	  * �������ݿ��ļ�
	  * @param map
	  */
	 public static String impFile(HashMap<String, Object> map) {
	 String info="";
	 String filepath=   ""; //StringUtil.formatDbColumn(map.get("FILEPATH"));
	 String[] cmds = new String[3];
	 String commandBuf = "imp pip_jk/pip@orcl fromuser=pip_jk touser=dms file="+filepath+" ignore=y";
	 cmds[0] = "cmd";
	 cmds[1] = "/C";
	 cmds[2] = commandBuf.toString();
	 Process process = null;
	 try {
	 process = Runtime.getRuntime().exec(cmds);
	 } catch (IOException e) {
	 e.printStackTrace();
	 info="�������";
	 }
	 boolean shouldClose = false;
	 try {
	 InputStreamReader isr = new InputStreamReader(process
	 .getErrorStream());
	 BufferedReader br = new BufferedReader(isr);
	 String line = null;
	 while ((line = br.readLine()) != null) {
	 System.out.println(line);
	 if (line.indexOf("????") != -1) {
	 shouldClose = true;
	 break;
	 }
	 }
	 } catch (IOException ioe) {
	 shouldClose = true;
	 info="�������";
	 }
	 if (shouldClose)
	 process.destroy();
	 int exitVal;
	 try {
	 exitVal = process.waitFor();
	 System.out.print(exitVal);
	 } catch (InterruptedException e) {
	 e.printStackTrace();
	 info="�������";
	 }
	 return info;
	 }
}
