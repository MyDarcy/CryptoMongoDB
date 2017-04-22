package test;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


/*
 * author: darcy
 * date: 2017/1/11 16:06
 * description: 
*/
public class FirstDemo {



    public static void main(String[] args) {
        try {
            Cipher c = Cipher.getInstance("DES"); // DES/CBC/PKCS5Padding
//            System.out.println(c.getBlockSize());//获取块大小
//            System.out.println(c.getAlgorithm());//获取加密算法
//            System.out.println(c.getProvider());

            /*SecretKey sk = new SecretKey() {
                public String getAlgorithm() {
                    return "darcy_enc_algorithms";
                }

                public String getFormat() {
                    return null;
                }

                public byte[] getEncoded() {
                    return null;
                }
            };*/

            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");

            SecretKey sk = keyGenerator.generateKey();

            c.init(Cipher.ENCRYPT_MODE, sk);
            String message = "tanglab 2016";
            byte[] bytes = message.getBytes();

            byte[] encyted_bytes = c.doFinal(bytes);
            System.out.println("length encrypted_bytes:" + encyted_bytes.length);
            for (int i = 0; i < encyted_bytes.length; i++) {
                System.out.print(encyted_bytes[i] + "\t");
            }
            System.out.println();
            System.out.println(new String(c.doFinal(encyted_bytes)));
            System.out.println("encrypted_bytes:" + encyted_bytes.length);

            Cipher c1 = Cipher.getInstance("DES");
            c1.init(Cipher.DECRYPT_MODE, sk);
            System.out.println(new String(c1.doFinal(encyted_bytes)));

            c.init(Cipher.DECRYPT_MODE, sk);
            System.out.println(new String(c.doFinal(encyted_bytes)));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

    }
}
