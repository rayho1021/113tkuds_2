import java.util.*;

public class M07_BinaryTreeLeftView {

    // 二元樹節點類
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
    
    /**
     * 使用 BFS 遍歷，每層第一個節點即為該層左視圖
     */
    public static List<Integer> getLeftView(TreeNode root) {
        List<Integer> leftView = new ArrayList<>();
        
        if (root == null) {
            return leftView;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            
            // 處理當前層的所有節點
            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();
                
                // 每層第一個節點就是左視圖節點
                if (i == 0) {
                    leftView.add(current.val);
                }
                
                // 將下一層節點加入佇列
                if (current.left != null) {
                    queue.offer(current.left);
                }
                if (current.right != null) {
                    queue.offer(current.right);
                }
            }
        }
        
        return leftView;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 讀取節點數量
        System.out.print("節點數量 n : ");
        int n = Integer.parseInt(scanner.nextLine().trim());
        
        // 讀取層序遍歷陣列
        System.out.print("層序遍歷數據 (用空格分隔，-1 表示 null) : ");
        String[] input = scanner.nextLine().trim().split("\\s+");
        int[] levelOrder = new int[n];
        
        for (int i = 0; i < n; i++) {
            levelOrder[i] = Integer.parseInt(input[i]);
        }
        
        // 建立二元樹
        TreeNode root = buildTree(levelOrder);
        
        // 獲取左視圖
        List<Integer> leftView = getLeftView(root);
        
        // 輸出結果
        System.out.print("LeftView:");
        for (int val : leftView) {
            System.out.print(" " + val);
        }
        System.out.println();
        
        scanner.close();
    }
}