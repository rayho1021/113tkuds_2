/*
練習 3.1：二元樹基本操作練習
檔名： BinaryTreeBasicOperations.java
實作以下二元樹操作：
1. 計算樹中所有節點值的總和與平均值
2. 找出樹中的最大值和最小值節點
3. 計算樹的寬度（每一層節點數的最大值）
4. 判斷一棵樹是否為完全二元樹
*/

import java.util.*;

public class BinaryTreeBasicOperations {
    
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
    
    // 統計結果封裝類別
    static class TreeStatistics {
        private int sum;
        private double average;
        private Integer maxValue;
        private Integer minValue;
        private int maxWidth;
        private boolean isComplete;
        private int nodeCount;
        
        public TreeStatistics(int sum, double average, Integer maxValue, Integer minValue, 
                            int maxWidth, boolean isComplete, int nodeCount) {
            this.sum = sum;
            this.average = average;
            this.maxValue = maxValue;
            this.minValue = minValue;
            this.maxWidth = maxWidth;
            this.isComplete = isComplete;
            this.nodeCount = nodeCount;
        }
        
        @Override
        public String toString() {
            return String.format(
                "樹統計資訊:\n" +
                "  節點總數: %d\n" +
                "  總和: %d\n" +
                "  平均值: %.2f\n" +
                "  最大值: %s\n" +
                "  最小值: %s\n" +
                "  最大寬度: %d\n" +
                "  是否為完全二元樹: %s",
                nodeCount, sum, average, 
                maxValue == null ? "無" : maxValue,
                minValue == null ? "無" : minValue,
                maxWidth, isComplete ? "是" : "否"
            );
        }
    }
    
    // 主要操作類別
    static class BinaryTreeOperations {
        
        /**
         * 計算樹中所有節點值的總和
         */
        public static int calculateSum(TreeNode root) {
            if (root == null) {
                return 0;
            }
            
            return root.val + calculateSum(root.left) + calculateSum(root.right);
        }
        
        /**
         * 計算樹中所有節點值的平均值
         */
        public static double calculateAverage(TreeNode root) {
            if (root == null) {
                return 0.0;
            }
            
            int nodeCount = countNodes(root);
            int sum = calculateSum(root);
            
            return (double) sum / nodeCount;
        }
        
        /**
         * 計算節點總數
         */
        public static int countNodes(TreeNode root) {
            if (root == null) {
                return 0;
            }
            
            return 1 + countNodes(root.left) + countNodes(root.right);
        }
        
        /**
         * 找出樹中的最大值節點
         */
        public static Integer findMax(TreeNode root) {
            if (root == null) {
                return null;
            }
            
            int max = root.val;
            
            Integer leftMax = findMax(root.left);
            if (leftMax != null && leftMax > max) {
                max = leftMax;
            }
            
            Integer rightMax = findMax(root.right);
            if (rightMax != null && rightMax > max) {
                max = rightMax;
            }
            
            return max;
        }
        
        /**
         * 找出樹中的最小值節點
         */
        public static Integer findMin(TreeNode root) {
            if (root == null) {
                return null;
            }
            
            int min = root.val;
            
            Integer leftMin = findMin(root.left);
            if (leftMin != null && leftMin < min) {
                min = leftMin;
            }
            
            Integer rightMin = findMin(root.right);
            if (rightMin != null && rightMin < min) {
                min = rightMin;
            }
            
            return min;
        }
        
        /**
         * 計算樹的最大寬度（每一層節點數的最大值）
         */
        public static int calculateMaxWidth(TreeNode root) {
            if (root == null) {
                return 0;
            }
            
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            int maxWidth = 0;
            
            while (!queue.isEmpty()) {
                int currentLevelSize = queue.size();
                maxWidth = Math.max(maxWidth, currentLevelSize);
                
                // 處理當前層的所有節點
                for (int i = 0; i < currentLevelSize; i++) {
                    TreeNode node = queue.poll();
                    
                    if (node.left != null) {
                        queue.offer(node.left);
                    }
                    if (node.right != null) {
                        queue.offer(node.right);
                    }
                }
            }
            
            return maxWidth;
        }
        
        /**
         * 判斷一棵樹是否為完全二元樹
         * 使用層序遍歷配合索引檢查
         */
        public static boolean isCompleteTree(TreeNode root) {
            if (root == null) {
                return true;
            }
            
            int totalNodes = countNodes(root);
            return isCompleteTreeHelper(root, 1, totalNodes);
        }
        
        /**
         * 完全二元樹檢查的輔助方法
         * 使用索引法：根節點索引為1，左子節點為2*i，右子節點為2*i+1
         */
        private static boolean isCompleteTreeHelper(TreeNode node, int index, int totalNodes) {
            if (node == null) {
                return true;
            }
            
            // 如果索引超過總節點數，則不是完全二元樹
            if (index > totalNodes) {
                return false;
            }
            
            // 遞迴檢查左右子樹
            return isCompleteTreeHelper(node.left, 2 * index, totalNodes) &&
                   isCompleteTreeHelper(node.right, 2 * index + 1, totalNodes);
        }
        
        /**
         * 獲取完整的樹統計資訊
         */
        public static TreeStatistics getStatistics(TreeNode root) {
            int sum = calculateSum(root);
            double average = calculateAverage(root);
            Integer maxValue = findMax(root);
            Integer minValue = findMin(root);
            int maxWidth = calculateMaxWidth(root);
            boolean isComplete = isCompleteTree(root);
            int nodeCount = countNodes(root);
            
            return new TreeStatistics(sum, average, maxValue, minValue, 
                                    maxWidth, isComplete, nodeCount);
        }
    }
    
    // 樹建構輔助類別
    static class TreeBuilder {
        /**
         * 從陣列建構二元樹（層序方式，null表示空節點）
         */
        public static TreeNode buildFromArray(Integer[] values) {
            if (values == null || values.length == 0 || values[0] == null) {
                return null;
            }
            
            TreeNode root = new TreeNode(values[0]);
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            
            int i = 1;
            while (!queue.isEmpty() && i < values.length) {
                TreeNode current = queue.poll();
                
                // 處理左子節點
                if (i < values.length && values[i] != null) {
                    current.left = new TreeNode(values[i]);
                    queue.offer(current.left);
                }
                i++;
                
                // 處理右子節點
                if (i < values.length && values[i] != null) {
                    current.right = new TreeNode(values[i]);
                    queue.offer(current.right);
                }
                i++;
            }
            
            return root;
        }
        
        /**
         * 簡單的樹結構視覺化（中序遍歷顯示）
         */
        public static void printInOrder(TreeNode root) {
            printInOrderHelper(root, "", true);
        }
        
        private static void printInOrderHelper(TreeNode node, String prefix, boolean isLast) {
            if (node != null) {
                System.out.println(prefix + (isLast ? "└── " : "├── ") + node.val);
                
                if (node.left != null || node.right != null) {
                    if (node.left != null) {
                        printInOrderHelper(node.left, prefix + (isLast ? "    " : "│   "), node.right == null);
                    }
                    if (node.right != null) {
                        printInOrderHelper(node.right, prefix + (isLast ? "    " : "│   "), true);
                    }
                }
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== 二元樹基本操作練習 ===\n");
        
        // 測試案例1：完全二元樹
        System.out.println("測試案例1：完全二元樹");
        Integer[] completeTreeValues = {1, 2, 3, 4, 5, 6, 7};
        TreeNode completeTree = TreeBuilder.buildFromArray(completeTreeValues);
        
        System.out.println("樹結構：");
        TreeBuilder.printInOrder(completeTree);
        System.out.println();
        
        TreeStatistics stats1 = BinaryTreeOperations.getStatistics(completeTree);
        System.out.println(stats1);
        System.out.println();
        
        // 測試案例2：非完全二元樹
        System.out.println("測試案例2：非完全二元樹");
        Integer[] incompleteTreeValues = {10, 5, 15, 3, 7, null, 20, 1};
        TreeNode incompleteTree = TreeBuilder.buildFromArray(incompleteTreeValues);
        
        System.out.println("樹結構：");
        TreeBuilder.printInOrder(incompleteTree);
        System.out.println();
        
        TreeStatistics stats2 = BinaryTreeOperations.getStatistics(incompleteTree);
        System.out.println(stats2);
        System.out.println();
        
        // 測試案例3：單一節點
        System.out.println("測試案例3：單一節點");
        TreeNode singleNode = new TreeNode(42);
        
        System.out.println("樹結構：");
        TreeBuilder.printInOrder(singleNode);
        System.out.println();
        
        TreeStatistics stats3 = BinaryTreeOperations.getStatistics(singleNode);
        System.out.println(stats3);
        System.out.println();
        
        // 測試案例4：空樹
        System.out.println("測試案例4：空樹");
        TreeNode emptyTree = null;
        
        TreeStatistics stats4 = BinaryTreeOperations.getStatistics(emptyTree);
        System.out.println(stats4);
        System.out.println();
        
        // 測試案例5：不平衡樹（測試寬度計算）
        System.out.println("測試案例5：不平衡樹");
        Integer[] unbalancedTreeValues = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        TreeNode unbalancedTree = TreeBuilder.buildFromArray(unbalancedTreeValues);
        
        System.out.println("樹結構：");
        TreeBuilder.printInOrder(unbalancedTree);
        System.out.println();
        
        TreeStatistics stats5 = BinaryTreeOperations.getStatistics(unbalancedTree);
        System.out.println(stats5);
        
        System.out.println("\n=== 練習完成 ===");
    }
}