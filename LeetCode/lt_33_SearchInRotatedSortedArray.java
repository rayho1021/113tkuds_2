/*
題目：Search in Rotated Sorted Array
給定一個可能在某個未知索引處旋轉的已排序數組，在其中搜索目標值
要求時間複雜度為 O(log n)
範例: Input: nums = [4,5,6,7,0,1,2], target = 3 --> Output: -1
*/

class Solution {
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        
        int left = 0;
        int right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // 找到目標值
            if (nums[mid] == target) {
                return mid;
            }
            
            // 判斷左半部分是否有序
            if (nums[left] <= nums[mid]) {
                // 左半部分有序
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1; // 目標值在左半部分的有序區間內
                } 
                else {
                    left = mid + 1; // 目標值不在左半部分，搜索右半部分
                }
            } 
            else {
                // 右半部分有序
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1; // 目標值在右半部分的有序區間內
                } else {
                    right = mid - 1; // 目標值不在右半部分，搜索左半部分
                }
            }
        }   
        return -1; // 未找到目標值
    }
}

/*
解題思路：
1. 旋轉排序數組：雖然整個數組不是完全有序的，但可以分為兩個有序的部分。
2. 使用修改後的二分搜索：在每次二分時，至少有一半是完全有序的。
3. 判斷：通過比較 nums[left] 和 nums[mid] 來確定哪一半是有序的
   - 如果 nums[left] <= nums[mid]，說明左半部分有序
   - 否則右半部分有序
4. 在確定有序的一半中，判斷目標值是否在該範圍內：
   - 如果在，則縮小搜索範圍到該有序部分
   - 如果不在，則搜索另一半
5. 重複此過程直到找到目標值或搜索範圍為空。
6. 時間複雜度：O(log n) - 每次將搜索範圍縮小一半
*/