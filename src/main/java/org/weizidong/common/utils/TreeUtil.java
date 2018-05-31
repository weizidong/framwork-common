package org.weizidong.common.utils;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 树型结构递归嵌套工具
 *
 * @author WeiZiDong
 */
public class TreeUtil {

    /**
     * 构建节点树
     *
     * @param list      节点列表
     * @param predicate 父节点过滤函数
     * @param fun       子节点过滤函数
     * @return 生成完成的节点树
     */
    public static <T> List<T> bulid(List<T> list, Predicate<T> predicate, Function<T, List<T>> fun) {
        List<T> trees = list.stream().filter(predicate).collect(Collectors.toList());
        trees.forEach(t -> findChildren(t, fun));
        return trees;
    }

    /**
     * 查找子节点
     *
     * @param t   当前节点
     * @param fun 查找函数
     * @return 获取到的子节点
     */
    private static <T> List<T> findChildren(T t, Function<T, List<T>> fun) {
        List<T> list = fun.apply(t);
        if (list != null && list.size() > 0) {
            list.forEach(it -> findChildren(it, fun));
        }
        return list;
    }
}
