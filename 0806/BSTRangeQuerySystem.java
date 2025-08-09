/*
練習 3.2：BST範圍查詢系統
建立一個BST範圍查詢系統：
1. 實作範圍查詢：找出在 [min, max] 範圍內的所有節點
2. 實作範圍計數：計算在指定範圍內的節點數量
3. 實作範圍總和：計算在指定範圍內所有節點值的總和
4. 實作最接近查詢：找出最接近給定值的節點
*/

import java.util.*;

public class BSTRangeQuerySystem {
    
    // BST節點類別
    static class BSTNode {
        int val;
        BSTNode left;
        BSTNode right;
        
        public BSTNode(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }
    
    // 範圍查詢結果封裝類別
    static class RangeQueryResult {
        private List<Integer> values;
        private int count;
        private long sum;
        private int min;
        private int max;
        
        public RangeQueryResult() {
            this.values = new ArrayList<>();
            this.count = 0;
            this.sum = 0;
            this.min = Integer.MAX_VALUE;
            this.max = Integer.MIN_VALUE;
        }
        
        public void addValue(int val) {
            values.add(val);
            count++;
            sum += val;
            min = Math.min(min, val);
            max = Math.max(max, val);
        }
        
        public boolean isEmpty() {
            return count == 0;
        }
        
        public void display(int rangeMin, int rangeMax) {
            if (isEmpty()) {
                System.out.println("範圍 [" + rangeMin + ", " + rangeMax + "] 內沒有找到任何節點");
                return;
            }
            
            System.out.println("範圍查詢結果 [" + rangeMin + ", " + rangeMax + "]:");
            System.out.println("  找到的值: " + values);
            System.out.println("  節點數量: " + count);
            System.out.println("  總和: " + sum);
            System.out.println("  平均值: " + String.format("%.2f", (double) sum / count));
            System.out.println("  範圍內最小值: " + min);
            System.out.println("  範圍內最大值: " + max);
        }
        
        // Getters
        public List<Integer> getValues() { return values; }
        public int getCount() { return count; }
        public long getSum() { return sum; }
    }
    
    // BST核心操作類別
    static class BST {
        private BSTNode root;
        private int nodeCount;
        private List<String> operationHistory;
        
        public BST() {
            this.root = null;
            this.nodeCount = 0;
            this.operationHistory = new ArrayList<>();
        }
        
        /**
         * 插入節點
         */
        public void insert(int val) {
            root = insertHelper(root, val);
            nodeCount++;
            operationHistory.add("插入: " + val);
        }
        
        private BSTNode insertHelper(BSTNode node, int val) {
            if (node == null) {
                return new BSTNode(val);
            }
            
            if (val <= node.val) {
                node.left = insertHelper(node.left, val);
            } else {
                node.right = insertHelper(node.right, val);
            }
            
            return node;
        }
        
        /**
         * 刪除節點
         */
        public boolean delete(int val) {
            int originalCount = nodeCount;
            root = deleteHelper(root, val);
            
            if (nodeCount < originalCount) {
                operationHistory.add("刪除: " + val);
                return true;
            }
            return false;
        }
        
        private BSTNode deleteHelper(BSTNode node, int val) {
            if (node == null) {
                return null;
            }
            
            if (val < node.val) {
                node.left = deleteHelper(node.left, val);
            } else if (val > node.val) {
                node.right = deleteHelper(node.right, val);
            } else {
                nodeCount--;
                
                if (node.left == null) {
                    return node.right;
                } else if (node.right == null) {
                    return node.left;
                }
                
                // 找到右子樹的最小值
                int minVal = findMin(node.right);
                node.val = minVal;
                node.right = deleteHelper(node.right, minVal);
            }
            
            return node;
        }
        
        private int findMin(BSTNode node) {
            while (node.left != null) {
                node = node.left;
            }
            return node.val;
        }
        
        /**
         * 範圍查詢 - 找出在 [min, max] 範圍內的所有節點
         */
        public RangeQueryResult rangeQuery(int min, int max) {
            RangeQueryResult result = new RangeQueryResult();
            rangeQueryHelper(root, min, max, result);
            operationHistory.add("範圍查詢: [" + min + ", " + max + "] -> " + result.getCount() + " 個節點");
            return result;
        }
        
        private void rangeQueryHelper(BSTNode node, int min, int max, RangeQueryResult result) {
            if (node == null) {
                return;
            }
            
            // 如果當前節點值在範圍內，加入結果
            if (node.val >= min && node.val <= max) {
                result.addValue(node.val);
            }
            
            // 剪枝：只在必要時搜尋子樹
            if (node.val > min) {
                rangeQueryHelper(node.left, min, max, result);
            }
            if (node.val < max) {
                rangeQueryHelper(node.right, min, max, result);
            }
        }
        
        /**
         * 範圍計數
         */
        public int rangeCount(int min, int max) {
            return rangeCountHelper(root, min, max);
        }
        
        private int rangeCountHelper(BSTNode node, int min, int max) {
            if (node == null) {
                return 0;
            }
            
            int count = 0;
            
            if (node.val >= min && node.val <= max) {
                count = 1;
            }
            
            if (node.val > min) {
                count += rangeCountHelper(node.left, min, max);
            }
            if (node.val < max) {
                count += rangeCountHelper(node.right, min, max);
            }
            
            return count;
        }
        
        /**
         * 範圍總和
         */
        public long rangeSum(int min, int max) {
            return rangeSumHelper(root, min, max);
        }
        
        private long rangeSumHelper(BSTNode node, int min, int max) {
            if (node == null) {
                return 0;
            }
            
            long sum = 0;
            
            if (node.val >= min && node.val <= max) {
                sum = node.val;
            }
            
            if (node.val > min) {
                sum += rangeSumHelper(node.left, min, max);
            }
            if (node.val < max) {
                sum += rangeSumHelper(node.right, min, max);
            }
            
            return sum;
        }
        
        /**
         * 最接近查詢
         */
        public Integer findClosest(int target) {
            if (root == null) {
                return null;
            }
            
            int[] closest = {root.val};
            findClosestHelper(root, target, closest);
            operationHistory.add("最接近查詢: " + target + " -> " + closest[0]);
            return closest[0];
        }
        
        private void findClosestHelper(BSTNode node, int target, int[] closest) {
            if (node == null) {
                return;
            }
            
            // 更新最接近的值
            if (Math.abs(node.val - target) < Math.abs(closest[0] - target) ||
                (Math.abs(node.val - target) == Math.abs(closest[0] - target) && node.val < closest[0])) {
                closest[0] = node.val;
            }
            
            // 根據BST性質決定搜尋方向
            if (target < node.val) {
                findClosestHelper(node.left, target, closest);
            } else if (target > node.val) {
                findClosestHelper(node.right, target, closest);
            }
        }
        
        /**
         * 顯示樹結構
         */
        public void displayTree() {
            if (root == null) {
                System.out.println("樹是空的");
                return;
            }
            
            System.out.println("BST結構 (共 " + nodeCount + " 個節點):");
            displayTreeHelper(root, "", true);
        }
        
        private void displayTreeHelper(BSTNode node, String prefix, boolean isLast) {
            if (node != null) {
                System.out.println(prefix + (isLast ? "└── " : "├── ") + node.val);
                
                if (node.left != null || node.right != null) {
                    if (node.left != null) {
                        displayTreeHelper(node.left, prefix + (isLast ? "    " : "│   "), node.right == null);
                    }
                    if (node.right != null) {
                        displayTreeHelper(node.right, prefix + (isLast ? "    " : "│   "), true);
                    }
                }
            }
        }
        
        /**
         * 取得樹的統計資訊
         */
        public void displayStatistics() {
            if (root == null) {
                System.out.println("樹統計: 空樹");
                return;
            }
            
            List<Integer> allValues = new ArrayList<>();
            collectAllValues(root, allValues);
            
            Collections.sort(allValues);
            
            System.out.println("樹統計資訊:");
            System.out.println("  節點總數: " + nodeCount);
            System.out.println("  最小值: " + allValues.get(0));
            System.out.println("  最大值: " + allValues.get(allValues.size() - 1));
            System.out.println("  高度: " + calculateHeight(root));
            System.out.println("  所有值: " + allValues);
        }
        
        private void collectAllValues(BSTNode node, List<Integer> values) {
            if (node != null) {
                collectAllValues(node.left, values);
                values.add(node.val);
                collectAllValues(node.right, values);
            }
        }
        
        private int calculateHeight(BSTNode node) {
            if (node == null) {
                return 0;
            }
            return 1 + Math.max(calculateHeight(node.left), calculateHeight(node.right));
        }
        
        /**
         * 顯示操作歷史
         */
        public void displayHistory() {
            System.out.println("操作歷史 (最近 " + Math.min(10, operationHistory.size()) + " 項):");
            int start = Math.max(0, operationHistory.size() - 10);
            for (int i = start; i < operationHistory.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + operationHistory.get(i));
            }
        }
        
        public boolean isEmpty() {
            return root == null;
        }
        
        public int getNodeCount() {
            return nodeCount;
        }
        
        public void clear() {
            root = null;
            nodeCount = 0;
            operationHistory.clear();
            operationHistory.add("清空樹");
        }
    }
    
    // 互動式介面類別
    static class InteractiveInterface {
        private BST bst;
        private Scanner scanner;
        
        public InteractiveInterface() {
            this.bst = new BST();
            this.scanner = new Scanner(System.in);
        }
        
        public void start() {
            System.out.println("=== BST範圍查詢系統 ===");
            System.out.println("歡迎使用！");
            
            // 初始化選項
            initializeBST();
            
            // 主程式迴圈
            while (true) {
                showMainMenu();
                int choice = getIntInput("請選擇操作 (1-5): ");
                
                switch (choice) {
                    case 1:
                        treeOperationsMenu();
                        break;
                    case 2:
                        rangeQueryMenu();
                        break;
                    case 3:
                        specialQueryMenu();
                        break;
                    case 4:
                        toolsMenu();
                        break;
                    case 5:
                        System.out.println("感謝使用BST範圍查詢系統！");
                        return;
                    default:
                        System.out.println("無效選項，請重新選擇。");
                }
                
                System.out.println(); // 空行分隔
            }
        }
        
        private void initializeBST() {
            System.out.println("\n請選擇初始化方式:");
            System.out.println("1. 手動逐一插入節點");
            System.out.println("2. 使用預設範例");
            System.out.println("3. 批量輸入 (逗號分隔)");
            System.out.println("4. 隨機生成");
            System.out.println("5. 暫時跳過 (稍後手動建立)");
            
            int choice = getIntInput("請選擇 (1-5): ");
            
            switch (choice) {
                case 1:
                    manualInsert();
                    break;
                case 2:
                    createSampleTree();
                    break;
                case 3:
                    batchInsert();
                    break;
                case 4:
                    randomGenerate();
                    break;
                case 5:
                    System.out.println("稍後可在主選單中建立樹結構。");
                    break;
                default:
                    System.out.println("無效選項，使用預設範例。");
                    createSampleTree();
            }
        }
        
        private void createSampleTree() {
            int[] sampleValues = {15, 10, 20, 8, 12, 17, 25, 6, 9, 11, 14, 16, 19, 22, 30};
            for (int val : sampleValues) {
                bst.insert(val);
            }
            System.out.println("已建立預設範例樹 (15個節點)");
            bst.displayTree();
        }
        
        private void manualInsert() {
            System.out.println("手動插入模式 (輸入 -1 結束):");
            while (true) {
                int val = getIntInput("請輸入要插入的值: ");
                if (val == -1) break;
                bst.insert(val);
                System.out.println("已插入 " + val);
            }
        }
        
        private void batchInsert() {
            System.out.print("請輸入數值 (逗號分隔): ");
            String input = scanner.nextLine();
            try {
                String[] parts = input.split(",");
                for (String part : parts) {
                    int val = Integer.parseInt(part.trim());
                    bst.insert(val);
                }
                System.out.println("已批量插入 " + parts.length + " 個值");
            } catch (NumberFormatException e) {
                System.out.println("輸入格式錯誤，請使用逗號分隔的數字。");
            }
        }
        
        private void randomGenerate() {
            int count = getIntInput("請輸入要生成的節點數量: ");
            int min = getIntInput("請輸入數值範圍最小值: ");
            int max = getIntInput("請輸入數值範圍最大值: ");
            
            Random random = new Random();
            for (int i = 0; i < count; i++) {
                int val = random.nextInt(max - min + 1) + min;
                bst.insert(val);
            }
            System.out.println("已隨機生成 " + count + " 個節點");
        }
        
        private void showMainMenu() {
            System.out.println("─────────────────────────");
            System.out.println("當前BST狀態: " + bst.getNodeCount() + " 個節點");
            System.out.println("─────────────────────────");
            System.out.println("1. 樹結構操作");
            System.out.println("2. 範圍查詢功能");
            System.out.println("3. 特殊查詢");
            System.out.println("4. 工具功能");
            System.out.println("5. 退出系統");
        }
        
        private void treeOperationsMenu() {
            System.out.println("\n=== 樹結構操作 ===");
            System.out.println("1. 插入節點");
            System.out.println("2. 刪除節點");
            System.out.println("3. 顯示樹結構");
            System.out.println("4. 返回主選單");
            
            int choice = getIntInput("請選擇操作: ");
            
            switch (choice) {
                case 1:
                    int insertVal = getIntInput("請輸入要插入的值: ");
                    bst.insert(insertVal);
                    System.out.println("已插入 " + insertVal);
                    bst.displayTree();
                    break;
                case 2:
                    if (bst.isEmpty()) {
                        System.out.println("樹是空的，無法刪除。");
                        break;
                    }
                    int deleteVal = getIntInput("請輸入要刪除的值: ");
                    if (bst.delete(deleteVal)) {
                        System.out.println("已刪除 " + deleteVal);
                        bst.displayTree();
                    } else {
                        System.out.println("值 " + deleteVal + " 不存在於樹中");
                    }
                    break;
                case 3:
                    bst.displayTree();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("無效選項。");
            }
        }
        
        private void rangeQueryMenu() {
            if (bst.isEmpty()) {
                System.out.println("樹是空的，請先插入一些節點。");
                return;
            }
            
            System.out.println("\n=== 範圍查詢功能 ===");
            System.out.println("1. 完整範圍查詢 (顯示所有資訊)");
            System.out.println("2. 範圍計數");
            System.out.println("3. 範圍總和");
            System.out.println("4. 多範圍比較");
            System.out.println("5. 返回主選單");
            
            int choice = getIntInput("請選擇操作: ");
            
            switch (choice) {
                case 1:
                    performRangeQuery();
                    break;
                case 2:
                    performRangeCount();
                    break;
                case 3:
                    performRangeSum();
                    break;
                case 4:
                    performMultiRangeComparison();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("無效選項。");
            }
        }
        
        private void performRangeQuery() {
            int[] range = getRangeInput();
            if (range == null) return;
            
            RangeQueryResult result = bst.rangeQuery(range[0], range[1]);
            result.display(range[0], range[1]);
        }
        
        private void performRangeCount() {
            int[] range = getRangeInput();
            if (range == null) return;
            
            int count = bst.rangeCount(range[0], range[1]);
            System.out.println("範圍 [" + range[0] + ", " + range[1] + "] 內有 " + count + " 個節點");
        }
        
        private void performRangeSum() {
            int[] range = getRangeInput();
            if (range == null) return;
            
            long sum = bst.rangeSum(range[0], range[1]);
            System.out.println("範圍 [" + range[0] + ", " + range[1] + "] 內節點總和為 " + sum);
        }
        
        private void performMultiRangeComparison() {
            System.out.println("多範圍比較模式:");
            
            System.out.println("範圍A:");
            int[] rangeA = getRangeInput();
            if (rangeA == null) return;
            
            System.out.println("範圍B:");
            int[] rangeB = getRangeInput();
            if (rangeB == null) return;
            
            RangeQueryResult resultA = bst.rangeQuery(rangeA[0], rangeA[1]);
            RangeQueryResult resultB = bst.rangeQuery(rangeB[0], rangeB[1]);
            
            System.out.println("\n比較結果:");
            System.out.println("範圍A [" + rangeA[0] + ", " + rangeA[1] + "]: " + 
                             resultA.getCount() + "個節點, 總和=" + resultA.getSum());
            System.out.println("範圍B [" + rangeB[0] + ", " + rangeB[1] + "]: " + 
                             resultB.getCount() + "個節點, 總和=" + resultB.getSum());
        }
        
        private void specialQueryMenu() {
            if (bst.isEmpty()) {
                System.out.println("樹是空的，請先插入一些節點。");
                return;
            }
            
            System.out.println("\n=== 特殊查詢 ===");
            System.out.println("1. 最接近值查詢");
            System.out.println("2. 返回主選單");
            
            int choice = getIntInput("請選擇操作: ");
            
            switch (choice) {
                case 1:
                    int target = getIntInput("請輸入目標值: ");
                    Integer closest = bst.findClosest(target);
                    if (closest != null) {
                        int distance = Math.abs(closest - target);
                        System.out.println("最接近 " + target + " 的值是 " + closest + 
                                         " (距離: " + distance + ")");
                    }
                    break;
                case 2:
                    return;
                default:
                    System.out.println("無效選項。");
            }
        }
        
        private void toolsMenu() {
            System.out.println("\n=== 工具功能 ===");
            System.out.println("1. 樹狀態統計");
            System.out.println("2. 操作歷史");
            System.out.println("3. 重設樹");
            System.out.println("4. 返回主選單");
            
            int choice = getIntInput("請選擇操作: ");
            
            switch (choice) {
                case 1:
                    bst.displayStatistics();
                    break;
                case 2:
                    bst.displayHistory();
                    break;
                case 3:
                    System.out.print("確定要重設樹嗎？這將刪除所有節點 (y/n): ");
                    String confirm = scanner.nextLine();
                    if (confirm.toLowerCase().equals("y")) {
                        bst.clear();
                        System.out.println("樹已重設");
                    }
                    break;
                case 4:
                    return;
                default:
                    System.out.println("無效選項。");
            }
        }
        
        private int[] getRangeInput() {
            try {
                int min = getIntInput("請輸入範圍最小值: ");
                int max = getIntInput("請輸入範圍最大值: ");
                
                if (min > max) {
                    System.out.print("最小值大於最大值，是否交換？ (y/n): ");
                    String swap = scanner.nextLine();
                    if (swap.toLowerCase().equals("y")) {
                        int temp = min;
                        min = max;
                        max = temp;
                    } else {
                        System.out.println("無效範圍，操作取消。");
                        return null;
                    }
                }
                
                return new int[]{min, max};
            } catch (Exception e) {
                System.out.println("輸入錯誤，操作取消。");
                return null;
            }
        }
        
        private int getIntInput(String prompt) {
            while (true) {
                try {
                    System.out.print(prompt);
                    return Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("請輸入有效的整數。");
                }
            }
        }
    }
    
    /**
     * 主程式
     */
    public static void main(String[] args) {
        InteractiveInterface ui = new InteractiveInterface();
        ui.start();
    }
}