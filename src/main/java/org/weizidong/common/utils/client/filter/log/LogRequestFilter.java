package org.weizidong.common.utils.client.filter.log;

import org.weizidong.common.utils.LogUtil;
import org.weizidong.common.utils.client.ClientUtil;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

/**
 * 请求客户端日志记录过滤器
 *
 * @author WeiZiDong
 */
public class LogRequestFilter implements ClientRequestFilter {

    public static final String START_TS_PROPERTY = "_start_ts";

    @Override
    public void filter(ClientRequestContext requestContext) {
        LogUtil.info(ClientUtil.class, "发送请求到：{}", requestContext.getUri());
        LogUtil.info(ClientUtil.class, "参数：{}", requestContext.getEntity());
        requestContext.setProperty(START_TS_PROPERTY, System.currentTimeMillis());
    }

}
