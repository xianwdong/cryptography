import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author dxw
 * @date 2018/4/18
 */
public class BD {

    public static void broadcastZ(User3[] users) {
        int n = users.length;
        for (int i = 0; i < users.length; ++i) {
            users[(i + 1) % n].setZPre(users[i].getZi());
            users[(i - 1 + n) % n].setZNext(users[i].getZi());
        }
    }

    public static void broadcastX(User3[] users, BigInteger p) {
        int n = users.length;
        BigInteger[] XArr = new BigInteger[n];
        for (int i = 0; i < n; ++i) {
            BigInteger temp = users[i].getZPre().modInverse(p);
            XArr[i] = DH.quickPower(users[i].getZNext().multiply(temp).mod(p), users[i].getRandomNumber(), p);
            users[i].setXi(XArr);
        }
    }

    public static BigInteger getK(User3[] users, BigInteger p) {
        int n = users.length;
        BigInteger[] XArr = users[0].getXi();
        BigInteger result = new BigInteger("1");

        for (int j = 0; j < n; ++j) {
            result = new BigInteger("1");
            int i;
            for (i = 1; i <= n - 1; ++i) {
                result = result.multiply(XArr[(i + j - 1) % n].pow(n - i)).mod(p);
            }
            result = result.multiply(DH.quickPower(users[j].getZPre().pow(n), users[j].getRandomNumber(), p)).mod(p);
            System.out.println(result);
        }

        return result;
    }

    public static void main(String[] args) {
        int n = 5;
        int length = 512;
        BigInteger p = BigInteger.probablePrime(length, new SecureRandom());
        BigInteger g = DH.getOriginalRoot(p, length);
        User3[] users = new User3[n];
        for (int i = 0; i < n; ++i) {
            users[i] = new User3(p, g);
        }
        broadcastZ(users);
        broadcastX(users, p);
        getK(users, p);

        BigInteger[] rr = new BigInteger[n];
        for (int i = 0; i < n; ++i) {
            if (i != n - 1) {
                rr[i] = users[i].getRandomNumber().multiply(users[i + 1].getRandomNumber());
            } else {
                rr[i] = users[i].getRandomNumber().multiply(users[0].getRandomNumber());
            }
        }
        BigInteger result = new BigInteger("1");
        for (int i = 0; i < n; ++i) {
            result = result.multiply(DH.quickPower(g, rr[i], p)).mod(p);
        }
        System.out.println(result);
    }

}
