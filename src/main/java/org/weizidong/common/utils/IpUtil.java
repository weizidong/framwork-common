package org.weizidong.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.weizidong.common.utils.client.ClientUtil;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

/**
 * IP操作工具类
 *
 * @author WeiZiDong
 */
public class IpUtil {
    private IpUtil() {
    }

    /**
     * 描述：获取IP地址
     *
     * @param request HttpServletRequest
     * @return IP地址
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
     * 描述：获取IP+[IP所属地址]
     *
     * @param request HttpServletRequest
     */
    public static String getIpBelongAddress(HttpServletRequest request) {
        String ip = getIpAddress(request);
        String belongIp = getIPbelongAddress(ip);
        return ip + belongIp;
    }

    /**
     * 描述：获取IP所属地址
     *
     * @param ip IP地址
     */
    public static String getIPbelongAddress(String ip) {
        String ipAddress = "[]";
        try {
            // 淘宝提供的服务地址
            String resp = ClientUtil.get("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip, String.class);
            JSONObject resObj = JSONObject.parseObject(resp);
            String code = resObj.getString("code");
            if ("0".equals(code)) {
                JSONObject data = resObj.getJSONObject("data");
                ipAddress = MessageFormat.format("[{0}-{1}-{2}-{3}]", data.getString("country"), data.getString("region"), data.getString("city"), data.getString("isp"));
            }
        } catch (Exception e) {
            LogUtil.error(IpUtil.class, e);
        }
        return ipAddress;
    }
}
