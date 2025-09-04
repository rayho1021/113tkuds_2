/*
題目：Find First and Last Position of Element in Sorted Array
給定一個非遞減排序的整數數組，找出給定目標值的起始和結束位置
如果目標值不存在，返回 [-1, -1]，要求時間複雜度為 O(log n)
範例: Input: nums = [5,7,7,8,8,10], target = 6 --> Output: [-1,-1]
*/

class Solution {
    public int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return new int[]{-1, -1};
        }
        
        int firstPos = findFirst(nums, target); // 分別找到第一個和最後一個位置
        int lastPos = findLast(nums, target);
        return new int[]{firstPos, lastPos};
    }
    
    // 找到目標值的第一個出現位置
    private int findFirst(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int result = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] == target) {
                result = mid; // 記錄當前找到的位置
                right = mid - 1; // 繼續向左搜索更早的位置
            } else if (nums[mid] < target) {
                left = mid + 1; // 目標值在右半部分
            } else {
                right = mid - 1; // 目標值在左半部分
            }
        }
        return result;
    }
    
    // 找到目標值的最後一個出現位置
    private int findLast(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int result = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                result = mid; // 記錄當前找到的位置
                left = mid + 1; // 繼續向右搜索更晚的位置
            } 
            else if (nums[mid] < target) {
                left = mid + 1; // 目標值在右半部分
            } 
            else {
                right = mid - 1; // 目標值在左半部分
            }
        }
        return result;
    }
}

/*
解題思路：
1. 使用兩次二分搜索分別找到目標值的第一個和最後一個位置。
2. 找第一個位置時：當找到目標值後，繼續向左搜索，看是否還有更早的相同值。
3. 找最後一個位置時：當找到目標值後，繼續向右搜索，看是否還有更晚的相同值。
- findFirst: 找到目標值後，right = mid - 1，繼續在左半部分搜索
- findLast: 找到目標值後，left = mid + 1，繼續在右半部分搜索
4. 兩次搜索都完成後，如果目標值存在則返回 [first, last]，否則返回 [-1, -1]。
5. 時間複雜度：O(log n) - 執行兩次二分搜索
*/