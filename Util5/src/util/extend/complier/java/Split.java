package util.extend.complier.java;

public interface Split {
	//�ҵ� Dao ,View�Ͳ������.
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
