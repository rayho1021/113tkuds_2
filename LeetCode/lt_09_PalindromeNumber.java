/*
題目：Palindrome Number
給定一個整數 x，判斷 x 是否為回文數字。
範例：x = 121 → true (從左到右和從右到左讀都是 121)
範例：x = -121 → false (從右到左變成 121-，不是回文)
*/

class Solution {
    public boolean isPalindrome(int x) {
        // 負數和以 0 結尾的正數（除了 0 本身）都不是回文數
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }
        
        int reversedHalf = 0; // 儲存反轉後的右半部分
        
        // 只需要反轉數字的一半進行比較
        while (x > reversedHalf) {
            reversedHalf = reversedHalf * 10 + x % 10; // 將 x 的最後一位加到 reversedHalf
            x /= 10; // 移除 x 的最後一位
        }
        
        // 奇數位數：x == reversedHalf / 10（忽略中間位數）
        // 偶數位數：x == reversedHalf
        return x == reversedHalf || x == reversedHalf / 10;
    }
}

/*
解題思路：
1. 使用數學方法避免字串轉換。
2. 排除不可能的情況：負數和以 0 結尾的正數（除了 0）都不是回文。
3. 只需反轉數字的一半，而不是整個數字。
4. 迴圈條件：當 x <= reversedHalf 時停止，此時已處理了一半的數字。
5. 結束條件：
  - 偶數位數（如 1221）：x = 12, reversedHalf = 12，直接比較相等
  - 奇數位數（如 12321）：x = 12, reversedHalf = 123，比較 x 和 reversedHalf/10
6. 邊界處理：x = 0 是回文，x 以 0 結尾且不為 0 的數字不是回文（如 10, 100）。
*/