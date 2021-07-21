package com.example.datastructure.rbtree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class RBTree implements Serializable {

    private final static boolean RED = false;
    private final static boolean BLACK = true;

    private RBNode root;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class RBNode<K extends Comparable<K> , V> {
        private RBNode parent;
        private RBNode left;
        private RBNode right;
        private boolean color;
        private K k;
        private V v;
    }

    /**
     *  左旋示意图：围绕p进行左旋
     *      r                 r
     *     /                /
     *    p               pr
     * /   \            /   \
     * pl   pr   =>    p    rr
     *    /  \       /  \
     *   rl   rr    pl  rl
     *  左旋算法分步进行：
     *  1. 将pr节点的左子节点更新为p的右子节点，pr有左子节点时，将p赋给pr左子节点rl的父节点
     *  2. p有父节点r时，将p的父节点赋给pr的父节点，同时更新r的左子节点或者右子节点为pr
     *  3. 将pr的左子节点设为p，将p的父节点设为pr
     * @param
     */
    private void leftRotate(RBNode p) {
        if(p != null) {
            // 1. 将pr节点的左子节点更新为p的右子节点，pr有左子节点时，将p赋给pr左子节点rl的父节点
            // 获取pr节点，也即为p的右节点
            RBNode pr = p.right;
            // 将pr节点的左子节点更新为p的右子节点
            p.right = pr.left;
            //pr有左子节点时，将p赋给pr左子节点rl的父节点
            if (pr.left != null) {
                pr.left.parent = p;
            }

            // 2. p有父节点r时，将p的父节点赋给pr的父节点，同时更新r的左子节点或者右子节点为pr
            if (p.parent == null) {
                // 直接设置root节点为pr
                this.root = pr;
            } else {
                // 有r根节点的情况
                if (p == p.parent.left) {
                    // 原来的p节点为r的左节点的情况
                    p.parent.left = pr;
                } else {
                    // 原来的p节点为r的右节点的情况
                    p.parent.right = pr;
                }
            }

            // 3. 将pr的左子节点设为p，将p的父节点设为pr
            pr.left = p;
            p.parent = pr;
        }
    }

    /**
     *  右旋示意图：围绕p进行右旋
     *                r                 r
     *              /                 /
     *            p                  pl
     *          /   \             /   \
     *        pl   pr      =>   rl     p
     *      /  \                     /   \
     *     rl  rr                   rr   pr
     *  右旋算法分步进行：
     *  1. 将pl节点的右子节点更新为p的左子节点，pl有右子节点时，将p赋给pl右子节点rr的父节点
     *  2. p有父节点r时，将p的父节点赋给pl的父节点，同时更新r的左子节点或者右子节点为pl
     *  3. 将pl的右子节点设为p，将p的父节点设为pl
     * @param
     */
    private void rightRotate(RBNode p) {
        if(p != null) {
            // 1. 将pl节点的右子节点更新为p的左子节点，pl有右子节点时，将p赋给pl右子节点rr的父节点
            // 获取pl节点，也即为p的左节点
            RBNode pl = p.left;
            // 将pl节点的右子节点更新为p的左子节点
            p.left = pl.right;
            //pl有右子节点时，将p赋给pl右子节点rl的父节点
            if (pl.right != null) {
                pl.right.parent = p;
            }

            // 2. p有父节点r时，将p的父节点赋给pl的父节点，同时更新r的左子节点或者右子节点为pl
            if (p.parent == null) {
                // 直接设置root节点为pr
                this.root = pl;
            } else {
                // 有r根节点的情况
                if (p == p.parent.right) {
                    // 原来的p节点为r的右节点的情况
                    p.parent.right = pl;
                } else {
                    // 原来的p节点为r的左节点的情况
                    p.parent.right = pl;
                }
            }

            // 3. 将pl的右子节点设为p，将p的父节点设为pl
            pl.right = p;
            p.parent = pl;
        }
    }

}
