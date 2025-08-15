/*
練習 3.4：BST第k小/大元素
實作BST中的第k小/大元素查詢：
1. 找出BST中第k小的元素
2. 找出BST中第k大的元素
3. 找出BST中第k小到第j小之間的所有元素
4. 實作一個支援動態插入刪除的第k小元素查詢
*/

import java.util.*;

public class BSTKthElement {
    
    // 基本BST節點類別
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
    
    // 進階BST節點類別（支援動態查詢）
    static class EnhancedTreeNode {
        int val;
        EnhancedTreeNode left;
        EnhancedTreeNode right;
        int subtreeSize; // 子樹節點數量
        
        public EnhancedTreeNode(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
            this.subtreeSize = 1;
        }
    }
    
    // 基本BST操作類別
    static class BasicBST {
        private TreeNode root;
        
        public BasicBST() {
            this.root = null;
        }
        
        /**
         * 插入節點
         */
        public void insert(int val) {
            root = insertHelper(root, val);
        }
        
        private TreeNode insertHelper(TreeNode node, int val) {
            if (node == null) {
                return new TreeNode(val);
            }
            
            if (val <= node.val) {
                node.left = insertHelper(node.left, val);
            } else {
                node.right = insertHelper(node.right, val);
            }
            
            return node;
        }
        
        /**
         * 找出BST中第k小的元素
         */
        public Integer findKthSmallest(int k) {
            if (k <= 0) {
                return null;
            }
            
            int[] counter = {k};
            return findKthSmallestHelper(root, counter);
        }
        
        private Integer findKthSmallestHelper(TreeNode node, int[] counter) {
            if (node == null) {
                return null;
            }
            
            // 先遍歷左子樹
            Integer leftResult = findKthSmallestHelper(node.left, counter);
            if (leftResult != null) {
                return leftResult;
            }
            
            // 處理當前節點
            counter[0]--;
            if (counter[0] == 0) {
                return node.val;
            }
            
            // 遍歷右子樹
            return findKthSmallestHelper(node.right, counter);
        }
        
        /**
         * 找出BST中第k大的元素
         */
        public Integer findKthLargest(int k) {
            if (k <= 0) {
                return null;
            }
            
            int totalNodes = countNodes(root);
            if (k > totalNodes) {
                return null;
            }
            
            // 第k大 = 第(totalNodes - k + 1)小
            return findKthSmallest(totalNodes - k + 1);
        }
        
        /**
         * 找出BST中第k小到第j小之間的所有元素
         */
        public List<Integer> findKthToJthSmallest(int k, int j) {
            List<Integer> result = new ArrayList<>();
            
            if (k <= 0 || j <= 0 || k > j) {
                return result;
            }
            
            int[] counter = {1};
            findRangeHelper(root, k, j, counter, result);
            return result;
        }
        
        private void findRangeHelper(TreeNode node, int k, int j, int[] counter, List<Integer> result) {
            if (node == null) {
                return;
            }
            
            // 遍歷左子樹
            findRangeHelper(node.left, k, j, counter, result);
            
            // 處理當前節點
            if (counter[0] >= k && counter[0] <= j) {
                result.add(node.val);
            }
            counter[0]++;
            
            // 如果已經超過j，不需要繼續遍歷
            if (counter[0] > j + 1) {
                return;
            }
            
            // 遍歷右子樹
            findRangeHelper(node.right, k, j, counter, result);
        }
        
        /**
         * 計算節點總數
         */
        private int countNodes(TreeNode node) {
            if (node == null) {
                return 0;
            }
            return 1 + countNodes(node.left) + countNodes(node.right);
        }
        
        /**
         * 獲取所有元素（中序遍歷）
         */
        public List<Integer> getAllElements() {
            List<Integer> result = new ArrayList<>();
            inorderTraversal(root, result);
            return result;
        }
        
        private void inorderTraversal(TreeNode node, List<Integer> result) {
            if (node != null) {
                inorderTraversal(node.left, result);
                result.add(node.val);
                inorderTraversal(node.right, result);
            }
        }
        
        public TreeNode getRoot() {
            return root;
        }
    }
    
    // 動態BST類別（支援高效的第k小查詢）
    static class DynamicBST {
        private EnhancedTreeNode root;
        
        public DynamicBST() {
            this.root = null;
        }
        
        /**
         * 插入節點（維護子樹大小）
         */
        public void insert(int val) {
            root = insertHelper(root, val);
        }
        
        private EnhancedTreeNode insertHelper(EnhancedTreeNode node, int val) {
            if (node == null) {
                return new EnhancedTreeNode(val);
            }
            
            // 增加子樹大小
            node.subtreeSize++;
            
            if (val <= node.val) {
                node.left = insertHelper(node.left, val);
            } else {
                node.right = insertHelper(node.right, val);
            }
            
            return node;
        }
        
        /**
         * 刪除節點（維護子樹大小）
         */
        public boolean delete(int val) {
            int originalSize = getSize();
            root = deleteHelper(root, val);
            return getSize() < originalSize;
        }
        
        private EnhancedTreeNode deleteHelper(EnhancedTreeNode node, int val) {
            if (node == null) {
                return null;
            }
            
            if (val < node.val) {
                node.left = deleteHelper(node.left, val);
                if (node.left == null || getSubtreeSize(node.left) < node.subtreeSize - 1) {
                    node.subtreeSize--;
                }
            } else if (val > node.val) {
                node.right = deleteHelper(node.right, val);
                if (node.right == null || getSubtreeSize(node.right) < node.subtreeSize - 1) {
                    node.subtreeSize--;
                }
            } else {
                // 找到要刪除的節點
                if (node.left == null) {
                    return node.right;
                } else if (node.right == null) {
                    return node.left;
                }
                
                // 找到右子樹的最小值
                EnhancedTreeNode successor = findMin(node.right);
                node.val = successor.val;
                node.right = deleteHelper(node.right, successor.val);
                node.subtreeSize--;
            }
            
            return node;
        }
        
        private EnhancedTreeNode findMin(EnhancedTreeNode node) {
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }
        
        /**
         * 高效的第k小元素查詢
         */
        public Integer findKthSmallest(int k) {
            if (k <= 0 || k > getSize()) {
                return null;
            }
            
            return findKthSmallestHelper(root, k);
        }
        
        private Integer findKthSmallestHelper(EnhancedTreeNode node, int k) {
            if (node == null) {
                return null;
            }
            
            int leftSize = getSubtreeSize(node.left);
            
            if (k <= leftSize) {
                // 第k小在左子樹
                return findKthSmallestHelper(node.left, k);
            } else if (k == leftSize + 1) {
                // 當前節點就是第k小
                return node.val;
            } else {
                // 第k小在右子樹
                return findKthSmallestHelper(node.right, k - leftSize - 1);
            }
        }
        
        /**
         * 獲取子樹大小
         */
        private int getSubtreeSize(EnhancedTreeNode node) {
            return node == null ? 0 : node.subtreeSize;
        }
        
        /**
         * 獲取整棵樹的大小
         */
        public int getSize() {
            return getSubtreeSize(root);
        }
        
        /**
         * 獲取所有元素（用於驗證）
         */
        public List<Integer> getAllElements() {
            List<Integer> result = new ArrayList<>();
            inorderTraversal(root, result);
            return result;
        }
        
        private void inorderTraversal(EnhancedTreeNode node, List<Integer> result) {
            if (node != null) {
                inorderTraversal(node.left, result);
                result.add(node.val);
                inorderTraversal(node.right, result);
            }
        }
        
        /**
         * 獲取元素的排名（第幾小）
         */
        public int getRank(int val) {
            return getRankHelper(root, val);
        }
        
        private int getRankHelper(EnhancedTreeNode node, int val) {
            if (node == null) {
                return 0;
            }
            
            if (val < node.val) {
                return getRankHelper(node.left, val);
            } else if (val > node.val) {
                return getSubtreeSize(node.left) + 1 + getRankHelper(node.right, val);
            } else {
                return getSubtreeSize(node.left) + 1;
            }
        }
    }
    
    // 樹視覺化輔助類別
    static class TreeVisualizer {
        public static void printBasicTree(TreeNode root) {
            if (root == null) {
                System.out.println("空樹");
                return;
            }
            printBasicTreeHelper(root, "", true);
        }
        
        private static void printBasicTreeHelper(TreeNode node, String prefix, boolean isLast) {
            if (node != null) {
                System.out.println(prefix + (isLast ? "└── " : "├── ") + node.val);
                
                if (node.left != null || node.right != null) {
                    if (node.left != null) {
                        printBasicTreeHelper(node.left, prefix + (isLast ? "    " : "│   "), node.right == null);
                    }
                    if (node.right != null) {
                        printBasicTreeHelper(node.right, prefix + (isLast ? "    " : "│   "), true);
                    }
                }
            }
        }
        
        public static void printEnhancedTree(EnhancedTreeNode root) {
            if (root == null) {
                System.out.println("空樹");
                return;
            }
            printEnhancedTreeHelper(root, "", true);
        }
        
        private static void printEnhancedTreeHelper(EnhancedTreeNode node, String prefix, boolean isLast) {
            if (node != null) {
                System.out.println(prefix + (isLast ? "└── " : "├── ") + 
                                 node.val + " (size:" + node.subtreeSize + ")");
                
                if (node.left != null || node.right != null) {
                    if (node.left != null) {
                        printEnhancedTreeHelper(node.left, prefix + (isLast ? "    " : "│   "), node.right == null);
                    }
                    if (node.right != null) {
                        printEnhancedTreeHelper(node.right, prefix + (isLast ? "    " : "│   "), true);
                    }
                }
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== BST第k小/大元素練習 ===\n");
        
        // 建立基本BST
        BasicBST basicBST = new BasicBST();
        int[] values = {15, 10, 20, 8, 12, 17, 25, 6, 11, 13, 22, 30};
        
        System.out.println("建立基本BST，插入元素: " + Arrays.toString(values));
        for (int val : values) {
            basicBST.insert(val);
        }
        
        System.out.println("BST結構:");
        TreeVisualizer.printBasicTree(basicBST.getRoot());
        
        List<Integer> allElements = basicBST.getAllElements();
        System.out.println("中序遍歷結果（有序）: " + allElements);
        System.out.println();
        
        // 測試第k小元素查詢
        System.out.println("=== 第k小元素查詢 ===");
        for (int k = 1; k <= 5; k++) {
            Integer kthSmallest = basicBST.findKthSmallest(k);
            System.out.println("第" + k + "小的元素: " + kthSmallest);
        }
        System.out.println();
        
        // 測試第k大元素查詢
        System.out.println("=== 第k大元素查詢 ===");
        for (int k = 1; k <= 5; k++) {
            Integer kthLargest = basicBST.findKthLargest(k);
            System.out.println("第" + k + "大的元素: " + kthLargest);
        }
        System.out.println();
        
        // 測試範圍查詢
        System.out.println("=== 範圍查詢 ===");
        int rangeK = 3, rangeJ = 7;
        List<Integer> rangeElements = basicBST.findKthToJthSmallest(rangeK, rangeJ);
        System.out.println("第" + rangeK + "小到第" + rangeJ + "小的元素: " + rangeElements);
        
        rangeK = 2; rangeJ = 5;
        rangeElements = basicBST.findKthToJthSmallest(rangeK, rangeJ);
        System.out.println("第" + rangeK + "小到第" + rangeJ + "小的元素: " + rangeElements);
        System.out.println();
        
        // 測試動態BST
        System.out.println("=== 動態BST測試 ===");
        DynamicBST dynamicBST = new DynamicBST();
        
        System.out.println("插入元素到動態BST:");
        for (int val : values) {
            dynamicBST.insert(val);
            System.out.print(val + " ");
        }
        System.out.println();
        
        System.out.println("\n動態BST結構（顯示子樹大小）:");
        TreeVisualizer.printEnhancedTree(dynamicBST.root);
        
        System.out.println("動態BST中序遍歷: " + dynamicBST.getAllElements());
        System.out.println("樹的總大小: " + dynamicBST.getSize());
        System.out.println();
        
        // 測試動態查詢效率
        System.out.println("=== 動態查詢測試 ===");
        for (int queryK = 1; queryK <= 5; queryK++) {
            Integer kthSmallest = dynamicBST.findKthSmallest(queryK);
            System.out.println("第" + queryK + "小的元素: " + kthSmallest);
        }
        System.out.println();
        
        // 測試排名查詢
        System.out.println("=== 排名查詢測試 ===");
        int[] testValues = {10, 15, 20, 25};
        for (int val : testValues) {
            int rank = dynamicBST.getRank(val);
            System.out.println("元素 " + val + " 的排名（第幾小）: " + rank);
        }
        System.out.println();
        
        // 測試動態刪除
        System.out.println("=== 動態刪除測試 ===");
        int deleteValue = 15;
        System.out.println("刪除元素: " + deleteValue);
        boolean deleted = dynamicBST.delete(deleteValue);
        System.out.println("刪除成功: " + deleted);
        
        System.out.println("刪除後的BST結構:");
        TreeVisualizer.printEnhancedTree(dynamicBST.root);
        
        System.out.println("刪除後中序遍歷: " + dynamicBST.getAllElements());
        System.out.println("刪除後樹的大小: " + dynamicBST.getSize());
        
        // 驗證刪除後的第k小查詢
        System.out.println("\n刪除後的第k小查詢:");
        for (int k = 1; k <= 3; k++) {
            Integer kthSmallest = dynamicBST.findKthSmallest(k);
            System.out.println("第" + k + "小的元素: " + kthSmallest);
        }
        
        System.out.println("\n=== 練習完成 ===");
    }
}