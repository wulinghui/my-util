package util.extend.complier.java;

import java.io.File;

import util.extend.complier.java.ComplierClient.ComplierFile;
import util.extend.complier.java.ComplierClient.ComplierFileOfPart;

public class SplitJavaPartOf extends ComplierFileOfPart<JavaFile> {

	public SplitJavaPartOf(ComplierFile<JavaFile> part) {
		super(part);
	}

	@Override
	public JavaFile handle(File file, StringBuffer sb) {
		JavaFile java = this.getPart().handle(file, sb);
		
		return java;
	}

}
