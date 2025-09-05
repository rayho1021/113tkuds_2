/*
題目: 旋轉陣列搜尋
1.在一個「升序後被旋轉(拼接)」的整數陣列中(設備 ID 清單)，搜尋目標值 target 並回傳，找不到回 -1。
2.解法核心：每次二分中點，判斷左或右區是否有序，並決定要往哪一半縮小。
3.限制：
* 1 <= n <= 1e5
* 陣列原為升序後旋轉
* -1e9 <= 值,target <= 1e9
4.邊界：
- 陣列未旋轉（純升序）
- 陣列僅一個元素（匹配 / 不匹配）
- target 在旋轉分界附近
- target 不存在
5.複雜度：O(log n)
6.
輸入：
- n target
- n 個整數（旋轉後的陣列）
輸出：一個整數（索引或 -1）。
*/

import java.util.*;

public class LC33_SearchRotated_RentHot {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int target = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }
        sc.close();
        System.out.println(search(nums, target));
    }

    
    // 二分搜尋：每次判斷中點在哪一半有序，進一步縮小搜尋範圍
    private static int search(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            // 判斷左半是否有序
            if (nums[l] <= nums[mid]) {
                // target 是否落在左半
                if (nums[l] <= target && target < nums[mid]) {
                    r = mid - 1;
                } 
                else {
                    l = mid + 1;
                }
            } 
            else {
                // 右半有序
                if (nums[mid] < target && target <= nums[r]) {
                    l = mid + 1;
                } 
                else {
                    r = mid - 1;
                }
            }
        }
        return -1; // 找不到
    }
}


/*
解題思路 : 
1.左右指標 l=0、r=n-1。
2.取中點：檢查 nums[mid] == target → 直接回傳。
3.判斷哪邊有序：
(1) 若 nums[l] <= nums[mid]，左半區有序。
    - 檢查 target 是否在 [nums[l], nums[mid]) → 縮小至左半，否則往右半。
(2) 否則右半有序。
    - 檢查 target 是否在 (nums[mid], nums[r]] → 縮小至右半，否則往左半。
4.迴圈結束：若沒找到，回傳 -1。
5.旋轉陣列至少有一半仍然有序，每次二分都能排除一半區間。
*/