package cn.wlh.util.base;

public class Logger1 extends Logger {
	Logger1(org.slf4j.Logger lOGGER) {
		super(lOGGER);
	}

	/** ���û����á�
	 * @param flag
	 */
	public static void setFlag(int flag) {
		Logger.flag = flag;
	}
}
