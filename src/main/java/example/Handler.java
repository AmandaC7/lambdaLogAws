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

import java.util.List;
import java.util.Map;

// example.Handler value: example.example.Handler
public class Handler implements RequestHandler<Map<String, String>, List> {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    static String bucketName = "bucketlistar";

    @Override
    public List handleRequest(Map<String, String> event, Context context) {

        //Conectar numa s3 e listar os arquivos
        final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
        ObjectListing ol = s3.listObjects(bucketName);

        List s3objects = s3.listObjects(bucketName).getObjectSummaries();


        return s3objects;
    }
}
