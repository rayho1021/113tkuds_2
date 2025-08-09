/*
練習 3.9：BST轉換與平衡
實作BST的轉換和平衡操作：
1. 將BST轉換為排序的雙向鏈結串列
2. 將排序陣列轉換為平衡的BST
3. 檢查BST是否平衡，並計算平衡因子
4. 將BST中每個節點的值改為所有大於等於該節點值的總和
*/
import java.util.*;

public class BSTConversionAndBalance {
    
    // 二元樹節點類別
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        public TreeNode(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }
    
    // 平衡資訊類別
    static class BalanceInfo {
        private boolean isBalanced;
        private int height;
        private int balanceFactor;
        private List<NodeBalanceInfo> nodeInfos;
        
        public BalanceInfo() {
            this.isBalanced = true;
            this.height = 0;
            this.balanceFactor = 0;
            this.nodeInfos = new ArrayList<>();
        }
        
        public boolean isBalanced() { return isBalanced; }
        public int getHeight() { return height; }
        public List<NodeBalanceInfo> getNodeInfos() { return nodeInfos; }
        
        public void setImbalanced() { this.isBalanced = false; }
        public void setHeight(int height) { this.height = height; }
        
        public void displayReport() {
            System.out.println("=== 平衡性檢查報告 ===");
            System.out.println("樹是否平衡: " + (isBalanced ? "是" : "否"));
            System.out.println("樹的高度: " + height);
            
            if (!isBalanced) {
                System.out.println("不平衡節點:");
                for (NodeBalanceInfo info : nodeInfos) {
                    if (Math.abs(info.balanceFactor) > 1) {
                        System.out.println("  節點 " + info.nodeValue + 
                                         ": 平衡因子 = " + info.balanceFactor + 
                                         ", 高度 = " + info.height);
                    }
                }
            }
            
            System.out.println("所有節點的平衡因子:");
            for (NodeBalanceInfo info : nodeInfos) {
                System.out.println("  節點 " + info.nodeValue + 
                                 ": 平衡因子 = " + info.balanceFactor + 
                                 ", 高度 = " + info.height);
            }
        }
    }
    
    // 節點平衡資訊類別
    static class NodeBalanceInfo {
        int nodeValue;
        int height;
        int balanceFactor;
        
        public NodeBalanceInfo(int nodeValue, int height, int balanceFactor) {
            this.nodeValue = nodeValue;
            this.height = height;
            this.balanceFactor = balanceFactor;
        }
    }
    
    // BST轉換核心類別
    static class BSTTransformer {
        
        /**
         * 將BST轉換為排序的雙向鏈結串列
         * 使用left指標作為prev，right指標作為next
         */
        public static TreeNode convertToDoublyLinkedList(TreeNode root) {
            if (root == null) {
                return null;
            }
            
            TreeNode[] prev = new TreeNode[1]; // 使用陣列包裝以支援參照傳遞
            TreeNode[] head = new TreeNode[1];
            
            convertToDoublyLinkedListHelper(root, prev, head);
            
            return head[0];
        }
        
        private static void convertToDoublyLinkedListHelper(TreeNode node, TreeNode[] prev, TreeNode[] head) {
            if (node == null) {
                return;
            }
            
            // 處理左子樹
            convertToDoublyLinkedListHelper(node.left, prev, head);
            
            // 處理當前節點
            if (prev[0] == null) {
                // 第一個節點，設定為頭節點
                head[0] = node;
            } else {
                // 建立雙向連接
                prev[0].right = node;
                node.left = prev[0];
            }
            prev[0] = node;
            
            // 處理右子樹
            convertToDoublyLinkedListHelper(node.right, prev, head);
        }
        
        /**
         * 將排序陣列轉換為平衡的BST
         */
        public static TreeNode sortedArrayToBST(int[] nums) {
            if (nums == null || nums.length == 0) {
                return null;
            }
            
            return sortedArrayToBSTHelper(nums, 0, nums.length - 1);
        }
        
        private static TreeNode sortedArrayToBSTHelper(int[] nums, int left, int right) {
            if (left > right) {
                return null;
            }
            
            // 選擇中點作為根節點
            int mid = left + (right - left) / 2;
            TreeNode root = new TreeNode(nums[mid]);
            
            // 遞迴建構左右子樹
            root.left = sortedArrayToBSTHelper(nums, left, mid - 1);
            root.right = sortedArrayToBSTHelper(nums, mid + 1, right);
            
            return root;
        }
        
        /**
         * 檢查BST是否平衡，並計算平衡因子
         */
        public static BalanceInfo checkBalance(TreeNode root) {
            BalanceInfo result = new BalanceInfo();
            
            if (root == null) {
                return result;
            }
            
            checkBalanceHelper(root, result);
            
            return result;
        }
        
        private static int checkBalanceHelper(TreeNode node, BalanceInfo result) {
            if (node == null) {
                return 0;
            }
            
            // 遞迴計算左右子樹高度
            int leftHeight = checkBalanceHelper(node.left, result);
            int rightHeight = checkBalanceHelper(node.right, result);
            
            // 計算當前節點的平衡因子
            int balanceFactor = leftHeight - rightHeight;
            int currentHeight = Math.max(leftHeight, rightHeight) + 1;
            
            // 記錄節點資訊
            result.nodeInfos.add(new NodeBalanceInfo(node.val, currentHeight, balanceFactor));
            
            // 檢查是否平衡
            if (Math.abs(balanceFactor) > 1) {
                result.setImbalanced();
            }
            
            // 更新整體高度
            result.setHeight(Math.max(result.getHeight(), currentHeight));
            
            return currentHeight;
        }
        
        /**
         * 將BST中每個節點的值改為所有大於等於該節點值的總和
         */
        public static void convertToGreaterSumTree(TreeNode root) {
            if (root == null) {
                return;
            }
            
            int[] sum = new int[1]; // 累加器
            convertToGreaterSumTreeHelper(root, sum);
        }
        
        private static void convertToGreaterSumTreeHelper(TreeNode node, int[] sum) {
            if (node == null) {
                return;
            }
            
            // 反向中序遍歷：右 -> 根 -> 左
            convertToGreaterSumTreeHelper(node.right, sum);
            
            // 更新當前節點值
            sum[0] += node.val;
            node.val = sum[0];
            
            convertToGreaterSumTreeHelper(node.left, sum);
        }
        
        /**
         * 獲取BST的中序遍歷結果
         */
        public static List<Integer> getInorderTraversal(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            inorderTraversalHelper(root, result);
            return result;
        }
        
        private static void inorderTraversalHelper(TreeNode node, List<Integer> result) {
            if (node != null) {
                inorderTraversalHelper(node.left, result);
                result.add(node.val);
                inorderTraversalHelper(node.right, result);
            }
        }
        
        /**
         * 複製樹結構（用於保護原始樹）
         */
        public static TreeNode copyTree(TreeNode root) {
            if (root == null) {
                return null;
            }
            
            TreeNode newNode = new TreeNode(root.val);
            newNode.left = copyTree(root.left);
            newNode.right = copyTree(root.right);
            
            return newNode;
        }
    }
    
    // 雙向鏈結串列工具類別
    static class DoublyLinkedListUtils {
        
        /**
         * 顯示雙向鏈結串列（正向）
         */
        public static void displayForward(TreeNode head) {
            if (head == null) {
                System.out.println("空鏈結串列");
                return;
            }
            
            System.out.print("正向遍歷: ");
            TreeNode current = head;
            while (current != null) {
                System.out.print(current.val);
                if (current.right != null) {
                    System.out.print(" ↔ ");
                }
                current = current.right;
            }
            System.out.println();
        }
        
        /**
         * 顯示雙向鏈結串列（反向）
         */
        public static void displayBackward(TreeNode head) {
            if (head == null) {
                System.out.println("空鏈結串列");
                return;
            }
            
            // 找到尾節點
            TreeNode tail = head;
            while (tail.right != null) {
                tail = tail.right;
            }
            
            System.out.print("反向遍歷: ");
            TreeNode current = tail;
            while (current != null) {
                System.out.print(current.val);
                if (current.left != null) {
                    System.out.print(" ↔ ");
                }
                current = current.left;
            }
            System.out.println();
        }
        
        /**
         * 驗證雙向鏈結串列的完整性
         */
        public static boolean validateDoublyLinkedList(TreeNode head) {
            if (head == null) {
                return true;
            }
            
            TreeNode current = head;
            TreeNode prev = null;
            
            // 正向檢查
            while (current != null) {
                if (current.left != prev) {
                    return false;
                }
                prev = current;
                current = current.right;
            }
            
            return true;
        }
        
        /**
         * 獲取鏈結串列長度
         */
        public static int getLength(TreeNode head) {
            int count = 0;
            TreeNode current = head;
            while (current != null) {
                count++;
                current = current.right;
            }
            return count;
        }
    }
    
    // 樹視覺化工具類別
    static class TreeVisualizer {
        
        public static void printTree(TreeNode root, String description) {
            System.out.println(description);
            if (root == null) {
                System.out.println("空樹");
                return;
            }
            printTreeHelper(root, "", true);
        }
        
        private static void printTreeHelper(TreeNode node, String prefix, boolean isLast) {
            if (node != null) {
                System.out.println(prefix + (isLast ? "└── " : "├── ") + node.val);
                
                if (node.left != null || node.right != null) {
                    if (node.left != null) {
                        printTreeHelper(node.left, prefix + (isLast ? "    " : "│   "), node.right == null);
                    }
                    if (node.right != null) {
                        printTreeHelper(node.right, prefix + (isLast ? "    " : "│   "), true);
                    }
                }
            }
        }
        
        /**
         * 比較兩棵樹的結構
         */
        public static void compareTreeStructures(TreeNode tree1, String desc1, 
                                                TreeNode tree2, String desc2) {
            System.out.println("=== 樹結構比較 ===");
            
            printTree(tree1, desc1 + ":");
            System.out.println();
            printTree(tree2, desc2 + ":");
            
            List<Integer> inorder1 = BSTTransformer.getInorderTraversal(tree1);
            List<Integer> inorder2 = BSTTransformer.getInorderTraversal(tree2);
            
            System.out.println("\n中序遍歷比較:");
            System.out.println(desc1 + ": " + inorder1);
            System.out.println(desc2 + ": " + inorder2);
            System.out.println("中序遍歷相同: " + inorder1.equals(inorder2));
        }
    }
    
    /**
     * 主程式 
     */
    public static void main(String[] args) {
        System.out.println("=== BST轉換與平衡練習 ===\n");
        
        // 建立測試用的BST
        TreeNode originalBST = buildSampleBST();
        TreeVisualizer.printTree(originalBST, "原始BST:");
        
        List<Integer> originalInorder = BSTTransformer.getInorderTraversal(originalBST);
        System.out.println("原始中序遍歷: " + originalInorder);
        System.out.println();
        
        // 1. 將BST轉換為排序的雙向鏈結串列
        System.out.println("=== 1. BST轉雙向鏈結串列 ===");
        TreeNode bstCopy1 = BSTTransformer.copyTree(originalBST);
        TreeNode doublyLinkedList = BSTTransformer.convertToDoublyLinkedList(bstCopy1);
        
        DoublyLinkedListUtils.displayForward(doublyLinkedList);
        DoublyLinkedListUtils.displayBackward(doublyLinkedList);
        
        boolean isValidList = DoublyLinkedListUtils.validateDoublyLinkedList(doublyLinkedList);
        int listLength = DoublyLinkedListUtils.getLength(doublyLinkedList);
        
        System.out.println("鏈結串列完整性: " + (isValidList ? "正確" : "錯誤"));
        System.out.println("鏈結串列長度: " + listLength);
        System.out.println("原始節點數量: " + originalInorder.size());
        System.out.println();
        
        // 2. 將排序陣列轉換為平衡的BST
        System.out.println("=== 2. 排序陣列轉平衡BST ===");
        int[] sortedArray = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println("排序陣列: " + Arrays.toString(sortedArray));
        
        TreeNode balancedBST = BSTTransformer.sortedArrayToBST(sortedArray);
        TreeVisualizer.printTree(balancedBST, "建構的平衡BST:");
        
        List<Integer> balancedInorder = BSTTransformer.getInorderTraversal(balancedBST);
        System.out.println("平衡BST中序遍歷: " + balancedInorder);
        System.out.println();
        
        // 3. 檢查BST是否平衡，並計算平衡因子
        System.out.println("=== 3. 平衡性檢查 ===");
        
        System.out.println("a) 原始BST的平衡性:");
        BalanceInfo originalBalance = BSTTransformer.checkBalance(originalBST);
        originalBalance.displayReport();
        
        System.out.println("\nb) 平衡BST的平衡性:");
        BalanceInfo balancedBalance = BSTTransformer.checkBalance(balancedBST);
        balancedBalance.displayReport();
        
        // 建立一個明顯不平衡的樹
        System.out.println("\nc) 不平衡樹的檢查:");
        TreeNode unbalancedBST = buildUnbalancedBST();
        TreeVisualizer.printTree(unbalancedBST, "不平衡BST:");
        BalanceInfo unbalancedInfo = BSTTransformer.checkBalance(unbalancedBST);
        unbalancedInfo.displayReport();
        System.out.println();
        
        // 4. 將BST中每個節點的值改為所有大於等於該節點值的總和
        System.out.println("=== 4. 大於等於和轉換 ===");
        TreeNode bstCopy2 = BSTTransformer.copyTree(originalBST);
        
        System.out.println("轉換前:");
        TreeVisualizer.printTree(bstCopy2, "原始BST:");
        List<Integer> beforeConversion = BSTTransformer.getInorderTraversal(bstCopy2);
        System.out.println("轉換前中序遍歷: " + beforeConversion);
        
        BSTTransformer.convertToGreaterSumTree(bstCopy2);
        
        System.out.println("\n轉換後:");
        TreeVisualizer.printTree(bstCopy2, "大於等於和樹:");
        List<Integer> afterConversion = BSTTransformer.getInorderTraversal(bstCopy2);
        System.out.println("轉換後中序遍歷: " + afterConversion);
        
        // 驗證轉換正確性
        System.out.println("\n轉換驗證:");
        verifyGreaterSumConversion(beforeConversion, afterConversion);
        
        System.out.println("\n=== 綜合比較 ===");
        TreeVisualizer.compareTreeStructures(originalBST, "原始BST", balancedBST, "平衡BST");
        
        System.out.println("\n=== 練習完成 ===");
    }
    
    /**
     * 建立測試用的BST
     */
    private static TreeNode buildSampleBST() {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(6);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right.left = new TreeNode(5);
        root.right.right = new TreeNode(7);
        return root;
    }
    
    /**
     * 建立不平衡的BST
     */
    private static TreeNode buildUnbalancedBST() {
        TreeNode root = new TreeNode(1);
        root.right = new TreeNode(2);
        root.right.right = new TreeNode(3);
        root.right.right.right = new TreeNode(4);
        root.right.right.right.right = new TreeNode(5);
        return root;
    }
    
    /**
     * 驗證大於等於和轉換的正確性
     */
    private static void verifyGreaterSumConversion(List<Integer> before, List<Integer> after) {
        for (int i = 0; i < before.size(); i++) {
            int originalValue = before.get(i);
            int convertedValue = after.get(i);
            
            // 計算期望的大於等於和
            int expectedSum = 0;
            for (int j = 0; j < before.size(); j++) {
                if (before.get(j) >= originalValue) {
                    expectedSum += before.get(j);
                }
            }
            
            boolean isCorrect = (convertedValue == expectedSum);
            System.out.println("節點 " + originalValue + ": 轉換值 = " + convertedValue + 
                             ", 期望值 = " + expectedSum + " " + (isCorrect ? "✓" : "✗"));
        }
    }
}