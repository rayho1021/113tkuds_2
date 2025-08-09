/*
練習 3.7：樹的路徑問題
解決各種樹的路徑相關問題：
1. 找出從根節點到所有葉節點的路徑
2. 判斷樹中是否存在和為目標值的根到葉路徑
3. 找出樹中和最大的根到葉路徑
4. 計算樹中任意兩節點間的最大路徑和（樹的直徑）
*/

import java.util.*;

public class TreePathProblems {
    
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
    
    // 路徑資訊封裝類別
    static class PathInfo {
        private List<Integer> path;
        private long pathSum;
        private String description;
        
        public PathInfo(List<Integer> path, long pathSum, String description) {
            this.path = new ArrayList<>(path);
            this.pathSum = pathSum;
            this.description = description;
        }
        
        public List<Integer> getPath() { return path; }
        public long getPathSum() { return pathSum; }
        public String getDescription() { return description; }
        public int getPathLength() { return path.size(); }
        
        public String getPathString() {
            if (path.isEmpty()) {
                return "空路徑";
            }
            return path.stream()
                      .map(String::valueOf)
                      .reduce((a, b) -> a + " → " + b)
                      .orElse("");
        }
        
        @Override
        public String toString() {
            return String.format("%s: %s (和: %d, 長度: %d)", 
                               description, getPathString(), pathSum, getPathLength());
        }
    }
    
    // 路徑統計類別
    static class PathStatistics {
        private List<PathInfo> allPaths;
        private PathInfo maxSumPath;
        private PathInfo minSumPath;
        private PathInfo longestPath;
        private PathInfo shortestPath;
        
        public PathStatistics(List<PathInfo> paths) {
            this.allPaths = new ArrayList<>(paths);
            if (!paths.isEmpty()) {
                calculateStatistics();
            }
        }
        
        private void calculateStatistics() {
            maxSumPath = allPaths.stream()
                                .max(Comparator.comparingLong(PathInfo::getPathSum))
                                .orElse(null);
            
            minSumPath = allPaths.stream()
                                .min(Comparator.comparingLong(PathInfo::getPathSum))
                                .orElse(null);
            
            longestPath = allPaths.stream()
                                 .max(Comparator.comparingInt(PathInfo::getPathLength))
                                 .orElse(null);
            
            shortestPath = allPaths.stream()
                                  .min(Comparator.comparingInt(PathInfo::getPathLength))
                                  .orElse(null);
        }
        
        public void displayStatistics() {
            System.out.println("=== 路徑統計資訊 ===");
            System.out.println("總路徑數: " + allPaths.size());
            
            if (allPaths.isEmpty()) {
                System.out.println("無路徑資料");
                return;
            }
            
            double averageSum = allPaths.stream()
                                       .mapToLong(PathInfo::getPathSum)
                                       .average()
                                       .orElse(0.0);
            
            double averageLength = allPaths.stream()
                                          .mapToInt(PathInfo::getPathLength)
                                          .average()
                                          .orElse(0.0);
            
            System.out.printf("平均路徑和: %.2f\n", averageSum);
            System.out.printf("平均路徑長度: %.2f\n", averageLength);
            
            if (maxSumPath != null) {
                System.out.println("最大和路徑: " + maxSumPath);
            }
            if (minSumPath != null) {
                System.out.println("最小和路徑: " + minSumPath);
            }
            if (longestPath != null) {
                System.out.println("最長路徑: " + longestPath);
            }
            if (shortestPath != null) {
                System.out.println("最短路徑: " + shortestPath);
            }
        }
        
        public PathInfo getMaxSumPath() { return maxSumPath; }
        public PathInfo getMinSumPath() { return minSumPath; }
    }
    
    // 樹路徑問題解決類別
    static class TreePathSolver {
        
        /**
         * 找出從根節點到所有葉節點的路徑
         */
        public static List<PathInfo> findAllRootToLeafPaths(TreeNode root) {
            List<PathInfo> result = new ArrayList<>();
            if (root == null) {
                return result;
            }
            
            List<Integer> currentPath = new ArrayList<>();
            findAllPathsHelper(root, currentPath, 0, result);
            return result;
        }
        
        private static void findAllPathsHelper(TreeNode node, List<Integer> currentPath, 
                                             long currentSum, List<PathInfo> result) {
            if (node == null) {
                return;
            }
            
            // 將當前節點加入路徑
            currentPath.add(node.val);
            currentSum += node.val;
            
            // 如果是葉節點，記錄路徑
            if (node.left == null && node.right == null) {
                String description = "根到葉路徑 " + (result.size() + 1);
                result.add(new PathInfo(currentPath, currentSum, description));
            } else {
                // 繼續探索子樹
                findAllPathsHelper(node.left, currentPath, currentSum, result);
                findAllPathsHelper(node.right, currentPath, currentSum, result);
            }
            
            // 回溯：移除當前節點
            currentPath.remove(currentPath.size() - 1);
        }
        
        /**
         * 判斷樹中是否存在和為目標值的根到葉路徑
         */
        public static boolean hasPathSum(TreeNode root, int targetSum) {
            if (root == null) {
                return false;
            }
            
            return hasPathSumHelper(root, targetSum, 0);
        }
        
        private static boolean hasPathSumHelper(TreeNode node, int targetSum, long currentSum) {
            if (node == null) {
                return false;
            }
            
            currentSum += node.val;
            
            // 如果是葉節點，檢查路徑和是否等於目標值
            if (node.left == null && node.right == null) {
                return currentSum == targetSum;
            }
            
            // 檢查左右子樹
            return hasPathSumHelper(node.left, targetSum, currentSum) ||
                   hasPathSumHelper(node.right, targetSum, currentSum);
        }
        
        /**
         * 找出和為目標值的所有根到葉路徑
         */
        public static List<PathInfo> findPathsWithSum(TreeNode root, int targetSum) {
            List<PathInfo> result = new ArrayList<>();
            if (root == null) {
                return result;
            }
            
            List<Integer> currentPath = new ArrayList<>();
            findPathsWithSumHelper(root, targetSum, 0, currentPath, result);
            return result;
        }
        
        private static void findPathsWithSumHelper(TreeNode node, int targetSum, long currentSum,
                                                 List<Integer> currentPath, List<PathInfo> result) {
            if (node == null) {
                return;
            }
            
            currentPath.add(node.val);
            currentSum += node.val;
            
            if (node.left == null && node.right == null && currentSum == targetSum) {
                String description = "目標和路徑 (和=" + targetSum + ")";
                result.add(new PathInfo(currentPath, currentSum, description));
            } else {
                findPathsWithSumHelper(node.left, targetSum, currentSum, currentPath, result);
                findPathsWithSumHelper(node.right, targetSum, currentSum, currentPath, result);
            }
            
            currentPath.remove(currentPath.size() - 1);
        }
        
        /**
         * 找出樹中和最大的根到葉路徑
         */
        public static PathInfo findMaxSumPath(TreeNode root) {
            if (root == null) {
                return null;
            }
            
            List<PathInfo> allPaths = findAllRootToLeafPaths(root);
            if (allPaths.isEmpty()) {
                return null;
            }
            
            PathInfo maxPath = allPaths.stream()
                                      .max(Comparator.comparingLong(PathInfo::getPathSum))
                                      .orElse(null);
            
            if (maxPath != null) {
                return new PathInfo(maxPath.getPath(), maxPath.getPathSum(), "最大和根到葉路徑");
            }
            
            return null;
        }
        
        /**
         * 計算樹中任意兩節點間的最大路徑和（樹的直徑）
         */
        public static long maxPathSum(TreeNode root) {
            if (root == null) {
                return 0;
            }
            
            long[] maxSum = {Long.MIN_VALUE};
            maxPathSumHelper(root, maxSum);
            return maxSum[0];
        }
        
        /**
         * 輔助方法：計算從當前節點向下的最大路徑和
         * 同時更新全局最大路徑和
         */
        private static long maxPathSumHelper(TreeNode node, long[] maxSum) {
            if (node == null) {
                return 0;
            }
            
            // 計算左右子樹的最大路徑和（負數時取0，表示不選擇該路徑）
            long leftMax = Math.max(0, maxPathSumHelper(node.left, maxSum));
            long rightMax = Math.max(0, maxPathSumHelper(node.right, maxSum));
            
            // 通過當前節點的最大路徑和
            long currentMax = node.val + leftMax + rightMax;
            
            // 更新全局最大值
            maxSum[0] = Math.max(maxSum[0], currentMax);
            
            // 返回從當前節點向下的最大路徑和（只能選擇左或右其中一條路徑）
            return node.val + Math.max(leftMax, rightMax);
        }
        
        /**
         * 找出構成最大路徑和的具體路徑
         */
        public static PathInfo findMaxPathSumWithPath(TreeNode root) {
            if (root == null) {
                return null;
            }
            
            PathResult result = new PathResult();
            findMaxPathSumWithPathHelper(root, result);
            
            if (result.maxPath.isEmpty()) {
                return null;
            }
            
            return new PathInfo(result.maxPath, result.maxSum, "最大路徑和（任意兩節點）");
        }
        
        // 輔助類別：儲存最大路徑和的詳細資訊
        static class PathResult {
            long maxSum = Long.MIN_VALUE;
            List<Integer> maxPath = new ArrayList<>();
            List<Integer> currentPath = new ArrayList<>();
        }
        
        private static long findMaxPathSumWithPathHelper(TreeNode node, PathResult result) {
            if (node == null) {
                return 0;
            }
            
            result.currentPath.add(node.val);
            
            long leftMax = Math.max(0, findMaxPathSumWithPathHelper(node.left, result));
            long rightMax = Math.max(0, findMaxPathSumWithPathHelper(node.right, result));
            
            long currentMax = node.val + leftMax + rightMax;
            
            if (currentMax > result.maxSum) {
                result.maxSum = currentMax;
                result.maxPath = new ArrayList<>(result.currentPath);
            }
            
            result.currentPath.remove(result.currentPath.size() - 1);
            
            return node.val + Math.max(leftMax, rightMax);
        }
    }
    
    // 樹建構輔助類別
    static class TreeBuilder {
        /**
         * 建構測試用的二元樹
         */
        public static TreeNode buildSampleTree1() {
            TreeNode root = new TreeNode(10);
            root.left = new TreeNode(5);
            root.right = new TreeNode(-3);
            root.left.left = new TreeNode(3);
            root.left.right = new TreeNode(2);
            root.right.right = new TreeNode(11);
            root.left.left.left = new TreeNode(3);
            root.left.left.right = new TreeNode(-2);
            root.left.right.right = new TreeNode(1);
            return root;
        }
        
        /**
         * 建構包含負數的測試樹
         */
        public static TreeNode buildNegativeTree() {
            TreeNode root = new TreeNode(-10);
            root.left = new TreeNode(9);
            root.right = new TreeNode(20);
            root.right.left = new TreeNode(15);
            root.right.right = new TreeNode(7);
            return root;
        }
        
        /**
         * 建構簡單的測試樹
         */
        public static TreeNode buildSimpleTree() {
            TreeNode root = new TreeNode(1);
            root.left = new TreeNode(2);
            root.right = new TreeNode(3);
            root.left.left = new TreeNode(4);
            root.left.right = new TreeNode(5);
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
        System.out.println("=== 樹的路徑問題練習 ===\n");
        
        // 測試案例1：包含正負數的複雜樹
        System.out.println("測試案例1：複雜樹結構");
        TreeNode complexTree = TreeBuilder.buildSampleTree1();
        TreeBuilder.printTree(complexTree, "樹結構:");
        
        // 1. 找出所有根到葉路徑
        System.out.println("\n1. 所有根到葉路徑:");
        List<PathInfo> allPaths = TreePathSolver.findAllRootToLeafPaths(complexTree);
        for (PathInfo path : allPaths) {
            System.out.println("   " + path);
        }
        
        // 2. 測試目標和路徑
        System.out.println("\n2. 目標和路徑檢查:");
        int[] targetSums = {22, 8, 18, 100};
        for (int target : targetSums) {
            boolean hasPath = TreePathSolver.hasPathSum(complexTree, target);
            System.out.println("   和為 " + target + " 的路徑: " + (hasPath ? "存在" : "不存在"));
            
            if (hasPath) {
                List<PathInfo> targetPaths = TreePathSolver.findPathsWithSum(complexTree, target);
                for (PathInfo path : targetPaths) {
                    System.out.println("     → " + path.getPathString());
                }
            }
        }
        
        // 3. 最大和根到葉路徑
        System.out.println("\n3. 最大和根到葉路徑:");
        PathInfo maxPath = TreePathSolver.findMaxSumPath(complexTree);
        if (maxPath != null) {
            System.out.println("   " + maxPath);
        }
        
        // 4. 樹的直徑（最大路徑和）
        System.out.println("\n4. 樹的直徑:");
        long maxPathSum = TreePathSolver.maxPathSum(complexTree);
        System.out.println("   最大路徑和: " + maxPathSum);
        
        PathInfo maxPathWithDetails = TreePathSolver.findMaxPathSumWithPath(complexTree);
        if (maxPathWithDetails != null) {
            System.out.println("   " + maxPathWithDetails);
        }
        
        // 路徑統計
        System.out.println();
        PathStatistics stats1 = new PathStatistics(allPaths);
        stats1.displayStatistics();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // 測試案例2：包含負數的樹
        System.out.println("測試案例2：包含負數的樹");
        TreeNode negativeTree = TreeBuilder.buildNegativeTree();
        TreeBuilder.printTree(negativeTree, "樹結構:");
        
        List<PathInfo> negativePaths = TreePathSolver.findAllRootToLeafPaths(negativeTree);
        System.out.println("\n所有根到葉路徑:");
        for (PathInfo path : negativePaths) {
            System.out.println("   " + path);
        }
        
        PathInfo maxNegativePath = TreePathSolver.findMaxSumPath(negativeTree);
        System.out.println("\n最大和根到葉路徑:");
        if (maxNegativePath != null) {
            System.out.println("   " + maxNegativePath);
        }
        
        long maxNegativePathSum = TreePathSolver.maxPathSum(negativeTree);
        System.out.println("樹的直徑: " + maxNegativePathSum);
        
        System.out.println();
        PathStatistics stats2 = new PathStatistics(negativePaths);
        stats2.displayStatistics();
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // 測試案例3：簡單樹
        System.out.println("測試案例3：簡單樹");
        TreeNode simpleTree = TreeBuilder.buildSimpleTree();
        TreeBuilder.printTree(simpleTree, "樹結構:");
        
        List<PathInfo> simplePaths = TreePathSolver.findAllRootToLeafPaths(simpleTree);
        System.out.println("\n所有根到葉路徑:");
        for (PathInfo path : simplePaths) {
            System.out.println("   " + path);
        }
        
        // 測試多個目標和
        System.out.println("\n目標和路徑測試:");
        for (int target : new int[]{7, 8, 4}) {
            List<PathInfo> targetPaths = TreePathSolver.findPathsWithSum(simpleTree, target);
            System.out.println("和為 " + target + " 的路徑:");
            if (targetPaths.isEmpty()) {
                System.out.println("   無");
            } else {
                for (PathInfo path : targetPaths) {
                    System.out.println("   " + path.getPathString());
                }
            }
        }
        
        System.out.println("\n=== 完成 ===");
    }
}