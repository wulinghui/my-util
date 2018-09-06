package test;

public class YufaTest {
	static {
		System.out.println("YufaTest  --  YufaTest");
	}
	public YufaTest() {
		return;
	}
	public static void main(String[] args) {
		Class a = C.class;
		new C();//一个static class 就是一个单独的类...
	}
	
	public static interface A{
		Object aaaa();
	}
	public static class C {
		static {
			System.out.println("ccccc");
		}
	}
	
	public static class B implements A{
		static {
			System.out.println("bbbb");
		}
		@Override
		public String aaaa() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	public static @interface ddd {
		
	}
}
