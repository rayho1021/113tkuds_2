/*
題目：3Sum Closest
在整數陣列中找出三個數字，使其和最接近目標值 target。
回傳這三個數字的和。假設每個輸入只有一個唯一解。
範例：nums = [-1,2,1,-4], target = 1 → Output: 2 (因為 -1 + 2 + 1 = 2 最接近 1)
*/

class Solution {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums); // 排序陣列
        int closestSum = nums[0] + nums[1] + nums[2]; // 初始化最接近的和為前三個數字的和
        
        // 固定第一個數字，使用雙指標找最接近 target 的三數和
        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1; // 左指標從第一個數字的下一位開始
            int right = nums.length - 1; // 右指標從陣列末尾開始
            
            // 雙指標搜尋
            while (left < right) {
                int currentSum = nums[i] + nums[left] + nums[right]; // 計算當前三數之和
                if (currentSum == target) {  // 如果當前和正好等於目標值，直接回傳
                    return currentSum;
                }
                
                // 如果當前和比之前記錄的更接近目標值，更新最接近的和
                if (Math.abs(currentSum - target) < Math.abs(closestSum - target)) {
                    closestSum = currentSum;
                }
                
                if (currentSum < target) {
                    left++; // 當前和小於目標，需要增大，左指標右移
                } 
                else {
                    right--; // 當前和大於目標，需要減小，右指標左移
                }
            }
        }
        return closestSum; // 回傳最接近目標值的三數之和
    }
}

/*
解題思路：
1. 使用與 3Sum 類似的雙指標方法，但目標是找最接近 target 的和而非等於 0 的和。
2. 先對陣列排序，使雙指標技巧能有效工作。
3. 固定第一個數字 nums[i]，用雙指標在剩餘區間尋找最佳的兩個數字組合。
4. 使用 Math.abs(currentSum - target) 計算當前和與目標的距離。
5. 當找到更接近目標的和時，更新 closestSum。
6. 根據當前和與目標的大小關係決定移動方向：
  - currentSum < target：左指標右移（增大和）
  - currentSum > target：右指標左移（減小和）
7. 如果找到完全相等的和，立即回傳（最優解），提前結束。
*/