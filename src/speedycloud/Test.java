package speedycloud;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;

public class Test {
    public static void main(String[] argc) throws Exception {
        //SpeedyCloudS3 s3api = new SpeedyCloudS3("705EE33BF78F96C80395184E78C024ED","020f25c3fcf27ccff0213dab8452595b7ffe4313fc14f636dff1a79b893a616c");
        //SpeedyCloudS3 s3api = new SpeedyCloudS3("http://118.119.254.216","8ECF99788044FA255AF79DD05451C450","df235c5664509dbe9c4971cdc7119ba3eb0228f1dae44a5e2df5cec378955b26");
    	//SpeedyCloudS3 s3api = new SpeedyCloudS3("8ECF99788044FA255AF79DD05451C451","df235c5664509dbe9c4971cdc7119ba3eb0228f1dae44a5e2df5cec378955b27");
    	//SpeedyCloudS3 s3api = new SpeedyCloudS3("8ECF99788044FA255AF79DD05451C450","df235c5664509dbe9c4971cdc7119ba3eb0228f1dae44a5e2df5cec378955b26");
//    	String host = "http://oss-cn-beijing.speedycloud.org";
//    	String ak= "5C0FA427C421219C0D67FF372AB71784";
//    	String sk = "d519b8b1a9c0cc51100ccff69a3f574c87ba2969ab7f8a8f30d243a8d5d7d69b";
    	//String ak = "24C30741003ADDCF5A3647F323FFCEEE";
    	//String sk = "8a6b964b53a870b10f71d85a61a7632a2a813eab60e475fbd5746aab0c233fff";
    	//SpeedyCloudS3 s3api = new SpeedyCloudS3(host,ak,sk);
        
        //create bucket 
        //String createBucket = s3api.createBucket("bucket4");
        //System.out.println(createBucket);
        
        //list bucket
    	//String list = s3api.list("wangjiyou");
        //System.out.println(list);

        //put file
        //String put = s3api.putObjectFromFile("wangjiyou","timg1-test.jpg","D:\\timg.jpg");
        //System.out.println(put);
//        String setkeyacl = s3api.updateKeyAcl("wangjiyou", "timg.jpg", "public-read");
//        System.out.println(setkeyacl);
        
        //put string
        //String putString = s3api.putObjectFromString("wangjiyou","bb.txt","wangjiyou hahahahhahaha");
        //System.out.println(putString);
        
        //set bucket acl
        //String setbucketacl = s3api.updateBucketAcl("wangjiyou",  "public-read");
        //System.out.println("setbucketacl:"+setbucketacl);
        
        //set key acl
        //String setkeyacl = s3api.updateKeyAcl("wangjiyou", "bb.txt", "public-read");
        //System.out.println(setkeyacl);
        
    	//String deleteobj = s3api.deleteKey("bucket12", "ddf");
    	//System.out.println(deleteobj);
//        String delete = s3api.deleteBucket("bucket12");
//        System.out.println(delete);
        //String createBucket = s3api.createBucket("wangjiyou");
        //System.out.println(createBucket);
        //String putObjectFromFile(String bucket, String key, String path)
    	/**/
        
    	//String key = URLEncoder.encode("ubuntu.pdf", "utf-8");
        //String k1 = key;//key.replaceAll("\\+", "%20");
        //String setbucketacl = s3api.updateBucketAcl("wangjiyou",  "public-read");
        //System.out.println("setbucketacl:"+setbucketacl);
        //String put = s3api.putObjectFromFile("test","web1111.html","D:\\web.html");
        //System.out.println(put);
        //String setkeyacl = s3api.updateKeyAcl("", "timg.jpg", "public-read");
        //System.out.println(setkeyacl);
        //int a = s3api.IsExsit("HEAD", "code","timg.jpg");
        //System.out.println(a);
        
        //put = s3api.putObjectFromZipFile(bucket, key, path)
   /*     
    *     	String key = URLEncoder.encode("aa.txt", "utf-8");
        String k1 = key;//key.replaceAll("\\+", "%20");
        String put = s3api.putObjectFromFile("wangjiyou",k1,"D:\\Java\\jdk1.8.0_141\\LICENSE");
        System.out.println(put);
        String putString = s3api.putObjectFromString("wangjiyou","bb.txt","wangjiyou hahahahhahaha");
        System.out.println(putString);
        String setkeyacl = s3api.updateKeyAcl("wangjiyou", "bb.txt", "public-read");
        System.out.println(setkeyacl);
        String setbucketacl = s3api.updateBucketAcl("wangjiyou",  "public-read");
        System.out.println("setbucketacl:"+setbucketacl);
     */  
        //http://106.2.24.17/video_source
        //init mysql api
        //http://106.2.24.17:8000/video_source Ϊmysql api�������̶���ַ
        
        
        /*
                      ������
        url: ���ϴ�������洢�Ķ����ur�ˣ����
        address: ��Դ�ĵ�ַ
        bucket: Ŀ��Ͱ
        host: Ŀ��Ͱ��host
    	����ֵ��
        200�� {"source_id": "404dbfe2-d66a-11e7-a00b-000c29b58aad", "status": "Success"}
         * */
//        String url = "http://oss-cn-beijing.speedycloud.org/centaline/paranora.mp4";
//        String address="";
//        String bucket="centaline";
//        String host = "oss-cn-beijing.speedycloud.org";
//        String accesskey = "24C30741003ADDCF5A3647F323FFCEEE";
//        String secretkey = "8a6b964b53a870b10f71d85a61a7632a2a813eab60e475fbd5746aab0c233fff";
//        SpeedyCloudS3 sqlapi = new SpeedyCloudS3("http://106.2.24.17:8000/video_source","","");
//        String init = sqlapi.InitMysql( url, address,  bucket, host,accesskey,secretkey);
//        System.out.println(init);
        
        
        //ת��
        //http://106.2.24.17:8000/transcode Ϊת��������̶���ַ
        
        /*
         *     ������
        init: ��ʼ��mysql api�ķ���ֵ
        bucket: Ŀ��Ͱ
        host: Ŀ��Ͱ��host
        resolutions: �ֱ��ʣ�����á������ָ������磺480P1,480P2,720P
        callback_url: �ص�url
        //source_id: �ϸ��ӿڷ��ص�source_id
         * 
         * */
//        SpeedyCloudS3 trans = new SpeedyCloudS3("http://106.2.24.17:8000/transcode","","");
//        String transret=trans.Transcode(init, bucket,host,"480P1,480P2,720P", "106.2.24.10:8080",accesskey,secretkey);
//        System.out.println(transret);
    	
    	
    	//����ת��ʾ��
    	/*
    	 * ����˵����
    	 *         	bucket: Ŀ��Ͱ
        		    key: ����洢��������
        		    localfilepath�� �����ļ�
        		    houseaddress����Դ��ַ
        			resolutions: �ֱ��ʣ�����á������ָ������磺480P1,480P2,720P
        			callback_url: �ص�url
        			watermark_path:ˮӡͼƬurl�� //"http://djaksdjak/a.jpg"
					watermark_position: ˮӡλ��//"left_up" "right_up" "left_down" "right_down" "center"
    	 * 
    	 * 
    	 * */
    	
    	String ak = "24C30741003ADDCF5A3647F323FFCEEE";
    	String sk = "8a6b964b53a870b10f71d85a61a7632a2a813eab60e475fbd5746aab0c233fff";
    	//http://oss-cn-beijing.speedycloud.org/centaline/paranora-A.mp4
    	SpeedyCloudS3 s3api = new SpeedyCloudS3("http://oss-cn-beijing.speedycloud.org",ak,sk);
    	
    	String bucket = "wangjiyou";
    	String key ="test.mp4";
    	String localfilepath = "D:\\test.mp4";
    	String houseaddress = "";
    	String resolutions = "480P1,480P2,720P";
    	String callback_url = "http://106.2.24.10:8080";

    	String watermark_path =  "http://oss-cn-beijing.speedycloud.org/centaline/zhongyuan_water.png";
    	String watermark_position = "center";
    	
    	String ret = s3api.PutTranscodeFinal("centaline", "wangjiyou-test.mp4", "D:\\fuckjiji.mp4","shanghai city","480P1,480P2,720P", "106.2.24.10:8080",watermark_path,watermark_position);
    	//String ret = s3api.PutTranscode("centaline", "wangjiyou-test.mp4", "D:\\fuckjiji.mp4","shanghai city","480P1,480P2,720P", "106.2.24.10:8080",watermark_path,watermark_position);
    	System.out.println("======================");
    	System.out.println(ret);
    }
}
