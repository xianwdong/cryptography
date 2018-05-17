package newprotocol;

import java.math.BigInteger;
import java.security.SecureRandom;

import static common.Util.getOriginalRoot;
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
        // System.out.println("加密前的数据: " + user1.getData());
        // user1先获取user2的公钥
        BigInteger publicKey = user2.getPublicKey();
        // user1用user2的公钥对数据进行加密
        BigInteger X = user1.getX();
        BigInteger index = user1.getA().multiply(X).multiply(X);
        BigInteger data = publicKey.modPow(index, p);
        //quickPower(publicKey, user1.getA().multiply(user1.getX()).multiply(user1.getX()), p);
        // user2接收数据之后，用私钥对数据进行解密
        BigInteger power = user2.getX().modInverse(q);
        BigInteger originalData = quickPower(data, power, p);
        user2.addData(originalData);
        // System.out.println("解密后的数据: " + originalData);
    }

    public static Data dataForUI(int n) {
        Data data = new Data();
        int length = 256;
        BigInteger q, p;
        while (true) {
            q = BigInteger.probablePrime(length, new SecureRandom());
            p = q.multiply(new BigInteger("2")).add(new BigInteger("1"));
            if (p.isProbablePrime(100)) {
                break;
            }
        }
        data.setP(p.toString());
        data.setQ(q.toString());
        BigInteger g = getOriginalRoot(p, q);
        data.setG(g.toString());
        User[] users = new User[n];
        for (int i = 0; i < users.length; ++i) {
            users[i] = new User(p, g, q);
        }
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
        data.setMessage(message);
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
            BigInteger g = getOriginalRoot(p, q);
            User[] users = new User[5];
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
                 //System.out.println("第" + (i + 1) + "个用户: " + users[i].getCommonKey());
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

    public static void main1(String[] args) {

        int correct = 0, wrong = 0;
        for (int times = 0; times < 10; ++times) {
            int length = 256;
            BigInteger q, p;
            while (true) {
                q = BigInteger.probablePrime(length, new SecureRandom());
                p = q.multiply(new BigInteger("2")).add(new BigInteger("1"));
                if (p.isProbablePrime(100)) {
                    break;
                }
            }
            // System.out.println("p : " + p);
            // System.out.println("q : " + q);
            BigInteger g = getOriginalRoot(p, q);
            // System.out.println("g : " + g);
            User[] users = new User[5];
            for (int i = 0; i < users.length; ++i) {
                users[i] = new User(p, g, q);
            }
            for (int i = 0; i < users.length; ++i) {
                for (int j = 0; j < users.length; ++j) {
                    if (i != j) {
                        sendData(users[i], users[j]);
                    //System.out.println(data);
                    }
                }
                //System.out.println("----------------");
            }
            int i = 0;
            for ( ; i < users.length - 1; ++i) {
                // System.out.println("第" + (i + 1) + "个用户: " + users[i].getCommonKey());
                if (!users[i].getCommonKey().equals(users[i + 1].getCommonKey())) {
                    ++wrong;
                    break;
                }
            }
            if (i == users.length - 1) {
                ++correct;
            }
        }
        System.out.println("correct : " + correct);
        System.out.println("wrong : " + wrong);
    }


    public void f(String[] args) {


        BigInteger q = new BigInteger("761");
                //"201593568205072105604726827991920790741");

        BigInteger p = new BigInteger("1523");
                //"403187136410144211209453655983841581483");

        BigInteger g = new BigInteger("3");

        BigInteger a, x1, x2;
        BigInteger n = q.subtract(new BigInteger("2"));
        do {
            a = new BigInteger(q.bitLength(), new SecureRandom());
        } while (a.compareTo(n) >= 0);
        do {
            x1 = new BigInteger(q.bitLength(), new SecureRandom());
        } while (x1.compareTo(n) >= 0);
        do {
            x2 = new BigInteger(q.bitLength(), new SecureRandom());
        } while (x2.compareTo(n) >= 0);
        BigInteger pk =g.modPow(x2,p);  //quickPower(g, x2, p);
        BigInteger data1 =g.modPow(a.multiply(x1).multiply(x1), p) ;  //quickPower(g, a.multiply(x1).multiply(x1), p);
        BigInteger data2 = quickPower(pk, a.multiply(x1).multiply(x1), p);

        BigInteger mi = x2.modInverse(q);

        //System.out.println("mi="+mi);
        BigInteger data3 = data2.modPow(mi, p); //  quickPower(pk, a.multiply(x1).multiply(x1).multiply(mi), p);
        System.out.println(data1);
        System.out.println(data3);
        //System.out.println(g.modPow(a.multiply(x1).multiply(x1).multiply(mi).multiply(x2),p));
        /*while (true) {
            BigInteger q = BigInteger.probablePrime(128, new SecureRandom());
            BigInteger p = q.multiply(new BigInteger("2")).add(new BigInteger("1"));
            if (p.isProbablePrime(100)) {
                System.out.println(q);

                System.out.println(p);
                break;
            }
        }*/


        /*int length = 512;
        BigInteger p = BigInteger.probablePrime(length, new SecureRandom());
        BigInteger g = getOriginalRoot(p, length);
        User4[] users = new User4[5];
        for (int i = 0; i < users.length; ++i) {
            users[i] = new User4(p, g);
        }
        for (int i = 0; i < users.length; ++i) {
            BigInteger publicKey = users[i].getPublicKey();
            for (int j = 0; j < users.length; ++j) {
                users[j].getPublicKeyList().add(publicKey);
            }
        }
        for (int i = 0; i < users.length; ++i) {
            System.out.println("第" + (i + 1) + "个用户: " + users[i].getCommonKey());
        }*/
    }

}

class Data {
    String p;
    String q;
    String g;
    String message;

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
}