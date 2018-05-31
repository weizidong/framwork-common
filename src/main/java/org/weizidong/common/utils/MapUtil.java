package org.weizidong.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * map工具类
 *
 * @author WeiZiDong
 * @date 2018-05-31
 */
public class MapUtil {
    private MapUtil() {
    }

    /**
     * Map key 升序排序
     * <p>
     * 排序方式：ASCII码从小到大排序（字典序）
     *
     * @param map 需排序的map集合
     * @return 排序后的map集合
     */
    public static <T> Map<String, T> sortAsc(Map<String, T> map) {
        HashMap<String, T> tempMap = new LinkedHashMap<>();
        List<Map.Entry<String, T>> infoIds = new ArrayList<>(map.entrySet());
        //排序
        infoIds.sort(Comparator.comparing(Map.Entry::getKey));
        for (Map.Entry<String, T> item : infoIds) {
            tempMap.put(item.getKey(), item.getValue());
        }
        return tempMap;
    }

    /**
     * url 参数串连
     *
     * @param map            需拼接的参数map
     * @param valueUrlEncode 是否需要对map的value进行url编码
     * @return 拼接后的URL键值对字符串
     */
    public static String mapJoin(Map<String, String> map, boolean valueUrlEncode) {
        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            if (StringUtils.isNotBlank(map.get(key))) {
                try {
                    String temp = (key.endsWith("_") && key.length() > 1) ? key.substring(0, key.length() - 1) : key;
                    sb.append(temp).append("=");
                    //获取到map的值
                    String value = map.get(key);
                    //判断是否需要url编码
                    if (valueUrlEncode) {
                        value = URLEncoder.encode(map.get(key), "utf-8").replace("+", "%20");
                    }
                    sb.append(value).append("&");
                } catch (UnsupportedEncodingException e) {
                    LogUtil.error(MapUtil.class, e);
                }
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 简单 xml 转换为 Map
     *
     * @param xml XML
     * @return Map对象
     */
    public static Map<String, String> xmlToMap(String xml) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(new ByteArrayInputStream(xml.getBytes()));
            Element element = document.getDocumentElement();
            NodeList nodeList = element.getChildNodes();
            Map<String, String> map = new LinkedHashMap<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node e = nodeList.item(i);
                map.put(e.getNodeName(), e.getTextContent());
            }
            return map;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            LogUtil.error(MapUtil.class, e);
        }
        return null;
    }
}
