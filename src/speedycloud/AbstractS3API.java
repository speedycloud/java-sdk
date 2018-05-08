package speedycloud;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.*;
import java.text.SimpleDateFormat;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.TreeMap;


public class AbstractS3API {
    private String host;
    private String access_key;
    private String secret_key;
    private SortedMap<String, String> metadata;

    public AbstractS3API(String host,String access_key, String secret_key) {
    	//this.host = "http://118.119.254.216";//"http://cos.speedycloud.org";
        //this.host = "http://103.235.221.75";//"http://cos.speedycloud.org";
    	this.host = host;
        this.access_key = access_key;
        this.secret_key = secret_key;
        this.metadata = new TreeMap<String, String>();
    }

    private String createSignString(String... args) {
        if (args.length == 5) {
            String sign = args[0];
            for (int i = 1; i < 4; i++) {
                sign += "\n" + args[i];
            }
            for (Map.Entry<String, String> entry : this.metadata.entrySet()) {
                sign += "\n" + entry.getKey().toLowerCase() + ":" + entry.getValue();
            }
            sign += "\n" + args[4];
            return sign;
        } else {
            String sign = args[0];
            for (int i = 1; i < 5; i++) {
                sign += "\n" + args[i];
            }
            for (Map.Entry<String, String> entry : this.metadata.entrySet()) {
                sign += "\n" + entry.getKey().toLowerCase() + ":" + entry.getValue();
            }
            sign += "\n" + args[5];
            return sign;
        }
    }

    private String createSign(String... args) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] key = this.secret_key.getBytes();
        SecretKey secretKey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(secretKey);
        String signString = createSignString(args);
        byte[] data = signString.getBytes();
        
        String value = Base64.getEncoder().encodeToString(mac.doFinal(data));
        System.out.println(value);
        return value;
    }

    private String putData(String method, String url, String data, String type) {
        try {
            URL localURL = new URL(this.host + url);
            URLConnection connection = localURL.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
            for (Map.Entry<String, String> entry : this.metadata.entrySet()) {
                httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setDoOutput(true);
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz",Locale.ENGLISH);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = new Date();
            String requestDate = dateFormat.format(date);
            httpURLConnection.setRequestProperty("Date", requestDate);
            try {
                httpURLConnection.setRequestProperty("Authorization", "AWS " + this.access_key + ":" + createSign(method, "", "", requestDate, url));
            } catch (InvalidKeyException e) {
                return e.getMessage();
            } catch (NoSuchAlgorithmException e) {
                return e.getMessage();
            } finally {

            }
            httpURLConnection.setConnectTimeout(10000);
            long contentLength = 0;

            if (type.equals("file")) {
                File file = new File(data);
                if (file.length() > 1024 * 1024 * 1024) {
                    return "File is bigger than 1G!";
                }
                contentLength = file.length();
                httpURLConnection.setRequestProperty("Content-Length", Long.toString(contentLength));
                FileInputStream fileInputStream = new FileInputStream(data);
                byte[] buffer = new byte[1024];
                int length = -1;
              
                httpURLConnection.setRequestProperty("Connection", "Close");
                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                while ((length = fileInputStream.read(buffer)) != -1) {
                    dataOutputStream.write(buffer, 0, length);
                }
                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();
            } else {
                byte[] requestStringBytes = data.getBytes();
                contentLength = requestStringBytes.length;
                httpURLConnection.setRequestProperty("Content-Length", Long.toString(contentLength));

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(requestStringBytes);
                outputStream.close();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
            String content = "";
            String line;
            while ((line = reader.readLine()) != null) {
                content += line;
            }
            reader.close();
            //System.out.println(httpURLConnection.getResponseCode());
            int code= httpURLConnection.getResponseCode();
            httpURLConnection.disconnect();
            return String.valueOf(code);
            
            //return content;
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public String request(String method, String url) {
        try {
            URL localURL = new URL(this.host + url);
            URLConnection connection = localURL.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
            for (Map.Entry<String, String> entry : this.metadata.entrySet()) {
                httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setDoOutput(true);
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz",Locale.ENGLISH);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = new Date();
            String requestDate = dateFormat.format(date);
            System.out.println(requestDate);
            httpURLConnection.setRequestProperty("Date", requestDate);
            try {
                httpURLConnection.setRequestProperty("Authorization", "AWS " + this.access_key + ":" + createSign(method, "", "", requestDate, url));
            } 
            catch (InvalidKeyException e) {
                return e.getMessage();
            } catch (NoSuchAlgorithmException e) {
                return e.getMessage();
            } finally {
//                httpURLConnection.disconnect();
            }
            httpURLConnection.setConnectTimeout(10000);
            System.out.println(httpURLConnection.getResponseCode());
            System.out.println(httpURLConnection.getContentLengthLong());
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
            String content = "";
            String line;
            while ((line = reader.readLine()) != null) {
                content += line;
            }
            reader.close();
            httpURLConnection.disconnect();
            return content;
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    public String requestUpdate(String method, String url, String acl) {
        try {
            URL localURL = new URL(this.host + url);
            URLConnection connection = localURL.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
            httpURLConnection.setRequestProperty("X-Amz-Acl", acl);
            for (Map.Entry<String, String> entry : this.metadata.entrySet()) {
                httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
            httpURLConnection.setRequestMethod(method);
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz",Locale.ENGLISH);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = new Date();
            String requestDate = dateFormat.format(date);
            httpURLConnection.setRequestProperty("Date", requestDate);
            try {
                //httpURLConnection.setRequestProperty("Authorization", "AWS " + this.access_key + ":" + createSign(method, "", "", acl, requestDate, url));
		httpURLConnection.setRequestProperty("Authorization", "AWS " + this.access_key + ":" + createSign(method, "", "", requestDate,"x-amz-acl:"+acl, url));
            } catch (InvalidKeyException e) {
                return e.getMessage();
            } catch (NoSuchAlgorithmException e) {
                return e.getMessage();
            } finally {
                httpURLConnection.disconnect();
            }
            httpURLConnection.setConnectTimeout(10000);
            System.out.println(httpURLConnection.getResponseCode());
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
            String content = "";
            String line;
            while ((line = reader.readLine()) != null) {
                content += line;
            }
            reader.close();
            httpURLConnection.disconnect();
            return content;
        } catch (IOException e) {
            return e.getMessage();
        }
    }
    
    public int requestIsExsit(String method, String url) {
        try {
            URL localURL = new URL(this.host + url);
            URLConnection connection = localURL.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
            for (Map.Entry<String, String> entry : this.metadata.entrySet()) {
                httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setDoOutput(true);
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz",Locale.ENGLISH);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = new Date();
            String requestDate = dateFormat.format(date);
            System.out.println(requestDate);
            httpURLConnection.setRequestProperty("Date", requestDate);
            try {
                httpURLConnection.setRequestProperty("Authorization", "AWS " + this.access_key + ":" + createSign(method, "", "", requestDate, url));
            } 
            catch (InvalidKeyException e) {
                return 0;
            } catch (NoSuchAlgorithmException e) {
                return 0;
            } finally {
//                httpURLConnection.disconnect();
            }
            httpURLConnection.setConnectTimeout(10000);
            return httpURLConnection.getResponseCode();
            //return httpURLConnection.getContentLength();
        } catch (IOException e) {
            return 0;
        }
    }
    
    public String requestInitMysql(String method, String data) {
        try {
            URL localURL = new URL(this.host);
            URLConnection connection = localURL.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
            for (Map.Entry<String, String> entry : this.metadata.entrySet()) {
                httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setDoOutput(true);
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz",Locale.ENGLISH);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = new Date();
            String requestDate = dateFormat.format(date);
            System.out.println(requestDate);
            httpURLConnection.setRequestProperty("Date", requestDate);
      
            byte[] requestStringBytes = data.getBytes();
            int contentLength = requestStringBytes.length;
            httpURLConnection.setRequestProperty("Content-Length", Long.toString(contentLength));

            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(requestStringBytes);
            outputStream.close();
            
            

            httpURLConnection.setConnectTimeout(10000);
            System.out.println(httpURLConnection.getResponseCode());
            System.out.println(httpURLConnection.getContentLengthLong());
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
            String content = "";
            String line;
            while ((line = reader.readLine()) != null) {
                content += line;
            }
            reader.close();
            httpURLConnection.disconnect();
            return content;
        } catch (IOException e) {
            return e.getMessage();
        }
    }    
    
    
    private SCResult putDataMulti(String method, String url, String data, String type) {
    	SCResult scresult = new SCResult();
        try {
            URL localURL = new URL(this.host + url);
            URLConnection connection = localURL.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
            for (Map.Entry<String, String> entry : this.metadata.entrySet()) {
                httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setDoOutput(true);
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz",Locale.ENGLISH);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date date = new Date();
            String requestDate = dateFormat.format(date);
            httpURLConnection.setRequestProperty("Date", requestDate);
            try {
                httpURLConnection.setRequestProperty("Authorization", "AWS " + this.access_key + ":" + createSign(method, "", "", requestDate, url));
            } catch (InvalidKeyException e) {
            	scresult.setMsg(e.getMessage());
            	scresult.setCode(400);
                return scresult;
            } catch (NoSuchAlgorithmException e) {
            	scresult.setMsg(e.getMessage());
            	scresult.setCode(400);
                return scresult;
            } finally {

            }
            httpURLConnection.setConnectTimeout(10000);
            long contentLength = 0;

            if (type.equals("file")) {
                File file = new File(data);
                if (file.length() > 1024 * 1024 * 1024) {
                	scresult.setMsg("File is bigger than 1G!");
                	scresult.setCode(400);
                    return scresult;                    
                }
                contentLength = file.length();
                httpURLConnection.setRequestProperty("Content-Length", Long.toString(contentLength));
                FileInputStream fileInputStream = new FileInputStream(data);
                byte[] buffer = new byte[1024];
                int length = -1;
              
                httpURLConnection.setRequestProperty("Connection", "Close");
                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                while ((length = fileInputStream.read(buffer)) != -1) {
                    dataOutputStream.write(buffer, 0, length);
                }
                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();
            } else {
                byte[] requestStringBytes = data.getBytes();
                contentLength = requestStringBytes.length;
                httpURLConnection.setRequestProperty("Content-Length", Long.toString(contentLength));

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(requestStringBytes);
                outputStream.close();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
            String content = "";
            String line;
            while ((line = reader.readLine()) != null) {
                content += line;
            }
            reader.close();
            //System.out.println(httpURLConnection.getResponseCode());
            int code= httpURLConnection.getResponseCode();
            httpURLConnection.disconnect();
            Map<String, List<String>> map = httpURLConnection.getHeaderFields();
            //String.valueOf(code);
            if(code==200) {
            	scresult.setCode(200);
            	String etag = map.get("Etag").get(0);
            	scresult.seteTag(etag.substring(1, etag.length()-1));
                return scresult;
            }else {
            	scresult.setMsg(content);
            	scresult.setCode(code);
                return scresult;
            }
            
            //return content;
        } catch (IOException e) {
        	scresult.setMsg(e.getMessage());
        	scresult.setCode(400);
            return scresult;
        }
    }

    public String putKeyFromFile(String method, String url, String path) {
        return putData(method, url, path, "file");
    }
    
    public SCResult putKeyFromFileMulti(String method, String url, String path) {
        return putDataMulti(method, url, path, "file");
    }

    public String putKeyFromString(String method, String url, String requestString) {
        return putData(method, url, requestString, "string");
    }

    public String putString(String method, String url, String requestString) {
        return putData(method, url, requestString, "string");
    }

    public void setMeta(String key, String value) {
        String first = key.substring(0, 1).toUpperCase();
        String rest = key.substring(1, key.length());
        this.metadata.put("X-Amz-Meta-" + first + rest, value);
    }
}
