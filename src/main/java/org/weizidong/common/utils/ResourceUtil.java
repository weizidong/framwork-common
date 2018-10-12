package org.weizidong.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;

/**
 * 资源工具类
 *
 * @author WeiZiDong
 */
public class ResourceUtil {
  private ResourceUtil() {
  }

  /**
   * 类加载器
   */
  private static ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

  /**
   * 获取实体类源代码的物理路径
   *
   * @param clazz 目标.class
   * @return URI路径
   */
  public static URL getCodeSourceURL(Class clazz) {
    return clazz.getProtectionDomain().getCodeSource().getLocation();
  }

  /**
   * 获取资源输入流
   *
   * @param path 路径
   * @return 资源输入流
   */
  public static InputStream getResourceAsStream(String path) throws IOException {
    // 返回读取指定资源的输入流
    return getResourceAsStream(path, getClassLoaders(null));
  }

  /**
   * 获取资源输入流
   *
   * @param resource    资源相对路径
   * @param classLoader 类加载器
   * @return 资源输入流
   */
  public static InputStream getResourceAsStream(String resource, ClassLoader[] classLoader) throws IOException {
    for (ClassLoader cl : classLoader) {
      if (null != cl) {
        InputStream returnValue = cl.getResourceAsStream(resource);
        // 一些类加载器需要分隔符（File.separator）作为前缀，所以如果没有找到资源，我们将添加它并重试。
        if (null == returnValue) {
          returnValue = cl.getResourceAsStream(File.separator + resource);
        }
        if (null != returnValue) {
          return returnValue;
        }
      }
    }
    throw new IOException(MessageFormat.format("资源“{0}”不存在！", resource));
  }

  /**
   * 获取资源URI路径
   *
   * @param path 资源相对路径
   * @return 资源URL
   */
  public static URL getResource(String path) throws IOException {
    // 返回读取指定资源的输入流
    return getResource(path, getClassLoaders(null));
  }

  /**
   * 获取资源URI路径
   *
   * @param resource    资源相对路径
   * @param classLoader 类加载器
   * @return 资源URL
   */
  public static URL getResource(String resource, ClassLoader[] classLoader) throws IOException {
    for (ClassLoader cl : classLoader) {
      if (null != cl) {
        URL returnValue = cl.getResource(resource);
        // 一些类加载器需要分隔符（File.separator）作为前缀，所以如果没有找到资源，我们将添加它并重试。
        if (null == returnValue) {
          returnValue = cl.getResource(File.separator + resource);
        }
        if (null != returnValue) {
          return returnValue;
        }
      }
    }
    throw new IOException(MessageFormat.format("资源“{0}”不存在！", resource));
  }

  /**
   * 获取类加载器
   *
   * @param classLoader 自定义类加载器
   * @return 类加载器
   */
  public static ClassLoader[] getClassLoaders(ClassLoader classLoader) {
    return new ClassLoader[]{classLoader, Thread.currentThread().getContextClassLoader(), ResourceUtil.class.getClassLoader(), systemClassLoader};
  }

}
