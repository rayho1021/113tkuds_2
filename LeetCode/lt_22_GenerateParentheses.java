/*
題目：Generate Parentheses
給定 n 對括號，產生所有有效的括號組合。
有效括號組合必須滿足：每個左括號都有對應的右括號，且括號順序正確。
範例：n = 3 → ["((()))","(()())","(())()","()(())","()()()"]
*/

class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>(); // 儲存所有有效的括號組合
        // 使用回溯法生成括號組合，回傳所有有效組合
        backtrack(result, new StringBuilder(), 0, 0, n);
        return result;
    }
    
    // 回溯遞迴
    private void backtrack(List<String> result, StringBuilder current, int open, int close, int max) {
        // 當前字串長度達到 2*n（n 對括號），遞迴終止
        if (current.length() == max * 2) {
            result.add(current.toString()); // 加入有效組合到結果
            return;
        }
        
        // 如果左括號數量還沒達到 n，可以繼續添加左括號
        if (open < max) {
            current.append('('); // 選擇：添加左括號
            backtrack(result, current, open + 1, close, max); // 遞迴
            current.deleteCharAt(current.length() - 1); // 回溯：移除剛才的選擇
        }
        
        // 如果右括號數量少於左括號數量，可以添加右括號
        if (close < open) {
            current.append(')'); // 選擇：添加右括號
            backtrack(result, current, open, close + 1, max); // 遞迴
            current.deleteCharAt(current.length() - 1); // 回溯：移除剛才的選擇
        }
    }
}

/*
解題思路：
1.使用回溯法 (Backtracking) 生成所有可能的有效括號組合。
2.用 open 和 close 分別記錄當前已使用的左括號和右括號數量。
3. 約束：
  - 左括號數量不能超過 n
  - 右括號數量不能超過當前左括號數量（確保括號匹配）
4. 決策樹：每個節點有兩種選擇（添加左括號或右括號），受上述約束限制。
5. 剪枝：
  - 當 open >= max 時，不能再添加左括號
  - 當 close >= open 時，不能再添加右括號
6. 終止：當字串長度達到 2*n 時，必定形成一個有效的括號組合。
7. 回溯：每次遞迴返回時，移除最後添加的字元，嘗試其他可能性。
*/