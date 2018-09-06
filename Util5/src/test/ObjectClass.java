package test;

public class ObjectClass {
	ObjectClass(){
		return;//会new ,提前new对象
	}
	ObjectClass(int a){
		throw new NullPointerException();
	}
	public static void main(String[] args) {
		ObjectClass a = new ObjectClass();
		System.out.println(a);
		ObjectClass b = new ObjectClass(1);
		System.out.println(b);
	}
}
