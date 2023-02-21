/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.processunstructureddata;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.result.InsertOneResult;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

/**
 *
 * @author almadb
 */
public class UnstructuredDB {
    private final MongoClient mongoClient;
    private final String databaseName;
    private final String collectionName;
    private final MongoCollection<Document> coll;
    
    public UnstructuredDB(){
        String uri = "mongodb://almadb:almadb@localhost:27017/?maxPoolSize=20&w=majority";
        mongoClient = MongoClients.create(uri);
        databaseName = "almadb";
        collectionName = "testcollection";
        coll = mongoClient.getDatabase(databaseName)
            .getCollection(collectionName);
    }
    
    public void close(){
        mongoClient.close();
    }
    
    public List<String> find(String fieldName, String value){
        List<String> result = new ArrayList<>();

        // find code goes here
        //Bson filter = Filters.empty();
        Bson filter = eq(fieldName, value);

        MongoCursor<Document> cursor = coll.find(filter).iterator();

        try {
            while (cursor.hasNext()) {
                result.add(cursor.next().toJson());
            }

        } finally {
            cursor.close();
        }
        return result;
    }
    
    public List<String> findAll(){
        List<String> result = new ArrayList<>();

        // find code goes here
        Bson filter = Filters.empty();

        MongoCursor<Document> cursor = coll.find(filter).iterator();

        try {
            while (cursor.hasNext()) {
                result.add(cursor.next().toJson());
            }

        } finally {
            cursor.close();
        }
        return result;
    }
    
    public void insert(String item, String bucketName, String path){
        try {
            Document doc = Document.parse(item); // convert JSON to BSON
            InsertOneResult result = coll.insertOne(
                    doc
                        .append("_id", new ObjectId())
                        .append("BucketName", bucketName)
                        .append("Path", path)
            );
        } catch (MongoException me) {
            System.err.println("Unable to insert due to an error: " + me.getMessage());
        }
    }
}
