/*
題目: 緊急通報格式括號檢查
1.
* () 表可選段、[] 表區域代碼群組、{} 表變數替換區。
* 驗證字串格式，是否「完全正確巢狀」且無錯配，判斷括號是否成對、順序合法、無交錯錯誤。
2. 解法核心：Stack 資料結構 + 括號配對檢查
3. 邊界：
- 空字串 → true
- 單一字元 → false
- 以閉括號開頭 ")..." → false
- 巢狀與並列混合 "{()()}" → 需正確處理
4. 複雜度：時間 O(n)，空間 O(n)
5.
- 輸入：一行括號字串 s (0 ≤ |s| ≤ 10^5，僅含 ()[]{})
- 輸出：true/false
*/

import java.util.*;

public class LC20_ValidParentheses_AlertFormat {
    public boolean isValid(String s) {
        if (s == null || s.length() % 2 != 0) {
            return false; // 空字串視為合法，奇數長度必定不匹配
        }
        
        // 建立對應關係
        Map<Character, Character> closeToOpen = new HashMap<>();
        closeToOpen.put(')', '(');
        closeToOpen.put(']', '[');
        closeToOpen.put('}', '{');
        
        //  Stack 儲存開括號，等待配對
        Stack<Character> stack = new Stack<>();
        
        // 逐字元檢查括號配對
        for (char c : s.toCharArray()) {
            if (closeToOpen.containsKey(c)) {
                // 遇到閉括號：檢查是否有對應的開括號
                if (stack.isEmpty() || stack.pop() != closeToOpen.get(c)) {
                    return false; // 無匹配開括號或類型不符
                }
            } 
            else {
                
                stack.push(c); // 遇到開括號：推入 stack 等待配對
            }
        }
        
        // 所有括號都應該配對完成，stack 應為空
        return stack.isEmpty();
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LC20_ValidParentheses_AlertFormat solution = new LC20_ValidParentheses_AlertFormat();
        String input = scanner.nextLine().trim();
        
        // 檢查括號是否合法
        boolean result = solution.isValid(input);
        System.out.println("格式是否合格:" + result);
        scanner.close();
    }
}

/*
解題思路 : 
1.利用 Stack 後進先出： 儲存開括號，當遇到閉括號時檢查棧頂是否為對應的開括號。
2.HashMap 配對：建立 Map<Character, Character> 將閉括號映射到對應的開括號。
3.檢查：遇到閉括號時立即檢查配對，若不匹配則提早返回 false。
4.驗證：處理完所有字元後，檢查 stack 是否為空，確保所有開括號都有對應的閉括號。
5.邊界處理：奇數長度字串必定無法完全配對，直接返回 false。
*/