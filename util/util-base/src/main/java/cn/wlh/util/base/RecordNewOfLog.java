package cn.wlh.util.base;

/**
 * @author �����
 * ͨ����־����ʽ��¼new����Ĺ��̡�
 */
public class RecordNewOfLog {
	public static final Logger RECORD_NEW_OBJECT_OF_LOG = Logger.getLogger(Logger.class);
	/**������дtoString�������ˡ�
	 * @param flag
	 */
	public RecordNewOfLog(char flag) {
			RECORD_NEW_OBJECT_OF_LOG.log(flag, "new ObjectClass=" + this.getClass() + "\nnew ObjectInner" + this.toString());
	}
}
