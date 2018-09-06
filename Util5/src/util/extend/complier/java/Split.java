package util.extend.complier.java;

public interface Split {
	//我的 Dao ,View就不拆分了.
	SplitJavaFile wlh_dao = new SplitJavaFile("TODO S-",";",";") {
		public String handleInner(String inner) {
			return inner.trim();
		}
	};
	SplitJavaFile oracle = new SplitJavaFile("TODO S-",";",";") {
		public String handleInner(String inner) {
			return inner.trim();
		}
	};
}
