/*
練習 1.5：選擇排序實作
實作選擇排序演算法：
1. 實作基本的選擇排序
2. 顯示每一輪的排序過程
3. 計算比較次數和交換次數
4. 比較與氣泡排序的效能差異
*/

import java.util.Arrays;
import java.util.Random;

public class SelectionSortImplementation {
    
    /**
     * 排序操作統計資料類別
     */
    public static class SortStatistics {
        private int comparisons;
        private int swaps;
        private long executionTime;
        
        public SortStatistics() {
            this.comparisons = 0;
            this.swaps = 0;
            this.executionTime = 0;
        }
        
        public void incrementComparisons() { comparisons++; }
        public void incrementSwaps() { swaps++; }
        public void setExecutionTime(long time) { executionTime = time; }
        
        public int getComparisons() { return comparisons; }
        public int getSwaps() { return swaps; }
        public long getExecutionTime() { return executionTime; }
        
        @Override
        public String toString() {
            return String.format("比較次數: %d, 交換次數: %d, 執行時間: %d 毫秒", 
                               comparisons, swaps, executionTime);
        }
    }
    
    /**
     * 選擇排序實作，包含視覺化和統計功能
     */
    public static SortStatistics selectionSort(int[] array, boolean showProcess) {
        SortStatistics stats = new SortStatistics();
        long startTime = System.currentTimeMillis();
        
        if (showProcess) {
            System.out.println("\n=== 選擇排序過程 ===");
            System.out.println("初始陣列: " + Arrays.toString(array));
            System.out.println();
        }
        
        int n = array.length;
        
        // 逐一移動未排序子陣列的邊界
        for (int i = 0; i < n - 1; i++) {
            // 在未排序陣列中找到最小元素
            int minIndex = i;
            
            if (showProcess) {
                System.out.printf("第 %d 輪: 在範圍 [%d-%d] 中尋找最小值\n", 
                                i + 1, i, n - 1);
                printArrayWithHighlight(array, i, minIndex, -1, "搜尋中");
            }
            
            for (int j = i + 1; j < n; j++) {
                stats.incrementComparisons();
                
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                    
                    if (showProcess) {
                        printArrayWithHighlight(array, i, minIndex, j, 
                                              String.format("找到新的最小值: %d", array[minIndex]));
                    }
                }
            }
            
            // 將找到的最小元素與第一個元素交換
            if (minIndex != i) {
                stats.incrementSwaps();
                
                if (showProcess) {
                    System.out.printf("交換 %d (位置 %d) 與 %d (位置 %d)\n", 
                                    array[i], i, array[minIndex], minIndex);
                }
                
                // 執行交換
                int temp = array[minIndex];
                array[minIndex] = array[i];
                array[i] = temp;
            }
            
            if (showProcess) {
                printArrayWithHighlight(array, i + 1, -1, -1, "第 " + (i + 1) + " 輪完成");
                System.out.println("目前統計: " + stats);
                System.out.println();
            }
        }
        
        long endTime = System.currentTimeMillis();
        stats.setExecutionTime(endTime - startTime);
        
        if (showProcess) {
            System.out.println("最終排序陣列: " + Arrays.toString(array));
            System.out.println("=== 排序完成 ===\n");
        }
        
        return stats;
    }
    
    /**
     * 氣泡排序實作，用於比較
     */
    public static SortStatistics bubbleSort(int[] array) {
        SortStatistics stats = new SortStatistics();
        long startTime = System.currentTimeMillis();
        
        int n = array.length;
        boolean swapped;
        
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            
            for (int j = 0; j < n - i - 1; j++) {
                stats.incrementComparisons();
                
                if (array[j] > array[j + 1]) {
                    // 交換元素
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    
                    stats.incrementSwaps();
                    swapped = true;
                }
            }
            
            // 如果沒有發生交換，陣列已排序
            if (!swapped) {
                break;
            }
        }
        
        long endTime = System.currentTimeMillis();
        stats.setExecutionTime(endTime - startTime);
        
        return stats;
    }
    
    /**
     * 印出陣列並標示特定元素
     */
    public static void printArrayWithHighlight(int[] array, int sortedEnd, int minIndex, 
                                             int currentIndex, String description) {
        System.out.print("陣列: [");
        
        for (int i = 0; i < array.length; i++) {
            // 顏色標記用於視覺化
            if (i < sortedEnd) {
                System.out.print("(" + array[i] + ")"); // 已排序部分
            } else if (i == minIndex && minIndex != -1) {
                System.out.print("[" + array[i] + "]"); // 當前最小值
            } else if (i == currentIndex && currentIndex != -1) {
                System.out.print("<" + array[i] + ">"); // 正在比較
            } else {
                System.out.print(" " + array[i] + " "); // 未排序
            }
            
            if (i < array.length - 1) {
                System.out.print(", ");
            }
        }
        
        System.out.println("] - " + description);
        System.out.println("說明: (已排序) [最小值] <比較中> 未排序");
    }
    
    /**
     * 隨機陣列測試
     */
    public static int[] generateRandomArray(int size, int maxValue) {
        Random random = new Random();
        int[] array = new int[size];
        
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(maxValue) + 1;
        }
        
        return array;
    }
    
    /**
     * 比較選擇排序和氣泡排序的效能
     */
    public static void compareAlgorithms(int[] array) {
        System.out.println("=".repeat(60));
        System.out.println("           演算法效能比較");
        System.out.println("=".repeat(60));
        
        // 測試選擇排序
        int[] selectionArray = array.clone();
        System.out.println("測試選擇排序中...");
        SortStatistics selectionStats = selectionSort(selectionArray, false);
        
        // 測試氣泡排序
        int[] bubbleArray = array.clone();
        System.out.println("測試氣泡排序中...");
        SortStatistics bubbleStats = bubbleSort(bubbleArray);
        
        // 顯示比較結果
        System.out.println("\n" + "-".repeat(50));
        System.out.println("效能比較結果");
        System.out.println("-".repeat(50));
        System.out.printf("%-15s %-10s %-10s %-10s%n", "演算法", "比較次數", "交換次數", "時間(毫秒)");
        System.out.println("-".repeat(50));
        System.out.printf("%-15s %-10d %-10d %-10d%n", "選擇排序", 
                         selectionStats.getComparisons(), selectionStats.getSwaps(), 
                         selectionStats.getExecutionTime());
        System.out.printf("%-15s %-10d %-10d %-10d%n", "氣泡排序", 
                         bubbleStats.getComparisons(), bubbleStats.getSwaps(), 
                         bubbleStats.getExecutionTime());
        
        // 分析
        System.out.println("\n" + "-".repeat(50));
        System.out.println("分析:");
        System.out.println("-".repeat(50));
        
        // 比較次數分析
        if (selectionStats.getComparisons() > 0) {
            double comparisonRatio = (double) bubbleStats.getComparisons() / selectionStats.getComparisons();
            System.out.printf("- 比較次數: 選擇排序進行了 %d 次比較\n", 
                             selectionStats.getComparisons());
            System.out.printf("  氣泡排序進行了 %d 次比較 (%.2f倍)\n", 
                             bubbleStats.getComparisons(), comparisonRatio);
        }
        
        // 交換次數分析
        if (selectionStats.getSwaps() > 0) {
            double swapRatio = (double) bubbleStats.getSwaps() / selectionStats.getSwaps();
            System.out.printf("- 交換次數: 選擇排序進行了 %d 次交換\n", 
                             selectionStats.getSwaps());
            System.out.printf("  氣泡排序進行了 %d 次交換 (%.2f倍)\n", 
                             bubbleStats.getSwaps(), swapRatio);
        } else {
            System.out.printf("- 交換次數: 選擇排序進行了 %d 次交換\n", 
                             selectionStats.getSwaps());
            System.out.printf("  氣泡排序進行了 %d 次交換\n", bubbleStats.getSwaps());
        }
        
        // 時間分析
        if (selectionStats.getExecutionTime() > 0 && bubbleStats.getExecutionTime() > 0) {
            double timeRatio = (double) bubbleStats.getExecutionTime() / selectionStats.getExecutionTime();
            System.out.printf("• 執行時間: 選擇排序花費 %d 毫秒\n", 
                             selectionStats.getExecutionTime());
            System.out.printf("  氣泡排序花費 %d 毫秒 (%.2f倍時間)\n", 
                             bubbleStats.getExecutionTime(), timeRatio);
        }
        
        // 結論
        System.out.println("\n 結論:");
        System.out.println("- 選擇排序通常比氣泡排序進行較少的交換操作");
        System.out.println("- 兩者在比較次數上都有 O(n²) 時間複雜度");
        System.out.println("- 當交換操作成本很高時，選擇排序更有效率");
        System.out.println("- 氣泡排序可以針對部分排序的陣列進行最佳化");
    }
    
    /**
     * 示範選擇排序的逐步視覺化
     */
    public static void demonstrateSelectionSort() {
        System.out.println("=".repeat(60));
        System.out.println("         選擇排序逐步示範");
        System.out.println("=".repeat(60));
        
        // 用小陣列進行清楚的視覺化
        int[] demoArray = {64, 25, 12, 22, 11};
        System.out.println("示範陣列: " + Arrays.toString(demoArray));
        
        SortStatistics stats = selectionSort(demoArray, true);
        
        System.out.println("最終統計: " + stats);
        System.out.println("\n選擇排序特性:");
        System.out.println("- 時間複雜度: 所有情況下都是 O(n²)");
        System.out.println("- 空間複雜度: O(1) - 原地排序");
        System.out.println("- 穩定性: 不穩定 (可能改變相等元素的相對順序)");
        System.out.println("- 最適用於: 小陣列或交換成本很高的情況");
    }
    
    /**
     * 主程式 - 示範選擇排序實作
     */
    public static void main(String[] args) {
        System.out.println("=".repeat(70));
        System.out.println("                  選擇排序實作");
        System.out.println("=".repeat(70));
        
        try {
            // 1. 逐步示範
            demonstrateSelectionSort();
            
            // 2. 較大陣列的效能比較
            System.out.println("\n" + "=".repeat(60));
            System.out.println("           較大陣列的效能測試");
            System.out.println("=".repeat(60));
            
            int[] testArray = generateRandomArray(15, 100);
            System.out.println("原始陣列: " + Arrays.toString(testArray));
            
            compareAlgorithms(testArray);
            
            // 3. 測試不同類型的陣列
            System.out.println("\n" + "=".repeat(60));
            System.out.println("              測試不同類型的陣列");
            System.out.println("=".repeat(60));
            
            // 已排序陣列
            int[] sortedArray = {1, 2, 3, 4, 5, 6, 7, 8};
            System.out.println("\n測試已排序陣列:");
            System.out.println("陣列: " + Arrays.toString(sortedArray.clone()));
            compareAlgorithms(sortedArray);
            
            // 反向排序陣列
            int[] reverseArray = {8, 7, 6, 5, 4, 3, 2, 1};
            System.out.println("\n測試反向排序陣列:");
            System.out.println("陣列: " + Arrays.toString(reverseArray.clone()));
            compareAlgorithms(reverseArray);
            
        } catch (Exception e) {
            System.err.println("發生錯誤: " + e.getMessage());
        }
    }
}