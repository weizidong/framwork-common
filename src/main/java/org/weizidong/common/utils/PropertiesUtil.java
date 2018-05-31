package org.weizidong.common.utils;

import java.io.*;
import java.net.URI;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 读取properties文件工具
 *
 * @author WeiZiDong
 */
public class PropertiesUtil {
    private Properties props;
    private URI uri;

    PropertiesUtil(String fileName) {
        readProperties(fileName);
    }

    private void readProperties(String fileName) {
        try {
            props = new Properties();
            InputStream fis = this.getClass().getResourceAsStream(fileName);
            props.load(fis);
            uri = this.getClass().getResource(fileName).toURI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取某个属性
     *
     * @param key 键
     * @return 值
     */
    public String getProperty(String key) {
        String res = props.getProperty(key);
        LogUtil.debug(getClass(), "读取：{} ===> {}", key, res);
        return res;
    }

    /**
     * 获取所有属性，返回一个map,不常用 可以试试props.putAll(t)
     */
    public Map<String, Object> getAllProperty() {
        Map<String, Object> map = new HashMap<>(16);
        Enumeration<?> enu = props.propertyNames();
        while (enu.hasMoreElements()) {
            Object key = enu.nextElement();
            String value = props.getProperty(key.toString());
            map.put(key.toString(), value);
        }
        return map;
    }

    /**
     * 在控制台上打印出所有属性，调试时用。
     */
    public void printProperties() {
        props.list(System.out);
    }

    /**
     * 写入properties信息
     *
     * @param key   键
     * @param value 值
     */
    public void writeProperties(String key, String value) {
        LogUtil.debug(getClass(), "写入：{} ===> {} ", key, value);
        try {
            OutputStream fos = new FileOutputStream(new File(uri));
            props.setProperty(key, value);
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流
            props.store(fos, null);
        } catch (IOException e) {
            LogUtil.error(getClass(), e);
        }
    }

    public static void main(String[] args) {
        PropertiesUtil pu = new PropertiesUtil("configs/config.properties");
        pu.printProperties();
    }
}
