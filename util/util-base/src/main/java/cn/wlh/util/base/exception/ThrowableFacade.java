package cn.wlh.util.base.exception;

import cn.wlh.util.base.exception.ExceptionUtil1.ExceptionCache;

public interface ThrowableFacade {
	/**1.ʹ���쳣��ֹ����ִ��
	 * 3.��ϵͳ�쳣���д���֮��Ѹ��쳣�׳���
	 * 4.��ϵͳ�쳣���д���֮��Ѹ��쳣ת�����Զ����쳣���׳���
	 * */
	ExceptionUtil SYSTEM = new ExceptionUtil();
	/**
	 * ���ֵջ��Ϣ�ġ�
	 */
	ExceptionUtil1 STACK_TRACES_INFO = new ExceptionUtil1();
	/**����ϵͳ����ֵջ���档
	 * */
	ExceptionCache STACK_TRACES_INFO_OF_SYSTEM = STACK_TRACES_INFO.new ExceptionCache();	
	/**4.��ϵͳ�쳣���д���֮��Ѹ��쳣ת��������ʱ�쳣���׳�*/
	ExceptionUtil2 THROW_RUN = new ExceptionUtil2();  
}
