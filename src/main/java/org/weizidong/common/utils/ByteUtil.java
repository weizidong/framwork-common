package org.weizidong.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 基本数据互转工具
 *
 * @author WeiZiDong
 * @date 2018-07-03
 */
public class ByteUtil {

  /**
   * short转成byte数组
   * 2个字节
   */
  public static byte[] getBytes(short data) {
    byte[] bytes = new byte[2];
    bytes[0] = (byte) (data & 0xff);
    bytes[1] = (byte) ((data & 0xff00) >> 8);
    return bytes;
  }

  /**
   * char转成byte数组
   * 2个字节
   */
  public static byte[] getBytes(char data) {
    byte[] bytes = new byte[2];
    bytes[0] = (byte) (data);
    bytes[1] = (byte) (data >> 8);
    return bytes;
  }

  /**
   * int转成byte数组
   * 4个字节
   */
  public static byte[] getBytes(int data) {
    byte[] bytes = new byte[4];
    bytes[0] = (byte) (data & 0xff);
    bytes[1] = (byte) ((data & 0xff00) >> 8);
    bytes[2] = (byte) ((data & 0xff0000) >> 16);
    bytes[3] = (byte) ((data & 0xff000000) >> 24);
    return bytes;
  }

  /**
   * long转成byte数组
   * 8个字节
   */
  public static byte[] getBytes(long data) {
    byte[] bytes = new byte[8];
    bytes[0] = (byte) (data & 0xff);
    bytes[1] = (byte) ((data >> 8) & 0xff);
    bytes[2] = (byte) ((data >> 16) & 0xff);
    bytes[3] = (byte) ((data >> 24) & 0xff);
    bytes[4] = (byte) ((data >> 32) & 0xff);
    bytes[5] = (byte) ((data >> 40) & 0xff);
    bytes[6] = (byte) ((data >> 48) & 0xff);
    bytes[7] = (byte) ((data >> 56) & 0xff);
    return bytes;
  }

  /**
   * float转成byte数组
   * 4个字节
   */
  public static byte[] getBytes(float data) {
    int intBits = Float.floatToIntBits(data);
    return getBytes(intBits);
  }

  /**
   * double转成byte数组
   * 8个字节
   */
  public static byte[] getBytes(double data) {
    long intBits = Double.doubleToLongBits(data);
    return getBytes(intBits);
  }

  /**
   * 字符串转成byte数组
   */
  public static byte[] getBytes(String data, String charsetName) {
    Charset charset = Charset.forName(charsetName);
    return data.getBytes(charset);
  }

  /**
   * 字符串转成byte数组，默认UTF-8编码
   */
  public static byte[] getBytes(String data) {
    return getBytes(data, "UTF-8");
  }

  /**
   * 转成short
   * 2个字节
   */
  public static short getShort(byte[] bytes) {
    return (short) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
  }

  /**
   * 转成char
   * 2个字节
   */
  public static char getChar(byte[] bytes) {
    return (char) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
  }

  /**
   * 转成int
   * 4个字节
   */
  public static int getInt(byte[] bytes) {
    return (0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)) | (0xff0000 & (bytes[2] << 16)) | (0xff000000 & (bytes[3] << 24));
  }

  /**
   * 转成long
   * 8个字节
   */
  public static long getLong(byte[] bytes) {
    return (0xffL & (long) bytes[0]) | (0xff00L & ((long) bytes[1] << 8)) | (0xff0000L & ((long) bytes[2] << 16)) | (0xff000000L & ((long) bytes[3] << 24))
            | (0xff00000000L & ((long) bytes[4] << 32)) | (0xff0000000000L & ((long) bytes[5] << 40)) | (0xff000000000000L & ((long) bytes[6] << 48)) | (0xff00000000000000L & ((long) bytes[7] << 56));
  }

  /**
   * 转成Float
   * 4个字节
   */
  public static float getFloat(byte[] bytes) {
    return Float.intBitsToFloat(getInt(bytes));
  }

  /**
   * 转成Double
   * 8个字节
   */
  public static double getDouble(byte[] bytes) {
    long l = getLong(bytes);
    return Double.longBitsToDouble(l);
  }

  /**
   * 转成字符串
   */
  public static String getString(byte[] bytes, String charsetName) {
    return new String(bytes, Charset.forName(charsetName));
  }

  /**
   * 转成字符串，默认UTF-8编码
   */
  public static String getString(byte[] bytes) {
    return getString(bytes, "UTF-8");
  }

  /**
   * 流转换为byte[]
   * 未关闭输入流，外部手动关闭
   */
  public static byte[] readBytes(InputStream in) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    byte[] buffer = new byte[2048];
    int len = in.read(buffer);
    if (len <= 0) {
      return null;
    }
    out.write(buffer, 0, len);
    return out.toByteArray();
  }

  /**
   * byte List转成byte数组
   */
  public static byte[] getBytes(List<byte[]> bytes) {
    int i = 0;
    for (byte[] by : bytes) {
      i += by.length;
    }
    byte[] b = new byte[i];
    int len = 0;
    for (byte[] by : bytes) {
      System.arraycopy(by, 0, b, len, by.length);
      len += by.length;
    }
    return b;
  }

  /**
   * 时间转字节数组
   */
  public static byte[] timeToByte(Date time) {
    Calendar c = Calendar.getInstance();
    c.setTime(time);
    byte[] b = new byte[11];
    // 年 -- 2字节
    System.arraycopy(ByteUtil.getBytes((short) c.get(Calendar.YEAR)), 0, b, 0, 2);
    // 月 -- 1字节
    System.arraycopy(ByteUtil.getBytes((byte) c.get(Calendar.MONTH) + 1), 0, b, 2, 1);
    // 日 -- 1字节
    System.arraycopy(ByteUtil.getBytes((byte) c.get(Calendar.DAY_OF_MONTH)), 0, b, 3, 1);
    // 时 -- 1字节
    System.arraycopy(ByteUtil.getBytes((byte) c.get(Calendar.HOUR_OF_DAY)), 0, b, 4, 1);
    // 分 -- 1字节
    System.arraycopy(ByteUtil.getBytes((byte) c.get(Calendar.MINUTE)), 0, b, 5, 1);
    // 秒 -- 1字节
    System.arraycopy(ByteUtil.getBytes((byte) c.get(Calendar.SECOND)), 0, b, 6, 1);
    // 微妙 -- 4字节
    System.arraycopy(ByteUtil.getBytes(c.get(Calendar.MILLISECOND)), 0, b, 7, 4);
    return b;
  }

  /**
   * 字节数组转时间
   */
  public static Date byteToTime(byte[] b) {
    Calendar c = Calendar.getInstance();
    // 年 -- 2字节
    c.set(Calendar.YEAR, ByteUtil.getShort(new byte[]{b[0], b[1]}));
    // 月 -- 1字节
    c.set(Calendar.MONTH, b[2] - 1);
    // 日 -- 1字节
    c.set(Calendar.DATE, b[3]);
    // 时 -- 1字节
    c.set(Calendar.HOUR_OF_DAY, b[4]);
    // 分 -- 1字节
    c.set(Calendar.MINUTE, b[5]);
    // 秒 -- 1字节
    c.set(Calendar.SECOND, b[6]);
    // 微妙 -- 4字节
//    c.set(Calendar.MILLISECOND, ByteUtil.getInt(new byte[]{b[7], b[8], b[9], b[10]}));
    c.set(Calendar.MILLISECOND, 0);
    return c.getTime();
  }
}
