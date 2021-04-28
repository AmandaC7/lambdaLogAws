package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDateTime;
import java.util.Map;

// example.Handler value: example.example.Handler
public class Handler implements RequestHandler<Map<String, String>, Boolean> {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    static String bucketName = "bucketlistar";
    final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
    Boolean result;
    @Override
    public Boolean handleRequest(Map<String, String> event, Context context) {

        ObjectListing ol = s3.listObjects(bucketName);

        //Conectar numa s3 e listar os arquivos
/*
        List s3objects = s3.listObjects(bucketName).getObjectSummaries();
*/
        LocalDateTime now = LocalDateTime.now().minusDays(1);

        for (S3ObjectSummary objectSummary : ol.getObjectSummaries()) {

            if(StringUtils.fromDate(objectSummary.getLastModified()).compareTo(String.valueOf(now)) > 0){
                result = true;
            }
            else{
                result = false;
            }

            /*result = objectSummary.getKey() + "\n" +
                    objectSummary.getSize() + "\n" +
                    StringUtils.fromDate(objectSummary.getLastModified());*/
        }

        return result;
    }
}
