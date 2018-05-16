package common;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

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

    public static BigInteger getOriginalRoot(BigInteger p, BigInteger q) {
        for (long j = 0; j < 200000; j++) {
            for (int i = 2; i < 5000; i++) {
                BigInteger g = new BigInteger(String.valueOf(i));
                if (g.modPow(BigInteger.valueOf(2), p).intValue() == 1 ||
                        g.modPow(q, p).intValue() == 1) {
                    continue;
                } else {
                    return g.multiply(g);
                }
            }
        }
        return new BigInteger("3");
    }

    public static BigInteger getOriginalRoot(BigInteger p, int length) {
        for (long j = 0; j < 200000; j++) {
            BigInteger q = BigInteger.probablePrime(length, new SecureRandom());
            BigInteger pp = BigInteger.valueOf(2).multiply(q).add(BigInteger.valueOf(1));
            //if (pp.isProbablePrime(1)) {
            for (int i = 2; i < 5000; i++) {
                BigInteger g = new BigInteger(String.valueOf(i));
                if (g.modPow(BigInteger.valueOf(2), p).intValue() == 1 ||
                        g.modPow(q, p).intValue() == 1) {
                    continue;
                } else {
                    return g.multiply(g);
                }
            }
        }
        return new BigInteger("3");
    }

    public BigInteger SecretKeySwapNext() {
        try {
            int bitLength = 512;
            SecureRandom rnd = new SecureRandom();
            for (long j = 0; j < 200000; j++) {
                BigInteger q = BigInteger.probablePrime(bitLength,
                        new SecureRandom()); //System.out.println("Q="+q);
                BigInteger p = BigInteger.valueOf(2).multiply(q).add(
                        BigInteger.valueOf(1)); //System.out.println("P="+p);
                if (p.isProbablePrime(100)) {
                    for (int i = 2; i < 5000; i++) {
                        BigInteger gg = new BigInteger(String.valueOf(i));
                        if (gg.modPow(BigInteger.valueOf(2), p).intValue() == 1 || gg.
                                modPow(q, p).intValue() == 1) {
                            continue;
                        } else {
                            return gg.multiply(gg);
                        }
                    }
                    break;
                } else {
                    continue;
                }
            }
        } finally {
            return new BigInteger("2");
        }
    }

    public static void main(String[] args) {
        BigInteger p = new BigInteger("7");
        BigInteger q = new BigInteger("3");
        for (int i = 1; i < 7; ++i) {
            System.out.println(new BigInteger("2").pow(i).mod(p));
        }
    }
}
