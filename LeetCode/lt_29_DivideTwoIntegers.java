/*
題目：Divide Two Integers
在不使用乘法、除法和取餘運算子的情況下，將兩個整數相除。
整數除法應該向零截斷，即去掉小數部分。
範例：dividend = 7, divisor = -3 → -2 (7/-3 = -2.33... 截斷為 -2)
*/

class Solution {
    public int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE; // 避免 -2^31 / -1 = 2^31 溢位
        }
        
        boolean isNegative = (dividend < 0) ^ (divisor < 0); // 確定結果的正負號
        
        // 轉換為正數進行計算，使用 long 避免溢位
        long absDividend = Math.abs((long) dividend);
        long absDivisor = Math.abs((long) divisor);
        
        int quotient = 0; // 儲存商的結果
        
        // 使用位移操作加速除法運算
        while (absDividend >= absDivisor) {
            long temp = absDivisor; // 暫存除數
            int multiple = 1; // 記錄倍數
            
            // 透過左移找到最大的 divisor * 2^k <= dividend
            while (absDividend >= (temp << 1)) {
                temp <<= 1; // 除數左移一位（相當於乘以 2）
                multiple <<= 1; // 倍數左移一位（相當於乘以 2）
            }
            
            absDividend -= temp; // 減去找到的最大倍數
            quotient += multiple; // 累加到商中
        }
        // 根據正負號回傳結果
        return isNegative ? -quotient : quotient;
    }
}

/*
解題思路：
1. 使用位移運算來模擬除法，避免直接使用乘法、除法和取餘運算。
2. 重複減去除數的倍數，找出最大可減去的倍數。
3. 使用二進位左移操作快速計算除數的 2^k 倍，加速運算過程。
4. 邏輯：
  - 找到最大的 divisor * 2^k 使得它小於等於當前的被除數
  - 從被除數中減去這個值，並將 2^k 累加到商中
  - 重複直到被除數小於除數
5. 正負號處理：使用 XOR 運算判斷結果的正負號。
6. 溢位：特殊處理 Integer.MIN_VALUE / -1 的情況，防止結果超出 32 位元範圍。
7. 使用 long 類型進行計算，避免中間過程的整數溢位。
*/