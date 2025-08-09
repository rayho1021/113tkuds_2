/*
練習 3.6：BST驗證與修復
實作BST的驗證與修復功能：
1. 驗證一棵二元樹是否為有效的BST
2. 找出BST中不符合規則的節點
3. 修復一棵有兩個節點位置錯誤的BST
4. 計算需要移除多少個節點才能讓樹變成有效的BST
*/

import java.util.*;

public class BSTValidationAndRepair {
    
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
    
    // 驗證結果封裝類別
    static class ValidationResult {
        private boolean isValid;
        private List<String> errorMessages;
        private List<Integer> violatingValues;
        private String validationMethod;
        
        public ValidationResult(boolean isValid, String method) {
            this.isValid = isValid;
            this.validationMethod = method;
            this.errorMessages = new ArrayList<>();
            this.violatingValues = new ArrayList<>();
        }
        
        public void addError(String message, int value) {
            errorMessages.add(message);
            violatingValues.add(value);
        }
        
        public void displayResult() {
            System.out.println("驗證方法: " + validationMethod);
            System.out.println("BST有效性: " + (isValid ? "有效" : "無效"));
            
            if (!isValid && !errorMessages.isEmpty()) {
                System.out.println("錯誤詳情:");
                for (int i = 0; i < errorMessages.size(); i++) {
                    System.out.println("  - " + errorMessages.get(i));
                }
                System.out.println("違規值: " + violatingValues);
            }
        }
        
        public boolean isValid() { return isValid; }
        public List<String> getErrorMessages() { return errorMessages; }
        public List<Integer> getViolatingValues() { return violatingValues; }
    }
    
    // 修復結果封裝類別
    static class RepairResult {
        private boolean canRepair;
        private boolean wasRepaired;
        private TreeNode firstSwapped;
        private TreeNode secondSwapped;
        private String repairDescription;
        
        public RepairResult(boolean canRepair) {
            this.canRepair = canRepair;
            this.wasRepaired = false;
            this.repairDescription = "";
        }
        
        public void setRepaired(TreeNode first, TreeNode second, String description) {
            this.wasRepaired = true;
            this.firstSwapped = first;
            this.secondSwapped = second;
            this.repairDescription = description;
        }
        
        public void displayResult() {
            System.out.println("修復可行性: " + (canRepair ? "可修復" : "無法修復"));
            if (wasRepaired) {
                System.out.println("修復狀態: 已修復");
                System.out.println("修復內容: " + repairDescription);
                if (firstSwapped != null && secondSwapped != null) {
                    System.out.println("交換的節點值: " + firstSwapped.val + " <-> " + secondSwapped.val);
                }
            } else if (canRepair) {
                System.out.println("修復狀態: 尚未執行修復");
            } else {
                System.out.println("修復狀態: " + repairDescription);
            }
        }
        
        public boolean canRepair() { return canRepair; }
        public boolean wasRepaired() { return wasRepaired; }
    }
    
    // BST驗證與修復操作類別
    static class BSTValidator {
        
        /**
         * 方法1：使用範圍檢查驗證BST
         */
        public static ValidationResult validateByRange(TreeNode root) {
            ValidationResult result = new ValidationResult(true, "範圍檢查法");
            boolean isValid = validateByRangeHelper(root, Long.MIN_VALUE, Long.MAX_VALUE, result);
            result.isValid = isValid;
            return result;
        }
        
        private static boolean validateByRangeHelper(TreeNode node, long min, long max, ValidationResult result) {
            if (node == null) {
                return true;
            }
            
            if (node.val <= min || node.val >= max) {
                result.addError("節點值 " + node.val + " 超出有效範圍 (" + min + ", " + max + ")", node.val);
                return false;
            }
            
            return validateByRangeHelper(node.left, min, node.val, result) &&
                   validateByRangeHelper(node.right, node.val, max, result);
        }
        
        /**
         * 方法2：使用中序遍歷驗證BST
         */
        public static ValidationResult validateByInorder(TreeNode root) {
            ValidationResult result = new ValidationResult(true, "中序遍歷法");
            List<Integer> inorderList = new ArrayList<>();
            inorderTraversal(root, inorderList);
            
            for (int i = 1; i < inorderList.size(); i++) {
                if (inorderList.get(i) <= inorderList.get(i - 1)) {
                    result.addError("序列不遞增: " + inorderList.get(i - 1) + " >= " + inorderList.get(i), 
                                  inorderList.get(i));
                    result.isValid = false;
                }
            }
            
            return result;
        }
        
        private static void inorderTraversal(TreeNode node, List<Integer> result) {
            if (node != null) {
                inorderTraversal(node.left, result);
                result.add(node.val);
                inorderTraversal(node.right, result);
            }
        }
        
        /**
         * 找出BST中不符合規則的節點
         */
        public static List<TreeNode> findInvalidNodes(TreeNode root) {
            List<TreeNode> invalidNodes = new ArrayList<>();
            findInvalidNodesHelper(root, Long.MIN_VALUE, Long.MAX_VALUE, invalidNodes);
            return invalidNodes;
        }
        
        private static void findInvalidNodesHelper(TreeNode node, long min, long max, List<TreeNode> invalidNodes) {
            if (node == null) {
                return;
            }
            
            if (node.val <= min || node.val >= max) {
                invalidNodes.add(node);
            }
            
            findInvalidNodesHelper(node.left, min, node.val, invalidNodes);
            findInvalidNodesHelper(node.right, node.val, max, invalidNodes);
        }
        
        /**
         * 修復一棵有兩個節點位置錯誤的BST
         */
        public static RepairResult repairTwoSwappedNodes(TreeNode root) {
            // 使用中序遍歷找出錯誤的節點
            TreeNode[] swappedNodes = findTwoSwappedNodes(root);
            
            if (swappedNodes[0] == null && swappedNodes[1] == null) {
                RepairResult result = new RepairResult(false);
                result.repairDescription = "未發現兩個節點交換的錯誤模式";
                return result;
            }
            
            RepairResult result = new RepairResult(true);
            
            if (swappedNodes[0] != null && swappedNodes[1] != null) {
                // 交換兩個節點的值
                int temp = swappedNodes[0].val;
                swappedNodes[0].val = swappedNodes[1].val;
                swappedNodes[1].val = temp;
                
                result.setRepaired(swappedNodes[0], swappedNodes[1], 
                                 "交換了兩個錯誤位置的節點值");
            }
            
            return result;
        }
        
        /**
         * 找出被交換的兩個節點
         */
        private static TreeNode[] findTwoSwappedNodes(TreeNode root) {
            TreeNode[] result = new TreeNode[2];
            TreeNode[] prev = new TreeNode[1];
            
            findSwappedNodesHelper(root, prev, result);
            return result;
        }
        
        private static void findSwappedNodesHelper(TreeNode node, TreeNode[] prev, TreeNode[] swapped) {
            if (node == null) {
                return;
            }
            
            findSwappedNodesHelper(node.left, prev, swapped);
            
            if (prev[0] != null && prev[0].val > node.val) {
                if (swapped[0] == null) {
                    swapped[0] = prev[0];
                    swapped[1] = node;
                } else {
                    swapped[1] = node;
                }
            }
            prev[0] = node;
            
            findSwappedNodesHelper(node.right, prev, swapped);
        }
        
        /**
         * 計算需要移除多少個節點才能讓樹變成有效的BST
         */
        public static int minNodesToRemove(TreeNode root) {
            if (root == null) {
                return 0;
            }
            
            List<Integer> inorderList = new ArrayList<>();
            inorderTraversal(root, inorderList);
            
            // 找出最長遞增子序列的長度
            int longestIncreasingLength = findLongestIncreasingSubsequence(inorderList);
            
            // 需要移除的節點數 = 總節點數 - 最長遞增子序列長度
            return inorderList.size() - longestIncreasingLength;
        }
        
        /**
         * 找出最長嚴格遞增子序列的長度
         */
        private static int findLongestIncreasingSubsequence(List<Integer> nums) {
            if (nums.isEmpty()) {
                return 0;
            }
            
            int[] dp = new int[nums.size()];
            Arrays.fill(dp, 1);
            
            for (int i = 1; i < nums.size(); i++) {
                for (int j = 0; j < i; j++) {
                    if (nums.get(j) < nums.get(i)) {
                        dp[i] = Math.max(dp[i], dp[j] + 1);
                    }
                }
            }
            
            int maxLength = 0;
            for (int length : dp) {
                maxLength = Math.max(maxLength, length);
            }
            
            return maxLength;
        }
        
        /**
         * 綜合診斷BST健康狀況
         */
        public static void comprehensiveDiagnosis(TreeNode root) {
            System.out.println("=== BST綜合診斷 ===");
            
            // 1. 驗證BST有效性
            ValidationResult rangeResult = validateByRange(root);
            ValidationResult inorderResult = validateByInorder(root);
            
            System.out.println("\n1. BST有效性檢查:");
            rangeResult.displayResult();
            System.out.println();
            inorderResult.displayResult();
            
            // 2. 找出違規節點
            System.out.println("\n2. 違規節點分析:");
            List<TreeNode> invalidNodes = findInvalidNodes(root);
            if (invalidNodes.isEmpty()) {
                System.out.println("未發現違規節點");
            } else {
                System.out.print("違規節點值: ");
                for (TreeNode node : invalidNodes) {
                    System.out.print(node.val + " ");
                }
                System.out.println();
            }
            
            // 3. 修復可行性分析
            System.out.println("\n3. 修復可行性分析:");
            RepairResult repairResult = new RepairResult(true);
            TreeNode[] swappedNodes = findTwoSwappedNodes(root);
            
            if (swappedNodes[0] != null && swappedNodes[1] != null) {
                System.out.println("發現可能的交換錯誤節點: " + swappedNodes[0].val + " 和 " + swappedNodes[1].val);
                System.out.println("修復建議: 交換這兩個節點的值");
            } else {
                System.out.println("未發現明確的兩節點交換錯誤");
            }
            
            // 4. 最小移除節點分析
            System.out.println("\n4. 最小移除節點分析:");
            int nodesToRemove = minNodesToRemove(root);
            List<Integer> inorderList = new ArrayList<>();
            inorderTraversal(root, inorderList);
            
            System.out.println("當前節點總數: " + inorderList.size());
            System.out.println("需要移除的最少節點數: " + nodesToRemove);
            System.out.println("修復後保留的節點數: " + (inorderList.size() - nodesToRemove));
            System.out.println("中序遍歷序列: " + inorderList);
        }
    }
    
    // 樹建構輔助類別
    static class TreeBuilder {
        /**
         * 手動建構測試用的BST
         */
        public static TreeNode buildValidBST() {
            TreeNode root = new TreeNode(10);
            root.left = new TreeNode(5);
            root.right = new TreeNode(15);
            root.left.left = new TreeNode(2);
            root.left.right = new TreeNode(7);
            root.right.left = new TreeNode(12);
            root.right.right = new TreeNode(18);
            return root;
        }
        
        /**
         * 建構有兩個節點錯誤的BST
         */
        public static TreeNode buildSwappedBST() {
            TreeNode root = new TreeNode(10);
            root.left = new TreeNode(5);
            root.right = new TreeNode(15);
            root.left.left = new TreeNode(2);
            root.left.right = new TreeNode(18); // 錯誤：應該是7
            root.right.left = new TreeNode(12);
            root.right.right = new TreeNode(7);  // 錯誤：應該是18
            return root;
        }
        
        /**
         * 建構完全無效的BST
         */
        public static TreeNode buildInvalidBST() {
            TreeNode root = new TreeNode(10);
            root.left = new TreeNode(15); // 錯誤：左子樹值大於根
            root.right = new TreeNode(5);  // 錯誤：右子樹值小於根
            root.left.left = new TreeNode(20);
            root.left.right = new TreeNode(12);
            return root;
        }
        
        /**
         * 樹結構視覺化顯示
         */
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
    }
    
    /**
     * 主程式
     */
    public static void main(String[] args) {
        System.out.println("=== BST驗證與修復練習 ===\n");
        
        // 測試案例1：有效的BST
        System.out.println("測試案例1：有效的BST");
        TreeNode validBST = TreeBuilder.buildValidBST();
        TreeBuilder.printTree(validBST, "樹結構:");
        
        BSTValidator.comprehensiveDiagnosis(validBST);
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 測試案例2：兩個節點交換錯誤的BST
        System.out.println("測試案例2：兩個節點交換錯誤的BST");
        TreeNode swappedBST = TreeBuilder.buildSwappedBST();
        TreeBuilder.printTree(swappedBST, "修復前的樹結構:");
        
        BSTValidator.comprehensiveDiagnosis(swappedBST);
        
        // 執行修復
        System.out.println("\n=== 執行修復 ===");
        RepairResult repairResult = BSTValidator.repairTwoSwappedNodes(swappedBST);
        repairResult.displayResult();
        
        if (repairResult.wasRepaired()) {
            System.out.println("\n修復後的樹結構:");
            TreeBuilder.printTree(swappedBST, "");
            
            // 驗證修復結果
            ValidationResult postRepairValidation = BSTValidator.validateByRange(swappedBST);
            System.out.println("\n修復後驗證:");
            postRepairValidation.displayResult();
        }
        
        System.out.println("\n" + "=".repeat(50) + "\n");
        
        // 測試案例3：完全無效的BST
        System.out.println("測試案例3：完全無效的BST");
        TreeNode invalidBST = TreeBuilder.buildInvalidBST();
        TreeBuilder.printTree(invalidBST, "樹結構:");
        
        BSTValidator.comprehensiveDiagnosis(invalidBST);
        
        // 嘗試修復
        System.out.println("\n=== 嘗試修復 ===");
        RepairResult invalidRepairResult = BSTValidator.repairTwoSwappedNodes(invalidBST);
        invalidRepairResult.displayResult();
        
        System.out.println("\n=== 完成 ===");
    }
}