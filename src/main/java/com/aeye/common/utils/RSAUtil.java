package com.aeye.common.utils;

import javax.crypto.Cipher;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * SM2签名算法：SM3withSM2
 * SM2曲线规则：sm2p256v1
 * SM4加解密算法：SM4/ECB/PKCS7Padding
 * RSA加解密算法：RSA/ECB/PKCS1Padding
 * AES加解密算法：AES/ECB/PKCS5Padding
 * @author shenxingping
 * @date 2021/09/09
 */
public class RSAUtil {

    /**
     * RSA密钥生成算法
     */
    public static final String RSA_ALGORITHM_NAME = "RSA";
    /**
     * RSA加解密算法
     */
    public static final String RSA_ALGORITHM_NAME_ECB_PADDING = "RSA/ECB/PKCS1Padding";


    public static byte[] encryptRsa(byte[] pubKey, byte[] message) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM_NAME_ECB_PADDING);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM_NAME);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(message);
    }

    public static byte[] decryptRsa(byte[] priKey, byte[] input) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM_NAME_ECB_PADDING);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(priKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM_NAME);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(input);
    }
}
