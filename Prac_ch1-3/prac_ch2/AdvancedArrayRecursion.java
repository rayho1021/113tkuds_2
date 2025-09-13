/*
練習 2.2 陣列遞迴操作進階
實作以下遞迴陣列操作：
1. 遞迴實作快速排序演算法
2. 遞迴合併兩個已排序的陣列
3. 遞迴尋找陣列中的第 k 小元素
4. 遞迴檢查陣列是否存在子序列總和等於目標值
 */

import java.util.*;

public class AdvancedArrayRecursion {
    
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();
    
    // 用於追蹤遞迴層數的靜態變數
    private static int recursionDepth = 0;
    private static boolean showSteps = false;
    
    /**
     * 快速排序演算法 - 遞迴實作
     * 使用分治法：選擇pivot → 分割 → 遞迴排序 → 合併
     */
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            if (showSteps) {
                printIndent(recursionDepth);
                System.out.printf("排序範圍 [%d, %d]: %s\n", 
                    low, high, arrayToString(arr, low, high));
            }
            
            recursionDepth++;
            
            // 找到分割點
            int pivotIndex = partition(arr, low, high);
            
            if (showSteps) {
                printIndent(recursionDepth - 1);
                System.out.printf("Pivot位置: %d (值: %d)\n", 
                    pivotIndex, arr[pivotIndex]);
            }
            
            // 遞迴排序左半部
            quickSort(arr, low, pivotIndex - 1);
            
            // 遞迴排序右半部
            quickSort(arr, pivotIndex + 1, high);
            
            recursionDepth--;
            
            if (showSteps) {
                printIndent(recursionDepth);
                System.out.printf("完成範圍 [%d, %d]: %s\n", 
                    low, high, arrayToString(arr, low, high));
            }
        }
    }
    
    /**
     * 快速排序的分割函式
     * 將陣列分為小於pivot和大於pivot兩部分
     */
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; // 選擇最後一個元素作為pivot
        int i = low - 1; // 小於pivot的元素索引
        
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        
        swap(arr, i + 1, high);
        return i + 1;
    }
    
    /**
     * 遞迴合併兩個已排序的陣列
     */
    public static int[] mergeArrays(int[] arr1, int[] arr2) {
        return mergeArraysHelper(arr1, 0, arr2, 0, new int[arr1.length + arr2.length], 0);
    }
    
    /**
     * 合併陣列的遞迴輔助函式
     */
    private static int[] mergeArraysHelper(int[] arr1, int idx1, int[] arr2, int idx2, 
                                          int[] result, int resultIdx) {
        // 基底條件：其中一個陣列已處理完
        if (idx1 >= arr1.length) {
            // arr1處理完，複製arr2剩餘元素
            if (idx2 < arr2.length) {
                System.arraycopy(arr2, idx2, result, resultIdx, arr2.length - idx2);
            }
            return result;
        }
        
        if (idx2 >= arr2.length) {
            // arr2處理完，複製arr1剩餘元素
            System.arraycopy(arr1, idx1, result, resultIdx, arr1.length - idx1);
            return result;
        }
        
        if (showSteps) {
            printIndent(recursionDepth);
            System.out.printf("比較 arr1[%d]=%d vs arr2[%d]=%d\n", 
                idx1, arr1[idx1], idx2, arr2[idx2]);
        }
        
        recursionDepth++;
        
        // 選擇較小的元素加入結果陣列
        if (arr1[idx1] <= arr2[idx2]) {
            result[resultIdx] = arr1[idx1];
            if (showSteps) {
                printIndent(recursionDepth - 1);
                System.out.printf("選擇 arr1[%d]=%d\n", idx1, arr1[idx1]);
            }
            mergeArraysHelper(arr1, idx1 + 1, arr2, idx2, result, resultIdx + 1);
        } else {
            result[resultIdx] = arr2[idx2];
            if (showSteps) {
                printIndent(recursionDepth - 1);
                System.out.printf("選擇 arr2[%d]=%d\n", idx2, arr2[idx2]);
            }
            mergeArraysHelper(arr1, idx1, arr2, idx2 + 1, result, resultIdx + 1);
        }
        
        recursionDepth--;
        return result;
    }
    
    /**
     * 遞迴尋找陣列中的第k小元素
     * 使用快速選擇演算法（Quick Select）
     */
    public static int findKthSmallest(int[] arr, int k) {
        if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("k 必須在 1 到 " + arr.length + " 之間");
        }
        
        // 建立陣列副本避免修改原陣列
        int[] copy = arr.clone();
        return findKthSmallestHelper(copy, 0, copy.length - 1, k - 1); // 轉為0-based
    }
    
    /**
     * 第k小元素查找的遞迴輔助函式
     */
    private static int findKthSmallestHelper(int[] arr, int low, int high, int k) {
        if (low == high) {
            if (showSteps) {
                printIndent(recursionDepth);
                System.out.printf("找到第%d小元素: %d\n", k + 1, arr[low]);
            }
            return arr[low];
        }
        
        if (showSteps) {
            printIndent(recursionDepth);
            System.out.printf("在範圍 [%d, %d] 中尋找第%d小元素\n", 
                low, high, k + 1);
        }
        
        recursionDepth++;
        
        // 分割陣列
        int pivotIndex = partition(arr, low, high);
        
        if (showSteps) {
            printIndent(recursionDepth - 1);
            System.out.printf("Pivot位置: %d，左邊有 %d 個元素\n", 
                pivotIndex, pivotIndex - low);
        }
        
        int result;
        if (k == pivotIndex) {
            // 找到第k小元素
            result = arr[pivotIndex];
            if (showSteps) {
                printIndent(recursionDepth - 1);
                System.out.printf("Pivot就是第%d小元素: %d\n", k + 1, result);
            }
        } else if (k < pivotIndex) {
            // 第k小元素在左半部
            if (showSteps) {
                printIndent(recursionDepth - 1);
                System.out.println("往左半部尋找");
            }
            result = findKthSmallestHelper(arr, low, pivotIndex - 1, k);
        } else {
            // 第k小元素在右半部
            if (showSteps) {
                printIndent(recursionDepth - 1);
                System.out.println("往右半部尋找");
            }
            result = findKthSmallestHelper(arr, pivotIndex + 1, high, k);
        }
        
        recursionDepth--;
        return result;
    }
    
    /**
     * 遞迴檢查陣列是否存在子序列總和等於目標值
     * 使用動態規劃的遞迴實作
     */
    public static boolean hasSubsetSum(int[] arr, int target) {
        List<Integer> currentSubset = new ArrayList<>();
        return hasSubsetSumHelper(arr, 0, target, currentSubset);
    }
    
    /**
     * 子序列總和檢查的遞迴輔助函式
     */
    private static boolean hasSubsetSumHelper(int[] arr, int index, int remainingSum, List<Integer> currentSubset) {
        if (showSteps) {
            printIndent(recursionDepth);
            System.out.printf("索引 %d，剩餘總和 %d，目前子集: %s\n", 
                index, remainingSum, currentSubset);
        }
        
        // 基底條件：剩餘總和為0，找到解答
        if (remainingSum == 0) {
            if (showSteps) {
                printIndent(recursionDepth);
                System.out.printf("找到解答! 子集: %s，總和: %d\n", 
                    currentSubset, currentSubset.stream().mapToInt(Integer::intValue).sum());
            }
            return true;
        }
        
        // 基底條件：超出陣列範圍或剩餘總和為負數
        if (index >= arr.length || remainingSum < 0) {
            if (showSteps) {
                printIndent(recursionDepth);
                System.out.println("無效路徑");
            }
            return false;
        }
        
        recursionDepth++;
        
        // 選擇1：不包含當前元素
        if (showSteps) {
            printIndent(recursionDepth - 1);
            System.out.printf("不選擇 arr[%d]=%d\n", index, arr[index]);
        }
        boolean excludeCurrent = hasSubsetSumHelper(arr, index + 1, remainingSum, currentSubset);
        
        // 選擇2：包含當前元素
        if (showSteps) {
            printIndent(recursionDepth - 1);
            System.out.printf("選擇 arr[%d]=%d\n", index, arr[index]);
        }
        currentSubset.add(arr[index]);
        boolean includeCurrent = hasSubsetSumHelper(arr, index + 1, remainingSum - arr[index], currentSubset);
        currentSubset.remove(currentSubset.size() - 1); // 回溯
        
        recursionDepth--;
        
        return excludeCurrent || includeCurrent;
    }
    
    /**
     * 工具方法：交換陣列中兩個元素
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    /**
     * 工具方法：列印縮排（用於顯示遞迴層次）
     */
    private static void printIndent(int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
    }
    
    /**
     * 工具方法：陣列轉字串（指定範圍）
     */
    private static String arrayToString(int[] arr, int start, int end) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = start; i <= end && i < arr.length; i++) {
            if (i > start) sb.append(", ");
            sb.append(arr[i]);
        }
        sb.append("]");
        return sb.toString();
    }
    
    /**
     * 工具方法：完整陣列轉字串
     */
    private static String arrayToString(int[] arr) {
        return Arrays.toString(arr);
    }
    
    /**
     * 生成隨機陣列
     */
    private static int[] generateRandomArray(int size, int min, int max) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(max - min + 1) + min;
        }
        return arr;
    }
    
    /**
     * 輸入陣列的方法
     */
    private static int[] inputArray(String prompt) {
        System.out.println(prompt);
        System.out.println("1. 手動輸入陣列");
        System.out.println("2. 生成隨機陣列");
        System.out.print("請選擇 (1-2): ");
        
        try {
            int choice = scanner.nextInt();
            
            if (choice == 1) {
                System.out.print("請輸入陣列大小: ");
                int size = scanner.nextInt();
                
                if (size <= 0 || size > 100) {
                    System.out.println("陣列大小必須在 1-100 之間");
                    return null;
                }
                
                int[] arr = new int[size];
                System.out.println("請輸入 " + size + " 個整數（空格分隔）:");
                for (int i = 0; i < size; i++) {
                    arr[i] = scanner.nextInt();
                }
                return arr;
                
            } else if (choice == 2) {
                System.out.print("請輸入陣列大小: ");
                int size = scanner.nextInt();
                System.out.print("請輸入最小值: ");
                int min = scanner.nextInt();
                System.out.print("請輸入最大值: ");
                int max = scanner.nextInt();
                
                if (size <= 0 || size > 100) {
                    System.out.println("陣列大小必須在 1-100 之間");
                    return null;
                }
                
                if (min > max) {
                    System.out.println("最小值不能大於最大值");
                    return null;
                }
                
                return generateRandomArray(size, min, max);
                
            } else {
                System.out.println("無效選擇");
                return null;
            }
        } catch (Exception e) {
            System.out.println("輸入錯誤");
            scanner.nextLine();
            return null;
        }
    }
    
    /**
     * 顯示主選單
     */
    public static void displayMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("陣列遞迴操作進階");
        System.out.println("=".repeat(60));
        System.out.println("1. 遞迴快速排序");
        System.out.println("2. 遞迴合併兩個排序陣列");
        System.out.println("3. 遞迴尋找第 k 小元素");
        System.out.println("4. 遞迴檢查子序列總和");
        System.out.println("5. 切換顯示遞迴步驟 (目前: " + (showSteps ? "開啟" : "關閉") + ")");
        System.out.println("6. 退出程式");
        System.out.println("=".repeat(60));
        System.out.print("請選擇功能 (1-6): ");
    }
    
    /**
     * 處理快速排序
     */
    public static void handleQuickSort() {
        System.out.println("\n--- 遞迴快速排序 ---");
        
        int[] arr = inputArray("準備進行快速排序:");
        if (arr == null) return;
        
        System.out.println("原始陣列: " + arrayToString(arr));
        
        recursionDepth = 0;
        long startTime = System.nanoTime();
        quickSort(arr, 0, arr.length - 1);
        long endTime = System.nanoTime();
        
        System.out.println("排序完成: " + arrayToString(arr));
        System.out.printf("執行時間: %.3f 毫秒\n", (endTime - startTime) / 1_000_000.0);
        System.out.println("演算法資訊: 平均時間複雜度 O(n log n)，空間複雜度 O(log n)");
    }
    
    /**
     * 處理陣列合併
     */
    public static void handleMergeArrays() {
        System.out.println("\n--- 遞迴合併排序陣列 ---");
        
        int[] arr1 = inputArray("輸入第一個排序陣列:");
        if (arr1 == null) return;
        
        int[] arr2 = inputArray("輸入第二個排序陣列:");
        if (arr2 == null) return;
        
        // 確保陣列已排序
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        
        System.out.println("第一個陣列: " + arrayToString(arr1));
        System.out.println("第二個陣列: " + arrayToString(arr2));
        
        recursionDepth = 0;
        long startTime = System.nanoTime();
        int[] merged = mergeArrays(arr1, arr2);
        long endTime = System.nanoTime();
        
        System.out.println("合併結果: " + arrayToString(merged));
        System.out.printf("執行時間: %.3f 毫秒\n", (endTime - startTime) / 1_000_000.0);
        System.out.println("演算法資訊: 時間複雜度 O(m + n)，空間複雜度 O(m + n)");
    }
    
    /**
     * 處理第k小元素查找
     */
    public static void handleKthSmallest() {
        System.out.println("\n--- 遞迴尋找第 k 小元素 ---");
        
        int[] arr = inputArray("準備查找第k小元素:");
        if (arr == null) return;
        
        System.out.print("請輸入 k (1 到 " + arr.length + "): ");
        try {
            int k = scanner.nextInt();
            
            if (k < 1 || k > arr.length) {
                System.out.println("k 必須在 1 到 " + arr.length + " 之間");
                return;
            }
            
            System.out.println("原始陣列: " + arrayToString(arr));
            System.out.println("排序後參考: " + arrayToString(Arrays.stream(arr).sorted().toArray()));
            
            recursionDepth = 0;
            long startTime = System.nanoTime();
            int result = findKthSmallest(arr, k);
            long endTime = System.nanoTime();
            
            System.out.printf("第 %d 小元素: %d\n", k, result);
            System.out.printf("執行時間: %.3f 毫秒\n", (endTime - startTime) / 1_000_000.0);
            System.out.println("演算法資訊: 平均時間複雜度 O(n)，最壞 O(n²)");
            
        } catch (Exception e) {
            System.out.println("輸入錯誤");
            scanner.nextLine();
        }
    }
    
    /**
     * 處理子序列總和檢查
     */
    public static void handleSubsetSum() {
        System.out.println("\n--- 遞迴檢查子序列總和 ---");
        
        int[] arr = inputArray("準備檢查子序列總和:");
        if (arr == null) return;
        
        System.out.print("請輸入目標總和: ");
        try {
            int target = scanner.nextInt();
            
            System.out.println("陣列: " + arrayToString(arr));
            System.out.println("目標總和: " + target);
            
            if (arr.length > 15) {
                System.out.println("陣列較大，執行時間可能較長...");
            }
            
            recursionDepth = 0;
            long startTime = System.nanoTime();
            boolean result = hasSubsetSum(arr, target);
            long endTime = System.nanoTime();
            
            System.out.printf("結果: %s存在子序列總和等於 %d\n", 
                result ? "" : "不", target);
            System.out.printf("執行時間: %.3f 毫秒\n", (endTime - startTime) / 1_000_000.0);
            System.out.println("演算法資訊: 時間複雜度 O(2^n)，空間複雜度 O(n)");
            
        } catch (Exception e) {
            System.out.println("輸入錯誤");
            scanner.nextLine();
        }
    }
    
    public static void main(String[] args) {
        System.out.println("歡迎使用陣列遞迴操作進階程式！");
        System.out.println("此程式實作了四種重要的陣列遞迴演算法");
        
        while (true) {
            displayMenu();
            
            try {
                int choice = scanner.nextInt();
                
                switch (choice) {
                    case 1:
                        handleQuickSort();
                        break;
                    case 2:
                        handleMergeArrays();
                        break;
                    case 3:
                        handleKthSmallest();
                        break;
                    case 4:
                        handleSubsetSum();
                        break;
                    case 5:
                        showSteps = !showSteps;
                        System.out.println("遞迴步驟顯示已" + (showSteps ? "開啟" : "關閉"));
                        break;
                    case 6:
                        System.out.println("\n感謝使用陣列遞迴操作進階程式！再見！👋");
                        scanner.close();
                        return;
                    default:
                        System.out.println("無效的選擇，請輸入 1-6");
                }
                
                if (choice >= 1 && choice <= 4) {
                    System.out.print("\n按 Enter 鍵繼續...");
                    scanner.nextLine();
                    scanner.nextLine();
                }
                
            } catch (Exception e) {
                System.out.println("輸入錯誤：請輸入有效的數字");
                scanner.nextLine();
            }
        }
    }
}