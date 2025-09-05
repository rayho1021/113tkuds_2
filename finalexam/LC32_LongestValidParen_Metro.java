/*
題目: 北捷進出站最長有效片段
1.
- "(" 代表一次進站事件 ； ")" 代表一次出站事件
- 分析北捷閘門日誌，找出最長連續子字串使得進站('(')與出站(')')完全配對
2.解法核心：索引堆疊追蹤 + 動態長度計算
3.邊界案例：
- 空字串 → 0
- 全為 '(' 或全為 ')' → 0
- 完全配對 "()()" → 4
- 孤立起始 ')' 如 ")()" → 需重置基準
4.時空複雜度：時間 O(n)，空間 O(n)
5.
輸入：一行括號字串
* 0 <= |s| <= 1e5
* 僅含 '(' 或 ')'
輸出：一個整數 (最長合法配對片段的長度)
*/

import java.util.*;

public class LC32_LongestValidParen_Metro {
    public int longestValidParentheses(String s) {
        if (s == null || s.length() <= 1) {
            return 0;
        }
        // 使用索引堆疊，棧底放入 -1 為初始基準索引
        Stack<Integer> indexStack = new Stack<>();
        indexStack.push(-1);

        int maxLength = 0; // 記錄最長合法片段長度
        
        // 逐字元處理括號配對
        for (int i = 0; i < s.length(); i++) {
            char current = s.charAt(i);
            
            if (current == '(') {
                // 遇到進站事件：將當前索引推入堆疊
                indexStack.push(i);
            } 
            else {  // 遇到出站事件：嘗試配對
                indexStack.pop(); // 移除棧頂
                
                // 棧空表示無匹配的 '('，將當前索引作為新的基準
                if (indexStack.isEmpty()) {
                    indexStack.push(i);
                } 
                else {
                    // 計算當前合法片段長度：當前位置 - 新棧頂位置
                    int currentLength = i - indexStack.peek();
                    maxLength = Math.max(maxLength, currentLength);
                }
            }
        }
        return maxLength;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LC32_LongestValidParen_Metro solution = new LC32_LongestValidParen_Metro();
        
        // 讀取括號字串
        String input = scanner.nextLine().trim();
        
        // 計算最長合法配對片段長度
        int result = solution.longestValidParentheses(input);
        
        // 輸出結果
        System.out.println("最長合法配對:" + result);
        
        scanner.close();
    }
}

/*
解題思路 : 
1. 使用 Stack 索引堆疊：儲存索引而非字元，棧底放入 -1 作為計算長度的基準點。
2  進站處理，遇到 '(' 時，將其索引推入堆疊，等待後續的 ')' 配對。
3. 出站配對，遇到 ')' 時，先 pop 棧頂，然後檢查堆疊狀態：
- 若棧空：表示無匹配的 '('，將當前索引作為新基準推入
- 若非空：計算合法片段長度 = 當前索引 - 新棧頂索引
4. 動態更新最大值：每次成功配對後更新全域最長長度。
5. 基準點重置：當遇到無法配對的 ')' 時，自動重置基準點，確保後續計算的正確性。
*/