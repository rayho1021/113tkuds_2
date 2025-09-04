/* 
題目：Remove Element
從整數陣列中原地移除所有等於 val 的元素，回傳不等於 val 的元素數量。
元素順序可以改變，只需要前 k 個位置包含所有不等於 val 的元素。
範例：nums = [0,1,2,2,3,0,4,2], val = 2 → 5, nums = [0,1,4,0,3,_,_,_]
*/

class Solution {
    public int removeElement(int[] nums, int val) {
        if (nums == null || nums.length == 0) return 0; // 邊界
        int slow = 0; // slow 指標：指向下一個非 val 元素應該放置的位置
        
        for (int fast = 0; fast < nums.length; fast++) {  // fast 指標遍歷整個陣列
            // 如果 fast 指向的元素不等於 val
            if (nums[fast] != val) {
                nums[slow] = nums[fast]; // 將非 val 元素複製到 slow 位置
                slow++; // slow 向前移動
            }
            // 如果等於 val，fast 繼續移動，slow 保持不動（跳過該元素）
        }
        
        return slow; // 回傳非 val 元素的數量
    }
}

/*
解題思路：
1. 使用雙指 slow 和 fast。
2. slow 指標：已處理的非 val 元素區域，指向下一個非 val 元素的放置位置。
3. fast 指標：遍歷整個陣列，尋找所有不等於 val 的元素。
4. 邏輯：
  - 當 nums[fast] != val 時，將元素複製到 slow 位置並移動 slow
  - 當 nums[fast] == val 時，跳過該元素，只移動 fast
5. 通過覆寫陣列前部分實現，不需要額外空間。
6. 由於只要求前 k 個位置包含非 val 元素，順序可以改變。
7. 返回 slow 表示非 val 元素的數量，前 k 個位置包含所有非 val 元素。
*/