package org.weizidong.conmon.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

/**
 * IP操作工具类
 * 
 * @author WeiZiDong
 *
 */
public class IPUtil {

	public final static Logger LOGGER = LogManager.getLogger(IPUtil.class);

	/**
	 * 
	 * 描述：获取IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {

		String ip = request.getHeader("x-forwarded-for");

		if (ip == null || ip.length() == 0 || "nuknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "nuknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "nuknown".equalsIgnoreCase(ip)) {

			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 
	 * 描述：获取IP+[IP所属地址]
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpBelongAddress(HttpServletRequest request) {

		String ip = getIpAddress(request);
		String belongIp = getIPbelongAddress(ip);

		return ip + belongIp;
	}

	/**
	 * 
	 * 描述：获取IP所属地址
	 * 
	 * @param ip
	 * @return
	 */
	public static String getIPbelongAddress(String ip) {

		String ipAddress = "[]";
		try {
			// 淘宝提供的服务地址
			String context = call("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip);
			JSONObject fromObject = JSONObject.parseObject(context);
			String code = fromObject.getString("code");
			if (code.equals("0")) {
				JSONObject jsonObject = fromObject.getJSONObject("data");
				ipAddress = "[" + jsonObject.get("country") + "/" + jsonObject.get("city") + "]";
			}
		} catch (Exception e) {

			LOGGER.error("获取IP所属地址出错", e);
			e.printStackTrace();
		}
		return ipAddress;
	}

	/**
	 * 
	 * 描述：获取Ip所属地址
	 * 
	 * @param urlStr
	 * @return
	 */
	public static String call(String urlStr) {

		try {

			URL url = new URL(urlStr);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();

			httpCon.setConnectTimeout(3000);
			httpCon.setDoInput(true);
			httpCon.setRequestMethod("GET");

			int code = httpCon.getResponseCode();

			if (code == 200) {
				return streamConvertToSting(httpCon.getInputStream());
			}
		} catch (Exception e) {
			LOGGER.error("获取IP所属地址出错", e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 描述：将InputStream转换成String
	 * 
	 * @param is
	 * @return
	 */
	public static String streamConvertToSting(InputStream is) {

		String tempStr = "";
		try {

			if (is == null)
				return null;
			ByteArrayOutputStream arrayOut = new ByteArrayOutputStream();
			byte[] by = new byte[1024];
			int len = 0;
			while ((len = is.read(by)) != -1) {
				arrayOut.write(by, 0, len);
			}
			tempStr = new String(arrayOut.toByteArray());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return tempStr;
	}

}
