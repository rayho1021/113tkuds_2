/*
題目: 延誤等級首末定位
1.在已排序的整數陣列中，找出指定 target 的首個與最後一個索引。若不存在，輸出 -1 -1。
2.解法核心：使用二分搜尋（lower_bound / upper_bound），鎖定左右界。
3.限制：
* 0 <= n <= 1e5
* 已排序（非遞減）    
* -1e9 <= 值, target <= 1e9
4.邊界：
- n=0 → -1 -1
- 所有元素皆為 target
- target 只出現一次（位於頭或尾）
- target 不存在但落在範圍中
5.複雜度：O(log n)
6.
輸入：
- n target
- n 個整數（非遞減排序）
輸出：兩個索引（首索引、尾索引；若不存在輸出 -1 -1）。
*/

import java.util.*;

public class LC34_SearchRange_DelaySpan {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.hasNextInt() ? sc.nextInt() : 0;
        int target = sc.hasNextInt() ? sc.nextInt() : 0;
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }
        sc.close();

        int[] res = searchRange(nums, target);
        System.out.println(res[0] + " " + res[1]);
    }

    // 找 target 的首尾索引
    private static int[] searchRange(int[] nums, int target) {
        int n = nums.length;
        if (n == 0) return new int[]{-1, -1};

        int left = lowerBound(nums, target);
        // 檢查是否存在
        if (left == n || nums[left] != target) {
            return new int[]{-1, -1};
        }
        int right = lowerBound(nums, target + 1) - 1;
        return new int[]{left, right};
    }

    // lower_bound: 回傳第一個 >= target 的索引
    private static int lowerBound(int[] nums, int target) {
        int l = 0, r = nums.length;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] < target) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        return l;
    }
}


/*
解題思路 : 
1.二分搜尋 (lower_bound)：
- 找第一個大於等於 target 的位置 left。
- 若 left == n 或 nums[left] != target → target 不存在，回 -1 -1。
2.找右界：
- 找第一個大於等於 target+1 的位置，然後往左退一步，即為 right。
3.輸出：left right。
4.因為陣列已排序，兩次二分可精確鎖定區間。
*/