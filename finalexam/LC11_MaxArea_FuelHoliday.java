/*
題目: 連假油量促銷最大區間
1."選兩個"不同位置的油槽架設輸油管，使得 橫距×最小高度， 最大化輸油管的輸出帶寬
2.給定整數陣列 heights，其中每個值代表該位置可支援的有效高度
3.選兩指標 i, j（i<j）最大化 (j-i)*min(heights[i], heights[j])，回傳最大值。
4.解法核心：L,R 夾逼 (每步計算面積取最大) + 貪婪 (移動短邊)
5.邊界：
- n=2 → 僅有一種選擇
- 全相等高度 → 距離最大者為答案
- 單調遞增/遞減 → 需正確處理
- 包含零值 → min 運算需正確
6.複雜度：時間 O(n)，空間 O(1)
7.
- 輸入:
第一行: 油槽數量 n
第二行: n 個高度 (2 ≤ n ≤ 10^5，0 ≤ height[i] ≤ 10^9)
- 輸出：一個整數 (最大面積)
*/

import java.util.*;

public class LC11_MaxArea_FuelHoliday {
    public int maxArea(int[] heights) {
        if (heights == null || heights.length < 2) {
            return 0;
        }
        int left = 0; // 左指標，從最左端開始
        int right = heights.length - 1; // 右指標，從最右端開始
        int maxArea = 0; // 記錄最大輸出帶寬
        
        // 雙指針向中間夾逼
        while (left < right) {
            // 計算當前配置的輸出帶寬：橫距 × 較小高度
            int width = right - left; // 管線橫距
            int minHeight = Math.min(heights[left], heights[right]); // 限制高度
            int currentArea = width * minHeight; // 當前帶寬

            maxArea = Math.max(maxArea, currentArea); // 更新最大帶寬
            
            // 貪婪：移動較短的一邊，才有可能獲得更大面積
            if (heights[left] < heights[right]) {
                left++; // 左邊較短，向右移動左指針
            } 
            else {
                right--; // 右邊較短或相等，向左移動右指針
            }
        }
        return maxArea;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LC11_MaxArea_FuelHoliday solution = new LC11_MaxArea_FuelHoliday();
        int n = scanner.nextInt(); // 油槽數量
        int[] heights = new int[n]; //各油槽高度
        
        for (int i = 0; i < n; i++) {
            heights[i] = scanner.nextInt();
        }
        
        // 計算最大輸出帶寬
        int result = solution.maxArea(heights);
        System.out.println("最大面積:"+result);    
        scanner.close();
    }
}

/*
解題思路 : 
1.雙指針夾逼：從陣列兩端開始，使用 left 和 right 指標向中間移動，充分利用最大可能的橫距。
2.面積計算：當前面積 = (right - left) × min(heights[left], heights[right])，橫距 x 受限的最小高度。
3.貪婪移動策略：
- 若 heights[left] < heights[right]：移動 left++，因為保留較高的右邊有更大潛力
- 否則移動 right--，因為右邊是瓶頸或相等時移動任一邊都可
4.移動較短邊是唯一可能增加面積的策略，移動較長邊只會減少橫距且高度不會增加。
5.終止條件：當 left >= right 時停止，此時已檢查所有有效配對。
*/