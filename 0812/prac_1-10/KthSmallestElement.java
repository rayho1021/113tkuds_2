/*
題目 4：找到陣列中第 K 小的元素
題目描述：
給定一個未排序的陣列，找到第 K 小的元素。要求使用 Heap 解決，並比較不同方法的效率。
* 方法1：使用大小為 K 的 Max Heap
* 方法2：使用 Min Heap 然後提取 K 次
* 比較兩種方法的時間和空間複雜度

測試案例：
陣列：[7, 10, 4, 3, 20, 15], K=3
答案：7 (排序後為 [3, 4, 7, 10, 15, 20])

陣列：[1], K=1
答案：1

陣列：[3, 1, 4, 1, 5, 9, 2, 6], K=4
答案：3

*/

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class KthSmallestElement {
    
    /**
     * 方法1：使用大小為 K 的 Max Heap
     */
    public static int findKthSmallestWithMaxHeap(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            throw new IllegalArgumentException("Invalid input");
        }
        
        // 使用 Max Heap（大的元素在頂部）
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        
        for (int num : nums) {
            if (maxHeap.size() < k) {
                // heap 未滿，直接加入
                maxHeap.offer(num);
            } else if (num < maxHeap.peek()) {
                // 如果當前元素比 heap 頂部小，替換頂部元素
                maxHeap.poll();
                maxHeap.offer(num);
            }
        }
        
        return maxHeap.peek();
    }
    
    /**
     * 方法2：使用 Min Heap 然後提取 K 次
     */
    public static int findKthSmallestWithMinHeap(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            throw new IllegalArgumentException("Invalid input");
        }
        
        // 使用 Min Heap（小的元素在頂部）
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        // 將所有元素加入 Min Heap
        for (int num : nums) {
            minHeap.offer(num);
        }
        
        // 提取 k 次，第 k 次就是答案
        int result = 0;
        for (int i = 0; i < k; i++) {
            result = minHeap.poll();
        }
        
        return result;
    }
    
    /**
     * 測試兩種方法並比較結果
     */
    public static void testBothMethods(int[] nums, int k) {
        System.out.print("陣列：" + Arrays.toString(nums) + ", K=" + k);
        
        try {
            long startTime, endTime;
            
            // 測試方法1
            startTime = System.nanoTime();
            int result1 = findKthSmallestWithMaxHeap(nums.clone(), k);
            endTime = System.nanoTime();
            long time1 = endTime - startTime;
            
            // 測試方法2
            startTime = System.nanoTime();
            int result2 = findKthSmallestWithMinHeap(nums.clone(), k);
            endTime = System.nanoTime();
            long time2 = endTime - startTime;
            
            System.out.println();
            System.out.println("方法1 (Max Heap): " + result1 + " (時間: " + time1 + " ns)");
            System.out.println("方法2 (Min Heap): " + result2 + " (時間: " + time2 + " ns)");
            
            // 驗證結果是否一致
            if (result1 == result2) {
                System.out.println("答案: " + result1);
            } else {
                System.out.println("結果不一致！");
            }
            
        } catch (IllegalArgumentException e) {
            System.out.println("錯誤: " + e.getMessage());
        }
    }
    
    /**
     * 顯示排序後的陣列
     */
    public static void showSortedArray(int[] nums) {
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        System.out.println("排序後: " + Arrays.toString(sorted));
    }
    
    public static void main(String[] args) {
        System.out.println("=== 第 K 小元素查找測試 ===\n");
        
        // 測試案例
        int[][] testArrays = {
            {7, 10, 4, 3, 20, 15},
            {1},
            {3, 1, 4, 1, 5, 9, 2, 6}
        };
        
        int[] testK = {3, 1, 4};
        
        for (int i = 0; i < testArrays.length; i++) {
            System.out.println("測試案例 " + (i + 1) + ":");
            testBothMethods(testArrays[i], testK[i]);
            showSortedArray(testArrays[i]);
            System.out.println();
        }
        
        // 額外測試：邊界情況
        System.out.println("邊界情況測試：");
        
        // 測試 K=1（最小值）
        int[] arr1 = {5, 2, 8, 1, 9};
        testBothMethods(arr1, 1);
        
        // 測試 K=length（最大值）
        int[] arr2 = {3, 7, 1, 4};
        testBothMethods(arr2, arr2.length);
        
        // 測試重複元素
        int[] arr3 = {4, 4, 4, 4};
        testBothMethods(arr3, 2);
        
        // 測試無效輸入
        System.out.println("\n無效輸入測試：");
        testBothMethods(new int[]{1, 2, 3}, 0);  // K=0
        testBothMethods(new int[]{1, 2, 3}, 5);  // K > length
        
        // 效率比較總結
        System.out.println("\n=== 方法比較總結 ===");
        System.out.println("方法1 (Max Heap 大小為 K):");
        System.out.println("  - 時間複雜度: O(n log k)");
        System.out.println("  - 空間複雜度: O(k)");
        System.out.println("  - 適用: K 較小時效率高");
        
        System.out.println("\n方法2 (Min Heap + K 次提取):");
        System.out.println("  - 時間複雜度: O(n + k log n)");
        System.out.println("  - 空間複雜度: O(n)");
        System.out.println("  - 適用: 實作簡單，但空間使用多");
        
        System.out.println("\n=== 測試完成 ===");
    }
}