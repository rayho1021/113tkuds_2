/*
題目：Zigzag Conversion
將字串按照之字形排列在給定行數上，然後逐行讀取轉換後的字串。
範例：s = "PAYPALISHIRING", numRows = 3
P   A   H   N
A P L S I I G  
Y   I   R
輸出："PAHNAPLSIIGYIR"
*/

class Solution {
    public String convert(String s, int numRows) {
        // 只有一行或字串長度 <= 行數時，直接回傳原字串
        if (numRows == 1 || s.length() <= numRows) {
            return s;
        }
       
        // 為每一行建立 StringBuilder 來收集字元
        StringBuilder[] rows = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            rows[i] = new StringBuilder();
        }
       
        int currentRow = 0; // 目前處理的行數
        boolean goingDown = false; // true 表示向下，false 表示向上
       
        // 遍歷字串中的每個字元
        for (char c : s.toCharArray()) {
            rows[currentRow].append(c); // 將字元加入當前行
            
            // 判斷是否需要改變方向：到達第一行或最後一行時改變方向
            if (currentRow == 0 || currentRow == numRows - 1) {
                goingDown = !goingDown;
            }
            
            // 根據方向移動到下一行
            currentRow += goingDown ? 1 : -1;
        }
       
        // 將所有行的內容合併成最終結果
        StringBuilder result = new StringBuilder();
        for (StringBuilder row : rows) {
            result.append(row);
        }
       
        return result.toString(); // 回傳轉換後的字串
    }
}

/*
解題思路：
1. 使用模擬法，按照之字形的移動規律將字元分配到對應的行中。
2. 建立 numRows 個 StringBuilder，每個對應一行，用於收集該行的字元。
3. 使用 currentRow 追蹤當前所在的行，goingDown 標記移動方向。
4. 移動規律：在第 0 行時開始向下，在最後一行時開始向上，形成之字形路徑。
5. 每處理一個字元後，根據當前位置決定下一步的移動方向。
6. 邊界條件：當到達第一行或最後一行時，必須改變移動方向。
7. 最後將各行的 StringBuilder 內容依序合併，得到最終的轉換結果。
*/