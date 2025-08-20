import java.util.*;

public class M10_RBPropertiesCheck {
    
    // 紅黑樹節點類別
    static class RBNode {
        int val;
        char color;  
        
        RBNode(int val, char color) {
            this.val = val;
            this.color = color;
        }
        
        boolean isBlack() {
            return color == 'B';
        }
        
        boolean isRed() {
            return color == 'R';
        }
    }
    
    private static RBNode[] tree;
    private static int size;
    
    /**
     * 從輸入建立紅黑樹陣列表示
     * @param input 輸入的節點值和顏色對
     * @param n 節點數量
     */
    public static void buildTree(String[] input, int n) {
        tree = new RBNode[n];
        size = n;
        
        for (int i = 0; i < n; i++) {
            String[] parts = input[i].split("\\s+");
            int val = Integer.parseInt(parts[0]);
            char color = parts[1].charAt(0);
            
            if (val == -1) {
                tree[i] = null;  // null 節點
            } else {
                tree[i] = new RBNode(val, color);
            }
        }
    }
    
    /**
     * 獲取左子節點索引
     * @param index 父節點索引
     * @return 左子節點索引，超出範圍返回 -1
     */
    private static int getLeftChild(int index) {
        int leftIndex = 2 * index + 1;
        return leftIndex < size ? leftIndex : -1;
    }
    
    /**
     * 獲取右子節點索引
     * @param index 父節點索引
     * @return 右子節點索引，超出範圍返回 -1
     */
    private static int getRightChild(int index) {
        int rightIndex = 2 * index + 2;
        return rightIndex < size ? rightIndex : -1;
    }
    
    /**
     * 檢查根節點是否為黑色
     * @return 根節點是否為黑色
     */
    public static boolean checkRootColor() {
        if (size == 0 || tree[0] == null) {
            return true;  // 空樹視為滿足
        }
        return tree[0].isBlack();
    }
    
    /**
     * 檢查是否有紅紅相鄰違規
     * @return 第一個違規節點的索引，沒有違規返回 -1
     */
    public static int checkRedRedViolation() {
        for (int i = 0; i < size; i++) {
            if (tree[i] != null && tree[i].isRed()) {
                // 檢查左子節點
                int leftIndex = getLeftChild(i);
                if (leftIndex != -1 && tree[leftIndex] != null && tree[leftIndex].isRed()) {
                    return i;  // 發現紅紅相鄰
                }
                
                // 檢查右子節點
                int rightIndex = getRightChild(i);
                if (rightIndex != -1 && tree[rightIndex] != null && tree[rightIndex].isRed()) {
                    return i;  // 發現紅紅相鄰
                }
            }
        }
        return -1;  // 沒有違規
    }
    
    /**
     * 檢查黑高度是否一致
     * @return 黑高度是否一致
     */
    public static boolean checkBlackHeight() {
        if (size == 0 || tree[0] == null) {
            return true;  // 空樹滿足
        }
        return getBlackHeight(0) != -1;
    }
    
    /**
     * 遞迴計算節點的黑高度
     * @param index 節點索引
     * @return 黑高度，如果不一致返回 -1
     */
    private static int getBlackHeight(int index) {
        // 基底情況：超出範圍或 null 節點（視為黑色葉子）
        if (index == -1 || index >= size || tree[index] == null) {
            return 1;  // NIL 節點視為黑色，高度為 1
        }
        
        // 遞迴計算左右子樹的黑高度
        int leftIndex = getLeftChild(index);
        int rightIndex = getRightChild(index);
        
        int leftBlackHeight = getBlackHeight(leftIndex);
        int rightBlackHeight = getBlackHeight(rightIndex);
        
        // 如果左右子樹的黑高度不一致，返回 -1
        if (leftBlackHeight == -1 || rightBlackHeight == -1 || 
            leftBlackHeight != rightBlackHeight) {
            return -1;
        }
        
        // 如果當前節點是黑色，黑高度 +1
        if (tree[index].isBlack()) {
            return leftBlackHeight + 1;
        } else {
            return leftBlackHeight;  // 紅色節點不增加黑高度
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 讀取節點數量
        System.out.print("節點數量 n : ");
        int n = Integer.parseInt(scanner.nextLine().trim());
        
        // 讀取節點資料
        System.out.println("請輸入 " + n + " 個節點 (值 顏色):");
        String[] input = new String[n];
        for (int i = 0; i < n; i++) {
            input[i] = scanner.nextLine().trim();
        }
        
        // 建立樹
        buildTree(input, n);
        
        
        // 檢查性質 1：根節點為黑色
        if (!checkRootColor()) {
            System.out.println("檢查結果: RootNotBlack");
            scanner.close();
            return;
        }
        
        // 檢查性質 2：無紅紅相鄰
        int violationIndex = checkRedRedViolation();
        if (violationIndex != -1) {
            System.out.println("檢查結果: RedRedViolation at index " + violationIndex);
            scanner.close();
            return;
        }
        
        // 檢查性質 3：黑高度一致
        if (!checkBlackHeight()) {
            System.out.println("檢查結果: BlackHeightMismatch");
            scanner.close();
            return;
        }
        
        // 所有性質都滿足
        System.out.println("檢查結果: RB Valid");
        
        scanner.close();
    }
}