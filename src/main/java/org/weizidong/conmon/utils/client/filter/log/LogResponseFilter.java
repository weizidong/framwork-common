package org.weizidong.conmon.utils.client.filter.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import java.io.IOException;

/**
 * 请求客户端请求响应过滤器
 */
public class LogResponseFilter implements ClientResponseFilter {
	private static final Logger log = LogManager.getLogger(LogResponseFilter.class);

	public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {

		long startTs = (Long) requestContext.getProperty(LogRequestFilter.START_TS_PROPERTY);
		long endTs = System.currentTimeMillis();
		log.info("响应请求：" + requestContext.getUri());
		log.info("耗时(ms):" + (endTs - startTs));
	}

}
