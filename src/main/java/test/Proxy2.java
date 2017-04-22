package test;

import com.mongodb.MongoClient;
import org.bson.Document;

import java.util.List;

/**
 * Created by darcy on 2017/2/20.
 */

/*
 * author: darcy
 * date: 2017/2/20 10:55
 * description:
*/
public interface Proxy2 {

    /**
     * @Description
     * @params
     * @return
     */
    void encryptData(String dbName, String collectionName, Document document);

/**
 * @Description 
 * @params  * @param null
 * @return 
 */
    void encryptDatas(String dbName, String collectionName, List<Document> documents);

    Document decryptData(String dbName, String collectionName);

    List<Document> decrytDatas(String dbName, String collectionName);


}
