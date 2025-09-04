/*
題目：Longest Valid Parentheses
給定一個只包含 '(' 和 ')' 的字串，找出最長有效括號子字串的長度
有效括號子字串是指能正確配對的括號序列
範例: Input: s = "(()" --> Output: 2
解釋: 最長有效括號子字串是 "()".
*/

class Solution {
    public int longestValidParentheses(String s) {
        if (s == null || s.length() < 2) {
            return 0; // 空字串 or 長度 < 2 無法形成有效括號
        }
        
        // 使用堆疊儲存索引，初始推入 -1 作為基準點，用於計算長度
        Stack<Integer> stack = new Stack<>();
        stack.push(-1); 
        
        int maxLength = 0;
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
  
            if (c == '(') {
                stack.push(i); // 遇到左括號，將索引推入堆疊
            } 
            else {
                stack.pop(); // 遇到右括號，彈出堆疊頂端
                
                if (stack.isEmpty()) {
                    stack.push(i); // 堆疊為空表示當前右括號無法配對，將當前索引作為新的基準點
                } 
                else {
                    // 計算當前有效括號的長度: 當前索引 - 堆疊頂端索引 = 有效括號長度
                    int currentLength = i - stack.peek();
                    maxLength = Math.max(maxLength, currentLength);
                }
            }
        }
        return maxLength;
    }
}

/*
解題思路：
1. 使用堆疊來追蹤括號的配對情況，堆疊中儲存字元的索引而非字元本身。
2. 初始時在堆疊中推入 -1 作為基準點，用於計算長度。
3. 遇到左括號 '(' 時，將其索引推入堆疊。
4. 遇到右括號 ')' 時：
   - 先彈出堆疊頂端（可能是配對的左括號索引，或是基準點）
   - 如果彈出後堆疊為空，表示當前右括號無法配對，將當前索引作為新基準點
   - 如果堆疊不為空，計算 (當前索引 - 堆疊頂端索引) 得到有效括號長度
5. 持續更新最長有效括號長度。
*/