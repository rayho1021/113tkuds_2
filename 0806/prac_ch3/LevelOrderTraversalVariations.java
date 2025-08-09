/* 
練習 3.5：層序走訪變形
實作各種層序走訪的變形：
1. 將每一層的節點分別儲存在不同的List中
2. 實作之字形層序走訪（奇數層從左到右，偶數層從右到左）
3. 只列印每一層的最後一個節點
4. 實作垂直層序走訪（按照節點的水平位置分組）
*/

import java.util.*;

public class LevelOrderTraversalVariations {
    
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
    
    // 節點位置資訊類別（用於垂直走訪）
    static class PositionedNode {
        TreeNode node;
        int level;
        int column;
        
        public PositionedNode(TreeNode node, int level, int column) {
            this.node = node;
            this.level = level;
            this.column = column;
        }
    }
    
    // 走訪結果封裝類別
    static class TraversalResults {
        private List<List<Integer>> levelOrderResult;
        private List<List<Integer>> zigzagResult;
        private List<Integer> rightmostResult;
        private Map<Integer, List<Integer>> verticalResult;
        
        public TraversalResults() {
            this.levelOrderResult = new ArrayList<>();
            this.zigzagResult = new ArrayList<>();
            this.rightmostResult = new ArrayList<>();
            this.verticalResult = new TreeMap<>();
        }
        
        // Getters
        public List<List<Integer>> getLevelOrderResult() { return levelOrderResult; }
        public List<List<Integer>> getZigzagResult() { return zigzagResult; }
        public List<Integer> getRightmostResult() { return rightmostResult; }
        public Map<Integer, List<Integer>> getVerticalResult() { return verticalResult; }
        
        // 顯示所有結果
        public void displayAll() {
            System.out.println("=== 層序走訪變形結果 ===");
            
            System.out.println("\n1. 分層儲存結果:");
            for (int i = 0; i < levelOrderResult.size(); i++) {
                System.out.println("   第" + (i + 1) + "層: " + levelOrderResult.get(i));
            }
            
            System.out.println("\n2. 之字形走訪結果:");
            for (int i = 0; i < zigzagResult.size(); i++) {
                String direction = (i % 2 == 0) ? "左→右" : "右→左";
                System.out.println("   第" + (i + 1) + "層(" + direction + "): " + zigzagResult.get(i));
            }
            
            System.out.println("\n3. 每層最後節點:");
            System.out.println("   " + rightmostResult);
            
            System.out.println("\n4. 垂直走訪結果:");
            for (Map.Entry<Integer, List<Integer>> entry : verticalResult.entrySet()) {
                System.out.println("   座標" + entry.getKey() + ": " + entry.getValue());
            }
        }
    }
    
    // 層序走訪變形操作類別
    static class LevelOrderVariations {
        
        /**
         * 將每一層的節點分別儲存在不同的List中
         */
        public static List<List<Integer>> levelOrderByLayers(TreeNode root) {
            List<List<Integer>> result = new ArrayList<>();
            if (root == null) {
                return result;
            }
            
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            
            while (!queue.isEmpty()) {
                int levelSize = queue.size();
                List<Integer> currentLevel = new ArrayList<>();
                
                // 處理當前層的所有節點
                for (int i = 0; i < levelSize; i++) {
                    TreeNode node = queue.poll();
                    currentLevel.add(node.val);
                    
                    if (node.left != null) {
                        queue.offer(node.left);
                    }
                    if (node.right != null) {
                        queue.offer(node.right);
                    }
                }
                
                result.add(currentLevel);
            }
            
            return result;
        }
        
        /**
         * 之字形層序走訪（奇數層從左到右，偶數層從右到左）
         */
        public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
            List<List<Integer>> result = new ArrayList<>();
            if (root == null) {
                return result;
            }
            
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            boolean leftToRight = true;
            
            while (!queue.isEmpty()) {
                int levelSize = queue.size();
                List<Integer> currentLevel = new ArrayList<>();
                
                // 處理當前層的所有節點
                for (int i = 0; i < levelSize; i++) {
                    TreeNode node = queue.poll();
                    currentLevel.add(node.val);
                    
                    if (node.left != null) {
                        queue.offer(node.left);
                    }
                    if (node.right != null) {
                        queue.offer(node.right);
                    }
                }
                
                // 如果是偶數層（從右到左），反轉當前層結果
                if (!leftToRight) {
                    Collections.reverse(currentLevel);
                }
                
                result.add(currentLevel);
                leftToRight = !leftToRight; // 切換方向
            }
            
            return result;
        }
        
        /**
         * 只列印每一層的最後一個節點（最右邊的節點）
         */
        public static List<Integer> rightmostNodes(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            if (root == null) {
                return result;
            }
            
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            
            while (!queue.isEmpty()) {
                int levelSize = queue.size();
                TreeNode rightmostNode = null;
                
                // 處理當前層的所有節點，記錄最後一個
                for (int i = 0; i < levelSize; i++) {
                    TreeNode node = queue.poll();
                    rightmostNode = node; // 最後一個處理的節點就是最右邊的
                    
                    if (node.left != null) {
                        queue.offer(node.left);
                    }
                    if (node.right != null) {
                        queue.offer(node.right);
                    }
                }
                
                result.add(rightmostNode.val);
            }
            
            return result;
        }
        
        /**
         * 垂直層序走訪（按照節點的水平位置分組）
         */
        public static Map<Integer, List<Integer>> verticalTraversal(TreeNode root) {
            Map<Integer, List<Integer>> result = new TreeMap<>(); // TreeMap自動排序
            if (root == null) {
                return result;
            }
            
            Queue<PositionedNode> queue = new LinkedList<>();
            queue.offer(new PositionedNode(root, 0, 0));
            
            while (!queue.isEmpty()) {
                PositionedNode posNode = queue.poll();
                TreeNode node = posNode.node;
                int level = posNode.level;
                int column = posNode.column;
                
                // 將節點加入對應的垂直座標組
                result.computeIfAbsent(column, k -> new ArrayList<>()).add(node.val);
                
                if (node.left != null) {
                    queue.offer(new PositionedNode(node.left, level + 1, column - 1));
                }
                if (node.right != null) {
                    queue.offer(new PositionedNode(node.right, level + 1, column + 1));
                }
            }
            
            return result;
        }
        
        /**
         * 綜合執行所有走訪變形
         */
        public static TraversalResults performAllTraversals(TreeNode root) {
            TraversalResults results = new TraversalResults();
            
            results.levelOrderResult = levelOrderByLayers(root);
            results.zigzagResult = zigzagLevelOrder(root);
            results.rightmostResult = rightmostNodes(root);
            results.verticalResult = verticalTraversal(root);
            
            return results;
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
        
        /**
         * 標準層序走訪（用於對比）
         */
        public static List<Integer> standardLevelOrder(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            if (root == null) {
                return result;
            }
            
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                result.add(node.val);
                
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            
            return result;
        }
    }
    
    /**
     * 主程式 
     */
    public static void main(String[] args) {
        System.out.println("=== 層序走訪變形練習 ===\n");
        
        // 測試案例1：完全二元樹
        System.out.println("測試案例1：完全二元樹");
        Integer[] completeTreeValues = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        TreeNode completeTree = TreeBuilder.buildFromArray(completeTreeValues);
        TreeBuilder.printTree(completeTree, "樹結構:");
        
        System.out.println("標準層序走訪: " + TreeBuilder.standardLevelOrder(completeTree));
        
        TraversalResults results1 = LevelOrderVariations.performAllTraversals(completeTree);
        results1.displayAll();
        System.out.println();
        
        // 測試案例2：不平衡樹
        System.out.println("測試案例2：不平衡樹");
        Integer[] unbalancedTreeValues = {1, 2, 3, 4, null, null, 7, 8, null, null, null, null, null, null, 15};
        TreeNode unbalancedTree = TreeBuilder.buildFromArray(unbalancedTreeValues);
        TreeBuilder.printTree(unbalancedTree, "樹結構:");
        
        System.out.println("標準層序走訪: " + TreeBuilder.standardLevelOrder(unbalancedTree));
        
        TraversalResults results2 = LevelOrderVariations.performAllTraversals(unbalancedTree);
        results2.displayAll();
        System.out.println();
        
        // 測試案例3：單側樹（測試極端情況）
        System.out.println("測試案例3：單側樹");
        Integer[] leftSkewedValues = {1, 2, null, 4, null, null, null, 8};
        TreeNode leftSkewedTree = TreeBuilder.buildFromArray(leftSkewedValues);
        TreeBuilder.printTree(leftSkewedTree, "樹結構:");
        
        System.out.println("標準層序走訪: " + TreeBuilder.standardLevelOrder(leftSkewedTree));
        
        TraversalResults results3 = LevelOrderVariations.performAllTraversals(leftSkewedTree);
        results3.displayAll();
        
        // 額外展示：走訪模式比較
        System.out.println("\n=== 走訪模式比較（完全二元樹）===");
        System.out.println("標準層序: " + TreeBuilder.standardLevelOrder(completeTree));
        
        List<List<Integer>> levelOrder = results1.getLevelOrderResult();
        List<Integer> flattened = new ArrayList<>();
        for (List<Integer> level : levelOrder) {
            flattened.addAll(level);
        }
        System.out.println("分層重組: " + flattened);
        
        List<List<Integer>> zigzag = results1.getZigzagResult();
        List<Integer> zigzagFlattened = new ArrayList<>();
        for (List<Integer> level : zigzag) {
            zigzagFlattened.addAll(level);
        }
        System.out.println("之字形序: " + zigzagFlattened);
        
        System.out.println("右側視圖: " + results1.getRightmostResult());
        
        System.out.println("\n=== 完成 ==\n=");
    }
}