package util.base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public  abstract class _Writer {
	/**һ����д��,С��jvm�ڴ�,csq.toString()*/
	public static void oneWrite(File fi,boolean appendFlag,Object csq) throws IOException{
		FileWriter fw = null;
		BufferedWriter bf= null;
		try{
			fw = new FileWriter(fi,appendFlag);//�����쳣
			bf  = new BufferedWriter(fw);//û���쳣..
			//��ָ��
			if( csq != null)	bf.write(csq.toString());
		}finally{
//			realise(fw,bf);
			realise(bf , fw);
		}
	}
	/**�ͷ���Դ
	 * @throws IOException */
	public static void realise(Writer... writer) throws IOException {
		int len = writer.length;
		for (int i = 0; i < len; i++) {
			if (writer[i] != null)	writer[i].close();
		}
	}
}
