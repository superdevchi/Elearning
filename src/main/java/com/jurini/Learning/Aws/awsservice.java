package com.jurini.Learning.Aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class awsservice {



    public String getFileImageName;

    public String URL;




    public String BucketName = "9music";


    public String accesskey = "AKIARAQEMD63IEOP7S4R";
    public String secrectkey = "WaV1iLHwLCqyAsKrjCElyZ1cSVYjF8wteU0LMcg8";

    AWSCredentials credentials = new BasicAWSCredentials(accesskey,secrectkey);



    AmazonS3 s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.EU_NORTH_1).build();


    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            System.out.println("Error converting multipartFile to file");
        }
        return convertedFile;
    }

    public void uploadFileImage(MultipartFile file){
        File FileObj = convertMultiPartFileToFile(file);
        String FileName = System.currentTimeMillis() + "" + file.getOriginalFilename();
        getFileImageName = FileName;
        s3client.putObject( new PutObjectRequest(BucketName,FileName,FileObj).withCannedAcl(CannedAccessControlList.PublicRead));

        URL = String.valueOf(s3client.getUrl(BucketName, FileName));

        System.out.println("uploaded check amazon" + "url" + URL);


    }

    public String GETURL(){
        return String.valueOf(s3client.getUrl(BucketName, getFileImageName));
    }
}
