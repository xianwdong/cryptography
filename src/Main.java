import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author dxw
 * @date 2018/4/16
 */
public class Main {

    public static void main(String[] args) throws Exception {

        int length = 512;
        BigInteger p = BigInteger.probablePrime(length, new SecureRandom());
        BigInteger g = DH.getOriginalRoot(p, length);
        User userA = new User(p, g);
        User userB = new User(p, g);

        System.out.println(DH.getSymmetricKey(userA, userB));




    }

}
