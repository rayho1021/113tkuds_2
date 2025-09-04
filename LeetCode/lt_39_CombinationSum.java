/*
題目：Combination Sum
給定一個不重複整數陣列 candidates 和 目標整數 target
返回所有和等於 target 的唯一組合列表，同一個數字可以被無限次選擇
範例: Input: candidates = [2,3,5], target = 8 --> Output: [[2,2,2,2],[2,3,3],[3,5]]
*/

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> currentCombination = new ArrayList<>();
        backtrack(candidates, target, 0, currentCombination, result); // 開始回溯搜索
        return result;
    }

    // 回溯搜索所有可能的組合
    // candidates 候選數字陣列、remainingTarget 剩餘目標值、startIndex 當前搜索的起始索引（避免重複組合）、currentCombination 當前正在構建的組合、result 結果列表
    private void backtrack(int[] candidates, int remainingTarget, int startIndex, List<Integer> currentCombination, List<List<Integer>> result) {
        
        // 基礎情況：找到有效組合
        if (remainingTarget == 0) {
            result.add(new ArrayList<>(currentCombination)); // 添加當前組合的副本
            return;
        }
        
        // 剪枝：如果剩餘目標值小於0，無需繼續
        if (remainingTarget < 0) {
            return;
        }
        
        // 從 startIndex 開始遍歷候選數字
        for (int i = startIndex; i < candidates.length; i++) {
            currentCombination.add(candidates[i]); // 選擇當前候選數字
            // 探索：遞歸搜索，允許重複選擇當前數字（i 而非 i+1）
            backtrack(candidates, remainingTarget - candidates[i], i, currentCombination, result);
            currentCombination.remove(currentCombination.size() - 1); // 回溯：移除最後添加的數字，嘗試下一個候選
        }
    }
}

/*
解題思路：
1. 使用回溯（Backtracking）來尋找所有可能的組合：
   - 對每個候選數字，決定是否將其加入當前組合
   - 同一個數字可以被重複選擇多次
   - 當剩餘目標值為0時，找到一個有效組合

2. 避免重複組合：
   - 使用 startIndex 確保組合按升序生成
   - 例如：[2,3,3] 和 [3,2,3] 是同一組合，通過 startIndex 只生成前者

3. 回溯：
   - 選擇：將候選數字加入當前組合
   - 探索：遞歸搜索剩餘目標值（startIndex = i，允許重複選擇，非 i+1）
   - 撤銷：移除最後加入的數字，嘗試其他可能

4. 剪枝：
   - 當 remainingTarget < 0 時立即返回，避免無效搜索
   - 當 remainingTarget = 0 時找到解，添加到結果中
*/