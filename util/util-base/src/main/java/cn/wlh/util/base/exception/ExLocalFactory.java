package cn.wlh.util.base.exception;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author �����
 * ʹ������ͳһ���ʵ���ӿڡ�ע�������ʵ�����ܻ���.
 * --һ�����Ӧһ��ʵ����
 * --����ʵ������Ļ���-(���±�)һ���������һ�д����Ӧһ��ʵ����
 */
public abstract class ExLocalFactory  {
	private ExLocalFactory() {}
	
	public static <T extends Throwable> AbstractExceptionOfThreadLocal<T>  newAbstractExceptionOfThreadLocal(int arrayLenth ,Class<T> classType) {
		return new ExLocal1(arrayLenth, classType);
	}
	
//	static class AA{
//		//--һ�����Ӧһ��ʵ����
//		static AbstractExceptionOfThreadLocal<RuntimeException> newAbstractExceptionOfThreadLocal = ExLocalFactory.newAbstractExceptionOfThreadLocal(1, RuntimeException.class);
//		
//		void aaa() {
//			//�ڴ��������һ�ж�Ӧһ���±�
//			newAbstractExceptionOfThreadLocal.doThrowOfNew(0, "hahaha shenqingh cuo wu");
//		}
//	}
	
	
	static class ExLocal1<Run extends Throwable> extends  AbstractExceptionOfThreadLocal<Run>{

		Constructor<Run> constructor ; 
		public ExLocal1(int arrayLenth ,Class<Run> classType ) {
			super(arrayLenth);
			try {
				constructor = classType.getConstructor(String.class);
			} catch (NoSuchMethodException | SecurityException e) {
				try {
					Throwable throwable = classType.getConstructor().newInstance();
					CASE_THREADLOCAL.set(throwable);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e1) {
//					CASE_THREADLOCAL.set(e1);
//					CASE_THREADLOCAL.set(null);
				}
			}
		}
		@Override
		protected Run newInstance(String message) {
			try {
				return constructor.newInstance(new Object[] {message});
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				return (Run) getCase();
			}
		}
	}
}
