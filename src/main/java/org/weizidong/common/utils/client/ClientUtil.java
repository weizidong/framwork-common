package org.weizidong.common.utils.client;

import com.alibaba.fastjson.support.jaxrs.FastJsonProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.weizidong.common.utils.client.filter.log.LogRequestFilter;
import org.weizidong.common.utils.client.filter.log.LogResponseFilter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

/**
 * 客户端Client工具类
 *
 * @author WeiZiDong
 */
public class ClientUtil {
    private ClientUtil() {
    }

    private static Client client;

    static {
        buildClients();
    }

    private static void buildClients() {
        ClientConfig clientConfig = new ClientConfig();
        // 注册过滤器
        clientConfig.register(FastJsonProvider.class);
        clientConfig.register(LogRequestFilter.class);
        clientConfig.register(LogResponseFilter.class);
        client = ClientBuilder.newClient(clientConfig);
    }

    /**
     * 获取客户端
     *
     * @return 客户端对象
     */
    public static Client getClient() {
        return client;
    }

    /**
     * 建立请求目标
     *
     * @param url 目标url
     * @return 请求对象
     */
    public static WebTarget target(String url) {
        return client.target(url);
    }

    /**
     * POST form表单
     *
     * @param path      完整的访问路径
     * @param formParam form表单
     * @param beanClass 返回结果的类型
     * @param <T>       结果类型
     * @return 返回的结果
     */
    public static <T> T postForm(String path, MultivaluedMap<String, String> formParam, Class<T> beanClass) {
        Form form = new Form(formParam);
        return target(path).request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), beanClass);
    }

    /**
     * POST JSON数据
     *
     * @param path      完整的访问路径
     * @param param     参数Bean
     * @param beanClass 返回结果的类型
     * @param <T>       结果类型
     * @return 返回的结果
     */
    public static <T> T postJson(String path, Object param, Class<T> beanClass) {
        return target(path).request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(param, MediaType.APPLICATION_JSON_TYPE), beanClass);
    }

    /**
     * GET 方法
     *
     * @param path      完整路径，包含URL参数
     * @param beanClass 返回结果的类型
     * @param <T>       结果类型
     * @return 返回的结果
     */
    public static <T> T get(String path, Class<T> beanClass) {
        return target(path).request().get(beanClass);
    }

}
