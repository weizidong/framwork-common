package org.wzd.framwork.utils;

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
 */
public class QiniuUtil {
    public static final PropertiesUtil qiniuPro = new PropertiesUtil("/configs/qiniu.properties");
    public static final Integer expires = Integer.parseInt(qiniuPro.getProperty("expires"));
    public static final String bucket = qiniuPro.getProperty("bucket");
    public static final String url = qiniuPro.getProperty("url");
    public static final Auth auth = Auth.create(qiniuPro.getProperty("AK"), qiniuPro.getProperty("SK"));

    public static void delFile(Zone zone, String key) throws QiniuException {
        BucketManager bucketManager = new BucketManager(auth, new Configuration(zone));
        bucketManager.delete(bucket, key);
    }

    public static String uploadToken(Map<String, String> body) {
        StringMap policy = new StringMap();
        policy.put("returnBody", JSON.toJSONString(body));
        return auth.uploadToken(bucket, null, expires, policy);
    }
}
