package cn.hoshiikunn.ezshop.tree;

import java.util.ArrayList;
import java.util.List;

public interface TreeNode<T extends TreeNode<T>> {

    Long getId();

    Long getPid();

    List<T> getChildren();

    void setChildren(List<T> children);

    default void addChild(T child) {
        List<T> children = getChildren();
        if (children == null) {
            children = new ArrayList<>();
            setChildren(children);
        }
        children.add(child);
    }
}