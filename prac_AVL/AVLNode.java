public class AVLNode {
    int data;
    AVLNode left, right;
    int height;
    
    public AVLNode(int data) {
        this.data = data;
        this.height = 1;
    }
    
    // 計算平衡因子
    public int getBalance() {
        int leftHeight = (left != null) ? left.height : 0;
        int rightHeight = (right != null) ? right.height : 0;
        return leftHeight - rightHeight;
    }
    
    // 更新節點高度
    public void updateHeight() {
        int leftHeight = (left != null) ? left.height : 0;
        int rightHeight = (right != null) ? right.height : 0;
        this.height = Math.max(leftHeight, rightHeight) + 1;
    }
    
    public static void main(String[] args) {
        System.out.println("=== AVL 節點測試 ===\n");
        
        // 測試 1: 創建單個節點
        System.out.println("測試 1: 創建單個節點");
        AVLNode root = new AVLNode(50);
        System.out.println("根節點數據: " + root.data);
        System.out.println("初始高度: " + root.height);
        System.out.println("初始平衡因子: " + root.getBalance());
        
        // 測試 2: 添加左子節點
        System.out.println("\n測試 2: 添加左子節點");
        root.left = new AVLNode(30);
        System.out.println("左子節點數據: " + root.left.data);
        System.out.println("左子節點高度: " + root.left.height);
        System.out.println("左子節點平衡因子: " + root.left.getBalance());
        
        // 更新根節點高度
        root.updateHeight();
        System.out.println("更新後根節點高度: " + root.height);
        System.out.println("更新後根節點平衡因子: " + root.getBalance());
        
        // 測試 3: 添加右子節點
        System.out.println("\n測試 3: 添加右子節點");
        root.right = new AVLNode(70);
        System.out.println("右子節點數據: " + root.right.data);
        System.out.println("右子節點高度: " + root.right.height);
        
        // 更新根節點高度和平衡因子
        root.updateHeight();
        System.out.println("添加右子節點後根節點高度: " + root.height);
        System.out.println("添加右子節點後根節點平衡因子: " + root.getBalance());
        
        // 測試 4: 創建更深的樹結構
        System.out.println("\n測試 4: 創建更深的樹結構");
        
        // 在左子節點下添加子節點
        root.left.left = new AVLNode(20);
        root.left.right = new AVLNode(40);
        
        // 在右子節點下添加子節點
        root.right.left = new AVLNode(60);
        root.right.right = new AVLNode(80);
        
        // 自底向上更新高度
        root.left.updateHeight();
        root.right.updateHeight();
        root.updateHeight();
        
        System.out.println("樹結構建立完成:");
        System.out.println("根節點 (50) - 高度: " + root.height + ", 平衡因子: " + root.getBalance());
        System.out.println("├── 左子節點 (30) - 高度: " + root.left.height + ", 平衡因子: " + root.left.getBalance());
        System.out.println("│   ├── (20) - 高度: " + root.left.left.height + ", 平衡因子: " + root.left.left.getBalance());
        System.out.println("│   └── (40) - 高度: " + root.left.right.height + ", 平衡因子: " + root.left.right.getBalance());
        System.out.println("└── 右子節點 (70) - 高度: " + root.right.height + ", 平衡因子: " + root.right.getBalance());
        System.out.println("    ├── (60) - 高度: " + root.right.left.height + ", 平衡因子: " + root.right.left.getBalance());
        System.out.println("    └── (80) - 高度: " + root.right.right.height + ", 平衡因子: " + root.right.right.getBalance());
        
        // 測試 5: 測試不平衡情況
        System.out.println("\n測試 5: 測試不平衡情況");
        AVLNode unbalanced = new AVLNode(10);
        unbalanced.right = new AVLNode(20);
        unbalanced.right.right = new AVLNode(30);
        
        // 自底向上更新高度
        unbalanced.right.updateHeight();
        unbalanced.updateHeight();
        
        System.out.println("不平衡樹範例 (右傾):");
        System.out.println("根節點 (10) - 高度: " + unbalanced.height + ", 平衡因子: " + unbalanced.getBalance());
        System.out.println("└── 右子節點 (20) - 高度: " + unbalanced.right.height + ", 平衡因子: " + unbalanced.right.getBalance());
        System.out.println("    └── 右子節點 (30) - 高度: " + unbalanced.right.right.height + ", 平衡因子: " + unbalanced.right.right.getBalance());
        
        if (Math.abs(unbalanced.getBalance()) > 1) {
            System.out.println("檢測到不平衡! 根節點平衡因子: " + unbalanced.getBalance());
            System.out.println("需要執行旋轉操作來維持 AVL 特性");
        }
        
        // 測試 6: 邊界情況測試
        System.out.println("\n測試 6: 邊界情況測試");
        AVLNode singleNode = new AVLNode(100);
        System.out.println("單個節點:");
        System.out.println("數據: " + singleNode.data);
        System.out.println("高度: " + singleNode.height);
        System.out.println("平衡因子: " + singleNode.getBalance());
        
        // 測試空子節點情況
        AVLNode nodeWithOneChild = new AVLNode(200);
        nodeWithOneChild.left = new AVLNode(150);
        nodeWithOneChild.updateHeight();
        
        System.out.println("\n只有左子節點的節點:");
        System.out.println("根節點數據: " + nodeWithOneChild.data);
        System.out.println("高度: " + nodeWithOneChild.height);
        System.out.println("平衡因子: " + nodeWithOneChild.getBalance());
        
        System.out.println("\n=== 完成 ===");
    }
}