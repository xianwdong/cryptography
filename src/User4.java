import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.LinkedList;

import static common.Util.quickPower;

/**
 * @author dxw
 * @date 2018/5/6
 */
public class User4 {

    // public值
    private BigInteger p;
    private BigInteger g;

    // 随机值
    private BigInteger a;
    private BigInteger x;

    // 存储所有的公钥
    private LinkedList<BigInteger> publicKeyList = new LinkedList<>();

    public User4() {}

    public User4(BigInteger p, BigInteger g) {
        this.p = new BigInteger(p.toString());
        this.g = new BigInteger(g.toString());
        BigInteger n = p.subtract(new BigInteger("2"));
        do {
            a = new BigInteger(p.bitLength(), new SecureRandom());
        } while (a.compareTo(n) >= 0);
        do {
            x = new BigInteger(p.bitLength(), new SecureRandom());
        } while (x.compareTo(n) >= 0);
    }

    public BigInteger getPublicKey() {
        BigInteger temp = a.multiply(x).mod(p).multiply(x).mod(p);
        return quickPower(g, temp, p);
    }

    public BigInteger getCommonKey() {
        BigInteger result = new BigInteger("1");
        for (BigInteger number : publicKeyList) {
            result = result.multiply(number).mod(p);
        }
        return result;
    }

    public LinkedList<BigInteger> getPublicKeyList() {
        return publicKeyList;
    }
}
