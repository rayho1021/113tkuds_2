/*
題目：Combination Sum II
給定一個候選數字集合 candidates 和目標數字 target，找出所有和等於目標的唯一組合
每個數字在組合中只能使用一次，且解集不能包含重複的組合
範例: 
Input: candidates = [2,5,2,1,2], target = 5
Output: 
[
[1,2,2],
[5]
]
*/

class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> currentCombination = new ArrayList<>();
        Arrays.sort(candidates); // 先排序，方便跳過重複元素
        
        // 開始回溯搜索
        backtrack(candidates, target, 0, currentCombination, result);
        return result;
    }
    
    /**
     回溯搜索所有可能的組合
     * candidates 候選數字陣列 (已排序)、remainingTarget 剩餘目標值
     * startIndex 當前搜索的起始索引、currentCombination 當前正在構建的組合、result 結果列表
     */
    private void backtrack(int[] candidates, int remainingTarget, int startIndex,
                           List<Integer> currentCombination, List<List<Integer>> result) {

        // 找到有效組合
        if (remainingTarget == 0) {
            result.add(new ArrayList<>(currentCombination)); // 添加當前組合的副本
            return;
        } 
        
        // 從 startIndex 開始遍歷候選數字
        for (int i = startIndex; i < candidates.length; i++) {
            // 剪枝：如果當前數字已經大於剩餘目標，後面的更大數字也不可能
            if (candidates[i] > remainingTarget) {
                break;
            }
            
            // 跳過重複元素：避免在同一層產生重複組合
            // i > startIndex 確保不會跳過第一個相同的元素
            if (i > startIndex && candidates[i] == candidates[i - 1]) {
                continue;
            }

            currentCombination.add(candidates[i]); // 選擇當前候選數字
            
            // 遞歸搜索：每個數字只能用一次，所以 startIndex = i + 1
            backtrack(candidates, remainingTarget - candidates[i], i + 1,
                      currentCombination, result);
            
            // 回溯：移除最後添加的數字，嘗試下一個候選
            currentCombination.remove(currentCombination.size() - 1);
        }
    }
}

/*
解題思路：
1. 與之前 Combination Sum 的主要區別：
   - 每個數字只能使用一次（遞歸時 startIndex = i + 1）
   - 需要處理重複數字，避免產生重複組合

2. 避免重複組合：
   - 首先對陣列排序，使相同數字相鄰
   - 在同一層遞歸中跳過重複數字：if (i > startIndex && candidates[i] == candidates[i-1])
   - 這確保對於重複數字，按順序使用，避免 [1,1,6] 和 [1,1,6] 的重複

3. i > startIndex：
   - i == startIndex：這是該層第一個元素，必須考慮
   - i > startIndex：這不是第一個元素，如果與前一個相同則跳過

4. 剪枝：
   - 由於陣列已排序，當 candidates[i] > remainingTarget 時可直接 break
   - 因為後續所有數字都會更大，不可能滿足條件

5. 回溯：
   - 選擇：將候選數字加入當前組合
   - 探索：遞歸搜索（startIndex = i + 1，每個數字只用一次）
   - 撤銷：移除最後加入的數字
*/