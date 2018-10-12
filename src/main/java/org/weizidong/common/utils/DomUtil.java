package org.weizidong.common.utils;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * xml工具类
 *
 * @author WeiZiDong
 * @date 2018-07-05
 */
public class DomUtil {
  /**
   * 解析
   */
  public static Map<String, String> parse(File file) {
    SAXReader reader = new SAXReader();
    Map<String, String> map = new HashMap<>(16);
    try {
      Element root = reader.read(file).getRootElement();
      Iterator it = root.elementIterator();
      while (it.hasNext()) {
        Element info = (Element) it.next();
        map.put(info.elementText("name"), info.elementText("value") + (info.elementText("uint") == null ? "" : info.elementText("uint")));
      }
      return map;
    } catch (DocumentException e) {
      e.printStackTrace();
    }
    return map;
  }
}
