package org.weizidong.common.utils;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES对称加密和解密
 *
 * @author WeiZiDong
 */
public class CryptoUtil {
  private CryptoUtil() {
  }

  public static final String AES = "AES";
  public static final String DES = "DES";

  /**
   * 生成原始对称密钥
   *
   * @param secretKey 密匙
   * @param algorithm 加密方式
   * @return 原始对称密钥的字节数组
   */
  public static byte[] getKey(String secretKey, String algorithm) {
    try {
      // 1.构造密钥生成器，指定为AES算法,不区分大小写
      KeyGenerator keygen = KeyGenerator.getInstance(algorithm);
      // 2.根据ecnodeRules规则初始化密钥生成器
      keygen.init(new SecureRandom(secretKey.getBytes()));
      // 3.产生原始对称密钥
      SecretKey originalKey = keygen.generateKey();
      // 4.获得原始对称密钥的字节数组
      return originalKey.getEncoded();
    } catch (NoSuchAlgorithmException e) {
      LogUtil.error(CryptoUtil.class, e, "生成原始对称秘钥失败！");
    }
    return null;
  }

  /**
   * 加密
   *
   * @param secretKey 密匙
   * @param content   加密内容
   * @param algorithm 加密方式
   * @return 密文
   */
  public static String encode(String secretKey, String content, String algorithm) {
    return encode(StringUtils.equals(algorithm, "DES") ? secretKey.getBytes() : getKey(secretKey, algorithm), content, algorithm);
  }

  /**
   * 加密
   *
   * @param raw       密匙
   * @param content   加密内容
   * @param algorithm 加密方式
   * @return 密文
   */
  public static String encode(byte[] raw, String content, String algorithm) {
    try {
      // 根据字节数组生成AES密钥
      SecretKey key = new SecretKeySpec(raw, algorithm);
      // 根据指定算法AES自成密码器
      Cipher cipher = Cipher.getInstance(algorithm);
      // 初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
      cipher.init(Cipher.ENCRYPT_MODE, key);
      // 获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
      byte[] byteEncode = content.getBytes(StandardCharsets.UTF_8);
      // 根据密码器的初始化方式--加密：将数据加密
      byte[] byteAes = cipher.doFinal(byteEncode);
      // 将加密后的数据转换为字符串
      return new String(Base64.getEncoder().encode(byteAes));
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
      LogUtil.error(CryptoUtil.class, e, "加密失败！");
    }
    // 如果有错就返加null
    return null;
  }

  /**
   * 解密
   *
   * @param raw       密匙
   * @param content   解密内容
   * @param algorithm 解密方式
   * @return 明文
   */
  public static String decode(byte[] raw, String content, String algorithm) {
    try {
      // 根据字节数组生成AES密钥
      SecretKey key = new SecretKeySpec(raw, algorithm);
      // 根据指定算法AES自成密码器
      Cipher cipher = Cipher.getInstance(algorithm);
      // 初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
      cipher.init(Cipher.DECRYPT_MODE, key);
      // 将加密并编码后的内容解码成字节数组
      byte[] byteContent = Base64.getDecoder().decode(content);
      // 解密
      return new String(cipher.doFinal(byteContent), StandardCharsets.UTF_8);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
      LogUtil.error(CryptoUtil.class, e, "解密失败！");
    }
    // 如果有错就返加null
    return null;
  }

  /**
   * 解密
   *
   * @param secretKey 密匙
   * @param content   密文
   * @param algorithm 解密方式
   * @return 明文
   */
  public static String decode(String secretKey, String content, String algorithm) {
    return decode(StringUtils.equals(algorithm, "DES") ? secretKey.getBytes() : getKey(secretKey, algorithm), content, algorithm);
  }

}
