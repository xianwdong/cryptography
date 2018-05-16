package newprotocol;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.LinkedList;

import static common.Util.quickPower;

/**
 * @author dxw
 * @date 2018/5/6
 */
public class User {

    // public值
    private BigInteger p;
    private BigInteger g;

    // q 用q来生成p p = 2q+1
    private BigInteger q;

    // 随机值
    private BigInteger a;
    // 私钥
    private BigInteger x;
    // 公钥
    private BigInteger publicKey;
    // 发送给其他用户的数据
    private BigInteger data;

    // 存储所有的公钥
    private LinkedList<BigInteger> dataList = new LinkedList<>();

    public User() {}

    public User(BigInteger p, BigInteger g, BigInteger q) {
        this.p = new BigInteger(p.toString());
        this.g = new BigInteger(g.toString());
        this.q = new BigInteger(q.toString());
        BigInteger n = q.subtract(new BigInteger("2"));
        do {
            a = new BigInteger(q.bitLength(), new SecureRandom());
        } while (a.compareTo(n) >= 0);
        do {
            x = new BigInteger(q.bitLength(), new SecureRandom());
        } while (x.compareTo(n) >= 0);
        data = g.modPow(a.multiply(x).multiply(x), p);
        publicKey =  g.modPow(x, p); //quickPower(g, x, p);
    }

    public BigInteger getPublicKey() {
        return publicKey;
    }

    public BigInteger getCommonKey() {
        BigInteger result = new BigInteger("1");
        for (BigInteger number : dataList) {
            result = result.multiply(number).mod(p);
        }
        // return result;
        return result.multiply(data).mod(p);
    }

    public void addData(BigInteger data) {
        dataList.add(data);
    }

    public LinkedList<BigInteger> getDataList() {
        return dataList;
    }

    public BigInteger getA() {
        return a;
    }

    public BigInteger getX() {
        return x;
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getQ() {
        return q;
    }

    public BigInteger getG() {
        return g;
    }

    public BigInteger getData() {
        return data;
    }
}
