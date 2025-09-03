/*
題目：4Sum
在整數陣列中找出所有和為 target 的四元組 [nums[a], nums[b], nums[c], nums[d]]，其中 a != b != c != d。
結果不能包含重複的四元組。
範例：nums = [1,0,-1,0,-2,2], target = 0 → [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
*/

class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>(); // 儲存結果
        if (nums == null || nums.length < 4) return result; // 陣列長度不足 4 則回傳空
        
        Arrays.sort(nums); // 排序陣列
        
        // 固定前兩個數字，用雙指標找後兩個數字
        for (int i = 0; i < nums.length - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue; // 跳過重複的第一個數字
            
            for (int j = i + 1; j < nums.length - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue; // 跳過重複的第二個數字
                
                int left = j + 1; // 左指標從第二個數字的下一位開始
                int right = nums.length - 1; // 右指標從陣列末尾開始
                
                // 使用雙指標尋找 nums[i] + nums[j] + nums[left] + nums[right] = target
                while (left < right) {
                    long sum = (long)nums[i] + nums[j] + nums[left] + nums[right]; // 使用 long 避免溢位
                    
                    if (sum == target) {
                        // 找到一組解，加入結果列表
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        
                        // 跳過重複的左、右指標數字
                        while (left < right && nums[left] == nums[left + 1]) left++;
                        while (left < right && nums[right] == nums[right - 1]) right--;
                        
                        left++; // 移動左指標
                        right--; // 移動右指標
                    } 
                    else if (sum < target) {
                        left++; // 總和太小，需要更大的數，左指標右移
                    } 
                    else {
                        right--; // 總和太大，需要更小的數，右指標左移
                    }
                }
            }
        } 
        return result; // 回傳所有不重複的四元組
    }
}

/*
解題思路：
1. 固定前兩個數字，用雙指標在剩餘部分找後兩個數字。
2. 先排序陣列，便於使用雙指標和去重
3. 四層嵌套結構：
  - 外層固定第一個數字 nums[i]
  - 第二層固定第二個數字 nums[j]
  - 內層使用雙指標 left 和 right 尋找後兩個數字
4. 去重：
  - 跳過重複的第一個數字：if (i > 0 && nums[i] == nums[i-1]) continue
  - 跳過重複的第二個數字：if (j > i+1 && nums[j] == nums[j-1]) continue
  - 找到解後跳過重複的 left 和 right 指標數字
5. 使用 long 類型計算 sum，避免四個 int 相加時的整數溢位問題。
6. 指標移動規則：sum < target 時左指標右移，sum > target 時右指標左移。
*/