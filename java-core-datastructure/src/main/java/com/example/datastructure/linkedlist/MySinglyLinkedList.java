package com.example.datastructure.linkedlist;


/**
 * 单链表java代码实现
 * @date 2021/10/31
 */
public class MySinglyLinkedList<T> {

    public Node<T> head;

    private static class Node<T> {
        public T data;
        public Node next;
        Node(T data) {
            this.data = data;
            next = null;
        }
    }

    /**
     * 链表新增节点，直接附加到链表最后面
     * @Date 2021/08/04 13:54
     * @Param [list, data]
     * @return void
     */
    public static <T> void add(MySinglyLinkedList<T> list , T data) {
        Node<T> newNode = new Node<T>(data);

        // 单链表是empty的，将新增的节点直接作为head节点
        if (list.head == null) {
            list.head = newNode;
        }
        else { // 其它情况直接在链表后面附加上新节点
            Node<T> currentNode = list.head;
            while (currentNode.next != null) {
                currentNode = currentNode.next;
            }
            // 将新增的节点作为最后一个节点
            currentNode.next = newNode;
        }
    }

    /**
     * 在指定前驱节点后面附加新节点 <br>
     * @Date 2021/08/04 14:26
     * @Param prevNode 前驱节点
     * @Param data 新增节点数据
     * @return void
     */
    public static <T> void addAfter(Node<T> prevNode , T data) {
        if (prevNode == null) {
            System.out.println("前驱节点不能为null");
        }
        // 原本链表0->2，创建新节点1
        Node<T> newNode = new Node<T>(data);
        // 将新节点的指针指向前驱节点原本的next节点 1->2
        newNode.next = prevNode.next;
        // 将前驱节点的next指针指向新节点 0->1
        prevNode.next = newNode;
    }

    /**
     * 指定下标移除链表节点 <br>
     * @Date 2021/08/04 14:34
     * @Param [list, pos]
     * @return void
     */
    public static <T> void removeAtPosition(MySinglyLinkedList<T> list , int pos) {
        // 当前节点
        Node<T> currentNode = list.head;
        // 记录前一个节点
        Node<T> prevNode = null;
        // 下标计数器
        int counter = 0;
        if (currentNode != null) {
            // 移除第一个节点
            if (pos == 0) {
                // 指针移动，节点往后移，再将下一个节点置为head节点
                list.head = currentNode.next;
            }
            else {
                while (currentNode.next != null) {
                    // 找到移除节点要节点位置
                    if (counter == pos) {
                        prevNode.next = currentNode.next;
                        break;
                    }
                    else {
                        // 记录前驱节点
                        prevNode = currentNode;
                        // 往后遍历
                        currentNode = currentNode.next;
                        // 记录指针位置
                        counter++;
                    }
                }

            }
        }

    }

    /**
     * 通过数据找到节点，移除节点 <br>
     * @Date 2021/08/04 15:12
     * @return void
     */
    public static <T> void removeByValue(MySinglyLinkedList<T> list , T data) {
        Node<T> currentNode = list.head;
        Node<T> prevNode = null;
        if (currentNode != null) {
            // 第一个节点
            if (currentNode.data == data) {
                // 节点移动，改变head节点
                list.head = currentNode.next;
            }
            else {
                while (currentNode != null && currentNode.data != data) {
                    prevNode = currentNode;
                    currentNode = currentNode.next;
                }
                // 找到了节点
                if (currentNode != null) {
                    // 节点移动
                    prevNode.next = currentNode.next;
                    System.out.println(String.format("移除节点：%s" , data));
                }
                else { // 找不到对应节点
                    System.out.println("遍历不到对应data的节点");
                }
            }
        }
    }

    /**
     * 打印单链表数据 <br>
     * @Date 2021/08/04 16:24
     * @Param [list]
     * @return void
     */
    public static <T> void printLinkedList(MySinglyLinkedList<T> list) {
        Node<T> currentNode = list.head;
        while (currentNode != null) {
            if (currentNode.next != null) {
                System.out.print(String.format("%s -> ", currentNode.data));
            }
            else {
                System.out.print(String.format("%s", currentNode.data));
            }
            currentNode = currentNode.next;
        }
        System.out.println();
    }

    /**
     * 查询单链表<br>
     * @Date 2021/08/04 16:24
     * @Param [head, data]
     * @return boolean
     */
    public static <T> boolean search(Node<T> head, T data) {
        if (head == null) {
            return false;
        }
        if (head.data == data) {
            return true;
        }
        // 递归查找
        return search(head.next , data);
    }

    /**
     * 统计单遍历节点数量 <br>
     * @Date 2021/08/04 16:25
     * @Param [list]
     * @return int
     */
    public static <T> int size(MySinglyLinkedList<T> list) {
        int counter = 0;
        Node<T> currentNode = list.head;
        while (currentNode.next != null) {
            currentNode = currentNode.next;
            counter ++;
        }
        return counter;
    }

    public static MySinglyLinkedList<Integer> buildLinedList() {
        MySinglyLinkedList<Integer> list = new MySinglyLinkedList<Integer>();
        MySinglyLinkedList.add(list , 0);
        MySinglyLinkedList.add(list , 1);
        MySinglyLinkedList.add(list , 2);
        MySinglyLinkedList.add(list , 3);
        MySinglyLinkedList.add(list , 4);
        MySinglyLinkedList.add(list , 5);
        MySinglyLinkedList.add(list , 6);
        MySinglyLinkedList.add(list , 7);
        MySinglyLinkedList.add(list , 8);
        MySinglyLinkedList.add(list , 9);
        MySinglyLinkedList.add(list , 10);
        return list;
    }


    public static void main(String[] args) {
        MySinglyLinkedList<Integer> list = buildLinedList();

        System.out.println(String.format("linked list size : %s" , size(list)));
        // 打印验证，0 -> 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> 10
        printLinkedList(list);

        // 在head节点的下一个节点后面加上新节点
        addAfter(list.head.next , 11);
        // 打印验证，0 -> 1 -> 11 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> 10
        printLinkedList(list);

        // 重置链表
        list = buildLinedList();
        // 在对应位置移除链表节点
        removeAtPosition(list , 1);
        // 打印验证，0 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> 10
        printLinkedList(list);

        // 重置链表
        list = buildLinedList();
        // 根据数据移除链表节点
        removeByValue(list , 10);
        // 打印验证 ，0 -> 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9
        printLinkedList(list);

        // 重置链表
        list = buildLinedList();
        Node headNode = list.head;
        if (search(headNode , 5)) {
            System.out.println("查找到对应的节点");
        }
        else {
            System.out.println("不能查找到对应节点");
        }

    }

}

