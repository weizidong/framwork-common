package org.wzd.framwork.utils;

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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 极光推送工具类
 */
public class JPushUtil {
    private static final Logger LOGGER = LogManager.getLogger(RedisUtil.class);
    private static PropertiesUtil pros = new PropertiesUtil("/configs/jpush.properties");

    private static String getAppKey() {
        return pros.getProperty("APP_KEY");
    }

    private static String getMasterSecret() {
        return pros.getProperty("MASTER_SECRET");
    }

    /**
     * 快捷地构建推送对象：所有平台，所有设备，内容为 msg 的通知。
     *
     * @return
     */
    public static PushPayload buildPushObject_all_all_alert(String msg) {
        return PushPayload.alertAll(msg);
    }

    /**
     * 构建推送对象：所有平台，推送目标是别名为 "alias"，通知内容为 msg。
     *
     * @return
     */
    public static PushPayload buildPushObject_all_alias_alert(String alias, String msg) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.alert(msg))
                .build();
    }

    /**
     * 构建推送对象：平台是 Android，目标是 tag 为 "tag" 的设备，内容是 Android 通知 msg，并且标题为 title。
     *
     * @return
     */
    public static PushPayload buildPushObject_android_tag_alertWithTitle(String tag, String msg, String title) {
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
     * - 通知信息是 alert，角标数字为 badge，通知声音为 sound，并且附加字段 extra_key = extra_val；
     * <p>
     * 消息内容是 content。通知是 APNs 推送通道的，消息是 JPush 应用内消息通道的。
     * <p>
     * APNs 的推送环境是“生产”（如果不显式设置的话，Library 会默认指定为开发）
     *
     * @return
     */
    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage(String alert, int badge, String sound, String content, String extra_key, String extra_val, String... tags) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.tag_and(tags))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(alert)
                                .setBadge(badge)
                                .setSound(sound)
                                .addExtra(extra_key, extra_val)
                                .build())
                        .build())
                .setMessage(Message.content(content))
                .setOptions(Options.newBuilder().setApnsProduction(true).build())
                .build();
    }

    /**
     * 给一个目标发信息
     *
     * @param tag 推送目标
     * @param msg 消息内容
     */
    public static void sendSinglePush(String tag, String msg) {
        JPushClient jpushClient = new JPushClient(getMasterSecret(), getAppKey(), null, ClientConfig.getInstance());
        PushPayload payload = buildPushObject_android_and_iosSingle(tag, msg);
        try {
            PushResult result = jpushClient.sendPush(payload);
            LOGGER.info("推送消息结果 - " + result);
        } catch (APIConnectionException e) {
            LOGGER.error("连接错误。应该稍后重试。 ", e);
        } catch (APIRequestException e) {
            LOGGER.error("推送消息：" + msg + "  到  " + tag + "失败！", e);
        }
    }

    /**
     * 构建推送对象：平台是 Android和iOS，目标是 tag 的设备，内容是 msg。
     *
     * @param tag 目标
     * @param msg 内容
     * @return
     */
    public static PushPayload buildPushObject_android_and_iosSingle(String tag, String msg) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(tag))
                .setNotification(Notification.newBuilder().setAlert(msg)
                        .addPlatformNotification(AndroidNotification.newBuilder().setTitle("")
                                .build())
                        .addPlatformNotification(IosNotification.newBuilder().incrBadge(1).build()).build())
                .build();
    }

    /**
     * 给所有设备推送消息
     *
     * @param msg 内容
     */
    public static void sendPush(String msg) {
        JPushClient jpushClient = new JPushClient(getMasterSecret(), getAppKey(), null, ClientConfig.getInstance());
        PushPayload payload = buildPushObject_android_and_ios(msg);
        try {
            PushResult result = jpushClient.sendPush(payload);
            LOGGER.info("推送消息结果 - " + result);
        } catch (APIConnectionException e) {
            LOGGER.error("连接错误。应该稍后重试。 ", e);
        } catch (APIRequestException e) {
            LOGGER.error("推送消息：" + msg + "失败！", e);
        }
    }

    /**
     * 构建推送对象：平台是 Android和iOS，目标是 所有设备，内容是 msg。
     *
     * @param msg 内容
     */
    public static PushPayload buildPushObject_android_and_ios(String msg) {
        return PushPayload.newBuilder().setPlatform(Platform.android_ios()).setAudience(Audience.all())
                .setNotification(Notification.newBuilder().setAlert(msg).addPlatformNotification(AndroidNotification.newBuilder().setTitle("").build())
                        .addPlatformNotification(IosNotification.newBuilder().incrBadge(1).build()).build())
                .build();
    }

}
