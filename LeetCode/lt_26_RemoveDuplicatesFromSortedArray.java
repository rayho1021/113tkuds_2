/*
題目：Remove Duplicates from Sorted Array
1.從已排序的陣列中原地移除重複元素，使每個唯一元素只出現一次。
2.保持元素的相對順序，回傳唯一元素的數量。
範例：nums = [0,0,1,1,1,2,2,3,3,4] → 5, nums = [0,1,2,3,4,_,_,_,_,_]
*/

class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) return 0; // 邊界
        int slow = 0; // 慢指標，指向下一個唯一元素應該放置的位置
        
        // 快指標，從第二個元素開始遍歷陣列
        for (int fast = 1; fast < nums.length; fast++) {
            // 如果 fast 指向的元素與 slow 指向的元素不同
            if (nums[fast] != nums[slow]) {
                slow++; // slow 向前移動一位
                nums[slow] = nums[fast]; // 將 fast 的元素複製到 slow 位置
            }
        } //如果相同，fast 繼續移動，slow 保持不動（跳過重複元素）
    
        return slow + 1; // 回傳唯一元素的數量（slow 是索引，所以要加 1）
    }
}

/*
解題思路：
1. 使用雙指標，slow 和 fast。
2. slow 指標：已處理的唯一元素區域，指向下一個唯一元素的放置位置。
3. fast 指標：遍歷整個陣列，尋找與當前唯一元素不同的新元素。
4. 邏輯：
  - 當 nums[fast] != nums[slow] 時，發現新的唯一元素
  - 將 slow 前移並複製 fast 位置的元素到 slow 位置
  - 當 nums[fast] == nums[slow] 時，跳過重複元素
5. 重複元素必定相鄰，只需比較相鄰元素即可判斷。
6. 通過覆寫陣列前部分實現，不需要額外空間。
7. 返回 slow + 1 表示唯一元素的數量，前 k 個位置包含所有唯一元素。
*/