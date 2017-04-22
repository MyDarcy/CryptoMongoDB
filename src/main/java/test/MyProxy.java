package test;

import com.mongodb.MongoClient;
import org.bson.Document;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.util.List;

/**
 * Created by darcy on 2017/2/20.
 */

/*
 * author: darcy
 * date: 2017/2/20 11:09
 * description: 
*/
public class MyProxy implements Proxy2 {

    MongoClient mongoClient;
    BASE64Encoder base64Encoder = new BASE64Encoder();
    BASE64Decoder base64Decoder = new BASE64Decoder();

    public MyProxy(MongoClient client) {
        this.mongoClient = client;
    }

    @Override
    public void encryptData(String cipherDatabase, String cipherCollection, Document document) {

    }


    /**
     * @Description
     * @param cipherDatabase
     * @param cipherCollection
     * @param documents
     * @return void
     */
    @Override
    public void encryptDatas(String cipherDatabase, String cipherCollection, List<Document> documents) {

    }

    @Override
    public Document decryptData(String cipherDatabase, String cipherCollection) {
        return null;
    }

    @Override
    public List<Document> decrytDatas(String cipherDatabase, String cipherCollection) {
        return null;
    }
}
