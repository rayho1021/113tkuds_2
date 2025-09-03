/*
題目：Longest Palindromic Substring
給定一個字串 s，回傳其中最長的回文子字串。
範例：s = "babad" → Output: "bab" (或 "aba"，兩者都是有效答案)
*/

class Solution {
   public String longestPalindrome(String s) {
       if (s == null || s.length() < 1) return ""; // 處理邊界情況
       int start = 0, end = 0; // 記錄最長回文子字串的起始和結束位置
       
       // 遍歷每個字元，檢查奇數長度的回文（以 i 為中心）；檢查偶數長度的回文（以 i 和 i+1 為中心）
       for (int i = 0; i < s.length(); i++) {
           int len1 = expandAroundCenter(s, i, i);
           int len2 = expandAroundCenter(s, i, i + 1);
           int len = Math.max(len1, len2); // 取得當前位置能找到的最長回文長度
           
           // 如果找到更長的回文，更新起始和結束位置
           if (len > end - start) {
               start = i - (len - 1) / 2; // 計算新的起始位置
               end = i + len / 2; // 計算新的結束位置
           }
       }
       
       return s.substring(start, end + 1); // 回傳最長回文子字串
   }
   
   // 輔助方法：從中心向外擴展，找出最長回文的長度
   private int expandAroundCenter(String s, int left, int right) {
       // 當左右字元相等且未超出邊界時，繼續擴展
       while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
           left--; // 向左擴展
           right++; // 向右擴展
       }
       return right - left - 1; // 回傳回文的長度
   }
}

/*
解題思路：
1. 使用中心擴展法，從每個可能的中心點向外擴展尋找回文。
2. 回文有兩種形式：奇數長度（如 "aba"）和偶數長度（如 "abba"）。
3. 對於每個位置 i，分別檢查以 i 為中心的奇數回文和以 i, i+1 為中心的偶數回文。
4. expandAroundCenter 方法從給定中心向外擴展，直到不再構成回文為止。
5. 對於每個找到的回文，比較其長度並更新最長回文的起始和結束位置。
6. 位置計算：start = i - (len-1)/2，end = i + len/2，適用於奇偶兩種情況。
7. 最終回傳從 start 到 end 的子字串作為答案。
*/