package utils;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Author by darcy
 * Date on 17-4-22 下午4:11.
 * Description:
 */
public class EncryptionUtils {

    private static Cipher cipher;
    private static KeyGenerator keyGenerator;
    private static SecretKey sk;

    static {
        try {
            cipher = Cipher.getInstance("AES"); //  DES/CBC/NoPadding
            keyGenerator = KeyGenerator.getInstance("AES");
            sk = keyGenerator.generateKey();
            System.out.println(sk.getFormat());
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static byte[] encrytMessage(String message) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] messageBytes = message.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, sk);
        byte[] encryptedBytes = cipher.doFinal(messageBytes);
        return encryptedBytes;
    }

    public static String decryptMessage(byte[] encryptedBytes) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.DECRYPT_MODE, sk);
        byte[] decrytedBytes = cipher.doFinal(encryptedBytes);
        return new String(decrytedBytes);
    }

    public static String decryptMessage(String cipherMessage) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        return decryptMessage(cipherMessage.getBytes());
    }

    public static byte[] encrytMessage(Integer integer) throws BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        return encrytMessage(String.valueOf(integer));
    }
}
