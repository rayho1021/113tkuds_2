/*
題目：String to Integer (atoi)
實作 myAtoi 函數，將字串轉換為 32 位元有符號整數，遵循以下演算法：
1. 忽略開頭的空白字元
2. 檢查正負號（+ 或 -），預設為正數
3. 讀取數字直到遇到非數字字元或字串結尾，忽略前導零
4. 如果超出 32 位元範圍 [-2^31, 2^31-1]，則截斷到範圍邊界
範例：s = "42" → 42, s = "-042" → -42, s = "1337c0d3" → 1337
*/

class Solution {
    public int myAtoi(String s) {
        if (s == null || s.length() == 0) return 0; // 處理空字串
        
        int index = 0; // 目前處理的字元索引
        int n = s.length(); // 字串長度
        
        // 跳過開頭的空白字元
        while (index < n && s.charAt(index) == ' ') {
            index++;
        }
        
        if (index == n) return 0; // 字串只有空白字元
        
        // 檢查正負號
        int sign = 1; // 預設為正數
        if (s.charAt(index) == '-') {
            sign = -1; // 設定為負數
            index++;
        } else if (s.charAt(index) == '+') {
            index++; // 保持正數，移動索引
        }
        
        // 轉換數字
        int result = 0;
        while (index < n && Character.isDigit(s.charAt(index))) {
            int digit = s.charAt(index) - '0'; // 字元轉數字
            
            // 檢查溢位並截斷
            // 檢查正溢位：result > MAX_VALUE/10 或 result == MAX_VALUE/10 且 digit > 7
            if (result > Integer.MAX_VALUE / 10 || 
                (result == Integer.MAX_VALUE / 10 && digit > Integer.MAX_VALUE % 10)) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            
            result = result * 10 + digit; // 累積數字結果
            index++;
        }
        
        return sign * result; // 套用正負號並回傳結果
    }
}

/*
解題思路：
1. 按照題目要求的四個步驟依序處理：空白、正負號、數字轉換、溢位處理。
2. 使用 index 指標逐步掃描字串，確保每個步驟都在正確的位置開始。
3. 空白處理：使用 while 迴圈跳過所有開頭的空白字元。
4. 正負號處理：檢查當前字元是否為 '+' 或 '-'，設定 sign 變數並移動指標。
5. 數字轉換：使用 Character.isDigit() 檢查數字字元，逐位累積結果。
6. 溢位檢測：在每次更新 result 前檢查是否會超出 32 位元整數範圍。
7. 邊界處理：超出範圍時根據正負號回傳對應的邊界值（MAX_VALUE 或 MIN_VALUE）。
8. 時間複雜度 O(n)，空間複雜度 O(1)，其中 n 是字串長度。
*/