import java.util.*;

public class M09_AVLValidate {
    
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }
    
    // 從層序遍歷陣列建立二元樹
    public static TreeNode buildTree(int[] levelOrder) { // levelOrder: 層序遍歷陣列，-1 表示 null
        if (levelOrder == null || levelOrder.length == 0 || levelOrder[0] == -1) {
            return null;
        }
        
        TreeNode root = new TreeNode(levelOrder[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        int index = 1;
        
        while (!queue.isEmpty() && index < levelOrder.length) {
            TreeNode current = queue.poll();
            
            // 處理左子節點
            if (index < levelOrder.length) {
                if (levelOrder[index] != -1) {
                    current.left = new TreeNode(levelOrder[index]);
                    queue.offer(current.left);
                }
                index++;
            }
            
            // 處理右子節點
            if (index < levelOrder.length) {
                if (levelOrder[index] != -1) {
                    current.right = new TreeNode(levelOrder[index]);
                    queue.offer(current.right);
                }
                index++;
            }
        }
        
        return root;
    }
    
    /*
     * Time Complexity: O(n)
     * 說明：遍歷每個節點一次，每個節點檢查其值是否在有效範圍內
     *       每次遞迴只做常數時間的比較操作，總時間複雜度為 O(n)
     */

    // 檢查是否為有效的 BST
    public static boolean isValidBST(TreeNode root) {
        return isValidBSTHelper(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private static boolean isValidBSTHelper(TreeNode node, long min, long max) {
        if (node == null) {
            return true;
        }
        
        // 檢查當前節點值是否在有效範圍內
        if (node.val <= min || node.val >= max) {
            return false;
        }
        
        // 遞迴檢查左右子樹
        // 左子樹：上界更新為當前值
        // 右子樹：下界更新為當前值
        return isValidBSTHelper(node.left, min, node.val) && 
               isValidBSTHelper(node.right, node.val, max);
    }
    
    /*
     * Time Complexity: O(n)
     * 說明：使用後序遍歷，每個節點被訪問一次，每次計算高度和平衡因子為常數時間
     *       如果發現不平衡節點立即返回 -1，總時間複雜度為 O(n)
     */
    
    // 檢查是否為有效的 AVL
    public static boolean isValidAVL(TreeNode root) {
        return getHeightAndCheckAVL(root) != -1;
    }
    
    /**
     * 獲取樹高並檢查 AVL 性質的遞迴方法
     */
    private static int getHeightAndCheckAVL(TreeNode node) {
        if (node == null) {
            return 0;  // 空樹高度為 0
        }
        
        // 遞迴檢查左子樹
        int leftHeight = getHeightAndCheckAVL(node.left);
        if (leftHeight == -1) {
            return -1;  // 左子樹已經不是 AVL
        }
        
        // 遞迴檢查右子樹
        int rightHeight = getHeightAndCheckAVL(node.right);
        if (rightHeight == -1) {
            return -1;  // 右子樹已經不是 AVL
        }
        
        // 計算平衡因子
        int balanceFactor = Math.abs(leftHeight - rightHeight);
        
        // 檢查平衡因子是否 ≤ 1
        if (balanceFactor > 1) {
            return -1;  // 當前節點不滿足 AVL 性質
        }
        
        // 返回當前節點的樹高
        return Math.max(leftHeight, rightHeight) + 1;
    }
    
    /*
     * Time Complexity: O(n)
     * 說明：先進行 BST 檢查 O(n)，再進行 AVL 檢查 O(n)，總時間複雜度為 O(n)
     *       如果不是 BST 會提早結束，如果是 BST 但不是 AVL 也會在發現不平衡時提早結束
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 讀取節點數量
        System.out.print("節點數量 n : ");
        int n = Integer.parseInt(scanner.nextLine().trim());
        
        // 讀取層序遍歷陣列
        System.out.print("層序遍歷數據 (用空格分隔，-1表示null) : ");
        String[] input = scanner.nextLine().trim().split("\\s+");
        int[] levelOrder = new int[n];
        
        for (int i = 0; i < n; i++) {
            levelOrder[i] = Integer.parseInt(input[i]);
        }
        
        // 建立二元樹
        TreeNode root = buildTree(levelOrder);
        
        // 檢查是否為有效 BST
        if (!isValidBST(root)) {
            System.out.println("Invalid BST");
            scanner.close();
            return;
        }
        
        // 檢查是否為有效 AVL
        if (!isValidAVL(root)) {
            System.out.println("Invalid AVL");
            scanner.close();
            return;
        }
        
        // 同時滿足 BST 和 AVL
        System.out.println("Valid");
        
        scanner.close();
    }
}