/*
練習 1：實作基本操作
實作一個簡化版的 AVL 樹，包含以下功能：
1. 插入節點
2. 搜尋節點
3. 計算樹的高度
4. 檢查是否為有效的 AVL 樹

- 先實作標準 BST，再加入平衡檢查
- 使用遞迴方式計算高度
- 檢查每個節點的平衡因子是否在 [-1, 1] 範圍內
*/

public class AVLBasicExercise {

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

        // 1. 插入節點
        public void insert(int key) {
            this.root = insertNode(this.root, key);
        }

        private Node insertNode(Node node, int key) {
            // 標準 BST 插入
            if (node == null) {
                return new Node(key);
            }
            if (key < node.key) {
                node.left = insertNode(node.left, key);
            } else if (key > node.key) {
                node.right = insertNode(node.right, key);
            } else {
                // 不允許插入重複的 key
                return node;
            }

            // 更新節點高度
            node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

            return node;
        }

        // 2. 搜尋節點
        public boolean search(int key) {
            return searchNode(this.root, key);
        }

        private boolean searchNode(Node node, int key) {
            if (node == null) {
                return false;
            }
            if (node.key == key) {
                return true;
            }
            if (key < node.key) {
                return searchNode(node.left, key);
            } else {
                return searchNode(node.right, key);
            }
        }

        // 3. 計算樹的高度
        public int getTreeHeight() {
            return getHeight(this.root);
        }

        // 取得節點的高度
        private int getHeight(Node node) {
            if (node == null) {
                return 0;
            }
            return node.height;
        }

        // 4. 檢查是否為有效的 AVL 樹
        public boolean isAVL() {
            return isAVLRecursive(this.root);
        }

        private boolean isAVLRecursive(Node node) {
            if (node == null) {
                return true;
            }

            // 檢查平衡因子
            int balanceFactor = getBalanceFactor(node);
            if (Math.abs(balanceFactor) > 1) {
                return false;
            }

            // 遞迴檢查左右子樹
            return isAVLRecursive(node.left) && isAVLRecursive(node.right);
        }

        // 取得平衡因子
        private int getBalanceFactor(Node node) {
            if (node == null) {
                return 0;
            }
            return getHeight(node.left) - getHeight(node.right);
        }

        // 印出樹（中序遍歷）
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
        AVLTree avlTree = new AVLTree();

        System.out.println("--- 插入節點 ---");
        avlTree.insert(10);
        avlTree.insert(20);
        avlTree.insert(30);
        avlTree.insert(40);
        avlTree.insert(50);
        avlTree.inOrderTraversal();

        System.out.println("\n--- 檢查 AVL 性質 ---");
        System.out.println("樹的高度: " + avlTree.getTreeHeight());
        System.out.println("此樹是否為 AVL: " + avlTree.isAVL()); // 由於沒有平衡，此時應為 false

        System.out.println("\n--- 搜尋節點 ---");
        int keyToSearch = 30;
        System.out.println("搜尋 " + keyToSearch + ": " + avlTree.search(keyToSearch));
        keyToSearch = 99;
        System.out.println("搜尋 " + keyToSearch + ": " + avlTree.search(keyToSearch));
    }
}