package org.weizidong.common.utils;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

/**
 * 对话框快捷工具
 *
 * @author WeiZiDong
 * @date 2018-07-24
 */
public class DialogUtil {

  /**
   * 对话框
   */
  public static void show(Stage stage, String title, String header, String content, Alert.AlertType type) {
    Platform.runLater(() -> {
      Alert alert = new Alert(type);
      alert.setTitle(title);
      alert.setHeaderText(header);
      alert.setContentText(content);
      alert.initOwner(stage);
      alert.showAndWait();
    });
  }

  /**
   * 消息对话框
   */
  public static void info(Stage stage, String title, String header, String content) {
    show(stage, title, header, content, Alert.AlertType.INFORMATION);
  }

  /**
   * 没有标题的消息对话框
   */
  public static void info(Stage stage, String title, String content) {
    info(stage, title, null, content);
  }

  /**
   * 警告对话框
   */
  public static void warning(Stage stage, String title, String header, String content) {
    show(stage, title, header, content, Alert.AlertType.WARNING);
  }

  /**
   * 没有标题的警告对话框
   */
  public static void warning(Stage stage, String title, String content) {
    warning(stage, title, null, content);
  }

  /**
   * 错误对话框
   */
  public static void error(Stage stage, String title, String header, String content) {
    show(stage, title, header, content, Alert.AlertType.ERROR);
  }

  /**
   * 没有标题的错误对话框
   */
  public static void error(Stage stage, String title, String content) {
    error(stage, title, null, content);
  }

  /**
   * 异常对话框
   */
  public static void exception(Stage stage, String title, String header, String content, Throwable e) {
    Platform.runLater(() -> {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle(title);
      alert.setHeaderText(header);
      alert.setContentText(content);
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      e.printStackTrace(pw);
      String exceptionText = sw.toString();
      Label label = new Label("异常信息如下:");
      TextArea textArea = new TextArea(exceptionText);
      textArea.setEditable(false);
      textArea.setWrapText(true);
      textArea.setMaxWidth(Double.MAX_VALUE);
      textArea.setMaxHeight(Double.MAX_VALUE);
      GridPane.setVgrow(textArea, Priority.ALWAYS);
      GridPane.setHgrow(textArea, Priority.ALWAYS);
      GridPane expContent = new GridPane();
      expContent.setMaxWidth(Double.MAX_VALUE);
      expContent.add(label, 0, 0);
      expContent.add(textArea, 0, 1);
      alert.getDialogPane().setExpandableContent(expContent);
      alert.initOwner(stage);
      alert.showAndWait();
    });
  }

  /**
   * 没有标题的异常对话框
   */
  public static void exception(Stage stage, String title, String content, Throwable e) {
    exception(stage, title, null, content, e);
  }

  /**
   * 确认对话框
   */
  public static boolean confirm(Stage stage, String title, String header, String content) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    alert.initOwner(stage);
    Optional<ButtonType> result = alert.showAndWait();
    return result.orElse(ButtonType.CANCEL) == ButtonType.OK;
  }

  /**
   * 没有标题的确认对话框
   */
  public static boolean confirm(Stage stage, String title, String content) {
    return confirm(stage, title, null, content);
  }

  /**
   * 输入对话框
   */
  public static Optional<String> text(Stage stage, String title, String header, String content) {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle(title);
    dialog.setHeaderText(header);
    dialog.setContentText(content);
    dialog.initOwner(stage);
    return dialog.showAndWait();
  }

  /**
   * 输入对话框
   */
  public static Optional<String> text(Stage stage, String title, String content) {
    return text(stage, title, null, content);
  }
}
