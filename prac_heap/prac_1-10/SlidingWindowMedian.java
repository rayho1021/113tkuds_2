/*
題目 6：滑動視窗的中位數
題目描述：
給定一個陣列和視窗大小 K，計算每個滑動視窗的中位數。
- 使用兩個 heap：Max Heap（小的一半）和 Min Heap（大的一半）
- 維持兩個 heap 的大小平衡
- 處理視窗滑動時元素的添加和移除
- 考慮 K 為奇數和偶數的情況

測試案例：
陣列：[1,3,-1,-3,5,3,6,7], K=3
輸出：[1, -1, -1, 3, 5, 6]
解釋：
[1,3,-1] → 中位數 1
[3,-1,-3] → 中位數 -1
[-1,-3,5] → 中位數 -1
[-3,5,3] → 中位數
3[5,3,6] → 中位數
5[3,6,7] → 中位數 6

陣列：[1,2,3,4], K=2
輸出：[1.5, 2.5, 3.5]
*/

import java.util.*;

public class SlidingWindowMedian {

    private final PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    private final PriorityQueue<Integer> minHeap = new PriorityQueue<>();

    private final Map<Integer, Integer> lazyRemovalMap = new HashMap<>();

    public double[] medianSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return new double[0];
        }

        int n = nums.length;
        double[] result = new double[n - k + 1];

        // 初始化第一個視窗
        for (int i = 0; i < k; i++) {
            addNum(nums[i]);
        }
        result[0] = getMedian();

        // 滑動視窗
        for (int i = k; i < n; i++) {
            removeNum(nums[i - k]);
            addNum(nums[i]);

            result[i - k + 1] = getMedian();
        }

        return result;
    }

    private void addNum(int num) {
        if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
            maxHeap.offer(num);
        } else {
            minHeap.offer(num);
        }
        balanceHeaps();
    }

    private void removeNum(int num) {
        lazyRemovalMap.put(num, lazyRemovalMap.getOrDefault(num, 0) + 1);

        if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
            if (num == maxHeap.peek()) {
                cleanUpHeap(maxHeap);
            }
        } else {
            if (num == minHeap.peek()) {
                cleanUpHeap(minHeap);
            }
        }
        balanceHeaps();
    }

    private void balanceHeaps() {
        while (maxHeap.size() > minHeap.size() + 1) {
            minHeap.offer(maxHeap.poll());
            cleanUpHeap(maxHeap);
        }
        while (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
            cleanUpHeap(minHeap);
        }
    }

    private void cleanUpHeap(PriorityQueue<Integer> heap) {
        while (!heap.isEmpty() && lazyRemovalMap.containsKey(heap.peek())) {
            int num = heap.peek();
            lazyRemovalMap.put(num, lazyRemovalMap.get(num) - 1);
            if (lazyRemovalMap.get(num) == 0) {
                lazyRemovalMap.remove(num);
            }
            heap.poll();
        }
    }

    private double getMedian() {
        if (maxHeap.isEmpty()) {
            return 0.0;
        }

        if (maxHeap.size() > minHeap.size()) {
            return maxHeap.peek();
        } else {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }
    }

    public static void main(String[] args) {
        SlidingWindowMedian solution = new SlidingWindowMedian();

        int[] nums1 = {1, 3, -1, -3, 5, 3, 6, 7};
        int k1 = 3;
        double[] result1 = solution.medianSlidingWindow(nums1, k1);
        System.out.println("測試案例 1：");
        System.out.println("輸入陣列: " + Arrays.toString(nums1) + ", K: " + k1);
        System.out.print("輸出中位數: [");
        for (int i = 0; i < result1.length; i++) {
            if (result1[i] % 1 == 0) {
                System.out.print((int) result1[i]);
            } else {
                System.out.print(result1[i]);
            }
            if (i < result1.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");

        System.out.println("---");

        int[] nums2 = {1, 2, 3, 4};
        int k2 = 2;
        double[] result2 = solution.medianSlidingWindow(nums2, k2);
        System.out.println("測試案例 2：");
        System.out.println("輸入陣列: " + Arrays.toString(nums2) + ", K: " + k2);
        System.out.print("輸出中位數: [");
        for (int i = 0; i < result2.length; i++) {
            if (result2[i] % 1 == 0) {
                System.out.print((int) result2[i]);
            } else {
                System.out.print(result2[i]);
            }
            if (i < result2.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}