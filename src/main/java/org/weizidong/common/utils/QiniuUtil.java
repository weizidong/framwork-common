package org.weizidong.common.utils;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import java.util.Map;

/**
 * 七牛操作封装
 * @author WeiZiDong
 * @date 2018-10-12
 */
public class QiniuUtil {
  public static final PropertiesUtil QINIU_PRO = new PropertiesUtil("/configs/qiniu.properties");
  public static final Integer EXPIRES = Integer.parseInt(QINIU_PRO.get("expires"));
  public static final String BUCKET = QINIU_PRO.get("bucket");
  public static final String URL = QINIU_PRO.get("url");
  public static final Auth AUTH = Auth.create(QINIU_PRO.get("AK"), QINIU_PRO.get("SK"));

  /**
   * 删除文件
   *
   * @param zone 上传文件区
   * @param key  文件key
   */
  public static void delFile(Zone zone, String key) {
    BucketManager bucketManager = new BucketManager(AUTH, new Configuration(zone));
    try {
      bucketManager.delete(BUCKET, key.replace(URL, ""));
    } catch (QiniuException e) {
      LogUtil.error(QiniuUtil.class, e, "删除文件失败！");
    }
  }

  /**
   * 获取上传token签名
   *
   * @param body 签名内容
   */
  public static String uploadToken(Map<String, String> body) {
    StringMap policy = new StringMap();
    policy.put("returnBody", JSON.toJSONString(body));
    return AUTH.uploadToken(BUCKET, null, EXPIRES, policy);
  }
}
