/*
題目：Sudoku Solver
通過填充空格來解決數獨謎題，每行、每列、每個3x3子格中數字1-9都必須恰好出現一次
'.' 表示空格
*/

class Solution {
    public void solveSudoku(char[][] board) {
        solve(board);
    }
    
    // 使用回溯法解決數獨
    private boolean solve(char[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == '.') {  // 找到空格
                    // 嘗試填入數字 1-9
                    for (char digit = '1'; digit <= '9'; digit++) {
                        if (isValid(board, row, col, digit)) {
                            board[row][col] = digit; // 如果數字有效，填入並遞迴求解
                            // 遞迴解決剩餘部分
                            if (solve(board)) {
                                return true; // 找到解答
                            }
                            board[row][col] = '.'; // 回溯：移除當前數字，嘗試下一個
                        }
                    }
                    // 所有數字都無效，返回 false 觸發回溯
                    return false;
                }
            }
        }
        // 所有格子都已填滿，找到完整解答
        return true;
    }
    
    // 檢查在指定位置填入數字是否有效
    private boolean isValid(char[][] board, int row, int col, char digit) {
        // 檢查行是否重複
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == digit) {
                return false;
            }
        }
        // 檢查列是否重複
        for (int i = 0; i < 9; i++) {
            if (board[i][col] == digit) {
                return false;
            }
        }
        
        // 檢查 3x3 子格是否重複
        int boxRow = (row / 3) * 3; // 子格起始行
        int boxCol = (col / 3) * 3; // 子格起始列
        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if (board[i][j] == digit) {
                    return false;
                }
            }
        }
        return true; // 該數字在此位置有效
    }
}

/*
解題思路：
1. 使用回溯（Backtracking）來求解數獨：
   - 遍歷棋盤找到第一個空格 ('.')
   - 嘗試填入數字 1-9，檢查是否符合數獨規則
   - 如果有效，遞迴解決剩餘問題
   - 如果遞迴成功返回 true，表示找到解答
   - 如果遞迴失敗，回溯（清空當前格子）並嘗試下一個數字

2. 檢查：
   - 檢查同一行是否已存在該數字
   - 檢查同一列是否已存在該數字  
   - 檢查同一個 3x3 子格是否已存在該數字

3. 3x3 子格的定位：
   - 子格起始行：(row / 3) * 3
   - 子格起始列：(col / 3) * 3
   - 然後遍歷該 3x3 區域檢查重複

4. 回溯：
   - 當所有數字都無法填入某個位置時，返回 false
   - 觸發回溯，撤銷之前的選擇，嘗試其他可能性
*/