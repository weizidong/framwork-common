package org.weizidong.common.utils;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.poi.util.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author WeiZiDong
 * @date 2018-06-15
 */
public class CompressUtil {
  private final static int BUFFER = 1048576;

  /**
   * 解压tar.gz文件
   */
  public static void deCompress(File gzip, String sourceFolder) {
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    GZIPInputStream gzis = null;
    TarArchiveInputStream tais = null;
    OutputStream out = null;
    try {
      fis = new FileInputStream(gzip);
      bis = new BufferedInputStream(fis);
      gzis = new GZIPInputStream(bis);
      tais = new TarArchiveInputStream(gzis);
      TarArchiveEntry tae;
      boolean flag = false;
      while ((tae = tais.getNextTarEntry()) != null) {
        File tmpFile = new File(FileUtil.checkDir(sourceFolder) + tae.getName());
        if (!flag) {
          //使用 mkdirs 可避免因文件路径过多而导致的文件找不到的异常
          flag = FileUtil.mkDir(tmpFile.getParent());
        }
        out = new FileOutputStream(tmpFile);
        int length;
        byte[] b = new byte[BUFFER];
        while ((length = tais.read(b)) != -1) {
          out.write(b, 0, length);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IOUtils.closeQuietly(tais);
      IOUtils.closeQuietly(gzis);
      IOUtils.closeQuietly(bis);
      IOUtils.closeQuietly(fis);
      try {
        if (out != null) {
          out.flush();
          out.close();
        }
      } catch (Exception ignored) {
      }
    }
  }

  /**
   * 压缩tar文件
   */
  public static File compress(ArrayList<File> list, String outPutPath, String fileName) {
    File outPutFile = null;
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    FileOutputStream fos = null;
    GZIPOutputStream gzp = null;
    File tar = new File(FileUtil.checkDir(outPutPath) + "temp.tar");
    try {
      fis = new FileInputStream(pack(list, tar));
      bis = new BufferedInputStream(fis, BUFFER);
      outPutFile = new File(FileUtil.checkDir(outPutPath) + fileName + ".tar.gz");
      fos = new FileOutputStream(outPutFile);
      gzp = new GZIPOutputStream(fos);
      int count;
      byte[] data = new byte[BUFFER];
      while ((count = bis.read(data, 0, BUFFER)) != -1) {
        gzp.write(data, 0, count);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (gzp != null) {
          gzp.finish();
          gzp.flush();
          gzp.close();
        }
        IOUtils.closeQuietly(fos);
        IOUtils.closeQuietly(bis);
        IOUtils.closeQuietly(fis);
        FileUtils.deleteQuietly(tar);
      } catch (IOException ignored) {
      }
    }
    return outPutFile;
  }


  /**
   * 私有函数将文件集合压缩成tar包后返回
   *
   * @param files  要压缩的文件集合
   * @param target tar.输出流的目标文件
   * @return File  指定返回的目标文件
   */
  private static File pack(ArrayList<File> files, File target) {
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    TarArchiveOutputStream taos = null;
    FileInputStream fis = null;
    try {
      fos = new FileOutputStream(target);
      bos = new BufferedOutputStream(fos, BUFFER);
      taos = new TarArchiveOutputStream(bos);
      //解决文件名过长问题
      taos.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
      for (File file : files) {
        taos.putArchiveEntry(new TarArchiveEntry(file));
        fis = new FileInputStream(file);
        IOUtils.copy(fis, taos);
        taos.closeArchiveEntry();
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        IOUtils.closeQuietly(fis);
        if (taos != null) {
          taos.finish();
          taos.flush();
          taos.close();
        }
        if (bos != null) {
          bos.flush();
          bos.close();
        }
        if (fos != null) {
          fos.flush();
          fos.close();
        }
      } catch (IOException ignored) {
      }
    }
    return target;
  }
}
