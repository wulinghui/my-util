package util.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public  abstract class _Serializt{
	public static void SerializePerson(Object obj,String filePath) throws FileNotFoundException, IOException {  
		serializeObj(obj, new File(filePath) );
	}
	public static void serializeObj(Object obj,File file) throws FileNotFoundException, IOException {  
    	_File.createFile( file );
        // ObjectOutputStream �������������Person����洢��E�̵�Person.txt�ļ��У���ɶ�Person��������л�����  
    	FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oo = new ObjectOutputStream(fos);  
        oo.writeObject(obj);
        fos.close();
        oo.close();
    }  
    @SuppressWarnings("unchecked")
	public static <T> T deserializeObj(File file) throws  Exception {  
    	FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);  
        Object ob = ois.readObject();  
        fis.close();
        ois.close();
        return (T) ob;  
    }
    /**
	 * ���л���ָ�����������
	 * @param user
	 * @param out --�����ر�
	 * @throws IOException
	 */
	public static void serialize(Object user,OutputStream out) throws IOException {
		 ObjectOutputStream oos = null;
	     oos = new ObjectOutputStream(out);
	     oos.writeObject(user);
	     oos.flush();
	     oos.close();
	}
	/**�����л��ɶ���
	 * @param in --�����ر�
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static <T> T deserialize(InputStream in) throws IOException, ClassNotFoundException {
		 ObjectInputStream ois = null;
	     ois = new ObjectInputStream(in);
	     T t = (T) ois.readObject();
	     ois.close();
	     return  t;
	}
    /**ͨ�����л�ʵ�ֵĿ�¡����
	 * @param obj
	 * @return ʵ�������л��������
     * @throws IOException 
     * @throws ClassNotFoundException 
	 * @throws TsException 
	 */
	public static <T extends Serializable>  T clone(T obj) throws IOException, ClassNotFoundException  {  
        // ���������Ķ���  
          // ��ȡ�����ֽ�����  
         ByteArrayOutputStream baos =  new ByteArrayOutputStream();  
         ObjectOutputStream oos = new ObjectOutputStream(baos);
         ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());  
         ObjectInputStream ois = new ObjectInputStream(bais);
		 oos.writeObject(obj);
		 T	clonedObj = (T) ois.readObject();  
          // �����ڴ�ռ䣬д��ԭʼ���������¶���  
          //�����¶��󣬲�������ת��  
       return clonedObj;  
    }
}
