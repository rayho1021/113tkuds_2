/*
題目：Longest Common Prefix
找出字串陣列中所有字串的最長共同前綴，如果沒有共同前綴，回傳空字串 ""。
範例：strs = ["flower","flow","flight"] → "fl"
範例：strs = ["dog","racecar","car"] → ""
*/

class Solution {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return ""; // 空陣列回傳 ""
        
        // 從第一個字串開始，逐個檢查所有字串的前綴
        for (int i = 0; i < strs[0].length(); i++) {
            char currentChar = strs[0].charAt(i); // 取得第一個字串在位置 i 的字元
            
            // 檢查其他所有字串在相同位置是否有相同字元
            for (int j = 1; j < strs.length; j++) {
                // 如果字串長度不足或字元不匹配，回傳從開頭到位置 i-1 的子字串 (到目前的)
                if (i >= strs[j].length() || strs[j].charAt(i) != currentChar) {
                    return strs[0].substring(0, i); 
                }
            }
        }
        
        return strs[0]; // 若第一個字串是所有字串的前綴，回傳整個第一個字串
    }
}

/*
解題思路：
1. 使用垂直掃描法，以第一個字串為基準，逐個字元位置進行比較。
2. 外層迴圈:遍歷第一個字串的每個字元位置（從左到右）。
3. 內層迴圈:檢查其他所有字串在相同位置是否有相同的字元。
4. 終止條件：
  - 任一字串長度不足（到達字串末尾）
  - 任一字串在當前位置的字元與基準不同
5. 當遇到不匹配時，立即回傳到目前為止的共同前綴。
6. 邊界處理：如果第一個字串本身就是所有字串的前綴，回傳完整的第一個字串。
*/