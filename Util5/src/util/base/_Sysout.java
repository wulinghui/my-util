package util.base;

public class _Sysout {
	public static void print(Object obj) {
		String s = obj==null ? "null" : obj.toString();
		System.out.println(s);
	}
	public static void print0(Object... obj) {
		for (Object object : obj) {
			print(object);
		}
	}
	public static void print(String describe,Object obj) {
		String s = obj==null ? "null" : obj.toString();
		System.out.println(describe + s);
	}
	public static void print0(String describe,Object... obj) {
		for (Object object : obj) {
			print(describe,object);
		}
	}
}
