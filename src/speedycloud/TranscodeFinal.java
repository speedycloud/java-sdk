package speedycloud;

/*
 *         bucket: 目标桶
        host: 目标桶的host
        resolutions: 分辨率，多个用“，”分隔，例如：480P1,480P2,720P
        callback_url: 回调url
        source_id: 上个接口返回的source_id
 * */

public class TranscodeFinal {
	private String data;
	public TranscodeFinal(String data) {
		this.data = data;
	}
	
    public String Process(String transurl) throws Exception {
    	  SpeedyCloudS3 pfinal = new SpeedyCloudS3(transurl,"","");
    	  String ret = pfinal.FinalProcess(data);
    	  return ret;
    }

}