/*
題目：Roman to Integer
將羅馬數字轉換為整數，需要處理減法形式（小數字在大數字前面）
羅馬數字符號：I=1, V=5, X=10, L=50, C=100, D=500, M=1000
減法規則：IV=4, IX=9, XL=40, XC=90, CD=400, CM=900
範例：s = "MCMXCIV" → 1994 (M=1000, CM=900, XC=90, IV=4)
*/

class Solution {
   public int romanToInt(String s) {
       Map<Character, Integer> romanMap = new HashMap<>();
       romanMap.put('I', 1);
       romanMap.put('V', 5);
       romanMap.put('X', 10);
       romanMap.put('L', 50);
       romanMap.put('C', 100);
       romanMap.put('D', 500);
       romanMap.put('M', 1000);
       
       int result = 0; // 儲存最終結果
       
       // 從左到右遍歷羅馬數字字串
       for (int i = 0; i < s.length(); i++) {
           int currentValue = romanMap.get(s.charAt(i)); // 當前字元的數值
           
           // 檢查是否為減法形式：當前數值 < 下一個數值
           if (i + 1 < s.length() && currentValue < romanMap.get(s.charAt(i + 1))) {
               result -= currentValue; // 減法形式，減去當前數值
           } else {
               result += currentValue; // 一般情況，加上當前數值
           }
       }
       return result;
   }
}

/*
解題思路：
1. 使用從左到右掃描的方法，根據當前字元與下一個字元的關係決定加減操作。
2. 如果當前數值小於下一個數值，則是減法形式，需要減去當前數值。
3. 減法形式判斷：比較相鄰兩個字元的數值大小來判斷是否為減法組合。
4. 邊界處理：檢查 i+1 是否越界，避免陣列存取錯誤。
*/