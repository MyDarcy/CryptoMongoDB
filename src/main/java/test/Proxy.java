package test;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.swing.*;
import java.security.InvalidKeyException;
import java.util.Set;

/**
 * Created by darcy on 2017/1/12.
 */

/*
 * author: darcy
 * date: 2017/1/12 15:06
 * description: 
*/
public class Proxy {

    private static MongoClient mongoClient = new MongoClient();

    public static void test_enc() throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        Demo demo = new Demo();

        String databaseName = "first";
        String collectionName = "universitys";
        // 访问数据库
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        /*Document document = new Document("name", "华南理工大学")
                .append("shortName", "华工")
                .append("englishName", "South China University Of Technology")
                .append("shortEnglishName", "scut")
                .append("address", "中国广州")
                .append("title", "985")
                .append("rank", 25);*/

        String cipherDatabaseName = databaseName;
        String cipherCollectionName = new String(demo.encrytMessage(collectionName));
        MongoDatabase cipherDatabase = mongoClient.getDatabase(cipherDatabaseName);
        MongoCollection<Document> cipherCollection = cipherDatabase.getCollection(cipherCollectionName);


        FindIterable<Document> documents = collection.find();
        for (Document document : documents) {
            Set<String> keySet = document.keySet();
            Document cipherDocument = new Document();
            for (String key : keySet) {
                if (key.equals("_id"))
                    continue;
                String cipherKey = String.valueOf(key.hashCode())/*new String(demo.encrytMessage(key))*/;
                String value = document.getString(key);
                String cipherValue = new String(demo.encrytMessage(value));
                cipherDocument.append(cipherKey, cipherValue);
            }
            cipherCollection.insertOne(cipherDocument);
        }

//        FindIterable<Document> cipherDocuments = cipherCollection.find();
//        System.out.println("plaintext:");
//        for (Document cipherDocument : cipherDocuments) {
//            Set<String> cipherKeySet = cipherDocument.keySet();
//            Document plaintextDocument = new Document();
//            for (String cipherKey : cipherKeySet) {
//                String key = demo.decryptMessage(cipherKey);
//                String cipherValue = cipherDocument.getString(cipherKey);
//                String value = demo.decryptMessage(cipherValue);
//                plaintextDocument.append(key, value);
//            }
//            System.out.println(plaintextDocument);
//        }
    }

    public static void main(String[] args) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        test_enc();
    }

}
