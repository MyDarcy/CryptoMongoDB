package test;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.Set;



/*
 * author: darcy
 * date: 2017/1/12 15:06
 * description: 
*/
public class Proxy22222 {
    private static MongoClient mongoClient = new MongoClient();


    public static void main(String[] args) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, IOException {
        String cipherKey = null;
        String value = null;
        String cipherValue = null;
        byte[] cipherValueBytes16 = null;
        BASE64Encoder base64Encoder = new BASE64Encoder();
        BASE64Decoder base64Decoder = new BASE64Decoder();

        Demo demo = new Demo();
        String databaseName = "first";
        String collectionName = "students";//选择明文数据集合
        Block<Document> printBlock = new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document.toJson());
            }
        };
        // 访问数据库
        MongoDatabase cpDatabaseName = mongoClient.getDatabase(databaseName);//明密文数据集合都在这个数据库里头
        MongoCollection plainCollection=cpDatabaseName.getCollection(collectionName);
        MongoCollection<Document> cipherCollection =
                cpDatabaseName.getCollection("encrypt_students");//密文数据集合

        /*
        插入密文数据
         */
        FindIterable<Document> plainDocuments = plainCollection.find();//获取明文集合中的数据
        //对每一条数据都加密
        for (Document document : plainDocuments) {
            Set<String> keySet = document.keySet();
            Document cipherDocument = new Document();
            for (String key : keySet) {
                if (key.equals("_id"))
                    continue;//不加密_id字段
                cipherKey = base64Encoder.encode(demo.encrytMessage(key));//转成16进制字符串存储
                value = document.getString(key);//找到对应的明文value
                cipherValue = base64Encoder.encode(demo.encrytMessage(value));
                cipherDocument.append(cipherKey, cipherValue);
            }
            cipherCollection.insertOne(cipherDocument);
        }
        /*
        读取密文数据并解密
         */
        FindIterable<Document> cypherDocuments=cipherCollection.find();//获取密文集合中的数据
        for (Document document : cypherDocuments){
            Set<String> keySet=document.keySet();
            for (String key : keySet){
                if (key.equals("_id"))
                    continue;
                String plainKey=demo.decryptMessage(base64Decoder.decodeBuffer(key));
                value=document.getString(key);
                String plainValue=demo.decryptMessage(base64Decoder.decodeBuffer(value));
                System.out.println("明文key:"+plainKey);
                System.out.println("明文value:"+plainValue);
            }
        }
    }
}
