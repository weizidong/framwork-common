package org.wzd.framwork.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 读取properties文件工具
 *
 * @author WeiZiDong
 */
public class PropertiesUtil {
	private static final Logger LOGGER = LogManager.getLogger(PropertiesUtil.class);
	private Properties props;
	private URI uri;

	public PropertiesUtil(String fileName) {
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
	 */
	public String getProperty(String key) {
		String res = props.getProperty(key);
		LOGGER.debug(key + " ===> " + res);
		return props.getProperty(key);
	}

	/**
	 * 获取所有属性，返回一个map,不常用 可以试试props.putAll(t)
	 */
	public Map<String, String> getAllProperty() {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<?> enu = props.propertyNames();
		while (enu.hasMoreElements()) {
			String key = (String) enu.nextElement();
			String value = props.getProperty(key);
			map.put(key, value);
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
	 */
	public void writeProperties(String key, String value) {
		LOGGER.debug("写入： " + key + " ===> " + value);
		try {
			OutputStream fos = new FileOutputStream(new File(uri));
			props.setProperty(key, value);
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			props.store(fos, null);
		} catch (Exception e) {
			LOGGER.debug("写入： " + key + " ===> " + value + " 失败！");
			e.printStackTrace();
		}
	}
}
