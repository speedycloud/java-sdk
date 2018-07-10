/*
 *  SpeedyCloud Java SDK S3Sample
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.UUID;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.speedycloud.services.s3.AmazonS3;
import com.speedycloud.services.s3.AmazonS3Client;
import com.speedycloud.services.s3.model.Bucket;
import com.speedycloud.services.s3.model.GetObjectRequest;
import com.speedycloud.services.s3.model.ListObjectsRequest;
import com.speedycloud.services.s3.model.ObjectListing;
import com.speedycloud.services.s3.model.PutObjectRequest;
import com.speedycloud.services.s3.model.S3Object;
import com.speedycloud.services.s3.model.S3ObjectSummary;

public class S3Sample {

    // 配置信息，设置自己 AK、SK
    private static final String endpoint = "oss-cn-beijing.speedycloud.org";
    private static final String accessKey = "your-access-key";
    private static final String secretKey = "your-secret-key";

    public static void main(String[] args) throws IOException {

        // 实例化 S3 连接
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setSignerOverride("S3SignerType");
        clientConfig.setProtocol(Protocol.HTTP);
        AmazonS3 s3Client = new AmazonS3Client(credentials, clientConfig);
        s3Client.setEndpoint(endpoint);

        // 设置桶名、对象名
        String bucketName = "my-first-s3-bucket-" + UUID.randomUUID();
        String key = "MyObjectKey";

        System.out.println("===========================================");
        System.out.println("Getting Started with Amazon S3");
        System.out.println("===========================================\n");

        try {
            // 创建桶
            System.out.println("Creating bucket " + bucketName + "\n");
            s3Client.createBucket(bucketName);

            // 获取桶下对象列表
            System.out.println("Listing buckets");
            for (Bucket bucket : s3Client.listBuckets()) {
                System.out.println(" - " + bucket.getName());
            }
            System.out.println();

            // 上传对象
            System.out.println("Uploading a new object to S3 from a file\n");
            s3Client.putObject(new PutObjectRequest(bucketName, key, createSampleFile()));

            // 下载对象
            System.out.println("Downloading an object");
            S3Object object = s3Client.getObject(new GetObjectRequest(bucketName, key));
            System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
            displayTextInputStream(object.getObjectContent());

            // 获取桶内对象列表，同时指定前缀
            System.out.println("Listing objects");
            ObjectListing objectListing = s3Client.listObjects(new ListObjectsRequest()
                    .withBucketName(bucketName)
                    .withPrefix("My"));
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                System.out.println(" - " + objectSummary.getKey() + "  " +
                        "(size = " + objectSummary.getSize() + ")");
            }
            System.out.println();

            // 删除对象
            System.out.println("Deleting an object\n");
            s3Client.deleteObject(bucketName, key);

            // 删除桶
            System.out.println("Deleting bucket " + bucketName + "\n");
            s3Client.deleteBucket(bucketName);
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }

    /**
     * Creates a temporary file with text data to demonstrate uploading a file
     * to Amazon S3
     *
     * @return A newly created temporary file with text data.
     *
     * @throws IOException
     */
    private static File createSampleFile() throws IOException {
        File file = File.createTempFile("speedycloud-java-sdk-", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.write("01234567890112345678901234\n");
        writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
        writer.write("01234567890112345678901234\n");
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.close();

        return file;
    }

    /**
     * Displays the contents of the specified input stream as text.
     *
     * @param input
     *            The input stream to display as text.
     *
     * @throws IOException
     */
    private static void displayTextInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            System.out.println("    " + line);
        }
        System.out.println();
    }

}