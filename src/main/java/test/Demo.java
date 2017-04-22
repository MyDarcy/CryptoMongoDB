package test;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


/*
 * author: darcy
 * date: 2017/1/12 14:41
 * description: 
*/
public class Demo {

    private Cipher cipher;
    private KeyGenerator keyGenerator;
    private SecretKey sk;

    public Demo() {
        try {
            cipher = Cipher.getInstance("AES"); //  DES/CBC/NoPadding
            keyGenerator = KeyGenerator.getInstance("AES");
            sk = keyGenerator.generateKey();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public byte[] encrytMessage(String message) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] messageBytes = message.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, sk);
        byte[] encryptedBytes = cipher.doFinal(messageBytes);
        return encryptedBytes;
    }

    public String decryptMessage(byte[] encryptedBytes) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.DECRYPT_MODE, sk);
        byte[] decrytedBytes = cipher.doFinal(encryptedBytes);
        return new String(decrytedBytes);
    }

    public String decryptMessage(String cipherMessage) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        return decryptMessage(cipherMessage.getBytes());
    }

    public String bytesToHex(byte[] encryptedBytes) {
        return null;
    }

    public static void main(String[] args) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException, IOException {
        String text = "tanglab 2016 | 密码学 | 可搜索加密";
        BASE64Encoder base64Encoder = new BASE64Encoder();

        Demo demo = new Demo();
        byte[] encrytedMessage = demo.encrytMessage(text);
//        byte[] --> str to store in db;
        String encodeStr = base64Encoder.encode(encrytedMessage);
        System.out.println("encodeStr:" + encodeStr);
        System.out.println(encrytedMessage.length);
        System.out.println(demo.decryptMessage(encrytedMessage));
        BASE64Decoder base64Decoder = new BASE64Decoder();
        // encodeStr from db to byte[],
        System.out.println("decodeStr:" + demo.decryptMessage(base64Decoder.decodeBuffer(encodeStr)));

    }
}
