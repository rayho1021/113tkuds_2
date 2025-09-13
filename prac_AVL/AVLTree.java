public class AVLTree {
    private AVLNode root;
    
    // 取得節點高度
    private int getHeight(AVLNode node) {
        return (node != null) ? node.height : 0;
    }
    
    // 插入節點
    // 時間複雜度: O(log n), 空間複雜度: O(log n)
    public void insert(int data) {
        root = insertNode(root, data);
    }
    
    private AVLNode insertNode(AVLNode node, int data) {
        // 1. 標準 BST 插入
        if (node == null) {
            return new AVLNode(data);
        }
        
        if (data < node.data) {
            node.left = insertNode(node.left, data);
        } else if (data > node.data) {
            node.right = insertNode(node.right, data);
        } else {
            return node; // 重複值不插入
        }
        
        // 2. 更新高度
        node.updateHeight();
        
        // 3. 檢查平衡因子
        int balance = node.getBalance();
        
        // 4. 處理不平衡情況
        // Left Left 情況
        if (balance > 1 && data < node.left.data) {
            return AVLRotations.rightRotate(node);
        }
        
        // Right Right 情況
        if (balance < -1 && data > node.right.data) {
            return AVLRotations.leftRotate(node);
        }
        
        // Left Right 情況
        if (balance > 1 && data > node.left.data) {
            node.left = AVLRotations.leftRotate(node.left);
            return AVLRotations.rightRotate(node);
        }
        
        // Right Left 情況
        if (balance < -1 && data < node.right.data) {
            node.right = AVLRotations.rightRotate(node.right);
            return AVLRotations.leftRotate(node);
        }
        
        return node;
    }
    
    // 搜尋節點
    // 時間複雜度: O(log n), 空間複雜度: O(log n)
    public boolean search(int data) {
        return searchNode(root, data);
    }
    
    private boolean searchNode(AVLNode node, int data) {
        if (node == null) return false;
        if (data == node.data) return true;
        if (data < node.data) return searchNode(node.left, data);
        return searchNode(node.right, data);
    }
    
    // 找最小值節點
    private AVLNode findMin(AVLNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    // 刪除節點
    // 時間複雜度: O(log n), 空間複雜度: O(log n)
    public void delete(int data) {
        root = deleteNode(root, data);
    }
    
    private AVLNode deleteNode(AVLNode node, int data) {
        // 1. 標準 BST 刪除
        if (node == null) return null;
        
        if (data < node.data) {
            node.left = deleteNode(node.left, data);
        } else if (data > node.data) {
            node.right = deleteNode(node.right, data);
        } else {
            // 找到要刪除的節點
            if (node.left == null || node.right == null) {
                AVLNode temp = (node.left != null) ? node.left : node.right;
                if (temp == null) {
                    temp = node;
                    node = null;
                } else {
                    // 複製內容而不是引用
                    node.data = temp.data;
                    node.left = temp.left;
                    node.right = temp.right;
                    node.height = temp.height;
                }
            } else {
                AVLNode temp = findMin(node.right);
                node.data = temp.data;
                node.right = deleteNode(node.right, temp.data);
            }
        }
        
        if (node == null) return node;
        
        // 2. 更新高度
        node.updateHeight();
        
        // 3. 檢查平衡因子並修復
        int balance = node.getBalance();
        
        // Left Left 情況
        if (balance > 1 && node.left.getBalance() >= 0) {
            return AVLRotations.rightRotate(node);
        }
        
        // Left Right 情況
        if (balance > 1 && node.left.getBalance() < 0) {
            node.left = AVLRotations.leftRotate(node.left);
            return AVLRotations.rightRotate(node);
        }
        
        // Right Right 情況
        if (balance < -1 && node.right.getBalance() <= 0) {
            return AVLRotations.leftRotate(node);
        }
        
        // Right Left 情況
        if (balance < -1 && node.right.getBalance() > 0) {
            node.right = AVLRotations.rightRotate(node.right);
            return AVLRotations.leftRotate(node);
        }
        
        return node;
    }
    
    // 驗證是否為有效的 AVL 樹
    public boolean isValidAVL() {
        return checkAVL(root) != -1;
    }
    
    private int checkAVL(AVLNode node) {
        if (node == null) return 0;
        
        int leftHeight = checkAVL(node.left);
        int rightHeight = checkAVL(node.right);
        
        if (leftHeight == -1 || rightHeight == -1) return -1;
        
        if (Math.abs(leftHeight - rightHeight) > 1) return -1;
        
        return Math.max(leftHeight, rightHeight) + 1;
    }
    
    // 列印樹狀結構
    public void printTree() {
        printInOrder(root);
        System.out.println();
    }
    
    private void printInOrder(AVLNode node) {
        if (node != null) {
            printInOrder(node.left);
            System.out.print(node.data + "(" + node.getBalance() + ") ");
            printInOrder(node.right);
        }
    }
    
    // 視覺化顯示樹結構
    public void displayTree() {
        System.out.println("樹結構視覺化:");
        if (root == null) {
            System.out.println("空樹");
        } else {
            displayTreeHelper(root, "", true);
        }
    }
    
    private void displayTreeHelper(AVLNode node, String prefix, boolean isLast) {
        if (node != null) {
            System.out.println(prefix + (isLast ? "└── " : "├── ") + 
                             node.data + "(h:" + node.height + ",b:" + node.getBalance() + ")");
            
            if (node.left != null || node.right != null) {
                if (node.right != null) {
                    displayTreeHelper(node.right, prefix + (isLast ? "    " : "│   "), false);
                }
                if (node.left != null) {
                    displayTreeHelper(node.left, prefix + (isLast ? "    " : "│   "), true);
                }
            }
        }
    }
    
    // 取得樹的高度
    public int getTreeHeight() {
        return getHeight(root);
    }
    
    // 檢查是否為空樹
    public boolean isEmpty() {
        return root == null;
    }
    
    public static void main(String[] args) {
        System.out.println("=== AVL 樹測試 ===\n");
        
        // 創建 AVL 樹
        AVLTree tree = new AVLTree();
        
        // 測試 1: 基本插入操作
        System.out.println("測試 1: 基本插入操作");
        int[] insertValues = {50, 30, 70, 20, 40, 60, 80};
        
        for (int value : insertValues) {
            tree.insert(value);
            System.out.println("插入 " + value + " -> 樹高度: " + tree.getTreeHeight() + 
                             ", 是否有效AVL: " + tree.isValidAVL());
        }
        
        System.out.println("\n插入完成後的樹結構:");
        tree.displayTree();
        System.out.print("中序遍歷: ");
        tree.printTree();
        
        // 測試 2: 搜尋操作
        System.out.println("\n測試 2: 搜尋操作");
        int[] searchValues = {40, 25, 70, 100};
        for (int value : searchValues) {
            boolean found = tree.search(value);
            System.out.println("搜尋 " + value + ": " + (found ? "✓ 找到" : "✗ 未找到"));
        }
        
        // 測試 3: 刪除操作
        System.out.println("\n測試 3: 刪除操作");
        int[] deleteValues = {20, 30, 50};
        
        for (int value : deleteValues) {
            System.out.println("\n刪除 " + value + " 前:");
            System.out.print("中序遍歷: ");
            tree.printTree();
            System.out.println("樹高度: " + tree.getTreeHeight() + ", 是否有效AVL: " + tree.isValidAVL());
            
            tree.delete(value);
            
            System.out.println("刪除 " + value + " 後:");
            System.out.print("中序遍歷: ");
            tree.printTree();
            System.out.println("樹高度: " + tree.getTreeHeight() + ", 是否有效AVL: " + tree.isValidAVL());
        }
        
        System.out.println("\n刪除操作完成後的樹結構:");
        tree.displayTree();
        
        // 測試 4: 自動平衡驗證
        System.out.println("\n測試 4: 自動平衡驗證");
        AVLTree balanceTest = new AVLTree();
        
        System.out.println("插入遞增序列 1,2,3,4,5,6,7 測試自動平衡:");
        for (int i = 1; i <= 7; i++) {
            balanceTest.insert(i);
            System.out.println("插入 " + i + " -> 高度: " + balanceTest.getTreeHeight() + 
                             ", 平衡: " + balanceTest.isValidAVL());
        }
        
        System.out.println("\n遞增插入後的平衡樹結構:");
        balanceTest.displayTree();
        System.out.print("中序遍歷: ");
        balanceTest.printTree();
        
        // 測試 5: 複雜的插入刪除混合操作
        System.out.println("\n🔸 測試 5: 混合操作測試");
        AVLTree mixedTest = new AVLTree();
        
        // 插入一些值
        int[] mixedInsert = {100, 50, 150, 25, 75, 125, 175};
        System.out.println("插入序列: " + java.util.Arrays.toString(mixedInsert));
        for (int value : mixedInsert) {
            mixedTest.insert(value);
        }
        
        System.out.println("初始樹結構:");
        mixedTest.displayTree();
        
        // 進行一系列刪除和插入
        System.out.println("\n進行混合操作:");
        mixedTest.delete(25);
        System.out.println("刪除 25 後: 高度=" + mixedTest.getTreeHeight() + ", 平衡=" + mixedTest.isValidAVL());
        
        mixedTest.insert(200);
        System.out.println("插入 200 後: 高度=" + mixedTest.getTreeHeight() + ", 平衡=" + mixedTest.isValidAVL());
        
        mixedTest.delete(100);
        System.out.println("刪除 100 後: 高度=" + mixedTest.getTreeHeight() + ", 平衡=" + mixedTest.isValidAVL());
        
        System.out.println("\n最終樹結構:");
        mixedTest.displayTree();
        System.out.print("最終中序遍歷: ");
        mixedTest.printTree();
        
        // 測試 6: 邊界情況測試
        System.out.println("\n測試 6: 邊界情況測試");
        AVLTree edgeTest = new AVLTree();
        
        System.out.println("空樹測試:");
        System.out.println("是否為空: " + edgeTest.isEmpty());
        System.out.println("樹高度: " + edgeTest.getTreeHeight());
        System.out.println("是否有效AVL: " + edgeTest.isValidAVL());
        System.out.println("搜尋不存在值: " + edgeTest.search(999));
        
        // 單個節點測試
        edgeTest.insert(42);
        System.out.println("\n插入單個節點 42:");
        System.out.println("是否為空: " + edgeTest.isEmpty());
        System.out.println("樹高度: " + edgeTest.getTreeHeight());
        System.out.println("是否有效AVL: " + edgeTest.isValidAVL());
        edgeTest.displayTree();
        
        // 重複插入測試
        System.out.println("\n重複插入相同值 42:");
        edgeTest.insert(42);
        System.out.print("中序遍歷: ");
        edgeTest.printTree();
        
        // 刪除唯一節點
        edgeTest.delete(42);
        System.out.println("刪除唯一節點後:");
        System.out.println("是否為空: " + edgeTest.isEmpty());
        System.out.println("樹高度: " + edgeTest.getTreeHeight());
        
        // 測試 7: 性能測試
        System.out.println("\n測試 7: 性能測試");
        AVLTree performanceTest = new AVLTree();
        
        long startTime = System.nanoTime();
        
        // 插入大量隨機數據
        java.util.Random random = new java.util.Random();
        int[] testData = new int[1000];
        for (int i = 0; i < 1000; i++) {
            testData[i] = random.nextInt(10000);
            performanceTest.insert(testData[i]);
        }
        
        long insertTime = System.nanoTime();
        
        // 搜尋一些值
        int foundCount = 0;
        for (int i = 0; i < 100; i++) {
            if (performanceTest.search(testData[i])) {
                foundCount++;
            }
        }
        
        long searchTime = System.nanoTime();
        
        System.out.println("插入 1000 個隨機數耗時: " + (insertTime - startTime) / 1_000_000 + " 毫秒");
        System.out.println("搜尋 100 次耗時: " + (searchTime - insertTime) / 1_000_000 + " 毫秒");
        System.out.println("最終樹高度: " + performanceTest.getTreeHeight());
        System.out.println("理論最優高度約為: " + Math.ceil(Math.log(1000) / Math.log(2)));
        System.out.println("是否保持有效AVL: " + performanceTest.isValidAVL());
        System.out.println("搜尋成功率: " + foundCount + "/100");
        
        System.out.println("\n=== AVL 樹測試完成 ===");
    }
}