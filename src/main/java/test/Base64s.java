package test;

import sun.misc.BASE64Decoder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Author by darcy
 * Date on 17-4-22 上午10:48.
 * Description:
 */
public class Base64s {

    public static void main(String[] args) {
        String string1 = "Base64 in Java8";
        String string2 = "华南理工大学.广州.中国";
        encodeDecodeTest(string1);
        System.out.println();
        encodeDecodeTest(string2);

    }

    private static void encodeDecodeTest(String string1) {
        String encode = Base64.getEncoder()
                .encodeToString(string1.getBytes(StandardCharsets.UTF_8));
        System.out.println(encode);

        String decode = new String(Base64.getDecoder().decode(encode), StandardCharsets.UTF_8);
        System.out.println(decode);


    }

}
