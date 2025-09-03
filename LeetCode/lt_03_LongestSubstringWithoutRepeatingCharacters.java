/*
題目：Longest Substring Without Repeating Characters
給定一個字串 s，找出最長的無重複字元子字串的長度。
範例：s = "pwwkew" → Output: 3 (答案是 "wke"，長度為 3)
*/

class Solution {
    public int lengthOfLongestSubstring(String s) {
        // n 為字串長度，ans 記錄最大無重複子字串長度
        int n = s.length(), ans = 0; 
        Map<Character, Integer> map = new HashMap<>(); // 儲存字元與其最新索引位置的映射
        
        // i 左指標，j 右指標
        for (int i = 0, j = 0; j < n; j++) {
            // 如果當前字元已經在 map 中出現過，使用 Math.max 確保 i 只能向前移動不會後退
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)) + 1, i);
            }
            ans = Math.max(ans, j - i + 1); // 更新最大長度
            map.put(s.charAt(j), j); // 更新當前字元的索引位置加入 map 中
        }
        return ans;
    }
}

/*
解題思路：
1. 使用滑動視窗技巧，維護一個動態視窗 [i, j]，其中 j 不斷向右擴展。
2. 使用 HashMap 記錄每個字元最後出現的索引位置，實現 O(1) 的重複檢測。
3. 當遇到重複字元時，將左邊界 i 移動到「重複字元上次出現位置的下一位」。
4. 關鍵技巧：使用 Math.max() 確保左邊界 i 只能向前移動，避免因舊的重複字元而後退。
5. 每次擴展右邊界時，都計算當前視窗長度並更新最大值。
6. 最後更新當前字元在 map 中的位置，為後續檢測做準備。
*/