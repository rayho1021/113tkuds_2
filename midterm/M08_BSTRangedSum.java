import java.util.*;


public class M08_BSTRangedSum {
    
    // 二元搜尋樹節點類別
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
    
    /**
     * 從層序遍歷陣列建立二元搜尋樹
     */
    public static TreeNode buildTree(int[] levelOrder) { // 層序遍歷陣列，-1 表示 null
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
     * 計算 BST 中落在區間 [L, R] 內的節點值總和
     */
    public static int rangedSum(TreeNode root, int L, int R) {
        // 空節點
        if (root == null) {
            return 0;
        }
        
        int currentVal = root.val;
        int sum = 0;
        
        if (currentVal < L) {
            // 當前節點值小於 L，左子樹都小於 L，只需搜尋右子樹
            sum = rangedSum(root.right, L, R);
        } else if (currentVal > R) {
            // 當前節點值大於 R，右子樹都大於 R，只需搜尋左子樹
            sum = rangedSum(root.left, L, R);
        } else {
            // 當前節點值在區間 [L, R] 內
            sum = currentVal + rangedSum(root.left, L, R) + rangedSum(root.right, L, R);
        }
        
        return sum;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 讀取節點數量
        System.out.print("\n節點數量 n : ");
        int n = Integer.parseInt(scanner.nextLine().trim());
        
        // 讀取層序遍歷陣列
        System.out.print("層序遍歷數據 (用空格分隔，-1 表示 null) : ");
        String[] input = scanner.nextLine().trim().split("\\s+");
        int[] levelOrder = new int[n];
        
        for (int i = 0; i < n; i++) {
            levelOrder[i] = Integer.parseInt(input[i]);
        }
        
        // 讀取區間 [L, R]
        System.out.print("區間 L R : ");
        String[] range = scanner.nextLine().trim().split("\\s+");
        int L = Integer.parseInt(range[0]);
        int R = Integer.parseInt(range[1]);
        
        // 建立 BST
        TreeNode root = buildTree(levelOrder);
        
        // 計算區間總和
        int sum = rangedSum(root, L, R);
        
        // 輸出
        System.out.println("Sum: " + sum + "\n");
        
        scanner.close();
    }
}