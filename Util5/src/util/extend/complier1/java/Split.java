package util.extend.complier1.java;

import dao.sql.Word;

public interface Split {
	//�ҵ� Dao ,View�Ͳ������.
	SplitJavaFile wlh_dao = new SplitJavaFile("TODO S-",";",";") {
		public String handleInner(String inner, JavaFile java) {
			java.addImport(Word.class);
			return inner.trim();
		}
	};
	SplitJavaFile oracle = new SplitJavaFile("TODO S-",";",";") {
		public String handleInner(String inner, JavaFile java) {
			java.addImport(Word.class);
			
			return java.inserReturn( java.toStringFormat(inner.trim()) );
		}
	};
}
