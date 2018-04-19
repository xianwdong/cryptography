import sun.misc.BASE64Encoder;

import java.math.BigInteger;
import java.security.*;

/**
 * @author dxw
 * @date 2018/4/17
 */
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

    public static void exchange(User2 userA, User2 userB) throws Exception {
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
        Certificate certificate1 = new Certificate();
        certificate1.setId(new String(userA.getCertificate().getId()));
        certificate1.setB(new BigInteger(userA.getCertificate().getB().toString()));
        certificate1.setS(new BigInteger(userA.getCertificate().getS().toString()));
        certificate1.setSig(new String(userA.getCertificate().getSig()));
        userB.setCertificateFromOther(certificate1);


        boolean result1 = RSASignature.doCheck(getMd5Code(userB.getCertificateFromOther().getId() +
                        userB.getCertificateFromOther().getB() + userB.getCertificateFromOther().getS()),
                        userB.getCertificateFromOther().getSig(), publicString, "utf-8");
        /*if (result1 == false) {
            throw new Exception("B验证A的证书失败");
        }*/
        System.out.println(result1);

        // 对B进行签名
        String contentOfB = getMd5Code(userB.getCertificate().getId() + userB.getCertificate().getB() + userB.getCertificate().getS());
        String sigOfB = RSASignature.sign(contentOfB, privateString, "utf-8");
        userB.getCertificate().setSig(sigOfB);

        // 设置A的CertificateFromOther
        Certificate certificate2 = new Certificate();
        certificate2.setId(userB.getCertificate().getId());
        certificate2.setB(new BigInteger(userB.getCertificate().getB().toString()));
        certificate2.setS(new BigInteger(userB.getCertificate().getS().toString()));
        certificate2.setSig(sigOfB);
        userA.setCertificateFromOther(certificate2);

        boolean result2 = RSASignature.doCheck(getMd5Code(userA.getCertificateFromOther().getId() +
                        userA.getCertificateFromOther().getB() + userA.getCertificateFromOther().getS()),
                userA.getCertificateFromOther().getSig(), publicString, "utf-8");
        /*if (result2 == false) {
            throw new Exception("A验证B的证书失败");
        }*/
        System.out.println(result2);

        // 计算KA
        BigInteger Sb = userA.getCertificateFromOther().getS();
        BigInteger bb = userA.getCertificateFromOther().getB();
        BigInteger KA = DH.quickPower(Sb, userA.getA(), userA.getP())
                .multiply(DH.quickPower(userA.getCertificateFromOther().getB(), userA.getRandomNumber(), userA.getP())).mod(userA.getP());
        System.out.println(KA.toString());

        // 计算KB
        BigInteger Sa = userB.getCertificateFromOther().getS();
        BigInteger ba = userB.getCertificateFromOther().getB();
        BigInteger Kb = DH.quickPower(Sa, userB.getA(), userB.getP())
                .multiply(DH.quickPower(userB.getCertificateFromOther().getB(), userB.getRandomNumber(), userB.getP())).mod(userB.getP());
        System.out.println(Kb.toString());
    }

    public static void main(String[] args) throws Exception {
        long t1 = System.currentTimeMillis();
        int length = 512;
        BigInteger p = BigInteger.probablePrime(length, new SecureRandom());
        BigInteger g = DH.getOriginalRoot(p, length);
        User2 userA = new User2(p, g);
        User2 userB = new User2(p, g);
        exchange(userA, userB);
        long t2 = System.currentTimeMillis();
        System.out.println(t2 - t1);
    }

}
