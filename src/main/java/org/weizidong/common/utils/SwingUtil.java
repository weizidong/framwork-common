package org.weizidong.common.utils;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 窗口处理封装
 *
 * @author WeiZiDong
 * @date 2018-07-26
 */
public class SwingUtil {

  /**
   * 隐藏控件
   */
  public static void hide(Region r) {
    r.setVisible(false);
    r.setManaged(false);
  }

  /**
   * 显示控件
   */
  public static void show(Region r) {
    r.setVisible(true);
    r.setManaged(true);
  }

  /**
   * 创建新窗口
   */
  public static <T> Stage openWindow(String fxml, String title, Integer width, Integer height, Consumer<T> get, Callback<Class<?>, Object> controllerFactory, String css) {
    try {
      FXMLLoader loader = new FXMLLoader();
      if (controllerFactory != null) {
        loader.setControllerFactory(controllerFactory);
      } else {
        loader.setBuilderFactory(new JavaFXBuilderFactory());
      }
      loader.setLocation(SwingUtil.class.getResource(fxml));
      Stage stage = new Stage();
      Scene scene;
      if (width != null && height != null) {
        scene = new Scene(loader.load(), width, height);
      } else {
        scene = new Scene(loader.load());
//        stage.setMaximized(true);
      }
      scene.getStylesheets().add(SwingUtil.class.getResource(css).toExternalForm());
      stage.setTitle(title);
      stage.getIcons().add(IconUtil.getLogo());
      stage.widthProperty().addListener((observable, oldValue, newValue) -> stage.getIcons().add(IconUtil.getLogo()));
      stage.setScene(scene);
      stage.setOnCloseRequest(event -> stage.hide());
      stage.show();
      if (get != null) {
        get.accept(loader.getController());
      }
      return stage;
    } catch (Exception e) {
      LogUtil.error(SwingUtil.class, e, "{0} 打开失败！", title);
      return null;
    }
  }

  /**
   * 设置开始时间
   *
   * @param end 结束时间
   * @return DateCell
   */
  public static DateCell setStartDate(DatePicker end) {
    return new DateCell() {
      @Override
      public void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty);
        if (end.getValue() == null && item.isAfter(LocalDate.now())) {
          setDisable(true);
          setStyle("-fx-background-color: #AAAAAA");
        }
        if (end.getValue() != null && item.isAfter(end.getValue())) {
          setDisable(true);
          setStyle("-fx-background-color: #AAAAAA");
        }
      }
    };
  }

  /**
   * 设置结束时间
   *
   * @param start 开始时间
   * @return DateCell
   */
  public static DateCell setEndDate(DatePicker start) {
    return new DateCell() {
      @Override
      public void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty);
        if (start.getValue() == null && item.isBefore(LocalDate.now())) {
          setDisable(true);
          setStyle("-fx-background-color: #AAAAAA");
        }
        if (start.getValue() != null && item.isBefore(start.getValue())) {
          setDisable(true);
          setStyle("-fx-background-color: #AAAAAA");
        }
      }
    };
  }

  /**
   * 关闭/展开全部节点
   *
   * @param root     根节点
   * @param isExpand 关闭/展开
   */
  public static <T> void expandedAll(TreeItem<T> root, boolean isExpand) {
    if (root.getChildren() != null && !root.getChildren().isEmpty()) {
      root.getChildren().forEach(item -> {
        item.setExpanded(isExpand);
        expandedAll(item, isExpand);
      });
    }
  }

  /**
   * 搜索节点
   *
   * @param result 结果集
   * @param root   根节点
   * @param fun    匹配方法
   */
  public static <T> void findChild(List<TreeItem<T>> result, TreeItem<T> root, Function<T, Boolean> fun) {
    if (fun.apply(root.getValue())) {
      result.add(root);
    }
    if (root.getChildren() != null && !root.getChildren().isEmpty()) {
      for (TreeItem<T> treeItem : root.getChildren()) {
        findChild(result, treeItem, fun);
      }
    }
  }

  /**
   * 查找子节点
   *
   * @param root 根节点
   * @param fun  匹配方法
   * @return 找到的第一个子节点，未找到返回null
   */
  public static <T> TreeItem<T> findChild(TreeItem<T> root, Function<T, Boolean> fun) {
    if (fun.apply(root.getValue())) {
      return root;
    }
    if (root.getChildren() != null && !root.getChildren().isEmpty()) {
      for (TreeItem<T> treeItem : root.getChildren()) {
        TreeItem<T> item = findChild(treeItem, fun);
        if (item != null) {
          return item;
        }
      }
    }
    return null;
  }

  /**
   * 生成Label
   *
   * @param name label内容
   * @param hPos h定位
   * @param vPos v定位
   * @return 生成的label
   */
  public static Label getLabel(String name, HPos hPos, VPos vPos) {
    Label l = new Label(name);
    if (hPos != null) {
      GridPane.setHalignment(l, hPos);
    }
    if (vPos != null) {
      GridPane.setValignment(l, vPos);
    }
    return l;
  }

  /**
   * 生成TextField
   *
   * @param grid     GridPane
   * @param editable 是否可编辑
   * @param name     名称
   * @param val      默认值
   * @param h        h定位
   * @param v        v定位
   * @param rowIndex 行数
   * @return 生成的TextField
   */
  public static <T> TextField getTextField(GridPane grid, boolean editable, String name, T val, HPos h, VPos v, Integer rowIndex) {
    TextField text = val == null ? new TextField() : new TextField(String.valueOf(val));
    text.setEditable(editable);
    grid.add(SwingUtil.getLabel(name, h, v), 0, rowIndex);
    grid.add(text, 1, rowIndex);
    return text;
  }

  /**
   * 生成CheckBox[]
   *
   * @param grid     GridPane
   * @param title    组标题
   * @param size     每行个数
   * @param rowIndex 行数
   * @param items    内容
   * @param checked  选中项
   * @param consumer 回调
   * @return 生成的CheckBox[]
   */
  public static <T> CheckBox[] getCheckBox(GridPane grid, String title, int size, Integer rowIndex, List<T> items, List<T> checked, Consumer<List<T>> consumer) {
    GridPane pane = new GridPane();
    Label label = new Label(title);
    label.setStyle("-fx-font-size: 16px;-fx-font-weight: bold;");
    pane.add(label, 0, 0);
    CheckBox[] cbs = new CheckBox[items.size()];
    CopyOnWriteArrayList<T> list = new CopyOnWriteArrayList<>(checked);
    for (int i = 0; i < items.size(); i++) {
      CheckBox cb = cbs[i] = new CheckBox(items.get(i).toString());
      if (!list.isEmpty()) {
        cb.setSelected(list.contains(items.get(i)));
      }
      int finalI = i;
      cb.selectedProperty().addListener((observable, oldValue, newValue) -> {
        if (list.contains(items.get(finalI))) {
          list.remove(items.get(finalI));
        } else {
          list.add(items.get(finalI));
        }
        consumer.accept(list);
      });
      cb.setPadding(new Insets(0, 10, 5, 10));
      pane.add(cb, i % size, i / size + 1);
    }
    grid.add(pane, 1, rowIndex);
    return cbs;
  }

  /**
   * 生成ComboBox
   *
   * @param grid     GridPane
   * @param disable  是否可选
   * @param name     标题
   * @param items    内容
   * @param index    选中项
   * @param h        h定位
   * @param v        v定位
   * @param rowIndex 行数
   * @return 生成的ComboBox
   */
  public static <T> ComboBox<T> getComboBox(GridPane grid, int disable, String name, ObservableList<T> items, int index, HPos h, VPos v, Integer rowIndex) {
    ComboBox<T> box = new ComboBox<>();
    box.setItems(items);
    box.getSelectionModel().select(index);
    if (disable == 0) {
      box.setDisable(true);
    }
    grid.add(SwingUtil.getLabel(name, h, v), 0, rowIndex);
    grid.add(box, 1, rowIndex);
    return box;
  }

  /**
   * 生成GridPane
   *
   * @param hGap   h定位
   * @param vGap   v定位
   * @param insets 边距
   * @return 生成的GridPane
   */
  public static GridPane getGridPane(Integer hGap, Integer vGap, Insets insets) {
    GridPane grid = new GridPane();
    grid.setHgap(hGap);
    grid.setVgap(vGap);
    grid.setPadding(insets);
    return grid;
  }

  /**
   * 生成Dialog
   *
   * @param stage Stage
   * @param title 标题
   * @param btns  按钮组
   * @return 生成的Dialog
   */
  public static <T> Dialog<T> getDialog(Stage stage, String title, ButtonType... btns) {
    Dialog<T> dialog = new Dialog<>();
    dialog.initOwner(stage);
    dialog.setTitle(title);
    dialog.getDialogPane().getButtonTypes().addAll(btns);
    return dialog;
  }
}
