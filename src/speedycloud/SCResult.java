package speedycloud;

/*
 *         bucket: Ŀ��Ͱ
        host: Ŀ��Ͱ��host
        resolutions: �ֱ��ʣ�����á������ָ������磺480P1,480P2,720P
        callback_url: �ص�url
        source_id: �ϸ��ӿڷ��ص�source_id
 * */

 public class SCResult {
    public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String geteTag() {
		return eTag;
	}
	public void seteTag(String eTag) {
		this.eTag = eTag;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	private String msg;
    private String eTag;
    private int code;
    private String sourceId;
}