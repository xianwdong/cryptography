import java.math.BigInteger;
import java.security.SecureRandom;

import static common.Util.getOriginalRoot;

/**
 * @author dxw
 * @date 2018/5/9
 */
public class Main {

    public static void main(String[] args) {
        int length = 512;
        BigInteger p = BigInteger.probablePrime(length, new SecureRandom());
        BigInteger g = getOriginalRoot(p, length);
        User4[] users = new User4[5];
        for (int i = 0; i < users.length; ++i) {
            users[i] = new User4(p, g);
        }
        for (int i = 0; i < users.length; ++i) {
            BigInteger publicKey = users[i].getPublicKey();
            for (int j = 0; j < users.length; ++j) {
                users[j].getPublicKeyList().add(publicKey);
            }
        }
        for (int i = 0; i < users.length; ++i) {
            System.out.println("第" + (i + 1) + "个用户: " + users[i].getCommonKey());
        }
    }

}
