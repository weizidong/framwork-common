package org.weizidong.common.utils;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 图标管理类
 *
 * @author WeiZiDong
 * @date 2018-07-25
 */
public class IconUtil {
  public static Image getImage(String path) {
    return new Image(IconUtil.class.getResourceAsStream(path));
  }

  public static Image getLogo() {
    return getImage("/image/logo.png");
  }

  public static Node getOpen() {
    return new ImageView(getImage("/image/open.png"));
  }

  public static Node getClose() {
    return new ImageView(getImage("/image/close.png"));
  }

  public static Node getOk() {
    return new ImageView(getImage("/image/green.png"));
  }

  public static Node getError() {
    return new ImageView(getImage("/image/red.png"));
  }

  public static Node getNo() {
    return new ImageView(getImage("/image/gray.png"));
  }
}
