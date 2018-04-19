import com.sun.org.apache.bcel.internal.generic.BIPUSH;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

/**
 * @author dxw
 * @date 2018/4/19
 */
public class Testoo {

    public static String requestModInverseByEEA(String ai, String bi) {
        BigInteger a = new BigInteger(ai);
        BigInteger b = new BigInteger(bi);
        boolean isSmall = false;
        if (a.compareTo(b) < 0) {
            System.out.println("Warning:" + a.toString() + "<" + b.toString() + ",exchange them");
            BigInteger temp = a;
            a = b;
            b = temp;
            isSmall = true;
        }
        BigInteger simm = new BigInteger("1");
        BigInteger timm = new BigInteger("0");
        BigInteger sim = new BigInteger("0");
        BigInteger tim = new BigInteger("1");
        BigInteger rim = b, ri = a.remainder(b), qim = a.divide(b), qi, temp;
        while (ri.toString() != "0") {
            qi = rim.divide(ri);
            temp = sim;
            sim = simm.subtract(qim.multiply(sim));
            simm = temp;
            temp = tim;
            tim = timm.subtract(qim.multiply(tim));
            timm = temp;
            qim = qi;
            temp = ri;
            ri = rim.remainder(ri);
            rim = temp;
        }
        if (isSmall == true) {
            return tim.toString();
        } else {
            return sim.toString();
        }
    }

    public static String requestModInverse(String a, String b) {
        BigInteger ai = new BigInteger(a);
        BigInteger bi = new BigInteger(b);
        return ai.modInverse(bi).toString();
    }

    public static void main(String args[]) throws Exception {
        System.out.println(0 % 5);
    }

}
