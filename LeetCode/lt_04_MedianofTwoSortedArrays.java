/*
題目：Median of Two Sorted Arrays
給定兩個已排序的陣列 nums1 和 nums2，長度分別為 m 和 n，回傳兩個陣列的中位數。
時間複雜度要求為 O(log(m+n))。
範例：nums1 = [1,2], nums2 = [3,4] → Output: 2.50000 (合併後 [1,2,3,4]，中位數是 (2+3)/2 = 2.5)
*/

class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // 確保 nums1 是較短的陣列，減少搜尋空間
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }
        
        int m = nums1.length; // 較短陣列的長度
        int n = nums2.length; // 較長陣列的長度
        int low = 0, high = m; // 在較短陣列上進行二分搜尋的範圍
        
        while (low <= high) {
            int cut1 = (low + high) / 2; // nums1 的分割點
            int cut2 = (m + n + 1) / 2 - cut1; // nums2 的分割點，確保左半部元素總數正確
            
            // 取得分割點左邊的最大值，處理邊界情況
            int left1 = (cut1 == 0) ? Integer.MIN_VALUE : nums1[cut1 - 1];
            int left2 = (cut2 == 0) ? Integer.MIN_VALUE : nums2[cut2 - 1];
            
            // 取得分割點右邊的最小值，處理邊界情況
            int right1 = (cut1 == m) ? Integer.MAX_VALUE : nums1[cut1];
            int right2 = (cut2 == n) ? Integer.MAX_VALUE : nums2[cut2];
            
            // 檢查分割是否正確：左半最大值 <= 右半最小值
            if (left1 <= right2 && left2 <= right1) {
                // 找到正確分割點，計算中位數，若總長度為偶數，取中間兩個數的平均值；若為奇數，取左半部的最大值
                if ((m + n) % 2 == 0) {
                    return (Math.max(left1, left2) + Math.min(right1, right2)) / 2.0;
                } else {
                    return Math.max(left1, left2);
                }
            } else if (left1 > right2) {
                // nums1 分割點太右，需要向左移動；分割點太左，需要向右移動
                high = cut1 - 1;
            } else {
                low = cut1 + 1;
            }
        }
        
        return 1.0; // 理論上不會執行到這裡
    }
}

/*
解題思路：
1. 使用二分搜尋法在較短的陣列上尋找最佳分割點，時間複雜度 O(log(min(m,n)))。
2. 將兩個陣列分割成左右兩半，滿足左半部所有元素 <= 右半部所有元素。
3. 分割條件：left1 <= right2 且 left2 <= right1，確保分割後左右兩部分有序。
4. 分割點的選擇：cut1 + cut2 = (m + n + 1) / 2，確保左半部元素數量正確。
5. 中位數計算：總長度為偶數時取中間兩數平均值，奇數時取左半部最大值。
6. 二分搜尋調整：若 left1 > right2 則 cut1 太大需左移，否則需右移。
7. 邊界處理：使用 MIN_VALUE 和 MAX_VALUE 處理分割點在陣列邊界的情況。
*/