/*
題目：Reverse Integer
給定一個 32 位元有符號整數 x，回傳數字反轉後的結果。
如果反轉後的值超出 32 位元有符號整數範圍 [-2^31, 2^31 - 1]，則回傳 0。
假設環境不允許儲存 64 位元整數。
範例：x = 123 → Output: 321, x = -123 → Output: -321, x = 120 → Output: 21
*/

class Solution {
    public int reverse(int x) {
        int result = 0; // 儲存反轉後的結果
        
        // 當 x 還有數字需要處理時
        while (x != 0) {
            int pop = x % 10; // 取得 x 的最後一位數字
            x /= 10; // 移除 x 的最後一位數字
            
            // 檢查溢位：在更新 result 之前先檢查是否會超出範圍
            // 正溢位檢查：result > Integer.MAX_VALUE/10 或 result == Integer.MAX_VALUE/10 且 pop > 7
            // 負溢位檢查：result < Integer.MIN_VALUE/10 或 result == Integer.MIN_VALUE/10 且 pop < -8
            if (result > Integer.MAX_VALUE / 10 || (result == Integer.MAX_VALUE / 10 && pop > 7)) {
                return 0;
            }
            if (result < Integer.MIN_VALUE / 10 || (result == Integer.MIN_VALUE / 10 && pop < -8)) {
                return 0;
            }
            
            result = result * 10 + pop; // 將新的數字加入到 result 的末尾
        }
        
        return result; // 回傳反轉後的整數
    }
}

/*
解題思路：
1. 使用數學方法逐位反轉，避免使用字串或額外的資料結構。
2. 用 % 10 取得最後一位數字，用 / 10 移除最後一位數字。
3. 在不使用 64 位元整數的情況下檢測 32 位元整數溢位。
4. 溢位檢測：在執行 result * 10 + pop 之前先檢查是否會溢位。
5. 正溢位條件：result > MAX_VALUE/10 或者 result == MAX_VALUE/10 且 pop > 7。
6. 負溢位條件：result < MIN_VALUE/10 或者 result == MIN_VALUE/10 且 pop < -8。
7. 邊界值說明：Integer.MAX_VALUE = 2147483647，Integer.MIN_VALUE = -2147483648。
*/