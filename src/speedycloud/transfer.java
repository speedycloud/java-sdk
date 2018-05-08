package speedycloud;

/*
 *         bucket: 目标桶
        host: 目标桶的host
        resolutions: 分辨率，多个用“，”分隔，例如：480P1,480P2,720P
        callback_url: 回调url
        source_id: 上个接口返回的source_id
 * */

public class transfer {
	public String getBucket() {
		return bucket;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getResolutions() {
		return resolutions;
	}

	public void setResolutions(String resolutions) {
		this.resolutions = resolutions;
	}

	public String getCallback_url() {
		return callback_url;
	}

	public void setCallback_url(String callback_url) {
		this.callback_url = callback_url;
	}



	private String initresult;
	private String bucket;
	private String host;
	private String resolutions;
	private String callback_url;
	private String accesskey;
	private String secretkey;
	//Transcode(String initresult,String bucket,String host,String resolutions,String callback_url,String accesskey,String secretkey)
	public transfer(String initresult,String bucket,String host,String resolutions,String callback_url,String accesskey,String secretkey) {
		this.initresult = initresult;
		this.bucket = bucket;
		this.host = host;
		this.resolutions = resolutions;
		this.callback_url = callback_url;
		this.accesskey = accesskey;
		this.secretkey = secretkey;
	}
	
  //SpeedyCloudS3 trans = new SpeedyCloudS3("http://106.2.24.17:8000/transcode","","");
  //String transret=trans.Transcode(init, bucket,host,"480P1,480P2,720P", "106.2.24.10:8080",accesskey,secretkey);
    public String TransferApi(String transurl,String watermark_path,String watermark_position) throws Exception {
    	  SpeedyCloudS3 trans = new SpeedyCloudS3(transurl,"","");
    	  String transret=trans.Transcode(initresult, bucket,host,resolutions, callback_url,accesskey,secretkey,watermark_path,watermark_position);
    	  return transret;
    }

}