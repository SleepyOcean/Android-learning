package com.sleepy.box.util;

import android.util.Base64;

import org.apache.commons.lang3.StringUtils;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;


/**
 * Created by gehou on 2017/9/23.
 */

public class EncryptUtil {

    private final byte[] DESIV = new byte[] { 0x12, 0x34, 0x56, 120, (byte) 0x90, (byte) 0xab, (byte) 0xcd, (byte) 0xef };// 向量

    private AlgorithmParameterSpec iv = null;// 加密算法的参数接口
    private Key key = null;

    private String charset = "utf-8";

    /**
     * 初始化
     * @param deSkey    密钥
     * @throws Exception
     */
    public EncryptUtil(String deSkey, String charset){

        try{
            if (StringUtils.isNotBlank(charset)) {
                this.charset = charset;
            }
            DESKeySpec keySpec = new DESKeySpec(deSkey.getBytes(this.charset));// 设置密钥参数
            iv = new IvParameterSpec(DESIV);// 设置向量
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");// 获得密钥工厂
            key = keyFactory.generateSecret(keySpec);// 得到密钥对象
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 加密
     * @author ershuai
     * @date 2017年4月19日 上午9:40:53
     * @param data
     * @return
     * @throws Exception
     */
    public String encode(String data){
        try {
            Cipher enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");// 得到加密对象Cipher
            enCipher.init(Cipher.ENCRYPT_MODE, key, iv);// 设置工作模式为加密模式，给出密钥和向量
            byte[] pasByte = enCipher.doFinal(data.getBytes("utf-8"));
            return Base64.encodeToString(pasByte,Base64.DEFAULT);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * @author ershuai
     * @date 2017年4月19日 上午9:41:01
     * @param data
     * @return
     * @throws Exception
     */
    public String decode(String data){
        try {
            Cipher deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            deCipher.init(Cipher.DECRYPT_MODE, key, iv);

            byte b[]=android.util.Base64.decode(data,Base64.DEFAULT);

            return new String(b, "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
       return null;
    }
}
