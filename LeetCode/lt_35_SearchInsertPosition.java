/*
題目：Search Insert Position
給定一個已排序的不重複整數數組和一個目標值，返回目標值的索引，如果不存在則返回應該插入的位置索引。
要求時間複雜度為 O(log n)
範例: Input: nums = [1,3,5,6], target = 5 --> Output: 2
*/

class Solution {
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                return mid; // 找到目標值，直接返回索引
            } 
            else if (nums[mid] < target) {
                left = mid + 1; // 目標值在右半部分，向右搜索
            } 
            else {
                right = mid - 1; // 目標值在左半部分，向左搜索
            }
        }
        // 未找到目標值，left 就是應該插入的位置
        // 此時 left > right，left 指向第一個大於 target 的位置
        return left;
    }
}

/*
解題思路：
1. 使用二分搜索來查找目標值。
2. 如果找到目標值，直接返回其索引。
3. 如果未找到目標值，二分搜索結束時：
   - left 指向第一個大於 target 的元素位置
   - right 指向最後一個小於 target 的元素位置
   - 因此 left 就是 target 應該插入的位置
4. 二分搜索的特性保證了當搜索結束時，left 總是指向正確的插入位置：
   - 如果 target 小於所有元素，left = 0（插入開頭）
   - 如果 target 大於所有元素，left = nums.length（插入末尾）
   - 如果 target 在中間，left 指向第一個大於 target 的位置
5. 時間複雜度：O(log n) - 二分搜索
*/