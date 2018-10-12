package org.weizidong.common.utils;

import org.apache.commons.io.FileUtils;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

import java.io.File;

/**
 * @author WeiZiDong
 * @date 2018-06-15
 */
public class ZipUtil {
  /**
   * 压缩文件
   */
  public static void compress(String srcPathName, File zipFile) {
    compress(srcPathName, zipFile, null);
  }

  /**
   * 压缩文件
   */
  public static void compress(String srcPathName, File zipFile, String includes) {
    File srcDir = new File(srcPathName);
    if (!srcDir.exists()) {
      throw new RuntimeException(srcPathName + "不存在！");
    }
    FileUtils.deleteQuietly(zipFile);
    Project prj = new Project();
    Zip zip = new Zip();
    zip.setProject(prj);
    zip.setDestFile(zipFile);
    FileSet fileSet = new FileSet();
    fileSet.setProject(prj);
    fileSet.setDir(srcDir);
    if (includes != null) {
      // 包括哪些文件或文件夹
      // eg:zip.setIncludes("*.java");
      fileSet.setIncludes(includes);
    }
    // 排除哪些文件或文件夹
    // fileSet.setExcludes(...);
    zip.addFileset(fileSet);
    zip.setEncoding("UTF-8");
    zip.execute();
  }

  /**
   * 压缩文件
   */
  public static void compressComtrade(String srcPathName, File zipFile, String fileName) {
    File srcDir = new File(srcPathName);
    if (!srcDir.exists()) {
      throw new RuntimeException(srcPathName + "不存在！");
    }
    FileUtils.deleteQuietly(zipFile);
    Project prj = new Project();
    Zip zip = new Zip();
    zip.setProject(prj);
    zip.setDestFile(zipFile);
    zipFileComtrade(zip, prj, srcDir, fileName + ".cfg");
    zipFileComtrade(zip, prj, srcDir, fileName + ".dat");
    zipFileComtrade(zip, prj, srcDir, fileName + ".hdr");
    zipFileComtrade(zip, prj, srcDir, fileName + ".ext");
    zip.setEncoding("UTF-8");
    zip.execute();
  }

  /**
   * 压缩文件
   */
  private static void zipFileComtrade(Zip zip, Project prj, File srcDir, String fileName) {
    FileSet fileSet = new FileSet();
    fileSet.setProject(prj);
    fileSet.setDir(srcDir);
    // 包括哪些文件或文件夹 eg:zip.setIncludes("*.java");
    fileSet.setIncludes(fileName);
    // 排除哪些文件或文件夹
    // fileSet.setExcludes(...);
    zip.addFileset(fileSet);
  }

  /**
   * 解压缩
   *
   * @param destDir   生成的目标目录下   c:/a
   * @param sourceZip 源zip文件      c:/upload.zip
   *                  结果则是 将upload.zip文件解压缩到c:/a目录下
   */
  public static void unZip(String destDir, File sourceZip) {
    Expand expand = new Expand();
    expand.setProject(new Project());
    expand.setSrc(sourceZip);
    //是否覆盖
    expand.setOverwrite(true);
    expand.setDest(new File(destDir));
    expand.setEncoding("UTF-8");
    expand.execute();
  }

  /**
   * 解压缩
   *
   * @param destDir   生成的目标目录下   c:/a
   * @param sourceZip 源zip文件      c:/upload.zip
   *                  结果则是 将upload.zip文件解压缩到c:/a目录下
   */
  public static void unZip(String destDir, String sourceZip) {
    unZip(destDir, new File(sourceZip));
  }
}
