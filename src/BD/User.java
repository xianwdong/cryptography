package BD;

import java.math.BigInteger;
import java.security.SecureRandom;

import static common.Util.quickPower;

public class User {

    private BigInteger p;
    private BigInteger g;
    // 每个Ui的随机数
    private BigInteger randomNumber;
    // 用户广播的Zi: g ^ randomNumber mod p
    private BigInteger Zi;
    private BigInteger ZNext;
    private BigInteger ZPre;

    // Xi = (Zi+1/Zi-1) ^ randomNumber
    // (Zi+1/Zi-1)不是表示整数的除法, 需要用Zi+1乘以Zi-1模p的逆元
    private BigInteger[] Xi;

    public User() {}

    public User(BigInteger p, BigInteger g) {
        this.p = new BigInteger(p.toString());
        this.g = new BigInteger(g.toString());
        BigInteger n = p.subtract(new BigInteger("2"));
        do {
            randomNumber = BigInteger.probablePrime(p.bitLength(), new SecureRandom());
        } while (randomNumber.compareTo(n) >= 0);
        Zi = quickPower(g, randomNumber, p);
    }

    public BigInteger getP() {
        return p;
    }

    public void setP(BigInteger p) {
        this.p = p;
    }

    public BigInteger getG() {
        return g;
    }

    public void setG(BigInteger g) {
        this.g = g;
    }

    public BigInteger getRandomNumber() {
        return randomNumber;
    }

    public void setRandomNumber(BigInteger randomNumber) {
        this.randomNumber = randomNumber;
    }

    public BigInteger getZi() {
        return Zi;
    }

    public void setZi(BigInteger zi) {
        Zi = zi;
    }

    public BigInteger getZNext() {
        return ZNext;
    }

    public void setZNext(BigInteger ZNext) {
        this.ZNext = ZNext;
    }

    public BigInteger getZPre() {
        return ZPre;
    }

    public void setZPre(BigInteger ZPre) {
        this.ZPre = ZPre;
    }

    public BigInteger[] getXi() {
        return Xi;
    }

    public void setXi(BigInteger[] xi) {
        Xi = xi;
    }
}

class Data {
    String p;
    String q;
    String g;
    String message;
    String sumTime;
    String calTime;

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getG() {
        return g;
    }

    public void setG(String g) {
        this.g = g;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSumTime() {
        return sumTime;
    }

    public void setSumTime(String sumTime) {
        this.sumTime = sumTime;
    }

    public String getCalTime() {
        return calTime;
    }

    public void setCalTime(String calTime) {
        this.calTime = calTime;
    }
}