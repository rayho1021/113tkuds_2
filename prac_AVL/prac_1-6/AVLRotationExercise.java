/*
練習 2：旋轉操作實作
實作四種旋轉操作，並測試各種不平衡情況：
1.左旋
2.右旋
3.左右旋
4.右左旋

- 畫圖理解旋轉過程
- 注意更新節點高度
- 測試邊界情況
*/

public class AVLRotationExercise {

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

        /**
         * 1. 右旋 (Right Rotation)
         */
        private Node rightRotate(Node y) {
            Node x = y.left;
            Node T2 = x.right;

            // 執行旋轉
            x.right = y;
            y.left = T2;

            // 更新節點高度 (先 y，再 x)
            updateHeight(y);
            updateHeight(x);

            return x; // 回傳新的根
        }

        /**
         * 2. 左旋 (Left Rotation)
         */
        private Node leftRotate(Node x) {
            Node y = x.right;
            Node T2 = y.left;

            // 執行旋轉
            y.left = x;
            x.right = T2;

            // 更新節點高度 (先 x，再 y)
            updateHeight(x);
            updateHeight(y);

            return y; // 回傳新的根
        }

        // 插入節點的主要邏輯
        public void insert(int key) {
            this.root = insertNode(this.root, key);
        }

        private Node insertNode(Node node, int key) {
            // 1. 標準 BST 插入
            if (node == null) {
                return new Node(key);
            }
            if (key < node.key) {
                node.left = insertNode(node.left, key);
            } else if (key > node.key) {
                node.right = insertNode(node.right, key);
            } else {
                return node; // 不允許插入重複的 key
            }

            // 2. 更新當前節點的高度
            updateHeight(node);

            // 3. 取得平衡因子，判斷是否需要旋轉
            int balance = getBalance(node);

            // 4. 判斷並執行旋轉
            // 左-左 (LL) 情況: 右旋
            if (balance > 1 && key < node.left.key) {
                System.out.println("進行右旋 (LL)");
                return rightRotate(node);
            }

            // 右-右 (RR) 情況: 左旋
            if (balance < -1 && key > node.right.key) {
                System.out.println("進行左旋 (RR)");
                return leftRotate(node);
            }

            // 3. 左右旋 (LR) 情況: 先左旋再右旋
            if (balance > 1 && key > node.left.key) {
                System.out.println("進行左右旋 (LR)");
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }

            // 4. 右左旋 (RL) 情況: 先右旋再左旋
            if (balance < -1 && key < node.right.key) {
                System.out.println("進行右左旋 (RL)");
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }

            return node;
        }

        // 中序遍歷印出樹
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

        // 前序遍歷印出樹
        public void preOrderTraversal() {
            preOrder(this.root);
            System.out.println();
        }

        private void preOrder(Node node) {
            if (node != null) {
                System.out.print(node.key + " ");
                preOrder(node.left);
                preOrder(node.right);
            }
        }
    }

    public static void main(String[] args) {
        AVLTree avlTree = new AVLTree();

        System.out.println("--- 測試右旋 ---");
        avlTree.insert(30);
        avlTree.insert(20);
        avlTree.insert(10);
        System.out.print("中序遍歷 (應為 10 20 30): ");
        avlTree.inOrderTraversal();
        System.out.print("前序遍歷 (應為 20 10 30): ");
        avlTree.preOrderTraversal();
        System.out.println("根節點: " + avlTree.root.key);
        System.out.println("------------------------------------");

        AVLTree avlTree2 = new AVLTree();
        System.out.println("\n--- 測試左旋 ---");
        avlTree2.insert(10);
        avlTree2.insert(20);
        avlTree2.insert(30);
        System.out.print("中序遍歷 (應為 10 20 30): ");
        avlTree2.inOrderTraversal();
        System.out.print("前序遍歷 (應為 20 10 30): ");
        avlTree2.preOrderTraversal();
        System.out.println("根節點: " + avlTree2.root.key);
        System.out.println("------------------------------------");

        AVLTree avlTree3 = new AVLTree();
        System.out.println("\n--- 測試左右旋 ---");
        avlTree3.insert(30);
        avlTree3.insert(10);
        avlTree3.insert(20);
        System.out.print("中序遍歷 (應為 10 20 30): ");
        avlTree3.inOrderTraversal();
        System.out.print("前序遍歷 (應為 20 10 30): ");
        avlTree3.preOrderTraversal();
        System.out.println("根節點: " + avlTree3.root.key);
        System.out.println("------------------------------------");

        AVLTree avlTree4 = new AVLTree();
        System.out.println("\n--- 測試右左旋 ---");
        avlTree4.insert(10);
        avlTree4.insert(30);
        avlTree4.insert(20);
        System.out.print("中序遍歷 (應為 10 20 30): ");
        avlTree4.inOrderTraversal();
        System.out.print("前序遍歷 (應為 20 10 30): ");
        avlTree4.preOrderTraversal();
        System.out.println("根節點: " + avlTree4.root.key);
        System.out.println("------------------------------------");
    }
}