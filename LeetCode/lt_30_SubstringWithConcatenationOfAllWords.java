/*
題目：Substring with Concatenation of All Words
給定字串 s 和字串陣列 words，所有 words 中的字串長度相同。
找出 s 中所有由 words 任意排列連接組成的子字串的起始索引。
範例：s = "barfoothefoobarman", words = ["foo","bar"] → [0,9]
(索引 0 開始的 "barfoo" 和索引 9 開始的 "foobar" 都是有效的連接)
*/

class Solution {
   public List<Integer> findSubstring(String s, String[] words) {
       List<Integer> result = new ArrayList<>(); // 儲存結果
       if (s == null || s.length() == 0 || words == null || words.length == 0) {
           return result; // 邊界
       }
       
       int wordLen = words[0].length(); // 每個單字的長度
       int totalLen = words.length * wordLen; // 連接字串的總長度
       if (s.length() < totalLen) return result; // 字串長度不足，無法包含所有單字
       
       // 單字頻率映射表
       Map<String, Integer> wordCount = new HashMap<>();
       for (String word : words) {
           wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
       }
       
       // 從每個可能的起始位置開始檢查
       for (int i = 0; i <= s.length() - totalLen; i++) {
           Map<String, Integer> seen = new HashMap<>(); // 記錄當前視窗中單字的出現次數
           int j = 0; // 記錄已處理的單字數量
           
           // 逐個檢查單字
           while (j < words.length) {
               int start = i + j * wordLen; // 當前單字在 s 中的起始位置
               String word = s.substring(start, start + wordLen); // 提取當前單字
               
               // 如果當前單字不在 words 中，直接跳出
               if (!wordCount.containsKey(word)) {
                   break;
               }
               
               seen.put(word, seen.getOrDefault(word, 0) + 1); // 記錄單字出現次數
               
               // 如果當前單字出現次數超過 words 中的次數，跳出
               if (seen.get(word) > wordCount.get(word)) {
                   break;
               }

               j++; // 處理下一個單字
           }
           
           // 如果所有單字都成功匹配，加入結果
           if (j == words.length) {
               result.add(i);
           }
       }
       return result;
   }
}

/*
解題思路：
1. 使用滑動視窗，對每個可能的起始位置檢查是否包含所有單字的排列組合。
2. 建立 words 陣列的頻率映射表，記錄每個單字應該出現的次數。
3. 視窗大小：固定為所有單字長度的總和（wordLen × words.length）。
4. 在每個視窗內，按照單字長度分割字串並逐一檢查。
5. 使用 HashMap 記錄當前視窗中每個單字的出現次數。
6. 終止：
  - 遇到不在 words 中的單字立即跳出
  - 單字出現次數超過預期時立即跳出
7. 當所有單字都成功匹配且次數正確時，記錄起始索引。
*/