package BD;

import java.math.BigInteger;
import java.security.SecureRandom;

import static common.Util.getOriginalRoot;
import static common.Util.quickPower;

public class BD {

    public static void broadcastZ(User[] users) {
        int n = users.length;
        for (int i = 0; i < users.length; ++i) {
            users[(i + 1) % n].setZPre(users[i].getZi());
            users[(i - 1 + n) % n].setZNext(users[i].getZi());
        }
    }

    public static void broadcastX(User[] users, BigInteger p) {
        int n = users.length;
        BigInteger[] XArr = new BigInteger[n];
        for (int i = 0; i < n; ++i) {
            BigInteger temp = users[i].getZPre().modInverse(p);
            XArr[i] = quickPower(users[i].getZNext().multiply(temp).mod(p), users[i].getRandomNumber(), p);
        }
        for (int i = 0; i < n; ++i) {
            users[i].setXi(XArr);
        }
    }

    public static BigInteger getK(User[] users, BigInteger p) {
        int n = users.length;
        BigInteger[] XArr = users[0].getXi();
        BigInteger result = new BigInteger("1");

        for (int j = 0; j < n; ++j) {
            result = new BigInteger("1");
            int i;
            for (i = 1; i <= n - 1; ++i) {
                result = result.multiply(XArr[(i + j - 1) % n].pow(n - i)).mod(p);
            }
            result = result.multiply(quickPower(users[j].getZPre().pow(n), users[j].getRandomNumber(), p)).mod(p);
            //System.out.println("第" + (j + 1) + "个用户计算出的密钥: " + result);
        }

        return result;
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
        BigInteger g = getOriginalRoot(p, q);
        data.setG(g.toString());
        data.setP(p.toString());
        data.setQ(q.toString());
        User[] users = new User[n];
        for (int i = 0; i < n; ++i) {
            users[i] = new User(p, g);
        }
        long t2 = System.currentTimeMillis();
        broadcastZ(users);
        broadcastX(users, p);
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
            result = result.multiply(quickPower(g, rr[i], p)).mod(p);
        }
        long t3 = System.currentTimeMillis();
        data.setSumTime(String.valueOf(t3 - t1));
        data.setCalTime(String.valueOf(t3 - t2));

        BigInteger[] XArr = users[0].getXi();
        String message = "";
        for (int j = 0; j < n; ++j) {
            result = new BigInteger("1");
            int i;
            for (i = 1; i <= n - 1; ++i) {
                result = result.multiply(XArr[(i + j - 1) % n].pow(n - i)).mod(p);
            }
            result = result.multiply(quickPower(users[j].getZPre().pow(n), users[j].getRandomNumber(), p)).mod(p);
            message += "第" + (j + 1) + "个用户计算出的密钥: " + result + "\n";
        }
        data.setMessage(message);

        return data;
    }

    public static void main(String[] args) {
        //long sum1 = 0, sum2 = 0;
        //for (int times = 0; times < 50; ++times) {
            long t1 = System.currentTimeMillis();
            int n = 5;
            int length = 256;
            BigInteger q, p;
            while (true) {
                q = BigInteger.probablePrime(length, new SecureRandom());
                p = q.multiply(new BigInteger("2")).add(new BigInteger("1"));
                if (p.isProbablePrime(100)) {
                    break;
                }
            }

            BigInteger g = getOriginalRoot(p, q);
            System.out.println("p: " + p);
            System.out.println("g: " + g);
            User[] users = new User[n];
            for (int i = 0; i < n; ++i) {
                users[i] = new User(p, g);
            }
            long t2 = System.currentTimeMillis();
            broadcastZ(users);
            broadcastX(users, p);
            getK(users, p);
            long t3 = System.currentTimeMillis();

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
                result = result.multiply(quickPower(g, rr[i], p)).mod(p);
            }
            //sum1 += t3 - t1;
            //sum2 += t3 - t2;
            System.out.println("正确的密钥: " + result);
        System.out.println("程序运行时间: " + (t3 - t1) + "ms");
        System.out.println("密钥协商计算时间: " + (t3 - t2) + "ms");
        //}
        //System.out.println(sum1);
        //System.out.println(sum2);
    }

}
