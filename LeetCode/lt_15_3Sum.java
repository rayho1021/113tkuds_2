/*
題目：3Sum
在整數陣列中找出所有和為 0 的三元組 [nums[i], nums[j], nums[k]]，其中 i != j != k。
結果不能包含重複的三元組。
範例：nums = [-1,0,1,2,-1,-4] → [[-1,-1,2],[-1,0,1]]
*/

class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>(); // 儲存結果
        Arrays.sort(nums); // 先將陣列排序，便於使用雙指標和去重
        
        // 固定第一個數字，用雙指標找剩下兩個
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue; // 跳過重複的第一個數字，避免產生重複三元組
            if (nums[i] > 0) break; // 如果第一個數字 > 0，後面都是正數，不可能和為 0
            
            int left = i + 1; // 左指標，從第一個數字的下一位開始
            int right = nums.length - 1; // 右指標，從陣列末尾開始
            
            // 使用雙指標尋找 nums[i] + nums[left] + nums[right] = 0
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right]; // 計算三數之和
                
                if (sum == 0) {
                    // 找到一組解，加入結果列表
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1]) left++; // 跳過重複的左指標數字
                    while (left < right && nums[right] == nums[right - 1]) right--; // 跳過重複的右指標數字
                    left++; // 移動左指標
                    right--; // 移動右指標
                } 

                else if (sum < 0) {
                    left++; // 總和太小，需要更大的數，左指標右移
                } 
                else {
                    right--; // 總和太大，需要更小的數，右指標左移
                }
            }
        }
        return result; // 回傳所有不重複的三元組
    }
}

/*
解題思路：
1. 先將陣列排序，便於使用雙指標和去重。
2. 固定第一個數字 nums[i]，然後用雙指標在剩餘部分找兩個數使三數和為 0。
3. 雙指標：left 從 i+1 開始，right 從末尾開始，根據三數和調整指標位置。
4. 去重：
  - 跳過重複的第一個數字：if (i > 0 && nums[i] == nums[i-1]) continue
  - 找到解後跳過重複的 left 和 right 指標數字
5. 如果 nums[i] > 0，由於陣列已排序，後面都是正數，不可能和為 0。
6. sum < 0 時左指標右移（增大和），sum > 0 時右指標左移（減小和）。
*/