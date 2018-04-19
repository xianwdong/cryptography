import sun.plugin2.message.Message;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * @author dxw
 * @date 2018/4/17
 * user for MTI
 */
public class User2 {

    private BigInteger p;
    private BigInteger g;
    private BigInteger randomNumber;
    // 一个秘密指数
    private BigInteger a;

    private Certificate certificate;
    private Certificate certificateFromOther;

    public User2() {}

    public User2(BigInteger p, BigInteger g) {
        this.p = new BigInteger(p.toString());
        this.g = new BigInteger(g.toString());
        BigInteger n = p.subtract(new BigInteger("2"));
        // 这里的randomNumber和a都没必要是质数
        do {
            randomNumber = BigInteger.probablePrime(p.bitLength(), new SecureRandom());
        } while (randomNumber.compareTo(n) >= 0);
        do {
            a = BigInteger.probablePrime(p.bitLength(), new SecureRandom());
        } while (a.compareTo(n) >= 0);
        certificate = new Certificate();
        // id和b都是可以公开的
        String id = UUID.randomUUID().toString();
        BigInteger b = DH.quickPower(g, a, p);
        certificate.setId(id);
        certificate.setB(b);
        certificate.setS(DH.quickPower(g, randomNumber, p));
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public void setCertificateFromOther(Certificate certificateFromOther) {
        this.certificateFromOther = certificateFromOther;
    }

    public Certificate getCertificateFromOther() {
        return certificateFromOther;
    }

    public BigInteger getA() {
        return a;
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getRandomNumber() {
        return randomNumber;
    }
}

class Certificate {
    private String id;
    private BigInteger b;
    // S = a ^ randomNumber mod p
    private BigInteger S;
    private String sig;

    public void setB(BigInteger b) {
        this.b = b;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSig(String md) {
        this.sig = md;
    }

    public BigInteger getB() {
        return b;
    }

    public String getId() {
        return id;
    }

    public String getSig() {
        return sig;
    }

    public void setS(BigInteger s) {
        S = s;
    }

    public BigInteger getS() {
        return S;
    }
}