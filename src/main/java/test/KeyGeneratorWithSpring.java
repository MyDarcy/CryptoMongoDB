package test;

import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;

/**
 * Author by darcy
 * Date on 17-4-22 上午11:55.
 * Description:
 */
public class KeyGeneratorWithSpring {
    public static void main(String[] args) {
        BytesKeyGenerator keyGenerator = KeyGenerators.secureRandom(128);
        System.out.println(new String(keyGenerator.generateKey()));




    }
}
