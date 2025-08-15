/*
題目 5：合併多個有序陣列
題目描述：
給定 K 個有序陣列，將它們合併成一個有序陣列。
* 使用 Min Heap 儲存每個陣列的當前最小元素
* 每次取出 heap 的最小值，然後從該陣列取下一個元素加入 heap
* 注意處理陣列用完的情況
* 可以用一個類別包裝 (值, 陣列索引, 元素索引)

測試案例：
輸入：[[1,4,5], [1,3,4], [2,6]]
輸出：[1,1,2,3,4,4,5,6]

輸入：[[1,2,3], [4,5,6], [7,8,9]]
輸出：[1,2,3,4,5,6,7,8,9]

輸入：[[1], [0]]
輸出：[0,1]
*/

import java.util.Arrays;
import java.util.PriorityQueue;

public class MergeKSortedArrays {
    /**
     * 記錄 陣列元素值、來源陣列索引和在該陣列中的位置
     */
    static class ArrayElement {
        int value;        // 元素值
        int arrayIndex;   // 來自哪個陣列
        int elementIndex; // 在該陣列中的位置
        
        public ArrayElement(int value, int arrayIndex, int elementIndex) {
            this.value = value;
            this.arrayIndex = arrayIndex;
            this.elementIndex = elementIndex;
        }
        
        @Override
        public String toString() {
            return "值:" + value + "(陣列" + arrayIndex + "[" + elementIndex + "])";
        }
    }
    
    /**
     * 合併 K 個有序陣列
     */
    public static int[] mergeKSortedArrays(int[][] arrays) {
        if (arrays == null || arrays.length == 0) {
            return new int[0];
        }
        
        // 使用 Min Heap，按元素值排序
        PriorityQueue<ArrayElement> minHeap = new PriorityQueue<>(
            (a, b) -> Integer.compare(a.value, b.value)
        );
        
        // 計算總元素數量
        int totalElements = 0;
        
        // 將每個陣列的第一個元素加入 heap
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i] != null && arrays[i].length > 0) {
                minHeap.offer(new ArrayElement(arrays[i][0], i, 0));
                totalElements += arrays[i].length;
            }
        }
        
        // 結果陣列
        int[] result = new int[totalElements];
        int resultIndex = 0;
        
        // 合併過程
        while (!minHeap.isEmpty()) {
            // 取出最小元素
            ArrayElement current = minHeap.poll();
            result[resultIndex++] = current.value;
            
            // 從該陣列取下一個元素加入 heap
            int nextElementIndex = current.elementIndex + 1;
            if (nextElementIndex < arrays[current.arrayIndex].length) {
                int nextValue = arrays[current.arrayIndex][nextElementIndex];
                minHeap.offer(new ArrayElement(nextValue, current.arrayIndex, nextElementIndex));
            }
        }
        
        return result;
    }
    
    /**
     * 列印二維陣列
     */
    public static void printArrays(int[][] arrays) {
        System.out.print("[");
        for (int i = 0; i < arrays.length; i++) {
            System.out.print(Arrays.toString(arrays[i]));
            if (i < arrays.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.print("]");
    }
    
    /**
     * 測試方法
     */
    public static void testMerge(int[][] arrays) {
        System.out.print("輸入：");
        printArrays(arrays);
        
        int[] result = mergeKSortedArrays(arrays);
        
        System.out.println();
        System.out.println("輸出：" + Arrays.toString(result));
        System.out.println();
    }
    
    public static void main(String[] args) {
        System.out.println("=== 合併多個有序陣列測試 ===\n");
        
        // 測試案例 1
        System.out.println("測試案例 1：");
        int[][] arrays1 = {{1, 4, 5}, {1, 3, 4}, {2, 6}};
        testMerge(arrays1);
        
        // 測試案例 2
        System.out.println("測試案例 2：");
        int[][] arrays2 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        testMerge(arrays2);
        
        // 測試案例 3
        System.out.println("測試案例 3：");
        int[][] arrays3 = {{1}, {0}};
        testMerge(arrays3);
        
        // 額外測試案例
        System.out.println("額外測試：");
        
        // 空陣列
        System.out.println("空陣列測試：");
        int[][] emptyArrays = {};
        testMerge(emptyArrays);
        
        // 單一陣列
        System.out.println("單一陣列測試：");
        int[][] singleArray = {{1, 3, 5, 7, 9}};
        testMerge(singleArray);
        
        // 包含空陣列
        System.out.println("包含空陣列測試：");
        int[][] arraysWithEmpty = {{1, 4}, {}, {2, 3, 5}};
        testMerge(arraysWithEmpty);
        
        // 不同長度陣列
        System.out.println("不同長度陣列測試：");
        int[][] differentLengths = {{1, 5, 9, 13}, {2}, {3, 6, 7, 8, 10, 11, 12}};
        testMerge(differentLengths);
        
        // 重複元素
        System.out.println("重複元素測試：");
        int[][] duplicates = {{1, 1, 2}, {1, 2, 3}, {2, 2, 3}};
        testMerge(duplicates);
        
        // 負數測試
        System.out.println("負數測試：");
        int[][] negatives = {{-3, -1, 2}, {-2, 0, 4}, {-1, 1, 3}};
        testMerge(negatives);
        
        System.out.println("=== 測試完成 ===");
        
        // 複雜度分析
        System.out.println("\n=== 複雜度分析 ===");
        System.out.println("時間複雜度：O(N log K)");
        System.out.println("  - N：所有元素總數");
        System.out.println("  - K：陣列數量");
        System.out.println("  - 每個元素需要進出 heap 一次，heap 操作為 O(log K)");
        
        System.out.println("\n空間複雜度：O(K + N)");
        System.out.println("  - K：heap 最多存放 K 個元素");
        System.out.println("  - N：結果陣列需要 N 個空間");
    }
}