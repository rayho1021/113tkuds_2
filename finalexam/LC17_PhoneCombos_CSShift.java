/*
題目: 手機門號組合
1.將由數字 2–9 組成的字串（可能為空）展開成所有可能的字母組合，字母對應依標準電話鍵盤（2:abc, 3:def, …, 9:wxyz）。
2.解法核心：以回溯（DFS）逐位展開：一層代表一個數字，分支為其對應字母，使用 StringBuilder。
3.複雜度：O(乘積)；空間 O(深度)
4.
輸入：一行數字字串，限制: 0 <= |digits| <= 15； 僅含 2–9。
輸出：每行一個可能的字母組合
*/

import java.util.*;

public class LC17_PhoneCombos_CSShift {
    public static void main(String[] args) {
        // 讀取整行，允許空字串，不輸出任何結果
        Scanner sc = new Scanner(System.in);
        String digits = sc.hasNextLine() ? sc.nextLine().trim() : "";
        sc.close(); 
        if (digits.isEmpty()) {
            return;
        }
        
        // 依標準電話鍵盤字母順序
        String[] map = {
            "abc", // 2
            "def", // 3
            "ghi", // 4
            "jkl", // 5
            "mno", // 6
            "pqrs",// 7
            "tuv", // 8
            "wxyz" // 9
        };

        StringBuilder path = new StringBuilder(digits.length()); // path 儲存當前組合
        // 回溯：逐位展開，避免巨大輸出佔記憶體，直接在葉節點打印
        dfs(digits, 0, map, path);
    }
    
    // 回溯展開所有可能組合。
    private static void dfs(String digits, int idx, String[] map, StringBuilder path) {
        // 當前綴長度 == digits 長度 → 產生一個完整組合，直接輸出
        if (idx == digits.length()) {
            System.out.println(path.toString());
            return;
        }
        char d = digits.charAt(idx); // 當前數字對應的字母集合
        String letters = map[d - '2']; // '2' 對應 map[0] → 用 d - '2' 計算索引

        // 依鍵盤定義順序逐一嘗試
        for (int i = 0; i < letters.length(); i++) {
            path.append(letters.charAt(i));        // 選擇
            dfs(digits, idx + 1, map, path);       // 探索下一層
            path.deleteCharAt(path.length() - 1);  // 撤銷選擇（回溯）
        }
    }
}


/*
解題思路 : 
1.樹狀模型：將輸入視為一棵樹，深度為 |digits|；每層節點分支數為當位數字對應的字母數（3 或 4）。
2.回溯展開：用 StringBuilder 儲存當前組合，走到葉節點（長度等於 digits）即可立即輸出，避免累積所有結果造成記憶體壓力。
3.映射索引：以 digit - '2' 取對應 map 的索引，確保字母輸出順序符合標準電話鍵盤。
4.邊界處理：空字串直接結束、無輸出；單一數字則輸出對應字母各一行；含 7 或 9 的位元可自然支援 4 分支。
5.複雜度：輸出敏感問題，時間與總組合數成正比；額外空間為遞迴深度 O(深度)。
*/