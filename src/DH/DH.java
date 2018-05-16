package DH;

import java.math.BigInteger;
import java.security.SecureRandom;

import static common.Util.getOriginalRoot;
import static common.Util.quickPower;

public class DH {

    public static BigInteger getSymmetricKey(DH.User userA, DH.User userB) throws Exception {
        userA.sendPublicKey(userB);
        userB.sendPublicKey(userA);
        BigInteger symmetricKey1 = quickPower(userA.getPublicKeyFromOther(), userA.getRandomNumber(),
                userA.getP());
        BigInteger symmetricKey2 = quickPower(userB.getPublicKeyFromOther(), userB.getRandomNumber(),
                userB.getP());
        if (symmetricKey1.equals(symmetricKey2)) {
            return symmetricKey1;
        }
        throw new Exception("密钥计算错误");
    }

    public static void main(String[] args) throws Exception {

        int length = 512;
        BigInteger p = BigInteger.probablePrime(length, new SecureRandom());
        BigInteger g = getOriginalRoot(p, length);
        DH.User userA = new DH.User(p, g);
        DH.User userB = new DH.User(p, g);

        System.out.println(DH.getSymmetricKey(userA, userB));

    }
}
