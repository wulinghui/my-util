package util.extend.complier.java;

import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;

import util.base._Properties;

public class NewFileByDictionary {
	public void new0000() {
		String ss = "";
		Properties pa = null;
		try {
			pa = _Properties.getInstance(new File(""));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
		for (Entry<Object, Object> element : pa.entrySet() ) {
			ss.replaceAll( element.getKey().toString() , element.getValue().toString() );
		}
	}
}
