package org.weizidong.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 *
 * @author WeiZiDong
 */
public class ReflectUtil {

  private ReflectUtil() {
  }

  /**
   * 通过字段名从对象或对象的父类中得到字段的值
   *
   * @param object    对象实例
   * @param fieldName 字段名
   * @return 字段对应的值
   */
  public static Object getValue(Object object, String fieldName) {
    if (object == null) {
      return null;
    }
    if (StringUtils.isBlank(fieldName)) {
      return null;
    }
    Field field;
    Class<?> clazz = object.getClass();
    for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
      try {
        field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
      } catch (Exception e) {
        // 这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
        // 如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
      }
    }
    return null;
  }

  /**
   * 通过字段名从对象或对象的父类中得到字段的值（调用字典的get方法）
   *
   * @param object    对象实例
   * @param fieldName 字段名
   * @return 字段对应的值
   */
  public static Object getValueOfGet(Object object, String fieldName) {
    if (object == null) {
      return null;
    }
    if (StringUtils.isBlank(fieldName)) {
      return null;
    }
    Field field;
    Class<?> clazz = object.getClass();
    for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
      try {
        field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
        // 获得get方法
        Method getMethod = pd.getReadMethod();
        // 执行get方法返回一个Object
        return getMethod.invoke(object);
      } catch (Exception e) {
        // 这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
        // 如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
      }
    }
    return null;
  }

  /**
   * 通过字段名从对象或对象的父类中得到字段的值（调用字典的get方法，可以取出复杂的对象的值）
   *
   * @param object    对象实例
   * @param fieldName 字段名,如：operatorUser.name、operatorUser.org.name，递归调用
   * @return 字段对应的值
   */
  public static Object getValueOfGetIncludeObjectFeild(Object object, String fieldName) {
    if (object == null) {
      return null;
    }
    if (StringUtils.isBlank(fieldName)) {
      return null;
    }
    Field field;
    Class<?> clazz = object.getClass();
    for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
      try {
        if (fieldName.contains(".")) {
          // 如：operatorUser.name、operatorUser.org.name，递归调用
          String[] splitFiledName = fieldName.split("\\.");
          return getValueOfGetIncludeObjectFeild(getValueOfGetIncludeObjectFeild(object, splitFiledName[0]), splitFiledName[1]);
        }
        field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
        // 获得get方法
        Method getMethod = pd.getReadMethod();
        // 执行get方法返回一个Object
        return getMethod.invoke(object);
      } catch (Exception e) {
        // 这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
        // 如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
      }
    }
    return null;
  }

  public static void setProperty(Object o, String name, String v) {
    try {
      // 得到类中的所有属性集合
      Field[] fields = o.getClass().getDeclaredFields();
      for (Field field : fields) {
        //设置些属性是可以访问的
        field.setAccessible(true);
        if (StringUtils.equals(name, field.getName())) {
          field.set(name, cover(v, field.getType()));
        }
      }
    } catch (IllegalAccessException e) {
      String type = "unknow";
      try {
        type = o.getClass().getField(name).getType().getName();
      } catch (NoSuchFieldException ignored) {
      }
      LogUtil.error(ReflectUtil.class, e, "Object class: {0}  properties: {1}  Type: {2}  setProperty: {3}", o.getClass().getName(), name, type, v == null ? "null" : v);
    }
  }

  /**
   * 将字符串转换为对应的值
   */
  private static Object cover(String v, Class type) {
    if (type == Integer.class || type == int.class) {
      return Integer.valueOf(v);
    } else if (type == Long.class || type == long.class) {
      return Long.valueOf(v);
    } else if (type == Byte.class || type == byte.class) {
      return Byte.valueOf(v);
    } else if (type == Boolean.class || type == boolean.class) {
      return Boolean.valueOf(v);
    } else if (type == Double.class || type == double.class) {
      return Double.valueOf(v);
    } else if (type == Float.class || type == float.class) {
      return Float.valueOf(v);
    } else {
      return v;
    }
  }
}
