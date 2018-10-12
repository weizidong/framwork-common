package org.weizidong.common.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 文件工具类
 *
 * @author WeiZiDong
 */
public class FileUtil {
    private FileUtil() {
    }

    /**
     * 文件存储目录
     */
    public static final String RESOURCE_URL = File.separator + "userfiles" + File.separator;
    /**
     * 项目根路径判定
     */
    public static String BASE_PATH;

    static {
        String basePath = System.getProperty("jetty.home");
        if (basePath == null) {
            BASE_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "webapp";
        } else {
            BASE_PATH = basePath + File.separator + "webapps";
        }
    }

    /**
     * 文件名
     */
    private String name;
    /**
     * 文件相对路径
     */
    private String url;
    /**
     * 文件后缀
     */
    private String suffix;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }


    /**
     * 写文件到硬盘，返回相对路径
     */
    public static FileUtil writeFile(InputStream file, FormDataContentDisposition disposition) {
        try {
            // 文件全称
            String fullName = new String(disposition.getFileName().getBytes("ISO8859-1"), "UTF-8");
            // 文件名UUID生成
            String fileName = MathUtil.getUUID();
            // 后缀
            String ext = fullName.substring(fullName.lastIndexOf("."), fullName.length());
            // 相对路径
            String folder = RESOURCE_URL + DateUtil.format(new Date(), DateUtil.P_DATE_2) + File.separator;
            // 绝对路径
            String path = BASE_PATH + folder + fileName + ext;
            // 生成目录
            mkdirs(BASE_PATH + folder);
            FileUtils.copyInputStreamToFile(file, new File(path));
            // 返回值
            FileUtil f = new FileUtil();
            f.setName(fullName.substring(0, fullName.length() - 4));
            f.setUrl(folder + fileName + ext);
            f.setSuffix(ext.substring(1));
            return f;
        } catch (IOException e) {
            throw new RuntimeException("写文件失败", e);
        }
    }

    /**
     * 删除文件
     *
     * @param url 文件相对路径
     */
    public static void delete(String url) {
        File file = new File(BASE_PATH + url);
        if (file.isFile() && file.exists()) {
            file.delete();
        }
    }

    /**
     * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir． (mkdir如果父目录不存在则会抛出异常)
     *
     * @param destPath 文件夹路径
     */
    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
    }

  /**
   * 批量复制
   *
   * @param fromFileName 来源文件名
   * @param toFileName   目标文件名
   * @param exts         后缀名
   */
  public static void copyAll(String fromFileName, String toFileName, String... exts) {
    for (String ext : exts) {
      try {
        FileUtils.copyFile(new File(fromFileName + ext), new File(toFileName + ext));
      } catch (IOException e) {
        LogUtil.error(CryptoUtil.class, e, "批量复制失败！");
      }
    }
  }

  /**
   * 批量删除
   *
   * @param fileName 文件名
   * @param exts     后缀名
   */
  public static void deleteFiles(String fileName, String... exts) {
    for (String ext : exts) {
      FileUtils.deleteQuietly(new File(fileName + ext));
    }
  }

  /**
   * 批量删除
   *
   * @param dir  文件夹
   * @param exts 后缀名
   */
  public static boolean deleteFiles(File dir, String... exts) {
    File[] files = dir.listFiles();
    if (files == null || files.length <= 0) {
      return Boolean.FALSE;
    }
    boolean flag = Boolean.FALSE;
    for (File file : files) {
      if (StringUtils.endsWithAny(file.getName(), exts)) {
        flag = file.delete();
      }
    }
    return flag;
  }

  /**
   * 批量删除
   *
   * @param dir  文件夹
   * @param name 文件名
   */
  public static void delFiles(File dir, String name) {
    File[] files = dir.listFiles();
    if (files == null || files.length <= 0) {
      return;
    }
    for (File file : files) {
      if (StringUtils.startsWith(file.getName(), name)) {
        file.delete();
      }
    }
  }

  /**
   * 检查目录后缀是否有File.separator
   */
  public static String checkDir(String path) {
    return StringUtils.endsWithAny(path, File.separator, "\\") ? path : (path + File.separator);
  }

  /**
   * 拼接目录路径，File.separator
   */
  public static String appendDir(String... dirs) {
    StringBuilder path = new StringBuilder();
    for (String dir : dirs) {
      if (StringUtils.endsWithAny(path, File.separator, "\\")) {
        path.append(dir);
      } else {
        path.append(dir).append(File.separator);
      }
    }
    return path.toString();
  }

  /**
   * 拼接文件路径，File.separator
   */
  public static String appendFile(String... dirs) {
    StringBuilder path = new StringBuilder();
    for (int i = 0; i < dirs.length; i++) {
      String dir = dirs[i];
      if (StringUtils.endsWithAny(path, File.separator, "\\") || i == dirs.length - 1) {
        path.append(dir);
      } else {
        path.append(dir).append(File.separator);
      }
    }
    return path.toString();
  }

  /**
   * 创建文件夹
   */
  public static boolean mkDir(String path) {
    File f = new File(checkDir(path));
    if (!f.exists()) {
      return f.mkdirs();
    }
    return true;
  }

  /**
   * 获取指定目录下的文件或文件夹列表
   *
   * @param dirPath 父级目录，为null 获取根目录文件夹或根目录文件
   * @param hasDir  是否获取文件夹，为false获取文件
   * @return 获取到的文件夹或文件名称
   */
  public static List<String> getList(String dirPath, boolean hasDir) {
    File[] files;
    if (StringUtils.isBlank(dirPath)) {
      files = File.listRoots();
    } else {
      files = new File(dirPath).listFiles();
    }
    List<String> paths = new LinkedList<>();
    if (files != null) {
      for (File file : files) {
        if (hasDir && file.isDirectory()) {
          paths.add(StringUtils.isBlank(dirPath) ? file.getPath() : file.getName());
        } else if (!hasDir && file.isFile()) {
          paths.add(file.getName());
        }
      }
    }
    return paths;
  }

  /**
   * 获取指定文件的二进制流
   *
   * @param filePath 文件路径
   * @return 文件的二进制流
   */
  public static byte[] getFile(String filePath) throws IOException {
    return FileUtils.readFileToByteArray(new File(filePath));
  }

  /**
   * 生成真实路径
   */
  public static String getPath(String rootPath, Object path) {
    rootPath = StringUtils.defaultString(rootPath, "");
    return rootPath + (path == null ? "" : path.toString());
  }

  /**
   * 获取当前路径
   */
  public static String getCurrentPath() throws IOException {
    return URLDecoder.decode(ResourceUtil.getResource("").getPath().substring(1), "UTF-8");
  }
}
