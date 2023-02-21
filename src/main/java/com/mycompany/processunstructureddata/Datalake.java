/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.processunstructureddata;

import io.minio.GetObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.messages.Item;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author almadb
 */
public class Datalake {
    
    private final MinioClient minioClient;
    
    public Datalake(){
        minioClient =
          MinioClient.builder()
              .endpoint("http://localhost:9000")
              .credentials("minio_access_key", "minio_secret_key")
              .build();
    }
    
    public Iterable<Result<Item>> getItemsFromBucket(String bucket){
        if(minioClient != null)
            return minioClient.listObjects(
            ListObjectsArgs.builder().bucket(bucket).recursive(true).build());
        else return null;
    }
    
    public List<String> getItemsNameFromBucket(String bucket){
        Iterable<Result<Item>> results = getItemsFromBucket(bucket);
        if(results == null) return null;
        ArrayList<String> itemsName = new ArrayList<>();
        for(Result<Item> r : results){
            try {
                itemsName.add(r.get().objectName());
            } catch (Exception e) {
                System.err.println("Error getting items: " + e.getMessage());
                itemsName = null;
                break;
            }
        }
        return itemsName;
    }
    
    public InputStream getObject(String bucketName, String objectName){
        InputStream stream;
        try {
            stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build());
        } catch (Exception e) {
            System.err.println("Error getting object: " + e.getMessage());
            stream = null;
        }
        return stream;
    }
}
