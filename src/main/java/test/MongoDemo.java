package test;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.InsertOneOptions;
import com.mongodb.client.model.ValidationOptions;
import org.bson.Document;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by darcy on 2017/1/12.
 */

/*
 * author: darcy
 * date: 2017/1/12 15:03
 * description:
*/
public class MongoDemo {
    private static MongoClient mongoClient = new MongoClient();


    public static void test_students() {
        String databaseName = "first";
        String collectionName = "students";
        // 访问数据库
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        Document student = new Document("name", "darcy")
                .append("university", "scut")
                .append("grade", "grade 2")
                .append("skills", Arrays.asList("python", "java", "linux"))
                .append("info", new Document("address", "hubei wuhan").append("tel", 102));

        collection.insertOne(student);

        FindIterable<Document> documents = collection.find();

        for (Document document : documents) {
            Set<String> keySet = document.keySet();

            for (String key : keySet) {
                if (key.equals("skills")) {
                    System.out.println(key + "-->" +  document.get(key, List.class));
                } else if (key.equals("info")) {
                    System.out.println(key + "-->" + document.get(key, Document.class));
                } else {
                    System.out.println( key + "-->" + document.get(key));;
                }
            }

            System.out.println(document);
        }
    }

    public static void test_university() {
        String databaseName = "first";
        String collectionName = "universitys";
        // 访问数据库
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        Document document = new Document("name", "华南理工大学")
                .append("shortName", "华工")
                .append("englishName", "South China University Of Technology")
                .append("shortEnglishName", "scut")
                .append("address", "中国广州")
                .append("title", "985")
                .append("rank", "25");

        collection.insertOne(document);

        FindIterable<Document> documents = collection.find();
        System.out.println(document);
    }

    public static void main(String[] args) {

        // test students;
//        test_students();

        // test simple one;

        test_university();

    }

}
