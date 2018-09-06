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
 * 解决一张表有又多行字段问题
 * @author wlh
 * 有些内容需要一次性读入jvm-配置内容
 * 有些内容只需要读取一个.
 * 有些内容需要读取多个相同的.
 * 有些内容需要读取多个不相同的.
 * 也就是说2种情况:读一个,读多个。
 */
public abstract class _FileBase<T>{//T可以是任意对象,但是使用的时候必须申明
	/**用于手动注册,统一使用的路径,所以这里就不给getInstance接口了*/
	public static abstract class _FileBaseClient{
		public static _FileBase f = new _FileBase("", "", "", null, null){};
		
		
		/**获得文件夹路径;	把. -> 改成 各个系统的文件分割。*/
		public static String toPath(String a){
			String sb = _File.WOKE_PATH + File.separator +"store" + File.separator +a;
			return sb.replaceAll("\\.", "\\"+File.separator);//都是正则,\\是需要的
		}
	}  
	final String suf;//表名--后缀
	final String directory;//分表--文件夹路径
	public final Map<String,String> field = new LinkedHashMap<String,String>();//有序字段.
	public final int lenth;//有多少字段
	public _FileBase(String suf, String directory,//这里就只添加描述,不规定类型了
			String tableDescribe,String [] fields,String[] fieldsDescrbe) {
		super();
		this.suf = suf;
		this.directory = directory;
		this.lenth = fields.length;
		//统一把field和描述,都放入到一个文件夹中.
		for (int i = 0; i < this.lenth ; i++) {
			field.put(fields[i], fieldsDescrbe[i]) ;
		}
		//TODO 在把表所有字段的描述放到一个文件中
		File fieldDescribe = new File( directory.replaceFirst("store", "table") + File.separator + suf +".field");
		try {
			_Serializt.serializeObj( field , fieldDescribe );
		//TODO 再把描述则个表的内容放到一个文件中
			File tabledDes = new File( _File.WOKE_PATH + File.separator +"table" + File.separator + "directory" +".table");
			Map<String,String> map = _Serializt.deserializeObj(tabledDes);
			if(map == null) map = new HashMap<String,String>();
			map.put(suf+"::"+directory, tableDescribe);//放入
			_Serializt.serializeObj(map, tabledDes);//存储
		//TODO 如果需要查看所有信息的话写个循环就行了--所以这里就不用在添加一个文件了.
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public  String getAbsolutePath(String id){//id文件名
		return this.directory + File.separator + id + "." + this.suf;
	}
	protected T getBean(String id) throws IOException, Exception{
		return _Serializt.deserializeObj( new File( getAbsolutePath(id) ) );
	}
	protected  void storeBean(T t,String id) throws IOException {
		_Serializt.serializeObj(t, new File( getAbsolutePath(id) ) );
	}

	
	////exp oracle导出数据库表数据
	public boolean backupData()  
    {  
        boolean isSuccess = false;   
        String dataSource = "TEST_LOCAL";  
        String backupLocation = "D:\\test\\";  
        Date date = new Date();  
        DateFormat df = DateFormat.getDateTimeInstance();// 精确到时分秒  
        String dmpName = "test_" + df.format(date) + ".dmp";  
        dmpName = dmpName.replace(" ", "_");  
        dmpName = dmpName.replace(":", "");// 形如 test_2016-2-16_163405.dmp  
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
        // 读取ErrorStream很关键，解决了挂起的问题  
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
			+ "'"; // 此处可以进行配置管理 
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
			// 表不存在 EXP-00011 
			// 无法处理服务名 ORA-12154 
			// 导出终止失败 EXP-00000 
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
	  * 从数据库导出项目数据
	  * @param map
	  * @return
	  */
	 public static String expFile(HashMap<String, Object> map) {
	 String info="";
	 String filename=  "" ;      //StringUtil.formatDbColumn(map.get("FILENAME"))  ;
	 String proj_id=   "" ;      //StringUtil.formatDbColumn(map.get("PROJ_ID"));
	 String[] cmds = new String[3];
	 //表导出语句  如果要导出多表则在 tables参数里添加 例如 tables=(KM_DOC,KM_FOLDER,……,……)
	 String commandBuf = "exp pip_jk/pip@orcl file=E://"+filename+".dmp  tables=(KM_DOC,KM_FOLDER)  query=\"\"\" where PROJ_ID='"+proj_id+"'\"\"\"";
	 cmds[0] = "cmd";
	 cmds[1] = "/C";
	 cmds[2] = commandBuf.toString();
	 Process process = null;
	 try {
	 process = Runtime.getRuntime().exec(cmds);
	 } catch (IOException e) {
	  
	 e.printStackTrace();
	 info="导入出错";
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
	 info="导入出错";
	 }
	 if (shouldClose)
	 process.destroy();
	 int exitVal;
	 try {
	 exitVal = process.waitFor();
	 System.out.print(exitVal);
	 } catch (InterruptedException e) {
	 e.printStackTrace();
	 info="导入出错";
	 }
	 return info;
	 }
	 
	 /**
	  * 导入数据库文件
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
	 info="导入出错";
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
	 info="导入出错";
	 }
	 if (shouldClose)
	 process.destroy();
	 int exitVal;
	 try {
	 exitVal = process.waitFor();
	 System.out.print(exitVal);
	 } catch (InterruptedException e) {
	 e.printStackTrace();
	 info="导入出错";
	 }
	 return info;
	 }
}
