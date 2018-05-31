package org.weizidong.common.utils.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.jaxrs.FastJsonProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.weizidong.common.utils.client.filter.log.LogResponseFilter;
import org.weizidong.common.utils.client.filter.log.LogRequestFilter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

/**
 * 客户端Client
 *
 * @author WeiZiDong
 */
public class RestClientUtil {

	private RestClientUtil() {
	}

	private static Client baseClient;

	static {
		buildClients();
	}

	private static void buildClients() {
		ClientConfig clientConfig = new ClientConfig();
		// 注册过滤器
		clientConfig.register(FastJsonProvider.class);
		clientConfig.register(LogRequestFilter.class);
		clientConfig.register(LogResponseFilter.class);
		baseClient = ClientBuilder.newClient(clientConfig);
	}

	public static Client getBaseClient() {
		return baseClient;
	}

	public static WebTarget buildWebTarget(String url) {
		return baseClient.target(url);
	}

	/**
	 * POST form表单
	 *
	 * @param path
	 *            完整的访问路径
	 * @param formParam
	 *            form表单
	 * @param beanClass
	 *            返回结果的类型
	 * @param <T>
	 *            泛型
	 * @return
	 */
	public static <T> T postForm(String path, MultivaluedMap<String, String> formParam, Class<T> beanClass) {
		Form form = new Form(formParam);
		String json = buildWebTarget(path).request(MediaType.APPLICATION_JSON_TYPE)
				.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);
		return JSON.parseObject(json, beanClass);
	}

	/**
	 * POST JSON数据
	 *
	 * @param path
	 *            完整的访问路径
	 * @param param
	 *            参数Bean
	 * @param beanClass
	 *            返回结果的类型
	 * @param <T>
	 *            泛型
	 * @return
	 */
	public static <T> T postJson(String path, Object param, Class<T> beanClass) {
		String json = buildWebTarget(path).request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(param, MediaType.APPLICATION_JSON_TYPE),
				String.class);
		return JSON.parseObject(json, beanClass);
	}

	/**
	 * GET 方法
	 *
	 * @param path
	 *            完整路径，包含URL参数
	 * @param beanClass
	 *            返回结果的类型
	 * @param <T>
	 *            泛型
	 * @return
	 */
	public static <T> T get(String path, Class<T> beanClass) {
		String json = buildWebTarget(path).request().get(String.class);
		return JSON.parseObject(json, beanClass);
	}

}
