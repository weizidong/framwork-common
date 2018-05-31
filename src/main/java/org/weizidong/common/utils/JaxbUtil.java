package org.weizidong.common.utils;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Jaxb2工具类
 *
 * @author WeiZiDong
 */
public class JaxbUtil {
    private JaxbUtil() {
    }

    /**
     * JavaBean转换成xml 默认编码UTF-8
     *
     * @param obj JavaBean
     * @return xml
     */
    public static String convertToXml(Object obj) {
        return convertToXml(obj, "UTF-8");
    }

    /**
     * JavaBean转换成xml
     *
     * @param obj      JavaBean
     * @param encoding 编码格式
     * @return xml
     */
    public static String convertToXml(Object obj, String encoding) {
        String result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            result = writer.toString();
        } catch (Exception e) {
            LogUtil.error(JaxbUtil.class, e);
        }
        return result;
    }

    /**
     * xml转换成JavaBean
     *
     * @param xml xml
     * @param c   目标.class
     * @return JavaBean
     */
    public static <T> T convertToJavaBean(String xml, Class<T> c) {
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            LogUtil.error(JaxbUtil.class, e);
        }
        return t;
    }

    /**
     * xml转换成JavaBean
     *
     * @param xml xml
     * @param c   目标.class
     * @return JavaBean
     */
    public static <T> T converyToJavaBean(InputStream xml, Class<T> c) {
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(xml);
        } catch (Exception e) {
            LogUtil.error(JaxbUtil.class, e);
        }
        return t;
    }
}
