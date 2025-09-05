/*
題目: 回收站清單移除指定元素
1.回收場分類清單中移除指定代碼 val（已下架類別），就地覆寫保留非 val 元素，維持順序可讀性。
2.解題核心：單指針覆寫 + 單次遍歷
3.邊界：
- n=0 → 新長度 0
- 全部為 val → 新長度 0
- 沒有任何 val → 原長度不變
- val 只在特定位置（開頭/結尾/集中）
3.複雜度：時間 O(n)，空間 O(1)
4.
輸入：
- 第一行: 陣列長度 n + 目標值 val (n val)
- 第二行: n 個整數 (0 ≤ n ≤ 10^5，-10^9 ≤ 值,val ≤ 10^9)
輸出：
- 第一行: 新長度
- 第二行: 更新後前段結果
*/

import java.util.*;

public class LC27_RemoveElement_Recycle {
    public int removeElement(int[] nums, int val) {
        if (nums == null || nums.length == 0) {
            return 0;
        } 
        int write = 0; // 指向下一個可寫入的位置
        
        // 遍歷每個元素，按條件寫入
        for (int x : nums) {
            if (x != val) {
                nums[write++] = x; // 非目標值元素，寫入並移動指針
            }
            // 目標值元素直接跳過（刪除）
        }
        return write; // write 即為新的有效長度
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LC27_RemoveElement_Recycle solution = new LC27_RemoveElement_Recycle();
        int n = scanner.nextInt(); // 陣列長度和目標值
        int val = scanner.nextInt();

        if (n == 0) {  // 空陣列
            System.out.println(0);
            scanner.close();
            return;
        }
        
        // 讀取陣列
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }
    
        int newLength = solution.removeElement(nums, val);  // 移除指定元素
        System.out.println(newLength); // 輸出新長度
        
        if (newLength > 0) {
            for (int i = 0; i < newLength; i++) {
                if (i > 0) System.out.print(" ");
                System.out.print(nums[i]);
            }
            System.out.println();
        }
        scanner.close();
    }
}

/*
解題思路 : 
1.單指針覆寫：只使用一個 write 指針追蹤寫入位置。
2.迴圈：使用 for (int x : nums) 自動遍歷每個元素，無需額外的讀取指針。
3.條件寫入：
- 符合條件 x != val：執行 nums[write++] = x
-不符合條件：直接跳過，write 指針不移動
4.後置遞增：write++ 先使用當前值作為索引，然後遞增，一行搞定寫入和指針移動。
5.write 的最終值就是寫入元素的數量，即新的有效長度。
*/


