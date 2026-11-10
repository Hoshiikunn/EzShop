package cn.hoshiikunn.ezshop.utils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 树形结构工具类
 */
public final class TreeUtils {

    private TreeUtils() {
    }

    /**
     * 将扁平集合组装为树结构
     *
     * @param source         扁平节点集合
     * @param idGetter       节点ID获取方法
     * @param parentIdGetter 父节点ID获取方法
     * @param addChild       将子节点挂载到父节点的方法
     * @param rootPredicate  判断根节点的条件
     * @param comparator     节点排序规则
     * @return 树形结构的根节点集合
     */
    public static <T, K> List<T> build(
            Collection<T> source,
            Function<T, K> idGetter,
            Function<T, K> parentIdGetter,
            BiConsumer<T, T> addChild,
            Predicate<K> rootPredicate,
            Comparator<T> comparator
    ) {
        // 先排序，保证父节点和子节点的展示顺序
        List<T> ordered = source.stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        // 建立节点索引，便于查找父节点
        Map<K, T> nodeMap = new HashMap<>();
        for (T node : ordered) {
            nodeMap.put(idGetter.apply(node), node);
        }

        List<T> roots = new ArrayList<>();

        // 根节点放入结果集，子节点挂载到对应父节点
        for (T node : ordered) {
            K parentId = parentIdGetter.apply(node);

            if (rootPredicate.test(parentId)) {
                roots.add(node);
                continue;
            }

            T parent = nodeMap.get(parentId);
            if (parent != null) {
                addChild.accept(parent, node);
            }
        }

        return roots;
    }
}
