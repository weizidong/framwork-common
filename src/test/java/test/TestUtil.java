package test;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.weizidong.common.utils.*;

import java.util.Collections;

public class TestUtil {
  @Test
  public void test1() {
    PropertiesUtil pu = new PropertiesUtil("/configs/dubbo.properties");
    pu.get("dubbo.name");
    pu.write("dubbo.name", "config-service");
  }

  @Test
  public void test2() {
    LogUtil.debug(getClass(), Collections.singletonList("debug"));
    LogUtil.debug(getClass(), new JSONObject().fluentPut("debug", "debug"));
    LogUtil.debug(getClass(), "debug {}", 1);
    LogUtil.error(getClass(), new RuntimeException("null"));
    LogUtil.error(getClass(), new RuntimeException("null"), "debug");
  }

  @Test
  public void test3() {
    String old = "123";
    String password = CryptoUtil.encode("ABCD1234", old, "AES");
    LogUtil.debug(getClass(), "明文：{} ===> 密文：{}", old, password);
    old = CryptoUtil.decode("ABCD1234", password, "AES");
    LogUtil.debug(getClass(), "原明文：{} ===> 密文：{} ===> 现明文：{}", old, password, old);
  }

  @Test
  public void test4() {
    LogUtil.debug(getClass(), IpUtil.getIPbelongAddress("221.234.135.29"));
  }

  @Test
  public void test5() {
    LogUtil.debug(getClass(), DateUtil.format(DateUtil.getCurrentDayEndTime(), DateUtil.P_TIMESTAMP));
  }

}
