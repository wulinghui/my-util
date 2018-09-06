package cn.wlh.util.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class _InvokeRef {
	 /**  
		 * @param <T>
		 * @param target
		 * @param tar
		 * @param name
		 * @param declared ���˽�еķ���,������ø����
		 * @param objects  
		 * @return
		 * @throws NoSuchMethodException
		 * @throws SecurityException
		 * @throws IllegalAccessException
		 * @throws IllegalArgumentException
		 * @throws InvocationTargetException
		 */
		@SuppressWarnings("unchecked")
		private static <T> T invoke(Object target,Class<?> tar,String name,boolean declared,Object...objects ) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
			//ͨ��getClass���parameterTypes
			Class<?> [] parameterTypes = _Class.obj2Class(objects);
			//��ù�������,������
			return (T) (declared ? tar.getDeclaredMethod(name, parameterTypes).invoke(target, objects) 
					: tar.getMethod(name, parameterTypes).invoke(target, objects) );
		}
		public static <T> T invoke(Object target,String name,boolean declared,Object...objects ) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
			if(target !=null ) return null;
			return invoke(target, target.getClass() , name, declared, objects);
		}
		/** �÷���,ֻ��ѭ������Ĳ���
		 * @param obj -- ִ�з����Ķ��� , nullΪ��̬����         
		 * @param method	  
		 * @param fix	-- �̶���Para  
		 * @param forPara	-- ѭ���� Para  
		 * @param skip	-- forPara �� ����
		 * @return
		 * @throws IllegalArgumentException
		 * @throws IllegalAccessException
		 * @throws InvocationTargetException
		 *///Object [] -- �û�����ת��
		public static /*Object []*/ /*Object*/ <T> T[] forInvoke(Object obj,Method method,Object [] fix,Object [] forPara,int skip) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
			if( forPara == null || fix == null ) throw new NullPointerException(); 
			if( skip <= 0) throw new IndexOutOfBoundsException();
			int pLen = forPara.length;
			//У�� forPara.length ��  skip �ĺϷ���
			if ( pLen % skip != 0) throw new IndexOutOfBoundsException();  
			int fLen = fix.length;
			// ��װ ������args ..��fix + forPara�Ĳ���
			Object [] args = new Object [ fLen + skip ];
			for (int i = 0; i < fLen; i++) {//��fix����,֮��Ͳ��ı���
				args [i] = fix[i]; 
			}
			//������ѭ��...
			Object [] retObjs = new Object[ pLen / skip ];
			int flag = 0;
			for (int i = 0; i < pLen ; i+= skip) {
				//�Ȱ�forPara����ָ��λ��
				for (int j = 0; j < skip ; j++) {
					args[ fLen + j ] = forPara[j]; 
				}
				//invoke
				retObjs[flag] = method.invoke(obj, args);
				flag++;//�±�++
			}
			return (T[]) retObjs;
		}
	
		/**	@see this.forInvoke(Object obj,Method method,Object [] fix,Object [] forPara,int skip)
		 * @param obj obj -- ִ�з����Ķ���  , ����Ϊnull
		 * @param methodName
		 * @param parsClass
		 * @param fix
		 * @param forPara
		 * @return
		 * @throws IllegalArgumentException
		 * @throws IllegalAccessException
		 * @throws InvocationTargetException
		 * @throws SecurityException
		 * @throws NoSuchMethodException
		 */
		public static <T> T[] forInvoke(Object obj,String methodName,Class<?>[] parsClass,Object [] fix,Object [] forPara) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException{
			if(obj == null) throw new NullPointerException();//��֧�־�̬����    
			return forInvoke(obj, _Method.getAllMethod(obj.getClass(), methodName, parsClass) ,fix ,forPara,  parsClass.length - fix.length);
		}
		/** Ϊ������ѭ�����÷���
		 * @param obj
		 * @param method
		 * @param fix
		 * @param forPara
		 * @param argsOrderBy -- Ϊarcs����.  new int[]{ 1,3,5,//fix�Ĳ���
		 * 												 2 , 4}//forPara�Ĳ���
		 * 										ͨ��������Ϊ��ʱ��args�Ĳ���
		 * @return
		 * @throws IllegalArgumentException
		 * @throws IllegalAccessException
		 * @throws InvocationTargetException
		 */
		public static  <T> T[] forInvoke(Object obj,Method method,Object [] fix,Object [] forPara,int [] argsOrderBy) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
			//У��..
			if( forPara == null || fix == null || argsOrderBy == null) throw new NullPointerException(); 
			int f = fix.length;
			int p = forPara.length;
			int a = argsOrderBy.length;
			int skip = a - p;
			if( skip <= 0)  throw new IndexOutOfBoundsException();
			if ( p % skip != 0) throw new IndexOutOfBoundsException();
			// ��װ ������args ..��fix + forPara�Ĳ���
			Object [] args = new Object [ f + skip ];
			for (int i = 0; i < f; i++) {//��fix����ָ����λ��,֮��Ͳ��ı���
				args [ argsOrderBy[i] ] = fix[i];
			}
			//������ѭ��...
			Object [] retObjs = new Object[ p / skip ];
			int flag = 0;
			for (int i = 0; i < p ; i+= skip) {
				//�Ȱ�forPara����ָ��λ��
				for (int j = 0; j < skip ; j++) {
					args[  argsOrderBy[f + j]  ] = forPara[j]; 
				}
				//invoke
				retObjs[flag] = method.invoke(obj, args);
				flag++;//�±�++
			}
			return (T[]) retObjs;
		}
	
}
