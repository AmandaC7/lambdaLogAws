package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;

import java.time.LocalDateTime;
import java.util.Map;

// example.Handler value: example.example.Handler
public class Handler implements RequestHandler<Map<String, String>, Boolean> {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    static String bucketName = "bucketlistar";
    final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
    final AmazonCloudWatch cw = AmazonCloudWatchClientBuilder.defaultClient();
    Boolean result;

    @Override
    public Boolean handleRequest(Map<String, String> event, Context context) {
        ObjectListing ol = s3.listObjects(bucketName);
        LocalDateTime now = LocalDateTime.now().minusDays(1);
        LambdaLogger logger = context.getLogger();
        String response = new String("SUCCESS");

        for (S3ObjectSummary objectSummary : ol.getObjectSummaries()) {
            if (StringUtils.fromDate(objectSummary.getLastModified()).compareTo(String.valueOf(now)) > 0) {
                logger.log(response);
                result = true;
            }
            else{
                logger.log(response = "ERROR");
                result = false;
            }
        }
        if(result == null){
            logger.log(response = "ERROR");
            return result = false;
        }

        System.out.println("The result is: " + result);
        return result;
    }
}
