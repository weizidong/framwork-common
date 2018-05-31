package org.weizidong.common.utils.client.filter.log;

import org.weizidong.common.utils.LogUtil;
import org.weizidong.common.utils.client.ClientUtil;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;

/**
 * 请求客户端请求响应过滤器
 *
 * @author WeiZiDong
 */
public class LogResponseFilter implements ClientResponseFilter {

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) {
        long startTs = (Long) requestContext.getProperty(LogRequestFilter.START_TS_PROPERTY);
        LogUtil.info(ClientUtil.class, "响应请求：{}", requestContext.getUri());
        LogUtil.info(ClientUtil.class, "耗时(ms)：{}", (System.currentTimeMillis() - startTs));
    }

}
