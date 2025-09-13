/*
練習 3.8 : 樹的重建
根據走訪結果重建二元樹：
1. 根據前序和中序走訪結果重建二元樹
2. 根據後序和中序走訪結果重建二元樹
3. 根據層序走訪結果重建完全二元樹
4. 驗證重建的樹是否正確
*/

import java.util.*;

public class TreeReconstruction {
    
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
    
    // 重建結果封裝類別
    static class ReconstructionResult {
        private TreeNode root;
        private boolean isValid;
        private String method;
        private List<String> errors;
        private long reconstructionTime;
        
        public ReconstructionResult(String method) {
            this.method = method;
            this.root = null;
            this.isValid = false;
            this.errors = new ArrayList<>();
            this.reconstructionTime = 0;
        }
        
        public void setSuccess(TreeNode root, long time) {
            this.root = root;
            this.isValid = true;
            this.reconstructionTime = time;
        }
        
        public void addError(String error) {
            this.errors.add(error);
            this.isValid = false;
        }
        
        public TreeNode getRoot() { return root; }
        public boolean isValid() { return isValid; }
        public String getMethod() { return method; }
        public List<String> getErrors() { return errors; }
        
        public void displayResult() {
            System.out.println("重建方法: " + method);
            System.out.println("重建狀態: " + (isValid ? "成功" : "失敗"));
            System.out.println("重建時間: " + reconstructionTime + " ms");
            
            if (!isValid && !errors.isEmpty()) {
                System.out.println("錯誤資訊:");
                for (String error : errors) {
                    System.out.println("  - " + error);
                }
            }
        }
    }
    
    // 樹重建核心類別
    static class TreeReconstructor {
        
        /**
         * 根據前序和中序走訪結果重建二元樹
         */
        public static ReconstructionResult buildFromPreorderInorder(int[] preorder, int[] inorder) {
            ReconstructionResult result = new ReconstructionResult("前序 + 中序重建");
            long startTime = System.currentTimeMillis();
            
            // 輸入驗證
            if (!validateInput(preorder, inorder, result)) {
                return result;
            }
            
            try {
                // 建立中序序列的值到索引映射，加速查找
                Map<Integer, Integer> inorderMap = new HashMap<>();
                for (int i = 0; i < inorder.length; i++) {
                    if (inorderMap.containsKey(inorder[i])) {
                        result.addError("中序序列包含重複值: " + inorder[i]);
                        return result;
                    }
                    inorderMap.put(inorder[i], i);
                }
                
                TreeNode root = buildFromPreorderInorderHelper(preorder, 0, preorder.length - 1,
                                                               inorder, 0, inorder.length - 1, inorderMap);
                
                long endTime = System.currentTimeMillis();
                result.setSuccess(root, endTime - startTime);
                
            } catch (Exception e) {
                result.addError("重建過程發生錯誤: " + e.getMessage());
            }
            
            return result;
        }
        
        private static TreeNode buildFromPreorderInorderHelper(int[] preorder, int preStart, int preEnd,
                                                               int[] inorder, int inStart, int inEnd,
                                                               Map<Integer, Integer> inorderMap) {
            if (preStart > preEnd || inStart > inEnd) {
                return null;
            }
            
            int rootVal = preorder[preStart];
            TreeNode root = new TreeNode(rootVal);
            
            Integer rootIndex = inorderMap.get(rootVal);
            if (rootIndex == null || rootIndex < inStart || rootIndex > inEnd) {
                throw new RuntimeException("在中序序列中找不到根節點: " + rootVal);
            }
            
            int leftTreeSize = rootIndex - inStart;
            
            root.left = buildFromPreorderInorderHelper(preorder, preStart + 1, preStart + leftTreeSize,
                                                       inorder, inStart, rootIndex - 1, inorderMap);
            
            root.right = buildFromPreorderInorderHelper(preorder, preStart + leftTreeSize + 1, preEnd,
                                                        inorder, rootIndex + 1, inEnd, inorderMap);
            
            return root;
        }
        
        /**
         * 根據後序和中序走訪結果重建二元樹
         */
        public static ReconstructionResult buildFromPostorderInorder(int[] postorder, int[] inorder) {
            ReconstructionResult result = new ReconstructionResult("後序 + 中序重建");
            long startTime = System.currentTimeMillis();
            
            if (!validateInput(postorder, inorder, result)) {
                return result;
            }
            
            try {
                Map<Integer, Integer> inorderMap = new HashMap<>();
                for (int i = 0; i < inorder.length; i++) {
                    if (inorderMap.containsKey(inorder[i])) {
                        result.addError("中序序列包含重複值: " + inorder[i]);
                        return result;
                    }
                    inorderMap.put(inorder[i], i);
                }
                
                TreeNode root = buildFromPostorderInorderHelper(postorder, 0, postorder.length - 1,
                                                                inorder, 0, inorder.length - 1, inorderMap);
                
                long endTime = System.currentTimeMillis();
                result.setSuccess(root, endTime - startTime);
                
            } catch (Exception e) {
                result.addError("重建過程發生錯誤: " + e.getMessage());
            }
            
            return result;
        }
        
        private static TreeNode buildFromPostorderInorderHelper(int[] postorder, int postStart, int postEnd,
                                                                int[] inorder, int inStart, int inEnd,
                                                                Map<Integer, Integer> inorderMap) {
            if (postStart > postEnd || inStart > inEnd) {
                return null;
            }
            
            int rootVal = postorder[postEnd];
            TreeNode root = new TreeNode(rootVal);
            
            Integer rootIndex = inorderMap.get(rootVal);
            if (rootIndex == null || rootIndex < inStart || rootIndex > inEnd) {
                throw new RuntimeException("在中序序列中找不到根節點: " + rootVal);
            }
            
            int leftTreeSize = rootIndex - inStart;
            
            root.left = buildFromPostorderInorderHelper(postorder, postStart, postStart + leftTreeSize - 1,
                                                        inorder, inStart, rootIndex - 1, inorderMap);
            
            root.right = buildFromPostorderInorderHelper(postorder, postStart + leftTreeSize, postEnd - 1,
                                                         inorder, rootIndex + 1, inEnd, inorderMap);
            
            return root;
        }
        
        /**
         * 根據層序走訪結果重建完全二元樹
         */
        public static ReconstructionResult buildFromLevelOrder(Integer[] levelOrder) {
            ReconstructionResult result = new ReconstructionResult("層序重建完全二元樹");
            long startTime = System.currentTimeMillis();
            
            if (levelOrder == null || levelOrder.length == 0) {
                result.addError("層序序列為空");
                return result;
            }
            
            if (levelOrder[0] == null) {
                result.addError("根節點不能為null");
                return result;
            }
            
            try {
                TreeNode root = new TreeNode(levelOrder[0]);
                Queue<TreeNode> queue = new LinkedList<>();
                queue.offer(root);
                
                int index = 1;
                while (!queue.isEmpty() && index < levelOrder.length) {
                    TreeNode current = queue.poll();
                    
                    // 處理左子節點
                    if (index < levelOrder.length && levelOrder[index] != null) {
                        current.left = new TreeNode(levelOrder[index]);
                        queue.offer(current.left);
                    }
                    index++;
                    
                    // 處理右子節點
                    if (index < levelOrder.length && levelOrder[index] != null) {
                        current.right = new TreeNode(levelOrder[index]);
                        queue.offer(current.right);
                    }
                    index++;
                }
                
                long endTime = System.currentTimeMillis();
                result.setSuccess(root, endTime - startTime);
                
            } catch (Exception e) {
                result.addError("重建過程發生錯誤: " + e.getMessage());
            }
            
            return result;
        }
        
        /**
         * 輸入驗證
         */
        private static boolean validateInput(int[] seq1, int[] seq2, ReconstructionResult result) {
            if (seq1 == null || seq2 == null) {
                result.addError("輸入序列不能為null");
                return false;
            }
            
            if (seq1.length != seq2.length) {
                result.addError("兩個序列長度不匹配: " + seq1.length + " vs " + seq2.length);
                return false;
            }
            
            if (seq1.length == 0) {
                result.addError("輸入序列為空");
                return false;
            }
            
            // 檢查兩個序列是否包含相同的元素
            Set<Integer> set1 = new HashSet<>();
            Set<Integer> set2 = new HashSet<>();
            
            for (int val : seq1) {
                set1.add(val);
            }
            for (int val : seq2) {
                set2.add(val);
            }
            
            if (!set1.equals(set2)) {
                result.addError("兩個序列包含的元素不相同");
                return false;
            }
            
            return true;
        }
    }
    
    // 樹驗證器類別
    static class TreeValidator {
        
        /**
         * 驗證重建的樹是否正確
         */
        public static ValidationReport validateReconstructedTree(TreeNode root, 
                                                                 int[] originalPreorder,
                                                                 int[] originalInorder,
                                                                 int[] originalPostorder) {
            ValidationReport report = new ValidationReport();
            
            if (root == null) {
                if (originalPreorder.length == 0) {
                    report.addSuccess("空樹驗證通過");
                } else {
                    report.addError("重建結果為空樹，但原始序列不為空");
                }
                return report;
            }
            
            // 重新遍歷重建的樹
            List<Integer> newPreorder = new ArrayList<>();
            List<Integer> newInorder = new ArrayList<>();
            List<Integer> newPostorder = new ArrayList<>();
            
            preorderTraversal(root, newPreorder);
            inorderTraversal(root, newInorder);
            postorderTraversal(root, newPostorder);
            
            // 比較序列
            if (arraysEqual(newPreorder, originalPreorder)) {
                report.addSuccess("前序遍歷驗證通過");
            } else {
                report.addError("前序遍歷不匹配");
                report.addDetail("期望: " + Arrays.toString(originalPreorder));
                report.addDetail("實際: " + newPreorder.toString());
            }
            
            if (arraysEqual(newInorder, originalInorder)) {
                report.addSuccess("中序遍歷驗證通過");
            } else {
                report.addError("中序遍歷不匹配");
                report.addDetail("期望: " + Arrays.toString(originalInorder));
                report.addDetail("實際: " + newInorder.toString());
            }
            
            if (arraysEqual(newPostorder, originalPostorder)) {
                report.addSuccess("後序遍歷驗證通過");
            } else {
                report.addError("後序遍歷不匹配");
                report.addDetail("期望: " + Arrays.toString(originalPostorder));
                report.addDetail("實際: " + newPostorder.toString());
            }
            
            // 結構驗證
            int nodeCount = countNodes(root);
            if (nodeCount == originalPreorder.length) {
                report.addSuccess("節點數量驗證通過: " + nodeCount);
            } else {
                report.addError("節點數量不匹配: 期望 " + originalPreorder.length + ", 實際 " + nodeCount);
            }
            
            return report;
        }
        
        /**
         * 驗證層序重建的樹
         */
        public static ValidationReport validateLevelOrderTree(TreeNode root, Integer[] originalLevelOrder) {
            ValidationReport report = new ValidationReport();
            
            if (root == null) {
                if (originalLevelOrder.length == 0) {
                    report.addSuccess("空樹驗證通過");
                } else {
                    report.addError("重建結果為空樹，但原始序列不為空");
                }
                return report;
            }
            
            List<Integer> newLevelOrder = levelOrderTraversal(root);
            
            // 比較層序遍歷結果（忽略末尾的null值）
            List<Integer> originalFiltered = new ArrayList<>();
            for (Integer val : originalLevelOrder) {
                if (val != null) {
                    originalFiltered.add(val);
                }
            }
            
            if (newLevelOrder.equals(originalFiltered)) {
                report.addSuccess("層序遍歷驗證通過");
            } else {
                report.addError("層序遍歷不匹配");
                report.addDetail("期望: " + originalFiltered.toString());
                report.addDetail("實際: " + newLevelOrder.toString());
            }
            
            return report;
        }
        
        // 遍歷方法
        private static void preorderTraversal(TreeNode node, List<Integer> result) {
            if (node != null) {
                result.add(node.val);
                preorderTraversal(node.left, result);
                preorderTraversal(node.right, result);
            }
        }
        
        private static void inorderTraversal(TreeNode node, List<Integer> result) {
            if (node != null) {
                inorderTraversal(node.left, result);
                result.add(node.val);
                inorderTraversal(node.right, result);
            }
        }
        
        private static void postorderTraversal(TreeNode node, List<Integer> result) {
            if (node != null) {
                postorderTraversal(node.left, result);
                postorderTraversal(node.right, result);
                result.add(node.val);
            }
        }
        
        private static List<Integer> levelOrderTraversal(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            if (root == null) return result;
            
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                result.add(node.val);
                
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            
            return result;
        }
        
        private static boolean arraysEqual(List<Integer> list, int[] array) {
            if (list.size() != array.length) return false;
            for (int i = 0; i < array.length; i++) {
                if (!list.get(i).equals(array[i])) return false;
            }
            return true;
        }
        
        private static int countNodes(TreeNode node) {
            if (node == null) return 0;
            return 1 + countNodes(node.left) + countNodes(node.right);
        }
    }
    
    // 驗證報告類別
    static class ValidationReport {
        private List<String> successes;
        private List<String> errors;
        private List<String> details;
        
        public ValidationReport() {
            this.successes = new ArrayList<>();
            this.errors = new ArrayList<>();
            this.details = new ArrayList<>();
        }
        
        public void addSuccess(String message) { successes.add(message); }
        public void addError(String message) { errors.add(message); }
        public void addDetail(String detail) { details.add(detail); }
        
        public boolean isValid() { return errors.isEmpty(); }
        
        public void displayReport() {
            System.out.println("=== 驗證報告 ===");
            System.out.println("驗證狀態: " + (isValid() ? "通過" : "失敗"));
            
            if (!successes.isEmpty()) {
                System.out.println("成功項目:");
                for (String success : successes) {
                    System.out.println("  ✓ " + success);
                }
            }
            
            if (!errors.isEmpty()) {
                System.out.println("錯誤項目:");
                for (String error : errors) {
                    System.out.println("  ✗ " + error);
                }
            }
            
            if (!details.isEmpty()) {
                System.out.println("詳細資訊:");
                for (String detail : details) {
                    System.out.println("    " + detail);
                }
            }
        }
    }
    
    // 樹視覺化輔助類別
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
    }
    
    public static void main(String[] args) {
        System.out.println("=== 樹的重建練習 ===\n");
        
        // 測試案例1：標準二元樹
        System.out.println("測試案例1：標準二元樹重建");
        
        int[] preorder1 = {3, 9, 20, 15, 7};
        int[] inorder1 = {9, 3, 15, 20, 7};
        int[] postorder1 = {9, 15, 7, 20, 3};
        
        System.out.println("原始遍歷序列:");
        System.out.println("前序: " + Arrays.toString(preorder1));
        System.out.println("中序: " + Arrays.toString(inorder1));
        System.out.println("後序: " + Arrays.toString(postorder1));
        
        // 1. 前序+中序重建
        System.out.println("\n1. 前序 + 中序重建:");
        ReconstructionResult result1 = TreeReconstructor.buildFromPreorderInorder(preorder1, inorder1);
        result1.displayResult();
        
        if (result1.isValid()) {
            TreeVisualizer.printTree(result1.getRoot(), "重建的樹結構:");
            ValidationReport report1 = TreeValidator.validateReconstructedTree(
                result1.getRoot(), preorder1, inorder1, postorder1);
            report1.displayReport();
        }
        
        // 2. 後序+中序重建
        System.out.println("\n2. 後序 + 中序重建:");
        ReconstructionResult result2 = TreeReconstructor.buildFromPostorderInorder(postorder1, inorder1);
        result2.displayResult();
        
        if (result2.isValid()) {
            TreeVisualizer.printTree(result2.getRoot(), "重建的樹結構:");
            ValidationReport report2 = TreeValidator.validateReconstructedTree(
                result2.getRoot(), preorder1, inorder1, postorder1);
            report2.displayReport();
        }
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // 測試案例2：完全二元樹的層序重建
        System.out.println("測試案例2：完全二元樹層序重建");
        
        Integer[] levelOrder2 = {1, 2, 3, 4, 5, 6, 7, null, null, null, null, 8, 9};
        System.out.println("層序序列: " + Arrays.toString(levelOrder2));
        
        ReconstructionResult result3 = TreeReconstructor.buildFromLevelOrder(levelOrder2);
        result3.displayResult();
        
        if (result3.isValid()) {
            TreeVisualizer.printTree(result3.getRoot(), "重建的完全二元樹:");
            ValidationReport report3 = TreeValidator.validateLevelOrderTree(result3.getRoot(), levelOrder2);
            report3.displayReport();
        }
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // 測試案例3：單節點樹
        System.out.println("測試案例3：單節點樹重建");
        
        int[] preorder3 = {42};
        int[] inorder3 = {42};
        int[] postorder3 = {42};
        
        System.out.println("單節點樹序列: " + Arrays.toString(preorder3));
        
        ReconstructionResult result4 = TreeReconstructor.buildFromPreorderInorder(preorder3, inorder3);
        result4.displayResult();
        
        if (result4.isValid()) {
            TreeVisualizer.printTree(result4.getRoot(), "重建的單節點樹:");
            ValidationReport report4 = TreeValidator.validateReconstructedTree(
                result4.getRoot(), preorder3, inorder3, postorder3);
            report4.displayReport();
        }
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // 測試案例4：錯誤輸入測試
        System.out.println("測試案例4：錯誤輸入測試");
        
        int[] invalidPreorder = {1, 2, 3};
        int[] invalidInorder = {1, 4, 3}; // 不同的元素
        
        System.out.println("無效前序: " + Arrays.toString(invalidPreorder));
        System.out.println("無效中序: " + Arrays.toString(invalidInorder));
        
        ReconstructionResult result5 = TreeReconstructor.buildFromPreorderInorder(invalidPreorder, invalidInorder);
        result5.displayResult();
        
        // 測試長度不匹配
        int[] shortInorder = {1, 2};
        ReconstructionResult result6 = TreeReconstructor.buildFromPreorderInorder(invalidPreorder, shortInorder);
        result6.displayResult();
        
        System.out.println("\n=== 完成 ===");
    }
}