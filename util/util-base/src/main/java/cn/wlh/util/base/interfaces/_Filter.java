package cn.wlh.util.base.interfaces;

/**
 * @author wlh
 * �����Լ���Ҫ������˽ӿڵĸ���
 * @param <T>
 */
public interface _Filter<T>{
	_Filter NO_FILTER = new _Filter(){
		/**
		 * @param t
		 * @return false --���˵�
		 */
		@Override
		public boolean accept(Object t) {
			return true;
		}
	};
	_Filter All_FILTER = new _Filter(){
		/**
		 * @param t
		 * @return false --���˵�
		 */
		@Override
		public boolean accept(Object t) {
			return false;
		}
	};
	boolean accept(T t);
}
