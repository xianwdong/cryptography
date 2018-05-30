package newprotocol;

import java.math.BigInteger;
import java.security.SecureRandom;

import static common.Util.getOriginalRoot;
import static common.Util.getOriginalRoot2;
import static common.Util.quickPower;

/**
 * @author dxw
 * @date 2018/5/9
 */
public class Main {

    /*
     * user1为发送者，user2为接收者
     * */
    public static void sendData(User user1, User user2) {
        BigInteger p = user1.getP();
        BigInteger q = user1.getQ();
        // user1先获取user2的公钥
        BigInteger publicKey = user2.getPublicKey();
        // user1用user2的公钥对数据进行加密
        BigInteger X = user1.getX();
        BigInteger index = user1.getA().multiply(X).multiply(X);
        BigInteger data = publicKey.modPow(index, p);
        // user2接收数据之后，用私钥对数据进行解密
        BigInteger power = user2.getX().modInverse(q);
        BigInteger originalData = quickPower(data, power, p);
        user2.addData(originalData);
    }

    public static Data dataForUI(int n) {
        Data data = new Data();
        int length = 256;
        BigInteger q, p;
        long t1 = System.currentTimeMillis();
        while (true) {
            q = BigInteger.probablePrime(length, new SecureRandom());
            p = q.multiply(new BigInteger("2")).add(new BigInteger("1"));
            if (p.isProbablePrime(100)) {
                break;
            }
        }
        data.setP(p.toString());
        data.setQ(q.toString());
        BigInteger g = getOriginalRoot2(p, q);
        data.setG(g.toString());
        User[] users = new User[n];
        for (int i = 0; i < users.length; ++i) {
            users[i] = new User(p, g, q);
        }
        long t2 = System.currentTimeMillis();
        for (int i = 0; i < users.length; ++i) {
            for (int j = 0; j < users.length; ++j) {
                if (i != j) {
                    sendData(users[i], users[j]);
                }
            }
        }
        String message = "";
        for (int i = 0; i < users.length; ++i) {
            message += "第" + (i + 1) + "个用户: " + users[i].getCommonKey() + "\n";
        }
        long t3 = System.currentTimeMillis();

        data.setMessage(message);
        data.setSumTime(String.valueOf(t3 - t1));
        data.setCalTime(String.valueOf(t3 - t2));
        return data;
    }

    public static void main(String[] args) {
        long sum1 = 0, sum2 = 0;
        for (int times = 0; times < 50; ++times) {
            long t1 = System.currentTimeMillis();
            //System.out.println("程序开始运行时间t1: " + t1);
            int length = 256;
            BigInteger q, p;
            while (true) {
                q = BigInteger.probablePrime(length, new SecureRandom());
                p = q.multiply(new BigInteger("2")).add(new BigInteger("1"));
                if (p.isProbablePrime(100)) {
                    break;
                }
            }
            BigInteger g = getOriginalRoot2(p, q);
            User[] users = new User[10];
            for (int i = 0; i < users.length; ++i) {
                users[i] = new User(p, g, q);
            }
            long t2 = System.currentTimeMillis();
            //System.out.println("密钥协商计算开始时间t2: " + t2);
            for (int i = 0; i < users.length; ++i) {
                for (int j = 0; j < users.length; ++j) {
                    if (i != j) {
                        sendData(users[i], users[j]);
                    }
                }
            }

            for (int i = 0; i < users.length; ++i) {
                users[i].getCommonKey();
            }
            long t3 = System.currentTimeMillis();
            /*System.out.println("程序结束运行时间t3: " + t3);
            System.out.println("程序运行总时间t3 - t1: " + (t3 - t1));
            System.out.println("密钥协商计算总时间t3 - t2: " + (t3 - t2));*/
            sum1 += t3 - t1;
            sum2 += t3 - t2;
        }
        System.out.println(sum1);
        System.out.println(sum2);

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