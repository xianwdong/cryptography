import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author dxw
 * @date 2018/4/16
 * User for basic DH
 */
public class User {

    private BigInteger p;
    private BigInteger g;
    private BigInteger randomNumber;    // random number a for g ^ a mod p 1 <= a <= p - 2
    private BigInteger publicKey;       // g ^ a mod p
    private BigInteger publicKeyFromOther;
    private BigInteger symmetricKey;

    public User(BigInteger p, BigInteger g) {
        this.p = new BigInteger(p.toString());
        this.g = new BigInteger(g.toString());
        BigInteger n = p.subtract(new BigInteger("2"));
        do {
            // 这里的randomNumber没有必要用质数
            randomNumber = BigInteger.probablePrime(p.bitLength(), new SecureRandom());
        } while (randomNumber.compareTo(n) >= 0);
        publicKey = DH.quickPower(g, randomNumber, p);
    }

    public void sendPublicKey(User user) {
        user.publicKeyFromOther = new BigInteger(this.publicKey.toString());
    }

    public BigInteger getPublicKeyFromOther() {
        return publicKeyFromOther;
    }

    public BigInteger getRandomNumber() {
        return randomNumber;
    }

    public BigInteger getP() {
        return p;
    }

    public void setG(BigInteger g) {
        this.g = g;
    }

    public void setP(BigInteger p) {
        this.p = p;
    }
}
