/*
練習 3：刪除操作實作
實作 AVL 樹的刪除操作，處理三種情況：
1.刪除葉子節點
2.刪除只有一個子節點的節點
3.刪除有兩個子節點的節點

- 刪除後需要重新平衡
- 找前驅或後繼節點替代
- 從刪除點往上檢查平衡
*/

public class AVLDeleteExercise {

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

        // 插入操作
        public void insert(int key) {
            this.root = insertNode(this.root, key);
        }

        private Node insertNode(Node node, int key) {
            if (node == null) {
                return new Node(key);
            }
            if (key < node.key) {
                node.left = insertNode(node.left, key);
            } else if (key > node.key) {
                node.right = insertNode(node.right, key);
            } else {
                return node;
            }

            updateHeight(node);
            int balance = getBalance(node);

            if (balance > 1 && key < node.left.key) {
                return rightRotate(node);
            }
            if (balance < -1 && key > node.right.key) {
                return leftRotate(node);
            }
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

        //  刪除操作 
        public void delete(int key) {
            this.root = deleteNode(this.root, key);
        }

        private Node deleteNode(Node node, int key) {
            // 1. 標準 BST 刪除
            if (node == null) {
                return node;
            }

            if (key < node.key) {
                node.left = deleteNode(node.left, key);
            } else if (key > node.key) {
                node.right = deleteNode(node.right, key);
            } else { // 找到要刪除的節點
                // 情況 1 & 2: 節點只有一個或沒有子節點
                if (node.left == null || node.right == null) {
                    Node temp = (node.left != null) ? node.left : node.right;
                    // 沒有子節點
                    if (temp == null) {
                        return null;
                    } else { // 只有一個子節點
                        node = temp;
                    }
                } else { // 情況 3: 節點有兩個子節點
                    Node temp = findMin(node.right);
                    node.key = temp.key;
                    node.right = deleteNode(node.right, temp.key);
                }
            }

            if (node == null) {
                return node;
            }

            // 2. 更新節點高度
            updateHeight(node);

            // 3. 檢查平衡因子並進行旋轉
            int balance = getBalance(node);

            // 左子樹過高 (LL 或 LR )
            if (balance > 1) {
                if (getBalance(node.left) >= 0) {
                    return rightRotate(node);
                } else {
                    node.left = leftRotate(node.left);
                    return rightRotate(node);
                }
            }

            // 右子樹過高 (RR 或 RL )
            if (balance < -1) {
                if (getBalance(node.right) <= 0) {
                    return leftRotate(node);
                } else {
                    node.right = rightRotate(node.right);
                    return leftRotate(node);
                }
            }
            
            return node;
        }

        private Node findMin(Node node) {
            Node current = node;
            while (current.left != null) {
                current = current.left;
            }
            return current;
        }

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

        // 建立一個有足夠節點的樹
        int[] keys = {9, 5, 10, 0, 6, 11, -1, 1, 2};
        for (int key : keys) {
            avlTree.insert(key);
        }

        System.out.println("--- 初始樹結構 ---");
        System.out.print("中序遍歷: ");
        avlTree.inOrderTraversal();
        System.out.print("前序遍歷: ");
        avlTree.preOrderTraversal();
        System.out.println("------------------------------------");

        System.out.println("\n--- 測試刪除葉子節點 (key = 11) ---");
        avlTree.delete(11);
        System.out.print("中序遍歷: ");
        avlTree.inOrderTraversal();
        System.out.print("前序遍歷: ");
        avlTree.preOrderTraversal();
        System.out.println("------------------------------------");

        System.out.println("\n--- 測試刪除只有一個子節點的節點 (key = 0) ---");
        avlTree.delete(0);
        System.out.print("中序遍歷: ");
        avlTree.inOrderTraversal();
        System.out.print("前序遍歷: ");
        avlTree.preOrderTraversal();
        System.out.println("------------------------------------");

        System.out.println("\n--- 測試刪除有兩個子節點的節點 (key = 9) ---");
        avlTree.delete(9);
        System.out.print("中序遍歷: ");
        avlTree.inOrderTraversal();
        System.out.print("前序遍歷: ");
        avlTree.preOrderTraversal();
        System.out.println("------------------------------------");
    }
}