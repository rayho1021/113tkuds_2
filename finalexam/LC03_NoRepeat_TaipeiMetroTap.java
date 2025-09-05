/*
題目: 北捷刷卡最長無重複片段
1.找出捷運刷卡流水字串中，"最長連續無重複"字元的子字串長度，用於估算尖峰時段同時不同乘客通過閘門的最大流量。
2.解法核心：滑動視窗技巧 + HashMap 索引記錄
3.邊界：
- 空字串 → 0
- 全相同字元 "aaaa" → 1
- 全不重複 → |s|
- 交錯重複 "abba" → 2
4.複雜度：時間 O(n)，空間 O(k)
5.
輸入：一行字串 s (0 ≤ |s| ≤ 10^5，包含 ASCII 可見字符)
輸出：一個整數 (最長無重複片段長度)
*/

import java.util.*;

public class LC03_NoRepeat_TaipeiMetroTap {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        // 使用 HashMap 記錄每個字元最後出現的索引位置
        Map<Character, Integer> charIndexMap = new HashMap<>();
        int maxLength = 0; // 最長無重複片段的長度
        int left = 0; // 滑動視窗左邊界
        
        // right 滑動視窗右邊界，向右擴展
        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            
            // 如果當前字元已經在視窗內出現過，且其位置在左邊界之後
            if (charIndexMap.containsKey(currentChar) && 
                charIndexMap.get(currentChar) >= left) {
                // 將左邊界移動到重複字元的下一個位置
                left = charIndexMap.get(currentChar) + 1;
            }
            
            charIndexMap.put(currentChar, right); // 更新當前字元的最新索引位置
            
            // 更新最長長度： right - left + 1
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LC03_NoRepeat_TaipeiMetroTap solution = new LC03_NoRepeat_TaipeiMetroTap();
        String input = scanner.nextLine().trim();
        
        // 計算最長無重複片段長度
        int result = solution.lengthOfLongestSubstring(input);
        System.out.println("最長無重複片段長度:" + result); 
        scanner.close();
    }
}

/*
解題思路 : 
1. 滑動視窗技巧：使用雙指針 left 和 right 維護一個動態視窗，確保沒有重複字元。
2. HashMap 查找：用 Map<Character, Integer> 記錄每個字元最後出現的索引位置，避免重複掃描。
3. 左邊界更新：當遇到重複字元時，將左邊界 left 直接跳到該字元上次出現位置的下一個位置，有效縮小視窗。
4. 更新最大值：每次右指針移動後，計算當前視窗大小 right - left + 1，並更新全域最大長度。
5. 邊界處理：空字串直接回傳 0，單字元字串回傳 1。
*/