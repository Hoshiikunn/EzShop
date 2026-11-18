package cn.hoshiikunn.ezshop.tree;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 树形结构工具类
 */
public final class TreeUtils {

    private TreeUtils() {
    }

    /**
     * 将扁平树节点列表组装为树形结构
     * <p>
     * 不对节点排序，保持传入列表的原始顺序
     *
     * @param nodes 扁平树节点列表
     * @param <T>   树节点类型
     * @return 根节点集合
     * @throws IllegalArgumentException 节点 ID 重复时抛出
     */
    public static <T extends TreeNode<T>> List<T> buildTree(List<T> nodes) {
        // 不排序，保持传入顺序
        return buildTree(nodes, null);
    }

    /**
     * 将扁平树节点列表组装为树形结构
     * <p>
     * 传入比较器时先按规则排序；不传时保持传入列表的原始顺序
     * <p>
     * {@code pid} 为空或 {@code 0} 的节点视为根节点；
     * 父节点不存在时忽略该节点；
     * 节点 ID 重复时快速失败
     *
     * @param nodes      扁平树节点列表
     * @param comparator 节点排序规则，可为 {@code null}
     * @param <T>        树节点类型
     * @return 根节点集合
     * @throws IllegalArgumentException 节点 ID 重复时抛出
     */
    public static <T extends TreeNode<T>> List<T> buildTree(
            List<T> nodes,
            Comparator<T> comparator
    ) {
        // 空集合直接返回空树
        if (nodes == null || nodes.isEmpty()) {
            return Collections.emptyList();
        }

        // 按指定规则排序，不传时保持传入顺序
        List<T> ordered = comparator == null
                ? new ArrayList<>(nodes)
                : nodes.stream().sorted(comparator).collect(Collectors.toList());

        // 建立 ID 与节点的映射
        Map<Long, T> nodeById = new HashMap<>();
        for (T node : ordered) {
            // 校验节点 ID 唯一
            T old = nodeById.putIfAbsent(node.getId(), node);
            if (old != null) {
                throw new IllegalArgumentException("树节点 ID 重复: " + node.getId());
            }
        }

        // 组装根节点和子节点
        List<T> roots = new ArrayList<>();
        for (T node : ordered) {
            Long pid = node.getPid();

            // pid 为空或 0 表示根节点
            if (pid == null || pid == 0L) {
                roots.add(node);
                continue;
            }

            // 父节点存在则挂载，不存在则忽略
            T parent = nodeById.get(pid);
            if (parent != null) {
                parent.addChild(node);
            }
        }

        return roots;
    }
}
