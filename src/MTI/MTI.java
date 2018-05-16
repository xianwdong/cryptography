package MTI;

import base.AES;
import base.RSA;
import sun.misc.BASE64Encoder;

import java.math.BigInteger;
import java.security.*;

import static common.Util.getOriginalRoot;
import static common.Util.quickPower;

public class MTI {

    public static String getMd5Code(String message) {
        String md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md.digest(message.getBytes());
            md5 = AES.parseByte2HexStr(md5Bytes);
            return md5;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void exchange(MTI.User2 userA, MTI.User2 userB) throws Exception {
        KeyPair keyPair = RSA.initKey(512);
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        String privateString = new BASE64Encoder().encode(privateKey.getEncoded());
        String publicString = new BASE64Encoder().encode(publicKey.getEncoded());

        // 对A进行签名
        String contentOfA = getMd5Code(userA.getCertificate().getId() + userA.getCertificate().getB() + userA.getCertificate().getS());
        String sigOfA = RSASignature.sign(contentOfA, privateString, "utf-8");
        userA.getCertificate().setSig(sigOfA);

        // 设置B的CertificateFromOther
        MTI.Certificate certificate1 = new MTI.Certificate();
        certificate1.setId(new String(userA.getCertificate().getId()));
        certificate1.setB(new BigInteger(userA.getCertificate().getB().toString()));
        certificate1.setS(new BigInteger(userA.getCertificate().getS().toString()));
        certificate1.setSig(new String(userA.getCertificate().getSig()));
        userB.setCertificateFromOther(certificate1);

        boolean result1 = RSASignature.doCheck(getMd5Code(userB.getCertificateFromOther().getId() +
                        userB.getCertificateFromOther().getB() + userB.getCertificateFromOther().getS()),
                        userB.getCertificateFromOther().getSig(), publicString, "utf-8");
        if (result1 == false) {
            throw new Exception("B验证A的证书失败");
        }
        System.out.println("B验证A的证书成功");

        // 对B进行签名
        String contentOfB = getMd5Code(userB.getCertificate().getId() + userB.getCertificate().getB() + userB.getCertificate().getS());
        String sigOfB = RSASignature.sign(contentOfB, privateString, "utf-8");
        userB.getCertificate().setSig(sigOfB);

        // 设置A的CertificateFromOther
        MTI.Certificate certificate2 = new MTI.Certificate();
        certificate2.setId(userB.getCertificate().getId());
        certificate2.setB(new BigInteger(userB.getCertificate().getB().toString()));
        certificate2.setS(new BigInteger(userB.getCertificate().getS().toString()));
        certificate2.setSig(sigOfB);
        userA.setCertificateFromOther(certificate2);

        boolean result2 = RSASignature.doCheck(getMd5Code(userA.getCertificateFromOther().getId() +
                        userA.getCertificateFromOther().getB() + userA.getCertificateFromOther().getS()),
                userA.getCertificateFromOther().getSig(), publicString, "utf-8");
        if (result2 == false) {
            throw new Exception("A验证B的证书失败");
        }
        System.out.println("A验证B的证书成功");

        // 计算KA
        BigInteger Sb = userA.getCertificateFromOther().getS();
        BigInteger bb = userA.getCertificateFromOther().getB();
        BigInteger KA = quickPower(Sb, userA.getA(), userA.getP())
                .multiply(quickPower(bb, userA.getRandomNumber(), userA.getP())).mod(userA.getP());
        System.out.println(KA.toString());

        // 计算KB
        BigInteger Sa = userB.getCertificateFromOther().getS();
        BigInteger ba = userB.getCertificateFromOther().getB();
        BigInteger Kb = quickPower(Sa, userB.getA(), userB.getP())
                .multiply(quickPower(ba, userB.getRandomNumber(), userB.getP())).mod(userB.getP());
        System.out.println(Kb.toString());
    }

    public static void main(String[] args) throws Exception {
        int length = 512;
        BigInteger p = BigInteger.probablePrime(length, new SecureRandom());
        BigInteger g = getOriginalRoot(p, length);
        MTI.User2 userA = new MTI.User2(p, g);
        MTI.User2 userB = new MTI.User2(p, g);
        exchange(userA, userB);
    }

}
