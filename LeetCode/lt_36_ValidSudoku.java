/*
題目：Valid Sudoku
1. 判斷一個 9x9 的數獨板是否有效
2. 需要驗證：(1) 每行不重複 (2) 每列不重複 (3) 每個3x3子格不重複
3. 只需要驗證已填入的數字，空格用 '.' 表示
*/

import java.util.HashSet;
import java.util.Set;

class Solution {
    public boolean isValidSudoku(char[][] board) {
        Set<String> seen = new HashSet<>(); // 使用 HashSet 來檢查重複
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char value = board[i][j];

                if (value == '.') { 
                    continue; // 跳過空格
                }
                
                // 為每個數字在行、列、子格中創建唯一標識符
                String inRow = "row" + i + "-" + value;
                String inCol = "col" + j + "-" + value;
                String inBox = "box" + (i/3) + (j/3) + "-" + value;
                
                // 檢查是否已經存在相同的標識符
                if (!seen.add(inRow) || !seen.add(inCol) || !seen.add(inBox)) {
                    return false; // 發現重複，數獨無效
                }
            }
        }
        return true; // 沒有發現重複，數獨有效
    }
}

/*
解題思路：
1. 使用一個 HashSet 來追蹤所有已見過的數字及其位置信息。
2. 遍歷整個 9x9 的棋盤，對每個非空格子，創建三個唯一標識符:
    -  "row" + 行號 + "-" + 數字值：標識該數字在某行出現
    -  "col" + 列號 + "-" + 數字值：標識該數字在某列出現  
    -  "box" + 子格號 + "-" + 數字值：標識該數字在某子格出現
3. 子格號的計算：(i/3)(j/3) 將 9x9 棋盤分為 9 個 3x3 子格
   - 例如：位置 (4,7) 屬於子格 (1,2) = "box12"
4. 使用 Set.add() 的返回值來檢查重複： 如果返回 false，表示該標識符已存在，即發現重複。
5. 只要發現任何重複就返回 false，全部檢查完畢且無重複則返回 true。
*/