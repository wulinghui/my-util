package cn.wlh.util.base.exception;

/**
 * @author �����
 * ������..
 */@Deprecated
public class ExLocalRun extends ExAbstractThrowableLocal2<RuntimeException> {

	public ExLocalRun(int arrayLenth) {
		super(arrayLenth);
	}
//	public static void main(String[] args) {
//		new AAA().aaa();
//	} 
}
//
//class  AAA{
//	static ExLocalRun exLocalRun = new ExLocalRun(1); 
//	void aaa(){
//		exLocalRun.doThrowOfNew(0, "aaa�쳣");
//	}
//}