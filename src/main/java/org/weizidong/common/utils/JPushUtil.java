package org.weizidong.common.utils;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.apache.commons.lang3.StringUtils;

/**
 * 极光推送工具类
 *
 * @author WeiZiDong
 * @date 2018-10-12
 */
public class JPushUtil {
  private static PropertiesUtil pros = new PropertiesUtil("/configs/jpush.properties");

  private static String getAppKey() {
    return pros.get("APP_KEY");
  }

  private static String getMasterSecret() {
    return pros.get("MASTER_SECRET");
  }

  private static Boolean isClose() {
    String close = pros.get("close");
    return StringUtils.equalsIgnoreCase(close, "true") ? Boolean.TRUE : Boolean.FALSE;
  }

  /**
   * 快捷地构建推送对象：所有平台，所有设备，内容为 msg 的通知。
   */
  public static PushPayload buildPushAllAlert(String msg) {
    return PushPayload.alertAll(msg);
  }

  /**
   * 构建推送对象：所有平台，推送目标是别名为 "alias"，通知内容为 msg。
   */
  public static PushPayload buildPushAllAliasAlert(String alias, String msg) {
    return PushPayload.newBuilder()
            .setPlatform(Platform.all())
            .setAudience(Audience.alias(alias))
            .setNotification(Notification.alert(msg))
            .build();
  }

  /**
   * 构建推送对象：平台是 Android，目标是 tag 为 "tag" 的设备，内容是 Android 通知 msg，并且标题为 title。
   */
  public static PushPayload buildPushAndroidTagAlertWithTitle(String tag, String msg, String title) {
    return PushPayload.newBuilder()
            .setPlatform(Platform.android())
            .setAudience(Audience.tag(tag))
            .setNotification(Notification.android(msg, title, null))
            .build();
  }

  /**
   * 构建推送对象：
   * <p>
   * 平台是 iOS，推送目标是 集合tags["tag1", "tag_all"] 的交集，推送内容同时包括通知与消息
   * <p>
   * - 通知信息是 alert，角标数字为 badge，通知声音为 sound，并且附加字段 extraKey = extraVal；
   * <p>
   * 消息内容是 content。通知是 APNs 推送通道的，消息是 JPush 应用内消息通道的。
   * <p>
   * APNs 的推送环境是“生产”（如果不显式设置的话，Library 会默认指定为开发）
   */
  public static PushPayload buildPushIosTagAndAlertWithExtrasAndMessage(String alert, int badge, String sound, String content, String extraKey, String extraVal, String... tags) {
    return PushPayload.newBuilder()
            .setPlatform(Platform.ios())
            .setAudience(Audience.tag_and(tags))
            .setNotification(Notification.newBuilder()
                    .addPlatformNotification(IosNotification.newBuilder()
                            .setAlert(alert)
                            .setBadge(badge)
                            .setSound(sound)
                            .addExtra(extraKey, extraVal)
                            .build())
                    .build())
            .setMessage(Message.content(content))
            .setOptions(Options.newBuilder().setApnsProduction(true).build())
            .build();
  }

  /**
   * 给一个目标发信息
   *
   * @param tag   推送目标
   * @param title 标题
   * @param msg   消息内容
   */
  public static void sendSinglePush(String tag, String title, String msg) {
    if (isClose()) {
      return;
    }
    JPushClient jpushClient = new JPushClient(getMasterSecret(), getAppKey(), null, ClientConfig.getInstance());
    PushPayload payload = buildPushAndroidAndIosSingle(tag, title, msg);
    try {
      PushResult result = jpushClient.sendPush(payload);
      LogUtil.info(JPushUtil.class, "推送消息结果 - {}", result);
    } catch (APIConnectionException e) {
      LogUtil.error(JPushUtil.class, e, "连接错误。应该稍后重试。 ");
    } catch (APIRequestException e) {
      LogUtil.error(JPushUtil.class, e, "推送消息：{0}  到  {1}失败！", msg, tag);
    }
  }

  /**
   * 给一个目标发信息
   *
   * @param tag 推送目标
   * @param msg 消息内容
   */
  public static void sendSinglePush(String tag, String msg) {
    sendSinglePush(tag, "", msg);
  }

  /**
   * 构建推送对象：平台是 Android和iOS，目标是 tag 的设备，标题是 title。内容是 msg。
   *
   * @param tag 目标
   * @param msg 内容
   */
  public static PushPayload buildPushAndroidAndIosSingle(String tag, String title, String msg) {
    return PushPayload.newBuilder()
            .setPlatform(Platform.android_ios())
            .setAudience(Audience.alias(tag))
            .setNotification(Notification.newBuilder().setAlert(msg)
                    .addPlatformNotification(AndroidNotification.newBuilder().setTitle(title)
                            .build())
                    .addPlatformNotification(IosNotification.newBuilder().incrBadge(1).build()).build())
            .build();
  }

  /**
   * 给所有设备推送消息
   *
   * @param title 标题
   * @param msg   内容
   */
  public static void sendPush(String title, String msg) {
    if (isClose()) {
      return;
    }
    JPushClient jpushClient = new JPushClient(getMasterSecret(), getAppKey(), null, ClientConfig.getInstance());
    PushPayload payload = buildPushAndroidAndIos(title, msg);
    try {
      PushResult result = jpushClient.sendPush(payload);
      LogUtil.info(JPushUtil.class, "推送消息结果 - {}", result);
    } catch (APIConnectionException e) {
      LogUtil.error(JPushUtil.class, e, "连接错误。应该稍后重试。 ");
    } catch (APIRequestException e) {
      LogUtil.error(JPushUtil.class, e, "推送消息：{0}失败！", msg);
    }
  }

  /**
   * 给所有设备推送消息
   *
   * @param msg 内容
   */
  public static void sendPush(String msg) {
    sendPush("", msg);
  }

  /**
   * 构建推送对象：平台是 Android和iOS，目标是 所有设备，内容是 msg。
   *
   * @param title 标题
   * @param msg   内容
   */
  public static PushPayload buildPushAndroidAndIos(String title, String msg) {
    return PushPayload.newBuilder().setPlatform(Platform.android_ios()).setAudience(Audience.all())
            .setNotification(Notification.newBuilder().setAlert(msg).addPlatformNotification(AndroidNotification.newBuilder().setTitle(title).build())
                    .addPlatformNotification(IosNotification.newBuilder().incrBadge(1).build()).build())
            .build();
  }

}
