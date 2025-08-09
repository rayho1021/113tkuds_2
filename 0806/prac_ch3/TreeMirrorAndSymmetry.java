/*
練習 3.3：樹的鏡像與對稱
實作樹的鏡像和對稱相關操作：
1. 判斷一棵二元樹是否為對稱樹
2. 將一棵二元樹轉換為其鏡像樹
3. 比較兩棵二元樹是否互為鏡像
4. 檢查一棵樹是否為另一棵樹的子樹
*/

import java.util.*;

public class TreeMirrorAndSymmetry {
    
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
    
    // 比較結果封裝類別
    static class ComparisonResult {
        private boolean result;
        private String description;
        
        public ComparisonResult(boolean result, String description) {
            this.result = result;
            this.description = description;
        }
        
        public boolean getResult() {
            return result;
        }
        
        public String getDescription() {
            return description;
        }
        
        @Override
        public String toString() {
            return "結果: " + (result ? "是" : "否") + " - " + description;
        }
    }
    
    // 樹的鏡像與對稱操作類別
    static class TreeMirrorOperations {
        
        /**
         * 判斷一棵二元樹是否為對稱樹
         */
        public static boolean isSymmetric(TreeNode root) {
            if (root == null) {
                return true; // 空樹被視為對稱樹
            }
            
            return isSymmetricHelper(root.left, root.right);
        }
        
        /**
         * 對稱檢查的輔助方法
         * 比較兩個子樹是否互為鏡像
         */
        private static boolean isSymmetricHelper(TreeNode left, TreeNode right) {
            // 如果兩個節點都為空，則對稱
            if (left == null && right == null) {
                return true;
            }
            
            // 如果只有一個節點為空，則不對稱
            if (left == null || right == null) {
                return false;
            }
            
            // 檢查當前節點值是否相等，並遞迴檢查子樹
            return (left.val == right.val) &&
                   isSymmetricHelper(left.left, right.right) &&
                   isSymmetricHelper(left.right, right.left);
        }
        
        /**
         * 將一棵二元樹轉換為其鏡像樹（破壞性轉換）
         */
        public static TreeNode mirrorTree(TreeNode root) {
            if (root == null) {
                return null;
            }
            
            // 交換左右子樹
            TreeNode temp = root.left;
            root.left = root.right;
            root.right = temp;
            
            // 遞迴處理左右子樹
            mirrorTree(root.left);
            mirrorTree(root.right);
            
            return root;
        }
        
        /**
         * 建立樹的鏡像副本（非破壞性轉換）
         */
        public static TreeNode createMirrorCopy(TreeNode root) {
            if (root == null) {
                return null;
            }
            
            TreeNode newNode = new TreeNode(root.val);
            newNode.left = createMirrorCopy(root.right);  // 左子樹 = 原右子樹的鏡像
            newNode.right = createMirrorCopy(root.left);  // 右子樹 = 原左子樹的鏡像
            
            return newNode;
        }
        
        /**
         * 比較兩棵二元樹是否互為鏡像
         */
        public static boolean areMirrors(TreeNode tree1, TreeNode tree2) {
            // 如果兩個樹都為空，則互為鏡像
            if (tree1 == null && tree2 == null) {
                return true;
            }
            
            // 如果只有一個樹為空，則不互為鏡像
            if (tree1 == null || tree2 == null) {
                return false;
            }
            
            // 檢查根節點值是否相等，並遞迴檢查鏡像關係
            return (tree1.val == tree2.val) &&
                   areMirrors(tree1.left, tree2.right) &&
                   areMirrors(tree1.right, tree2.left);
        }
        
        /**
         * 檢查一棵樹是否為另一棵樹的子樹
         */
        public static boolean isSubtree(TreeNode mainTree, TreeNode subTree) {
            // 空樹是任何樹的子樹
            if (subTree == null) {
                return true;
            }
            
            // 如果主樹為空但子樹不為空，則不是子樹
            if (mainTree == null) {
                return false;
            }
            
            // 檢查從當前節點開始是否匹配，或在左右子樹中繼續尋找
            return isIdentical(mainTree, subTree) ||
                   isSubtree(mainTree.left, subTree) ||
                   isSubtree(mainTree.right, subTree);
        }
        
        /**
         * 檢查兩棵樹是否完全相同
         */
        private static boolean isIdentical(TreeNode tree1, TreeNode tree2) {
            // 如果兩個樹都為空，則相同
            if (tree1 == null && tree2 == null) {
                return true;
            }
            
            // 如果只有一個樹為空，則不相同
            if (tree1 == null || tree2 == null) {
                return false;
            }
            
            // 檢查根節點值是否相等，並遞迴檢查左右子樹
            return (tree1.val == tree2.val) &&
                   isIdentical(tree1.left, tree2.left) &&
                   isIdentical(tree1.right, tree2.right);
        }
        
        /**
         * 複製樹結構（用於保護原始樹不被修改）
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
        public static void printTree(TreeNode root, String prefix) {
            if (root == null) {
                System.out.println(prefix + "空樹");
                return;
            }
            
            System.out.println(prefix + "樹結構:");
            printTreeHelper(root, prefix, "", true);
        }
        
        private static void printTreeHelper(TreeNode node, String globalPrefix, String prefix, boolean isLast) {
            if (node != null) {
                System.out.println(globalPrefix + prefix + (isLast ? "└── " : "├── ") + node.val);
                
                if (node.left != null || node.right != null) {
                    if (node.left != null) {
                        printTreeHelper(node.left, globalPrefix, prefix + (isLast ? "    " : "│   "), node.right == null);
                    }
                    if (node.right != null) {
                        printTreeHelper(node.right, globalPrefix, prefix + (isLast ? "    " : "│   "), true);
                    }
                }
            }
        }
        
        /**
         * 獲取樹的層序遍歷表示（用於比較）
         */
        public static List<String> getLevelOrder(TreeNode root) {
            List<String> result = new ArrayList<>();
            if (root == null) {
                result.add("null");
                return result;
            }
            
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                if (node == null) {
                    result.add("null");
                } else {
                    result.add(String.valueOf(node.val));
                    queue.offer(node.left);
                    queue.offer(node.right);
                }
            }
            
            // 移除末尾的null值
            while (!result.isEmpty() && result.get(result.size() - 1).equals("null")) {
                result.remove(result.size() - 1);
            }
            
            return result;
        }
    }
    
    /**
     * 主程式 
     */
    public static void main(String[] args) {
        System.out.println("\n === 樹的鏡像與對稱 ===\n");
        
        // 測試案例1：對稱樹
        System.out.println("測試案例1：對稱樹");
        Integer[] symmetricValues = {1, 2, 2, 3, 4, 4, 3};
        TreeNode symmetricTree = TreeBuilder.buildFromArray(symmetricValues);
        TreeBuilder.printTree(symmetricTree, "");
        
        boolean isSymmetric = TreeMirrorOperations.isSymmetric(symmetricTree);
        System.out.println("是否為對稱樹: " + isSymmetric);
        
        // 建立鏡像副本
        TreeNode mirrorCopy = TreeMirrorOperations.createMirrorCopy(symmetricTree);
        System.out.println("\n鏡像副本:");
        TreeBuilder.printTree(mirrorCopy, "");
        
        // 檢查原樹和鏡像副本是否互為鏡像
        boolean areMirrors = TreeMirrorOperations.areMirrors(symmetricTree, mirrorCopy);
        System.out.println("原樹與鏡像副本是否互為鏡像: " + areMirrors);
        System.out.println();
        
        // 測試案例2：非對稱樹
        System.out.println("測試案例2：非對稱樹");
        Integer[] asymmetricValues = {1, 2, 3, null, 4, null, 5};
        TreeNode asymmetricTree = TreeBuilder.buildFromArray(asymmetricValues);
        TreeBuilder.printTree(asymmetricTree, "");
        
        boolean isAsymmetric = TreeMirrorOperations.isSymmetric(asymmetricTree);
        System.out.println("是否為對稱樹: " + isAsymmetric);
        
        // 建立鏡像副本並進行破壞性轉換
        TreeNode asymmetricCopy = TreeMirrorOperations.copyTree(asymmetricTree);
        System.out.println("\n原樹的副本 (轉換前):");
        TreeBuilder.printTree(asymmetricCopy, "");
        
        TreeMirrorOperations.mirrorTree(asymmetricCopy);
        System.out.println("經過鏡像轉換後:");
        TreeBuilder.printTree(asymmetricCopy, "");
        
        // 檢查轉換後是否與原樹互為鏡像
        boolean afterTransform = TreeMirrorOperations.areMirrors(asymmetricTree, asymmetricCopy);
        System.out.println("原樹與轉換後的樹是否互為鏡像: " + afterTransform);
        System.out.println();
        
        // 測試案例3：子樹檢查
        System.out.println("測試案例3：子樹檢查");
        Integer[] mainTreeValues = {3, 4, 5, 1, 2, null, null, null, null, 0};
        TreeNode mainTree = TreeBuilder.buildFromArray(mainTreeValues);
        TreeBuilder.printTree(mainTree, "主樹: ");
        
        Integer[] subTreeValues = {4, 1, 2};
        TreeNode subTree = TreeBuilder.buildFromArray(subTreeValues);
        TreeBuilder.printTree(subTree, "子樹: ");
        
        boolean isSubtree = TreeMirrorOperations.isSubtree(mainTree, subTree);
        System.out.println("子樹是否為主樹的子樹: " + isSubtree);
        
        // 測試另一個不是子樹的情況
        Integer[] notSubTreeValues = {4, 1, 3};
        TreeNode notSubTree = TreeBuilder.buildFromArray(notSubTreeValues);
        TreeBuilder.printTree(notSubTree, "非子樹: ");
        
        boolean isNotSubtree = TreeMirrorOperations.isSubtree(mainTree, notSubTree);
        System.out.println("非子樹是否為主樹的子樹: " + isNotSubtree);
        
        System.out.println("\n=== 完成 === \n");
    }
}