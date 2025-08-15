/*
題目 2：檢測陣列是否為有效 Max Heap
題目描述：
給定一個陣列，判斷它是否符合 Max Heap 的性質。如果不符合，指出第一個違反規則的節點位置。
測試案例：
[100, 90, 80, 70, 60, 75, 65] → true
[100, 90, 80, 95, 60, 75, 65] → false (索引3的95大於父節點90)
[50] → true
[] → true
*/

public class ValidMaxHeapChecker {
    
    /**
     * 檢查陣列是否為有效的 Max Heap
     */
    public static boolean isValidMaxHeap(int[] arr) {
        // 空陣列或單一元素視為有效 heap
        if (arr == null || arr.length <= 1) {
            return true;
        }
        
        int n = arr.length;
        
        // 遍歷所有非葉子節點
        // 最後一個非葉子節點的索引是 (n-2)/2
        for (int i = 0; i <= (n - 2) / 2; i++) {
            int leftChild = 2 * i + 1;
            int rightChild = 2 * i + 2;
            
            // 檢查左子節點
            if (leftChild < n && arr[i] < arr[leftChild]) {
                System.out.println("違規節點：索引 " + leftChild + " 的值 " + arr[leftChild] + 
                                 " 大於父節點索引 " + i + " 的值 " + arr[i]);
                return false;
            }
            
            // 檢查右子節點（如果存在）
            if (rightChild < n && arr[i] < arr[rightChild]) {
                System.out.println("違規節點：索引 " + rightChild + " 的值 " + arr[rightChild] + 
                                 " 大於父節點索引 " + i + " 的值 " + arr[i]);
                return false;
            }
        }
        
        return true;
    }
    
    /*
     * 列印
     */
    public static void printArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            System.out.print("[]");
            return;
        }
        
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.print("]");
    }
    
    public static void main(String[] args) {
        System.out.println("\n=== Max Heap 測試 ===\n");
        
        // 測試案例
        int[][] testCases = {
            {100, 90, 80, 70, 60, 75, 65},  // 有效的 Max Heap
            {100, 90, 80, 95, 60, 75, 65},  // 無效：索引3的95大於父節點90
            {50},                            // 單一元素
            {},                              // 空陣列
            {10, 5},                         // 兩個元素，有效
            {5, 10},                         // 兩個元素，無效
            {100, 90, 80, 70, 60, 75, 85}   // 無效：索引6的85大於父節點80
        };
        
        for (int i = 0; i < testCases.length; i++) {
            System.out.print("測試案例 " + (i + 1) + ": ");
            printArray(testCases[i]);
            
            boolean result = isValidMaxHeap(testCases[i]);
            System.out.println(" → " + result);
            
            if (i < testCases.length - 1) {
                System.out.println();
            }
        }
        
        System.out.println("\n=== 測試完成 ===");
    }
}