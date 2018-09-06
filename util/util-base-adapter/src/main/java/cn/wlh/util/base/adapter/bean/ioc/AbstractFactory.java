package cn.wlh.util.base.adapter.bean.ioc;

public class AbstractFactory implements IFactory{
	private final String ids [];
	byte aaa [] = new byte[] {}; 
	/**
	 * @param cla Ĭ��ע�����Լ�.
	 * IOC.register(cla, this); 
	 */
	public AbstractFactory(Class cla){
		IOC.register(cla, this);
		
		ids = new String[] {
				"00","11"
		};// ͨ�����ݿ������,һ(Class)�Զ�(String) 
	}
	/* (non-Javadoc)
	 * @see cn.wlh.util.base.adapter.bean.ioc.IFactory#getBean(java.lang.String)
	 */
	@Override
	public  Object getBean(String id) {
//		byte parseByte = Byte.parseByte(id);
//		byte zz = aaa[parseByte];
//		if(_11.equals(id)) {
//			//new 1;
//		}else if("22".equals(id)) {
//			//new 2;
//		}
		String string = ids[0];
		//��α仯?
		switch( id ) {
		//java9 ��֧�ֱ��ʽ..
//		case string:
//			//new 1;
//		case ids[1]:
			//new 2;
		}
		// �������Ч������..
		if( this.ids[0].equals(id) ) {
			
		}else if(this.ids[1].equals(id)){
			
		}
		//
		 
		return null;
	}
	/** ��this.ids[one] �� this.ids[two] �����ݸ�����.�Ӷ��ﵽ����new����ķ���  
	 * @param one
	 * @param two
	 */
	public void setOrderBy(int one , int two) {
		String str = this.ids[one];
		
		//��one�����ݸ����� , ͬʱ�������ݿ������.
		updateData( this.ids[one] , this.ids[two]);
		this.ids[one] = this.ids[two];
		
		
		//��two�����ݸ����� , ͬʱ�������ݿ������.
		updateData( this.ids[two] , str );
		this.ids[two] = str;
		
	}
	/** �������ݿ�.����ά��.
	 * @param old �ɵ�ֵ
	 * @param new0 �µ�ֵ
	 */
	public void updateData(String old , String new0) {
		String sql = 
				"update data_factory f , set f.value = '" + new0 
				+ "' where f.key='" + this.getClass().getName()
				+ "' and f.value='" + old + "'";
		
	}
}
