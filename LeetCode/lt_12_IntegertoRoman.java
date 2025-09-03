/*
題目：Integer to Roman
將整數轉換為羅馬數字，需要處理特殊的減法形式（如 4=IV, 9=IX, 40=XL 等）
羅馬數字符號：I=1, V=5, X=10, L=50, C=100, D=500, M=1000
減法形式：IV=4, IX=9, XL=40, XC=90, CD=400, CM=900
範例：num = 3749 → "MMMDCCXLIX", num = 1994 → "MCMXCIV"
*/

class Solution {
    public String intToRoman(int num) {
        // 按照數值大小降序排列所有可能的羅馬數字組合
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        
        StringBuilder result = new StringBuilder(); // 結果字串
        
        // 從最大的數值開始，依序處理
        for (int i = 0; i < values.length; i++) {
            int count = num / values[i]; // 計算當前數值能使用多少次該羅馬數字組合
            if (count > 0) {
                for (int j = 0; j < count; j++) {  // 將對應的羅馬符號重複添加 count 次
                    result.append(symbols[i]);
                }
                // 從剩餘數值中減去已處理的部分
                num %= values[i];
            }
        }
        
        return result.toString(); // 回傳完整的羅馬數字字串
    }
}

/*
解題思路：
1. 使用貪心演算法，從最大的羅馬數字開始，依序選擇能使用的最大組合。
2. 將所有可能的羅馬數字組合（包含減法形式）預先存入陣列並排序。
3. 減法形式處理：直接將 CM(900), CD(400), XC(90), XL(40), IX(9), IV(4) 當作獨立符號。
4. 對每個數值組合，計算它在目標數字中出現的次數，然後添加對應符號。
5. 每次處理完一個組合後，使用取餘運算更新剩餘的數值。
*/