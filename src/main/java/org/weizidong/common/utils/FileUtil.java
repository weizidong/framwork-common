package org.weizidong.common.utils;

import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

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
}
