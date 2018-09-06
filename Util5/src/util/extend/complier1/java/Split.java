package util.extend.complier1.java;

import dao.sql.Word;

public interface Split {
	//我的 Dao ,View就不拆分了.
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
