package data;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import utils.EncryptionUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.util.Base64;
import java.util.Set;

/**
 * Author by darcy
 * Date on 17-4-22 下午4:09.
 * Description:
 */
public class DataEncryption {

    private static Base64.Encoder encoder = Base64.getEncoder();
    private static Base64.Decoder decoder = Base64.getDecoder();

    public static void main(String[] args) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {

        cityFromPlainDBToEncryptDB();

    }

    private static void cityFromPlainDBToEncryptDB() throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        String databaseName = "world";
        String collectionName = "city";

        String encryptedDatabaseName = "encrypted_" + databaseName;
        String encryptedCollectionName = "encrypted_" + collectionName;

        MongoClient mClient = new MongoClient();
        MongoDatabase plainDB = mClient.getDatabase(databaseName);
        MongoCollection<Document> plainDBCollection = plainDB.getCollection(collectionName);

        MongoDatabase cipherDB = mClient.getDatabase(encryptedDatabaseName);
        MongoCollection<Document> cipherDBCollection = cipherDB.getCollection(encryptedCollectionName);

        FindIterable<Document> plainDocuments = plainDBCollection.find();
        for (Document pDocument : plainDocuments) {
            Set<String> keySet = pDocument.keySet();
            Document cipherDocument = new Document();
            for (String key : keySet) {
                if (key.equals("_id"))
                    continue;
                // 针对不不同的类型要调用不同的API，how to solve it...
                if (key.equals("population")) {
                    String cipherKey = new String(encoder
                            .encode(EncryptionUtils.encrytMessage(key)), StandardCharsets.UTF_8);
                    String cipherValue = new String(encoder
                            .encode(EncryptionUtils.encrytMessage(pDocument.getInteger(key))), StandardCharsets.UTF_8);
                    cipherDocument.append(cipherKey, cipherValue);
                } else {
                    String cipherKey = new String(encoder
                            .encode(EncryptionUtils.encrytMessage(key)), StandardCharsets.UTF_8);
                    String cipherValue = new String(encoder
                            .encode(EncryptionUtils.encrytMessage(pDocument.getString(key))), StandardCharsets.UTF_8);
                    cipherDocument.append(cipherKey, cipherValue);
                }

            }
            cipherDBCollection.insertOne(cipherDocument);
        }
    }


    private static void test() throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        String database = "world";
        String collection = "city";
        byte[] encrytMessage = EncryptionUtils.encrytMessage(database);
        byte[] encodedEncryptedMessage = encoder.encode(encrytMessage);
        byte[] decodedEncryptedMessage = decoder.decode(encodedEncryptedMessage);
        String decryptMessage = EncryptionUtils.decryptMessage(decodedEncryptedMessage);
        System.out.println(decryptMessage);

        /*System.out.println("raw encode str start");
        System.out.println(new String(encrytMessage));
        System.out.println("raw encode str end.\n");
        System.out.println("base64 encode start");
        System.out.println(new String(encoder.encode(encrytMessage), StandardCharsets.UTF_8));
        System.out.println("base64 decode end");*/
    }

}
