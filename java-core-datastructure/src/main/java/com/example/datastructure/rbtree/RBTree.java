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
            // 不管p是否存在父节点，我们都设置p的父节点也为 pr的父节点
            pr.parent = p.parent;
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
            pl.parent = p.parent;
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

    /**
     * 新增节点之后，红黑树的调整操作 <br>
     * @Date 2021/08/12 17:36
     * @Param [node]
     * @return void
     */
    private void fixAfterInsertion( RBNode<K,V> node) {
        node.color = RED;
        // 父节点是红色的，才需要调整，黑色节点直接新增就行
        while (node != null && node != root && node.parent.color == RED) {
            // 父节点是祖父节点的左节点
            if (parentOf(node) == leftOf(parentOf(parentOf(node)))) {
                // 找到叔叔节点
                RBNode<K,V> y = rightOf(parentOf(parentOf(node)));

                // case1 : 叔叔节点也是红色
                if (y != null && colorOf(y) == RED) {
                    setColor(parentOf(node) , BLACK);
                    setColor(y , BLACK);
                    setColor(parentOf(parentOf(node)) , RED);
                    node = parentOf(parentOf(node));
                }
                else {

                    // case2 ： 叔叔节点是黑色，且新增节点是右子节点
                    if (node == rightOf(parentOf(node))) {
                        // 将父节点和新增节点调换
                        node = parentOf(node);
                        // 从父节点处做左旋
                        leftRotate(node);
                    }

                    // case 3 : 叔叔节点是黑色，且新增节点是左子节点
                    setColor(parentOf(node) , BLACK);
                    setColor(parentOf(parentOf(node)) , RED);
                    rightRotate(parentOf(parentOf(node)));
                }
            }
            else {
                // 找到叔叔节点
                RBNode<K,V> y = leftOf(parentOf(parentOf(node)));

                // case1 : 叔叔节点也是红色
                if (y != null && colorOf(y) == RED) {
                    setColor(parentOf(node) , BLACK);
                    setColor(y , BLACK);
                    setColor(parentOf(parentOf(node)) , RED);
                    node = parentOf(parentOf(node));
                }
                else {

                    // case2 ： 叔叔节点是黑色，且新增节点是左子节点
                    if (node == leftOf(parentOf(node))) {
                        node = parentOf(node);
                        rightRotate(node);
                    }

                    // case 3 : 叔叔节点是黑色，且新增节点是右子节点
                    setColor(parentOf(node) , BLACK);
                    setColor(parentOf(parentOf(node)) , RED);
                    leftRotate(parentOf(parentOf(node)));
                }
            }
        }
        // root节点肯定是黑色
        root.color = BLACK;
    }

    /**
     * 根据key移除节点 <br>
     * @Date 2021/08/13 10:28
     * @Param [key]
     * @return V
     */
    public V remove(K key){
        // 1. 根据需要删除的key 找到对应的Node节点
        RBNode node = getNode(key);
        if(node == null){
            // 不存在
            return null;
        }
        V oldValue = (V) node.v;
        // 具体删除节点的方法
        deleteEntry(node);
        return oldValue;
    }

    /**
     * 根据key找到对应node <br>
     * @Date 2021/08/13 10:29
     * @Param [key]
     * @return com.example.datastructure.rbtree.RBTree.RBNode
     */
    private RBNode getNode(K key) {
        RBNode node = this.root;
        while(node != null){
            int cmp = key.compareTo((K) node.k);
            if(cmp < 0){
                node = node.left;
            }else if(cmp > 0){
                node = node.right;
            }else{
                // 表示找到了对应的节点
                return node;
            }
        }
        return null;
    }


    /**
     * 删除节点操作. <br>
     *     有三种情况：
     *     1：删除叶子节点，直接删除
     *     2：删除有一个子节点的情况，找到替换节点
     *     3：如果删除的节点右两个子节点，此时需要找到前驱节点或者后继节点
     * @Date 2021/08/12 17:35
     * @Param [node]
     * @return void
     */
    public void deleteEntry(RBNode<K,V> node) {

        // 3、node节点有两个子节点的情况，找到前驱节点，复制前驱节点的元素给node节点，同时改变指针
        if (node.left != null && node.right != null) {
            RBNode<K,V> s = predecessor(node);
            node.k = s.k;
            node.v = s.v;
            node = s;
        }

        // 2、删除有一个子节点的情况找到替换节点
        RBNode<K,V> replacement = node.left != null? node.left : node.right;
        if (replacement != null) {
            // 改变指针
            replacement.parent = node.parent;
            if (node.parent == null ){
                // node是root节点
                root = replacement;
            }
            else if (node == node.parent.left){
                // 替换为左节点
                node.parent.left = replacement;
            }
            else {
                // 替换为右节点
                node.parent.right = replacement;
            }
            // 指针都指向null，等待GC
            node.left = node.right = node.parent = null;
            // 红黑树平衡
            if (node.color == BLACK) {
                fixAfterDeletion(replacement);
            }
        }
        else if (node.parent == null) {
            // 说明要删除的是root节点
            root = null;
        }
        else {
            // 1、node节点是叶子节点

            // 先调整
            if (node.color == BLACK) {
                fixAfterDeletion(node);
            }
            // 再删除
            if (node.parent != null) {
                if (node == node.parent.left) {
                    node.parent.left = null;
                } else if (node == node.parent.right) {
                    node.parent.right = null;
                }
                node.parent = null;
            }

        }
    }

    /**
     * 删除节点之后，红黑树的调整操作 <br>
     * @Date 2021/08/12 17:36
     * @Param [node]
     * @return void
     */
    private void fixAfterDeletion (RBNode<K,V> node) {
        while (node != root && colorOf(node) == BLACK) {
            // 是左子节点的情况
            if (node == leftOf(parentOf(node))) {
                // 找到兄弟节点
                RBNode<K,V> sib = rightOf(parentOf(node));
                // case1: node的兄弟节点是红色的
                if (colorOf(sib) == RED) {
                    setColor(sib , BLACK);
                    setColor(parentOf(node) , RED);
                    leftRotate(parentOf(node));
                    // 找到真正的兄弟节点
                    sib = rightOf(parentOf(node));
                }
                // case2: node的兄弟节点是黑色的，且两个子节点也都是黑色的
                if (colorOf(leftOf(node)) == BLACK && colorOf(rightOf(node)) == BLACK) {
                    // 情况比较复杂
                    setColor(sib , RED);
                    // 往上递归
                    node = parentOf(node);
                }
                else {
                    //case3: node的兄弟节点是黑色的，且左子节点是红色，右子节点是黑色
                    if (colorOf(rightOf(sib)) == BLACK) {
                        // 这种情况需要变色，同时右旋
                        setColor(sib , RED);
                        setColor(leftOf(sib) , BLACK);
                        rightRotate(sib);
                        // 重新调整兄弟节点
                        sib = rightOf(parentOf(node));
                    }
                    //case4: node的兄弟节点是黑色的，且右子节点是红色，左子节点任意颜色
                    setColor(sib , colorOf(parentOf(node)));
                    setColor(parentOf(node) , BLACK);
                    setColor(rightOf(sib) , BLACK);
                    leftRotate(parentOf(node));
                    // 跳出循环
                    node = root;
                }

            }

            else { // 与上面逻辑对称
                // 找到兄弟节点
                RBNode<K,V> sib = leftOf(parentOf(node));
                // Case 1: node的兄弟是红色的
                if (colorOf(sib) == RED) {
                    setColor(sib , BLACK);
                    setColor(parentOf(node) , RED);
                    rightRotate(parentOf(node));
                    // 找到真正的兄弟节点
                    sib = leftOf(parentOf(node));
                }
                // Case 2: node的兄弟是黑色，且的俩个子节点都是黑色的
                if (colorOf(rightOf(node)) == BLACK && colorOf(leftOf(node)) == BLACK) {
                    // 情况比较复杂
                    setColor(sib , RED);
                    // 往上递归
                    node = parentOf(node);
                }
                else {
                    // Case 3: node的兄弟是黑色的，并且左子节点是红色，右子节点为黑色
                    if (colorOf(leftOf(sib)) == BLACK) {
                        setColor(sib , RED);
                        setColor(rightOf(sib) , BLACK);
                        leftRotate(sib);
                        // 重新调整叔叔节点的位置
                        sib = leftOf(parentOf(node));
                    }
                    // Case 4: node的兄弟是黑色的；并且左子节点是红色的，右子节点任意颜色
                    setColor(sib , colorOf(parentOf(node)));
                    setColor(parentOf(node) , BLACK);
                    setColor(leftOf(sib) , BLACK);
                    rightRotate(parentOf(node));
                    // 跳出循环
                    node = root;
                }
            }
        }
        // 替代节点是红色节点，直接涂黑
        setColor(node , BLACK);
    }

    /**
     * 查找后继节点，先定位到右节点，然后往左查找，找到最小值<br>
     * @Author mazq
     * @Date 2021/08/12 17:17
     * @Param [node]
     * @return com.example.datastructure.rbtree.RBTree.RBNode<K,V>
     */
    private RBNode<K , V> successor(RBNode<K , V> node) {
        if (node == null) {
            return null;
        } else if (node.right != null) {
            // 取到右节点
            RBNode<K , V> p = node.right;
            // 往左查找，找到最小值
            while(p.left != null) {
                p = p.left;
            }
            return p;
        } else {
            // 比较少见的情况，该节点没有右子节点，往上遍历
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
     * 查找前驱节点 <br>
     * @Author mazq
     * @Date 2021/08/12 17:17
     * @Param [node]
     * @return com.example.datastructure.rbtree.RBTree.RBNode<K,V>
     */
    private RBNode<K , V> predecessor(RBNode<K , V> node) {
        if (node == null) {
            return null;
        } else if (node.left != null) {
            // 找到左节点
            RBNode<K , V> p = node.left;
            // 往右查找，找到最大值
            while(p.right != null) {
                p = p.right;
            }
            return p;
        } else {
            // 比较少见的情况，该节点没有左子节点，往上遍历
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
