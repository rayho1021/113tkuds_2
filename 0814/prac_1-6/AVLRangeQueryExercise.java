/*
練習 4：範圍查詢
實作範圍查詢功能，找出指定範圍內的所有元素：
```
public List<Integer> rangeQuery(int min, int max)
```
- 使用中序遍歷
- 利用 BST 性質剪枝
- 時間複雜度: O(log n + k)，k 為結果數量
*/

import java.util.ArrayList;
import java.util.List;

public class AVLRangeQueryExercise {

    // 節點類別
    static class Node {
        int key;
        int height;
        Node left;
        Node right;

        public Node(int key) {
            this.key = key;
            this.height = 1;
            this.left = null;
            this.right = null;
        }
    }

    // AVL 樹類別
    static class AVLTree {
        Node root;

        // 取得節點高度
        private int getHeight(Node node) {
            if (node == null) {
                return 0;
            }
            return node.height;
        }

        // 取得平衡因子
        private int getBalance(Node node) {
            if (node == null) {
                return 0;
            }
            return getHeight(node.left) - getHeight(node.right);
        }

        // 更新節點高度
        private void updateHeight(Node node) {
            node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        }

        // 右旋
        private Node rightRotate(Node y) {
            Node x = y.left;
            Node T2 = x.right;
            x.right = y;
            y.left = T2;
            updateHeight(y);
            updateHeight(x);
            return x;
        }

        // 左旋
        private Node leftRotate(Node x) {
            Node y = x.right;
            Node T2 = y.left;
            y.left = x;
            x.right = T2;
            updateHeight(x);
            updateHeight(y);
            return y;
        }

        // 插入
        public void insert(int key) {
            this.root = insertNode(this.root, key);
        }

        private Node insertNode(Node node, int key) {
            if (node == null) return new Node(key);
            if (key < node.key) node.left = insertNode(node.left, key);
            else if (key > node.key) node.right = insertNode(node.right, key);
            else return node;
            updateHeight(node);
            int balance = getBalance(node);
            if (balance > 1 && key < node.left.key) return rightRotate(node);
            if (balance < -1 && key > node.right.key) return leftRotate(node);
            if (balance > 1 && key > node.left.key) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
            if (balance < -1 && key < node.right.key) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
            return node;
        }

        // 範圍查詢
        public List<Integer> rangeQuery(int min, int max) {
            List<Integer> result = new ArrayList<>();
            rangeQueryRecursive(this.root, min, max, result);
            return result;
        }

        private void rangeQueryRecursive(Node node, int min, int max, List<Integer> result) {
            // 遞迴結束條件
            if (node == null) {
                return;
            }

            // 1. 剪枝：如果當前節點的值大於最小值，就可能在左子樹中找到結果
            if (min < node.key) {
                rangeQueryRecursive(node.left, min, max, result);
            }

            // 2. 檢查當前節點是否在範圍內
            if (node.key >= min && node.key <= max) {
                result.add(node.key);
            }

            // 3. 剪枝：如果當前節點的值小於最大值，就可能在右子樹中找到結果
            if (max > node.key) {
                rangeQueryRecursive(node.right, min, max, result);
            }
        }
    }

    public static void main(String[] args) {
        AVLTree avlTree = new AVLTree();

        // 插入一些節點
        int[] keys = {20, 10, 30, 5, 15, 25, 40, 2, 7, 12, 18, 22, 28, 35, 45};
        for (int key : keys) {
            avlTree.insert(key);
        }

        System.out.println("--- 樹的節點 (中序遍歷) ---");
        // 期望輸出: 2 5 7 10 12 15 18 20 22 25 28 30 35 40 45
        inOrderTraversal(avlTree.root);
        System.out.println("\n------------------------------------");

        // 測試範圍查詢
        int min = 10;
        int max = 30;
        System.out.println("--- 範圍查詢: [" + min + ", " + max + "] ---");
        List<Integer> result = avlTree.rangeQuery(min, max);
        System.out.println("結果: " + result);

        System.out.println("\n--- 範圍查詢: [1, 10] ---");
        min = 1;
        max = 10;
        result = avlTree.rangeQuery(min, max);
        System.out.println("結果: " + result);
        
        System.out.println("\n--- 範圍查詢: [30, 50] ---");
        min = 30;
        max = 50;
        result = avlTree.rangeQuery(min, max);
        System.out.println("結果: " + result);
    }
    
    // 中序遍歷印出樹
    public static void inOrderTraversal(Node node) {
        if (node != null) {
            inOrderTraversal(node.left);
            System.out.print(node.key + " ");
            inOrderTraversal(node.right);
        }
    }
}