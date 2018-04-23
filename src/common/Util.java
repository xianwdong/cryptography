package common;

import java.math.BigInteger;

/**
 * @author dxw
 * @date 2018/4/23
 */
public final class Util {

    // cal a ^ b mod c
    public static BigInteger quickPower(BigInteger a, BigInteger b, BigInteger c) {
        BigInteger result = new BigInteger("1");
        a = a.mod(c);
        while (b.compareTo(new BigInteger("0")) == 1) {
            if (b.mod(new BigInteger("2")).equals(new BigInteger("1"))) {
                result = result.multiply(a).mod(c);
            }
            b = b.divide(new BigInteger("2"));
            a = a.multiply(a).mod(c);
        }
        return result;
    }

    public static BigInteger getOriginalRoot(BigInteger primer, int length) {
        /*BigInteger p = BigInteger.ONE;
        for (long j = 0; j < 200000; j++) {
            BigInteger q = BigInteger.probablePrime(length, new SecureRandom());
            BigInteger pp = BigInteger.valueOf(2).multiply(q).add(BigInteger.valueOf(1));
            if (pp.isProbablePrime(1)) {
                p = pp;
                for (int i = 2; i < 5000; i++) {
                    BigInteger g = new BigInteger(String.valueOf(i));
                    if (g.modPow(BigInteger.valueOf(2), pp).intValue() == 1 ||
                            g.modPow(q, pp).intValue() == 1) {
                        continue;
                    } else {
                        return g;
                    }
                }
                break;
            } else {
                continue;
            }
        }*/
        return new BigInteger("2");
    }

}
