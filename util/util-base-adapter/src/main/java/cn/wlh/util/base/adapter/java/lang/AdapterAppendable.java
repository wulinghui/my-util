package cn.wlh.util.base.adapter.java.lang;

import java.io.IOException;

/**
 * @author �����
 * ��Щʱ��������SB�ķ���..��������final��   ��֧��.
 * ֻ��׷��..cn.wlh.util.base.adapter.json.AdapterAppendableTest.test0()
 * @see sun����ļ�.. AbstractStringBuilder���ǹ�����...
 * @param <K>
 */
public class AdapterAppendable<K extends Appendable> implements Appendable {
	protected K sb;
	static final String MESSAGE = "�����ܵ��쳣";

	public AdapterAppendable(K sb) {
		this.sb = sb;
	}

	@Override
	public AdapterAppendable<K> append(CharSequence csq) {
		try {
			sb.append(csq);
			return this;
		} catch (IOException e) {
			throw new RuntimeException(MESSAGE);
		}
	}

	@Override
	public Appendable append(CharSequence csq, int start, int end) {
		try {
			sb.append(csq, start, end);
			return this;
		} catch (IOException e) {
			throw new RuntimeException(MESSAGE);
		}
	}

	@Override
	public AdapterAppendable<K> append(char c) {
		try {
			sb.append(c);
			return this;
		} catch (IOException e) {
			throw new RuntimeException(MESSAGE);
		}
	}
	public K getSb() {
		return sb;
	}

	public void setSb(K sb) {
		this.sb = sb;
	}
}
