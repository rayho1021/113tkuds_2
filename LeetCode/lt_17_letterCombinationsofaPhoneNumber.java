/*
題目：Letter Combinations of a Phone Number
給定一個包含 2-9 數字的字串，回傳該數字可能代表的所有字母組合。
數字與字母的對應關係如同電話按鍵：2=abc, 3=def, 4=ghi, 5=jkl, 6=mno, 7=pqrs, 8=tuv, 9=wxyz
範例：digits = "23" → ["ad","ae","af","bd","be","bf","cd","ce","cf"]
*/

class Solution {
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>(); // 儲存所有可能的字母組合
        if (digits == null || digits.length() == 0) return result; // 空字串回傳空
        
        String[] phoneMap = {
            "",     // 0 (不使用)
            "",     // 1 (不使用)
            "abc",  // 2
            "def",  // 3
            "ghi",  // 4
            "jkl",  // 5
            "mno",  // 6
            "pqrs", // 7
            "tuv",  // 8
            "wxyz"  // 9
        };
        
        // 使用回溯法生成所有可能的組合
        backtrack(result, phoneMap, digits, 0, new StringBuilder());
        return result; // 回傳所有組合
    }
   
    // 回溯遞迴
    private void backtrack(List<String> result, String[] phoneMap, String digits, int index, StringBuilder current) {
        // 遞迴終止條件：已處理完所有數字
        if (index == digits.length()) {
            result.add(current.toString()); // 將當前組合加入結果
            return;
        }
        
        String letters = phoneMap[digits.charAt(index) - '0']; // 取得當前數字對應的字母字串
        // 嘗試當前數字對應的每個字母
        for (int i = 0; i < letters.length(); i++) {
            current.append(letters.charAt(i)); // 選擇當前字母
            backtrack(result, phoneMap, digits, index + 1, current); // 遞迴處理下一個數字
            current.deleteCharAt(current.length() - 1); // 回溯：移除剛才加入的字母
        }
    }
}

/*
解題思路：
1. 使用回溯法（Backtracking）來生成所有可能的字母組合。
2. 建立數字到字母的映射陣列，方便快速查找每個數字對應的字母。
3. 回溯過程：
  - 對於每個數字位置，嘗試該數字對應的所有字母
  - 選擇一個字母後，遞迴處理下一個數字
  - 處理完後回溯，移除剛才的選擇，嘗試其他字母
4. 當處理完所有數字位置時，將當前組合加入結果列表。
5. 每層代表一個數字位置，每個分支代表該數字的一個字母選擇。
*/