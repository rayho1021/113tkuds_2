/*
練習 6：持久化 AVL 樹
實作支援版本控制的 AVL 樹，每次修改產生新版本：
1.插入操作產生新版本
2.可查詢歷史版本
3.版本間共享不變節點

- 使用路徑複製
- 節點設為不可變
- 空間複雜度優化
*/

import java.util.HashMap;
import java.util.Map;

public class PersistentAVLExercise {

    // 節點類別
    static class Node {
        final int key;
        final int height;
        final Node left;
        final Node right;

        Node(int key, Node left, Node right) {
            this.key = key;
            this.left = left;
            this.right = right;
            this.height = 1 + Math.max(getHeight(left), getHeight(right));
        }

        private static int getHeight(Node node) {
            return (node == null) ? 0 : node.height;
        }
        public Node withLeft(Node newLeft) {
            return new Node(this.key, newLeft, this.right);
        }
        public Node withRight(Node newRight) {
            return new Node(this.key, this.left, newRight);
        }
    }

    // AVL 樹類別
    static class AVLTree {
        private Node root;

        public AVLTree() {
            this.root = null;
        }

        private AVLTree(Node root) {
            this.root = root;
        }

        // 取得節點高度
        private static int getHeight(Node node) {
            return (node == null) ? 0 : node.height;
        }

        // 取得平衡因子
        private static int getBalance(Node node) {
            return (node == null) ? 0 : getHeight(node.left) - getHeight(node.right);
        }

        // 右旋
        private Node rightRotate(Node y) {
            Node x = y.left;
            Node T2 = x.right;
            return x.withRight(y.withLeft(T2));
        }

        // 左旋
        private Node leftRotate(Node x) {
            Node y = x.right;
            Node T2 = y.left;
            return y.withLeft(x.withRight(T2));
        }

        // 插入，返回一個新的 AVLTree 
        public AVLTree insert(int key) {
            Node newRoot = insertNode(this.root, key);
            return new AVLTree(newRoot);
        }

        private Node insertNode(Node node, int key) {
            if (node == null) {
                return new Node(key, null, null);
            }
            if (key == node.key) {
                return node;
            }
            if (key < node.key) {
                Node newLeft = insertNode(node.left, key);
                node = node.withLeft(newLeft);
            } else {
                Node newRight = insertNode(node.right, key);
                node = node.withRight(newRight);
            }

            int balance = getBalance(node);

            if (balance > 1 && key < node.left.key) {
                return rightRotate(node);
            }
            if (balance < -1 && key > node.right.key) {
                return leftRotate(node);
            }
            if (balance > 1 && key > node.left.key) {
                Node newLeft = leftRotate(node.left);
                return rightRotate(node.withLeft(newLeft));
            }
            if (balance < -1 && key < node.right.key) {
                Node newRight = rightRotate(node.right);
                return leftRotate(node.withRight(newRight));
            }

            return node;
        }

        // 中序遍歷 
        public void inOrderTraversal() {
            inOrder(this.root);
            System.out.println();
        }

        private void inOrder(Node node) {
            if (node != null) {
                inOrder(node.left);
                System.out.print(node.key + " ");
                inOrder(node.right);
            }
        }
    }

    public static void main(String[] args) {
        // 使用 Map 來儲存不同版本的 AVL 樹
        Map<Integer, AVLTree> versions = new HashMap<>();

        // 版本 0
        AVLTree v0 = new AVLTree();
        versions.put(0, v0);
        System.out.println("版本 0 (空樹):");
        v0.inOrderTraversal();
        System.out.println("------------------------------------");

        // 版本 1：插入 10
        AVLTree v1 = v0.insert(10);
        versions.put(1, v1);
        System.out.println("\n版本 1: 插入 10");
        v1.inOrderTraversal();
        System.out.println("------------------------------------");

        // 版本 2：從版本 1 插入 20
        AVLTree v2 = v1.insert(20);
        versions.put(2, v2);
        System.out.println("\n版本 2: 插入 20");
        v2.inOrderTraversal();
        System.out.println("------------------------------------");

        // 版本 3：從版本 2 插入 30 (左旋)
        AVLTree v3 = v2.insert(30);
        versions.put(3, v3);
        System.out.println("\n版本 3: 插入 30 (觸發左旋)");
        v3.inOrderTraversal();
        System.out.println("------------------------------------");
        
        // 檢查歷史版本
        System.out.println("\n--- 檢查歷史版本 ---");
        System.out.println("查詢版本 0:");
        versions.get(0).inOrderTraversal(); 
        System.out.println("查詢版本 1:");
        versions.get(1).inOrderTraversal(); 
        System.out.println("查詢版本 2:");
        versions.get(2).inOrderTraversal(); 
        System.out.println("查詢版本 3:");
        versions.get(3).inOrderTraversal(); 
        System.out.println("------------------------------------");
    }
}