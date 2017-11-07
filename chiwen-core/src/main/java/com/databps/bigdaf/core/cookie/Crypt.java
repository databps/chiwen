package com.databps.bigdaf.core.cookie;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Crypt {
    private static String Algorithm = "DES"; // 定义 加密算法,可用 DES,DESede,Blowfish
    static boolean debug = false;
    // 生成密钥, 注意此步骤时间比较长
    public static byte[] getKey() throws Exception {
        KeyGenerator keygen = KeyGenerator.getInstance(Algorithm);
        SecretKey deskey = keygen.generateKey();
        if (debug)
            System.out.println("生成密钥:" + byte2hex(deskey.getEncoded()));
        return deskey.getEncoded();
    }
    // 加密
    public static String encode(String input, String key) throws Exception {
        return byte2hex(encode(input.getBytes(), key.getBytes()));
    }
    // 加密
    public static byte[] encode(byte[] input, byte[] key) throws Exception {
        SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
        if (debug) {
            System.out.println("加密前的二进串:" + byte2hex(input));
            System.out.println("加密前的字符串:" + new String(input));
        }
        Cipher c1 = Cipher.getInstance(Algorithm);
        c1.init(Cipher.ENCRYPT_MODE, deskey);
        byte[] cipherByte = c1.doFinal(input);
        if (debug)
            System.out.println("加密后的二进串:" + byte2hex(cipherByte));
        return cipherByte;
    }
    // 解密
    public static String decode(String input, String key) throws Exception {
        return new String(decode(hex2byte(input), key.getBytes()));
    }
    // 解密
    public static byte[] decode(byte[] input, byte[] key) throws Exception {
        SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
        if (debug)
            System.out.println("解密前的信息:" + byte2hex(input));
        Cipher c1 = Cipher.getInstance(Algorithm);
        c1.init(Cipher.DECRYPT_MODE, deskey);
        byte[] clearByte = c1.doFinal(input);
        if (debug) {
            System.out.println("解密后的二进串:" + byte2hex(clearByte));
            System.out.println("解密后的字符串:" + (new String(clearByte)));
        }
        return clearByte;
    }
    // md5()信息摘要, 不可逆
    public static byte[] md5(byte[] input) throws Exception {
        java.security.MessageDigest alg = java.security.MessageDigest.getInstance("MD5"); // or "SHA-1"
        if (debug) {
            System.out.println("摘要前的二进串:" + byte2hex(input));
            System.out.println("摘要前的字符串:" + new String(input));
        }
        alg.update(input);
        byte[] digest = alg.digest();
        if (debug)
            System.out.println("摘要后的二进串:" + byte2hex(digest));
        return digest;
    }
    // 字节码转换成16进制字符串
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            // if (n < b.length - 1)
            // hs = hs + ":";
        }
        // System.out.println("hs="+hs);
        return hs.toUpperCase();
    }
    // 将16进制字符串转换成字节码
    public static byte[] hex2byte(String hex) {
        byte[] bts = new byte[hex.length() / 2];
        for (int i = 0; i < bts.length; i++) {
            bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bts;
    }
   /* public static void main(String[] args) throws Exception {
        //debug = true;
        // byte[] key = getKey();
        //byte[] key = "好好学习".getBytes();
        //System.out.println(key.length);
        // decode(encode("", "simonesi"), "simonesi");
        //decode("664A1D0200F0F62C2716EABF808EFFA7A018EED2306A5906021D6E1C02BFB95486B005B14A510816", "QYDWTSGT");
        // md5("测试加密".getBytes());
        //md5("A".getBytes());
        String str="401744CACFF25DE98472AD121594907C";
        System.out.println(Crypt.decode(str, "CHAOXING"));
    }*/
}