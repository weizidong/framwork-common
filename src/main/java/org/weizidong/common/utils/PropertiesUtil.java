package org.weizidong.common.utils;

import java.io.*;
import java.net.URI;
import java.util.*;

/**
 * 读取properties文件工具
 *
 * @author WeiZiDong
 */
public class PropertiesUtil {
    /**
     * 资源文件
     */
    private Properties props;
    /**
     * 资源路径
     */
    private URI uri;

    /**
     * 实例化工具类
     *
     * @param fileName 资源相对路径
     */
    public PropertiesUtil(String fileName) {
        read(fileName);
    }

    /**
     * 读取资源文件
     *
     * @param fileName 资源文件相对路径
     */
    private void read(String fileName) {
        try {
            props = new Properties();
            InputStream fis = ResourceUtil.getResourceAsStream(fileName);
            uri = Objects.requireNonNull(ResourceUtil.getResource(fileName)).toURI();
            props.load(fis);
        } catch (Exception e) {
            LogUtil.debug(getClass(), e);
        }
    }

    /**
     * 获取某个属性
     *
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        String res = props.getProperty(key);
        LogUtil.debug(getClass(), "读取：{} ===> {}", key, res);
        return res;
    }

    /**
     * 获取所有属性，返回一个map,不常用 可以试试props.putAll(t)
     */
    public Map<String, Object> getAll() {
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
    public void printAll() {
        props.list(System.out);
    }

    /**
     * 写入properties信息
     *
     * @param key   键
     * @param value 值
     */
    public void write(String key, String value) {
        LogUtil.debug(getClass(), "写入：{} ===> {} ", key, value);
        try {
            OutputStream fos = new FileOutputStream(new File(uri));
            props.put(key, value);
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流
            props.store(fos, null);
        } catch (IOException e) {
            LogUtil.error(getClass(), e);
        }
    }

    /**
     * 写入properties信息
     *
     * @param map 键值对的map
     */
    public void writeAll(Map<String, Object> map) {
        LogUtil.debug(getClass(), "写入全部：{}", map);
        try {
            OutputStream fos = new FileOutputStream(new File(uri));
            props.putAll(map);
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流
            props.store(fos, null);
        } catch (IOException e) {
            LogUtil.error(getClass(), e);
        }
    }

}
