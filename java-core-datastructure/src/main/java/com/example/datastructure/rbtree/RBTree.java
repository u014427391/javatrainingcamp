package com.example.datastructure.rbtree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class RBTree<K extends Comparable<K> , V> implements Serializable {

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

        public RBNode( K key, V value,RBNode parent) {
            this.parent = parent;
            this.k = key;
            this.v = value;
        }
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
    public void leftRotate(RBNode p) {
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
    public void rightRotate(RBNode p) {
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

    /**
     * 新增红黑树节点操作 . <br>
     * @Author mazq
     * @Date 2021/08/12 16:31
     * @Param [key, value]
     * @return void
     */
    public void put(K key, V value) {

        RBNode<K,V> parent = null ;
        RBNode<K,V> t = root;

        // 找不到root节点，将新节点作为root节点
        if (t == null) {
            root = new RBNode<>(key, value, parent);
            return;
        }

        // 找到节点作为新增节点的父节点
        while (t != null) {
            parent = t;
            int cmp = key.compareTo(t.k);
            if (cmp < 0) {
                t = t.left;
            } else {
                t = t.right;
            }
        }

        // 创建新节点，判断新节点是作为parent节点的左节点还是右节点
        RBNode<K,V> node = new RBNode<K, V>(key , value , parent);
        int comp = node.k.compareTo(parent.k);
        if (comp < 0) {
            parent.left = node;
        } else {
            parent.right = node;
        }

        // 关键，新增节点之后，红黑树的调整
        fixAfterInsertion(node);
    }

    private void fixAfterInsertion( RBNode<K,V> node) {
        node.color = RED;
        while (node != null && node != root && node.parent.color == RED) {
            if (parentOf(node) == leftOf(parentOf(parentOf(node)))) {
                RBNode<K,V> y = rightOf(parentOf(parentOf(node)));
                if (y != null && colorOf(y) == RED) {
                    setColor(parentOf(node) , BLACK);
                    setColor(y , BLACK);
                    setColor(parentOf(parentOf(node)) , RED);
                    node = parentOf(parentOf(node));
                }
                else {
                    if (node == rightOf(parentOf(node))) {
                        leftRotate(node);
                        node = parentOf(node);
                    }
                    setColor(parentOf(node) , BLACK);
                    setColor(parentOf(parentOf(node)) , RED);
                    rightRotate(node);
                }
            }
            else {
                RBNode<K,V> y = leftOf(parentOf(parentOf(node)));
                if (y != null && colorOf(y) == RED) {
                    setColor(parentOf(node) , BLACK);
                    setColor(y , BLACK);
                    setColor(parentOf(parentOf(node)) , RED);
                    node = parentOf(parentOf(node));
                }
                else {
                    if (node == leftOf(parentOf(node))) {
                        rightRotate(node);
                        node = parentOf(node);
                    }
                    setColor(parentOf(node) , BLACK);
                    setColor(parentOf(parentOf(node)) , RED);
                    leftRotate(node);
                }
            }
        }
    }

    public void deleteEntry(RBNode<K,V> node) {
        if (node.left != null && node.right != null) {
            RBNode<K,V> s = predecessor(node);
            node.k = s.k;
            node.v = s.v;
            node = s;
        }

        RBNode<K,V> replacement = node.left != null? node.left : node.right;

        if (replacement != null) {

        }
        else if (node.parent == null) {
            root = null;
        }
        else {

        }
    }

    /**
     * 查找前驱节点 <br>
     * @Author mazq
     * @Date 2021/08/12 17:17
     * @Param [node]
     * @return com.example.datastructure.rbtree.RBTree.RBNode<K,V>
     */
    private RBNode<K , V> successor(RBNode<K , V> node) {
        if (node == null) {
            return null;
        } else if (node.right != null) {
            RBNode<K , V> p = node.right;
            while(p.left != null) {
                p = p.left;
            }
            return p;
        } else {
            RBNode<K ,V> p = node.parent;
            RBNode<K , V> ch = node;
            while (p != null && ch == p.right) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }

    /**
     * 查找后继节点 <br>
     * @Author mazq
     * @Date 2021/08/12 17:17
     * @Param [node]
     * @return com.example.datastructure.rbtree.RBTree.RBNode<K,V>
     */
    private RBNode<K , V> predecessor(RBNode<K , V> node) {
        if (node == null) {
            return null;
        } else if (node.left != null) {
            RBNode<K , V> p = node.left;
            while(p.right != null) {
                p = p.right;
            }
            return p;
        } else {
            RBNode<K ,V> p = node.parent;
            RBNode<K , V> ch = node;
            while (p != null && ch == p.left) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }

    private boolean colorOf(RBNode<K , V> node) {
        return node == null ? BLACK : node.color;
    }

    private RBNode<K , V> parentOf(RBNode<K , V> node) {
        return node != null ? node.parent : null;
    }

    private RBNode<K , V> leftOf(RBNode<K , V> node) {
        return node != null ? node.left : null;
    }

    private RBNode<K , V> rightOf(RBNode<K , V> node) {
        return node != null ? node.right : null;
    }

    private void setColor(RBNode<K , V> node , boolean color) {
        if (node != null) {
            node.color = color;
        }
    }

}
