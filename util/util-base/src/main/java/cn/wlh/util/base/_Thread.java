package cn.wlh.util.base;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public  abstract class _Thread {
	public static final ThreadGroup group = new ThreadGroup("g");
	public static final ExecutorService pool = 
			Executors.newFixedThreadPool( 10000 );
	public static final ScheduledExecutorService poolOfTimer =
		Executors.newScheduledThreadPool( 100 );
	public static final TimeUnit timeUnit = TimeUnit.SECONDS;
//	public static final ForkJoinPool fjpool = ForkJoinPool.commonPool();
	public static int MD5 = -1;
//	public static Thread[] threadArray = new  Thread[100];
//	static int index = 0;  --����Ĺ�����Դ����IOC.ְ������
	/**�����ִ�б��������,Ϊ�˷��񽵼�
	 * @param thread
	 * @return
	 */
	public static Thread executor(Runnable thread){
		Thread th = new Thread(group,thread);
		pool.execute(th);
		return th;
	}
	/** �����ִ��û�б������
	 * @param thread
	 * @return
	 * @throws TsException 
	 */
	public static void executor(Thread thread,int md5)  {
		if(md5 != MD5)
		pool.execute(thread);
	}
	/**ִ�е����첽�ķ���,�����û�ʹ��.û�еǼ�--�Ǽǹ���Ӧ�ý���iocȥά��
	 * @param task
	 * @param tasks
	 * @return
	 */
//	public static <T> T executor(ForkJoinTask<?> task,ForkJoinTask<?>... tasks){
//		int len = tasks.length;
//		if(len == 0) {
//			return (T) fjpool.invoke(task);
//		}else {
//			List co =  Arrays.asList(tasks);
//			co.add(0, task);
//			return (T) fjpool.invokeAll(co);
//		}
//	}
	
	/**��ʱ����,ִ��һ��
	 * @param command
	 * @param delay ��λ����
	 * @return
	 */
	public static ScheduledFuture<?> timerOne(Runnable command,long delay){
		return poolOfTimer.schedule( command,delay, timeUnit);
	}
	/**@see timerOne */
	public static <V> ScheduledFuture<V> timerOne(Callable<V> command,long delay){
		return poolOfTimer.schedule( command,delay, timeUnit);
	}
	/**���ڵ�ִ��,����ÿ��ִ��ʱ��Ϊ��һ����������������һ��ʱ����period
	 * initialDelay��ʼ����һ��.
	 * @param command 
	 * @param period --����ʱ��,��λ����
	 * @return
	 */
	public static ScheduledFuture<?> timerCycle(Runnable command,long period){
		return poolOfTimer.scheduleWithFixedDelay( command, 1 ,period, timeUnit);
	}
}
