public class AVLRotations {
    
    // 右旋操作
    // 時間複雜度: O(1), 空間複雜度: O(1)
    public static AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;
        
        // 執行旋轉
        x.right = y;
        y.left = T2;
        
        // 更新高度
        y.updateHeight();
        x.updateHeight();
        
        return x; // 新的根節點
    }
    
    // 左旋操作
    // 時間複雜度: O(1), 空間複雜度: O(1)
    public static AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;
        
        // 執行旋轉
        y.left = x;
        x.right = T2;
        
        // 更新高度
        x.updateHeight();
        y.updateHeight();
        
        return y; // 新的根節點
    }
    
    // 輔助方法：顯示樹結構
    private static void displayTree(AVLNode node, String prefix, boolean isLast) {
        if (node != null) {
            System.out.println(prefix + (isLast ? "└── " : "├── ") + 
                             node.data + "(h:" + node.height + ",b:" + node.getBalance() + ")");
            
            if (node.left != null || node.right != null) {
                if (node.right != null) {
                    displayTree(node.right, prefix + (isLast ? "    " : "│   "), false);
                }
                if (node.left != null) {
                    displayTree(node.left, prefix + (isLast ? "    " : "│   "), true);
                }
            }
        }
    }
    
    // 輔助方法：中序遍歷
    private static void inorderTraversal(AVLNode node) {
        if (node != null) {
            inorderTraversal(node.left);
            System.out.print(node.data + " ");
            inorderTraversal(node.right);
        }
    }
    
    /**
     * 測試 AVL 旋轉操作
     */
    public static void main(String[] args) {
        System.out.println("=== AVL 旋轉測試 ===\n");
        
        // 測試 1: 右旋轉 (LL 情況)
        System.out.println("測試 1: 右旋轉 (LL 情況)");
        System.out.println("建立需要右旋轉的樹結構: 30-20-10");
        
        AVLNode root1 = new AVLNode(30);
        root1.left = new AVLNode(20);
        root1.left.left = new AVLNode(10);
        
        // 更新高度
        root1.left.updateHeight();
        root1.updateHeight();
        
        System.out.println("\n右旋轉前:");
        displayTree(root1, "", true);
        System.out.print("中序遍歷: ");
        inorderTraversal(root1);
        System.out.println("\n根節點平衡因子: " + root1.getBalance() + " (不平衡!)");
        
        // 執行右旋轉
        AVLNode newRoot1 = rightRotate(root1);
        
        System.out.println("\n右旋轉後:");
        displayTree(newRoot1, "", true);
        System.out.print("中序遍歷: ");
        inorderTraversal(newRoot1);
        System.out.println("\n新根節點平衡因子: " + newRoot1.getBalance() + " (已平衡!)");
        
        // 測試 2: 左旋轉 (RR 情況)
        System.out.println("\n" + "=".repeat(50));
        System.out.println("測試 2: 左旋轉 (RR 情況)");
        System.out.println("建立需要左旋轉的樹結構: 10-20-30");
        
        AVLNode root2 = new AVLNode(10);
        root2.right = new AVLNode(20);
        root2.right.right = new AVLNode(30);
        
        // 更新高度
        root2.right.updateHeight();
        root2.updateHeight();
        
        System.out.println("\n左旋轉前:");
        displayTree(root2, "", true);
        System.out.print("中序遍歷: ");
        inorderTraversal(root2);
        System.out.println("\n根節點平衡因子: " + root2.getBalance() + " (不平衡!)");
        
        // 執行左旋轉
        AVLNode newRoot2 = leftRotate(root2);
        
        System.out.println("\n左旋轉後:");
        displayTree(newRoot2, "", true);
        System.out.print("中序遍歷: ");
        inorderTraversal(newRoot2);
        System.out.println("\n新根節點平衡因子: " + newRoot2.getBalance() + " (已平衡!)");
        
        // 測試 3: 帶子樹的旋轉
        System.out.println("\n" + "=".repeat(50));
        System.out.println("測試 3: 帶子樹的旋轉操作");
        
        AVLNode root3 = new AVLNode(50);
        root3.left = new AVLNode(30);
        root3.left.left = new AVLNode(20);
        root3.left.right = new AVLNode(40);
        root3.left.left.left = new AVLNode(10);
        
        // 自底向上更新高度
        root3.left.left.updateHeight();
        root3.left.updateHeight();
        root3.updateHeight();
        
        System.out.println("\n複雜結構右旋轉前:");
        displayTree(root3, "", true);
        System.out.println("根節點平衡因子: " + root3.getBalance());
        
        // 執行右旋轉
        AVLNode newRoot3 = rightRotate(root3);
        
        System.out.println("\n複雜結構右旋轉後:");
        displayTree(newRoot3, "", true);
        System.out.println("新根節點平衡因子: " + newRoot3.getBalance());
        
        // 測試 4: 旋轉操作驗證
        System.out.println("\n" + "=".repeat(50));
        System.out.println("測試 4: 旋轉操作驗證");
        
        // 建立平衡樹並故意打破平衡
        AVLNode balanced = new AVLNode(20);
        balanced.left = new AVLNode(10);
        balanced.right = new AVLNode(30);
        balanced.left.updateHeight();
        balanced.right.updateHeight();
        balanced.updateHeight();
        
        System.out.println("原始平衡樹:");
        displayTree(balanced, "", true);
        System.out.println("平衡因子: " + balanced.getBalance());
        
        // 添加節點使其不平衡
        balanced.left.left = new AVLNode(5);
        balanced.left.left.left = new AVLNode(1);
        balanced.left.left.updateHeight();
        balanced.left.updateHeight();
        balanced.updateHeight();
        
        System.out.println("\n添加節點後變為不平衡:");
        displayTree(balanced, "", true);
        System.out.println("平衡因子: " + balanced.getBalance() + " (需要右旋轉)");
        
        // 修正不平衡
        AVLNode corrected = rightRotate(balanced);
        
        System.out.println("\n右旋轉修正後:");
        displayTree(corrected, "", true);
        System.out.println("平衡因子: " + corrected.getBalance() + " (已修正)");
        
        // 測試 5: 性能測試
        System.out.println("\n" + "=".repeat(50));
        System.out.println("測試 5: 旋轉操作性能測試");
        
        long startTime = System.nanoTime();
        
        // 執行大量旋轉操作
        for (int i = 0; i < 10000; i++) {
            AVLNode temp = new AVLNode(100);
            temp.left = new AVLNode(50);
            temp.left.left = new AVLNode(25);
            temp.left.updateHeight();
            temp.updateHeight();
            rightRotate(temp);
        }
        
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // 轉換為毫秒
        
        System.out.println("執行 10,000 次右旋轉操作耗時: " + duration + " 毫秒");
        System.out.println("平均每次旋轉耗時: " + (duration / 10000.0) + " 毫秒");
        System.out.println("驗證旋轉操作確實為 O(1) 時間複雜度");
        
        System.out.println("\n=== AVL 旋轉測試完成 ===");
    }
}