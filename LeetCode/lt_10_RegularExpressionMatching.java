/*
題目：Regular Expression Matching
正規表達式匹配，支援 '.' 和 '*' 特殊字元：
'.' 匹配任意單一字元
'*' 匹配零個或多個前面的字元
匹配必須覆蓋整個輸入字串，不是部分匹配
範例：s = "aa", p = "a*" → true ('*' 表示零個或多個 'a'，所以 "aa" 匹配)
*/

class Solution {
    public boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        // dp[i][j] 表示 s 的前 i 個字元是否匹配 p 的前 j 個字元；空字串匹配空模式
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        
        // 處理空字串與非空模式的匹配（如 "a*b*c*" 可以匹配空字串）
        for (int j = 2; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2]; // '*' 匹配零次，忽略前面的字元和 '*'
            }
        }
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char sc = s.charAt(i - 1); // 字串 s 的第 i 個字元
                char pc = p.charAt(j - 1); // 模式 p 的第 j 個字元
                
                if (pc == '*') {
                    char prevChar = p.charAt(j - 2); // '*' 前面的字元
                    // '*' 匹配零次，忽略 "前字元*" 組合
                    dp[i][j] = dp[i][j - 2];
                    
                    // '*' 匹配一次或多次，且前字元能匹配當前字元
                    if (prevChar == '.' || prevChar == sc) {
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                } else {  // 處理一般字元或 '.' 的情況
                    if (pc == '.' || pc == sc) {
                        dp[i][j] = dp[i - 1][j - 1]; // 字元匹配，檢查前面的狀態，如果字元不匹配，dp[i][j] 保持 false（預設值）
                    }
                }
            }
        }
        
        return dp[m][n]; // 回傳整個字串是否匹配整個模式
    }
}

/*
解題思路：
1. 使用動態規劃解決，dp[i][j] 表示字串 s 的前 i 個字元是否匹配模式 p 的前 j 個字元。
2. 初始化 dp[0][0] = true（空字串匹配空模式），處理 "a*b*" 匹配空字串的情況。
3. 狀態轉移分兩種情況：
  - 當前字元不是 '*'：檢查字元是否匹配（相等或為 '.'），若匹配則繼承 dp[i-1][j-1]
  - 當前字元是 '*'：有兩種選擇
    (a) 匹配零次：dp[i][j] = dp[i][j-2]（忽略 "字元*" 組合）
    (b) 匹配一次或多次：如果前字元能匹配，dp[i][j] = dp[i-1][j]
4. '*' 它修飾前面的字元，可以讓該字元出現 0 次、1 次或多次。
5. 邊界處理：陣列索引與字串索引的對應關係（dp 多一行一列）。
*/