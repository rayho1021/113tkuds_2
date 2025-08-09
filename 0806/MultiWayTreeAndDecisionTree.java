/*
練習 3.10：多路樹與決策樹
實作多路樹和決策樹的基本操作：
1. 建立一個可以有任意多個子節點的多路樹
2. 實作多路樹的深度優先和廣度優先走訪
3. 模擬簡單的決策樹結構（如猜數字遊戲）
4. 計算多路樹的高度和每個節點的度數
*/

import java.util.*;

public class MultiWayTreeAndDecisionTree {
    
    // 泛型多路樹節點類別
    static class MultiWayNode<T> {
        private T data;
        private List<MultiWayNode<T>> children;
        private MultiWayNode<T> parent;
        
        public MultiWayNode(T data) {
            this.data = data;
            this.children = new ArrayList<>();
            this.parent = null;
        }
        
        // 添加子節點
        public void addChild(MultiWayNode<T> child) {
            if (child != null) {
                children.add(child);
                child.parent = this;
            }
        }
        
        // 移除子節點
        public boolean removeChild(MultiWayNode<T> child) {
            if (children.remove(child)) {
                child.parent = null;
                return true;
            }
            return false;
        }
        
        // Getters
        public T getData() { return data; }
        public void setData(T data) { this.data = data; }
        public List<MultiWayNode<T>> getChildren() { return children; }
        public MultiWayNode<T> getParent() { return parent; }
        public boolean isLeaf() { return children.isEmpty(); }
        public int getDegree() { return children.size(); }
    }
    
    // 決策樹節點類別
    static class DecisionNode {
        private String question;
        private String result;
        private boolean isLeaf;
        private Map<String, DecisionNode> branches;
        
        // 建構內部節點（問題節點）
        public DecisionNode(String question) {
            this.question = question;
            this.result = null;
            this.isLeaf = false;
            this.branches = new HashMap<>();
        }
        
        // 建構葉節點（結果節點）
        public static DecisionNode createLeaf(String result) {
            DecisionNode leaf = new DecisionNode("");
            leaf.result = result;
            leaf.isLeaf = true;
            return leaf;
        }
        
        // 添加分支
        public void addBranch(String condition, DecisionNode nextNode) {
            if (!isLeaf && nextNode != null) {
                branches.put(condition, nextNode);
            }
        }
        
        // Getters
        public String getQuestion() { return question; }
        public String getResult() { return result; }
        public boolean isLeaf() { return isLeaf; }
        public Map<String, DecisionNode> getBranches() { return branches; }
    }
    
    // 多路樹操作類別
    static class MultiWayTree<T> {
        private MultiWayNode<T> root;
        
        public MultiWayTree(T rootData) {
            this.root = new MultiWayNode<>(rootData);
        }
        
        public MultiWayTree(MultiWayNode<T> root) {
            this.root = root;
        }
        
        /**
         * 深度優先遍歷（前序）
         */
        public List<T> dfsPreorder() {
            List<T> result = new ArrayList<>();
            dfsPreorderHelper(root, result);
            return result;
        }
        
        private void dfsPreorderHelper(MultiWayNode<T> node, List<T> result) {
            if (node == null) return;
            
            result.add(node.data);
            for (MultiWayNode<T> child : node.children) {
                dfsPreorderHelper(child, result);
            }
        }
        
        /**
         * 深度優先遍歷（後序）
         */
        public List<T> dfsPostorder() {
            List<T> result = new ArrayList<>();
            dfsPostorderHelper(root, result);
            return result;
        }
        
        private void dfsPostorderHelper(MultiWayNode<T> node, List<T> result) {
            if (node == null) return;
            
            for (MultiWayNode<T> child : node.children) {
                dfsPostorderHelper(child, result);
            }
            result.add(node.data);
        }
        
        /**
         * 廣度優先遍歷
         */
        public List<T> bfs() {
            List<T> result = new ArrayList<>();
            if (root == null) return result;
            
            Queue<MultiWayNode<T>> queue = new LinkedList<>();
            queue.offer(root);
            
            while (!queue.isEmpty()) {
                MultiWayNode<T> node = queue.poll();
                result.add(node.data);
                
                for (MultiWayNode<T> child : node.children) {
                    queue.offer(child);
                }
            }
            
            return result;
        }
        
        /**
         * 層級遍歷（按層分組）
         */
        public List<List<T>> levelOrderTraversal() {
            List<List<T>> result = new ArrayList<>();
            if (root == null) return result;
            
            Queue<MultiWayNode<T>> queue = new LinkedList<>();
            queue.offer(root);
            
            while (!queue.isEmpty()) {
                int levelSize = queue.size();
                List<T> currentLevel = new ArrayList<>();
                
                for (int i = 0; i < levelSize; i++) {
                    MultiWayNode<T> node = queue.poll();
                    currentLevel.add(node.data);
                    
                    for (MultiWayNode<T> child : node.children) {
                        queue.offer(child);
                    }
                }
                
                result.add(currentLevel);
            }
            
            return result;
        }
        
        /**
         * 計算樹的高度
         */
        public int getHeight() {
            return getHeightHelper(root);
        }
        
        private int getHeightHelper(MultiWayNode<T> node) {
            if (node == null || node.isLeaf()) {
                return 0;
            }
            
            int maxChildHeight = 0;
            for (MultiWayNode<T> child : node.children) {
                maxChildHeight = Math.max(maxChildHeight, getHeightHelper(child));
            }
            
            return maxChildHeight + 1;
        }
        
        /**
         * 計算樹的統計資訊
         */
        public TreeStatistics<T> getStatistics() {
            TreeStatistics<T> stats = new TreeStatistics<>();
            calculateStatistics(root, stats);
            return stats;
        }
        
        private void calculateStatistics(MultiWayNode<T> node, TreeStatistics<T> stats) {
            if (node == null) return;
            
            stats.totalNodes++;
            int degree = node.getDegree();
            stats.maxDegree = Math.max(stats.maxDegree, degree);
            
            // 統計度數分布
            stats.degreeDistribution.put(degree, 
                stats.degreeDistribution.getOrDefault(degree, 0) + 1);
            
            if (node.isLeaf()) {
                stats.leafNodes.add(node.data);
            }
            
            for (MultiWayNode<T> child : node.children) {
                calculateStatistics(child, stats);
            }
        }
        
        /**
         * 樹的視覺化顯示
         */
        public void printTree() {
            printTree("樹結構:");
        }
        
        public void printTree(String description) {
            System.out.println(description);
            if (root == null) {
                System.out.println("空樹");
                return;
            }
            printTreeHelper(root, "", true);
        }
        
        private void printTreeHelper(MultiWayNode<T> node, String prefix, boolean isLast) {
            if (node == null) return;
            
            System.out.println(prefix + (isLast ? "└── " : "├── ") + node.data);
            
            List<MultiWayNode<T>> children = node.children;
            for (int i = 0; i < children.size(); i++) {
                boolean lastChild = (i == children.size() - 1);
                String newPrefix = prefix + (isLast ? "    " : "│   ");
                printTreeHelper(children.get(i), newPrefix, lastChild);
            }
        }
        
        /**
         * 尋找從根到目標節點的路徑
         */
        public List<T> findPath(T target) {
            List<T> path = new ArrayList<>();
            if (findPathHelper(root, target, path)) {
                return path;
            }
            return new ArrayList<>(); // 未找到返回空路徑
        }
        
        private boolean findPathHelper(MultiWayNode<T> node, T target, List<T> path) {
            if (node == null) return false;
            
            path.add(node.data);
            
            if (node.data.equals(target)) {
                return true;
            }
            
            for (MultiWayNode<T> child : node.children) {
                if (findPathHelper(child, target, path)) {
                    return true;
                }
            }
            
            path.remove(path.size() - 1); // 回溯
            return false;
        }
        
        public MultiWayNode<T> getRoot() { return root; }
    }
    
    // 樹統計資訊類別
    static class TreeStatistics<T> {
        public int totalNodes = 0;
        public int maxDegree = 0;
        public Map<Integer, Integer> degreeDistribution = new HashMap<>();
        public List<T> leafNodes = new ArrayList<>();
        
        public void displayStatistics() {
            System.out.println("=== 樹統計資訊 ===");
            System.out.println("總節點數: " + totalNodes);
            System.out.println("最大度數: " + maxDegree);
            System.out.println("葉節點數: " + leafNodes.size());
            System.out.println("葉節點: " + leafNodes);
            
            System.out.println("度數分布:");
            for (Map.Entry<Integer, Integer> entry : degreeDistribution.entrySet()) {
                System.out.println("  度數 " + entry.getKey() + ": " + entry.getValue() + " 個節點");
            }
        }
    }
    
    // 決策樹類別
    static class DecisionTree {
        private DecisionNode root;
        
        public DecisionTree(DecisionNode root) {
            this.root = root;
        }
        
        /**
         * 建立猜數字遊戲的決策樹
         */
        public static DecisionTree buildNumberGuessingTree(int min, int max) {
            DecisionNode root = buildNumberGuessingHelper(min, max);
            return new DecisionTree(root);
        }
        
        private static DecisionNode buildNumberGuessingHelper(int min, int max) {
            if (min == max) {
                return DecisionNode.createLeaf("答案是 " + min + "！");
            }
            
            int mid = min + (max - min) / 2;
            DecisionNode node = new DecisionNode("你想的數字是否大於 " + mid + "？");
            
            // 是：範圍是 [mid+1, max]
            node.addBranch("是", buildNumberGuessingHelper(mid + 1, max));
            
            // 否：範圍是 [min, mid]
            node.addBranch("否", buildNumberGuessingHelper(min, mid));
            
            return node;
        }
        
        /**
         * 執行決策過程（模擬）
         */
        public String executeDecision(Scanner input) {
            return executeDecisionHelper(root, input);
        }
        
        private String executeDecisionHelper(DecisionNode node, Scanner input) {
            if (node.isLeaf()) {
                return node.result;
            }
            
            System.out.println(node.question);
            System.out.print("請回答 (是/否): ");
            String answer = input.nextLine().trim();
            
            DecisionNode nextNode = node.branches.get(answer);
            if (nextNode != null) {
                return executeDecisionHelper(nextNode, input);
            } else {
                System.out.println("無效輸入，請回答 '是' 或 '否'");
                return executeDecisionHelper(node, input);
            }
        }
        
        /**
         * 顯示決策樹結構
         */
        public void printDecisionTree() {
            System.out.println("決策樹結構:");
            printDecisionTreeHelper(root, "", true);
        }
        
        private void printDecisionTreeHelper(DecisionNode node, String prefix, boolean isLast) {
            if (node == null) return;
            
            String content = node.isLeaf() ? "[結果] " + node.result : "[問題] " + node.question;
            System.out.println(prefix + (isLast ? "└── " : "├── ") + content);
            
            if (!node.isLeaf()) {
                List<String> conditions = new ArrayList<>(node.branches.keySet());
                Collections.sort(conditions); // 排序以保持一致性
                
                for (int i = 0; i < conditions.size(); i++) {
                    String condition = conditions.get(i);
                    boolean lastChild = (i == conditions.size() - 1);
                    String newPrefix = prefix + (isLast ? "    " : "│   ");
                    
                    System.out.println(newPrefix + (lastChild ? "└── " : "├── ") + condition + " → ");
                    String branchPrefix = newPrefix + (lastChild ? "    " : "│   ");
                    printDecisionTreeHelper(node.branches.get(condition), branchPrefix, true);
                }
            }
        }
        
        /**
         * 計算決策樹的深度
         */
        public int getDepth() {
            return getDepthHelper(root);
        }
        
        private int getDepthHelper(DecisionNode node) {
            if (node == null || node.isLeaf()) {
                return 0;
            }
            
            int maxDepth = 0;
            for (DecisionNode child : node.branches.values()) {
                maxDepth = Math.max(maxDepth, getDepthHelper(child));
            }
            
            return maxDepth + 1;
        }
    }
    
    // 示例建構器類別
    static class ExampleBuilder {
        
        /**
         * 建立組織架構樹
         */
        public static MultiWayTree<String> buildOrganizationTree() {
            MultiWayNode<String> ceo = new MultiWayNode<>("CEO");
            
            MultiWayNode<String> cto = new MultiWayNode<>("CTO");
            MultiWayNode<String> cfo = new MultiWayNode<>("CFO");
            MultiWayNode<String> cmo = new MultiWayNode<>("CMO");
            
            ceo.addChild(cto);
            ceo.addChild(cfo);
            ceo.addChild(cmo);
            
            // CTO 部門
            cto.addChild(new MultiWayNode<>("開發部經理"));
            cto.addChild(new MultiWayNode<>("測試部經理"));
            cto.addChild(new MultiWayNode<>("運維部經理"));
            
            // CFO 部門
            cfo.addChild(new MultiWayNode<>("會計主管"));
            cfo.addChild(new MultiWayNode<>("財務分析師"));
            
            // CMO 部門
            cmo.addChild(new MultiWayNode<>("市場推廣經理"));
            cmo.addChild(new MultiWayNode<>("品牌經理"));
            cmo.addChild(new MultiWayNode<>("數位行銷經理"));
            
            // 再添加一些下級
            MultiWayNode<String> devManager = cto.getChildren().get(0);
            devManager.addChild(new MultiWayNode<>("前端工程師"));
            devManager.addChild(new MultiWayNode<>("後端工程師"));
            devManager.addChild(new MultiWayNode<>("資料庫管理員"));
            
            return new MultiWayTree<>(ceo);
        }
        
        /**
         * 建立檔案系統樹
         */
        public static MultiWayTree<String> buildFileSystemTree() {
            MultiWayNode<String> root = new MultiWayNode<>("root");
            
            MultiWayNode<String> home = new MultiWayNode<>("home");
            MultiWayNode<String> var = new MultiWayNode<>("var");
            MultiWayNode<String> etc = new MultiWayNode<>("etc");
            
            root.addChild(home);
            root.addChild(var);
            root.addChild(etc);
            
            // home 目錄
            MultiWayNode<String> user1 = new MultiWayNode<>("user1");
            MultiWayNode<String> user2 = new MultiWayNode<>("user2");
            home.addChild(user1);
            home.addChild(user2);
            
            user1.addChild(new MultiWayNode<>("documents"));
            user1.addChild(new MultiWayNode<>("downloads"));
            user1.addChild(new MultiWayNode<>("pictures"));
            
            // documents 子目錄
            MultiWayNode<String> documents = user1.getChildren().get(0);
            documents.addChild(new MultiWayNode<>("report.pdf"));
            documents.addChild(new MultiWayNode<>("presentation.pptx"));
            documents.addChild(new MultiWayNode<>("notes.txt"));
            
            return new MultiWayTree<>(root);
        }
        
        /**
         * 建立數學表達式樹
         */
        public static MultiWayTree<String> buildExpressionTree() {
            MultiWayNode<String> plus = new MultiWayNode<>("+");
            
            MultiWayNode<String> multiply = new MultiWayNode<>("*");
            MultiWayNode<String> divide = new MultiWayNode<>("/");
            
            plus.addChild(multiply);
            plus.addChild(divide);
            
            // 乘法子樹
            multiply.addChild(new MultiWayNode<>("3"));
            multiply.addChild(new MultiWayNode<>("4"));
            
            // 除法子樹
            divide.addChild(new MultiWayNode<>("8"));
            divide.addChild(new MultiWayNode<>("2"));
            
            return new MultiWayTree<>(plus);
        }
    }
    
    /**
     * 主程式 
     */
    public static void main(String[] args) {
        System.out.println("=== 多路樹與決策樹練習 ===\n");
        
        // 1. 建立和展示多路樹
        System.out.println("=== 1. 多路樹結構展示 ===");
        
        // a) 組織架構樹
        System.out.println("a) 組織架構樹：");
        MultiWayTree<String> orgTree = ExampleBuilder.buildOrganizationTree();
        orgTree.printTree();
        System.out.println();
        
        // b) 檔案系統樹
        System.out.println("b) 檔案系統樹：");
        MultiWayTree<String> fileTree = ExampleBuilder.buildFileSystemTree();
        fileTree.printTree();
        System.out.println();
        
        // 2. 多路樹的遍歷
        System.out.println("=== 2. 多路樹遍歷 ===");
        System.out.println("使用組織架構樹進行遍歷展示：");
        
        List<String> dfsPreorder = orgTree.dfsPreorder();
        System.out.println("DFS前序遍歷: " + dfsPreorder);
        
        List<String> dfsPostorder = orgTree.dfsPostorder();
        System.out.println("DFS後序遍歷: " + dfsPostorder);
        
        List<String> bfs = orgTree.bfs();
        System.out.println("BFS遍歷: " + bfs);
        
        List<List<String>> levelOrder = orgTree.levelOrderTraversal();
        System.out.println("層級遍歷:");
        for (int i = 0; i < levelOrder.size(); i++) {
            System.out.println("  第" + (i + 1) + "層: " + levelOrder.get(i));
        }
        System.out.println();
        
        // 3. 樹的統計資訊
        System.out.println("=== 3. 樹的統計資訊 ===");
        
        System.out.println("組織架構樹統計:");
        TreeStatistics<String> orgStats = orgTree.getStatistics();
        orgStats.displayStatistics();
        System.out.println("樹的高度: " + orgTree.getHeight());
        
        System.out.println("\n檔案系統樹統計:");
        TreeStatistics<String> fileStats = fileTree.getStatistics();
        fileStats.displayStatistics();
        System.out.println("樹的高度: " + fileTree.getHeight());
        System.out.println();
        
        // 4. 路徑查找
        System.out.println("=== 4. 路徑查找 ===");
        String target = "前端工程師";
        List<String> path = orgTree.findPath(target);
        if (!path.isEmpty()) {
            System.out.println("從根到 '" + target + "' 的路徑: " + path);
        } else {
            System.out.println("未找到 '" + target + "'");
        }
        
        target = "report.pdf";
        path = fileTree.findPath(target);
        if (!path.isEmpty()) {
            System.out.println("從根到 '" + target + "' 的路徑: " + path);
        } else {
            System.out.println("未找到 '" + target + "'");
        }
        System.out.println();
        
        // 5. 決策樹展示
        System.out.println("=== 5. 決策樹 - 猜數字遊戲 ===");
        
        int min = 1, max = 10;
        DecisionTree gameTree = DecisionTree.buildNumberGuessingTree(min, max);
        
        System.out.println("猜數字遊戲 (範圍: " + min + " - " + max + ")");
        gameTree.printDecisionTree();
        
        System.out.println("決策樹深度: " + gameTree.getDepth());
        System.out.println();
        
        // 6. 表達式樹展示
        System.out.println("=== 6. 數學表達式樹 ===");
        MultiWayTree<String> exprTree = ExampleBuilder.buildExpressionTree();
        exprTree.printTree("表達式樹 (3 * 4) + (8 / 2):");
        
        System.out.println("前序遍歷 (前綴表達式): " + exprTree.dfsPreorder());
        System.out.println("後序遍歷 (後綴表達式): " + exprTree.dfsPostorder());
        
        TreeStatistics<String> exprStats = exprTree.getStatistics();
        exprStats.displayStatistics();
        System.out.println();
        
        // 7. 互動式猜數字遊戲（可選）
        System.out.println("=== 7. 互動式猜數字遊戲 ===");
        System.out.println("想要體驗猜數字遊戲嗎？請在1-8之間想一個數字！");
        System.out.println("(這裡演示樹結構，實際互動需要Scanner輸入)");
        
        DecisionTree smallGame = DecisionTree.buildNumberGuessingTree(1, 8);
        smallGame.printDecisionTree();
        
        System.out.println("這個決策樹最多需要 " + smallGame.getDepth() + " 次提問就能猜中數字！");
        
        System.out.println("\n=== 完成 ===");
    }
}