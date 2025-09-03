/*
題目：Two Sum
給定一個整數陣列 nums 和一個目標值 target，請回傳兩個索引，使得 nums[i] + nums[j] == target。
範例: Input: nums = [3,2,4], target = 6 → Output: [1,2]
*/


class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>(); 
        // 使用 HashMap 儲存數值與索引，加速查找
        for (int i = 0; i < nums.length; i++) {
            int temp = target - nums[i];
            if (map.containsKey(temp)) {
                // 找到符合條件的組合，回傳索引
                return new int[] { map.get(temp), i };
            }
            map.put(nums[i], i);
        }
        return null; // 沒找到則回傳空陣列
    }
}

/*
解題思路：
1. 題目要求找到兩個數字相加等於 target。
2. 使用 HashMap 儲存「數值 → 索引」，查找是否有另一個數值能與當前數字配對。
3. 時間複雜度 O(n)，只需一次迴圈即可完成。
*/