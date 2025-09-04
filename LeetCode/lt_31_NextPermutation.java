/*
題目：Next Permutation
給定一個整數陣列，找到其下一個字典序更大的排列
如果不存在下一個更大的排列，則重新排列為最小的排列（升序排列）
要求：in-place 操作，只能使用常數額外空間
範例: Input: nums = [1,1,5] --> Output: [1,5,1]
*/

class Solution {
    // 找到數組的下一個排列
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return; // 輸入的整數數組，空數組或單元素數組無需處理
        }
        
        int n = nums.length;
        
        // 從右往左找到第一個遞減的位置，nums[i] < nums[i+1] 的最大索引 i
        int pivot = -1;
        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] < nums[i + 1]) {
                pivot = i;
                break; // 找到第一個遞減位置就停止
            }
        }
        
        // 如果沒找到 pivot，表示整個數組是遞減的，這是最大的排列，需要重排為最小排列
        if (pivot == -1) {
            reverse(nums, 0, n - 1);
            return;
        }
        
        // 從右往左找到第一個大於 nums[pivot] 的數字，此數字是右邊中最小的、但比 pivot 位置數字大的數
        int successor = -1;
        for (int i = n - 1; i > pivot; i--) {
            if (nums[i] > nums[pivot]) {
                successor = i;
                break; // 找到就停止，右邊是遞減的，第一個找到的就是最小
            }
        }
        
        // 交換 pivot 和 successor 位置的數字
        swap(nums, pivot, successor);
        
        // 將 pivot 右邊的所有數字反轉，得到最小的字典序排列
        // pivot 右邊的部分在交換前後都保持遞減，反轉後成為遞增（最小排列）
        reverse(nums, pivot + 1, n - 1);
    }
    
    
    // 交換數組中兩個位置的元素 
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    // 反轉數組中指定範圍的元素   
    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            swap(nums, start, end);
            start++;
            end--;
        }
    }   
}

/*
解題思路：
1. 找到合適的交換點和交換對象。
2. 從右往左找第一個遞減位置 (pivot)，找到需要"增大"的位置。
3. 在 pivot 右邊找最小的、但比 pivot 大的數字進行交換。
4. 交換後，將 pivot 右邊的數字反轉為遞增序列，這樣得到的是下一個最小的排列。
5. 如果整個數組是遞減的（已是最大排列），則反轉整個數組得到最小排列。
*/