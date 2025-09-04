/*
題目：Count and Say
計數並說出序列：通過對前一個字串進行行程長度編碼（RLE）來生成下一個字串
* countAndSay(1) = "1"
* countAndSay(n) = RLE of countAndSay(n-1)
給定一個正整數n，返回計數並說出序列的元素。n^th
*/


class Solution {
    public String countAndSay(int n) {
        if (n == 1) {
            return "1"; // 基礎情況
        }
        
        String current = "1"; // 從第一個序列開始
        
        // 迭代生成從第2個到第n個序列
        for (int i = 2; i <= n; i++) {
            current = getNextSequence(current);
        }
        return current;
    }
    
    // 對給定字串進行行程長度編碼，生成下一個序列
    private String getNextSequence(String s) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        
        while (i < s.length()) {
            char currentChar = s.charAt(i);
            int count = 1;
            
            // 計算連續相同字符的數量
            while (i + count < s.length() && s.charAt(i + count) == currentChar) {
                count++;
            }
            
            result.append(count).append(currentChar); // 將計數和字符添加到結果中
            i += count; // 移動到下一組不同的字符
        }
        return result.toString();
    }
}

/*
解題思路：
1. Count and Say 序列是通過對前一個字串進行行程長度編碼（RLE）生成的：
   - countAndSay(1) = "1"
   - countAndSay(2) = "11" (一個1)
   - countAndSay(3) = "21" (兩個1)
   - countAndSay(4) = "1211" (一個2，一個1)

2. 行程長度編碼的過程：
   - 遍歷字串，計算連續相同字符的數量
   - 將「數量 + 字符」添加到結果字串中
   - 例如："222" -> "32" (三個2)

3. 邏輯：
   - 從基礎情況 "1" 開始
   - 迭代 n-1 次，每次對當前字串進行 RLE 編碼
   - 使用雙指針技術來計算連續字符的數量

4. getNextSequence 方法：
   - 使用變量 i 作為當前位置指針
   - 使用變量 count 計算從位置 i 開始的連續相同字符數量
   - 將計數和字符添加到結果中，然後跳到下一組不同字符
*/