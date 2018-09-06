package cn.wlh.util.base.adapter.dbutils.apache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import cn.wlh.util.base.adapter.java.util.AddAfterSeeListInterface;
import cn.wlh.util.base.adapter.java.util.CacheValuesMap;

/**
 * @author ����� ֮ǰ�� cn.wlh.util.base.adapter.dbutils.apache.GetSql2.Prepared
 *         <br/>
 *         ��cn.wlh.util.base.adapter.dbutils.apache.AnalysisSQL <br/>
 *         һ:���Ƕ����ü��Ϸ�ʽ��Ӧ������--�����ڴ洢<br/>
 *         ��:ͬʱ�������Բ����Ķ��������б����Ĳ���������һά����O(N)����. <br/>
 *         ���� ����ظ��Ĳ�����,map�ļ��ϴ�.���map.get()�����ǲ�����O(N+1.2.3.4....)��<br/>
 *         �������� , �������ڻ��棬�����ڲ�����Ĳ���
 *         <hr/>
 *         ������������Ա���Map����ʽ..set�ദ�ظ��Ĳ������Դﵽ������O(N)<br/>
 *         <br/>
 *         һ��ԭ����jdbc , �ʼ�����Լ�д���Ӻ���ǰ�����Ĺ���.��=?ǰ����ַ�������ó�����ֻ���� shenqing = ?�������.
 */
public class PreparedOfCache {
	PreparedOfCache(){}
	static PreparedOfCache prepared[] = new PreparedOfCache[] { new PreparedOfCache(),
			 new PreparedNamedOfCache(':')
	};
	char[] fileterChar = { ' ', '\n', '\t', '\'', '"' };
	char[] startChar = { '?', '=' };

	protected void setFileterChar(char[] fileterChar) {
		this.fileterChar = fileterChar;
	}

	protected void setStartChar(char[] startChar) {
		this.startChar = startChar;
	}

	protected void setFlags(boolean[] flags) {
		this.flags = flags;
	}

	boolean flags[] = new boolean[startChar.length]; // = ?�ı��

	/**
	 * ��� shenqing = ? ��shenqing����
	 * 
	 * @param sql
	 * @return shenqing
	 */
	public static PreparedOfCache getInstance(String id) {
		if ("".equals(id)) {
			return prepared[0];
		} else if (":".equals(id)) {
			return prepared[1];
		}
		return null;
	}

	/**
	 * ���һ����sql , ������key.
	 * 
	 * @param query
	 * @param beanFlag
	 * @return
	 */
	public CachePreparedMap getParameter(String sql) {
		// �Ȱ�sql�Ŀո� \t \nȫ����һ��' ' ��ʱ����Ҫ.
		char[] strChar = sql.toCharArray();
		int length = strChar.length;
		int endLength = startChar.length;
		List<Character> chars = new ArrayList<Character>();
		Object[] array;
		String str;
		// List<String> strs = new ArrayList<>();
		CachePreparedMap preparedMap = new CachePreparedMap();
		int indexOf = 1; // ��Ӧ������λ��
		// ѭ������char
		for (int i = length; length > endLength; i--) { // 2
			char c = strChar[i];

			if (flags[0]) {// ��? �����ж�=
				if (flags[1] == true && !lookUp(c, fileterChar)) { // ���ڽ��� �����ڹ�����������.
					chars.add(c);
				} else if (startChar[1] == c) { // Ϊ=��ʱ��
					flags[1] = true;
					// �ڽ���֮ǰ�Ĺ�����������. "s = ?" �� " = ?"��Щ
				} else if (lookUp(c, fileterChar) && flags[1] == false) {

				} else if (chars.size() > 0) { // ���ڽ��� ���ڹ�����������.. "shenq ingh = ?" �� "shenq ingh"
					flags[0] = false;
					flags[1] = false;
					array = chars.toArray();
					str = String.valueOf(array);
					// strs.add(str);
					preparedMap.putOneOfAutoAdd(str, indexOf);
					chars.clear();
					indexOf++;
				} else if (chars.size() == 0) { // //���ڽ��� ���ڹ�����������.. "shenq ingh = ?" �� "ingh ="

				}
			} else if (c == startChar[0]) {
				flags[0] = true; // ��? �´�ѭ���ж�=
			}
		}
		// strs.add(sql);
		preparedMap.setFinalSql(sql);
		preparedMap.saveAllKeyAndValue();
		return preparedMap;
	}

	// �Ƿ���������.
	public boolean lookUp(char c, char[] strChar) {
		for (char d : strChar) {
			if (d == c)
				return true; // ��
		}
		return false;// ����
	}

	/**
	 * @author �����<br/>
	 *         �������key��Ӧ��������<br/>
	 *         �磺 shenqingh = 1, 3 , 6,7 --key��1 3 6 7 λ����ʹ����.<br/>
	 *         ������ǰ�� 1 = shenqingh<br/>
	 *         3 = shenqingh<br/>
	 *         6 = shenqingh<br/>
	 *         7 = shenqingh <br/>
	 *         ���������List�Ƕ��������顣��������һ���ռ䡣
	 */
	public static class CachePreparedMap extends CacheValuesMap<String, Integer> {
		int size; // ����ֵ�Ĵ�С

		String finalSql;// ���յ�sql���

		@Override
		public void save() {
			throw new RuntimeException("��ʹ��saveAllKeyAndValue����..");
		}

		@Override
		public AddAfterSeeListInterface<Integer> put(String key, AddAfterSeeListInterface<Integer> value) {
			throw new RuntimeException("��ʹ��putOneOfAutoAdd����..");
		}

		@Override
		public CacheValuesMap<String, Integer> putOneOfAutoAdd(String key, Integer value) {
			size++;
			return super.putOneOfAutoAdd(key, value);
		}

		// /**�Զ�++
		// * @param key
		// */
		// public void valueAutoAdd(String key) {
		// AddAfterSeeListInterface<Integer> addAfterSeeListInterface = get(key);
		// if( addAfterSeeListInterface == null) {
		// addAfterSeeListInterface = new AddAfterSeeImp<>();
		// super.put(key, addAfterSeeListInterface);
		// }
		// Integer integer = addAfterSeeListInterface.get( size() -1 );
		// // �����Value������������,�����addAfterSeeListInterface�Ǵ�Ŷ�Ӧ������λ�õ�..
		// }
		/**
		 * ת��Ϊ���� save -- Map�����е�List
		 */
		public void saveAllKeyAndValue() {
			// ���е�List
			Collection<AddAfterSeeListInterface<Integer>> values = super.values();
			// TODO ��֪���ǲ�����ʵ�ġ���ʱ����������������û��ʵ�ֵķ�������null��֪���ˡ�
			Iterator<AddAfterSeeListInterface<Integer>> iterator = values.iterator();
			while (iterator.hasNext()) {
				AddAfterSeeListInterface<Integer> next = iterator.next();
				next.save();
			}
			// map
			super.save();
		}

		public String getFinalSql() {
			return finalSql;
		}
		/**
		 * ���Զ�ת��д..
		 * @param finalSql
		 */
		public void setFinalSql(String finalSql) {
			this.finalSql = finalSql.toUpperCase();
		}

		public int getSize() {
			return size;
		}
	}
}
